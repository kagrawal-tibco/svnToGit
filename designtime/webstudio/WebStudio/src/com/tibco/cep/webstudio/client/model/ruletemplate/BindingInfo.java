package com.tibco.cep.webstudio.client.model.ruletemplate;

import java.io.Serializable;

/**
 * Model for storing Rule Template Instance Bindings
 * 
 * @author Vikram Patil
 */
public class BindingInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String type;
	private String value;
	private DomainInfo domainInfo;
	private String style;

	public BindingInfo() {
		super();
	}

	public BindingInfo(String id, String type, String value, DomainInfo domainInfo) {
		this.setId(id);
		this.setType(type);
		this.setValue(value);
		this.setDomainInfo(domainInfo);
		this.setStyle("");
	}

	public String getId() {
		return this.id;
	}

	public void setId(String type) {
		this.id = type;
	}

	public DomainInfo getDomainInfo() {
		return this.domainInfo;
	}

	public void setDomainInfo(DomainInfo domainInfo) {
		this.domainInfo = domainInfo;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
