package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.OrderDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Order;
import com.gopher.system.model.OrderCommodity;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
    private OrderDAO orderDAO;
	@Override
	public Integer insert(Order t) {
		return orderDAO.insert(t);
	}

	@Override
	public void update(Order t) {
		orderDAO.update(t);
	}

	@Override
	public void delete(Integer pk) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Order> findList(Order t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOne(Integer pk) {
		Order query = new Order();
		query.setId(pk);
		return orderDAO.findOne(query);
	}

	@Override
	public List<Order> getOrderList(int limit) {
		return null;
	}

	@Override
	public void insert(OrderRequst orderRequst) {
		if(null == orderRequst){
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		String commodityListJson = orderRequst.getCommodityListJson();
		if(!StringUtils.hasText(commodityListJson)){
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		List<OrderCommodity> list = JSON.parseArray(commodityListJson, OrderCommodity.class);
		if(list == null || list.isEmpty()){
			throw new BusinessRuntimeException("请选择商品");
		}
		
		// TODO 1 生成订单号,保存订单
		//      2添加此次订单的商品到order_commodity表
		
	}

}
