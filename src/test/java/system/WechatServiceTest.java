package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gopher.system.service.WechatService;

public class WechatServiceTest extends BaseTest {
	@Autowired
	private WechatService wechatService;
	@Test
	public void getToken(){
		wechatService.getToken();
	}

}
