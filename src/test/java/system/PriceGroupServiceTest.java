package system;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gopher.system.model.CommodityPrice;
import com.gopher.system.model.vo.request.PriceCommodityPageRequest;
import com.gopher.system.model.vo.request.PriceGroupPageRequst;
import com.gopher.system.model.vo.request.PriceGroupRequest;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.service.CommodityPriceService;
import com.gopher.system.service.PriceGroupService;
import com.gopher.system.util.Page;
import com.gopher.system.util.ThreadLocalUtils;

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
	
	@Test
	public void getCommodityPage() {
		PriceCommodityPageRequest priceCommodityPageRequest= new PriceCommodityPageRequest();
		priceCommodityPageRequest.setId(5);
		priceCommodityPageRequest.setCommodityName("");
		Page<CommodityResponse>  page = priveGroupService.getCommodityPage(priceCommodityPageRequest);
		System.out.println(JSON.toJSONString(page));
	}
	@Autowired
	private CommodityPriceService commodityPriceService;
	
	@Test
	public void addCommodity() {
		ThreadLocalUtils.setObject(ThreadLocalUtils.USER_KEY, 1);
		CommodityPrice commodityPrice = new CommodityPrice();
		commodityPrice.setCommodityId(8);
		commodityPrice.setPriceGroupId(5);
		commodityPrice.setPrice(200);
		commodityPriceService.add(commodityPrice);
	}
	
	
	@Test
	public void deleteCommodity() {
		ThreadLocalUtils.setObject(ThreadLocalUtils.USER_KEY, 1);
		CommodityPrice commodityPrice = new CommodityPrice();
		commodityPrice.setId(11);
		commodityPriceService.delete(commodityPrice);
	}
	

}
