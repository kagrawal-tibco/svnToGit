package com.tibco.cep.sharedresource.jms;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
@author ssailapp
@date Dec 30, 2009 11:47:23 PM
 */

public class JmsConEditor extends AbstractSharedResourceEditor {
	private JmsConModelMgr modelmgr;
	private JmsConAdvancedPage advPage;
	private JmsConGeneralPage genPage;
	
	public JmsConEditor() {
		super();
	}

	protected void createGeneralPage() throws PartInitException {
		genPage = new JmsConGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(genPage.getControl());
		setPageText(index, genPage.getName());
		controls.add(genPage.getControl());
	}

	protected void createAdvancedPage() throws PartInitException {
		advPage = new JmsConAdvancedPage(this, getContainer(), modelmgr);
		int index = addPage(advPage.getControl());
		setPageText(index, advPage.getName());
		controls.add(advPage.getControl());
	}
	
	@Override
	public void dispose() {
		if (advPage != null) {
			advPage.dispose();
		}
		if (genPage != null) {
			genPage.dispose();
		}
		super.dispose();
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "JMS Connection", ".sharedjmscon");
		return (displayName);
	}

	protected void loadModel() {
		modelmgr = new JmsConModelMgr(project, this);
		modelmgr.parseModel();
	}

	protected String saveModel() {
		return (modelmgr.saveModel()); 
	}
	
	protected void addPages() {
		loadModel();
		try {
			createGeneralPage();
			createAdvancedPage();
			//createSourcePage();
			autoSave();
		} catch (PartInitException pie) {
		}
	}
	
	protected void pageChange(int newPageIndex) {
		switch (newPageIndex) {
		case 1: advPage.enableJndiAdvancedTab(); break;
		case 2: break;
		default: break;
		}
		super.pageChange(newPageIndex);
	}
	
	@Override
	protected AbstractSharedResourceEditorPage[] getSchemaEditorPages() {
		return new AbstractSharedResourceEditorPage[] { genPage,  advPage};
	}

	@Override
	protected FormPage[] getEditorPages() {
		// TODO Auto-generated method stub
		return null;
	}
}
