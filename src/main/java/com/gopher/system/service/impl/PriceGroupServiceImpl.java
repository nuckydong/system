package com.gopher.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.constant.State;
import com.gopher.system.dao.mysql.PriceGroupDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Commodity;
import com.gopher.system.model.CommodityPrice;
import com.gopher.system.model.PriceGroup;
import com.gopher.system.model.vo.request.PriceGroupPageRequst;
import com.gopher.system.model.vo.request.PriceGroupRequest;
import com.gopher.system.model.vo.response.CommodityPriceResponse;
import com.gopher.system.model.vo.response.PriceGroupResponse;
import com.gopher.system.service.CommodityPriceService;
import com.gopher.system.service.CommodityService;
import com.gopher.system.service.PriceGroupService;
import com.gopher.system.service.UserService;
import com.gopher.system.util.Page;
@Service
public class PriceGroupServiceImpl implements PriceGroupService{
	@Autowired
    private PriceGroupDAO priceGroupDAO;
	@Autowired
	private CommodityPriceService commodityPriceService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private UserService userService;
	/**
	 * 添加定价表
	 */
	@Override
	@Transactional
	public void add(PriceGroupRequest priceGroupRequest) {
		if(null == priceGroupRequest) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final String name = priceGroupRequest.getName();
		final String remark = priceGroupRequest.getRemark();
		final String commodityListJson = priceGroupRequest.getCommodityPriceListJson();
		
		if(!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("名称不能为空");
		}
		if(!StringUtils.hasText(commodityListJson)) {
			throw new BusinessRuntimeException("商品定价列表不能为空");
		}
		PriceGroup priceGroup = new PriceGroup();
		priceGroup.setName(name);
		priceGroup.setNumber("TODO");
		priceGroup.setRemark(remark);
		priceGroupDAO.insert(priceGroup);
		final int userId = userService.getCurrentUserId();
		priceGroup.setCreateUser(userId);
		priceGroup.setUpdateUser(userId);
		final int priceGroupId = priceGroup.getId();
		List<CommodityPrice> list = JSON.parseArray(commodityListJson, CommodityPrice.class);
		if(null != list) {
			for (CommodityPrice commodityPrice : list) {
				commodityPrice.setPriceGroupId(priceGroupId);
				commodityPriceService.add(commodityPrice);
			}
		}
	}

	@Override
	public void update(PriceGroupRequest priceGroupRequest) {
		if(null == priceGroupRequest) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int id = priceGroupRequest.getId();
		final String name = priceGroupRequest.getName();
		final String remark = priceGroupRequest.getRemark();
		final String commodityListJson = priceGroupRequest.getCommodityPriceListJson();
		if(id <= 0) {
			throw new BusinessRuntimeException("ID不能为空");
		}
		if(!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("名称不能为空");
		}
		if(!StringUtils.hasText(commodityListJson)) {
			throw new BusinessRuntimeException("商品定价列表不能为空");
		}
		PriceGroup priceGroup =new PriceGroup();
		priceGroup.setId(id);
		 priceGroup = priceGroupDAO.findOne(priceGroup);
		if(null == priceGroup) {
			throw new BusinessRuntimeException("根据ID找不到对应的记录");
		}
		priceGroup.setName(name);
		priceGroup.setRemark(remark);
		final int userId = userService.getCurrentUserId();
		priceGroup.setUpdateUser(userId);
		priceGroupDAO.update(priceGroup);
		List<CommodityPrice> listDB = commodityPriceService.getList(id);
		if(listDB != null) {
			for (CommodityPrice commodityPrice : listDB) {
				// 清理以前的
				commodityPriceService.delete(commodityPrice);
			}
		}
		List<CommodityPrice> list = JSON.parseArray(commodityListJson, CommodityPrice.class);
		if(null != list) {
			for (CommodityPrice commodityPrice : list) {
				commodityPrice.setPriceGroupId(id);
				commodityPriceService.add(commodityPrice);
			}
		}
	}

	@Override
	public PriceGroupResponse get(int id) {
		if(id <= 0) {
			throw new BusinessRuntimeException("ID不能为空");
		}
		List<CommodityPrice> listDB = commodityPriceService.getList(id);
		PriceGroupResponse result = null;
		PriceGroup priceGroup =new PriceGroup();
		priceGroup.setId(id);
		 priceGroup = priceGroupDAO.findOne(priceGroup);
		if(null == priceGroup) {
			throw new BusinessRuntimeException("根据ID找不到对应的记录");
		}
		List<CommodityPriceResponse> commodityPriceList = null;
		if(null != listDB) {
			commodityPriceList = new ArrayList<>();
			for (CommodityPrice commodityPrice : listDB) {
				Commodity commodity = commodityService.get(commodityPrice.getCommodityId());
				if(null != commodity) {
					CommodityPriceResponse rsp = new CommodityPriceResponse();
					rsp.setPrice(commodityPrice.getPrice());
					rsp.setPriceGroupId(id);
					rsp.setName(commodity.getName());
					rsp.setCommodityId(commodity.getId());
					rsp.setUnit(commodity.getUnit());
					commodityPriceList.add(rsp);
				}
			}
		}
		result = new PriceGroupResponse();
		result.setId(id);
		result.setName(priceGroup.getName());
		result.setNumber(priceGroup.getNumber());
		result.setRemark(priceGroup.getRemark());
		result.setCommodityPriceList(commodityPriceList);
		return result;
	}

	@Override
	public void delete(int id) {
		if(id <= 0) {
			throw new BusinessRuntimeException("ID不能为空");
		}
		//逻辑删除
		PriceGroup priceGroup =new PriceGroup();
		priceGroup.setId(id);
		priceGroup = priceGroupDAO.findOne(priceGroup);
		if(priceGroup == null) {
			throw new BusinessRuntimeException("商品定价列表不能为空");
		}
		priceGroup.setState(State.INVALID.getState());
		priceGroupDAO.update(priceGroup);
		List<CommodityPrice> listDB = commodityPriceService.getList(id);
		if(listDB != null) {
			for (CommodityPrice commodityPrice : listDB) {
				// 清理以前的
				commodityPriceService.delete(commodityPrice);
			}
		}
	}

	@Override
	public Page<PriceGroup> getPage(PriceGroupPageRequst priceGroupPageRequst) {
		if(null == priceGroupPageRequst) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int pageNumber = priceGroupPageRequst.getPageNumber();
		final int pageSize  = priceGroupPageRequst.getPageSize();
		priceGroupPageRequst.setSkip((pageNumber -1) * pageSize);
		final int totalCount = priceGroupDAO.count(priceGroupPageRequst);
		List<PriceGroup>  list = priceGroupDAO.findList(priceGroupPageRequst);
		Page<PriceGroup> result = new Page<PriceGroup>();
	    result.setList(list);
	    result.setTotalCount(totalCount);
	    result.setPageNumber(pageNumber);
	    result.setPageSize(pageSize);
		return result;
	}
	
	@Override
	public List<PriceGroup> getList(PriceGroupPageRequst priceGroupPageRequst) {
		if(null == priceGroupPageRequst) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int pageNumber = 1;
		final int pageSize  = Integer.MAX_VALUE;
		priceGroupPageRequst.setSkip((pageNumber -1) * pageSize);
		List<PriceGroup>  list = priceGroupDAO.findList(priceGroupPageRequst);
		return list;
	}

	@Override
	public PriceGroup getPriceGroup(int id) {
		PriceGroup priceGroup =new PriceGroup();
		priceGroup.setId(id);
		return priceGroupDAO.findOne(priceGroup);
	}

}
