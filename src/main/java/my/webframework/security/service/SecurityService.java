package my.webframework.security.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import my.webframework.security.domain.Employee;
import my.webframework.security.domain.Employee_;
import my.webframework.security.domain.MenuItem;
import my.webframework.security.domain.MenuItem_;
import my.webframework.security.domain.Organization;
import my.webframework.security.domain.Organization_;
import my.webframework.security.domain.Role;
import my.webframework.security.domain.RoleMenuItem;
import my.webframework.security.domain.Role_;
import my.webframework.security.reponsitory.IEmployeeReponsitory;
import my.webframework.security.reponsitory.IMenuItemReponsitory;
import my.webframework.security.reponsitory.IOrganizationReponsitory;
import my.webframework.security.reponsitory.IRoleMenuItemReponsitory;
import my.webframework.security.reponsitory.IRoleReponsitory;
import my.webframework.security.reponsitory.ISystemReponsitory;
import my.webframework.share.BatchSaveResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author crjun
 *
 */
/**
 * @author crjun
 *
 */
/**
 * @author crjun
 *
 */
@Service
@Transactional
public class SecurityService implements ISecurityService {
	private static final Logger LOGGER = Logger.getLogger(SecurityService.class);

	@Resource(name = "employeeReponsitory")
	private IEmployeeReponsitory employeeReponsitory;
	@Resource(name = "roleReponsitory")
	private IRoleReponsitory roleReponsitory;
	@Resource(name = "menuItemReponsitory")
	private IMenuItemReponsitory menuItemReponsitory;
	@Resource(name = "organizationReponsitory")
	private IOrganizationReponsitory organizationReponsitory;
	@Resource(name = "roleMenuItemReponsitory")
	private IRoleMenuItemReponsitory roleMenuItemReponsitory;
	@Resource(name = "systemReponsitory")
	private ISystemReponsitory systemReponsitory;

	public void setEmployeeReponsitory(IEmployeeReponsitory employeeReponsitory) {
		this.employeeReponsitory = employeeReponsitory;
	}

	public void setRoleReponsitory(IRoleReponsitory roleReponsitory) {
		this.roleReponsitory = roleReponsitory;
	}

	public void setMenuItemReponsitory(IMenuItemReponsitory menuItemReponsitory) {
		this.menuItemReponsitory = menuItemReponsitory;
	}

	public void setOrganizationReponsitory(IOrganizationReponsitory organizationReponsitory) {
		this.organizationReponsitory = organizationReponsitory;
	}

	public void setRoleMenuItemReponsitory(IRoleMenuItemReponsitory roleMenuItemReponsitory) {
		this.roleMenuItemReponsitory = roleMenuItemReponsitory;
	}

	public void setSystemReponsitory(ISystemReponsitory systemReponsitory) {
		this.systemReponsitory = systemReponsitory;
	}

	@Override
	public boolean addOrUpdateOrganization(Organization organization) {
		organization = organizationReponsitory.saveAndFlush(organization);
		return organization.getId() == null ? false : true;
	}

