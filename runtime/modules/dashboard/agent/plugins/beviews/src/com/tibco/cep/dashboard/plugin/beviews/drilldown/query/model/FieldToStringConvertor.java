package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

class FieldToStringConvertor {

	static String convert(String alias, String fieldName) {
		StringBuilder sb = new StringBuilder();
		if (alias != null) {
			sb.append(alias);
			if (fieldName.startsWith("@") == false) {
				sb.append(".");
			}
		}
		sb.append(KeywordEscaper.escapeKeyword(fieldName));
		return sb.toString();
	}

}
