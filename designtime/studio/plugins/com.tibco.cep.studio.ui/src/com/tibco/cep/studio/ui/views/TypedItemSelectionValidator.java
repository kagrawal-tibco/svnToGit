package com.tibco.cep.studio.ui.views;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;



public class TypedItemSelectionValidator implements ISelectionStatusValidator {
	
	private IStatus statusOK = new StatusInfo();
	private IStatus statusERROR= new StatusInfo("",IStatus.ERROR);

	private Class[] acceptedTypes;
	private boolean multipleSelection;
	private Collection rejectedItems;
	
	
	/**
	 * 
	 * @param acceptedTypes
	 * @param multipleSelection
	 */
	public TypedItemSelectionValidator(Class[] acceptedTypes,
			boolean multipleSelection) {
		this(acceptedTypes, multipleSelection, null);
	}


	/**
	 * 
	 * @param acceptedTypes
	 * @param multipleSelection
	 * @param rejectedItems
	 */
	public TypedItemSelectionValidator(Class[] acceptedTypes,
			boolean multipleSelection, Collection rejectedItems) {
		this.acceptedTypes = acceptedTypes;
		this.multipleSelection = multipleSelection;
		this.rejectedItems = rejectedItems;
	}



	@Override
	public IStatus validate(Object[] selection) {
		if (isValid(selection)) {
			return statusOK;
		}
		return statusERROR;
	}
	
	private boolean isOfAcceptedType(Object o) {
		for (int i= 0; i < acceptedTypes.length; i++) {
			if (acceptedTypes[i].isInstance(o)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isRejectedElement(Object elem) {
		return (rejectedItems != null) && rejectedItems.contains(elem);
	}
	

	protected boolean isSelectedValid(Object elem) {
		return true;
	}
	
	private boolean isValid(Object[] selection) {
		if (selection.length == 0) {
			return false;
		}
		
		if (!multipleSelection && selection.length != 1) {
			return false;
		}
		
		for (int i= 0; i < selection.length; i++) {
			Object o= selection[i];	
			if (!isOfAcceptedType(o) || isRejectedElement(o) || !isSelectedValid(o)) {
				return false;
			}
		}
		return true;
	}
	

}
