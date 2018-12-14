package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.GroupCommodity;

public interface GroupCommodityDAO {
	
	void deleteByGroup(int groupId);
	
	void insert (GroupCommodity groupCommodity);
	
	void update (GroupCommodity groupCommodity);
	
	List<GroupCommodity> find(GroupCommodity groupCommodity);
	
	void delete(int id);
	
	List<GroupCommodity> getListByGroup(int groupId);

}
