package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.GroupCommodityDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.GroupCommodity;
import com.gopher.system.service.GroupCommodityService;

@Service
public class GroupCommodityServiceImpl implements GroupCommodityService {
	@Autowired
	private GroupCommodityDAO groupCommodityDAO;

	@Override
	public void deleteByGroup(int groupId) {
		groupCommodityDAO.deleteByGroup(groupId);
	}

	@Override
	public void insert(GroupCommodity groupCommodity) {
		List<GroupCommodity> list = groupCommodityDAO.find(groupCommodity);
		if(null != list && !list.isEmpty()) {
			throw new BusinessRuntimeException("商品已经添加，请检查后再添加");
		}
		groupCommodityDAO.insert(groupCommodity);
	}

	@Override
	public List<GroupCommodity> getListByGroup(int groupId) {
		return groupCommodityDAO.getListByGroup(groupId);
	}

	@Override
	public void update(GroupCommodity groupCommodity) {
		final int id = groupCommodity.getId();
		if(id <= 0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		GroupCommodity DBObj = groupCommodityDAO.findOne(id);
		if(DBObj == null ) {
			throw new BusinessRuntimeException("根据ID找不到记录");
		}
		DBObj.setPrice(groupCommodity.getPrice());
		groupCommodityDAO.update(DBObj);
	}

	@Override
	public void delete(int id) {
		groupCommodityDAO.delete(id);
	}

}
