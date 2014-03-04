import my.webframework.security.config.AppConfig;
import my.webframework.security.reponsitory.ISystemReponsitory;
import my.webframework.security.service.springmvc.MvcConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfig.class, MvcConfig.class })
public class MyTest {
	
	@Autowired
	private ISystemReponsitory	systemReponsitory;
	
	@Test
	public void test() {
		System.out.println("++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++++++" + systemReponsitory.getOne(1));
	}
	
}
