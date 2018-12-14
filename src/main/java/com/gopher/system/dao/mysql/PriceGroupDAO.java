package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.Commodity;
import com.gopher.system.model.PriceGroup;
import com.gopher.system.model.vo.request.PriceCommodityPageRequest;
import com.gopher.system.model.vo.request.PriceGroupPageRequst;

public interface PriceGroupDAO {
	
	public void insert(PriceGroup priceGroup);
	
	public void update(PriceGroup priceGroup);
	
	public PriceGroup findOne(PriceGroup priceGroup);
    
	public List<PriceGroup> findList(PriceGroupPageRequst priceGroupPageRequst);
	
	public int count(PriceGroupPageRequst priceGroupPageRequst);
	
	public List<Commodity> getPage(PriceCommodityPageRequest priceCommodityPageRequest);
	
	public int getCount(PriceCommodityPageRequest priceCommodityPageRequest);
}
