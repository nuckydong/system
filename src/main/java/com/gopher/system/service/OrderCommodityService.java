package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.OrderCommodity;

public interface OrderCommodityService{
	/**
	 * 
	 * @param orderCommodity
	 * @return
	 */
	public Integer insert(OrderCommodity orderCommodity);
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderCommodity> findList(int orderId);
	/**
	 * 根据订单ID删除
	 * @param orderId
	 */
	public void deleteByOrderId(int orderId);
}
