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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 机构表结构
 * 
 * @author 陈瑞军
 */
@Entity
@Table(name = "organization")
public class Organization {
	/** 机构编码 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	/** 机构名称 */
	@Column(name = "label")
	private String label;
	/** 机构全称 */
	@Column(name = "full_name")
	private String full_name;
	/** 行政区划 */
	@Column(name = "xzqh")
	private String xzqh;
	/** 地址 */
	@Column(name = "address")
	private String address;
	/** 序号 */
	@Column(name = "order_number")
	private Double order_number;
	/** 邮编 */
	@Column(name = "zip_code")
	private String zip_code;
	/** 公开电话 */
	@Column(name = "pub_phone")
	private String pub_phone;
	/** 机构咨询电话 */
	@Column(name = "consulation_phone")
	private String consulation_phone;
	/** 中心窗口电话 */
	@Column(name = "window_phone")
	private String window_phone;
	/** 网址 */
	@Column(name = "www")
	private String www;
	/** 属性 */
	@Column(name = "property")
	private String property;
	/** 上级机构编码 */
	@Column(name = "pid")
	private Integer pid;
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE }, targetEntity = Organization.class)
	@JoinColumn(name = "pid")
	private Set<Organization> children;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH }, targetEntity = Employee.class)
	@JoinColumn(name = "organization_id")
	private Set<Employee> employees;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE }, targetEntity = Role.class)
	@JoinColumn(name = "organization_id")
	private Set<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getOrder_number() {
		return order_number;
	}

	public void setOrder_number(Double order_number) {
		this.order_number = order_number;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getPub_phone() {
		return pub_phone;
	}

	public void setPub_phone(String pub_phone) {
		this.pub_phone = pub_phone;
	}

	public String getConsulation_phone() {
		return consulation_phone;
	}

	public void setConsulation_phone(String consulation_phone) {
		this.consulation_phone = consulation_phone;
	}

	public String getWindow_phone() {
		return window_phone;
	}

	public void setWindow_phone(String window_phone) {
		this.window_phone = window_phone;
	}

	public String getWww() {
		return www;
	}

	public void setWww(String www) {
		this.www = www;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Set<Organization> getChildren() {
		return children;
	}

	public void setChildren(Set<Organization> children) {
		this.children = children;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}