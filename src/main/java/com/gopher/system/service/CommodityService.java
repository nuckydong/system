package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.Commodity;
import com.gopher.system.model.vo.request.CommodityListRequst;
import com.gopher.system.model.vo.response.CommodityResponse;

public interface CommodityService {
	
	public Commodity get(int id);
	
	public CommodityResponse getCommodity(int id);
	
	public Integer insert(Commodity commodity);
	
	List<CommodityResponse> getCommodityList(CommodityListRequst commodityListRequst);
}
