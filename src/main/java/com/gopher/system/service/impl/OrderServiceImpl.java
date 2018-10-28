package com.gopher.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.OrderDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Order;
import com.gopher.system.model.OrderCommodity;
import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.model.vo.response.OrderDetailResponse;
import com.gopher.system.service.CommodityService;
import com.gopher.system.service.OrderCommodityService;
import com.gopher.system.service.OrderService;
import com.gopher.system.service.UserService;
@Service
public class OrderServiceImpl implements OrderService{
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
    private OrderDAO orderDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private  OrderCommodityService orderCommodityService;
	@Autowired
	private CommodityService commodtityService;
	
    @Transactional
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
		User user = userService.getCurrentUser();
		if(null == user) {
			throw new BusinessRuntimeException(CodeAndMsg.NEED_LOGIN);
		}
		Order order = new Order();
		order.setNumber(System.currentTimeMillis()+"");
		order.setCreateUser(user.getId());
		order.setUpdateUser(user.getId());
		orderDAO.insert(order);
		LOG.info("新增订单成功：订单ID：{}",order.getId());
		// TODO 1 生成订单号,保存订单
		//      2添加此次订单的商品到order_commodity表
		for (OrderCommodity orderCommodity : list) {
			orderCommodity.setOrderId(order.getId());
			orderCommodityService.insert(orderCommodity);
		}
		
	}

	@Override
	public List<Order> getOrderListByCurrentUser() {
		Order order = new Order();
		User user = userService.getCurrentUser();
		if(null == user) {
			throw new BusinessRuntimeException(CodeAndMsg.NEED_LOGIN);
		}
		order.setCreateUser(user.getId());
		return orderDAO.findList(order);
	}

	@Override
	public OrderDetailResponse getOrderDetail(int id) {
		if(id <=0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		Order order = new Order();
		order.setId(id);
		order = orderDAO.findOne(order);
		OrderDetailResponse result = null;
		if(null != order) {
			result = new OrderDetailResponse();
			result.setId(order.getId());
			result.setCreateTime(order.getCreateTime());
			result.setUpdateTime(order.getUpdateTime());
			result.setNumber(order.getNumber());
			List<OrderCommodity> list = orderCommodityService.findList(id);
			if(null != list) {
				List<CommodityResponse> commodityList = new ArrayList<>(list.size());
				for (OrderCommodity orderCommodity : list) {
					final int commodityId = orderCommodity.getCommodityId();
					CommodityResponse response = new CommodityResponse();
					response.setId(commodityId);
					CommodityResponse rsp = commodtityService.getCommodity(commodityId);
					response.setName(rsp.getName());
					response.setCommodityTypeId(rsp.getCommodityTypeId());
					response.setCommodityTypeName(rsp.getCommodityTypeName());
					response.setPrice(orderCommodity.getPrice());
					response.setUnit(orderCommodity.getUnit());
					commodityList.add(response);
				}
				result.setCommodityList(commodityList);
			}
		}
		return result;
	}

}
