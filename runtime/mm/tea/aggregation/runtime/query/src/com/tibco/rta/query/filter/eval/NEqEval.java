/**
 * 
 */
package com.tibco.rta.query.filter.eval;

import com.tibco.rta.MetricKey;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.NEqFilter;
import com.tibco.rta.runtime.model.MetricNode;

/**
 * @author ssinghal
 *
 */
public class NEqEval extends RelationalEval{
	
	public NEqEval(NEqFilter filter) {
		super(filter);
	}
	
	@Override
	public boolean eval(MetricNode node) throws Exception {

		MetricQualifier qualifier = filter.getMetricQualifier();

		MetricKey key = (MetricKey) node.getKey();

		if (qualifier != null) {
			switch (qualifier) {

				case DIMENSION_LEVEL:
					return !(key.getDimensionLevelName().equals(filter.getValue()));
				default:
					return false;
			}
		} else if (filter.getKeyQualifier() != null) {
			return super.evaluate(node, RelationalOperator.NEQUALS,
					filter.getKeyQualifier(), filter.getKey(),
					filter.getValue());
		}

		return false;

	}
	
	public String toString() {
		return "NEQE: ";
	}

}
