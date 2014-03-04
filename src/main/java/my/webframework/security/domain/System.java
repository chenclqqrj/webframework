package my.webframework.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 系统实体定义，通常来定义系统的基本信息，以及相关的系统级变量。
 * 
 * @author 陈瑞军
 */
@Entity
@Table(name = "system", uniqueConstraints = @UniqueConstraint(columnNames = {
		"name", "appCode" }))
public class System extends my.webframework.security.domain.Entity {
	private static final long serialVersionUID = -7963468418994235529L;
	/* 系统编码，系统外部附加的辅助的唯一编号 */
	@Column(name = "appCode")
	private String appCode;
	/* 系统名称 */
	@Column(name = "name", nullable = false)
	private String name;
	/* 系统标题 */
	@Column(name = "title")
	private String title;
	/* 系统标题 */
	@Column(name = "description")
	private String description;
	/* 系统版本 */
	@Column(name = "version")
	private String version;
	/* 版权信息 */
	@Column(name = "copyright")
	private String copyright;
	/* 系统路径 */
	@Column(name = "url")
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
