package com.tibco.cep.studio.decision.table.ui.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * 
 * @author smahajan
 *
 */
public class DTRulePropertyFilter implements IFilter{

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
