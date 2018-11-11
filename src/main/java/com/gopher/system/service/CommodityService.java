package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.Commodity;
import com.gopher.system.model.vo.request.CommodityListRequst;
import com.gopher.system.model.vo.request.CommodityPageRequst;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.util.Page;

public interface CommodityService {
	
	public Commodity get(int id);
	
	public CommodityResponse getCommodity(int id);
	
	public Integer insert(Commodity commodity);
	
	public void update(Commodity comodity);
	
	public void delete(int id);
	
	List<CommodityResponse> getCommodityList(CommodityListRequst commodityListRequst);
	
	public  Page<CommodityResponse> getCommodityPage(CommodityPageRequst commodityPageRequst);
}
