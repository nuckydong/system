package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.Order;
import com.gopher.system.model.vo.request.OrderPageRequst;

public interface OrderDAO extends BaseDAO<Order>{
	
	public List<Order> findPage(OrderPageRequst orderPageRequst);
	
	public int count(OrderPageRequst orderPageRequst);

}
