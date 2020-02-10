package com.tibco.cep.webstudio.client.decisiontable.constraint;

import java.util.Collection;
import java.util.LinkedHashSet;



public class NotEqualsFilter extends EqualsFilter {

	public NotEqualsFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkOperator(byte operatorKind) {
		if (operatorKind != Operator.NOTEQ_OPERATOR) {
    		return false;
    	}
    	return true;
	}

	@Override
	/**
	 * Here we have to return values for this key
	 * but put a further filter on the available cells
	 * such that only those cells which do not have this
	 * key as body are returned.
	 */
	public Collection<Cell> getFilteredValues(Object key) {
		Collection<LinkedHashSet<Cell>> allValues = map.values();
		Collection<Cell> filteredValues = new LinkedHashSet<Cell>();
		
		if (key instanceof String) {
			String stringKey = (String)key;
			//Parse expression
			Expression keyExpression = new Expression(stringKey);
			try {
				keyExpression.parse();
				for (LinkedHashSet<Cell> valueSet : allValues) {
					//Check if key matches
					//If operand and expression in key match do not add
					for (Cell value : valueSet) {
						if (keyExpression.getOperands()[0].equals(value.getExpression().getOperands()[0])) {
							continue;
						}
						filteredValues.add(value);
					}
				}
			} catch (Exception e) {
			}
		}
		return filteredValues;
	}
}
