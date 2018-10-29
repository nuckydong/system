package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.Order;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.model.vo.response.OrderDetailResponse;

public interface OrderService{
	/**
	 * 获取当前用户的订单列表
	 * @param 
	 * @return
	 */
	List<Order> getOrderListByCurrentUser();
	/**
	 * 新增订单 
	 * @param orderRequst
	 */
	public void insert(OrderRequst orderRequst);
	
	/**
	 * 获取订单详情
	 * @param id
	 * @return
	 */
	public OrderDetailResponse getOrderDetail(int id);
	/**
	 * 取消订单
	 * @param id
	 */
	public void deleteOrder(int id);
	/**
	 * 编辑订单
	 * @param orderRequst
	 * @return
	 */
	public OrderDetailResponse updateOrder(OrderRequst orderRequst);
	

}
