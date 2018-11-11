package system;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gopher.system.model.CommodityPrice;
import com.gopher.system.model.vo.request.PriceGroupPageRequst;
import com.gopher.system.model.vo.request.PriceGroupRequest;
import com.gopher.system.service.PriceGroupService;

public class PriceGroupServiceTest extends BaseTest{
	@Autowired
	private PriceGroupService priveGroupService;
	
	@Test
	public void add() {
		PriceGroupRequest priceGroupRequest = new PriceGroupRequest();
		priceGroupRequest.setName("TEST");
		priceGroupRequest.setNumber("123");
		List<CommodityPrice> list  = new ArrayList<>();
		CommodityPrice cp = new CommodityPrice();
		cp.setCommodityId(1);
		cp.setPrice(1000);
		list.add(cp);
		String commodityPriceListJson = JSON.toJSONString(list);
		priceGroupRequest.setCommodityPriceListJson(commodityPriceListJson);
		
		System.out.println(JSON.toJSONString(priceGroupRequest));
		priveGroupService.add(priceGroupRequest);
	}
	
	@Test
	public void get() {
		System.out.println(JSON.toJSONString(priveGroupService.get(2)));
	}
	@Test
	public void getPgae() {
		PriceGroupPageRequst priceGroupPageRequst = new PriceGroupPageRequst();
		priceGroupPageRequst.setPageSize(10);
		priceGroupPageRequst.setPageNumber(1);
		System.out.println(JSON.toJSONString(priveGroupService.getPage(priceGroupPageRequst)));
	}

	@Test
	public void getList() {
		PriceGroupPageRequst priceGroupPageRequst = new PriceGroupPageRequst();
		System.out.println(JSON.toJSONString(priveGroupService.getList(priceGroupPageRequst)));
	}
}
