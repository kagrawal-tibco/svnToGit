package com.tibco.cep.webstudio.client.model.ruletemplate;

import java.io.Serializable;
import java.util.List;

/**
 * Model for representing Symbols used across various entities i.e.
 * RT/RTI/Concept/Event, etc
 * 
 * @author Vikram Patil
 */
public class SymbolInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	private String alias;
	private List<SymbolInfo> containedSymbols;
	private DomainInfo domainInfo;

	public SymbolInfo() {
		super();
	}

	public SymbolInfo(String type, String alias) {
		this.setType(type);
		this.setAlias(alias);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<SymbolInfo> getContainedSymbols() {
		return this.containedSymbols;
	}

	public void setContainedSymbols(List<SymbolInfo> containedSymbols) {
		this.containedSymbols = containedSymbols;
	}
	
	public DomainInfo getDomainInfo() {
		return this.domainInfo;
	}

	public void setDomainInfo(DomainInfo domainInfo) {
		this.domainInfo = domainInfo;
	}
}
