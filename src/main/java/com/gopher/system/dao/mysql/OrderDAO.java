package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.Order;
import com.gopher.system.model.vo.request.OrderPageRequst;
import com.gopher.system.model.vo.request.OrderStatisticRequest;

public interface OrderDAO extends BaseDAO<Order>{
	
	public List<Order> findPage(OrderPageRequst orderPageRequst);
	
	public List<Order> findList4Statistic(OrderStatisticRequest orderStatisticRequest);
	
	public int count(OrderPageRequst orderPageRequst);

}
