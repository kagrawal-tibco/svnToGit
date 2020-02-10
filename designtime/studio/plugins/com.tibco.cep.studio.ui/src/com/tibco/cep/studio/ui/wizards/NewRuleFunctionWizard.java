package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

public class NewRuleFunctionWizard extends AbstractNewEntityWizard<RuleFunctionFileCreationWizard> {

	public NewRuleFunctionWizard() {
		setWindowTitle(Messages.getString("new.rulefunction.wizard.title"));
	}

	 public NewRuleFunctionWizard(IDiagramEntitySelection diagramEntitySelect, String currentProjectName){
		 setWindowTitle(Messages.getString("new.rulefunction.wizard.title"));
		 setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
		 this.diagramEntitySelect = diagramEntitySelect;
		 this.currentProjectName = currentProjectName;
	 }
	
	public NewRuleFunctionWizard(String currentProjectName) {
		setWindowTitle(Messages.getString("new.rulefunction.wizard.title"));
		this.currentProjectName = currentProjectName;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#addPages()
	 */
	public void addPages() {
		try {
			if (_selection != null) {
				project = StudioResourceUtils.getProjectForWizard(_selection);
			}
			page = new RuleFunctionFileCreationWizard(Messages
					.getString("new.rulefunction.wizard.title"), _selection,
					StudioWorkbenchConstants._WIZARD_TYPE_NAME_RULE_FUNCTION,
					currentProjectName);
			page.setDescription(Messages
					.getString("new.rulefunction.wizard.desc"));
			page.setTitle(Messages.getString("new.rulefunction.wizard.title"));
			if (defaultEntityName != null) {
				page.setFileName(defaultEntityName);
			}
			addPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(Display.getDefault().getActiveShell(),
					"Rules Create", e.getMessage());
		}
	}

	@Override
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception {
		RuleFunction ruleFunction = RuleFactory.eINSTANCE.createRuleFunction();
		ruleFunction.setName(filename);
		ruleFunction.setVirtual(((RuleFunctionFileCreationWizard) page)
				.getVirtualButton().getSelection());
		ruleFunction.setDescription(page.getTypeDesc());
		ruleFunction.setConditionText("");
		ruleFunction.setActionText("");
		ruleFunction.setFolder(StudioResourceUtils.getFolder(getModelFile()));
		ruleFunction.setNamespace(StudioResourceUtils.getFolder(getModelFile()));
		ruleFunction.setReturnType(page.getReturnType());
		String folder = ruleFunction.getFolder();
		String extension = IndexUtils.getFileExtension(ruleFunction);
		URI uri = URI.createFileURI(baseURI + "/" + project.getName() + folder
				+ ruleFunction.getName() + "." + extension);
		ModelUtilsCore.persistRuleFunction(ruleFunction, uri);
	}

	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_RULE_FUNCTION;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.rulefunction.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.rulefunction.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin
				.getImageDescriptor("icons/wizard/rulefunctionWizard.png");
	}

}
