package com.tibco.cep.decision.table.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableRulePropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) toTest;
			toTest = selection.getFirstElement();
		}
		if(toTest instanceof TableRuleVariable){
			return true;
		}
		return false;
	}
}