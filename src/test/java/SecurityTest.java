import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import my.webframework.security.config.AppConfig;
import my.webframework.security.domain.Employee;
import my.webframework.security.domain.Organization;
import my.webframework.security.domain.User;
import my.webframework.security.reponsitory.IEmployeeReponsitory;
import my.webframework.security.reponsitory.IMenuItemReponsitory;
import my.webframework.security.reponsitory.IOrganizationReponsitory;
import my.webframework.security.reponsitory.IRoleReponsitory;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class SecurityTest {
	private Logger LOGGER = Logger.getLogger(SecurityTest.class);
	@Autowired
	private IOrganizationReponsitory or;
	@Autowired
	private IEmployeeReponsitory er;
	@Autowired
	private IRoleReponsitory rr;
	@Autowired
	private IMenuItemReponsitory mr;
	@Autowired
	private JpaTransactionManager txM;
	
	@Test
	@Rollback(false)
	public void printTest() {
		LOGGER.debug("+++++++++++++++++++");
	}

	@Test
	public void testOrganizationReponsitory() {
		Organization oc = new Organization();
		oc.setAddress("新疆克拉玛依红有软件");
		oc.setConsulation_phone("0990-6238776");
		oc.setFull_name("研究院");
		oc.setLabel("研究院");
		oc.setOrder_number(1d);
		oc.setProperty("暂无");
		oc.setPub_phone("0991-1111111");
		oc.setWindow_phone("010-1111111");
		oc.setWww("http://hongyousoftsware.com.cn/yanjiuyuan/");
		oc.setXzqh("新疆");
		oc.setZip_code("834000");

		Organization o = new Organization();
		o.setAddress("新疆克拉玛依红有软件");
		o.setConsulation_phone("0990-6238776");
		o.setFull_name("红有软件");
		o.setLabel("红有软件");
		o.setOrder_number(1d);
		o.setProperty("暂无");
		o.setPub_phone("0991-6238776");
		o.setWindow_phone("010-6238776");
		o.setWww("www.hongyousoftsware.com.cn");
		o.setXzqh("新疆");
		o.setZip_code("834000");
		/* ###################################### ----> 增加 */
		o = or.saveAndFlush(o);
		Assert.assertNotNull(o.getId());
		LOGGER.debug("保存成功 >>> " + JSONObject.toJSON(o));
		oc.setPid(o.getId());
		Set<Organization> os = new HashSet<Organization>();
		os.add(oc);
		o.setChildren(os);
		o = or.saveAndFlush(o);
		Integer id = o.getId();
		o = null;
		/* ###################################### ----> 查找 */
		o = or.findOne(id);
		Assert.assertArrayEquals(new int[] { 1 }, new int[] { o.getChildren()
				.size() });
		LOGGER.debug("保存孩子成功 >>> " + JSONObject.toJSON(o));
		/* ###################################### ----> 修改 */
		o.setXzqh("中国");
		o = or.saveAndFlush(o);
		o = null;
		o = or.findOne(id);
		Assert.assertArrayEquals(new String[] { "中国" },
				new String[] { o.getXzqh() });
		LOGGER.debug("修改后查找得 >>> " + JSONObject.toJSON(o));
		/* ###################################### ----> 删除 */
		or.delete(id);
		o = or.findOne(id);
		Assert.assertNull(o);
	}

	@Test
	public void testEmployeeReponsitory() {
		Employee e = new Employee();
		e.setBirthday(new Date());
		e.setArea_id(1);;
		e.setEmail("chenclqqrj@163.com");
		e.setHandphone("1360000000");
		e.setJob("程序员");
		e.setJob_date(new Date());
		e.setName("张无忌");
		e.setNation("汉");
		e.setOrder_number(1d);
		e.setPolitical("研究院");
		e.setPub_phone("0990-1234576");
		e.setSchool("光明顶");
		e.setSex("男");
		User u = new User();
		u.setUsername("guili");
		u.setPassword("111111");
//		u.setIslock("F");
		e.setUser(u);
		er.saveAndFlush(e);
		junit.framework.Assert.assertNotNull(e.getId());
		Integer id = e.getId();
		e = null;
		e = er.findOne(id);
		junit.framework.Assert.assertNotNull(e);
		LOGGER.debug(">>>>Employee::" + JSON.toJSONString(e));
		//===============================================
		e.setJob("秀才");
		er.saveAndFlush(e);
		e = null;
		e = er.findOne(id);
		junit.framework.Assert.assertEquals("秀才", e.getJob());
		LOGGER.debug(">>>>Employee::" + JSON.toJSONString(e));
		//===============================================
		Organization o = new Organization();
		o.setAddress("新疆克拉玛依红有软件");
		o.setConsulation_phone("0990-6238776");
		o.setFull_name("红有软件");
		o.setLabel("红有软件");
		o.setOrder_number(1d);
		o.setProperty("暂无");
		o.setPub_phone("0991-6238776");
		o.setWindow_phone("010-6238776");
		o.setWww("www.hongyousoftsware.com.cn");
		o.setXzqh("新疆");
		o.setZip_code("834000");
		o = or.saveAndFlush(o);
		junit.framework.Assert.assertNotNull(o.getId());
		e.setOrganization_id(o.getId());
		e = er.saveAndFlush(e);
		e = er.findOne(id);
		//===============================================
		//===============================================
		
	}
}
