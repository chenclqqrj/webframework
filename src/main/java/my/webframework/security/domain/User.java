package my.webframework.security.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 员工登录账户
 * @author 陈瑞军
 */
@Embeddable
public class User {
	/** 登录名称 */
	@Column(name = "login_name")
	private String username;
	/** 登录密码 */
	@Column(name = "password")
	@JsonIgnore // 此处使用@JsonIgnore来忽略某些字段
	private String password;
	/** 账户锁定（T或者F） */
	@Column(name = "islock")
	private String islock;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIslock() {
		return islock;
	}

	public void setIslock(String islock) {
		this.islock = islock;
	}
}