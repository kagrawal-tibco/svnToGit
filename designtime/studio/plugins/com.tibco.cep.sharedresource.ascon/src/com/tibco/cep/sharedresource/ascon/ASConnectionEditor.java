package com.tibco.cep.sharedresource.ascon;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
@author Huabin Zhang (huzhang@tibco-support.com)
@date Dec 22, 2011 10:55:25 AM
*/

public class ASConnectionEditor extends AbstractSharedResourceEditor {

	private ASConnectionModelMgr modelmgr;
	private ASConnectionGeneralPage genPage;

	@Override
	protected void createGeneralPage() throws PartInitException {
		genPage = new ASConnectionGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(genPage.getControl());
		setPageText(index, genPage.getName());
		controls.add(genPage.getControl());
	}

	@Override
	public void dispose() {
		if (genPage != null) {
			genPage.dispose();
		}
		super.dispose();
	}

	@Override
	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "AS Connection", ".sharedascon");
		return (displayName);
	}

	@Override
	protected void loadModel() {
		modelmgr = new ASConnectionModelMgr(project, this);
		modelmgr.parseModel();
	}

	@Override
	protected String saveModel() {
		return (modelmgr.saveModel()); 
	}

	@Override
	protected AbstractSharedResourceEditorPage[] getSchemaEditorPages() {
		return new AbstractSharedResourceEditorPage[] { genPage };
	}

	@Override
	protected FormPage[] getEditorPages() {
		return null;
	}

}

