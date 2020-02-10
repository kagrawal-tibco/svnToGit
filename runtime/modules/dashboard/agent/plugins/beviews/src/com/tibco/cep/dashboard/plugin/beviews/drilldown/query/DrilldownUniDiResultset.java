package com.tibco.cep.dashboard.plugin.beviews.drilldown.query;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;

/**
 * @author RGupta
 * 
 */
class DrilldownUniDiResultset extends DrilldownResultset {

	private Tuple currentTuple;
	private int nextIndex = 0;

	protected DrilldownUniDiResultset(ResultSet resultSet) {
		super(resultSet);
	}
	
	public boolean nextTuple() throws QueryException {
		if (resultSet.next()) {
			// Get it from cursor
			currentTuple = resultSet.getTuple();
			nextIndex++;
			return true;
		}
		// No more tuples in cursor. So close the cursor, we have got everything now in memory in lstTuples ArrayList
		resultSet.close();
		return false;
	}

	public boolean previousTuple() throws QueryException {
		return false;
	}

	public Tuple getTuple() throws QueryException {
		if (currentTuple == null) {
			throw new RuntimeException("Make a call to nextTuple");
		}
		return currentTuple;
	}

	public void startNext() {
	}

	public void stopNext() {
	}

	public void startPrevious() {
	}

	public void stopPrevious() {
	}

	public int getNextIndex() {
		return nextIndex;
	}

	public int getPrevIndex() {
		return -1;
	}
}
