package com.tibco.cep.dashboard.psvr.data;

import com.tibco.cep.dashboard.psvr.common.query.QueryParams;

public class DataSourceHandlerKey {
	
	protected String query;
	
	protected QueryParams queryParams;
	
	protected Threshold threshold;

	public DataSourceHandlerKey(String query, QueryParams queryParams, Threshold threshold) {
		super();
		this.query = query;
		this.queryParams = queryParams;
		this.threshold = threshold;
	}

	public String getQuery() {
		return query;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public Threshold getThreshold() {
		return threshold;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result + ((queryParams == null) ? 0 : queryParams.hashCode());
		result = prime * result + ((threshold == null) ? 0 : threshold.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSourceHandlerKey other = (DataSourceHandlerKey) obj;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (queryParams == null) {
			if (other.queryParams != null)
				return false;
		} else if (!queryParams.equals(other.queryParams))
			return false;
		if (threshold == null) {
			if (other.threshold != null)
				return false;
		} else if (!threshold.equals(other.threshold))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("DataSourceHandlerKey[");
		buffer.append("query=");
		buffer.append(query);
		buffer.append(",params=");
		buffer.append(queryParams);
		buffer.append(",threshold=");
		buffer.append(threshold);
		buffer.append("]");
		return buffer.toString();
	};
	
}
