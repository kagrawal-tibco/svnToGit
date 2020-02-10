package com.tibco.cep.webstudio.client.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Domain {

	private String name;
	private String folder;
	private String description;
	private String ownerProjectName;
	private String superDomainPath;
	private String namespace;
	private String dataType;
	
	private List<DomainEntry> domainEntries;

	public Domain() {
		description = "";		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwnerProjectName() {
		return ownerProjectName;
	}

	public void setOwnerProjectName(String ownerProjectName) {
		this.ownerProjectName = ownerProjectName;
	}

	public String getSuperDomainPath() {
		return superDomainPath;
	}

	public void setSuperDomainPath(String superDomainPath) {
		this.superDomainPath = superDomainPath;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void addDomainEntry(DomainEntry entry) {
		if (domainEntries == null) {
			domainEntries = new ArrayList<DomainEntry>();
		}
		if (!domainEntries.contains(entry)) {
			domainEntries.add(entry);
		}	
	}

	public List<DomainEntry> getDomainEntries() {
		if (domainEntries == null)
			domainEntries = new ArrayList<DomainEntry>();
		return Collections.unmodifiableList(domainEntries);
	}

	public void removeAllDomainEntries() {
		if (domainEntries == null)
			domainEntries = new ArrayList<DomainEntry>();
		else
			domainEntries.clear();
	}
	
}
