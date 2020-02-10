package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.StudioImages;

/*
@author ssailapp
@date Dec 7, 2009 4:06:42 PM
 */

public class AgentClassesPage extends FormPage {

	private ClusterConfigModelMgr modelmgr;
	private AgentBlock block;
	private ClusterConfigEditor editor;
	
	public AgentClassesPage(ClusterConfigEditor editor, ClusterConfigModelMgr modelmgr) {
		super(editor, "agents", getName());
		this.editor = editor;
		//this.editorParent = parent;
		this.modelmgr = modelmgr;
		
		//createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
		
		block = new AgentBlock(this, modelmgr);
	}

	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		form.setText(editor.getTitle());
		form.setImage(editor.getTitleImage());
		block.createContent(managedForm);
	}

	public static String getName() {
		return ("Agent Classes");
	}
	
	public void update() {
		block.updateBlock();
	}
}
