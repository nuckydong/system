package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.CommodityType;

public interface CommodityTypeDAO {
	
	public Integer insert(CommodityType commodityType);
	
	public List<CommodityType> findList(CommodityType commodityType);
	
	public CommodityType findOne(int id);
	
	public void update(CommodityType commodityType);

}
