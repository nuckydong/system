package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gopher.system.model.vo.request.CustomerCommodityGroupRequset;
import com.gopher.system.service.CustomerCommodityGroupService;
import com.gopher.system.util.ThreadLocalUtils;

public class CustomerCommodityGroupServiceTest extends BaseTest{
	@Autowired
	private CustomerCommodityGroupService customerCommodityGroupService;
	@Test
	public void add() {
		CustomerCommodityGroupRequset customerCommodityGroupRequset = new CustomerCommodityGroupRequset();
		ThreadLocalUtils.setObject(ThreadLocalUtils.USER_KEY, 3);
		customerCommodityGroupRequset.setCommodityIds("4,5,6");
		customerCommodityGroupRequset.setCustomerId(4);
		customerCommodityGroupRequset.setName("TEST");
		customerCommodityGroupRequset.setRemark("4TEST");
		customerCommodityGroupRequset.setSort(1);
		customerCommodityGroupService.add(customerCommodityGroupRequset);
	}
	
	@Test
	public void getList() {
		System.out.println(JSON.toJSONString(customerCommodityGroupService.getList(4)));
	}
	
	
	@Test
	public void delete() {
		customerCommodityGroupService.delete(1);
	}


}
