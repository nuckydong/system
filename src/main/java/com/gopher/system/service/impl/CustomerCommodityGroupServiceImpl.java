package com.gopher.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.CustomerCommodityGroupDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.CustomerCommodityGroup;
import com.gopher.system.model.GroupCommodity;
import com.gopher.system.model.vo.request.CustomerCommodityGroupRequset;
import com.gopher.system.model.vo.response.CustomerCommodityGroupResponse;
import com.gopher.system.service.CustomerCommodityGroupService;
import com.gopher.system.service.GroupCommodityService;
import com.gopher.system.service.UserService;
@Service
public class CustomerCommodityGroupServiceImpl implements CustomerCommodityGroupService{
	@Autowired
    private CustomerCommodityGroupDAO customerCommodityGroupDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupCommodityService groupCommodityService;
	
	@Override
	@Transactional
	public void add(CustomerCommodityGroupRequset customerCommodityGroupRequset) {
		if(null == customerCommodityGroupRequset ) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int currentLoginUser = userService.getCurrentUserId();
		final String name = customerCommodityGroupRequset.getName();
		final int sort = customerCommodityGroupRequset.getSort();
		final int customerId = customerCommodityGroupRequset.getCustomerId();
		final String commodityIds = customerCommodityGroupRequset.getCommodityIds();
		final String remark = customerCommodityGroupRequset.getRemark();
		if(!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("请输入名称");
		}
		if(sort <=0 ) {
			throw new BusinessRuntimeException("请设置优先级");
		}
		if(customerId <=0 ) {
			throw new BusinessRuntimeException("请选择对应的客户");
		}
		CustomerCommodityGroup  customerCommodityGroup = new CustomerCommodityGroup();
		customerCommodityGroup.setName(name);
		customerCommodityGroup.setSort(sort);
		customerCommodityGroup.setRemark(remark);
		customerCommodityGroup.setCustomerId(customerId);
		customerCommodityGroup.setCreateUser(currentLoginUser);
		customerCommodityGroup.setUpdateUser(currentLoginUser);
		customerCommodityGroupDAO.add(customerCommodityGroup);
		final int groupId = customerCommodityGroup.getId();
		List<Integer> commodityList = this.getCommodityIds(commodityIds);
		if(null != commodityList) {
			for (Integer commodityId : commodityList) {
				GroupCommodity groupCommodity = new GroupCommodity();
				groupCommodity.setGroupId(groupId);
				groupCommodity.setCommodityId(commodityId);
				groupCommodityService.insert(groupCommodity);
			}
		}
		
	}
    private List<Integer> getCommodityIds(String commodityIds){
    	 List<Integer> result = null;
    	if(StringUtils.hasText(commodityIds)) {
    		String[] idstrs = commodityIds.split(",");
    		result = new ArrayList<>();
    		for (String idstr : idstrs) {
    			result.add(Integer.valueOf(idstr));
			}
    	}
    	return result;
    }
	@Override
	public void update(CustomerCommodityGroupRequset customerCommodityGroupRequset) {

		if(null == customerCommodityGroupRequset ) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int groupId = customerCommodityGroupRequset.getId();
		final int currentLoginUser = userService.getCurrentUserId();
		final String name = customerCommodityGroupRequset.getName();
		final int sort = customerCommodityGroupRequset.getSort();
		final int customerId = customerCommodityGroupRequset.getCustomerId();
		final String commodityIds = customerCommodityGroupRequset.getCommodityIds();
		final String remark = customerCommodityGroupRequset.getRemark();
		if(groupId <=0 ) {
			throw new BusinessRuntimeException("ID不能为空");
		}
		if(!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("请输入名称");
		}
		if(sort <=0 ) {
			throw new BusinessRuntimeException("请设置优先级");
		}
		if(customerId <=0 ) {
			throw new BusinessRuntimeException("请选择对应的客户");
		}
		CustomerCommodityGroup  customerCommodityGroup = new CustomerCommodityGroup();
		customerCommodityGroup.setName(name);
		customerCommodityGroup.setSort(sort);
		customerCommodityGroup.setRemark(remark);
		customerCommodityGroup.setCustomerId(customerId);
		customerCommodityGroup.setUpdateUser(currentLoginUser);
		customerCommodityGroupDAO.update(customerCommodityGroup);
		// 清除之前的 重新添加
		groupCommodityService.deleteByGroup(groupId);
		List<Integer> commodityList = this.getCommodityIds(commodityIds);
		if(null != commodityList) {
			for (Integer commodityId : commodityList) {
				GroupCommodity groupCommodity = new GroupCommodity();
				groupCommodity.setGroupId(groupId);
				groupCommodity.setCommodityId(commodityId);
				groupCommodityService.insert(groupCommodity);
			}
		}
		
	
	}

	@Override
	public CustomerCommodityGroupResponse get(int id) {
		return null;
	}
    
	@Override
	@Transactional
	public void delete(int id) {
		customerCommodityGroupDAO.delete(id);
		groupCommodityService.deleteByGroup(id);
	}

	@Override
	public List<CustomerCommodityGroupResponse> getList(int customerId) {
		CustomerCommodityGroup customerCommodityGroup = new CustomerCommodityGroup();
		customerCommodityGroup.setCustomerId(customerId);
		List<CustomerCommodityGroup>  list = customerCommodityGroupDAO.getList(customerCommodityGroup);
		if(null != list) {
			//TODO 
		}
		return null;
	}

}
