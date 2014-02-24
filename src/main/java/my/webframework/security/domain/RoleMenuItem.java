package my.webframework.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色-菜单结构
 * 
 * @author 陈瑞军
 */
//@Entity
@Table(name = "modulerolelink")
public class RoleMenuItem {
	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	/** 角色 */
	@Column(name = "role_id", unique = true)
	public Integer role_id;
	/** 模块编码 */
	@Column(name = "module_id", unique = true)
	public Integer module_id;
	/** 权限补充，用来控制模块细节权限，未实现 */
	public String power_expand;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPower_expand() {
		return power_expand;
	}

	public void setPower_expand(String power_expand) {
		this.power_expand = power_expand;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public Integer getModule_id() {
		return module_id;
	}

	public void setModule_id(Integer module_id) {
		this.module_id = module_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleMenuItem other = (RoleMenuItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}