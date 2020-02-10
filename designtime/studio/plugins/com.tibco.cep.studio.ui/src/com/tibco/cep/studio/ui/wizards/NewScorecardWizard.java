package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class NewScorecardWizard extends AbstractNewEntityWizard<EntityFileCreationWizard> {
	
	public NewScorecardWizard(){
		setWindowTitle(Messages.getString("new.scorecard.wizard.title"));
	}
	
	@Override
	protected void createEntity(String filename, String baseURI,
			                    IProgressMonitor monitor) throws Exception{
		Scorecard scorecard = ElementFactory.eINSTANCE.createScorecard();
		populateEntity(filename, scorecard);

		scorecard.setScorecard(true);
		StudioResourceUtils.persistEntity(scorecard, baseURI,project,monitor);			
	}

	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_SCORECARD;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.scorecard.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.scorecard.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/scorecardWizard.png");
	}
}