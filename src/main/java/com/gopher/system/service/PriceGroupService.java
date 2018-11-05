package com.gopher.system.service;

import com.gopher.system.model.Page;
import com.gopher.system.model.PriceGroup;
import com.gopher.system.model.vo.request.PriceGroupPageRequst;
import com.gopher.system.model.vo.request.PriceGroupRequest;
import com.gopher.system.model.vo.response.PriceGroupResponse;

public interface PriceGroupService {
	
	public void add(PriceGroupRequest priceGroupRequest);
	
	public void update(PriceGroupRequest priceGroupRequest);
	
	public void delete(int id);
	
	public PriceGroup getPriceGroup(int id);
	
	public PriceGroupResponse get(int id);
	
	/**
	 * 定价表分页列表
	 * @return
	 */
	Page<PriceGroup> getPage(PriceGroupPageRequst priceGroupPageRequst);
	

}
