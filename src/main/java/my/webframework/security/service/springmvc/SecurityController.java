package my.webframework.security.service.springmvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import my.webframework.security.domain.Employee;
import my.webframework.security.domain.MenuItem;
import my.webframework.security.domain.Organization;
import my.webframework.security.domain.Role;
import my.webframework.security.service.ISecurityService;
import my.webframework.share.CommonResult;
import my.webframework.share.RequestUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
@Transactional
public class SecurityController {
	private static final Logger LOGGER = Logger.getLogger(SecurityController.class);

	// 如果使用了自定义的类型，或者enum，进行了数据类型的转换，需要在这里注册，然后方可以使用
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); 
	    binder.registerCustomEditor(Organization.class, new OrganizationTreeEditor(true)); 
	    binder.registerCustomEditor(MenuItem.class, new MenuItemTreeEditor(true)); 
	    binder.registerCustomEditor(Role.class, new RoleTreeEditor(true)); 
	    binder.registerCustomEditor(Employee.class, new EmployeeEditor(true)); 
	}

	@Resource(name = "securityService")
	private ISecurityService securityService;

	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

	@RequestMapping(value = "/organization/add-tree")
	@ResponseBody
	public CommonResult addOrganizationTree(@RequestParam Organization organization) {
		return new CommonResult(securityService.addOrUpdateOrganization(organization));
	}
	
	@RequestMapping(value = "/organization/add/by-json")
	@ResponseBody
	public CommonResult addOrganizationByJson(@RequestParam Organization organization) {
		return addOrganization(organization);
	}

	@RequestMapping(value = "/organization/add")
	@ResponseBody
	public CommonResult addOrganization(@ModelAttribute Organization organization) {
		organization.setChildren(null); // 避免保存树状结构 没有必要  映射控制
		return new CommonResult(securityService.addOrUpdateOrganization(organization));
	}

	@RequestMapping(value = "/organization/remove")
	@ResponseBody
	public CommonResult removeOrganization(@RequestParam int id) {
		return new CommonResult(securityService.removeOrganization(id));
	}

	/* 获取指定组织机构 id不能为空 */
	@RequestMapping(value = "/organization/one")
	@ResponseBody
	public Organization queryOrganization(final @RequestParam Integer id) {
		Validate.notNull(id);
		return securityService.queryOrganizationById(id);
	}

	@RequestMapping(value = "/organization/all-roots")
	@ResponseBody
	@Transactional
	public List<Organization> queryAllRootOrganizations() {
		return securityService.queryAllRootOrganizations();
	}

	@RequestMapping(value = "/organization/all-roots-tree")
	@ResponseBody
	public List<Organization> queryAllRootOrganizationsWithChildren() {
		return securityService.queryAllRootOrganizationsWithChildren();
	}

	@RequestMapping(value = "/organization/children")
	@ResponseBody
	public List<Organization> queryChildrenForOrganization(@RequestParam int organizationId) {
		return securityService.queryChildrenForOrganization(organizationId);
	}

	/* 增加员工到机构中 */
	@RequestMapping(value = "/organization/add-employees")
	@ResponseBody
	public CommonResult addEmployeesToOrganization(final @RequestParam int organizationId, final @RequestParam String emploeeIds) {
		return new CommonResult(securityService.addEmployeesToOrganization(organizationId, ArrayUtils.toObject(RequestUtil.convertStrToIntArray(emploeeIds))));
	}

	/* 删除员工从机构中 */
	@RequestMapping(value = "/organization/remove-employees")
	@ResponseBody
	public CommonResult removeEmployeesFromOrganization(final @RequestParam int organizationId, final @RequestParam String emploeeIds) {
		return new CommonResult(securityService.removeEmployeesFromOrganization(organizationId, ArrayUtils.toObject(RequestUtil.convertStrToIntArray(emploeeIds))));
	}

	/* 增加角色到机构中 */
	@RequestMapping(value = "/organization/add-roles")
	@ResponseBody
	public CommonResult addRolesToOrganization(final @RequestParam int organizationId, final @RequestParam String roleIds) {
		return new CommonResult(securityService.addRolesToOrganization(organizationId, ArrayUtils.toObject(RequestUtil.convertStrToIntArray(roleIds))));
	}

	/* 删除角色从机构中 */
	@RequestMapping(value = "/organization/remove-roles")
	@ResponseBody
	public CommonResult removeRolesFromOrganization(final @RequestParam int organizationId, final @RequestParam String roleIds) {
		return new CommonResult(securityService.removeRolesFromOrganization(organizationId, ArrayUtils.toObject(RequestUtil.convertStrToIntArray(roleIds))));
	}

	@RequestMapping(value = "/employee/add/by-json")
	@ResponseBody
	public CommonResult addEmployeeByJson(@RequestParam Employee employee) {
		return addEmployee(employee);
	}

	@RequestMapping(value = "/employee/add")
	@ResponseBody
	public CommonResult addEmployee(@ModelAttribute Employee employee) {
		return new CommonResult(securityService.addOrUpdateEmployee(employee));
	}

	@RequestMapping(value = "/employee/{employeeId}/set-photo")
	@ResponseBody
	public CommonResult updateEmployeePhoto(@PathVariable int employeeId, @RequestParam MultipartFile picture) {
		Employee employee = securityService.queryEmployeeById(employeeId);
		try {
			employee.setPhoto(picture.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new CommonResult(securityService.addOrUpdateEmployee(employee));
	}

	@RequestMapping(value = "/employee/remove")
	@ResponseBody
	public CommonResult removeEmployee(@RequestParam int id) {
		return new CommonResult(securityService.removeEmployee(id));
	}

	/* 获取员工 id不能为空 */
	@RequestMapping(value = "/employee/one")
	@ResponseBody
	public Employee queryEmployee(final @RequestParam Integer id) {
		Validate.notNull(id);
		return securityService.queryEmployeeById(id);
	}

	/* 获取全部员工 */
	@RequestMapping(value = "/employee/all")
	@ResponseBody
	public List<Employee> queryAllEmployees() {
		return securityService.queryAllEmployees();
	}

	/* 分页--获取全部员工 */
	@RequestMapping(value = "/employee/all/page")
	@ResponseBody
	public Page<Employee> pageQueryAllEmployees(@RequestParam int pageNum, @RequestParam int pageSize) {
		return securityService.queryAllEmployees(pageNum, pageSize);
	}

	/* 根据员工的name来筛选 模糊匹配 */
	@RequestMapping(value = "/employee/by-name")
	@ResponseBody
	public List<Employee> queryEmployeesByName(final @RequestParam String name) {
		return securityService.queryEmployeesByName(name);
	}

	/* 根据员工的name来筛选  模糊匹配 分页 */
	@RequestMapping(value = "/employee/by-name/page")
	@ResponseBody
	public Page<Employee> pageQueryEmployeesByName(final @RequestParam String name, final @RequestParam int pageNum, final @RequestParam int pageSize) {
		return securityService.queryEmployeesByName(name, pageNum, pageSize);
	}
	
	@RequestMapping(value = "/employee/add-roles")
	@ResponseBody
	public CommonResult addRolesToEmployee(final @RequestParam int employeeId, final @RequestParam String roleIds) {
		return new CommonResult(securityService.addRolesToEmployee(employeeId, ArrayUtils.toObject(RequestUtil.convertStrToIntArray(roleIds))));
	}
	
	@RequestMapping(value = "/employee/remove-roles")
	@ResponseBody
	public CommonResult removeRolesToEmployee(final @RequestParam int employeeId, final @RequestParam String roleIds) {
		return new CommonResult(securityService.removeRolesFromEmployee(employeeId, ArrayUtils.toObject(RequestUtil.convertStrToIntArray(roleIds))));
	}

	@RequestMapping(value = "/role/add-tree")
	@ResponseBody
	public CommonResult addRoleTree(@RequestParam Role role) {
		return new CommonResult(securityService.addOrUpdateRole(role));
	}
	
	@RequestMapping(value = "/role/add/by-json")
	@ResponseBody
	public CommonResult addRoleByJson(@RequestParam Role role) {
		role.setChildren(null);
		return addRole(role);
	}

	@RequestMapping(value = "/role/add")
	@ResponseBody
	public CommonResult addRole(@ModelAttribute Role role) {
		return new CommonResult(securityService.addOrUpdateRole(role));
	}

	@RequestMapping(value = "/role/remove")
	@ResponseBody
	public CommonResult removeRole(@RequestParam int id) {
		return new CommonResult(securityService.removeRole(id));
	}

	/* 获取员工 id不能为空 */
	@RequestMapping(value = "/role/one")
	@ResponseBody
	public Role queryRole(final @RequestParam Integer id) {
		Validate.notNull(id);
		return securityService.queryRoleById(id);
	}

	/* 获取全部角色 */
	@RequestMapping(value = "/role/all-roots")
	@ResponseBody
	public List<Role> queryAllRootRoles() {
		return securityService.queryAllRootRoles();
	}
	
	/* 获取全部角色 树结构 */
	@RequestMapping(value = "/role/all-roots-tree")
	@ResponseBody
	public List<Role> queryAllRootRolesWithChildren() {
		return securityService.queryAllRootRolesWithChildren();
	}

	@RequestMapping(value = "/role/children")
	@ResponseBody
	public List<Role> queryChildrenForRole(@RequestParam int roleId) {
		return securityService.queryChildrenForRole(roleId);
	}

	@RequestMapping(value = "/role/by-organization")
	@ResponseBody
	public List<Role> queryRolesByOrganization(@RequestParam int organizationId) {
		return securityService.queryRolesByOrganization(organizationId);
	}
	
	@RequestMapping(value = "/role/add-employees")
	@ResponseBody
	public CommonResult addEmployeesToRole(final @RequestParam int roleId, final @RequestParam String employeeIds) {
		return new CommonResult(securityService.addEmployeesToRole(roleId, ArrayUtils.toObject(RequestUtil.convertStrToIntArray(employeeIds))));
	}
	
	@RequestMapping(value = "/role/remove-employees")
	@ResponseBody
	public CommonResult removeEmployeesFromRole(final @RequestParam int roleId, final @RequestParam String employeeIds) {
		return new CommonResult(securityService.removeEmployeesFromRole(roleId, ArrayUtils.toObject(RequestUtil.convertStrToIntArray(employeeIds))));
	}

	@RequestMapping(value = "/menuItem/add-tree")
	@ResponseBody
	public CommonResult addMenuItemTree(@RequestParam MenuItem menuItem) {
		return new CommonResult(securityService.addOrUpdateMenuItem(menuItem));
	}
	
	@RequestMapping(value = "/menuItem/add/by-json")
	@ResponseBody
	public CommonResult addMenuItemByJson(@RequestParam MenuItem menuItem) {
		menuItem.setChildren(null);
		return addMenuItem(menuItem);
	}

	@RequestMapping(value = "/menuItem/add")
	@ResponseBody
	public CommonResult addMenuItem(@ModelAttribute MenuItem menuItem) {
		return new CommonResult(securityService.addOrUpdateMenuItem(menuItem));
	}

	@RequestMapping(value = "/menuItem/remove")
	@ResponseBody
	public CommonResult removeMenuItem(@RequestParam int id) {
		return new CommonResult(securityService.removeMenuItem(id));
	}

	@RequestMapping(value = "/menuItem/one")
	@ResponseBody
	public MenuItem queryMenuItem(final @RequestParam Integer id) {
		Validate.notNull(id);
		return securityService.queryMenuItemById(id);
	}

	@RequestMapping(value = "/menuItem/all-roots")
	@ResponseBody
	public List<MenuItem> queryAllRootMenuItems() {
		return securityService.queryAllRootMenuItems();
	}

	@RequestMapping(value = "/menuItem/all-roots-tree")
	@ResponseBody
	public List<MenuItem> queryAllRootMenuItemsWithChildren() {
		return securityService.queryAllRootMenuItemsWithChildren();
	}
	
	@RequestMapping(value = "/menuItem/children")
	@ResponseBody
	public List<MenuItem> queryChildrenForMenuItem(@RequestParam int menuItemId) {
		return securityService.queryChildrenForMenuItem(menuItemId);
	}
	
	@RequestMapping(value = "/role-menuItem/add")
	@ResponseBody
	public CommonResult addOrUpdateRoleMenuItem(@RequestParam int roleId, @RequestParam int menuItemId, @RequestParam String power_expand) {
		return new CommonResult(securityService.addOrUpdateRoleMenuItem(roleId, menuItemId, power_expand));
	}
	
	@RequestMapping(value = "/role-menuItem/remove")
	@ResponseBody
	public CommonResult removeRoleMenuItem(@RequestParam int roleMenuItemId) {
		return new CommonResult(securityService.removeRoleMenuItem(roleMenuItemId));
	}
	
	@RequestMapping(value = "/user/login")
	@ResponseBody
	public CommonResult login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
		boolean isAuth = securityService.auth(username, password);
		if (isAuth) {
			Employee e = securityService.queryEmployeeByUsername(username);
			request.getSession().setAttribute("currentEmployee", e);
		}
		return new CommonResult(isAuth);
	}
	
	@RequestMapping(value = "/user/loginout")
	@ResponseBody
	public CommonResult loginout(@RequestParam String username, HttpServletRequest request) {
        request.getSession().removeAttribute("currentEmployee");
		return new CommonResult(true);
	}
	
	@RequestMapping(value = "/user/change-password")
	@ResponseBody
	public CommonResult changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
		return null;
//		return new CommonResult(securityService.changePassword(username, oldPassword, newPassword));
	}
	
	@RequestMapping(value = "/user/change-lockstate")
	@ResponseBody
	public CommonResult changeLockState(@RequestParam String username, @RequestParam boolean isLock) {
		return new CommonResult(securityService.changeLockState(username, isLock));
	}
	
	@RequestMapping(value = "/user/lockstate")
	@ResponseBody
	public CommonResult getLockState(@RequestParam String username) {
		return null;
//		return new CommonResult(securityService.getLockState(username));
	}
	
	@RequestMapping(value = "/user/permission-tree")
	@ResponseBody
	public List<MenuItem> getPermissionTree(@RequestParam String username) {
		return securityService.getPermissionTree(username);
	}
	
	@RequestMapping(value = "/user/login-return-permission")
	@ResponseBody
	public CommonResult loginAndReturnPermission(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
		boolean isAuth = securityService.auth(username, password);
		if (isAuth) {
			Employee e = securityService.queryEmployeeByUsername(username);
			request.getSession().setAttribute("currentEmployee", e);
			return new CommonResult(getPermissionTree(username));
		} else {
			return new CommonResult(isAuth);
		}
	}
	
	@RequestMapping(value = "/hello")
	public ModelAndView hello(@RequestParam String name) {
		ModelAndView v = new ModelAndView();
		v.addObject("name", name);
		return v;
	}
	
}
