package com.tibco.cep.sharedresource.jms;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
@author ssailapp
@date Dec 30, 2009 11:47:23 PM
 */

public class JmsAppEditor extends AbstractSharedResourceEditor {
	private JmsAppModelMgr modelmgr;
	private JmsAppGeneralPage page;

	public JmsAppEditor() {
		super();
	}

	protected void createGeneralPage() {
		page = new JmsAppGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(page.getControl());
		setPageText(index, page.getName());
		controls.add(page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "JMS Application Properties", ".sharedJmsApp");
		return (displayName);
	}

	protected void loadModel() {
		modelmgr = new JmsAppModelMgr(project, this);
		modelmgr.parseModel();
	}

	protected String saveModel() {
		return (modelmgr.saveModel()); 
	}
	
	@Override
	protected AbstractSharedResourceEditorPage[] getSchemaEditorPages() {
		return new AbstractSharedResourceEditorPage[] { page };
	}

	@Override
	protected FormPage[] getEditorPages() {
		// TODO Auto-generated method stub
		return null;
	}
}
