package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gopher.system.service.WechatService;

public class WechatServiceTest extends TestBase{
	@Autowired
	private WechatService wechatService;
	@Test
	public void login(){
		wechatService.wechatLogin();
	}

}
