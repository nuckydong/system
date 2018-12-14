package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.Commodity;
import com.gopher.system.model.CustomerCommodityGroup;
import com.gopher.system.model.vo.request.GroupCommodityPageRequst;

public interface CustomerCommodityGroupDAO {
	
	public void insert(CustomerCommodityGroup customerCommodityGroup);
	
	public void update(CustomerCommodityGroup customerCommodityGroup);
	
	public CustomerCommodityGroup findOne(int id);
	
	public void delete(int id);
	
	public List<CustomerCommodityGroup> findList(CustomerCommodityGroup customerCommodityGroup);
	
	List<Commodity> getPage(GroupCommodityPageRequst groupCommodityPageRequst);
    
	int getCount(GroupCommodityPageRequst groupCommodityPageRequst);
	

}
