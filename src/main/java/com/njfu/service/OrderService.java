package com.njfu.service;

import com.njfu.error.BusinessException;
import com.njfu.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}
