package my.webframework.security.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 角色结构
 * 
 * @author 陈瑞军
 */
@Entity
@Table(name = "role")
public class Role {
	/** 角色编码 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	/** 上级角色编码 */
	@Column(name = "pid")
	private Integer pid;
	/** 角色名称 */
	@Column(name = "name")
	private String name;
	/** 所属的组织机构编码 */
	@Column(name = "organization_id")
	private Integer organization_id;
	/** 角色类型 */
	@Column(name = "type")
	private Integer type;
	/** 子角色 */
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE }, targetEntity = Role.class)
	@JoinColumn(name = "pid")
	private Set<Role> children;
	/** 角色与菜单的关系 */
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE }, targetEntity = RoleMenuItem.class)
	@JoinColumn(name = "role_id")
	private Set<RoleMenuItem> roleMenuItems;
	/** 角色与员工的关系 */
	@JsonIgnore
	@ManyToMany(mappedBy = "roles", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Employee.class)
	private Set<Employee> employees;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Integer organization_id) {
		this.organization_id = organization_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Set<Role> getChildren() {
		return children;
	}

	public void setChildren(Set<Role> children) {
		this.children = children;
	}

	public Set<RoleMenuItem> getRoleMenuItems() {
		return roleMenuItems;
	}

	public void setRoleMenuItems(Set<RoleMenuItem> roleMenuItems) {
		this.roleMenuItems = roleMenuItems;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
}