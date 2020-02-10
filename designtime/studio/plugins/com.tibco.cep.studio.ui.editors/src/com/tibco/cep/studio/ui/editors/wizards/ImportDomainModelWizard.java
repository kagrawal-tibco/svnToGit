/**
 * 
 */
package com.tibco.cep.studio.ui.editors.wizards;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.domain.importHandler.DomainConfiguration;
import com.tibco.cep.studio.core.domain.importHandler.DomainModelImportHandlerFactory;
import com.tibco.cep.studio.core.domain.importHandler.IDomainModelImportHandler;
import com.tibco.cep.studio.core.domain.importSource.IDomainImportSource;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.validation.ValidationError;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.domain.DomainFormEditor;
import com.tibco.cep.studio.ui.editors.domain.DomainFormEditorInput;
import com.tibco.cep.studio.ui.editors.wizardPages.DomainImportSourceSelectionWizardPage;
import com.tibco.cep.studio.ui.editors.wizardPages.DomainImportSourceWizardPageFactory;
import com.tibco.cep.studio.ui.editors.wizardPages.IDomainSourceWizardPage;
import com.tibco.cep.studio.ui.editors.wizardPages.ImportDatabaseTableDomainWizardPage;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author aathalye
 *
 */
public class ImportDomainModelWizard<P extends WizardPage & IDomainSourceWizardPage> extends Wizard implements IImportWizard {
	
	private DomainImportSourceSelectionWizardPage mainPage;
	
	/**
	 * Child Page
	 */
	private P childPage;
	
	private IStructuredSelection selection;
	
	private IWorkbenchWindow workbenchWindow;
	
