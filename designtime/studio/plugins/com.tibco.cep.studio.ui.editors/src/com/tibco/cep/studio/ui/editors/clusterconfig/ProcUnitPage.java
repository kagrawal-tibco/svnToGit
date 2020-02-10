package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.StudioImages;

/*
@author ssailapp
@date Feb 8, 2010 10:54:22 PM
 */

public class ProcUnitPage extends FormPage {
	private ProcUnitBlock block;
	private ClusterConfigEditor editor;
	
	public ProcUnitPage(ClusterConfigEditor editor, ClusterConfigModelMgr modelmgr) {
		super(editor, "procunit", getName());
		this.editor = editor;
		block = new ProcUnitBlock(this, modelmgr);
	}

	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		form.setText(editor.getTitle());
		form.setImage(editor.getTitleImage());
		block.createContent(managedForm);
	}

	public static String getName() {
		return ("Processing Units");
	}
	
	public void update() {
		block.updateBlock();
	}
}
