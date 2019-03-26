package com.gopher.system.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.gopher.system.model.PriceGroup;
import com.gopher.system.model.vo.request.PriceCommodityPageRequest;
import com.gopher.system.model.vo.request.PriceGroupPageRequst;
import com.gopher.system.model.vo.request.PriceGroupRequest;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.model.vo.response.PriceGroupResponse;
import com.gopher.system.util.Page;

public interface PriceGroupService {

	public int add(PriceGroupRequest priceGroupRequest);

	public void update(PriceGroupRequest priceGroupRequest);

	public void delete(int id);

	public PriceGroup getPriceGroup(int id);

	public PriceGroupResponse get(int id);

	/**
	 * 定价表分页列表
	 * 
	 * @return
	 */
	Page<PriceGroup> getPage(PriceGroupPageRequst priceGroupPageRequst);

	/**
	 * 获取所有得定价表
	 * 
	 * @param priceGroupPageRequst
	 * @return
	 */
	List<PriceGroup> getList(PriceGroupPageRequst priceGroupPageRequst);

	/**
	 * 获取当前价格单中得所有商品分页列表
	 * 
	 * @param priceCommodityPageRequest
	 * @return
	 */
	Page<CommodityResponse> getCommodityPage(PriceCommodityPageRequest priceCommodityPageRequest);

	void export(HttpServletResponse response, int id);

}
