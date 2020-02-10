package com.tibco.cep.studio.ui.statemachine.diagram.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.states.StateMachine;

/**
 * 
 * @author ggrigore/sasahoo
 * 
 */
public class StateMachineEditorInput extends FileEditorInput {

	
	private StateMachine stateMachine;
	private IProject project;
	
	public StateMachineEditorInput(IFile file, StateMachine sm) {
		super(file); 
		this.stateMachine = sm;
		this.project = file.getProject();
	}
	
	
	public StateMachine getStateMachine() {
		return this.stateMachine;
	}
	
	public IProject getProject() {
		return this.project;
	}


	public void setStateMachine(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}
	
}


