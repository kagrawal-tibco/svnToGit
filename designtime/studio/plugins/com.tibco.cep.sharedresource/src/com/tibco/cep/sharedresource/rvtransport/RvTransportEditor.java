package com.tibco.cep.sharedresource.rvtransport;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
@author ssailapp
@date Dec 29, 2009 5:13:47 PM
 */

public class RvTransportEditor extends AbstractSharedResourceEditor {
	private RvTransportModelMgr modelmgr;
	private RvGeneralPage page;

	public RvTransportEditor() {
		super();
	}

	protected void createGeneralPage() {
		page = new RvGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(page.getControl());
		setPageText(index, page.getName());
		controls.add(page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "Rendezvous Transport", ".rvtransport");
		return (displayName);
	}

	protected void loadModel() {
		modelmgr = new RvTransportModelMgr(project, this);
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
