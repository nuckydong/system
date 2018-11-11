package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.Commodity;
import com.gopher.system.model.vo.request.CommodityPageRequst;

public interface CommodityDAO extends BaseDAO<Commodity>{
	
	List<Commodity>  getPage(CommodityPageRequst commodityPageRequst);
	
	int count(CommodityPageRequst commodityPageRequst);
	
}
