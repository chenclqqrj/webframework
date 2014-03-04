package my.webframework.security.service;

import java.util.List;

import my.webframework.security.domain.Employee;
import my.webframework.security.domain.MenuItem;
import my.webframework.security.domain.Organization;
import my.webframework.security.domain.Role;
import my.webframework.security.domain.User;
import my.webframework.share.BatchSaveResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
/**  */
public interface ISecurityService {
	
	/** ############################################################### */
	/* 增加或者更新组织机构 */
	boolean addOrUpdateOrganization(Organization organization);
	/* 删除组织机构 */
	boolean removeOrganization(Integer id);
	/* 查询一个机构 */
	Organization queryOrganizationById(Integer id);
	/* 查询角色，获取全部的根结构 */
	List<Organization> queryAllRootOrganizations();
	/* 查询角色，获取全部的根结构   返回全部的目录树结构 */
	List<Organization> queryAllRootOrganizationsWithChildren();
	/* 查询角色，获取子角色 */
	List<Organization> queryChildrenForOrganization(Integer organizationId);
	/* 查询机构，筛选条件为null，获取全部 */
	List<Organization> queryOrganizations(Specification<Organization> filter);
	/* 增加员工到机构中 */
	boolean addEmployeesToOrganization(Integer organizationId, Integer... emploeeIds);
	/* 删除员工从机构中 */
	boolean removeEmployeesFromOrganization(Integer organizationId, Integer... emploeeIds);
	/* 增加角色到机构中 */
	boolean addRolesToOrganization(Integer organizationId, Integer... roleIds);
	/* 删除角色从机构中 */
	boolean removeRolesFromOrganization(Integer organizationId, Integer... roleIds);
	
	/** ############################################################### */
	/* 增加或者更新组织员工 */
	BatchSaveResult<Employee> batchAddOrUpdateEmployee(Employee... employees);
	/* 增加或者更新员工 */
	boolean addOrUpdateEmployee(Employee employee);
	/* 删除员工 */
	boolean removeEmployee(Integer id);
	/* 查询一个员工 */
	Employee queryEmployeeById(Integer id);
	/* 查询一个员工 */
	Employee queryEmployeeByUsername(String username);
	/* 查询员工，获取全部 */
	List<Employee> queryAllEmployees();
	/* 查询员工，获取全部 分页 */
	Page<Employee> queryAllEmployees(Integer pageNum, Integer pageSize);
	/* 查询员工，模糊匹配 查询 */
	List<Employee> queryEmployeesByName(String name);
	/* 查询员工，模糊匹配 查询   分页 */
	Page<Employee> queryEmployeesByName(String name, Integer pageNum, Integer pageSize);
	/* 查询员工，筛选条件为null，获取全部 */
	List<Employee> queryEmployees(Specification<Employee> filter);
	/* 查询员工，筛选条件为null，获取全部 分页 */
	Page<Employee> queryEmployeesByPage(Specification<Employee> filter, Pageable page);
	/* 增加角色给员工 */
	boolean addRolesToEmployee(Integer employeeId, Integer... roleIds);
	/* 删除角色给员工 */
	boolean removeRolesFromEmployee(Integer employeeId, Integer... roleIds);
	
	/** ############################################################### */
	/* 增加或者更新角色 */
	boolean addOrUpdateRole(Role role);
	/* 删除角色 */
	boolean removeRole(Integer id);
	/* 查询一个角色 */
	Role queryRoleById(Integer id);
	/* 查询角色，获取全部的根角色 */
	List<Role> queryAllRootRoles();
	/* 查询角色，获取全部的根角色   返回全部的目录树结构 */
	List<Role> queryAllRootRolesWithChildren();
	/* 查询角色，获取子角色 */
	List<Role> queryChildrenForRole(int roleId);
	/* 查询角色，根据部门查询*/
	List<Role> queryRolesByOrganization(Integer organizationId);
	/* 查询角色，筛选条件为null，获取全部 */
	List<Role> queryRoles(Specification<Role> filter);
	/* 增加员工给角色 */
	boolean addEmployeesToRole(Integer roleId, Integer... employeeIds);
	/* 删除员工给角色  */
	boolean removeEmployeesFromRole(Integer roleId, Integer... employeeIds);
	
	/** ############################################################### */
	/* 增加菜单 */
	boolean addOrUpdateMenuItem(MenuItem menuItem);
	/* 增加菜单 */
	boolean removeMenuItem(Integer id);
	/* 查询一个菜单 */
	MenuItem queryMenuItemById(Integer id);
	/* 查询菜单，获取全部的根菜单 */
	List<MenuItem> queryAllRootMenuItems();
	/* 查询菜单，获取全部的根菜单   返回全部的目录树结构 */
	List<MenuItem> queryAllRootMenuItemsWithChildren();
	/* 查询菜单，获取子菜单 */
	List<MenuItem> queryChildrenForMenuItem(int menuItemId);
	/* 查询菜单，筛选条件为null，获取全部 */
	List<MenuItem> queryMenuItems(Specification<MenuItem> filter);
	/* 菜单-角色 */
	boolean addOrUpdateRoleMenuItem(Integer roleId, Integer menuItemId, String power_expand);
	boolean removeRoleMenuItem(Integer roleMenuId);
	
	/** ############################################################### */
	List<MenuItem> getPermissionTree(String username);
	/** ############################################################### */
	boolean addSystem(System system);
	boolean updateSystem(System system);
	boolean removeSystem(Integer systemId);
	List<System> getAllSystem();
	
	/** ############################################################### */
	Integer addUser(User user);
	boolean updateUser(User user);
	boolean removeUser(Integer userId);
	boolean auth(String username, String password);
	boolean userExists(String username);
	String changePassword(Integer userId, String oldPassword, String newPassword);
	boolean changeLockState(String username, boolean isLock);
	List<User> queryUseres(Specification<User> filter);
}
