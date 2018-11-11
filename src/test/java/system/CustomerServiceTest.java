package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gopher.system.model.vo.request.CustomerPageRequst;
import com.gopher.system.service.CustomerService;

public class CustomerServiceTest extends BaseTest{
	@Autowired
	private CustomerService customerService;
	@Test
	public void getPage() {
		CustomerPageRequst customerPageRequst = new CustomerPageRequst();
		System.out.println(customerService.getPage(customerPageRequst));
	}
	

}
