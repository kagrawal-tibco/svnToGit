package com.tibco.cep.dashboard.psvr.data;

public class DataSourceHandlerRuntimeInfo {
	
	private String id;
	
	private String name;
	
	private String uniqueName;
	
	private String owner;
	
	private String source;
	
	private String query;
	
	private String queryParams;
	
	private String bindedQuery;
	
	private String threshold;
	
	private String[] referencers;

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getUniqueName() {
		return uniqueName;
	}

	public final void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public final String getOwner() {
		return owner;
	}

	public final void setOwner(String owner) {
		this.owner = owner;
	}

	public final String getSource() {
		return source;
	}

	public final void setSource(String source) {
		this.source = source;
	}

	public final String getQuery() {
		return query;
	}

	public final void setQuery(String query) {
		this.query = query;
	}
	
	public String getBindedQuery() {
		return bindedQuery;
	}
	
	public void setBindedQuery(String bindedQuery) {
		this.bindedQuery = bindedQuery;
	}

	public final String getQueryParams() {
		return queryParams;
	}

	public final void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public final String getThreshold() {
		return threshold;
	}

	public final void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public final String[] getReferencers() {
		return referencers;
	}

	public final void setReferencers(String[] referencers) {
		this.referencers = referencers;
	}

}
