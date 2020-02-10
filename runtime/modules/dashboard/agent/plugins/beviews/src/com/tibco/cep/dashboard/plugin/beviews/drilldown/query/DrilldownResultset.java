package com.tibco.cep.dashboard.plugin.beviews.drilldown.query;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;

/**
 * @author RGupta
 * 
 */
public abstract class DrilldownResultset {
	
	protected ResultSet resultSet;
	
//	protected String toTypeId;
//	
//	protected String toInstanceId;
//	
//	protected String fromTypeId;

	protected DrilldownResultset(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public String getResultSetId() {
		return resultSet.getId();
	}
	
	public abstract boolean nextTuple() throws QueryException;

	public abstract boolean previousTuple() throws QueryException;

	public abstract Tuple getTuple() throws QueryException;

	public abstract void startNext();

	public abstract void stopNext();

	public abstract void startPrevious();

	public abstract void stopPrevious();

	public abstract int getNextIndex();

	public abstract int getPrevIndex();



}
