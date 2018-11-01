package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.Page;
import com.gopher.system.model.PriceGroup;

public interface PriceGroupDAO {
	
	public void insert(PriceGroup priceGroup);
	
	public void update(PriceGroup priceGroup);
	
	public void delete(int id);
	
	public PriceGroup findOne(int id);
    
	public List<PriceGroup> findList(PriceGroup priceGroup);
	
	public Page<PriceGroup> findPage(Page<PriceGroup> page);
}
