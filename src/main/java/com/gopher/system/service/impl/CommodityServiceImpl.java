package com.gopher.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.CommodityDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Commodity;
import com.gopher.system.model.CommodityType;
import com.gopher.system.model.vo.request.CommodityListRequst;
import com.gopher.system.model.vo.request.CommodityPageRequst;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.service.CommodityService;
import com.gopher.system.service.CommodityTypeService;
import com.gopher.system.util.Page;
@Service
public class CommodityServiceImpl implements CommodityService{
	@Autowired
    private CommodityDAO commodityDAO;
	@Autowired
	private CommodityTypeService commodityTypeService;
	@Override
	public Integer insert(Commodity commodity) {
		if(null == commodity) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final String name = commodity.getName();
		final String unit = commodity.getUnit();
		final int type = commodity.getCommodityTypeId();
	    final int price = commodity.getPrice();
	    if(!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("商品名称不能为空");
	    }
	    if(!StringUtils.hasText(unit)) {
			throw new BusinessRuntimeException("商品价格单位不能为空");
	    }
	    if(type <= 0) {
			throw new BusinessRuntimeException("请选择商品类型");
	    }
	    if(price <= 0) {
	    	throw new BusinessRuntimeException("无效的商品价格");
	    }
	    
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
	@Override
	public Commodity get(int id) {
		Commodity query = new Commodity();
		query.setId(id);
		Commodity  commodityDB = commodityDAO.findOne(query);
		return commodityDB;
	}
	@Override
	public void update(Commodity commodity) {
		if(null == commodity) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		if(commodity.getId() <=0) {
			throw new BusinessRuntimeException("无效的商品ID");
		}
		final String name = commodity.getName();
		final String unit = commodity.getUnit();
		final int type = commodity.getCommodityTypeId();
	    final int price = commodity.getPrice();
	    if(!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("商品名称不能为空");
	    }
	    if(!StringUtils.hasText(unit)) {
			throw new BusinessRuntimeException("商品价格单位不能为空");
	    }
	    if(type <= 0) {
			throw new BusinessRuntimeException("请选择商品类型");
	    }
	    if(price <= 0) {
	    	throw new BusinessRuntimeException("无效的商品价格");
	    }
		commodityDAO.update(commodity);
	}
	@Override
	public void delete(int id) {
		if(id <=0) {
			throw new BusinessRuntimeException("无效的商品ID");
		}
		Commodity query = new Commodity();
		query.setId(id);
		commodityDAO.delete(query);
	}
	@Override
	public Page<CommodityResponse> getCommodityPage(CommodityPageRequst commodityPageRequst) {
		if(null == commodityPageRequst) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		Page<CommodityResponse> result = new Page<CommodityResponse>();
		List<Commodity> list= commodityDAO.getPage(commodityPageRequst);
		if(null != list && !list.isEmpty()) {
			List<CommodityResponse> li = new ArrayList<>();
			for (Commodity commodity : list) {
				li.add(this.getCommodity(commodity.getId()));
			}
			result.setList(li);
		}
		final int totalCount = commodityDAO.count(commodityPageRequst);
		result.setTotalCount(totalCount);
		
		return result;
	}

}
