package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.StudioImages;

/*
@author ssailapp
@date Feb 5, 2010 7:12:35 PM
 */

public class LogConfigPage extends FormPage {
	private ClusterConfigModelMgr modelmgr;
	private LogConfigBlock block;
	private ClusterConfigEditor editor;
	
	public LogConfigPage(ClusterConfigEditor editor, ClusterConfigModelMgr modelmgr) {
		super(editor, "logconfig", getName());
		this.editor = editor;
		this.modelmgr = modelmgr;
		block = new LogConfigBlock(this, modelmgr);
	}

	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		form.setText(editor.getTitle());
		form.setImage(editor.getTitleImage());
		block.createContent(managedForm);
	}

	public static String getName() {
		return ("Log Configuration");
	}
}
