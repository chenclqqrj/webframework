import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class WebHttpInvokeTest {
	private static final Logger LOGGER = Logger.getLogger(WebHttpInvokeTest.class);
	private HttpClientBuilder clientBuilder;
	private CloseableHttpClient httpClient;
	private HttpHost host;
	private HttpPost hostRequest;

	@Before
	public void before() {
		clientBuilder = HttpClientBuilder.create();
		httpClient = clientBuilder.build();
		host = new HttpHost("127.0.0.1", 8080);
	}

//	@Test
	public void testOrganizationTreeAdd() throws Exception {
		String o = "{\"id\": 1, \"label\": \"油田公司AA\", \"full_name\": \"新疆油田公司AA\", \"xzqh\": \"中国新疆\", \"address\": \"新疆克拉玛依\", \"order_number\": 1.0, \"zip_code\": \"832000\", \"pub_phone\": \"0990-2356894\", \"consulation_phone\": \"0990-1111111\", \"window_phone\": \"0990-2222222\", \"www\": \"http://10.72.5.8\", \"property\": \"无\", \"pid\": null, \"children\": [{\"id\": 2, \"label\": \"油田公司\", \"full_name\": \"新疆油田公司\", \"xzqh\": \"中国新疆\", \"address\": \"新疆克拉玛依\", \"order_number\": 1.0, \"zip_code\": \"832000\", \"pub_phone\": \"0990-2356894\", \"consulation_phone\": \"0990-1111111\", \"window_phone\": \"0990-2222222\", \"www\": \"http://10.72.5.8\", \"property\": \"无\", \"pid\": 1, \"children\": [{\"id\": 3, \"label\": \"油田公司\", \"full_name\": \"新疆油田公司\", \"xzqh\": \"中国新疆\", \"address\": \"新疆克拉玛依\", \"order_number\": 1.0, \"zip_code\": \"832000\", \"pub_phone\": \"0990-2356894\", \"consulation_phone\": \"0990-1111111\", \"window_phone\": \"0990-2222222\", \"www\": \"http://10.72.5.8\", \"property\": \"无\", \"pid\": 2, \"children\": [], \"employees\": null }, {\"label\": \"红有软件\", \"full_name\": \"红有软件\", \"xzqh\": \"新疆\", \"address\": \"新疆克拉玛依\", \"order_number\": 1.0, \"zip_code\": \"832000\", \"pub_phone\": \"0990-2356894\", \"consulation_phone\": \"0990-1111111\", \"window_phone\": \"0990-2222222\", \"www\": \"http://10.72.5.8\", \"property\": \"无\", \"pid\": 1, \"children\": [], \"employees\": null }], }], \"employees\": null }";
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/add-tree");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("organization", o));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testOrganizationAddByJson() throws Exception {
		String param = "{\"label\": \"油田公司\", \"full_name\": \"新疆油田公司\", \"xzqh\": \"中国新疆\", \"address\": \"新疆克拉玛依\", \"order_number\": 1.0, \"zip_code\": \"832000\", \"pub_phone\": \"0990-2356894\", \"consulation_phone\": \"0990-1111111\", \"window_phone\": \"0990-2222222\", \"www\": \"http://10.72.5.8\", \"property\": \"无\", \"pid\": null }";
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/add/by-json");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("organization", param));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testOrganizationAdd() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/add");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("label", "油田公司"));
		nvps.add(new BasicNameValuePair("full_name", "新疆油田公司"));
		nvps.add(new BasicNameValuePair("xzqh", "中国新疆"));
		nvps.add(new BasicNameValuePair("address", "新疆克拉玛依"));
		nvps.add(new BasicNameValuePair("order_number", "1"));
		nvps.add(new BasicNameValuePair("zip_code", "832000"));
		nvps.add(new BasicNameValuePair("pub_phone", "0990-2356894"));
		nvps.add(new BasicNameValuePair("consulation_phone", "0990-1111111"));
		nvps.add(new BasicNameValuePair("window_phone", "0990-2222222"));
		nvps.add(new BasicNameValuePair("www", "http://10.72.5.8"));
		nvps.add(new BasicNameValuePair("property", "无"));
		nvps.add(new BasicNameValuePair("pid", "1"));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testOrganizationRemove() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/remove");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "3"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testOrganizationOne() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/one");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "1"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testOrganizationAllRoots() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/all-roots");
		arrist();
	}

//	@Test
	public void testOrganizationAllRootWithChildren() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/all-roots-tree");
		arrist();
	}

