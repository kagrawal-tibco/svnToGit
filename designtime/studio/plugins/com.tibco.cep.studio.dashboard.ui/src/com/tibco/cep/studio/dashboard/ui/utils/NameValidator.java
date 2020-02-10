package com.tibco.cep.studio.dashboard.ui.utils;

import org.eclipse.jface.dialogs.IInputValidator;

import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynNameType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * @ *
 */
public class NameValidator implements IInputValidator {

	private LocalElement localElement;
	private String childType;
	private String currentName;

	public NameValidator(LocalElement localElement) throws Exception {
		super();
		this.localElement = localElement.getParent();
		childType = localElement.getElementType();
	}

	public NameValidator(LocalElement localElement, String childType) {
		this(localElement, childType, null);
	}

	public NameValidator(LocalElement localElement, String childType, String currentName) {
		super();
		this.localElement = localElement;
		this.childType = childType;
		this.currentName = currentName;
	}

	public String isValid(String newText) {
		try {
			if (newText.equals(currentName)) {
				return null;
			}
			SynNameType snt = new SynNameType();
			if (null == newText || newText.length() < 1) {
				return "";
			} else if (false == snt.isValid(newText)) {
				return "'"+newText+"' is not a valid name";
			}
			if (true == localElement.isNameBeingUsed(childType, newText)) {
				return "'" + newText + "' is already being used";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}