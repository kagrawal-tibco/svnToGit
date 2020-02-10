package com.tibco.cep.studio.dashboard.ui.wizards.datasource;

import java.text.MessageFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseMultiPageViewsWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class DataSourceWizard extends BaseMultiPageViewsWizard {

	private static final String DEFAULT_QUERY = "select * from {0}{1};";

	private DataSourceEntityFileCreationWizardPage dataSourceCreationWizardPage;
	private DataSourceQueryWizardPage dataSourcePage;

	private LocalDataSource localDataSource;

	public DataSourceWizard() {
		super(BEViewsElementNames.DATA_SOURCE, "Data Source", "New Data Source Wizard", "DataSourcePage", "New Data Source", "Create a New Data Source");
		setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("datasource_wiz.png"));
	}

	@Override
	public void addPages() {
		//add the DataSourceEntityFileCreationWizard page see #createPage()
		super.addPages();
		//create the data source wizard page
		dataSourcePage = new DataSourceQueryWizardPage();
		dataSourcePage.setTitle("New Data Source");
		dataSourcePage.setDescription("Create New Data Source");
		addPage(dataSourcePage);
	}

	@Override
	protected EntityFileCreationWizard createPage() {
		dataSourceCreationWizardPage = new DataSourceEntityFileCreationWizardPage(pageName, _selection, elementType, elementTypeName);
		dataSourceCreationWizardPage.setDescription(pageDesc);
		dataSourceCreationWizardPage.setTitle(pageTitle);
		return dataSourceCreationWizardPage;
	}

	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		if(localDataSource == null){
			//Finished from first page.
			localDataSource = createLocalDataSource(elementName, elementDesc);
		}
		updateLocalDataSource(dataSourceCreationWizardPage.getSelectedMetric());
		// when user comes back by hitting BACK button, and change the data source name or description.
		localDataSource.setName(entityCreatingWizardPage.getFileName());
		localDataSource.setDescription(entityCreatingWizardPage.getTypeDesc());
		localDataSource.synchronize();
		// Persist the element
		DashboardResourceUtils.persistEntity(localDataSource.getEObject(), baseURI, entityCreatingWizardPage.getProject(), monitor);
	}

	private LocalDataSource createLocalDataSource(String elementName, String elementDesc){
		IProject project = entityCreatingWizardPage.getProject();
		LocalDataSource localDataSource = (LocalDataSource) LocalECoreFactory.getInstance(project).createLocalElement(elementType);
		localDataSource.getID();
		localDataSource.setName(elementName);
		localDataSource.setDescription(elementDesc);
		localDataSource.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
		localDataSource.setNamespace(DashboardResourceUtils.getFolder(getModelFile()));
		localDataSource.setOwnerProject(project.getName());
		return localDataSource;
	}

	private void updateLocalDataSource(LocalMetric selectedMetric) {
		if (localDataSource != null && selectedMetric != null) {
			if (selectedMetric.equals(localDataSource.getElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT)) == false) {
				localDataSource.setElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT, selectedMetric);
				localDataSource.setPropertyValue(LocalDataSource.PROP_KEY_QUERY, MessageFormat.format(DEFAULT_QUERY, selectedMetric.getPropertyValue(LocalMetric.PROP_KEY_FOLDER),selectedMetric.getName()));
				localDataSource.getParticle(LocalDataSource.ELEMENT_KEY_QUERY_PARAM).removeAll(false);
			}
		}
	}

	@Override
	protected void pageIsAboutToBeHidden(IWizardPage page) {
		if (page == dataSourceCreationWizardPage) {
			if (localDataSource == null) {
				localDataSource = createLocalDataSource(entityCreatingWizardPage.getFileName(),  entityCreatingWizardPage.getTypeDesc());
			}
			updateLocalDataSource(dataSourceCreationWizardPage.getSelectedMetric());
			dataSourcePage.setLocalDataSource(localDataSource);
		}
	}

	@Override
	protected void pageIsAboutToBeShown(IWizardPage page) {
	}

}