//	@Test
	public void testOrganizationChildren() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/children");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("organizationId", "1"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testAddEmployeesToOrganization() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/add-employees");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("organizationId", "1"));
		nvps.add(new BasicNameValuePair("emploeeIds", "1,2"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testRemoveEmployeesToOrganization() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/remove-employees");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("organizationId", "1"));
		nvps.add(new BasicNameValuePair("emploeeIds", "1,2"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testAddRolesToOrganization() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/add-roles");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("organizationId", "1"));
		nvps.add(new BasicNameValuePair("roleIds", "1,3"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testRemoveRolesToOrganization() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/organization/remove-roles");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("organizationId", "1"));
		nvps.add(new BasicNameValuePair("roleIds", "1,3"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testEmployeeAdd() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/add");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("area_id", "1"));
		nvps.add(new BasicNameValuePair("name", "张无忌"));
		nvps.add(new BasicNameValuePair("sex", "男"));
		nvps.add(new BasicNameValuePair("user.username", "guili"));
		nvps.add(new BasicNameValuePair("user.islock", "T"));
		nvps.add(new BasicNameValuePair("user.password", "666666"));
		nvps.add(new BasicNameValuePair("order_number", "1"));
		nvps.add(new BasicNameValuePair("birthday", "1982-12-12"));
		nvps.add(new BasicNameValuePair("pub_phone", "0990-1111111"));
		nvps.add(new BasicNameValuePair("handphone", "13699999999"));
		nvps.add(new BasicNameValuePair("school", "光明顶"));
		nvps.add(new BasicNameValuePair("nation", "汉"));
		nvps.add(new BasicNameValuePair("political", "团员"));
		nvps.add(new BasicNameValuePair("job", "教主"));
		nvps.add(new BasicNameValuePair("job_date", "1990-1-12"));
		nvps.add(new BasicNameValuePair("email", "guili@123.com"));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testEmployeeAddByJson() throws Exception {
		String param = "{\"organization_id\": 1, \"area_id\": 1, \"name\": \"灭绝师太\", \"sex\": \"女\", \"user\": null, \"order_number\": 1.0, \"birthday\": \"1982-12-12\", \"pub_phone\": \"0990-1111111\", \"handphone\": \"13699999999\", \"school\": \"光明顶\", \"nation\": \"汉\", \"political\": \"团员\", \"job\": \"教主\", \"job_date\": \"1990-01-12\", \"email\": \"guili@123.com\", \"photo\": null }";
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/add/by-json");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("employee", param));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testUpdateEmployeePhoto() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/8/set-photo");

        FileBody picture = new FileBody(new File("D:\\tmp\\uploads\\1.jpg"));
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                 .addPart("picture", picture)
                 .build();
		hostRequest.setEntity(reqEntity);
		arrist();
	}

//	@Test
	public void testEmployeeRemove() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/remove");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "7"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testEmployeeOne() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/one");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "5"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testQueryAllEmployees() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/all");
		arrist();
	}

//	@Test
	public void testPageQueryAllEmployees() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/all/page");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", "无"));
		nvps.add(new BasicNameValuePair("pageNum", "1"));
		nvps.add(new BasicNameValuePair("pageSize", "2"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testQueryEmployeesByName() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/by-name");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", "无"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testPageQueryEmployeesByName() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/by-name/page");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", "无"));
		nvps.add(new BasicNameValuePair("pageNum", "1"));
		nvps.add(new BasicNameValuePair("pageSize", "2"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testAddRolesToEmployee() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/add-roles");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("employeeId", "1"));
		nvps.add(new BasicNameValuePair("roleIds", "1,3"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testRemoveRolesFromEmployee() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/employee/remove-roles");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("employeeId", "1"));
		nvps.add(new BasicNameValuePair("roleIds", "1,3"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testRoleAddByJson() throws Exception {
		String param = "{\"pid\": null, \"name\": \"左护法\", \"organization_id\": 2, \"type\": 1 }";
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/add/by-json");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("role", param));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testRoleAdd() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/add");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", "左护法"));
		nvps.add(new BasicNameValuePair("organization_id", "2"));
		nvps.add(new BasicNameValuePair("pid", "10"));
		nvps.add(new BasicNameValuePair("type", "1"));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testRoleRemove() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/remove");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "14"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testRoleTreeAdd() throws Exception {
		String r = "{\"id\": 1, \"pid\": null, \"name\": \"教主\", \"organization_id\": 2, \"type\": 1, \"children\": [{\"id\": 3, \"pid\": 1, \"name\": \"左护法\", \"organization_id\": 2, \"type\": 1, \"children\": [], \"roleMenuItems\": null, \"employees\": null }, {\"pid\": 1, \"name\": \"右护法\", \"organization_id\": 2, \"type\": 1, \"children\": [], \"roleMenuItems\": null, \"employees\": null }], \"roleMenuItems\": null, \"employees\": null }";
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/add-tree");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("role", r));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testRoleOne() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/one");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "3"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testRoleAllRoots() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/all-roots");
		arrist();
	}

//	@Test
	public void testRoleAllRootWithChildren() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/all-roots-tree");
		arrist();
	}

//	@Test
	public void testRoleChildren() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/children");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("roleId", "1"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testAddEmployeesToRole() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/add-employees");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("roleId", "2"));
		nvps.add(new BasicNameValuePair("employeeIds", "3,2"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testRemoveEmployeesFromRole() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/remove-employees");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("roleId", "1"));
		nvps.add(new BasicNameValuePair("employeeIds", "1,2"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testQueryRolesByOrganization() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role/by-organization");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("organizationId", "1"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testMenuItemTreeAdd() throws Exception {
		String mi = "{\"label\": \"九阳神功\", \"xh\": 1, \"module\": \"htt://localhost:8080\", \"sfqy\": true, \"pid\": null, \"type\": \"1\", \"tb\": null, \"sort\": 1.0, \"xtbm\": \"1\", \"description\": \"九阳神功\", \"creator\": \"阳顶天\", \"createdate\": \"2012-01-01\", \"xtgybl\": \"aaa\", \"cs\": \"name\", \"children\": [{\"label\": \"ttt\", \"xh\": null, \"module\": null, \"sfqy\": null, \"type\": null, \"tb\": null, \"sort\": 4.0000000001, \"xtbm\": null, \"description\": null, \"creator\": null, \"createdate\": null, \"xtgybl\": null, \"cs\": null, \"children\": [], \"roleMenuItems\": null }, {\"label\": \"ttt\", \"xh\": 2, \"module\": \"http://www.baidu.com\", \"sfqy\": null, \"type\": null, \"tb\": null, \"sort\": 4.0000000001, \"xtbm\": null, \"description\": null, \"creator\": null, \"createdate\": null, \"xtgybl\": null, \"cs\": null, \"children\": [], \"roleMenuItems\": null }], \"roleMenuItems\": null }";
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/menuItem/add-tree");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("menuItem", mi));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
	@Test
	public void testMenuItemAddByJson() throws Exception {
		String param = "{\"label\": \"九阳神功\", \"xh\": 1, \"module\": \"htt://localhost:8080\", \"sfqy\": true, \"pid\": null, \"type\": \"1\", \"tb\": null, \"sort\": 1.0, \"xtbm\": \"1\", \"description\": \"九阳神功\", \"creator\": \"阳顶天\", \"createdate\": \"2012-01-01\", \"xtgybl\": \"aaa\", \"cs\": \"name\", \"children\": null, \"roleMenuItems\": null }";
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/menuItem/add/by-json");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("menuItem", param));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testMenuItemAdd() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/menuItem/add");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("label", "九阳神功"));
		nvps.add(new BasicNameValuePair("xh", "1"));
		nvps.add(new BasicNameValuePair("module", "htt://localhost:8080"));
		nvps.add(new BasicNameValuePair("sfqy", "true"));
		nvps.add(new BasicNameValuePair("type", "1"));
		nvps.add(new BasicNameValuePair("sort", "1"));
		nvps.add(new BasicNameValuePair("xtbm", "1"));
		nvps.add(new BasicNameValuePair("description", "九阳神功"));
		nvps.add(new BasicNameValuePair("creator", "阳顶天"));
		nvps.add(new BasicNameValuePair("createdate", "2012-1-1"));
		nvps.add(new BasicNameValuePair("xtgybl", "aaa"));
		nvps.add(new BasicNameValuePair("cs", "name"));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testMenuItemRemove() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/menuItem/remove");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "6"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testMenuItemOne() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/menuItem/one");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "41"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testMenuItemAllRoots() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/menuItem/all-roots");
		arrist();
	}

//	@Test
	public void testMenuItemAllRootWithChildren() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/menuItem/all-roots-tree");
		arrist();
	}

//	@Test
	public void testMenuItemChildren() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/menuItem/children");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("menuItemId", "41"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testRoleMenuItemAdd() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/role-menuItem/add");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("menuItemId", "41"));
		nvps.add(new BasicNameValuePair("roleId", "41"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testLogin() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/user/login");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "zhangsan"));
		nvps.add(new BasicNameValuePair("password", "111111"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testLoginout() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/user/loginout");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "zhangsan"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testGetLockState() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/user/lockstate");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "zhangsan"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}

//	@Test
	public void testChangeLockState() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/user/change-lockstate");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "zhangsan"));
		nvps.add(new BasicNameValuePair("isLock", "false"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testChangePassword() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/user/change-password");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "zhangsan"));
		nvps.add(new BasicNameValuePair("oldPassword", "666666"));
		nvps.add(new BasicNameValuePair("newPassword", "111111"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testGetPermissionTree() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/user/permission-tree");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "zhangsan"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
//	@Test
	public void testLoginAndReturnPermission() throws Exception {
		hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/user/login-return-permission");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "zhangsan"));
		nvps.add(new BasicNameValuePair("password", "111dd111"));
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		arrist();
	}
	
	private void arrist() throws Exception {
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}

		};
		String responseBody = httpClient.execute(host, hostRequest, responseHandler);
		LOGGER.debug("----------------------------------------");
		LOGGER.debug(responseBody);
		LOGGER.debug("----------------------------------------");
	}
}
