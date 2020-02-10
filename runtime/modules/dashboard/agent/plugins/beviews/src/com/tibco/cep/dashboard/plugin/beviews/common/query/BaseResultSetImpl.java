package com.tibco.cep.dashboard.plugin.beviews.common.query;

import com.tibco.cep.dashboard.common.utils.SUID;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;

abstract class BaseResultSetImpl implements ResultSet {

	protected String id;

	protected BaseViewsQueryExecutorImpl queryExecutor;

	protected BaseResultSetImpl(BaseViewsQueryExecutorImpl queryExecutor){
		id = SUID.createId().toString();
		this.queryExecutor = queryExecutor;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void close() throws QueryException {
		try {
			//INFO we remove results from open set b4 closing it
			queryExecutor.unregisterResultSet(id);
			doClose();
		} catch (Exception e) {
			throw new QueryException("could not close "+this,e);
		}
	}

	protected abstract void doClose() throws Exception;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getName());
		sb.append("[id=");
		sb.append(id);
		sb.append("]");
		return sb.toString();
	}

}
