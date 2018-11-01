package com.gopher.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.CommodityDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Commodity;
import com.gopher.system.model.CommodityType;
import com.gopher.system.model.vo.request.CommodityListRequst;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.service.CommodityService;
import com.gopher.system.service.CommodityTypeService;
@Service
public class CommodityServiceImpl implements CommodityService{
	@Autowired
    private CommodityDAO commodityDAO;
	@Autowired
	private CommodityTypeService commodityTypeService;
	@Override
	public Integer insert(Commodity commodity) {
		return commodityDAO.insert(commodity);
	}
	@Override
	public List<CommodityResponse> getCommodityList(CommodityListRequst request) {
		Commodity query = new Commodity();
		if(request != null) {
			query.setCommodityTypeId(request.getCommodityTypeId());
			query.setName(request.getName());
		}
		List<Commodity> list = commodityDAO.findList(query);
		List<CommodityResponse> result = null;
		if(null !=list) {
			result = new ArrayList<>(list.size());
			for (Commodity cmmodity : list) {
				CommodityResponse cr = new CommodityResponse();
				BeanUtils.copyProperties(cmmodity, cr);
				CommodityType commodityType = commodityTypeService.getCommodityTypeById(cmmodity.getCommodityTypeId());
				if(commodityType != null){
					cr.setCommodityTypeName(commodityType.getName());
				}
				cr.setCommodityId(cmmodity.getId());
				result.add(cr);
			}
			
		}
		return result;
	}
	@Override
	public CommodityResponse getCommodity(int id) {
		if(id <=0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		Commodity query = new Commodity();
		query.setId(id);
		Commodity  commodityDB = commodityDAO.findOne(query);
		if(null == commodityDB) {
			throw new BusinessRuntimeException(CodeAndMsg.CANNOT_FIND_IN_DB);
		}
		CommodityResponse cr = new CommodityResponse();
		BeanUtils.copyProperties(commodityDB, cr);
		CommodityType commodityType = commodityTypeService.getCommodityTypeById(commodityDB.getCommodityTypeId());
		if(commodityType != null){
			cr.setCommodityTypeName(commodityType.getName());
		}
		return cr;
	}

}
