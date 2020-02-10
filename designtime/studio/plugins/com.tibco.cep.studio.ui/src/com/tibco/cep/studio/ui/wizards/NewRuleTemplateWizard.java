package com.tibco.cep.studio.ui.wizards;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class NewRuleTemplateWizard extends AbstractNewEntityWizard<EntityFileCreationWizard> {

	public NewRuleTemplateWizard(){
		setWindowTitle(Messages.getString("new.ruletemplate.wizard.title"));
	}

	@Override
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception{
		RuleTemplate rule = RuleFactory.eINSTANCE.createRuleTemplate();
		populateEntity(filename, rule);
		try {
			rule.setAuthor(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		rule.setPriority(5);
		rule.setForwardChain(true); // default is true
		rule.setConditionText("");
		rule.setActionText("");
		String folder = rule.getFolder();
		String extension = IndexUtils.getFileExtension(rule);
		URI uri = URI.createFileURI(baseURI+"/"+project.getName()+folder+rule.getName()+"."+extension);
		ModelUtilsCore.persistRuleTemplate(rule, uri);
	}

	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_RULE_TEMPLATE;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.ruletemplate.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.ruletemplate.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/ruleTemplateWizard.png");
	}
	
}
