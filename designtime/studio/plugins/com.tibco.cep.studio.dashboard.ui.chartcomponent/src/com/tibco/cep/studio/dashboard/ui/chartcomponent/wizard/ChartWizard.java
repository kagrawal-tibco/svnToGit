package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.ChartEditingController;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.preview.ChartPreviewController;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.preview.ChartPreviewForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages.ChartDesignTimeDataWizardPage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages.ChartEntityFileCreationWizard;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages.ChartOptionsWizardPage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages.ChartRunTimeDataWizardPage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages.ChartTypeWizardPage;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseMultiPageViewsWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class ChartWizard extends BaseMultiPageViewsWizard {

	private ChartEntityFileCreationWizard chartCreationWizardPage;

	private ChartEditingController chartEditingController;

	private ChartPreviewController chartPreviewController;

	private String lastChartName;

	private List<LocalDataSource> lastUsedDataSources;

	private boolean canFinish;

	private LocalElement synchronizedElement;

	public ChartWizard() {
		super(BEViewsElementNames.CHART_COMPONENT, "Chart", "New Chart Wizard", "ChartPage", "New Chart", "Create a new Chart");
		setDefaultPageImageDescriptor(DashboardChartPlugin.getDefault().getImageRegistry().getDescriptor("chartcomponent_wizard.png"));
		chartEditingController = new WizardSpecificChartEditingController();
		chartPreviewController = new WizardSpecificChartPreviewController(null);
		canFinish = true;
	}

	@Override
	public void addPages() {
		super.addPages();
		try {
			//type page is the master page
			ChartTypeWizardPage typePage = new ChartTypeWizardPage();
			addPage(typePage);
			chartEditingController.setMasterPage(typePage);
			//options page is slave page
			ChartOptionsWizardPage optionsPage = new ChartOptionsWizardPage();
			addPage(optionsPage);
			chartEditingController.addSlavePage(optionsPage);
			//design time page is slave page
			ChartDesignTimeDataWizardPage designTimePage = new ChartDesignTimeDataWizardPage();
			addPage(designTimePage);
			chartEditingController.addSlavePage(designTimePage);
			//run time page is slave page
			ChartRunTimeDataWizardPage runTimePage = new ChartRunTimeDataWizardPage();
			addPage(runTimePage);
			chartEditingController.addSlavePage(runTimePage);
		} catch (Exception e) {
			chartEditingController.logAndAlert(getWindowTitle(), new Status(IStatus.ERROR, chartEditingController.getPluginId(),"could not add pages to chart wizard",e));
			canFinish = false;
		}
	}

	@Override
	public boolean canFinish() {
		if (canFinish == false){
			return false;
		}
		return super.canFinish();
	}

	@Override
	protected EntityFileCreationWizard createPage() {
		chartCreationWizardPage = new ChartEntityFileCreationWizard(pageName, _selection, elementType, elementTypeName);
		chartCreationWizardPage.setDescription(pageDesc);
		chartCreationWizardPage.setTitle(pageTitle);
		return chartCreationWizardPage;

	}

	@Override
	protected void pageIsAboutToBeHidden(IWizardPage page) {
		try {
			if (page == chartCreationWizardPage) {
				createChart();
			}
		} catch (Exception e) {
			chartEditingController.logAndAlert(getWindowTitle(), new Status(IStatus.ERROR, chartEditingController.getPluginId(),"could not create a chart",e));
			canFinish = false;
		}
	}

	private void createChart() throws Exception {
		String chartName = chartCreationWizardPage.getFileName();
		String folder = DashboardResourceUtils.getFolder(chartCreationWizardPage.getModelFile());
		List<LocalDataSource> dataSourcesToUse = chartCreationWizardPage.getDataSources();
		if (dataSourcesToUse.equals(lastUsedDataSources) == false){
			//we have a new default data source
			//create a new local chart component
			String project = chartCreationWizardPage.getProject().getName();
			LocalUnifiedComponent chart = ChartCreator.createChart(LocalECoreFactory.getInstance(chartCreationWizardPage.getProject()), project, folder, folder, chartName, chartCreationWizardPage.getDataSources());
			chart.setDescription(chartCreationWizardPage.getTypeDesc());
			chartEditingController.setOriginalElement(chart);
			lastUsedDataSources = chartCreationWizardPage.getDataSources();
			lastChartName = chartName;
			//pass the created chart to chart preview controller
			chartPreviewController.setOriginalElement(chart);
			lastUsedDataSources = new LinkedList<LocalDataSource>(dataSourcesToUse);
		}
		else {
			//Modified by Anand to fix BE-11269 on 04/12/2011
			LocalUnifiedComponent chart = (LocalUnifiedComponent) chartEditingController.getOriginalElement();
			if (lastChartName.equals(chart.getPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME)) == true) {
				chart.setPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME, chartName);
				lastChartName = chartName;
			}
			//Added the below line to fix BE-11269
			chart.setName(chartName);
			chart.setFolder(folder);
			chart.setNamespace(folder);
		}
	}

	@Override
	protected void pageIsAboutToBeShown(IWizardPage page) {
		if (page instanceof ChartWizardBasePage) {
			ChartWizardBasePage chartWizardPage = (ChartWizardBasePage) page;
			chartWizardPage.setInput(chartEditingController.getOriginalElement());
			chartEditingController.setActivePage(chartWizardPage);
			chartPreviewController.setChartPreviewForm(chartWizardPage.getChartPreviewForm());
		}
	}

	@Override
	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		createChart();
		synchronizedElement = chartEditingController.synchronize();
		//persist chart
		DashboardResourceUtils.persistEntity((Entity) synchronizedElement.getEObject(), baseURI, entityCreatingWizardPage.getProject(), monitor);
	}

	class WizardSpecificChartEditingController extends ChartEditingController {

		@Override
		public void logAndAlert(String title, IStatus status) {
			log(status);
			switch (status.getSeverity()) {
				case IStatus.OK:
				case IStatus.INFO:
					((WizardPage)getContainer().getCurrentPage()).setMessage(status.getMessage(), WizardPage.INFORMATION);
					break;
				case IStatus.ERROR:
					((WizardPage)getContainer().getCurrentPage()).setMessage(status.getMessage(), WizardPage.ERROR);
					// we have an error
					disableAllPages();
					canFinish = false;
					break;
				case IStatus.WARNING:
					((WizardPage)getContainer().getCurrentPage()).setMessage(status.getMessage(), WizardPage.WARNING);
					break;
			}
			getContainer().updateMessage();

		}
	}

	class WizardSpecificChartPreviewController extends ChartPreviewController {

		public WizardSpecificChartPreviewController(ChartPreviewForm chartPreviewForm) {
			super(chartPreviewForm);
		}

		@Override
		public void logAndAlert(String title, IStatus status) {
			log(status);
			switch (status.getSeverity()) {
				case IStatus.OK:
				case IStatus.INFO:
					((WizardPage)getContainer().getCurrentPage()).setMessage(status.getMessage(), WizardPage.INFORMATION);
					break;
				case IStatus.ERROR:
					((WizardPage)getContainer().getCurrentPage()).setMessage(status.getMessage(), WizardPage.ERROR);
					canFinish = false;
					break;
				case IStatus.WARNING:
					((WizardPage)getContainer().getCurrentPage()).setMessage(status.getMessage(), WizardPage.WARNING);
					break;
			}
			getContainer().updateMessage();
		}
	}
}
