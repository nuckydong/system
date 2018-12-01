package com.gopher.system.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.gopher.system.model.Order;
import com.gopher.system.model.vo.request.OrderPageRequst;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.model.vo.response.OrderDetailResponse;
import com.gopher.system.model.vo.response.OrderPageResponse;
import com.gopher.system.util.Page;

public interface OrderService{
	/**
	 * 获取当前用户的订单列表
	 * @param 
	 * @return
	 */
	List<Order> getOrderListByCurrentUser();
	/**
	 * 订单分页列表
	 * @return
	 */
	Page<OrderPageResponse> getOrderPage(OrderPageRequst orderPageRequst);
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
	
	
	public void deleteByCustomerId(int customerId);
	/**
	 * 编辑订单
	 * @param orderRequst
	 * @return
	 */
	public OrderDetailResponse updateOrder(OrderRequst orderRequst);
	
	
	public void exportOrder(int id,HttpServletResponse response);
	

}
