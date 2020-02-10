package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.sharedresource.ui.util.Messages;

/*
@author ssailapp
@date Sep 25, 2009 4:47:41 PM
 */

public class NewJdbcConfigWizard extends NewSharedResourceWizard {

//	private static final String JDBC_RESOURCE_URI = "jdbc_resource_uri"; //$NON-NLS-1$
	private NewJdbcConfigWizardPage newJdbcConfigWizardPage;
//	private JdbcGeneralPage jdbcEditorPage;
	
	public NewJdbcConfigWizard() {
		setWindowTitle(Messages.getString("new.jdbcconfig.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newJdbcConfigWizardPage = new NewJdbcConfigWizardPage(selection);
        addPage(newJdbcConfigWizardPage);
        setWizardPage(newJdbcConfigWizardPage);
        //this.jdbcEditorPage = new JdbcGeneralPage(editorInput,new JdbcConfigModelMgr(project, null));
	}
	
	@Override
	public IFile getFile() {
		return newJdbcConfigWizardPage.getFile();
	}
}
