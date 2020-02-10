package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class NewConceptWizard extends AbstractNewEntityWizard<EntityFileCreationWizard> {

	 public NewConceptWizard(){
		   setWindowTitle(Messages.getString("new.concept.wizard.title"));
	 }
	 
	 public NewConceptWizard(IDiagramEntitySelection diagramEntitySelect, String currentProjectName){
		 setWindowTitle(Messages.getString("new.concept.wizard.title"));
		 setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
		 this.diagramEntitySelect = diagramEntitySelect;
		 this.currentProjectName = currentProjectName;
	 }
	
	@Override
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception{
        Concept concept = ElementFactory.eINSTANCE.createConcept();
		populateEntity(filename, concept);
		concept.setSuperConceptPath("");
		concept.setScorecard(false);
		concept.setAutoStartStateMachine(true);
		StudioResourceUtils.persistEntity(concept, baseURI, project, monitor);	
	}

	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_CONCEPT;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.concept.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.concept.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/conceptWizard.png");
	}
	
}