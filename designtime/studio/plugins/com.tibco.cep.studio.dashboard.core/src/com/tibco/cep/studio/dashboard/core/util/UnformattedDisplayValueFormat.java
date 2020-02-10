package com.tibco.cep.studio.dashboard.core.util;

public class UnformattedDisplayValueFormat extends DisplayValueFormat {

	public UnformattedDisplayValueFormat() {
		super("Unformatted", null, null);
	}

	@Override
	public String getDisplayValueFormat(String fieldName, String pattern) {
		return null;
	}

	@Override
	public String getPattern(String displayValueFormat) {
		return "";
	}

	@Override
	public boolean isPattern() {
		return false;
	}

}