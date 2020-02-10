package com.tibco.cep.bpmn.common.palette.model;


import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.common.palette.ModelType;

/**
 * 
 * @author majha
 *
 */
public class BpmnCommonPaletteGroupModelItemType extends BpmnCommonPaletteGroupItemType {

	private ModelType wrappedType;

	public BpmnCommonPaletteGroupModelItemType(ModelType type) {
		super(MODEL_TYPE);
		this.wrappedType = type;
	}
	
	public List<String> getTypes(){
		return wrappedType.getType();
	}
	
	public void addType(String type){
		wrappedType.getType().add(type)	;
	}

	public void removeType(String type){
		wrappedType.getType().remove(type)	;
	}

	@Override
	public EObject getWrappedType() {
		// TODO Auto-generated method stub
		return wrappedType;
	}

}
