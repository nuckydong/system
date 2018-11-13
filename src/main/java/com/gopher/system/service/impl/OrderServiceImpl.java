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
import com.gopher.system.constant.State;
import com.gopher.system.dao.mysql.OrderDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Customer;
import com.gopher.system.model.CustomerUser;
import com.gopher.system.model.Order;
import com.gopher.system.model.OrderCommodity;
import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.OrderPageRequst;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.model.vo.response.OrderDetailResponse;
import com.gopher.system.service.CommodityService;
import com.gopher.system.service.CustomerPriceService;
import com.gopher.system.service.CustomerService;
import com.gopher.system.service.CustomerUserService;
import com.gopher.system.service.OrderCommodityService;
import com.gopher.system.service.OrderService;
import com.gopher.system.service.UserService;
import com.gopher.system.util.Page;
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
	@Autowired
	private CustomerUserService customerUserService;
	@Autowired
	private CustomerService customerService;
	@Autowired
    private CustomerPriceService  customerPriceService;
	
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
		Order order = new Order();
		order.setNumber(System.currentTimeMillis()+"");
		final int userId = userService.getCurrentUserId();
		order.setCreateUser(userId);
		order.setUpdateUser(userId);
		order.setRemark(orderRequst.getRemark());
		CustomerUser cu = customerUserService.get(userId);
		if(null == cu){
			throw new BusinessRuntimeException("根据用户ID找不到对应的客户");
		}
		/**
		 * 当前订单的客户ID
		 */
		order.setCustomerId(cu.getCustomerId());
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
		CustomerUser cu = customerUserService.get(userService.getCurrentUserId());
		if(null == cu){
			throw new BusinessRuntimeException("根据用户ID找不到对应的客户");
		}
		order.setCustomerId(cu.getCustomerId());
		return orderDAO.findList(order);
	}
	
	
    private String getUserName(int userId){
    	User user = userService.getUerById(userId);
    	String userName = "";
    	if(null != user){
    		userName = user.getName();
    	}
    	return userName;
    }
    
    
	@Override
	public OrderDetailResponse getOrderDetail(int id) {
		if(id <=0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		Order order = this.getOrder(id);
		OrderDetailResponse result = null;
		if(null != order) {
			result = new OrderDetailResponse();
			result.setId(order.getId());
			result.setCreateTime(order.getCreateTime().getTime());
			result.setUpdateTime(order.getUpdateTime().getTime());
			result.setUpdateUser(this.getUserName(order.getUpdateUser()));
			result.setCreateUser(this.getUserName(order.getCreateUser()));
			result.setRemark(order.getRemark());
			result.setNumber(order.getNumber());
			final int customerId = order.getCustomerId();
			/**
			 * 当前客户使用的定价号
			 */
			//TODO 
			result.setPriceNumber(customerPriceService.getPriceNumberByCustomerId(customerId));
			Customer customer = customerService.findById(customerId);
			if(null != customer) {
				result.setCompany(customer.getName());
				result.setPhone(customer.getMobilePhone());
			}
			result.setNumber(order.getNumber());
			List<OrderCommodity> list = orderCommodityService.findList(id);
			if(null != list) {
				List<CommodityResponse> commodityList = new ArrayList<>(list.size());
				for (OrderCommodity orderCommodity : list) {
					final int commodityId = orderCommodity.getCommodityId();
					CommodityResponse response = new CommodityResponse();
					response.setId(commodityId);
					response.setCommodityId(commodityId);
					CommodityResponse rsp = commodtityService.getCommodity(commodityId);
					response.setName(rsp.getName());
					response.setCommodityTypeId(rsp.getCommodityTypeId());
					response.setCommodityTypeName(rsp.getCommodityTypeName());
					response.setPrice(orderCommodity.getPrice());
					response.setUnit(orderCommodity.getUnit());
					response.setAmount(orderCommodity.getAmount());
					commodityList.add(response);
				}
				result.setCommodityList(commodityList);
			}
		}
		return result;
	}

	@Override
	public void deleteOrder(int id) {
		if(id <=0) {
			throw new BusinessRuntimeException("无效的订单ID");
		}
		Order order = this.getOrder(id);
		order.setState(State.INVALID.getState());
		order.setUpdateUser(userService.getCurrentUserId());
		orderDAO.update(order);
		// 暂时不删除订单和商品的绑定关系表
	}
	
    private Order getOrder(int orderId){
		Order order = new Order();
		order.setId(orderId);
		order = orderDAO.findOne(order);
		if(null == order){
			throw new BusinessRuntimeException("根据订单ID找不到订单信息");
		}
		return order;
    }
    
    @Transactional
	@Override
	public OrderDetailResponse updateOrder(OrderRequst orderRequst) {
		if(orderRequst == null) {
			throw new BusinessRuntimeException("参数不能为空");
		}
		final int orderId = orderRequst.getId();
		final String commodityListJson = orderRequst.getCommodityListJson();
		if(!StringUtils.hasText(commodityListJson)){
			throw new BusinessRuntimeException("请选择商品");
		}
		if(orderId<=0){
			throw new BusinessRuntimeException("无效的订单ID");
		}
		Order order = this.getOrder(orderId);
		order.setRemark(orderRequst.getRemark());
		order.setUpdateUser(userService.getCurrentUserId());
		orderDAO.update(order);
		//删除之前的关联
		orderCommodityService.deleteByOrderId(orderId);
		//建立新的订单商品关联
		List<OrderCommodity> list = JSON.parseArray(commodityListJson, OrderCommodity.class);
		if(list == null || list.isEmpty()){
			throw new BusinessRuntimeException("请选择商品");
		}
		for (OrderCommodity orderCommodity : list) {
			orderCommodity.setOrderId(order.getId());
			orderCommodityService.insert(orderCommodity);
		}
		return this.getOrderDetail(orderId);
	}
	@Override
	public Page<Order> getOrderPage(OrderPageRequst orderPageRequst) {
		List<Order> list = orderDAO.findPage(orderPageRequst);
		final int totalCount = orderDAO.count(orderPageRequst);
		Page<Order> result = null;
		if(null != list) {
			result = new Page<>();
			for (Order order : list) {
				Customer customer = customerService.findById(order.getCustomerId());
				if(null != customer) {
					order.setCustomerName(customer.getName());
				}
			}
			result.setList(list);
		}
		result.setPageNumber(orderPageRequst.getPageNumber());
		result.setPageSize(orderPageRequst.getPageSize());
		result.setTotalCount(totalCount);
		return result;
	}

}
