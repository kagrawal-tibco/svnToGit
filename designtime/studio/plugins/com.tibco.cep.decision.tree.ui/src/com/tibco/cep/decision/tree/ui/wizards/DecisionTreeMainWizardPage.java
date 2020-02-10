package com.tibco.cep.decision.tree.ui.wizards;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.decision.tree.ui.DecisionTreeUIPlugin;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeImages;
import com.tibco.cep.decision.tree.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.StudioNewFileWizardPage;

/*
 @author ssailapp
 @date Sep 13, 2011
 */

public class DecisionTreeMainWizardPage extends StudioNewFileWizardPage {

	private static final String INITIAL_CONTENTS_TEMPLATE_FILE = "/templates/sampleDTree.dtree";

	public DecisionTreeMainWizardPage(IWorkbench workbench, IStructuredSelection selection) {
		super("Decision Tree", selection);
		setTitle(Messages.getString("new.decisiontree.wizard.title"));
		setDescription(Messages.getString("new.decisiontree.wizard.desc"));
		setImageDescriptor(DecisionTreeImages.getImageDescriptor(DecisionTreeImages.TREE));
		setFileExtension("dtree");
	}

	protected boolean validatePage() {
		return (validateSharedResourceFileName() && super.validatePage());
	}

	protected InputStream getInitialContents() {
		InputStream contents = null;
		try {
			contents = DecisionTreeUIPlugin.getDefault().getBundle().getEntry(INITIAL_CONTENTS_TEMPLATE_FILE)
					.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contents;
	}
}
