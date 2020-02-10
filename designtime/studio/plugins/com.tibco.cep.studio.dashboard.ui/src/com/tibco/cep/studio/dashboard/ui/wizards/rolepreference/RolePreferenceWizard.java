package com.tibco.cep.studio.dashboard.ui.wizards.rolepreference;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseViewsElementWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class RolePreferenceWizard extends BaseViewsElementWizard {

	private LocalRolePreference localRolePreference;
	private RolePreferenceEntityFileCreationWizardPage rolePreferenceCreationWizardPage;
	private LocalView localView;

	public RolePreferenceWizard() {
		 super(BEViewsElementNames.ROLE_PREFERENCE, "Role Preference", "New Role Preference Wizard", "RolePreferencePage", "New Role Preference", "Create a new Role Preference");
		 setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("rolepreference_wiz.png"));
	}

	@Override
	protected EntityFileCreationWizard createPage() {
		rolePreferenceCreationWizardPage = new RolePreferenceEntityFileCreationWizardPage(pageName, _selection, elementType, elementTypeName);
		rolePreferenceCreationWizardPage.setDescription(pageDesc);
		rolePreferenceCreationWizardPage.setTitle(pageTitle);
		return rolePreferenceCreationWizardPage;
	}

	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		if(localRolePreference == null){
			//Finished from first page.
			localRolePreference = createLocalRolePreference(elementName, elementDesc);
		}
		// when user comes back by hitting BACK button, and change the Role Pref name or description.
		localRolePreference.setName(entityCreatingWizardPage.getFileName());
		localRolePreference.setDescription(entityCreatingWizardPage.getTypeDesc());
		localRolePreference.setElement("View", localView);

		localRolePreference.synchronize();
		// Persist the element
		DashboardResourceUtils.persistEntity(localRolePreference.getEObject(), baseURI, entityCreatingWizardPage.getProject(), monitor);
	}

	private LocalRolePreference createLocalRolePreference(String elementName, String elementDesc){
		IProject project = entityCreatingWizardPage.getProject();

		localView = rolePreferenceCreationWizardPage.getSelectedView();

		LocalRolePreference localRolePreference = (LocalRolePreference) LocalECoreFactory.getInstance(project).createLocalElement(elementType);
		localRolePreference.getID();
		localRolePreference.setName(elementName);
		localRolePreference.setDescription(elementDesc);
		localRolePreference.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
		localRolePreference.setNamespace(DashboardResourceUtils.getFolder(getModelFile()));
		localRolePreference.setOwnerProject(project.getName());

		localRolePreference.setPropertyValue(LocalRolePreference.PROP_KEY_ROLE, rolePreferenceCreationWizardPage.getRoleName());

		localRolePreference.setElement("View", localView);

		List<LocalElement> selectedComponents = rolePreferenceCreationWizardPage.getSelectedComponents();
		if (selectedComponents.isEmpty() == false) {
			LocalElement componentGalleryFolder = localRolePreference.createLocalElement("Gallery");
			componentGalleryFolder.setName("root");
			for (LocalElement component : selectedComponents) {
				componentGalleryFolder.addElement(BEViewsElementNames.COMPONENT, component);
			}
		}

		return localRolePreference;
	}
}
