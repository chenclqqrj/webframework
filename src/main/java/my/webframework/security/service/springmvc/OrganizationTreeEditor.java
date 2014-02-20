package my.webframework.security.service.springmvc;

import java.beans.PropertyEditorSupport;

import my.webframework.security.domain.Organization;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class OrganizationTreeEditor extends PropertyEditorSupport {

	private final boolean allowEmpty;

	public OrganizationTreeEditor(boolean allowEmpty) {
		super();
		this.allowEmpty = allowEmpty;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println("##############################" + text);
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		Organization o = JSON.parseObject(text, Organization.class);
		System.out.println("##############################" + JSON.toJSONString(o));
		try {
			setValue(o);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Could not parse Organization: "
					+ ex.getMessage(), ex);
		}
	}

	@Override
	public String getAsText() {
		Organization value = (Organization) getValue();
		return (value != null ? JSON.toJSONString(value) : "");
	}

}