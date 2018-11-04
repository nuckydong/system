package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.CommodityPriceDAO;
import com.gopher.system.model.CommodityPrice;
import com.gopher.system.service.CommodityPriceService;
@Service
public class CommodityPriceServiceImpl implements CommodityPriceService{
	@Autowired
    private CommodityPriceDAO commodityPriceDAO;
	@Override
	public void add(CommodityPrice commodityPrice) {
		commodityPriceDAO.insert(commodityPrice);
	}

	@Override
	public void update(CommodityPrice commodityPrice) {
		commodityPriceDAO.update(commodityPrice);
	}

	@Override
	public void delete(CommodityPrice commodityPrice) {
		commodityPriceDAO.delete(commodityPrice);
	}

	@Override
	public CommodityPrice get(int id) {
		return commodityPriceDAO.findOne(id);
	}

	@Override
	public List<CommodityPrice> getList(int priceGroupId) {
		CommodityPrice commodityPrice = new CommodityPrice();
		commodityPrice.setPriceGroupId(priceGroupId);
		return commodityPriceDAO.findList(commodityPrice);
	}

}
