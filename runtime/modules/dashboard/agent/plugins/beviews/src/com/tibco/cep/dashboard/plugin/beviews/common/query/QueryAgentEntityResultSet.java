package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleConvertor;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.query.api.QueryResultSet;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.runtime.model.element.Concept;

public class QueryAgentEntityResultSet extends BaseResultSetImpl {

	private QueryResultSet resultSet;

	private TupleSchema tupleSchema;

	private Query query;

	protected QueryAgentEntityResultSet(BaseViewsQueryExecutorImpl queryExecutor, TupleSchema tupleSchema, Query query, QueryResultSet resultSet) {
		super(queryExecutor);
		this.query = query;
		this.resultSet = resultSet;
		this.tupleSchema = tupleSchema;
	}

	@Override
	public boolean next() throws QueryException {
		return resultSet.next();
	}

	@Override
	public Tuple getTuple() throws QueryException {
		return createTuple();
	}

	@Override
	public List<Tuple> getTuples(int count) throws QueryException {
		int i = 0;
		List<Tuple> tuples = new LinkedList<Tuple>();
		while (resultSet.next() == true && i < count) {
			tuples.add(createTuple());
			i++;
		}
		return tuples;
	}

	private Tuple createTuple() throws QueryException {
		Concept concept = (Concept) resultSet.getObject(0);
		try {
			return TupleConvertor.getInstance().convertToTuple(concept);
		} catch (FatalException e) {
			throw new QueryException("could not convert " + tupleSchema.getScopeName() + "@extId=" + concept.getExtId());
		}
	}

	@Override
	protected void doClose() throws Exception {
		//close the result set
		resultSet.close();
		//close the statement
		resultSet.getStatement().close();
		//close the query object
		query.close();
	}

}
