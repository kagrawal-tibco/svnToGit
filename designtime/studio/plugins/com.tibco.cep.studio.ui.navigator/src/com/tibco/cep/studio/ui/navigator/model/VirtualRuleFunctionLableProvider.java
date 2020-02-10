/**
 * 
 */
package com.tibco.cep.studio.ui.navigator.model;

import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider;

/**
 * @author aathalye
 *
 */
public class VirtualRuleFunctionLableProvider extends EntityLabelProvider {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof VirtualRuleFunctionImplementationNavigatorNode) {
			return StudioUIPlugin.getDefault().getImage("icons/vf_implements.png");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof VirtualRuleFunctionImplementationNavigatorNode) {
			DecisionTableElement decisionTableElement = ((VirtualRuleFunctionImplementationNavigatorNode)element).getDecisionTableElement();
			return " "+decisionTableElement.getFolder()+decisionTableElement.getName();
		}
		return null;
	}
}
