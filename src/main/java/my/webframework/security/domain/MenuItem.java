package my.webframework.security.domain;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 菜单管理
 * 
 * @author 陈瑞军
 */
//@Entity
@Table(name = "moduletree")
public class MenuItem extends my.webframework.share.Entity {

	private static final long serialVersionUID = -8491302719149503799L;
	/** 菜单编码 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	/** 名称 */
	@Column(name = "label")
	private String label;
	/** 序号 */
	@Column(name = "xh")
	private Integer xh;
	/** 访问地址 */
	@Column(name = "module")
	private String module;
	/** 是否启用 */
	@Column(name = "sfqy")
	private Boolean sfqy;
	/** 上级编码 */
	@Column(name = "pid")
	private Integer pid;
	/** 类型 */
	@Column(name = "type")
	private String type;
	/** 图标 */
	@Column(name = "tb")
	@Lob
	private Blob tb;
	/** 显示顺序 */
	@Column(name = "sort")
	private Double sort;
	/** 系统ID */
	@Column(name = "xtbm")
	private String xtbm;
	/** 功能说明 */
	@Column(name = "description")
	private String description;
	/** 创建人 */
	@Column(name = "creator")
	private String creator;
	/** 创建时间 */
	@Temporal(TemporalType.DATE)
	@Column(name = "createdate")
	private Date createdate;
	/** 系统公用变量 */
	@Column(name = "xtgybl")
	private String xtgybl;
	/** 参数 */
	@Column(name = "cs")
	private String cs;
	/** 系统编码 */
	@Column(name = "system_Id")
	private Integer system_Id;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE }, targetEntity = MenuItem.class)
	@JoinColumn(name = "pid")
	private List<MenuItem> children;
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE }, targetEntity = RoleMenuItem.class)
	@JoinColumn(name = "module_id", unique = true)
	private Set<RoleMenuItem> roleMenuItems;
	@Transient
	private String permission;

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

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Boolean getSfqy() {
		return sfqy;
	}

	public void setSfqy(Boolean sfqy) {
		this.sfqy = sfqy;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Blob getTb() {
		return tb;
	}

	public void setTb(Blob tb) {
		this.tb = tb;
	}

	public Double getSort() {
		return sort;
	}

	public void setSort(Double sort) {
		this.sort = sort;
	}

	public String getXtbm() {
		return xtbm;
	}

	public void setXtbm(String xtbm) {
		this.xtbm = xtbm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getXtgybl() {
		return xtgybl;
	}

	public void setXtgybl(String xtgybl) {
		this.xtgybl = xtgybl;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public Set<RoleMenuItem> getRoleMenuItems() {
		return roleMenuItems;
	}

	public void setRoleMenuItems(Set<RoleMenuItem> roleMenuItems) {
		this.roleMenuItems = roleMenuItems;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getSystem_Id() {
		return system_Id;
	}

	public void setSystem_Id(Integer system_Id) {
		this.system_Id = system_Id;
	}

}
