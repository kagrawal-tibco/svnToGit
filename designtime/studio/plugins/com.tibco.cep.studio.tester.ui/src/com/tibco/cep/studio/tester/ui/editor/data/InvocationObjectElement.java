package com.tibco.cep.studio.tester.ui.editor.data;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.tester.core.model.ConceptType;
import com.tibco.cep.studio.tester.core.model.EventType;
import com.tibco.cep.studio.tester.core.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.core.model.InvocationObjectType;

/**
 * 
 * @author sasahoo
 *
 */
public class InvocationObjectElement {

	private InvocationObjectType invocationObjectType;
	private ExecutionObjectType execObjectType; 
	
	/**
	 * @param excecutionType
	 * @param invocationObjectType
	 */
	public InvocationObjectElement(ExecutionObjectType excecutionType , 
			                       InvocationObjectType invocationObjectType) { 
		this.execObjectType = excecutionType;
		this.invocationObjectType = invocationObjectType;
	}

	/**
	 * @return
	 */
	public InvocationObjectType getInvocationObjectType() {
		return invocationObjectType;
	}
	
	/**
	 * @return
	 */
	public List<EntityTypeElement> getCausalObjects() {
		List<EntityTypeElement> list = new ArrayList<EntityTypeElement>();
		
		for (ConceptType causalObjectsType : execObjectType.getCausalObjects().getConcept()) {
			list.add(new ConceptTypeElement(causalObjectsType));
		}
		
		for (EventType causalObjectsType : execObjectType.getCausalObjects().getEvent()) {
			list.add(new EventTypeElement(causalObjectsType));
		}
		
		return list;
	}
	
	/**
	 * @return
	 */
	public List<EntityTypeElement> getCausalObjectsEndState() {
		List<EntityTypeElement> list = new ArrayList<EntityTypeElement>();
		
		for (ConceptType causalObjectsType : execObjectType.getCausalObjectsEndState().getConcept()) {
			list.add(new ConceptTypeElement(causalObjectsType));
		}
		
		for (EventType causalObjectsType : execObjectType.getCausalObjectsEndState().getEvent()) {
			list.add(new EventTypeElement(causalObjectsType));
		}
		
		return list;
	}
	
}
