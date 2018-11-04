package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.PriceGroup;

public interface PriceGroupDAO {
	
	public void insert(PriceGroup priceGroup);
	
	public void update(PriceGroup priceGroup);
	
	public PriceGroup findOne(PriceGroup priceGroup);
    
	public List<PriceGroup> findList(PriceGroup priceGroup);
	
	public int count(PriceGroup priceGroup);
}
