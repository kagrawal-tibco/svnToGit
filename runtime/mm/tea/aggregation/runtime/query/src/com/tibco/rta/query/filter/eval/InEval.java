package com.tibco.rta.query.filter.eval;

import java.util.ArrayList;
import java.util.List;

import com.tibco.rta.MetricKey;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.InFilter;
import com.tibco.rta.runtime.model.MetricNode;

public class InEval extends RelationalEval {

	List<Object> values = new ArrayList<Object>();

	public InEval(InFilter filter) {
		super(filter);
		this.values = filter.getInSet();
	}

	@Override
	public boolean eval(MetricNode node) throws Exception {

		MetricQualifier qualifier = filter.getMetricQualifier();

		MetricKey key = (MetricKey) node.getKey();

		if (qualifier != null) {
			switch (qualifier) {


			case DIMENSION_LEVEL:
				Object value = key.getDimensionLevelName();
				return isInList(value, values);
			default:
				return false;
			}
		}
		for (Object value : values) {
			if (super.evaluate(node, RelationalOperator.EQUALS, filter.getKeyQualifier(), filter.getKey(), value)) {
				return true;
			}
		}
		return false;
	}

}