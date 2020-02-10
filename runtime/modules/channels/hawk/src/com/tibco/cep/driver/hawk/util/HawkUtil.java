package com.tibco.cep.driver.hawk.util;

import com.tibco.cep.driver.hawk.HawkConstants;

public class HawkUtil {

	public static String formatEventName(String originalStr) {
		String newString = originalStr
				.replaceAll(HawkConstants.ESCAPE_CHAR_PERCENT, HawkConstants.REPLACE_CHAR_PERCENT)
				.replaceAll(HawkConstants.ESCAPE_CHAR_SLASH, HawkConstants.REPLACE_CHAR_SLASH)
				.replace(HawkConstants.ESCAPE_CHAR_SPACE, HawkConstants.REPLACE_CHAR_SPACE)
				.replace(HawkConstants.ESCAPE_CHAR_AND, HawkConstants.REPLACE_CHAR_AND);
		char[] chars = newString.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			if ((c > Integer.valueOf('0') - 1 && c < Integer.valueOf('9') + 1)
					|| (c > Integer.valueOf('a') - 1 && c < Integer.valueOf('z') + 1)
					|| (c > Integer.valueOf('A') - 1 && c < Integer.valueOf('Z') + 1) || c == '_') {
				sb.append(c);
			}

		}
		return sb.toString();
	}

}
