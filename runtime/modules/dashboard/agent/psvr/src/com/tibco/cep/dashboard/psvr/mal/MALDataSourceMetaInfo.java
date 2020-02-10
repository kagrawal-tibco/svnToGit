package com.tibco.cep.dashboard.psvr.mal;

import java.util.HashMap;
import java.util.List;

/**
 * @deprecated
 * @author anpatil
 *
 */
public final class MALDataSourceMetaInfo extends HashMap<String, Object>{

	private static final long serialVersionUID = -4684907353107721279L;

	public static final String QUERY_KEY = "query";

	public static final String DATASETLIMIT_KEY = "datasetlimit";

	public static final String OUTPUTFIELDS_KEY = "outputfields";

	public static final String DRILLABLEFIELDS_KEY = "drillablefields";

	public static final String OWNER_KEY = "owner";

	public static final String NAME_KEY = "name";

	public final String getQuery() {
		return (String) get(QUERY_KEY);
	}

	public final void setQuery(String query) {
		put(QUERY_KEY,query);
	}

	public final long getDataSetLimit() {
		return (Long) get(DATASETLIMIT_KEY);
	}

	public final void setDataSetLimit(long dataSetLimit) {
		put(DATASETLIMIT_KEY, dataSetLimit);
	}

	@SuppressWarnings("unchecked")
	public final List<MALFieldMetaInfo> getOutputFields() {
		return (List<MALFieldMetaInfo>) get(OUTPUTFIELDS_KEY);
	}

	public final void setOutputFields(List<MALFieldMetaInfo> outputFields) {
		put(OUTPUTFIELDS_KEY,outputFields);
	}

	@SuppressWarnings("unchecked")
	public final List<MALFieldMetaInfo> getDrillableFields() {
		return (List<MALFieldMetaInfo>) get(DRILLABLEFIELDS_KEY);
	}

	public final void setDrillableFields(List<MALFieldMetaInfo> drillableFields) {
		put(DRILLABLEFIELDS_KEY,drillableFields);
	}

	public final String getOwner() {
		return (String) get(OWNER_KEY);
	}

	public final void setOwner(String owner) {
		put(OWNER_KEY,owner);
	}

	public final String getName() {
		return (String) get(NAME_KEY);
	}

	public final void setName(String name) {
		put(NAME_KEY,name);
	}

}