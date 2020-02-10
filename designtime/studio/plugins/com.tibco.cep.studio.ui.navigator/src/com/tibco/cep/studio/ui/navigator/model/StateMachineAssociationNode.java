package com.tibco.cep.studio.ui.navigator.model;

public class StateMachineAssociationNode {

	protected String stateMachine;
	protected String ownerProjectName;

	public String getOwnerProjectName() {
		return ownerProjectName;
	}

	public String getStateMachine() {
		return stateMachine;
	}

	public StateMachineAssociationNode() {
	}

	public StateMachineAssociationNode(String sm, String ownerProjectName) {
		this.stateMachine = sm;
		this.ownerProjectName = ownerProjectName;
	}

}