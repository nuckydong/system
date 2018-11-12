package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.GroupCommodityDAO;
import com.gopher.system.model.GroupCommodity;
import com.gopher.system.service.GroupCommodityService;
@Service
public class GroupCommodityServiceImpl implements GroupCommodityService{
	@Autowired
    private GroupCommodityDAO groupCommodityDAO;
	@Override
	public void deleteByGroup(int groupId) {
		groupCommodityDAO.deleteByGroup(groupId);
	}

	@Override
	public void insert(GroupCommodity groupCommodity) {
		groupCommodityDAO.insert(groupCommodity);
	}

	@Override
	public List<GroupCommodity> getListByGroup(int groupId) {
		return groupCommodityDAO.getListByGroup(groupId);
	}

}
