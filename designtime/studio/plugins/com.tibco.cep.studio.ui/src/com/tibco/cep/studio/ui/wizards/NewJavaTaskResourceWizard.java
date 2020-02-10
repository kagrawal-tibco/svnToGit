package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.java.JavaFactory;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;


public class NewJavaTaskResourceWizard extends NewJavaSourceWizard {

	public NewJavaTaskResourceWizard(IDiagramEntitySelection diagramEntitySelect, String currentProjectName){
		super(diagramEntitySelect, currentProjectName);
	}
	
	public NewJavaTaskResourceWizard() {
		setWindowTitle(Messages.getString("new.javaTaskSource.wizard.title"));
	}
	
	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.javaTaskSource.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.javaTaskSource.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/newclass_wiz.png");
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.NewJavaSourceWizard#createEntity(java.lang.String, java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void createEntity(String filename, String baseURI,
								IProgressMonitor monitor) throws Exception {
		JavaResource javaRes = JavaFactory.eINSTANCE.createJavaResource();
		populateEntity(filename, javaRes);
		javaRes.setExtension(CommonIndexUtils.JAVA_EXTENSION);
		URI uri = URI.createFileURI(baseURI + "/" + project.getName() +javaRes.getFolder() 
				+javaRes.getName() + "." + javaRes.getExtension());
		ModelUtilsCore.persistJavaResource(javaRes, uri, false, true);
	}

}
