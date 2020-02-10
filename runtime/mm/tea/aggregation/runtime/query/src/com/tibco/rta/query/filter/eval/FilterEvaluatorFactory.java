package com.tibco.rta.query.filter.eval;

import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
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
import com.tibco.rta.query.filter.RelationalFilter;
import com.tibco.rta.query.filter.impl.AndFilterImpl;
import com.tibco.rta.query.filter.impl.EqFilterImpl;
import com.tibco.rta.query.filter.impl.GtFilterImpl;
import com.tibco.rta.query.filter.impl.LEFilterImpl;
import com.tibco.rta.query.filter.impl.LogicalFilterImpl;
import com.tibco.rta.query.filter.impl.OrFilterImpl;
import com.tibco.rta.runtime.model.MetricNode;

abstract public class FilterEvaluatorFactory {
	
	protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_QUERY.getCategory());
	
	
	public static FilterEvaluator createEvaluator (Filter filter) {
		
		FilterEvaluator f = null;
		if (filter instanceof EqFilter) {
			f = new EqEval((EqFilter) filter);
		} else if (filter instanceof NEqFilter) {
			f = new NEqEval((NEqFilter) filter);
		} else if (filter instanceof GEFilter) {
			f = new GEEval((GEFilter) filter);
		} else if (filter instanceof GtFilter) {
			f = new GtEval((GtFilter) filter);
		} else if (filter instanceof LEFilter) {
			f = new LEEval((LEFilter) filter);
		} else if (filter instanceof LtFilter) {
			f = new LtEval((LtFilter) filter);
		} else if (filter instanceof LikeFilter) {
			f = new LikeEval((LikeFilter)filter);
		} else if (filter instanceof InFilter) {
			f = new InEval((InFilter)filter);
		} else if (filter instanceof AndFilter) {
			f = new AndEval(((AndFilter)filter));
		} else if (filter instanceof OrFilter) {
			f = new OrEval(((OrFilter)filter));
		} else if (filter instanceof NotFilter) {
			f = new NotEval((NotFilter)filter);
		}

		return f;
	}
	
	abstract public boolean eval(MetricNode node) throws Exception;

	public static void main(String[] args) {
		
		RelationalFilter f = new EqFilterImpl();
		RelationalFilter f1 = new GtFilterImpl();
		LogicalFilterImpl lf = new AndFilterImpl();
		lf.addFilter(f);
		lf.addFilter(f1);
		LogicalFilterImpl lf1 = new OrFilterImpl();
		RelationalFilter f3 = new LEFilterImpl();
		lf1.addFilter(f3);
		lf1.addFilter(lf);
//		LikeFilter likeFilter = new LikeFilterImpl();
//		InFilter inFilter = new InFilterImpl();
//		inFilter.addValue("a", "b", "c");

		System.out.println("before: " + lf1);
		
		FilterEvaluator f2 = FilterEvaluatorFactory.createEvaluator (lf1);
		
		System.out.println(lf1);
		System.out.println(f2);
		
	}
	
	void onChange (MetricNode node) {
		
	}


}
