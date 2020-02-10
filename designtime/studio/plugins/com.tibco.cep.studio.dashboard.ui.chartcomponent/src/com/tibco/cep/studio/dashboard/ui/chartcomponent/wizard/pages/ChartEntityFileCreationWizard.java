package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.wizards.DashboardEntityFileCreationWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartEntityFileCreationWizard extends DashboardEntityFileCreationWizard {

	private List<LocalDataSource> dataSources;

	public ChartEntityFileCreationWizard(String pageName, IStructuredSelection selection, String type, String typeName) {
		super(pageName, selection, type, typeName);
		dataSources = new LinkedList<LocalDataSource>();
	}

	@Override
	protected boolean validatePage() {
		boolean validatePage = super.validatePage();
		if (validatePage == true) {
			try {
				LocalECoreFactory localECoreFactory = LocalECoreFactory.getInstance(getProject());
				retrieveDataSources(localECoreFactory);
				if (dataSources.isEmpty() == true){
					if (localECoreFactory.getChildren(BEViewsElementNames.METRIC).isEmpty() == true){
						setMessage("No metrics were found...", ERROR);
						validatePage = false;
					}
					else {
						setMessage("No data sources were found...", ERROR);
						validatePage = false;
					}
				}
			} catch (Exception e) {
				IStatus status = new Status(IStatus.WARNING, DashboardChartPlugin.PLUGIN_ID, "could not get default data source in chart component wizard for " + _selection, e);
				DashboardChartPlugin.getDefault().getLog().log(status);
				setMessage("could not find default data source...", IStatus.WARNING);
				setPageComplete(false);
				return false;
			}
		}
		return validatePage;
	}

	@SuppressWarnings("rawtypes")
	private void retrieveDataSources(LocalECoreFactory localECoreFactory) throws Exception{
		dataSources.clear();
		if (_selection != null) {
			//do we have selections
			Iterator iterator = _selection.iterator();
			while (iterator.hasNext()) {
				Object object = (Object) iterator.next();
				if (object instanceof IResource) {
					IResource resource = (IResource) object;
					//is the selection within the current project
					if (resource.getProject().equals(localECoreFactory.getProject()) == true) {
						//yes, it is, is it a data source
						DesignerElement element = IndexUtils.getElement(resource);
						if (element != null && element.getElementType().compareTo(ELEMENT_TYPES.DATA_SOURCE) == 0) {
							//yes it is, so we can use it
							dataSources.add((LocalDataSource) LocalECoreFactory.toLocalElement(localECoreFactory, ((EntityElement)element).getEntity()));
						}
//						else {
//							//we got disparate selections, we will use default data source
//							dataSources.clear();
//							break;
//						}
					}
				}
			}
		}
		if (dataSources.isEmpty() == true) {
			LocalDataSource defaultDataSource = StateMachineComponentHelper.getDefaultDataSource(localECoreFactory);
			if (defaultDataSource != null) {
				dataSources.add(defaultDataSource);
			}
		}
	}

	public List<LocalDataSource> getDataSources() {
		return dataSources;
	}

}
