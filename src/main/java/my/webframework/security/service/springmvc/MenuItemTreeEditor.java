package my.webframework.security.service.springmvc;

import java.beans.PropertyEditorSupport;

import my.webframework.security.domain.MenuItem;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class MenuItemTreeEditor extends PropertyEditorSupport {

	private final boolean allowEmpty;

	public MenuItemTreeEditor(boolean allowEmpty) {
		super();
		this.allowEmpty = allowEmpty;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		}
		MenuItem o = JSON.parseObject(text, MenuItem.class);
		try {
			setValue(o);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Could not parse MenuItem: "
					+ ex.getMessage(), ex);
		}
	}

	@Override
	public String getAsText() {
		MenuItem value = (MenuItem) getValue();
		return (value != null ? JSON.toJSONString(value) : "");
	}

}