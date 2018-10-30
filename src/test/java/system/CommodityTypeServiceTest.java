package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gopher.system.service.CommodityTypeService;

public class CommodityTypeServiceTest extends BaseTest{
	@Autowired
	private CommodityTypeService commodityTypeService;
	@Test
	public void getList(){
		System.out.println(JSON.toJSONString(commodityTypeService.getCommodityTypeList()));
	}

}
