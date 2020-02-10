package com.tibco.cep.studio.dashboard.ui.wizards.skin;

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
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalChartComponentColorSet;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseMultiPageViewsWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class ChartColorSetWizard extends BaseMultiPageViewsWizard {

	private EntityFileCreationWizard chartColorSetCreationPage;

	private ChartColorSetWizardSeriesColorPage seriesColorPage;

	private LocalChartComponentColorSet chartComponentColorSet;

	public ChartColorSetWizard(){
		 super(BEViewsElementNames.CHART_COLOR_SET, "Chart Color Set", "New Chart Color Set Wizard", "ChartComponentColorSetPage", "New Chart Color Set ", "Create a new Chart Color Set");
		 setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("chartcomponentcolorset_wizard.png"));
	 }

	 @Override
	public void addPages() {
		super.addPages();
		seriesColorPage = new ChartColorSetWizardSeriesColorPage();
		seriesColorPage.setTitle("New Chart Color Set");
		seriesColorPage.setDescription("Select Series Colors...");
		addPage(seriesColorPage);
	}

	 @Override
	protected EntityFileCreationWizard createPage() {
		chartColorSetCreationPage = super.createPage();
		return chartColorSetCreationPage;
	}

	@Override
	protected void pageIsAboutToBeHidden(IWizardPage page) {
		if (page == chartColorSetCreationPage) {
			if (chartComponentColorSet == null) {
				chartComponentColorSet = createChartComponentColorSet(chartColorSetCreationPage.getFileName(),  chartColorSetCreationPage.getTypeDesc());
			}
		}
	}

	@Override
	protected void pageIsAboutToBeShown(IWizardPage page) {
		if (page == seriesColorPage) {
			seriesColorPage.setLocalChartComponentColorSet(chartComponentColorSet);
		}
	}

	@Override
	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		if (chartComponentColorSet == null) {
			chartComponentColorSet = createChartComponentColorSet(chartColorSetCreationPage.getFileName(),  chartColorSetCreationPage.getTypeDesc());
		}
		// when user comes back by hitting BACK button, and change the data source name or description.
		chartComponentColorSet.setName(entityCreatingWizardPage.getFileName());
		chartComponentColorSet.setDescription(entityCreatingWizardPage.getTypeDesc());
		chartComponentColorSet.synchronize();
		// Persist the element
		DashboardResourceUtils.persistEntity(chartComponentColorSet.getEObject(), baseURI, entityCreatingWizardPage.getProject(), monitor);
	}

	private LocalChartComponentColorSet createChartComponentColorSet(String elementName, String elementDesc){
		IProject project = chartColorSetCreationPage.getProject();

		LocalChartComponentColorSet chartComponentColorSet = (LocalChartComponentColorSet) LocalECoreFactory.getInstance(project).createLocalElement(elementType);
		chartComponentColorSet.getID();
		chartComponentColorSet.setName(elementName);
		chartComponentColorSet.setDescription(elementDesc);
		chartComponentColorSet.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
		chartComponentColorSet.setNamespace(DashboardResourceUtils.getFolder(getModelFile()));
		chartComponentColorSet.setOwnerProject(project.getName());

		//add series color
		List<LocalElement> seriesColors = getSeriesColorsToUse(chartComponentColorSet);
		if (seriesColors.isEmpty() == false) {
			for (LocalElement seriesColor : seriesColors) {
				chartComponentColorSet.addElement(BEViewsElementNames.SERIES_COLOR, seriesColor);
			}
		}

		return chartComponentColorSet;
	}

	@SuppressWarnings("rawtypes")
	private List<LocalElement> getSeriesColorsToUse(LocalChartComponentColorSet chartComponentColorSet) {
		List<LocalElement> seriesColors = new LinkedList<LocalElement>();
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
							if (element.getElementType().compareTo(ELEMENT_TYPES.SERIES_COLOR) == 0) {
								seriesColors.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(entityCreatingWizardPage.getProject()), entityElement.getEntity()));
							}
//							else {
//								seriesColors.clear();
//								break;
//							}
						}
					}
				}
			}
		}
		if (seriesColors.isEmpty() == true) {
			List<LocalElement> availableSeriesColor = chartComponentColorSet.getRoot().getChildren(BEViewsElementNames.SERIES_COLOR);
			if (availableSeriesColor.isEmpty() == false) {
				seriesColors.add(availableSeriesColor.get(0));
			}
		}
		return seriesColors;
	}
}