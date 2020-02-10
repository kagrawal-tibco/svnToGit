package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tibco.be.functions.cluster.DataGridFunctions;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleConvertor;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class ConceptListResultSet extends BaseResultSetImpl {

	private List<Concept> concepts;

	private Iterator<Concept> iterator;

	private Concept currentConcept;

	private ServiceContext serviceContext;

	protected ConceptListResultSet(ServiceContext serviceContext, BaseViewsQueryExecutorImpl queryExecutor, List<Concept> concepts) {
		super(queryExecutor);
		this.serviceContext = serviceContext;
		this.concepts = concepts;
		this.iterator = this.concepts.iterator();
	}

	@Override
	public boolean next() throws QueryException {
		boolean hasNext = iterator.hasNext();
		if (hasNext == true) {
			currentConcept = iterator.next();
		}
		else {
			currentConcept = null;
		}
		return hasNext;
	}

	@Override
	public Tuple getTuple() throws QueryException {
		if (currentConcept == null) {
			return null;
		}
		RuleSession currRuleSession = RuleSessionManager.currentRuleSessions.get();
		try {
			RuleSessionManager.currentRuleSessions.set(serviceContext.getRuleSession());
			//reload the concept from cache
			DataGridFunctions.CacheLoadEntity(currentConcept);
			return TupleConvertor.getInstance().convertToTuple(currentConcept);
		} catch (FatalException e) {
			throw new QueryException(e);
		} finally {
			RuleSessionManager.currentRuleSessions.set(currRuleSession);
		}
	}

	@Override
	public List<Tuple> getTuples(int count) throws QueryException {
		List<Tuple> tuples = new LinkedList<Tuple>();
		int i = 0;
		while (next() == true && i < count) {
			tuples.add(getTuple());
			i++;
		}
		return tuples;
	}

	@Override
	protected void doClose() throws Exception {
		concepts.clear();
		iterator = concepts.iterator();
	}

}
