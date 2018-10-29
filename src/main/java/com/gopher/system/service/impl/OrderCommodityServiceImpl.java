package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.OrderCommodityDAO;
import com.gopher.system.model.OrderCommodity;
import com.gopher.system.service.OrderCommodityService;
@Service
public class OrderCommodityServiceImpl implements OrderCommodityService{
	@Autowired
    private OrderCommodityDAO orderCommodityDAO;
	@Override
	public Integer insert(OrderCommodity orderCommodity) {
		return orderCommodityDAO.insert(orderCommodity);
	}
	@Override
	public List<OrderCommodity> findList(int orderId) {
		OrderCommodity query = new OrderCommodity();
		query.setOrderId(orderId);
		return orderCommodityDAO.findList(query);
	}
	
	@Override
	public void deleteByOrderId(int orderId) {
		OrderCommodity query = new OrderCommodity();
		query.setOrderId(orderId);
		orderCommodityDAO.delete(query);
	}

}
