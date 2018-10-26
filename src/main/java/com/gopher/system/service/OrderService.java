package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.Order;
import com.gopher.system.model.vo.request.OrderRequst;

public interface OrderService extends BaseService<Order,Integer>{
	/**
	 * 获取订单
	 * @param limit 最近的条目数量
	 * @return
	 */
	List<Order> getOrderList(int limit);
	/**
	 * 新增订单 
	 * @param orderRequst
	 */
	public void insert(OrderRequst orderRequst);
	

}
