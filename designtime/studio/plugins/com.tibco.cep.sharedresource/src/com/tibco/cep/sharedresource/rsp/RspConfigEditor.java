package com.tibco.cep.sharedresource.rsp;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
@author ssailapp
@date Feb 22, 2010 7:23:56 PM
 */

public class RspConfigEditor extends AbstractSharedResourceEditor {

	private RspConfigModelMgr modelmgr;
	private RspGeneralPage page;

	public RspConfigEditor() {
		super();
	}

	protected void createGeneralPage() throws PartInitException {
		page = new RspGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(page.getControl());
		setPageText(index, page.getName());
		controls.add(page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "RuleServiceProvider Configuration", ".sharedrsp");
		return (displayName);
	}

	protected void loadModel() {
		modelmgr = new RspConfigModelMgr(project, this);
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
