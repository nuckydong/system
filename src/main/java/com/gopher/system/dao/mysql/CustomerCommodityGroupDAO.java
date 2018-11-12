package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.CustomerCommodityGroup;

public interface CustomerCommodityGroupDAO {
	
	void add(CustomerCommodityGroup customerCommodityGroup);
	
	void update(CustomerCommodityGroup customerCommodityGroup);
	
	void delete(int id);
	
	List<CustomerCommodityGroup> getList(CustomerCommodityGroup customerCommodityGroup);

}
