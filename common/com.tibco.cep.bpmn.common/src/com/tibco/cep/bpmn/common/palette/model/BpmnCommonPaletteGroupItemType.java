package com.tibco.cep.bpmn.common.palette.model;

import org.eclipse.emf.ecore.EObject;


/**
 * 
 * @author majha
 *
 */
public  abstract class BpmnCommonPaletteGroupItemType {
	public static final int EMF_TYPE = 1;
	public static final int JAVA_TYPE = 2;
	public static final int MODEL_TYPE = 3;
	int type;
	
	
	public BpmnCommonPaletteGroupItemType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	abstract public EObject getWrappedType();
}