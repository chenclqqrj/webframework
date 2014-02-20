package my.webframework.security.service.springmvc;

import java.beans.PropertyEditorSupport;

import my.webframework.security.domain.Employee;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class EmployeeEditor extends PropertyEditorSupport {

	private final boolean allowEmpty;

	public EmployeeEditor(boolean allowEmpty) {
		super();
		this.allowEmpty = allowEmpty;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		}
		Employee o = JSON.parseObject(text, Employee.class);
		try {
			setValue(o);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Could not parse MenuItem: "
					+ ex.getMessage(), ex);
		}
	}

	@Override
	public String getAsText() {
		Employee value = (Employee) getValue();
		return (value != null ? JSON.toJSONString(value) : "");
	}

}