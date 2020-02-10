package com.tibco.cep.sharedresource.httpconfig;

/*
 @author ssailapp
 @date Dec 12, 2009 8:58:15 AM
 */

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

public class HttpConfigEditor extends AbstractSharedResourceEditor {
	private HttpConfigModelMgr modelmgr;
    private HttpGeneralPage page;
    
	public HttpConfigEditor() {
		super();
	}

	protected void createGeneralPage() {
		page = new HttpGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(page.getControl());
		setPageText(index, page.getName());
		controls.add(page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "HTTP Connection", ".sharedhttp");
		return (displayName);
	}

	protected void loadModel() {
		modelmgr = new HttpConfigModelMgr(project, this);
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
