package com.tibco.rta.query.filter.eval;

import com.tibco.rta.MetricKey;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.EqFilter;
import com.tibco.rta.runtime.model.MetricNode;

class EqEval extends RelationalEval {

	public EqEval(EqFilter filter) {
		super(filter);
	}

	@Override
	public boolean eval(MetricNode node) throws Exception {

		MetricQualifier qualifier = filter.getMetricQualifier();

		MetricKey key = (MetricKey) node.getKey();

		if (qualifier != null) {
			switch (qualifier) {

			// case CUBE_NAME:
			// return key.getCubeName().equals(eqFilter.getValue());
			// case HIERARCHY_NAME:
			// return key.getDimensionHierarchyName().equals(
			// eqFilter.getValue());
			// case SCHEMA_NAME:
			// return key.getSchemaName().equals(eqFilter.getValue());

			case DIMENSION_LEVEL:
				return key.getDimensionLevelName().equals(filter.getValue());
			default:
				return false;
			}
		} else if (filter.getKeyQualifier() != null) {
			return super.evaluate(node, RelationalOperator.EQUALS,
					filter.getKeyQualifier(), filter.getKey(),
					filter.getValue());
		}

		return false;

	}
	
	public String toString() {
		return "EQE: ";
	}
}