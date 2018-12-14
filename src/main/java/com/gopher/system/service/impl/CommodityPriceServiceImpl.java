package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gopher.system.dao.mysql.CommodityPriceDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.CommodityPrice;
import com.gopher.system.model.CustomerPrice;
import com.gopher.system.service.CommodityPriceService;
import com.gopher.system.service.CustomerPriceService;
@Service
public class CommodityPriceServiceImpl implements CommodityPriceService{
	@Autowired
    private CommodityPriceDAO commodityPriceDAO;
	
	@Transactional
	@Override
	public void add(CommodityPrice commodityPrice) {
		// 查看当前的 priceGroupID 和communityId 是否已经存在双向绑定，如果存在则提示，已经添加过的商品
		List<CommodityPrice> list = commodityPriceDAO.findList(commodityPrice);
		if(null != list && list.size() > 0) {
			throw new BusinessRuntimeException("已经设置价格的商品");
		}
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
