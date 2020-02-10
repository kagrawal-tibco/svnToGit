package com.tibco.cep.studio.sb.sharedresource;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
 */

public class SBConfigEditor extends AbstractSharedResourceEditor {
	private SBConfigModelMgr modelmgr;
	private SBGeneralPage page;

	public SBConfigEditor() {
		super();
	}
	
	@Override
	protected void createGeneralPage() {
		page = new SBGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(page.getControl());
		setPageText(index, page.getName());
		controls.add(page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "StreamBase", ".sharedsb");
		return (displayName);
	}
	
	@Override
	protected void loadModel() {
		modelmgr = new SBConfigModelMgr(project, this);
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
