package com.tibco.cep.sharedresource.jdbc;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/*
@author ssailapp
@date Dec 29, 2009 11:28:07 PM
 */

public class JdbcConfigEditor extends AbstractSharedResourceEditor {
	private JdbcConfigModelMgr modelmgr;
	private JdbcGeneralPage page;

	public JdbcConfigEditor() {
		super();
	}

	protected void createGeneralPage() {
		page = new JdbcGeneralPage(this, getContainer(), modelmgr);
		int index = addPage(page.getControl());
		setPageText(index, page.getName());
		controls.add(page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "JDBC Connection", ".sharedjdbc");
		return (displayName);
	}

	protected void loadModel() {
		modelmgr = new JdbcConfigModelMgr(project, this);
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
