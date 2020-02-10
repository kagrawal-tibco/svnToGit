package com.tibco.cep.studio.ui.forms;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public abstract class AbstractEntityFormPage extends FormPage {

	public AbstractEntityFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		// create default Entity attributes section (name, description, properties)
		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		toolkit.decorateFormHeading(form.getForm());
		
		
	}

}