	/**
	 * 
	 */
	public ImportDomainModelWizard() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addPages() {
		mainPage = new DomainImportSourceSelectionWizardPage(Messages.getString("ImportDomainWizard.Title"), selection);
		addPage(mainPage);
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IWizardPage getNextPage(IWizardPage page) {
		childPage = DomainImportSourceWizardPageFactory.
						INSTANCE.getWizardPage(mainPage.getImportSource(), Messages.getString("ImportDomainWizard.Title"));
		//childPage.setDescription(Messages.getString("import.domain.wizard.description"));
		if(childPage instanceof ImportDatabaseTableDomainWizardPage) {
			((ImportDatabaseTableDomainWizardPage)childPage).setDomainDataType(mainPage.getDomainDataType());
		}
		childPage.setProjectName(mainPage.getProject().getName());
		for (IWizardPage addedPage : getPages()) {
			if (addedPage.getClass().equals(childPage.getClass())) {
				//Already added
				//The cast below is safe
				return childPage = (P)addedPage;
			}
		}
		addPage(childPage);
		return childPage;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		String domainName = mainPage.getFileName();
		IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(mainPage.getContainerFullPath());
		StringBuilder duplicateFileName  = new StringBuilder("");
		if (isDuplicateBEResource(resource, domainName, duplicateFileName)) {
			boolean confirmation = MessageDialog.openQuestion(workbenchWindow.getShell(), 
					Messages.getString("Duplicate_DomainModel_Title"),
					Messages.getString("Duplicate_DomainModel_Message") + 
					Messages.getString("Duplicate_DomainModel_Confirm"));
			if (confirmation) {
				importDomainModel(domainName);
				return true;
			} else {
				return false;
			}
		}
		else {
			importDomainModel(domainName);
			return true;
		}
	}

	/**
	 * 
	 * @param <O>
	 * @param domainName
	 */
	private <O extends Object> void importDomainModel(String domainName) {
		List<ValidationError> validationErrors = new ArrayList<ValidationError>(0);
		//Get values of each field
		String domainDataType = mainPage.getDomainDataType();
		DOMAIN_DATA_TYPES dataTypeEnum = DOMAIN_DATA_TYPES.get(domainDataType);
		O dataSource = childPage.getDataSource();
		IFile file = mainPage.getModelFile();
		String domainDescription = mainPage.getDomainDescription();
		IDomainModelImportHandler<O, IDomainImportSource<O>> domainModelImportHandler = null;
		boolean duplicateFlag = true;
		try {
			String domainFolderPath = StudioResourceUtils.getFolder(file);
			String rootDirectoryPath = getProject(mainPage.getProject().getName()).getLocation().toPortableString();
			DomainConfiguration domainConfiguration = new DomainConfiguration();
			domainConfiguration.setDomainDataType(dataTypeEnum);
			domainConfiguration.setDomainName(domainName);
			domainConfiguration.setDomainDescription(domainDescription);
			domainConfiguration.setDomainFolderPath(domainFolderPath);
			domainConfiguration.setProjectDirectoryPath(rootDirectoryPath);
			
			domainModelImportHandler =
				DomainModelImportHandlerFactory.INSTANCE.getImportHandler(mainPage.getImportSource(), validationErrors, dataSource);
			domainModelImportHandler.importDomain(domainConfiguration);
			//Save it
			if (domainModelImportHandler.getImportedDomain() != null) {
				String pathToSave = 
					new StringBuilder(rootDirectoryPath).
					append(File.separator).
					append(domainFolderPath).
					toString();
				ModelUtils.saveDomain(domainModelImportHandler.getImportedDomain(), pathToSave);
				file.refreshLocal(0, null);
			}
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		}
		if (domainModelImportHandler.getImportedDomain() == null) {
			MessageDialog.openInformation(workbenchWindow.getShell(), 
					Messages.getString("ImportDomainWizard.Title"), 
					                      "Imported Domain is Empty. Domain is not created");
		} else if (validationErrors.isEmpty()) {
			MessageDialog.openInformation(workbenchWindow.getShell(), 
					Messages.getString("ImportDomainWizard.Title"), 
					                      "Domain Import Successful");
			openEditor(mainPage.getProject().getName(), domainModelImportHandler.getImportedDomain());
		} else {
			//TODO Find a better way to display errors
			for (ValidationError ve : validationErrors) {
				MessageDialog.openError(workbenchWindow.getShell(), 
						Messages.getString("ImportDomainWizard.Title"),
						ve.getMessage());
				if(!ve.getMessage().contains("Duplicate entries in Domain Model")) {
					duplicateFlag = false;
				}
			}
			if(duplicateFlag) {
				openEditor(mainPage.getProject().getName(), domainModelImportHandler.getImportedDomain());
			}
		}
	}
	
	private void openEditor(String projectName, Domain importedDomain) {
		IFile file = IndexUtils.getFile(projectName, importedDomain);
		if (null == file) {
			MessageDialog.openError(workbenchWindow.getShell(), 
					Messages.getString("ImportDomainWizard.Title"), 
					                "Failed to open file for editor");
			return;
		}
		DomainFormEditorInput domainFormEditorInput = new DomainFormEditorInput(file, importedDomain);
		IWorkbenchPage page = workbenchWindow.getActivePage();
		//Open editor
		try {
			page.openEditor(domainFormEditorInput, DomainFormEditor.ID);
		} catch (PartInitException pe) {
			StudioUIPlugin.log(pe);
			MessageDialog.openError(workbenchWindow.getShell(), 
	                "Import Domain", 
	                "Failed to open domain editor");
			return;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.getString("ImportDomainWizard.Title")); 
		workbenchWindow = workbench.getActiveWorkbenchWindow();
		this.selection = selection;
	}

	/**
	 * Checks if this is a duplicate resource.
	 * @param resource
	 * @param resourceName
	 * @param duplicateFileName
	 * @return
	 */
	protected boolean isDuplicateBEResource(IResource resource,
			String resourceName, StringBuilder duplicateFileName) {
		return StudioResourceUtils.isDuplicateBEResource(resource, resourceName, duplicateFileName);
	}
	
	public boolean canFinish() {
		return getContainer().getCurrentPage().isPageComplete();
	}

	/**
	 * @param projectName
	 * @return
	 */
	private IProject getProject(String projectName) {
		return CommonUtil.getStudioProject(projectName);
	}
}