	@Override
	public boolean removeOrganization(Integer id) {
		organizationReponsitory.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Organization queryOrganizationById(Integer id) {
		return organizationReponsitory.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> queryAllRootOrganizations() {
		return organizationReponsitory.findAll(new Specification<Organization>() {
			@Override
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(cb.isNull(root.get(Organization_.pid)));
				return null;
			}
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> queryAllRootOrganizationsWithChildren() {
		List<Organization> organizations = queryAllRootOrganizations();
		if (organizations != null) { // 获取延迟加载的数据，最终形成一棵树
			for (Organization o : organizations) {
				arristLazyData(o);
			}
		}
		return organizations;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> queryChildrenForOrganization(Integer organizationId) {
		Organization o = organizationReponsitory.findOne(organizationId);
		return new ArrayList<Organization>(o.getChildren());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> queryOrganizations(Specification<Organization> filter) {
		return organizationReponsitory.findAll(filter);
	}

	@Override
	public boolean addEmployeesToOrganization(Integer organizationId, final Integer... employeeIds) {
		Organization o = organizationReponsitory.findOne(organizationId);
		Validate.notNull(o, "组织机构[" + organizationId + "]不存在");
		Set<Employee> employees = o.getEmployees();
		List<Employee> newEmployees = employeeReponsitory.findAll(new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// in语句和其他略有不同，需要首先建立一个IN<T>，然后增加值
				In<Integer> in = cb.in(root.get(Employee_.id));
				for (Integer v : employeeIds) {
					in.value(v);
				}
				return in;
			}
		});
		if (employees == null) {
			employees = new HashSet<Employee>();
		}
		employees.addAll(newEmployees);
		organizationReponsitory.saveAndFlush(o);
		return true;
	}

	@Override
	public boolean removeEmployeesFromOrganization(Integer organizationId, Integer... employeeIds) {
		Organization o = organizationReponsitory.findOne(organizationId);
		Validate.notNull(o, "组织机构[" + organizationId + "]不存在");
		Set<Employee> employees = o.getEmployees();
		if (employees == null) {
			return true;
		}
		List<Employee> removeEmployees = new ArrayList<Employee>();
		for (Employee e : employees) {
			if (ArrayUtils.contains(employeeIds, e.getId())) {
				removeEmployees.add(e);
			}
		}
		employees.removeAll(removeEmployees);
		organizationReponsitory.saveAndFlush(o);
		return true;
	}

	@Override
	public boolean addRolesToOrganization(Integer organizationId, final Integer... roleIds) {
		Organization o = organizationReponsitory.findOne(organizationId);
		Validate.notNull(o, "组织机构[" + organizationId + "]不存在");
		List<Role> newRoles = roleReponsitory.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// in语句和其他略有不同，需要首先建立一个IN<T>，然后增加值
				In<Integer> in = cb.in(root.get(Role_.id));
				for (Integer v : roleIds) {
					in.value(v);
				}
				return in;
			}
		});
		o.getRoles().addAll(newRoles);
		organizationReponsitory.saveAndFlush(o);
		return true;
	}

	@Override
	public boolean removeRolesFromOrganization(Integer organizationId, Integer... roleIds) {
		Organization o = organizationReponsitory.findOne(organizationId);
		Validate.notNull(o, "组织机构[" + organizationId + "]不存在");
		List<Role> removeRoles = new ArrayList<Role>();
		for (Role r : o.getRoles()) {
			if (ArrayUtils.contains(roleIds, r.getId())) {
				removeRoles.add(r);
			}
		}
		o.getRoles().removeAll(removeRoles);
		organizationReponsitory.saveAndFlush(o);
		return true;
	}

	@Override
	public boolean addOrUpdateEmployee(Employee employee) {
		employee = employeeReponsitory.saveAndFlush(employee);
		return employee.getId() == null ? false : true;
	}

	@Override
	public boolean removeEmployee(Integer id) {
		employeeReponsitory.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Employee queryEmployeeById(Integer id) {
		return employeeReponsitory.findOne(id);
		// Hibernate.initialize(e.getOrganization()); // 强制初始化
	}

	@Override
	public Employee queryEmployeeByUsername(final String username) {
		return employeeReponsitory.findOne(new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("user").get("username"), username);
			}
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> queryAllEmployees() {
		return employeeReponsitory.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Employee> queryAllEmployees(Integer pageNum, Integer pageSize) {
		return employeeReponsitory.findAll(new PageRequest(pageNum, pageSize));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> queryEmployeesByName(final String name) {
		return employeeReponsitory.findAll(new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(cb.like(root.get(Employee_.name), "%" + name + "%"));
				return null;
			}
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Employee> queryEmployeesByName(final String name, Integer pageNum, Integer pageSize) {
		return employeeReponsitory.findAll(new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(cb.like(root.get(Employee_.name), "%" + name + "%"));
				return null;
			}
		}, new PageRequest(pageNum, pageSize));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> queryEmployees(Specification<Employee> filter) {
		return employeeReponsitory.findAll(filter);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Employee> queryEmployeesByPage(Specification<Employee> filter, Pageable page) {
		return employeeReponsitory.findAll(filter, page);
	}

	@Override
	public boolean addRolesToEmployee(Integer employeeId, final Integer... roleIds) {
		Employee e = employeeReponsitory.findOne(employeeId);
		Validate.notNull(e, "员工[" + employeeId + "]不存在");
		List<Role> newRoles = roleReponsitory.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// in语句和其他略有不同，需要首先建立一个IN<T>，然后增加值
				In<Integer> in = cb.in(root.get(Role_.id));
				for (Integer v : roleIds) {
					in.value(v);
				}
				return in;
			}
		});
		e.getRoles().addAll(newRoles);
		employeeReponsitory.saveAndFlush(e);
		return true;
	}

	@Override
	public boolean removeRolesFromEmployee(Integer employeeId, Integer... roleIds) {
		Employee e = employeeReponsitory.findOne(employeeId);
		Validate.notNull(e, "员工[" + employeeId + "]不存在");
		Set<Role> roles = e.getRoles();
		List<Role> removeRoles = new ArrayList<Role>();
		for (Role r : roles) {
			if (ArrayUtils.contains(roleIds, r.getId())) {
				removeRoles.add(r);
				r.getEmployees().remove(e);
			}
		}
		roles.removeAll(removeRoles);
		employeeReponsitory.saveAndFlush(e);
		return true;
	}

	@Override
	public boolean addOrUpdateRole(Role role) {
		role = roleReponsitory.saveAndFlush(role);
		return role.getId() == null ? false : true;
	}

	@Override
	public boolean removeRole(Integer id) {
		Role r = roleReponsitory.findOne(id);
		Set<Employee> es = r.getEmployees();
		for (Employee e : es) {
			e.getRoles().remove(r);
		}
		roleReponsitory.delete(r);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Role queryRoleById(Integer id) {
		return roleReponsitory.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryAllRootRoles() {
		return roleReponsitory.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(cb.isNull(root.get(Role_.pid)));
				return null;
			}
		});
	}

	private void arristLazyData(Object o) {
		try {
			Method m = o.getClass().getMethod("getChildren");
			Set<?> roles = (Set<?>) m.invoke(o, new Object[] {});
			// Hibernate.initialize(o.getChildren());
			// 正常情况下应该使用这个来强制的加载，此处采用了反射调用，
			if (roles != null) {
				Iterator<?> i = roles.iterator();
				while (i.hasNext()) {
					arristLazyData(i.next());
				}
			}
		} catch (Throwable t) {
			LOGGER.debug("方法异常", t);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryAllRootRolesWithChildren() {
		List<Role> roles = queryAllRootRoles();
		if (roles != null) { // 获取延迟加载的数据，最终形成一棵树
			for (Role r : roles) {
				arristLazyData(r);
			}
		}
		return roles;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryChildrenForRole(int roleId) {
		Role role = roleReponsitory.findOne(roleId);
		return new ArrayList<Role>(role.getChildren());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryRolesByOrganization(final Integer organizationId) {
		return roleReponsitory.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(cb.equal(root.get(Role_.organization_id), organizationId));
				return null;
			}
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> queryRoles(Specification<Role> filter) {
		return roleReponsitory.findAll(filter);
	}

	@Override
	public boolean addEmployeesToRole(Integer roleId, final Integer... employeeIds) {
		Role r = roleReponsitory.findOne(roleId);
		Validate.notNull(r, "角色[" + roleId + "]不存在");
		List<Employee> newEmployees = employeeReponsitory.findAll(new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// in语句和其他略有不同，需要首先建立一个IN<T>，然后增加值
				In<Integer> in = cb.in(root.get(Employee_.id));
				for (Integer v : employeeIds) {
					in.value(v);
				}
				return in;
			}
		});
		// 处理多对多的关系的时候，注意，谁是维护关系的一方，要注入关系，然后被注入方，才可以实现关联！！！！
		for (Employee e : newEmployees) {
			e.getRoles().add(r);
		}
		r.getEmployees().addAll(newEmployees);
		roleReponsitory.saveAndFlush(r);
		return true;
	}

	@Override
	public boolean removeEmployeesFromRole(Integer roleId, Integer... employeeIds) {
		Role r = roleReponsitory.findOne(roleId);
		Validate.notNull(r, "角色[" + roleId + "]不存在");
		Set<Employee> employees = r.getEmployees();
		List<Employee> tempEmployees = new ArrayList<Employee>();
		for (Employee e : employees) {
			if (ArrayUtils.contains(employeeIds, e.getId())) {
				tempEmployees.add(e);
				e.getRoles().remove(r);
			}
		}
		employees.removeAll(tempEmployees);
		roleReponsitory.saveAndFlush(r);
		return true;
	}

	@Override
	public boolean addOrUpdateMenuItem(MenuItem menuItem) {
		menuItem = menuItemReponsitory.saveAndFlush(menuItem);
		return menuItem.getId() == null ? false : true;
	}

	@Override
	public boolean removeMenuItem(Integer id) {
		menuItemReponsitory.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public MenuItem queryMenuItemById(Integer id) {
		return menuItemReponsitory.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MenuItem> queryAllRootMenuItems() {
		return menuItemReponsitory.findAll(new Specification<MenuItem>() {
			@Override
			public Predicate toPredicate(Root<MenuItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(cb.isNull(root.get(MenuItem_.pid)));
				return null;
			}
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<MenuItem> queryAllRootMenuItemsWithChildren() {
		List<MenuItem> menuItems = queryAllRootMenuItems();
		if (menuItems != null) { // 获取延迟加载的数据，最终形成一棵树
			for (MenuItem mi : menuItems) {
				arristLazyData(mi);
			}
		}
		return menuItems;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MenuItem> queryChildrenForMenuItem(int menuItemId) {
		MenuItem menuItem = menuItemReponsitory.findOne(menuItemId);
		return new ArrayList<MenuItem>(menuItem.getChildren());
	}

	@Override
	@Transactional(readOnly = true)
	public List<MenuItem> queryMenuItems(Specification<MenuItem> filter) {
		return menuItemReponsitory.findAll(filter);
	}

	@Override
	public boolean addOrUpdateRoleMenuItem(Integer roleId, Integer menuItemId, String power_expand) {
		RoleMenuItem rmi = new RoleMenuItem();
		rmi.setRole_id(roleId);
		rmi.setModule_id(menuItemId);
		rmi.setPower_expand(power_expand);
		rmi = roleMenuItemReponsitory.save(rmi);
		return rmi.getId() == null ? false : true;
	}

	@Override
	public boolean removeRoleMenuItem(Integer roleMenuId) {
		roleMenuItemReponsitory.delete(roleMenuId);
		return true;
	}

	@Override
	public boolean auth(final String username, final String password) {
		Long count = employeeReponsitory.count(new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate userP = cb.equal(root.get("user").get("username"), username);
				Predicate passwordP = cb.equal(root.get("user").get("password"), password);
				query.where(userP, passwordP);
				return null;
			}
		});
		if (count > 0)
			return true;
		return false;
	}

	@Override
	public boolean changePassword(final String username, String oldPassword, String newPassword) {
		Employee e = queryEmployeeByUsername(username);
		if (null != e && StringUtils.equals(oldPassword, e.getUser().getPassword())) {
			e.getUser().setPassword(newPassword);
			employeeReponsitory.saveAndFlush(e);
			return true;
		} else {
			LOGGER.debug("员工不存或者旧密码不正确>>>>");
		}
		return false;
	}

	@Override
	public String getLockState(final String username) {
		Employee e = queryEmployeeByUsername(username);
		Validate.notNull(e, "员工账户[" + username + "]不存在");
		return e.getUser().getIslock();
	}

	@Override
	public boolean changeLockState(final String username, boolean isLock) {
		Employee e = queryEmployeeByUsername(username);
		Validate.notNull(e, "员工账户[" + username + "]不存在");
		if (isLock) {
			e.getUser().setIslock("T");
		} else {
			e.getUser().setIslock("F");
		}
		employeeReponsitory.saveAndFlush(e);
		return true;
	}

	@Override
	public BatchSaveResult<Employee> batchAddOrUpdateEmployee(Employee... employees) {
		throw new RuntimeException(">>>>>>尚没有实现！");
	}

	@Override
	@Transactional(readOnly = true)
	public List<MenuItem> getPermissionTree(String username) {
		List<MenuItem> trees = queryAllRootMenuItemsWithChildren();
		
		Set<RoleMenuItem> allPermissionMenuItems = new HashSet<RoleMenuItem>();
		Employee e = queryEmployeeByUsername(username);
		Set<Role> roles = e.getRoles();
		for(Role role : roles) {
			Set<RoleMenuItem> roleMenuItems = role.getRoleMenuItems();
			if (roleMenuItems == null)
				continue;
			allPermissionMenuItems.addAll(roleMenuItems);
		}
		// 开始遍历
		List<MenuItem> result = new ArrayList<MenuItem>();
		for (MenuItem tree : trees) {
			MenuItem newTree = filterPermissionTree(allPermissionMenuItems, tree);
			if (null == newTree) 
				continue;
			result.add(newTree);
		}
		return result;
	}
	
	private MenuItem copyNode(MenuItem node) {
		MenuItem newNode = new MenuItem();
		newNode.setCreatedate(node.getCreatedate());
		newNode.setCreator(node.getCreator());
		newNode.setCs(node.getCs());
		newNode.setDescription(node.getDescription());
		newNode.setId(node.getId());
		newNode.setLabel(node.getLabel());
		newNode.setModule(node.getModule());
		newNode.setPermission(node.getPermission());
		newNode.setPid(node.getPid());
		newNode.setSfqy(node.getSfqy());
		newNode.setSort(node.getSort());
		newNode.setTb(node.getTb());
		newNode.setType(node.getType());
		newNode.setXh(node.getXh());
		newNode.setXtbm(node.getXtbm());
		newNode.setXtgybl(node.getXtgybl());
		return newNode;
	}
	
	/* 针对某一个树，来过滤树 */
	private MenuItem filterPermissionTree(Set<RoleMenuItem> allPermissionMenuItems, MenuItem node) {
		MenuItem newNode = copyNode(node);
		RoleMenuItem rmi = isHavePermission(allPermissionMenuItems, node);
		boolean havePermission = null == rmi ? false : true;
		if (havePermission) {
			newNode.setPermission(rmi.getPower_expand());
		} else {
			newNode.setPermission("NO-PERMISSIONS");
		}
		
		List<MenuItem> childrens = node.getChildren();
		List<MenuItem> newChildrens = new ArrayList<MenuItem>();
		if (CollectionUtils.isNotEmpty(childrens)) { // 非叶子节点
			for (MenuItem children :childrens) {
				MenuItem temp = filterPermissionTree(allPermissionMenuItems, children);
				if (temp != null) {
					newChildrens.add(temp);
				}
			}
			Collections.sort(newChildrens, new Comparator<MenuItem>() {
				@Override
				public int compare(MenuItem o1, MenuItem o2) {
					if (o1.getXh() < o2.getXh())
						return -1;
					else if (o1.getXh() > o2.getXh())
						return 1;
					else 
						return 0;
				}
				
			});
			if (CollectionUtils.isEmpty(newChildrens) && !havePermission) { // 是空的，同时，非叶子节点也没有权限，直接返回null
				return null;
			}
			newNode.setChildren(newChildrens);
		} else { // 是叶子节点
			if (!havePermission) { // 同时，还没有权限，返回null
				return null;
			}
		}
		return newNode;
	}

	private RoleMenuItem isHavePermission(Set<RoleMenuItem> allPermissionMenuItems, MenuItem mi) {
		for(RoleMenuItem rmi : allPermissionMenuItems) {
			if (rmi.getModule_id() == mi.getId())
				return rmi;
		}
		return null;
	}

	@Override
	public boolean addSystem(System system) {
		/* 增加系统，默认注入改系统的超级账户 */
		return false;
	}

	@Override
	public boolean updateSystem(System system) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeSystem(Integer systemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<System> getAllSystem() {
		// TODO Auto-generated method stub
		return null;
	}
}
