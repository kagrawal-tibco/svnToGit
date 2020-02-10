package com.tibco.cep.studio.tester.ui.editor.data;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.tester.core.model.ExecutionObjectType;


/**
 * 
 * @author sasahoo
 *
 */
public class TestResultRoot {
	
	private List<ExecutionObjectType> execObjectList;
	/**
	 * @param reteObjectList
	 */
	public TestResultRoot(List<ExecutionObjectType> execObjectList) {
		this.execObjectList = execObjectList;
	}

	/**
	 * @return
	 */
	public List<ExecutionObjectType> getExecutionObjectList() {
		return execObjectList;
	}
	
	/**
	 * @return
	 */
	public List<ReteObjectElement> getReteObjectElements() {
		List<ReteObjectElement> reteList = new ArrayList<ReteObjectElement>();
		for (ExecutionObjectType executionObjectType : execObjectList) {
			ReteObjectElement reteObjectElement = new ReteObjectElement(executionObjectType, executionObjectType.getReteObject());
			reteList.add(reteObjectElement);
		}
		return reteList;
	}
	
}