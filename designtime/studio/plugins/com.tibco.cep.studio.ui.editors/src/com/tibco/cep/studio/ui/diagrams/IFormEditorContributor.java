package com.tibco.cep.studio.ui.diagrams;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;

import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

public interface IFormEditorContributor {

	void addSection(IManagedForm managedForm,
			Composite sashForm, AbstractSaveableEntityEditorPart editor);

	int getSectionWeight();
	
	void dispose();
	
}
