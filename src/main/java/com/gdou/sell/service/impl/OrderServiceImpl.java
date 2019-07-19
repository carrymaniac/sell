package com.gdou.sell.service.impl;

import com.gdou.sell.Exception.SellException;
import com.gdou.sell.converter.OrderMasterToOrderDTOConverter;
import com.gdou.sell.dao.OrderDetailDao;
import com.gdou.sell.dao.OrderMasterDao;
import com.gdou.sell.dataobject.OrderDetail;
import com.gdou.sell.dataobject.OrderMaster;
import com.gdou.sell.dataobject.ProductInfo;
import com.gdou.sell.dto.CartDTO;
import com.gdou.sell.dto.OrderDTO;
import com.gdou.sell.enums.OrderStatusEnum;
import com.gdou.sell.enums.PayStatusEnum;
import com.gdou.sell.enums.ResultEnum;
import com.gdou.sell.service.OrderService;
import com.gdou.sell.service.ProductService;
import com.gdou.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDetailDao orderDetailDao;
    @Autowired
    ProductService productService;
    @Autowired
    OrderMasterDao orderMasterDao;
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId =KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1.查询商品(数量,价格)
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo one = productService.findOne(orderDetail.getProductId());
            if(one == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价
            orderAmount = one.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(one,orderDetail);
            OrderDetail save = orderDetailDao.save(orderDetail);
            log.info("{订单详情入数据库}"+save);
        }

        //3.写入订单数据库(orderMaster,OrderDetail)
        OrderMaster orderMaster = new OrderMaster();
        //注意 要先拷贝 否则为null的值也会被覆盖进去
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //原本初始化的状态也被覆盖了
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterDao.save(orderMaster);
        //4.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> optional = orderMasterDao.findById(orderId);
        if(!optional.isPresent()){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }else {
            OrderMaster orderMaster = optional.get();
            List<OrderDetail> byOrOrderId = orderDetailDao.findByOrOrderId(orderId);
            if(CollectionUtils.isEmpty(byOrOrderId)){
                throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
            }
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster,orderDTO);
            orderDTO.setOrderDetailList(byOrOrderId);
            return orderDTO;
        }

    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> byBuyerOpenid = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> convert = OrderMasterToOrderDTOConverter.convert(byBuyerOpenid.getContent());
        return new PageImpl<>(convert,pageable,byBuyerOpenid.getTotalElements());

    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster save = orderMasterDao.save(orderMaster);
        if(save ==null){
            log.error("[取消订单] 更新失败了,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //库存返回
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 订单中无商品详情,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream().map(e ->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOS);
        //已支付,需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO FIXME 进行退款操作



        }
        return orderDTO;


    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[支付订单] 订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断付款状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[支付订单] 订单支付状态不正确,orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster save = orderMasterDao.save(orderMaster);
        if(save ==null){
            log.error("[支付订单] 订单支付失败,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单的状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[完结订单] 订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单的状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster save = orderMasterDao.save(orderMaster);
        if(save ==null){
            log.error("[完结订单] 更新失败了,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> byBuyerOpenid = orderMasterDao.findAll(pageable);
        List<OrderDTO> convert = OrderMasterToOrderDTOConverter.convert(byBuyerOpenid.getContent());
        return new PageImpl<>(convert,pageable,byBuyerOpenid.getTotalElements());
    }
}
