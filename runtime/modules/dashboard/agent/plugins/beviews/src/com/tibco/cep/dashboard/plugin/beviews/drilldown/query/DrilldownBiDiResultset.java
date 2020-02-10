package com.tibco.cep.dashboard.plugin.beviews.drilldown.query;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;

/**
 * @author RGupta
 * 
 */
public class DrilldownBiDiResultset extends DrilldownResultset {

	private List<Tuple> lstTuples;

	private ListIterator<Tuple> listIterator;

	private Tuple currentTuple;

	private int prevIndex;

	private int nextIndex;

	DrilldownBiDiResultset(ResultSet resultSet) {
		super(resultSet);
		lstTuples = new ArrayList<Tuple>();
		listIterator = lstTuples.listIterator();
	}

	public boolean nextTuple() throws QueryException {
		if (listIterator.hasNext()) {
			// Present in the iterator, means user has done previos
			// return it from cache and set the current tuple
			currentTuple = listIterator.next();
			return true;
		}
		if (resultSet.next()) {
			// Get it from cursor
			currentTuple = resultSet.getTuple();
			listIterator.add(currentTuple);
			return true;
		}
		// No more tuples in cursor. So close the cursor, we have got everything now in memory in lstTuples ArrayList
		resultSet.close();
		return false;
	}

	/**
	 * @param resultSet
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public boolean previousTuple() throws QueryException {
		if (listIterator.hasPrevious()) {
			currentTuple = listIterator.previous();
			return true;
		}
		return false;
	}

	/**
	 * @param resultSet
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public Tuple getTuple() throws QueryException {
		if (currentTuple == null) {
			throw new RuntimeException("Make a call to either nextTuple or previosTuple");
		}
		return currentTuple;
	}

	public void startNext() {
		// Move the iterator to the position of marked nextIndex
		while (nextIndex > listIterator.nextIndex()) {
			listIterator.next();
		}
		prevIndex = listIterator.previousIndex();
	}

	public void stopNext() {
		nextIndex = listIterator.nextIndex();
	}

	public void startPrevious() {
		// Move the iterator to the position of marked prevIndex
		while (prevIndex < listIterator.previousIndex()) {
			listIterator.previous();
		}
		nextIndex = listIterator.nextIndex();
	}

	public void stopPrevious() {
		prevIndex = listIterator.previousIndex();
	}

	public int getNextIndex() {
		return nextIndex;
	}

	public int getPrevIndex() {
		return prevIndex;
	}
}
