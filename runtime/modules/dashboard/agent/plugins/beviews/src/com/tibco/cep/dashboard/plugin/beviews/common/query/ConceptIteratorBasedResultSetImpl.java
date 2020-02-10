package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleConvertor;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.runtime.model.element.Concept;

@SuppressWarnings("rawtypes")
class ConceptIteratorBasedResultSetImpl extends BaseResultSetImpl {

	private Iterator iterator;

	private TupleConvertor tupleConvertor;

	ConceptIteratorBasedResultSetImpl(BackingStoreQueryExecutorImpl queryExecutor, Iterator iterator){
		super(queryExecutor);
		this.iterator = iterator;
		if (this.iterator == null){
			this.iterator = Collections.EMPTY_LIST.iterator();
		}
		tupleConvertor = TupleConvertor.getInstance();
	}

	@Override
	public Tuple getTuple() throws QueryException {
		try {
			return tupleConvertor.convertToTuple((Concept) iterator.next());
		} catch (FatalException e) {
			throw new QueryException(e.getMessage(),e);
		}
	}

	@Override
	public List<Tuple> getTuples(int count) throws QueryException {
		try {
			List<Concept> conceptList = new ArrayList<Concept>(count);
			int i = 0;
			while (next() && i < count){
				conceptList.add((Concept) iterator.next());
				i++;
			}
			//Modified by Anand to fix BE-12039
			Concept[] concepts = conceptList.toArray(new Concept[conceptList.size()]);
			TupleConvertor tupleConvertor = TupleConvertor.getInstance();
			return tupleConvertor.convertToTuple(concepts);
		} catch (FatalException e) {
			throw new QueryException(e.getMessage(),e);
		}
	}

	@Override
	public boolean next() throws QueryException {
		try {
			return iterator.hasNext();
		} catch (RuntimeException e) {
			if (e.getCause() instanceof SQLException){
				return false;
			}
			throw e;
		}
	}

	@Override
	protected void doClose() throws Exception {
		//before we close, we have to run through the iterator
		//till we don't have any elements. Thats is the only way
		//to force the db connection used to be closed
		try {
			while (iterator.hasNext()) {
				//do nothing
			}
		} catch (RuntimeException e) {
			//the underlying com.tibco.be.jdbcstore.impl.ConceptsWithVersionIterator does crazy things
			//SQLExceptions thrown as runtime exception
			//do nothing
		}
		//close the iterator result sets & statements
		((BackingStoreQueryExecutorImpl)queryExecutor).queryManager.closeQueryResult(iterator);
	}

}