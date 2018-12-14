package com.gopher.system.service;

import java.util.List;

import com.gopher.system.model.GroupCommodity;

public interface GroupCommodityService {
	
	void deleteByGroup(int groupId);
	
	void insert (GroupCommodity groupCommodity);
	
	void update (GroupCommodity groupCommodity);
	
	void delete (int id);
	
	List<GroupCommodity> getListByGroup(int groupId);

}
