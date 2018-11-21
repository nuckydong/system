package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.CustomerCommodityGroup;

public interface CustomerCommodityGroupDAO {
	
	public void insert(CustomerCommodityGroup customerCommodityGroup);
	
	public void update(CustomerCommodityGroup customerCommodityGroup);
	
	public CustomerCommodityGroup findOne(int id);
	
	public void delete(int id);
	
	public List<CustomerCommodityGroup> findList(CustomerCommodityGroup customerCommodityGroup);

}
