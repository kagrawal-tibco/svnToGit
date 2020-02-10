package com.tibco.cep.webstudio.client.i18n;

import java.util.Map;

/**
 * This class routes all DomainMessages properties. It enables the use of dynamic(created post war compile) properties files.<br/>
 * If the requested property is not found in custom properties it simply uses the property strings from locale property files.
 * 
 * @author moshaikh
 *
 */
public class DomainMessages implements I18nMessages {
	
	private Map<String, String> domainMessages;

	public DomainMessages(Map<String, String> domainMessages) {
		this.domainMessages = domainMessages;
	}

	private String getPropertyValue(String key, String... replaceValues) {
		return GlobalMessages.getPropertyValue(domainMessages, key, replaceValues);
	}
	
	public String button_duplicate() {
		return getPropertyValue("button.duplicate");
	}
	
	public String button_browse() {
		return getPropertyValue("button.browse");
	}
	
	public String text_value() {
		return getPropertyValue("text_value");
	}
	
	public String text_domainType() {
		return getPropertyValue("text_domainType");
	}
	
	public String text_inheritsFrom() {
		return getPropertyValue("text_inheritsFrom");
	}
	
	public String text_domainEntries() {
		return getPropertyValue("text_domainEntries");
	}
	
	public String text_details() {
		return getPropertyValue("text_details");
	}
	
	public String text_lower() {
		return getPropertyValue("text_lower");
	}
	
	public String text_upper() {
		return getPropertyValue("text_upper");
	}
	
	public String text_included() {
		return getPropertyValue("text_included");
	}
	
	public String text_simpleString() {
		return getPropertyValue("text_simpleString");
	}
	
	public String text_simpleNumber() {
		return getPropertyValue("text_simpleNumber");
	}
	
	public String text_long() {
		return getPropertyValue("text_long");
	}
	
	public String text_double() {
		return getPropertyValue("text_double");
	}
	
	public String text_date() {
		return getPropertyValue("text_date");
	}
	
	public String text_boolean() {
		return getPropertyValue("text_boolean");
	}
	
	public String text_selectDomain() {
		return getPropertyValue("text_selectDomain");
	}
	
	public String msg_empty_details() {
		return getPropertyValue("msg_empty_details");
	}
	
	public String msg_empty_selectDomain() {
		return getPropertyValue("msg_empty_selectDomain");
	}
	
	public String msg_invalid_domain_entry(String value, String type) {
		return getPropertyValue("msg_invalid_domain_entry", value, type);
	}
	
	public String msg_invalid_range_entry(String lower, String upper,
			String type) {
		return getPropertyValue("msg_invalid_range_entry", lower, upper, type);
	}
	
	public String msg_duplicate_domain_entries() {
		return getPropertyValue("msg_duplicate_domain_entries");
	}
	
	public String msg_duplicate_domain_entries_format() {
		return getPropertyValue("msg_duplicate_domain_entries_format");
	}
	
	public String radioGroupItem_single() {
		return getPropertyValue("radioGroupItem_single");
	}
	
	public String radioGroupItem_range() {
		return getPropertyValue("radioGroupItem_range");
	}
}
