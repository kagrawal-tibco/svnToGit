package com.tibco.cep.sharedresource.identity;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
@author ssailapp
@date Dec 28, 2009 1:09:40 PM
 */

public class IdentityConfigEditor extends AbstractSharedResourceEditor {
	private IdentityConfigModelMgr modelmgr;
	private IdentityGeneralPage page;

	public IdentityConfigEditor() {
		super();
	}
	
	@Override
	protected void createGeneralPage() {
		page = new IdentityGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(page.getControl());
		setPageText(index, page.getName());
		controls.add(page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "Identity", ".id");
		return (displayName);
	}
	
	@Override
	protected void loadModel() {
		modelmgr = new IdentityConfigModelMgr(project, this);
		modelmgr.parseModel();
	}

	@Override
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
