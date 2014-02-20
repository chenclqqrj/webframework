package my.webframework.security.service.springmvc;

import java.beans.PropertyEditorSupport;

import my.webframework.security.domain.Role;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class RoleTreeEditor extends PropertyEditorSupport {

	private final boolean allowEmpty;

	public RoleTreeEditor(boolean allowEmpty) {
		super();
		this.allowEmpty = allowEmpty;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		}
		Role o = JSON.parseObject(text, Role.class);
		try {
			setValue(o);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Could not parse Role: "
					+ ex.getMessage(), ex);
		}
	}

	@Override
	public String getAsText() {
		Role value = (Role) getValue();
		return (value != null ? JSON.toJSONString(value) : "");
	}

}