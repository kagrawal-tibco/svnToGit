package com.tibco.cep.studio.dashboard.ui.wizards.page.dashboard;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPage;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseMultiPageViewsWizard;
import com.tibco.cep.studio.dashboard.ui.wizards.page.PageTemplate;
import com.tibco.cep.studio.dashboard.ui.wizards.page.PageTemplateWizardPage;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class DashboardPageWizard extends BaseMultiPageViewsWizard {

	private DashboardPageCreator pageCreator;
	private DashboardPageTemplateSelectionWizardPage templateSelectionPage;

	public DashboardPageWizard() {
		super(BEViewsElementNames.DASHBOARD_PAGE, "Dashboard Page", "New Dashboard Page Wizard", "DashboardPagePage", "New Dashboard Page", "Create a new Dashboard Page");
		setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("dashboardpage_wizard.png"));
	}

	@Override
	public void addPages() {
		super.addPages();
		//initialize the page creator
		pageCreator = new DashboardPageCreator();
		//add page template selection page
		PageTemplate[] availableTemplates = pageCreator.getAvailableTemplates();
		templateSelectionPage = new DashboardPageTemplateSelectionWizardPage(availableTemplates,pageCreator.getDefaultTemplate());
		addPage(templateSelectionPage);
		//add the available template pages
		for (PageTemplate pageTemplate : availableTemplates) {
			PageTemplateWizardPage wizardPage = pageTemplate.getWizardPage();
			addPage(wizardPage);
		}
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == templateSelectionPage){
			return templateSelectionPage.getSelectedTemplate().getWizardPage();
		}
		if (page == templateSelectionPage.getSelectedTemplate().getWizardPage()){
			return null;
		}
		if (page == this.entityCreatingWizardPage){
			templateSelectionPage.setDisplayName(this.entityCreatingWizardPage.getFileName());
		}
		return super.getNextPage(page);
	}

	@Override
	protected void pageIsAboutToBeHidden(IWizardPage page) {
		//do nothing
	}

	@Override
	protected void pageIsAboutToBeShown(IWizardPage page) {
		List<LocalElement> selectedComponents = getSelectedComponents();
		if (page == templateSelectionPage) {
			templateSelectionPage.setSelection(selectedComponents);
		}
		else if (page instanceof PageTemplateWizardPage){
			PageTemplateWizardPage wizardPage = (PageTemplateWizardPage) page;
			if (entityCreatingWizardPage.getProject().equals(wizardPage.getProject()) == false || wizardPage.isPopulated() == false){
				wizardPage.setProject(entityCreatingWizardPage.getProject());
				wizardPage.populateControl();
				wizardPage.setPopulated(true);
				wizardPage.setSelectedComponents(selectedComponents);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected List<LocalElement> getSelectedComponents() {
		List<LocalElement> components = new LinkedList<LocalElement>();
		if (_selection != null) {
			Iterator iterator = _selection.iterator();
			while (iterator.hasNext()) {
				Object object = (Object) iterator.next();
				if (object instanceof IResource) {
					IResource resource = (IResource) object;
					//is the selection within the current project
					if (resource.getProject().equals(entityCreatingWizardPage.getProject()) == true) {
						//yes, it is, is it a data source
						DesignerElement element = IndexUtils.getElement(resource);
						if (element != null && element instanceof EntityElement) {
							EntityElement entityElement = (EntityElement) element;
							if (element.getElementType().compareTo(ELEMENT_TYPES.CHART_COMPONENT) == 0) {
								components.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(entityCreatingWizardPage.getProject()), entityElement.getEntity()));
							}
							else if (element.getElementType().compareTo(ELEMENT_TYPES.TEXT_COMPONENT) == 0) {
								components.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(entityCreatingWizardPage.getProject()), entityElement.getEntity()));
							}
							else if (element.getElementType().compareTo(ELEMENT_TYPES.STATE_MACHINE_COMPONENT) == 0) {
								components.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(entityCreatingWizardPage.getProject()), entityElement.getEntity()));
							}
							else if (element.getElementType().compareTo(ELEMENT_TYPES.PAGE_SELECTOR_COMPONENT) == 0) {
								components.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(entityCreatingWizardPage.getProject()), entityElement.getEntity()));
							}
//							else {
//								components.clear();
//								break;
//							}
						}
					}
				}
			}
		}
		return components;
	}


	@Override
	public boolean performFinish() {
		List<LocalElement> selectedComponents = getSelectedComponents();
		templateSelectionPage.setSelection(selectedComponents);
		PageTemplate selectedTemplate = templateSelectionPage.getSelectedTemplate();
		if (selectedTemplate != null) {
			PageTemplateWizardPage wizardPage = selectedTemplate.getWizardPage();
			if (wizardPage.isPopulated() == false) {
				wizardPage.setProject(entityCreatingWizardPage.getProject());
				wizardPage.populateControl();
				wizardPage.setPopulated(true);
			}
			wizardPage.setSelectedComponents(selectedComponents);
		}
		boolean canFinish = templateSelectionPage.getSelectedTemplate().getWizardPage().canFinish();
		if (canFinish == false){
			return false;
		}
		return super.performFinish();
	}

	@Override
	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		IProject project = entityCreatingWizardPage.getProject();
		pageCreator.setProject(project);
		pageCreator.setName(elementName);
		pageCreator.setDisplayName(templateSelectionPage.getDisplayName());
		pageCreator.setDescription(elementDesc);
		pageCreator.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
		pageCreator.setNamespace(pageCreator.getFolder());
		pageCreator.setTemplate(templateSelectionPage.getSelectedTemplate());
		LocalPage localPage = pageCreator.createPage();
		//synchronize/persist any additional elements created, before synchronizing the page
		List<LocalEntity> additionalElements = pageCreator.getAdditionalElements();
		for (LocalEntity localEntity : additionalElements) {
			localEntity.synchronize();
			DashboardResourceUtils.persistEntity(localEntity.getEObject(), baseURI, project, monitor);
		}
		//synchronize the local page
		localPage.synchronize();
		//persist local page
		DashboardResourceUtils.persistEntity(localPage.getEObject(), baseURI, project, monitor);
	}

}
