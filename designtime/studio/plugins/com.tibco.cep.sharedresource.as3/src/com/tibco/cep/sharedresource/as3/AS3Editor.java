package com.tibco.cep.sharedresource.as3;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.as3.AS3GeneralPage;
import com.tibco.cep.sharedresource.as3.AS3ModelMgr;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

public class AS3Editor extends AbstractSharedResourceEditor {
	private AS3ModelMgr modelmgr;
	private AS3GeneralPage page;

	protected void createGeneralPage() {
		this.page = new AS3GeneralPage(this, getContainer(), this.modelmgr);
		int index = addPage(this.page.getControl());
		setPageText(index, this.page.getName());
		this.controls.add(this.page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "AS3 Connection", ".as3");
		return displayName;
	}

	protected void loadModel() {
		this.modelmgr = new AS3ModelMgr(project, this);
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