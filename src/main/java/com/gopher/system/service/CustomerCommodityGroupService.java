package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.vo.request.CustomerCommodityGroupRequset;
import com.gopher.system.model.vo.response.CustomerCommodityGroupResponse;

public interface CustomerCommodityGroupService {
	
	public void add(CustomerCommodityGroupRequset customerCommodityGroupRequset);
	
	public void update(CustomerCommodityGroupRequset customerCommodityGroupRequset);
	
	public CustomerCommodityGroupResponse get(int id);
	
	public void delete(int id);
	
	public List<CustomerCommodityGroupResponse> getList(int customerId);

}
