package com.tibco.cep.sharedresource.jndi;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
@author ssailapp
@date Dec 29, 2009 8:16:47 PM
 */

public class JndiConfigEditor extends AbstractSharedResourceEditor {
	private JndiConfigModelMgr modelmgr;
	private JndiGeneralPage page;

	public JndiConfigEditor() {
		super();
	}

	protected void createGeneralPage() {
		page = new JndiGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(page.getControl());
		setPageText(index, page.getName());
		controls.add(page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "JNDI Configuration", ".sharedjndiconfig");
		return (displayName);
	}

	protected void loadModel() {
		modelmgr = new JndiConfigModelMgr(project, this);
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
