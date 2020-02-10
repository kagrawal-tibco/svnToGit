package com.tibco.cep.studio.ui.editors.globalvar;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.ui.util.StudioImages;

/*
@author ssailapp
@date Dec 29, 2009 11:46:43 PM
 */

public class GlobalVariablesGeneralPage extends FormPage {

	private GlobalVariablesModelMgr modelmgr;
	private GlobalVariablesEditorBlock block;
	private GlobalVariablesEditor editor;
	
	public GlobalVariablesGeneralPage(GlobalVariablesEditor editor, GlobalVariablesModelMgr modelmgr) {
		super(editor, "gv", getName());
		this.editor = editor;
		this.modelmgr = modelmgr;
		block = new GlobalVariablesEditorBlock(this, modelmgr);
	}

	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		form.setText(editor.getTitle());
		form.setImage(editor.getTitleImage());
		block.createContent(managedForm);
	}

	public static String getName() {
		return ("Configuration");
	}
}

