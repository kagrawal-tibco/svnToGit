/**
 * 
 */
package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

/**
 * @author aathalye
 *
 */
public class NewDomainModelWizard extends AbstractNewEntityWizard<NewDomainModelWizardPage> {
		
	 public NewDomainModelWizard(){
		   setWindowTitle(Messages.getString("new.domain.wizard.title"));
	 }
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#createEntity(java.lang.String, java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void createEntity(String filename, String baseURI, IProgressMonitor monitor) throws Exception{
        Domain domain = DomainFactory.eINSTANCE.createDomain();
		populateEntity(filename, domain);
		StudioResourceUtils.persistEntity(domain, baseURI, project, monitor);	
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#getEntityType()
	 */
	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_DOMAIN;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#getWizardDescription()
	 */
	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.domain.wizard.desc");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#getWizardTitle()
	 */
	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.domain.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/domainModelWizard.png");
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
			page = new NewDomainModelWizardPage(getWizardTitle(),_selection, getEntityType(), currentProjectName);
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			addPage(page);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
