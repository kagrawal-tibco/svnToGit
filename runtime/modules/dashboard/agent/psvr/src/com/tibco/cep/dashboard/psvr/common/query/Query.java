package com.tibco.cep.dashboard.psvr.common.query;

public interface Query {

	public String getQuery();

	public QueryParams getParameters();

	public String bindQuery();

}
