package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.utils.SUID;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;

public class TupleBasedResultSetImpl implements ResultSet {

	List<Tuple> tuples;
	private int idx;
	private String id;

	public TupleBasedResultSetImpl(List<Tuple> tuples) {
		id = SUID.createId().toString();
		this.tuples = new LinkedList<Tuple>(tuples);
		idx = -1;
	}

	@Override
	public void close() throws QueryException {
		tuples.clear();
		idx = -1;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Tuple getTuple() throws QueryException {
		if (idx == -1) {
			throw new QueryException("Incorrect position of cursor, call next before calling get tuple");
		}
		if (idx >= tuples.size()) {
			throw new QueryException("end of result set reached");
		}
		return tuples.get(idx);
	}

	@Override
	public List<Tuple> getTuples(int count) throws QueryException {
		if (idx == -1) {
			throw new QueryException("Incorrect position of cursor, call next before calling get tuple");
		}
		if (idx >= tuples.size()) {
			throw new QueryException("end of result set reached");
		}
		int endIdx = idx + count;
		if (endIdx >= tuples.size()) {
			endIdx = tuples.size() - 1;
		}
		return new ArrayList<Tuple>(tuples.subList(idx, endIdx));
	}

	@Override
	public boolean next() throws QueryException {
		idx++;
		return idx < tuples.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getName());
		sb.append("[id=");
		sb.append(id);
		sb.append("]");
		return sb.toString();
	}

}