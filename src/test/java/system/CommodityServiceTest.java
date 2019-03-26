package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gopher.system.model.Commodity;
import com.gopher.system.model.vo.request.CommodityListRequst;
import com.gopher.system.model.vo.request.CommodityPageRequst;
import com.gopher.system.service.CommodityService;
import com.gopher.system.util.ThreadLocalUtils;

public class CommodityServiceTest extends BaseTest{
	@Autowired
	private CommodityService commodityService;
	
	@Test
	public void insert() {
		Commodity cdt = new Commodity();
		cdt.setCommodityTypeId(1);
		cdt.setName("茄子");
		cdt.setUnit("KG");
		cdt.setPrice(100);
		commodityService.insert(cdt);
	}
	
	@Test
	public void getList() {
		CommodityListRequst request  = new CommodityListRequst();
		request.setName("土");
		System.out.println(JSON.toJSONString(commodityService.getCommodityList(request)));
	}
	@Test
	public void getListNotInGroup() {
		ThreadLocalUtils.setObject(ThreadLocalUtils.USER_KEY, 11);
		System.out.println(JSON.toJSONString(commodityService.getListNotInGroup(new Commodity())));
	}
	@Test
	public void getPage() {
		CommodityPageRequst request  = new CommodityPageRequst();
		request.setName("");
		System.out.println(JSON.toJSONString(commodityService.getCommodityPage(request)));
	}

}
