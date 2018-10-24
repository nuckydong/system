package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gopher.system.model.vo.request.RegisterRequst;
import com.gopher.system.service.AuthService;

public class AuthServiceTest extends BaseTest{
	@Autowired
	private AuthService authService;
	@Test
	public void registerTest(){
		RegisterRequst param = new RegisterRequst();
		param.setAccount("TEST2");
		param.setCompany("TEST");
		param.setPassword("TEST");
		param.setPhone("TEST");
		authService.register(param);
	}

}
