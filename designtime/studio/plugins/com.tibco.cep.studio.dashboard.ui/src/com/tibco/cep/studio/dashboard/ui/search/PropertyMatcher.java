package com.tibco.cep.studio.dashboard.ui.search;

import java.util.regex.Pattern;

import com.tibco.cep.studio.dashboard.core.util.StringUtil;

public abstract class PropertyMatcher<T> {

	private String pattern;

	private Pattern compiledPattern;

	private boolean ignoreCase;

	public PropertyMatcher(String pattern, boolean ignoreCase) {
		this.pattern = pattern;
		this.ignoreCase = ignoreCase;
		if (StringUtil.isEmpty(this.pattern) == false) {
			String compensatedPattern = this.pattern.replace("*", ".*");
			if (this.ignoreCase == true) {
				compiledPattern = Pattern.compile(compensatedPattern+".*", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			}
			else {
				compiledPattern = Pattern.compile(compensatedPattern+".*");
			}
		}
		else {
			compiledPattern = null;
		}
	}

	public boolean match(T element) {
		if (compiledPattern == null) {
			return true;
		}
		String value = getPropertyValue(element);
		return compiledPattern.matcher(value).matches();
	}

	protected abstract String getPropertyValue(T element);

}
