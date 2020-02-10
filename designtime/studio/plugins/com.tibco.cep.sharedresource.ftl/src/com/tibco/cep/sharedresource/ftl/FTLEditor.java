package com.tibco.cep.sharedresource.ftl;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ftl.FTLGeneralPage;
import com.tibco.cep.sharedresource.ftl.FTLModelMgr;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

public class FTLEditor extends AbstractSharedResourceEditor {
	private FTLModelMgr modelmgr;
	private FTLGeneralPage page;

	protected void createGeneralPage() {
		this.page = new FTLGeneralPage(this, getContainer(), this.modelmgr);
		int index = addPage(this.page.getControl());
		setPageText(index, this.page.getName());
		this.controls.add(this.page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "FTL Connection", ".ftl");
		return displayName;
	}

	protected void loadModel() {
		this.modelmgr = new FTLModelMgr(project, this);
		this.modelmgr.parseModel();
	}

	protected String saveModel() {
		return this.modelmgr.saveModel();
	}

	protected AbstractSharedResourceEditorPage[] getSchemaEditorPages() {
		return new AbstractSharedResourceEditorPage[] { page };
	}

	protected FormPage[] getEditorPages() {
		return null;
	}
}