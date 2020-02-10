package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalStateMachineComponent extends LocalComponent {

	public static final String ELEMENT_KEY_STATE_MACHINE = "StateMachine";

	public static final String ELEMENT_KEY_STATE_VISUALIZATION = "StateVisualization";

	private static final String THIS_TYPE = BEViewsElementNames.STATE_MACHINE_COMPONENT;

	public static final String PROP_STATE_VISUALIZATION_WIDTH = "StateVisualizationWidth";

	public static final String PROP_STATE_VISUALIZATION_HEIGHT = "StateVisualizationHeight";

	private LocalElement transientElement;

	public LocalStateMachineComponent() {
		super(THIS_TYPE);
	}

	public LocalStateMachineComponent(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalStateMachineComponent(LocalElement parentConfig, String name) {
		super(parentConfig, THIS_TYPE, name);
	}

	@Override
	protected LocalElement convertMdElementToLocalElement(Entity mdElement, String elementType) {
		if (elementType.equals(BEViewsElementNames.STATE_MACHINE)) {
			return new LocalExternalReference(mdElement);
		}
		return super.convertMdElementToLocalElement(mdElement, elementType);
	}

	public final LocalElement getTransientElement() {
		return transientElement;
	}

	public final boolean setTransientElement(LocalElement transientElement) throws Exception {
		LocalElement parent = transientElement.getParent();
		while (parent != null && parent != this){
			parent = parent.getParent();
		}
		if (parent != this){
			return false;
		}
		this.transientElement = transientElement;
		return true;
	}

	@Override
	public boolean isValid() throws Exception {
		LocalExternalReference stateMachineRef = (LocalExternalReference) getElement(ELEMENT_KEY_STATE_MACHINE);
		//check for valid state machine reference
		if (stateMachineRef == null || stateMachineRef.getEObject() == null){
			addValidationMessage(new SynValidationErrorMessage("No state model specified", getURI()));
			return false;
		}
		//we have a state machine reference
		//check for out of sync in terms state visualization count and state entities count
		StateMachine stateMachine = (StateMachine) stateMachineRef.getEObject();
		int usableStateCnt = StateMachineComponentHelper.getUsableStates(stateMachine).size();
		int stateVizCnt = getChildren(ELEMENT_KEY_STATE_VISUALIZATION).size();
		if (usableStateCnt < stateVizCnt){
			addValidationMessage(new SynValidationErrorMessage("Found "+(stateVizCnt-usableStateCnt)+" extra state visualizations", getURI()));
			return false;
		}
		return super.isValid();
	}

	@Override
	public String getURI() {
		return "/";
	}

}