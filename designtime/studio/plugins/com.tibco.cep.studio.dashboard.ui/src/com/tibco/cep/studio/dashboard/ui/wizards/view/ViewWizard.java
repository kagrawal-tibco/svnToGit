package com.tibco.cep.studio.dashboard.ui.wizards.view;

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
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseMultiPageViewsWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 * @author rmayala
 *
 */
public class ViewWizard extends BaseMultiPageViewsWizard {

	private ViewPageSelectionWizardPage selectionPage;

	private LocalView localView;

	/**
	 *  To check whether BACK button is pressed and some change on first page.
	 */
	private boolean isBackActivity = false;

	public ViewWizard() {
		super(BEViewsElementNames.VIEW, BEViewsElementNames.VIEW, "New View Wizard", "ViewPage", "New View", "Create New View");
		setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("view_wizard.png"));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tibco.cep.studio.dashboard.ui.wizards.views.BaseViewsElementWizard#addPages()
	 */
	public void addPages() {
		super.addPages();
		// Add page selection page.
		selectionPage = new ViewPageSelectionWizardPage("DashboardPageSelectionPage");
		selectionPage.setTitle("New View");
		selectionPage.setDescription("Select page(s) to add to the View");
		addPage(selectionPage);

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.dashboard.ui.wizards.views.BaseViewsElementWizard#persistFirstPage(java.lang.String, java.lang.String, java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		if(localView == null){
			// Finished from first page.
			localView = createLocalView(elementName, elementDesc);
		}

		if (isBackActivity) {
			// when user comes back by hitting BACK button, and change the view name or description etc.
			localView.setName(entityCreatingWizardPage.getFileName());
			localView.setDescription(entityCreatingWizardPage.getTypeDesc());
			localView.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
			localView.setNamespace(DashboardResourceUtils.getFolder(getModelFile()));
			localView.setOwnerProject(entityCreatingWizardPage.getProject().getName());
		}

		localView.synchronize();
		// Persist the element
		DashboardResourceUtils.persistEntity(localView.getEObject(), baseURI, entityCreatingWizardPage.getProject(), monitor);

	}

	private LocalView createLocalView(String elementName, String elementDesc){
		IProject project = entityCreatingWizardPage.getProject();
		LocalView view = (LocalView) LocalECoreFactory.getInstance(project).createLocalElement(elementType);
		view.getID();
		view.setName(elementName);
		view.setDescription(elementDesc);
		view.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
		view.setNamespace(DashboardResourceUtils.getFolder(getModelFile()));
		view.setOwnerProject(project.getName());
		//add dashboard pages
		List<LocalElement> pages = getPagesToUse(view);
		if (pages.isEmpty() == false) {
			for (LocalElement page : pages) {
				view.addElement(BEViewsElementNames.PAGE, page);
			}
			view.addElement("DefaultPage", pages.get(0));
		}
		return view;
	}

	@Override
	protected void pageIsAboutToBeShown(IWizardPage page) {
		if (page == selectionPage) {
			// Next button clicked(Entered on DashboardPage selection), create localConfig instance.
			if (localView == null) {
				localView = createLocalView(entityCreatingWizardPage.getFileName(), entityCreatingWizardPage.getTypeDesc());
			}
			selectionPage.setLocalConfig(localView);
		}
	}

	@Override
	protected void pageIsAboutToBeHidden(IWizardPage page) {
		if (page == selectionPage) {
			// BACK button pressed
			isBackActivity = true;
		}

	}

	@SuppressWarnings("rawtypes")
	private List<LocalElement> getPagesToUse(LocalView view) {
		List<LocalElement> dashboardPages = new LinkedList<LocalElement>();
		if (_selection != null) {
			Iterator iterator = _selection.iterator();
			while (iterator.hasNext()) {
				Object object = (Object) iterator.next();
				if (object instanceof IResource) {
					IResource resource = (IResource) object;
					//is the selection within the current project
					if (resource.getProject().equals(entityCreatingWizardPage.getProject()) == true) {
						//yes, it is, is it a series color
						DesignerElement element = IndexUtils.getElement(resource);
						if (element != null && element instanceof EntityElement) {
							EntityElement entityElement = (EntityElement) element;
							if (element.getElementType().compareTo(ELEMENT_TYPES.DASHBOARD_PAGE) == 0) {
								dashboardPages.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(entityCreatingWizardPage.getProject()), entityElement.getEntity()));
							}
//							else {
//								dashboardPages.clear();
//								break;
//							}
						}
					}
				}
			}
		}
		if (dashboardPages.isEmpty() == true) {
			List<LocalElement> availableDashboardPages = view.getRoot().getChildren(BEViewsElementNames.DASHBOARD_PAGE);
			if (availableDashboardPages.isEmpty() == false) {
				dashboardPages.add(availableDashboardPages.get(0));
			}
		}
		return dashboardPages;
	}

}
