package system;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gopher.system.model.OrderCommodity;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.service.OrderService;
import com.gopher.system.util.ThreadLocalUtils;

public class OrderServiceTest extends BaseTest{
	@Autowired
	private OrderService orderService;
	@Test
	public void insert() {
		ThreadLocalUtils.setObject(ThreadLocalUtils.USER_KEY, 1);
		OrderRequst orderRequst = new OrderRequst();
		List<OrderCommodity> comodityList = new ArrayList<>();
		OrderCommodity oc1 = new OrderCommodity();
		oc1.setAmount(10);
		oc1.setPrice(100);
		oc1.setCommodityId(2);
		oc1.setUnit("KG");
		comodityList.add(oc1);
		
		OrderCommodity oc2 = new OrderCommodity();
		oc2.setAmount(5);
		oc2.setPrice(100);
		oc2.setCommodityId(3);
		oc2.setUnit("KG");
		comodityList.add(oc2);
		System.out.println(JSON.toJSONString(comodityList));
		orderRequst.setCommodityListJson(JSON.toJSONString(comodityList));
		orderService.insert(orderRequst);
	}
	@Test
	public void getOrderList() {
		ThreadLocalUtils.setObject(ThreadLocalUtils.USER_KEY, 1);
		System.out.println(JSON.toJSONString(orderService.getOrderListByCurrentUser()));
	}
	
	@Test
	public void getOrderDetail() {
		ThreadLocalUtils.setObject(ThreadLocalUtils.USER_KEY, 1);
		System.out.println(JSON.toJSONString(orderService.getOrderDetail(10)));
	}

}
