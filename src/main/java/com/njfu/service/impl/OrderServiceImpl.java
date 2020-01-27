package com.njfu.service.impl;

import com.njfu.dao.OrderDOMapper;
import com.njfu.dao.SequenceDOMapper;
import com.njfu.dataobject.OrderDO;
import com.njfu.dataobject.SequenceDO;
import com.njfu.error.BusinessException;
import com.njfu.error.EmBusinessError;
import com.njfu.service.ItemService;
import com.njfu.service.OrderService;
import com.njfu.service.UserService;
import com.njfu.service.model.ItemModel;
import com.njfu.service.model.OrderModel;
import com.njfu.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //验证下单是否合法
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }

        if (amount <= 0||amount>=999){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量超出范围");
        }
        //下单完成减库存
        boolean  result = itemService.decreaseStock(itemId,amount);
        if (!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        //生成id订单号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //返回前端
        itemService.increaseSales(itemId,amount);

        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
     String generateOrderNo(){
        //前8 年月日
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime nowTime = LocalDateTime.now();
        String  nowDate = nowTime.format(DateTimeFormatter.BASIC_ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        //自增序列  保证单日订单号不重复

        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");

       sequence =  sequenceDO.getCurrentValue();
       sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
       sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
       String sequenceStr = String.valueOf(sequence);
       for (int i = 0; i < 6 - sequenceStr.length();i++){
           stringBuilder.append(0);
       }
       stringBuilder.append(sequenceStr);
        //库表位
        stringBuilder.append("00");

        return stringBuilder.toString();
    }


    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if (orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}
