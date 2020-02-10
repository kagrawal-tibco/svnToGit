/**
 * 
 */
package com.tibco.be.ws.functions.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vpatil
 */
public enum KEYWORD_REPLACEMENT_MAP {
	
	PROCESS("process", "processDef"),
	GROUP("group", "groupInfo"),
	event("event", "eventURI"),
	KEY("key", "dispkey");
	
	private String restrictedKeyword;
	private String replacementKeyword;
	
	private KEYWORD_REPLACEMENT_MAP(String keyword, String replacement) {
		this.restrictedKeyword = keyword;
		this.replacementKeyword = replacement;
	}

	public String getRestrictedKeyword() {
		return restrictedKeyword;
	}

	public String getReplacementKeyword() {
		return replacementKeyword;
	}
	
	public static List<String> getRestrictedKeywordList() {
		List<String> restrictedKeywords = new ArrayList<String>();
		for (KEYWORD_REPLACEMENT_MAP keyword : values()) {
			restrictedKeywords.add(keyword.getRestrictedKeyword());
		}
		return restrictedKeywords;
	}
	
	public static String getReplacementIfExists(String restrictedKeyword) {
		String replacement = null;
		for (KEYWORD_REPLACEMENT_MAP keyword : values()) {
			if (keyword.getRestrictedKeyword().equals(restrictedKeyword)) {
				replacement = keyword.getReplacementKeyword();
				break;
			}
		}
		return replacement;
	}
	
	public static List<String> getReplacementKeywordList() {
		List<String> replacementKeywords = new ArrayList<String>();
		for (KEYWORD_REPLACEMENT_MAP keyword : values()) {
			replacementKeywords.add(keyword.getReplacementKeyword());
		}
		return replacementKeywords;
	}
	
	public static String getRestrictedIfExists(String replacementKeyword) {
		String restricted = null;
		for (KEYWORD_REPLACEMENT_MAP keyword : values()) {
			if (keyword.getReplacementKeyword().equals(replacementKeyword)) {
				restricted = keyword.getRestrictedKeyword();
				break;
			}
		}
		return restricted;
	}

}
