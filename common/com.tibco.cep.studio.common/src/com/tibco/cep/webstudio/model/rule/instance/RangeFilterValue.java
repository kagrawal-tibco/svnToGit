/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;


public interface RangeFilterValue extends FilterValue {
	
	public final int MIN_FEATURE_ID = 0;
	public final int MAX_FEATURE_ID = 1;

	String getMinValue();
	String getMaxValue();

	void setMinValue(String value);
	void setMaxValue(String value);

}
