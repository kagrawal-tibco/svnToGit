package com.tibco.cep.sharedresource.hawk;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

public class HawkEditor extends AbstractSharedResourceEditor {
	private HawkModelMgr modelmgr;
	private HawkGeneralPage page;

	protected void createGeneralPage() {
		this.page = new HawkGeneralPage(this, getContainer(), this.modelmgr);
		int index = addPage(this.page.getControl());
		setPageText(index, this.page.getName());
		this.controls.add(this.page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "Hawk Connection", ".hawk");
		return displayName;
	}

	protected void loadModel() {
		this.modelmgr = new HawkModelMgr(project, this);
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