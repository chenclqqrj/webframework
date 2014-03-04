package my.webframework.security.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 员工结构
 * 
 * @author 陈瑞军
 */
@Entity
@Table(name = "employee")
public class Employee {
	/** 人员id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	/** 组织机构id */
	// @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH,
	// targetEntity = Organization.class)
	// @JoinColumn(name = "organization_id")
	// private Organization organization;
	@Column(name = "organization_id")
	private Integer organization_id;
	/** 部门id */
	@Column(name = "area_id")
	private Integer area_id;
	/** 姓名 */
	@Column(name = "name")
	private String name;
	/** 性别 */
	@Column(name = "sex")
	private String sex;
	/** 登录信息 */
//	@Embedded()
//	@AttributeOverrides({
//			@AttributeOverride(name = "username", column = @Column(name = "login_name")),
//			@AttributeOverride(name = "password", column = @Column(name = "password")),
//			@AttributeOverride(name = "isLock", column = @Column(name = "islock")) })
//	private User user;
	/** 排序字段 */
	@Column(name = "order_number")
	private double order_number;
	/** 出生年月 */
	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	/** 办公电话 */
	@Column(name = "pub_phone")
	private String pub_phone;
	/** 手机号 */
	@Column(name = "handphone")
	private String handphone;
	/** 学历 */
	@Column(name = "school")
	private String school;
	/** 民族 */
	@Column(name = "nation")
	private String nation;
	/** 政治面貌 */
	@Column(name = "political")
	private String political;
	/** 职务 */
	@Column(name = "job")
	private String job;
	/** 工作时间 */
	@Column(name = "job_date")
	@Temporal(TemporalType.DATE)
	private Date job_date;
	/** 邮箱地址 */
	@Column(name = "email")
	private String email;
	/** 照片 */
	@JsonIgnore
	@Column(name = "photo", length=10000000) // 此处会需配置正确，然后修改数据库此字段为LONGBLOB ，不然会出现Data truncation: Data too long for column row 1错误！！！
	@Lob()
	private byte[] photo;
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "roleemployeelink", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArea_id() {
		return area_id;
	}

	public void setArea_id(Integer area_id) {
		this.area_id = area_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public double getOrder_number() {
		return order_number;
	}

	public void setOrder_number(double order_number) {
		this.order_number = order_number;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPub_phone() {
		return pub_phone;
	}

	public void setPub_phone(String pub_phone) {
		this.pub_phone = pub_phone;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getJob_date() {
		return job_date;
	}

	public void setJob_date(Date job_date) {
		this.job_date = job_date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Integer getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Integer organization_id) {
		this.organization_id = organization_id;
	}

}