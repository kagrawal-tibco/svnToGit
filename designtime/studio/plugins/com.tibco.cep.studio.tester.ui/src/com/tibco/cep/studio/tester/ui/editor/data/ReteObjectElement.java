package com.tibco.cep.studio.tester.ui.editor.data;

import com.tibco.cep.studio.tester.core.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.core.model.ReteObjectType;

/**
 * 
 * @author sasahoo
 *
 */
public class ReteObjectElement {

	private ReteObjectType reteObjectType;
	private ExecutionObjectType execObjectType;
	
	/**
	 * @param excecutionType
	 * @param reteObjectType
	 */
	public ReteObjectElement(ExecutionObjectType excecutionType , 
			                 ReteObjectType reteObjectType){
		this.execObjectType = excecutionType;
		this.reteObjectType = reteObjectType;
	}

	/**
	 * @return
	 */
	public ReteObjectType getReteObjectType() {
		return reteObjectType;
	}
	
	/**
	 * @return
	 */
	public InvocationObjectElement getInvocationElement() {
		return new InvocationObjectElement(execObjectType, 
				execObjectType.getInvocationObject());
	}

}
