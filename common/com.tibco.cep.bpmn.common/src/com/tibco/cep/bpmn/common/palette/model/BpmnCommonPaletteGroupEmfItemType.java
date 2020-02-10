package com.tibco.cep.bpmn.common.palette.model;


import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.common.palette.EmfType;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * 
 * @author majha
 *
 */
public class BpmnCommonPaletteGroupEmfItemType extends BpmnCommonPaletteGroupItemType {

	private EmfType wrappedType;

	public BpmnCommonPaletteGroupEmfItemType(EmfType type) {
		super(EMF_TYPE);
		this.wrappedType = type;
	}
	
	public ExpandedName getEmfType(){
		return ExpandedName.parse(wrappedType.getEmfType());
	}
	
	public ExpandedName getExtendedType(){
		ExpandedName extendedType = null;
		if(wrappedType.getExtendedType() != null && !wrappedType.getExtendedType().trim().isEmpty())
			extendedType = ExpandedName.parse(wrappedType.getExtendedType());
		
		return extendedType;	
	}
	
	public boolean setEmfType(String emfType){
		if(!wrappedType.getEmfType().equalsIgnoreCase(emfType)){
			wrappedType.setEmfType(emfType);
			return true;
		}
		return false;
	}
	
	public boolean setExtendedType(String extendedType) {
		boolean changed = false;
		if (extendedType == null || wrappedType.getExtendedType() == null) {
			if (!(extendedType == null && wrappedType.getExtendedType() == null)) {
				wrappedType.setExtendedType(extendedType);
				changed = true;
			}
		} else if (!wrappedType.getExtendedType().equalsIgnoreCase(extendedType)) {
			wrappedType.setExtendedType(extendedType);
			changed = true;
		}

		return changed;
	}

	@Override
	public EObject getWrappedType() {
		// TODO Auto-generated method stub
		return wrappedType;
	}

}
