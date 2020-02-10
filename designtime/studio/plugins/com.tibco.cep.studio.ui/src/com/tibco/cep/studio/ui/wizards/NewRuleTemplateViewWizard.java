/**
 * 
 */
package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

/**
 *
 */
public class NewRuleTemplateViewWizard extends AbstractNewEntityWizard<NewRuleTemplateViewWizardPage> {
		
	 public NewRuleTemplateViewWizard(){
		   setWindowTitle(Messages.getString("new.ruletemplate.view.wizard.title"));
	 }
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#createEntity(java.lang.String, java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void createEntity(String filename, String baseURI, IProgressMonitor monitor) throws Exception{
		RuleTemplateView view = RuleFactory.eINSTANCE.createRuleTemplateView();
		populateEntity(filename, view);
		// add template association
		String ruleTemplatePath = ((NewRuleTemplateViewWizardPage)page).getRuleTemplatePath();
		view.setRuleTemplatePath(ruleTemplatePath);
		view.setPresentationText("");
		StudioResourceUtils.persistEntity(view, baseURI, project, monitor);	
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#getEntityType()
	 */
	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_RULE_TEMPLATE_VIEW;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#getWizardDescription()
	 */
	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.ruletemplate.view.desc");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#getWizardTitle()
	 */
	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.ruletemplate.view.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/ruleTemplateViewWizard.png");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#addPages()
	 */
	@Override
	public void addPages() {
		try {
			if(_selection != null){
				project = StudioResourceUtils.getProjectForWizard(_selection);
			}
			page = new NewRuleTemplateViewWizardPage(getWizardTitle(),_selection, getEntityType(), "", currentProjectName);
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			addPage(page);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
