package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tomsawyer.graph.TSGraphObject;



public interface ICommandFactory{
	
	boolean handlesModelType(EClass mtype, ENamedElement extType);
	
	public IGraphCommand<? extends TSGraphObject> getCommand(int type, Object ... args);
	

}
