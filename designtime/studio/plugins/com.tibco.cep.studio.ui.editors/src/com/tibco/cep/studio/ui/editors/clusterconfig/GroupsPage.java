package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.StudioImages;

/*
@author ssailapp
@date Dec 16, 2009 1:41:31 PM
 */

public class GroupsPage extends FormPage {

	private ClusterConfigModelMgr modelmgr;
	private GroupsBlock block;
	private ClusterConfigEditor editor;
	
	public GroupsPage(ClusterConfigEditor editor, ClusterConfigModelMgr modelmgr) {
		super(editor, "groups", getName());
		this.editor = editor;
		this.modelmgr = modelmgr;	
		block = new GroupsBlock(this, modelmgr);
	}

	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		form.setText(editor.getTitle());
		form.setImage(editor.getTitleImage());
		block.createContent(managedForm);
	}

	public static String getName() {
		return ("Collections");
	}
	
	public void update() {
		block.updateBlock();
	}
}
