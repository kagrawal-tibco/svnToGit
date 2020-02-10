package com.tibco.cep.webstudio.client.decisiontable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author sasahoo
 *
 */
public class Domain {

	private List<DomainEntry> entries = new ArrayList<DomainEntry>();

	public Domain() {
	}

	public List<DomainEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<DomainEntry> entries) {
		this.entries = entries;
	}
	
}
