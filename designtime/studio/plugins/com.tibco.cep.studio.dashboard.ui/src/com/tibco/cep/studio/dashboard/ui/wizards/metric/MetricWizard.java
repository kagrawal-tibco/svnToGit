package com.tibco.cep.studio.dashboard.ui.wizards.metric;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.utils.Messages;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseMultiPageViewsWizard;
import com.tibco.cep.studio.dashboard.ui.wizards.DashboardEntityFileCreationWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class MetricWizard extends BaseMultiPageViewsWizard {

	private DashboardEntityFileCreationWizard metricCreationWizardPage;

	private MetricFieldModificationWizardPage groupByFieldWizardPage;

	private MetricFieldModificationWizardPage aggrFieldWizardPage;

	private LocalMetric metric;

	public MetricWizard() {
		super(BEViewsElementNames.METRIC, "Metric", "New Metric Wizard", "MetricPage", "New Metric", "Create a New Metric");
		setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("metric_wizard.png"));
	}

	@Override
	public void addPages() {
		super.addPages();
		groupByFieldWizardPage = new MetricFieldModificationWizardPage(MetricFieldModificationWizardPage.FIELD_TYPE.GROUP_BY);
		groupByFieldWizardPage.setTitle("New Metric");
		groupByFieldWizardPage.setDescription("Add new group by fields");
		addPage(groupByFieldWizardPage);
		aggrFieldWizardPage = new MetricFieldModificationWizardPage(MetricFieldModificationWizardPage.FIELD_TYPE.AGGR);
		aggrFieldWizardPage.setTitle("New Metric");
		aggrFieldWizardPage.setDescription("Add new aggregation fields");
		addPage(aggrFieldWizardPage);
	}

	@Override
	protected EntityFileCreationWizard createPage() {
		metricCreationWizardPage = new DashboardEntityFileCreationWizard("MetricPage", _selection, BEViewsElementNames.METRIC, BEViewsElementNames.METRIC);
		metricCreationWizardPage.setDescription(Messages.getString("new.metric.wizard.desc"));
		metricCreationWizardPage.setTitle(Messages.getString("new.metric.wizard.page.title"));
		return metricCreationWizardPage;
	}

	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		groupByFieldWizardPage.setProcessFieldModifications(false);
		aggrFieldWizardPage.setProcessFieldModifications(false);
		if(metric == null){
			//Finished from first page.
			metric = createMetric(elementName, elementDesc);
		}
		List<LocalElement> groupByFields = new LinkedList<LocalElement>();
		List<LocalElement> aggrFields = new LinkedList<LocalElement>();
		seggregateFields(metric, groupByFields, aggrFields);
		if (groupByFields.isEmpty() == true) {
			metric.createLocalElement(LocalMetric.ELEMENT_KEY_FIELD);
		}
		if (aggrFields.isEmpty() == true) {
			metric.createLocalElement(LocalMetric.ELEMENT_KEY_FIELD);
		}
		List<LocalElement> fields = new LinkedList<LocalElement>(groupByFields);
		fields.addAll(aggrFields);
		int i = 0;
		for (LocalElement field : fields) {
			field.setSortingOrder(i);
			i++;
		}
		// when user comes back by hitting BACK button, and change the data source name or description.
		metric.setName(entityCreatingWizardPage.getFileName());
		metric.setDescription(entityCreatingWizardPage.getTypeDesc());
		metric.synchronize();
		// Persist the element
		DashboardResourceUtils.persistEntity(metric.getEObject(), baseURI, entityCreatingWizardPage.getProject(), monitor);
	}

	private LocalMetric createMetric(String elementName, String elementDesc){
		IProject project = entityCreatingWizardPage.getProject();
		LocalMetric localMetric = (LocalMetric) LocalECoreFactory.getInstance(project).createLocalElement(elementType);
		localMetric.getID();
		localMetric.setName(elementName);
		localMetric.setDescription(elementDesc);
		localMetric.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
		localMetric.setNamespace(DashboardResourceUtils.getFolder(getModelFile()));
		localMetric.setOwnerProject(project.getName());
		return localMetric;
	}

	@Override
	protected void pageIsAboutToBeHidden(IWizardPage page) {
		if (page == metricCreationWizardPage) {
			if (metric == null) {
				metric = createMetric(metricCreationWizardPage.getFileName(),  metricCreationWizardPage.getTypeDesc());
			}
		}
		else if (page == groupByFieldWizardPage) {
			groupByFieldWizardPage.setProcessFieldModifications(false);
		}
		else if (page == aggrFieldWizardPage) {
			aggrFieldWizardPage.setProcessFieldModifications(false);
		}
	}

	@Override
	protected void pageIsAboutToBeShown(IWizardPage page) {
		List<LocalElement> groupByFields = new LinkedList<LocalElement>();
		List<LocalElement> aggrFields = new LinkedList<LocalElement>();
		seggregateFields(metric, groupByFields, aggrFields);
		if (page == groupByFieldWizardPage) {
			if (groupByFields.isEmpty() == true) {
				metric.createLocalElement(LocalMetric.ELEMENT_KEY_FIELD);
			}
			groupByFieldWizardPage.setProcessFieldModifications(true);
			groupByFieldWizardPage.setLocalMetric(metric);
		}
		else if (page == aggrFieldWizardPage) {
			if (aggrFields.isEmpty() == true) {
				metric.createLocalElement(LocalMetric.ELEMENT_KEY_FIELD);
			}
			aggrFieldWizardPage.setProcessFieldModifications(true);
			aggrFieldWizardPage.setLocalMetric(metric);
		}
	}

	private void seggregateFields(LocalMetric metric, List<LocalElement> groupByFields, List<LocalElement> aggrFields) {
		List<LocalElement> fields = metric.getChildren(LocalMetric.ELEMENT_KEY_FIELD);
		for (LocalElement field : fields) {
			if (field.getPropertyValue(LocalMetricField.PROP_KEY_IS_GROUP_BY).equalsIgnoreCase("true") == true) {
				groupByFields.add(field);
			}
			else {
				aggrFields.add(field);
			}
		}
	}

}
