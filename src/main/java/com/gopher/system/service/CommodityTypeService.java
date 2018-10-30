package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.CommodityType;

public interface CommodityTypeService {
	
	public CommodityType getCommodityTypeById(int id);
	
	public List<CommodityType> getCommodityTypeList();
	
	public void update(CommodityType commodityType);
	
	public Integer insert(CommodityType commodityType);

}
