package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.beviewsconfig.DataSource;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class DataSourceRefactoringParticipant extends DashboardRefactoringParticipant {

	public DataSourceRefactoringParticipant() {
		super(BEViewsElementNames.EXT_DATA_SOURCE, BEViewsElementNames.METRIC);
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange)
			throws Exception {
		if (object instanceof Metric){
			URI refactoredURI = createRefactoredURI(object);
			Metric referredMetric = (Metric) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> dataSources = coreFactory.getChildren(BEViewsElementNames.DATA_SOURCE);
			for (LocalElement dataSourceElement : dataSources) {
				DataSource dataSource = (DataSource) dataSourceElement.getEObject();
				DataSource copyElement = (DataSource) EcoreUtil.copy(dataSource);
				if (isUsing(copyElement, referredMetric)){
					Metric proxyObject = (Metric)createProxyToNewPath(refactoredURI, dataSource.eResource().getURI(), referredMetric.eClass());
					//update src element 
					copyElement.setSrcElement(proxyObject);
					//update query 
					String query = copyElement.getQuery();
					String referredMetricFullPath = referredMetric.getFullPath();
					int i = query.indexOf(referredMetricFullPath);
					if (i != -1){
						StringBuilder newPath = new StringBuilder();
						if (isRenameRefactor() == true){
							newPath.append(referredMetric.getFolder());
							newPath.append(getNewElementName());
						}
						else if (isMoveRefactor() == true){
							newPath.append(getNewElementPath());
							newPath.append(referredMetric.getName());
						}
						StringBuilder sb = new StringBuilder(query);
						sb.replace(i,i+referredMetricFullPath.length(),newPath.toString());
						copyElement.setQuery(sb.toString());
					}
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyElement), copyElement);
					compositeChange.add(change);
				}
			}
		}
		if (object instanceof PropertyDefinition){
			PropertyDefinition referredProperty = (PropertyDefinition) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> dataSources = coreFactory.getChildren(BEViewsElementNames.DATA_SOURCE);
			for (LocalElement dataSourceElement : dataSources) {
				DataSource dataSource = (DataSource) dataSourceElement.getEObject();
				DataSource copiedDatasource = (DataSource) EcoreUtil.copy(dataSource);
				Metric referredMetric = (Metric) referredProperty.eContainer();
				if (isUsing(copiedDatasource, referredMetric)){
					//update the query replace old field name with new field name
					String referredPropertyName = referredProperty.getName();
					String propertyNewName = getNewElementName();
					copiedDatasource.setQuery(StringUtil.replaceAll(copiedDatasource.getQuery(), referredPropertyName, propertyNewName));
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copiedDatasource), copiedDatasource);
					compositeChange.add(change);
				}
			}
		}
		return;
	}

	private boolean isUsing(DataSource dataSource, Metric metric) {
		Metric referencedMetric = (Metric) dataSource.getSrcElement();
		if (referencedMetric != null) {
			return checkEquals(metric, referencedMetric);
		}
		return dataSource.getQuery().indexOf(metric.getFullPath()) != -1;
	}
	
	@Override
	protected String changeTitle() {
		return "Data Source Changes:";
	}

	@Override
	protected void deleteReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		if (object instanceof Metric){
			Metric deletedMetric = (Metric) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> dataSources = coreFactory.getChildren(BEViewsElementNames.DATA_SOURCE);
			for (LocalElement dataSourceElement : dataSources) {
				DataSource dataSource = (DataSource) dataSourceElement.getEObject();
				DataSource copyDataSource = (DataSource) EcoreUtil.copy(dataSource);
				if (isUsing(copyDataSource, deletedMetric)){
					//nullify the source element reference, since this data source is not usable
					copyDataSource.setSrcElement(null);
					//maintain the query and params , may be user can switch the metric and make the query usable
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyDataSource), copyDataSource);
					compositeChange.add(change);
				}
			}
		}
		else if (object instanceof PropertyDefinition) {
			//INFO we will not do anything here since it is difficult to remove a field from the query text 
		}
	}

}
