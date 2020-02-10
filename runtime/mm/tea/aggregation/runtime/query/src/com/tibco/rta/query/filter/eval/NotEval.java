package com.tibco.rta.query.filter.eval;

import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.EqFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.GEFilter;
import com.tibco.rta.query.filter.GtFilter;
import com.tibco.rta.query.filter.InFilter;
import com.tibco.rta.query.filter.LEFilter;
import com.tibco.rta.query.filter.LikeFilter;
import com.tibco.rta.query.filter.LtFilter;
import com.tibco.rta.query.filter.NEqFilter;
import com.tibco.rta.query.filter.NotFilter;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.runtime.model.MetricNode;


public class NotEval implements FilterEvaluator {

	NotFilter filter;
	
	FilterEvaluator baseEvaluator;
	
	public NotEval(NotFilter filter) {
		
		this.filter = filter;
		
		Filter baseFilter = this.filter.getBaseFilter();
		if (baseFilter instanceof EqFilter) {
			baseEvaluator = new EqEval((EqFilter) baseFilter);
		} else if (baseFilter instanceof NEqFilter) {
			baseEvaluator = new NEqEval((NEqFilter) baseFilter);
		} else if (baseFilter instanceof GEFilter) {
			baseEvaluator = new GEEval((GEFilter) baseFilter);
		} else if (baseFilter instanceof GtFilter) {
			baseEvaluator = new GtEval((GtFilter) baseFilter);
		} else if (baseFilter instanceof LEFilter) {
			baseEvaluator = new LEEval((LEFilter) baseFilter);
		} else if (baseFilter instanceof LtFilter) {
			baseEvaluator = new LtEval((LtFilter) baseFilter);
		} else if (baseFilter instanceof LikeFilter) {
			baseEvaluator = new LikeEval((LikeFilter) baseFilter);
		} else if (baseFilter instanceof InFilter) {
			baseEvaluator = new InEval((InFilter) baseFilter);
		} else if (baseFilter instanceof AndFilter) {
			baseEvaluator = new AndEval((AndFilter)baseFilter);
		} else if (baseFilter instanceof OrFilter) {
			baseEvaluator = new OrEval((OrFilter)baseFilter);
		} else if (baseFilter instanceof NotFilter) {
			baseEvaluator = new NotEval((NotFilter) baseFilter);
		} 
		
	}
	@Override
	public boolean eval(MetricNode node) throws Exception {
		//not the return value of the base filter
		return ! baseEvaluator.eval(node);
	}

}
