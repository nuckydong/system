package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.CommodityPrice;

public interface CommodityPriceService {
	
	public void add(CommodityPrice commodityPrice);
	
	public void update(CommodityPrice commodityPrice);
	
	public void delete(CommodityPrice commodityPrice);
	
	public CommodityPrice get(int id);

	public List<CommodityPrice> getList(final int priceGroupId);
}
