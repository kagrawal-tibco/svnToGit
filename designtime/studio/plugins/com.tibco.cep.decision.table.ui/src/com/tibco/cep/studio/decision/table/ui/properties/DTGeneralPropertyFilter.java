package com.tibco.cep.studio.decision.table.ui.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class DTGeneralPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) toTest;
			toTest = selection.getFirstElement();
		}
		if(toTest instanceof Table){
			return true;
		}
		
		if(toTest instanceof TableRuleVariable){
			return true;
		}
		
		if(toTest instanceof Integer){
			return true;
		}
		
		return false;
	}
}