package my.webframework.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户登录账户
 * 
 * @author 陈瑞军
 */
@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class User extends my.webframework.security.domain.Entity {
	private static final long serialVersionUID = -4384358401242576466L;
	/** 登录名称 */
	@Column(name = "username", nullable = false)
	private String username;
	/** 登录密码 */
	@Column(name = "password", nullable = false, updatable = false)
	@JsonIgnore
	private String password;
	/** 备用邮箱 */
	@Column(name = "reserveEmail", nullable = false)
	private String reserveEmail;
	/** 账户锁定 */
	@Column(name = "accountType", nullable = false)
	private AccountType accountType;
	/** 账户是否锁定 */
	@Column(name = "isLock")
	private boolean isLock;

	public static enum AccountType {
		/** 普通 */
		common,
		/** 邮件 */
		email,
		/** 电话 */
		phone,
		/** 电话 */
		identityCard
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReserveEmail() {
		return reserveEmail;
	}

	public void setReserveEmail(String reserveEmail) {
		this.reserveEmail = reserveEmail;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public boolean isLock() {
		return isLock;
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}