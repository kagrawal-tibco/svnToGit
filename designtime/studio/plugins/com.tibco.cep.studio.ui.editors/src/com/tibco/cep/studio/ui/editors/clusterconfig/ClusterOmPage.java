package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.StudioImages;

/*
@author ssailapp
@date Mar 9, 2010 2:37:52 PM
 */

public class ClusterOmPage extends FormPage {
	private ClusterConfigModelMgr modelmgr;
	private ClusterOmConfigBlock block;
	private ClusterConfigEditor editor;
	
	public ClusterOmPage(ClusterConfigEditor editor, ClusterConfigModelMgr modelmgr) {
		super(editor, "clusterom", getName());
		this.editor = editor;
		this.modelmgr = modelmgr;
		block = new ClusterOmConfigBlock(this, modelmgr);
	}

	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		form.setText(editor.getTitle());
		form.setImage(editor.getTitleImage());
		block.createContent(managedForm);
	}

	public static String getName() {
		return ("Cluster");
	}
	
	public void update() {
		block.updateBlock();
	}
}
