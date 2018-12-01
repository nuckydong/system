package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.CommodityPriceDAO;
import com.gopher.system.model.CommodityPrice;
import com.gopher.system.model.CustomerPrice;
import com.gopher.system.service.CommodityPriceService;
import com.gopher.system.service.CustomerPriceService;
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
	@Autowired
    private CustomerPriceService customerPriceService;
	@Override
	public List<CommodityPrice> getListByCustomer(int customerId) {
		CustomerPrice customerPrice = customerPriceService.getByCustomerId(customerId);
		if(null == customerPrice) {
			return null;
		}
		int priceGroupId = customerPrice.getPriceGroupId();
		CommodityPrice commodityPrice = new CommodityPrice();
		commodityPrice.setPriceGroupId(priceGroupId);
		return commodityPriceDAO.findList(commodityPrice);
	}

}
