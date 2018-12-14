package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.vo.request.CustomerCommodityGroupRequset;
import com.gopher.system.model.vo.request.GroupCommodityPageRequst;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.model.vo.response.CustomerCommodityGroupResponse;
import com.gopher.system.util.Page;

public interface CustomerCommodityGroupService {

	public void add(CustomerCommodityGroupRequset customerCommodityGroupRequset);

	public void update(CustomerCommodityGroupRequset customerCommodityGroupRequset);

	public CustomerCommodityGroupResponse get(int id);

	/**
	 * 删除商品菜单
	 * 
	 * @param id
	 */
	public void delete(int id);

	/*
	 * 获取对应客户的商品菜单
	 */
	public List<CustomerCommodityGroupResponse> getList(int customerId);

	/**
	 * 获取当前登录用户对应客户的商品菜单
	 * 
	 * @return
	 */
	public List<CustomerCommodityGroupResponse> getList();

	/**
	 * 获取当前分组下的商品列表
	 * 
	 * @param groupCommodityPageRequst
	 * @return
	 */
	Page<CommodityResponse> getCommodityPage(GroupCommodityPageRequst groupCommodityPageRequst);
}
