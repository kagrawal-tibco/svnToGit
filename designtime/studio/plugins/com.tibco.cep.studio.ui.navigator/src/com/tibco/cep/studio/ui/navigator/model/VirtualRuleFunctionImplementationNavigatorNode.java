/**
 * 
 */
package com.tibco.cep.studio.ui.navigator.model;

import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.ui.StudioNavigatorNode;

/**
 * @author aathalye
 *
 */
public class VirtualRuleFunctionImplementationNavigatorNode extends StudioNavigatorNode {
	
	private DecisionTableElement decisionTableElement;
	private String projectName;

	public VirtualRuleFunctionImplementationNavigatorNode(final DecisionTableElement decisionTableElement, final String projectName) {
		this.decisionTableElement = decisionTableElement;
		this.projectName = projectName;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.model.StudioNavigatorNode#getName()
	 */
	@Override
	public String getName() {
		return decisionTableElement.getName();
	}

	public DecisionTableElement getDecisionTableElement() {
		return decisionTableElement;
	}
	
	/**
	 * @return projectName
	 */
	public String getProjectName() {
		return projectName;
	}
}
