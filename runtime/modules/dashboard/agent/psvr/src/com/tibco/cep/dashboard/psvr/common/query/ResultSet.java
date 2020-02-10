package com.tibco.cep.dashboard.psvr.common.query;

import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;

/**
 * @author anpatil
 *
 */
public interface ResultSet {
	
	public String getId();
	
	public boolean next() throws QueryException;
	
	public Tuple getTuple() throws QueryException;
	
	public List<Tuple> getTuples(int count) throws QueryException;
	
	public void close() throws QueryException;

}
