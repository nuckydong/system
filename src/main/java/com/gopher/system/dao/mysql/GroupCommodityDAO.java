package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.GroupCommodity;

public interface GroupCommodityDAO {
	
	void deleteByGroup(int groupId);
	
	void insert (GroupCommodity groupCommodity);
	
	List<GroupCommodity> getListByGroup(int groupId);

}
