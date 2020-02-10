package com.tibco.rta.query.impl;

import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.impl.QueryFactory;

/**
 * @author vdhumal
 *
 */
public class QueryFactoryEx extends QueryFactory {

	public static final QueryFactoryEx INSTANCE = new QueryFactoryEx();
	
	@Override
	public QueryByFilterDef newQueryByFilterDef(String schemaName, String cubeName, String hierarchyName,
			String measurementName) {
		return new QueryFilterDefExImpl(schemaName, cubeName, hierarchyName, measurementName);
	}

	@Override	
	public QueryByKeyDef newQueryByKeyDef() {
		return new QueryKeyDefExImpl();
	}

}
