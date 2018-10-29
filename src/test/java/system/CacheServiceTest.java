package system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gopher.system.service.CacheService;

public class CacheServiceTest extends BaseTest{
	@Autowired
	private CacheService cacheService;
	
	@Test
	public void set(){
		cacheService.set("AUTH", 123, 60);
	}
	
	@Test
	public void get(){
		Object obj = cacheService.get("AUTH");
		System.out.println(obj);
	}

}
