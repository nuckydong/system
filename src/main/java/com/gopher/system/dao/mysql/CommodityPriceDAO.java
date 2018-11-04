package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.CommodityPrice;

/**
 * 商品定价表
 * @author nucky
 *
 */
public interface CommodityPriceDAO {
	/**
	 * 添加
	 * @param commodityPrice
	 */
	public void insert(CommodityPrice commodityPrice);
	
	public void update(CommodityPrice commodityPrice);
	
	public void delete(CommodityPrice commodityPrice);
	
	public CommodityPrice findOne(int id);
	
	public List<CommodityPrice> findList(CommodityPrice commodityPrice);

}
