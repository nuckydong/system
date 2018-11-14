package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gopher.system.model.vo.request.UserPageRequst;
import com.gopher.system.service.UserService;
import com.gopher.system.util.ThreadLocalUtils;

public class UserServieTest extends BaseTest {
	@Autowired
	private UserService userService;

	@Test
	public void getPage() {
		ThreadLocalUtils.setObject(ThreadLocalUtils.USER_KEY,3);
		UserPageRequst upe = new UserPageRequst();
		upe.setSearch("1");
		upe.setCustomerId(4);
		System.out.println(JSON.toJSONString(userService.getPage(upe)));
	}

}
