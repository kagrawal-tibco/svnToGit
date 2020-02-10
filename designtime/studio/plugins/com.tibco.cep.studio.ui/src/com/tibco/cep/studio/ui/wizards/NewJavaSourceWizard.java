package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.java.JavaFactory;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author sshekhar
 *
 */
public class NewJavaSourceWizard extends AbstractNewEntityWizard<JavaSourceFileCreationWizard> {

	public NewJavaSourceWizard() {
		setWindowTitle(Messages.getString("new.javaSource.wizard.title"));
	}

	public NewJavaSourceWizard(IDiagramEntitySelection diagramEntitySelect, String currentProjectName){
		setWindowTitle(Messages.getString("new.javaSource.wizard.title"));
		setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
		this.diagramEntitySelect = diagramEntitySelect;
		this.currentProjectName = currentProjectName;
	}

	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_JAVA;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.javaSource.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.javaSource.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/customFunction_newclass_wiz.png");
	}

	@Override
	protected void createEntity(String filename, String baseURI,
								IProgressMonitor monitor) throws Exception {
		JavaResource javaRes = JavaFactory.eINSTANCE.createJavaResource();
		populateEntity(filename, javaRes);
		javaRes.setExtension(CommonIndexUtils.JAVA_EXTENSION);
		URI uri = URI.createFileURI(baseURI + "/" + project.getName() +javaRes.getFolder() 
				+javaRes.getName() + "." + javaRes.getExtension());
		ModelUtilsCore.persistJavaResource(javaRes, uri, true, false);
	}

	public void addPages() {
		try {
			if (_selection != null) {
				project =  StudioResourceUtils.getProjectForWizard(_selection);
			}
			page = new JavaSourceFileCreationWizard(getWizardTitle(),_selection, getEntityType(),currentProjectName);
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			addPage(page);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
