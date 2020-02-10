package com.tibco.rta.query.filter.eval;

import java.util.ArrayList;
import java.util.List;

import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.LogicalFilter;

abstract class LogicalEval implements FilterEvaluator {

	protected List<FilterEvaluator> filterEvalsList = new ArrayList<FilterEvaluator>();
	
	public LogicalEval(LogicalFilter lf) {
		for (Filter f1 : lf.getFilters()) {
			FilterEvaluator fe = FilterEvaluatorFactory.createEvaluator(f1);
			filterEvalsList.add(fe);
		}

	}
	
	public void addFilterEval(FilterEvaluator filterEvals) {
		filterEvalsList.add(filterEvals);
	}
	
}