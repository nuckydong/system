package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gopher.system.dao.mysql.CommodityTypeDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.CommodityType;
import com.gopher.system.service.CommodityTypeService;
import com.gopher.system.service.UserService;

@Service
public class CommodityTypeServiceImpl implements CommodityTypeService {
	@Autowired
	private CommodityTypeDAO commodityTypeDAO;
	@Autowired
	private UserService userService;

	@Override
	public CommodityType getCommodityTypeById(int id) {
		if (id <= 0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		return commodityTypeDAO.findOne(id);
	}

	@Override
	public List<CommodityType> getCommodityTypeList() {

		return commodityTypeDAO.findList();
	}

	@Override
	public void update(CommodityType commodityType) {
		if (commodityType == null) {
			throw new BusinessRuntimeException("参数不能为空");
		}final int userId = userService.getCurrentUserId();
		commodityType.setUpdateUser(userId);
		commodityTypeDAO.update(commodityType);
	}

	@Override
	public Integer insert(CommodityType commodityType) {
		if (commodityType == null) {
			throw new BusinessRuntimeException("参数不能为空");
		}
		if(!StringUtils.hasText(commodityType.getName())){
			throw new BusinessRuntimeException("名称不能为空");
		}
		final int userId = userService.getCurrentUserId();
		commodityType.setCreateUser(userId);
		commodityType.setUpdateUser(userId);
		return commodityTypeDAO.insert(commodityType);
	}

}
