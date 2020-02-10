package com.tibco.cep.studio.decision.table.providers;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * @author vdhumal
 *
 */
public class DomainValueDataConverter extends MultiValueDataConverter {

	/**
	 * @param isMultiValue
	 * @param separator
	 */
	public DomainValueDataConverter(boolean isMultiValue, String separator) {
		super(isMultiValue, separator);
	}
	
	/**
	 * @param aobj
	 * @param b 
	 * @param string 
	 * @param propertyPath 
	 * @return the String representation of the Display values
	 */
	public String getDisplayValues(Object aobj, String projectname, Column col) {
		boolean isDomDesc = DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION);
		return DTDomainUtil.toCompleteString(aobj, 1, col, projectname, col.getColumnType() == ColumnType.CONDITION, isDomDesc);
	}

	/**
	 * @param aobj
	 * @param b 
	 * @param string 
	 * @param propertyPath 
	 * @return the String representation of the Expression values
	 */
	public String getExpressionValues(Object aobj, String projectname, Column col) {
		boolean isDomDesc = DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION);
		return DTDomainUtil.toCompleteString(aobj, 0, col, projectname, col.getColumnType() == ColumnType.CONDITION, isDomDesc);		
	}

}
