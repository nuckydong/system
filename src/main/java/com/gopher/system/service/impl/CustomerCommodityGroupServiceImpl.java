package com.gopher.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.CustomerCommodityGroupDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Commodity;
import com.gopher.system.model.CommodityType;
import com.gopher.system.model.CustomerCommodityGroup;
import com.gopher.system.model.CustomerUser;
import com.gopher.system.model.GroupCommodity;
import com.gopher.system.model.vo.request.CustomerCommodityGroupRequset;
import com.gopher.system.model.vo.request.GroupCommodityPageRequst;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.model.vo.response.CustomerCommodityGroupResponse;
import com.gopher.system.service.CommodityService;
import com.gopher.system.service.CommodityTypeService;
import com.gopher.system.service.CustomerCommodityGroupService;
import com.gopher.system.service.CustomerUserService;
import com.gopher.system.service.GroupCommodityService;
import com.gopher.system.service.UserService;
import com.gopher.system.util.Page;
@Service
public class CustomerCommodityGroupServiceImpl implements CustomerCommodityGroupService{
	@Autowired
    private CustomerCommodityGroupDAO customerCommodityGroupDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerUserService customerUserService;
	@Autowired
	private GroupCommodityService groupCommodityService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CommodityTypeService commodityTypeService;
	
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
		customerCommodityGroupDAO.insert(customerCommodityGroup);
//		final int groupId = customerCommodityGroup.getId();
//		List<Integer> commodityList = this.getCommodityIds(commodityIds);
//		if(null != commodityList) {
//			for (Integer commodityId : commodityList) {
//				GroupCommodity groupCommodity = new GroupCommodity();
//				groupCommodity.setGroupId(groupId);
//				groupCommodity.setCommodityId(commodityId);
//				groupCommodityService.insert(groupCommodity);
//			}
//		}
		
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
//		if(StringUtils.hasText(commodityIds)) {
//			groupCommodityService.deleteByGroup(groupId);
//			List<Integer> commodityList = this.getCommodityIds(commodityIds);
//			if(null != commodityList) {
//				for (Integer commodityId : commodityList) {
//					GroupCommodity groupCommodity = new GroupCommodity();
//					groupCommodity.setGroupId(groupId);
//					groupCommodity.setCommodityId(commodityId);
//					groupCommodityService.insert(groupCommodity);
//				}
//			}
//			
//		}
	}
    
	@Override
	public CustomerCommodityGroupResponse get(int id) {
		CustomerCommodityGroup  customerCommodityGroup = customerCommodityGroupDAO.findOne(id);
		CustomerCommodityGroupResponse result = null;
		if(null != customerCommodityGroup) {
			result = new CustomerCommodityGroupResponse();
			final int customerId = customerCommodityGroup.getCustomerId();
			result.setId(id);
			result.setCustomerId(customerId);
			result.setName(customerCommodityGroup.getName());
			result.setRemark(customerCommodityGroup.getRemark());
			result.setSort(customerCommodityGroup.getSort());
			List<GroupCommodity> list = groupCommodityService.getListByGroup(id);
			List<CommodityResponse> commodityList = null;
			if(null != list && !list.isEmpty()) {
				commodityList = new ArrayList<>(list.size());
				for (GroupCommodity groupCommodity : list) {
					CommodityResponse commodity = commodityService.getCommodity(groupCommodity.getCommodityId());
					if(commodity != null) {
						commodity.setCustomerCommodityGroupId(id);
						commodity.setCustomerCommodityGroupName(customerCommodityGroup.getName());
						commodityList.add(commodity);
					}
				}
			}
			result.setCommodityList(commodityList);
		}
		
		return result;
	}
    
	@Override
	@Transactional
	public void delete(int id) {
		customerCommodityGroupDAO.delete(id);
		groupCommodityService.deleteByGroup(id);
	}
	@Override
	public List<CustomerCommodityGroupResponse> getList(int customerId) {
		CustomerCommodityGroup query = new CustomerCommodityGroup();
		query.setCustomerId(customerId);
		List<CustomerCommodityGroup>  list = customerCommodityGroupDAO.findList(query);
		List<CustomerCommodityGroupResponse> reuslt = null;
		if(null != list) {
			reuslt = new ArrayList<>(list.size());
			for (CustomerCommodityGroup customerCommodityGroup : list) {
				reuslt.add(this.get(customerCommodityGroup.getId()));
			}
		}
		return reuslt;
	}
	
	@Override
	public List<CustomerCommodityGroupResponse> getList() {
		final int user_id = userService.getCurrentUserId();
		CustomerUser cu = customerUserService.get(user_id);
		if(null != cu) {
			return this.getList(cu.getCustomerId());
		}
		return null;
	}
	

	@Override
	public Page<CommodityResponse> getCommodityPage(GroupCommodityPageRequst groupCommodityPageRequst) {
		final int groupId = groupCommodityPageRequst.getId();
		CustomerCommodityGroup customerCommodityGroup = customerCommodityGroupDAO.findOne(groupId);
		if(null == customerCommodityGroup) {
			throw new BusinessRuntimeException("无效的分组ID");
		}
		final String groupName = customerCommodityGroup.getName();
		final int pageSize = groupCommodityPageRequst.getPageSize();
		final int pageNumber = groupCommodityPageRequst.getPageNumber();
		
		Page<CommodityResponse> page = new Page<CommodityResponse>();
		List<Commodity> commodityList = customerCommodityGroupDAO.getPage(groupCommodityPageRequst);
		final int totalCount = customerCommodityGroupDAO.getCount(groupCommodityPageRequst);
		if(null != commodityList) {
			List<CommodityResponse> list = new ArrayList<>();
			for (Commodity commodity : commodityList) {
				CommodityResponse cr = new CommodityResponse();
				BeanUtils.copyProperties(commodity, cr);
				CommodityType commodityType = commodityTypeService.getCommodityTypeById(commodity.getCommodityTypeId());
				if (commodityType != null) {
					cr.setCommodityTypeName(commodityType.getName());
				}
				cr.setCustomerCommodityGroupId(groupId);
				cr.setCustomerCommodityGroupName(groupName);
				list.add(cr);
			}
			page.setList(list);
		}
		page.setTotalCount(totalCount);
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		return page;
	}


}
