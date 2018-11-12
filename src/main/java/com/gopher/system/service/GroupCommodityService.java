package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.GroupCommodity;

public interface GroupCommodityService {
	
	void deleteByGroup(int groupId);
	
	void insert (GroupCommodity groupCommodity);
	
	List<GroupCommodity> getListByGroup(int groupId);
}
