package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataSource;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public abstract class ComponentRefactoringParticipant extends DashboardRefactoringParticipant {

	protected ComponentRefactoringParticipant(String supportedExtension, String... interestingElements) {
		super(supportedExtension, interestingElements);
	}

	protected abstract List<LocalElement> getAffectedComponents(IProject project) throws Exception;

	protected abstract List<DataConfig> getDataConfigs(SeriesConfig seriesConfig);

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		if (object instanceof Component) {
			Component referredComponent = (Component) object;
			URI refactoredURI = createRefactoredURI(referredComponent);
			List<LocalElement> affectedLocalComponents = getAffectedComponents(getResource().getProject());
			for (LocalElement affectedLocalComponent : affectedLocalComponents) {
				Component affectedComponent = (Component) affectedLocalComponent.getEObject();
				Component copyAffectedComponent = (Component) EcoreUtil.copy(affectedComponent);
				if (updateRelatedComponentReferences(EcoreUtil.getURI(affectedComponent), copyAffectedComponent, referredComponent, refactoredURI) == true) {
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyAffectedComponent), copyAffectedComponent);
					compositeChange.add(change);
				}
			}
		} else if (object instanceof DataSource) {
			DataSource referredDataSource = (DataSource) object;
			URI refactoredURI = createRefactoredURI(referredDataSource);
			List<LocalElement> affectedLocalComponents = getAffectedComponents(getResource().getProject());
			for (LocalElement affectedLocalComponent : affectedLocalComponents) {
				Component affectedComponent = (Component) affectedLocalComponent.getEObject();
				Component copyAffectedComponent = (Component) EcoreUtil.copy(affectedComponent);
				if (updateDataSourceReferences(EcoreUtil.getURI(affectedComponent), copyAffectedComponent, referredDataSource, refactoredURI) == true) {
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyAffectedComponent), copyAffectedComponent);
					compositeChange.add(change);
				}
			}
		} else if (object instanceof PropertyDefinition) {
			PropertyDefinition referredProperty = (PropertyDefinition) object;
			String referredPropertyName = referredProperty.getName();
			String propertyNewName = getNewElementName();
			Metric referredMetric = (Metric) referredProperty.eContainer();
			List<LocalElement> dataSources = LocalECoreFactory.getInstance(getResource().getProject()).getChildren(BEViewsElementNames.DATA_SOURCE);
			for (LocalElement dataSourceElement : dataSources) {
				DataSource dataSource = (DataSource) dataSourceElement.getEObject();
				if (isUsing(dataSource, referredMetric)) {
					// For this metric, now process charts
					List<LocalElement> affectedLocalComponents = getAffectedComponents(getResource().getProject());
					for (LocalElement affectedLocalComponent : affectedLocalComponents) {
						Component affectedComponent = (Component) affectedLocalComponent.getEObject();
						Component copyAffectedComponent = (Component) EcoreUtil.copy(affectedComponent);
						if (updatePropertyDefinitionReferences(EcoreUtil.getURI(affectedComponent), copyAffectedComponent, dataSource, referredPropertyName, propertyNewName) == true) {
							Change change = createTextFileChange(IndexUtils.getFile(projectName, copyAffectedComponent), copyAffectedComponent);
							compositeChange.add(change);
						}
					}
				}
			}
		} else if (object instanceof Metric) {
			Metric referredMetric = (Metric) object;
			List<LocalElement> affectedComponents = getAffectedComponents(getResource().getProject());
			for (LocalElement affectedComponent : affectedComponents) {
				Component component = (Component) affectedComponent.getEObject();
				Component copyComponent = (Component) EcoreUtil.copy(component);
				if (updateMetricReference(EcoreUtil.getURI(component), copyComponent, referredMetric) == true) {
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyComponent), copyComponent);
					compositeChange.add(change);
				}
			}
		}
	}

	protected abstract boolean updateRelatedComponentReferences(URI affectedComponentURI, Component affectedComponent, Component referredComponent, URI refactoredURI);

	protected boolean updateDataSourceReferences(URI affectedComponentURI, Component affectedComponent, DataSource referredDataSource, URI refactoredURI) {
		boolean changed = false;
		EList<Visualization> visualizations = affectedComponent.getVisualization();
		for (Visualization visualization : visualizations) {
			EList<SeriesConfig> seriesConfigs = visualization.getSeriesConfig();
			for (SeriesConfig seriesConfig : seriesConfigs) {
				ActionRule actionRule = seriesConfig.getActionRule();
				DataSource dataSource = actionRule.getDataSource();
				if (checkEquals(referredDataSource, dataSource)) {
					DataSource proxyObject = (DataSource) createProxyToNewPath(refactoredURI, affectedComponentURI, referredDataSource.eClass());
					actionRule.setDataSource(proxyObject);
					changed = true;
				}
			}
		}
		return changed;
	}

	protected boolean updatePropertyDefinitionReferences(URI affectedComponentURI, Component affectedComponent, DataSource dataSource, String oldPropertyName, String newPropertyName) {
		boolean changed = false;
		EList<Visualization> copyVisualizations = affectedComponent.getVisualization();
		for (Visualization copyVisualization : copyVisualizations) {
			EList<SeriesConfig> copySeriesConfigs = copyVisualization.getSeriesConfig();
			for (SeriesConfig copySeriesConfig : copySeriesConfigs) {
				ActionRule copyActionRule = copySeriesConfig.getActionRule();
				DataSource copyDataSource = copyActionRule.getDataSource();
				if (checkEquals(dataSource, copyDataSource)) {
					// get all data configs
					List<DataConfig> copyDataConfigs = getDataConfigs(copySeriesConfig);
					for (DataConfig copyDataConfig : copyDataConfigs) {
						DataFormat copyFormatter = copyDataConfig.getFormatter();
						if (copyFormatter.getDisplayFormat() != null && copyFormatter.getDisplayFormat().contains(oldPropertyName) == true) {
							copyFormatter.setDisplayFormat(StringUtil.replaceAll(copyFormatter.getDisplayFormat(), oldPropertyName, newPropertyName));
							changed = true;
						}
						if (copyFormatter.getToolTipFormat() != null && copyFormatter.getToolTipFormat().trim().length() != 0 && copyFormatter.getToolTipFormat().contains(oldPropertyName) == true) {
							copyFormatter.setToolTipFormat(StringUtil.replaceAll(copyFormatter.getToolTipFormat(), oldPropertyName, newPropertyName));
							changed = true;
						}
					}
					
				}
			}
		}
		return changed;
	}

	protected boolean updateMetricReference(URI affectedComponentURI, Component affectedComponent, Metric referredMetric) {
		boolean changed = false;
		EList<Visualization> copyVisualizations = affectedComponent.getVisualization();
		for (Visualization copyVisualization : copyVisualizations) {
			EList<SeriesConfig> copySeriesConfigs = copyVisualization.getSeriesConfig();
			for (SeriesConfig copySeriesConfig : copySeriesConfigs) {
				ActionRule copyActionRule = copySeriesConfig.getActionRule();
				DataSource copyDataSource = copyActionRule.getDataSource();
				if (isUsing(copyDataSource, referredMetric)) {
					// get all data configs
					List<DataConfig> copyDataConfigs = getDataConfigs(copySeriesConfig);
					for (DataConfig copyDataConfig : copyDataConfigs) {
						EObject copySourceField = copyDataConfig.getExtractor().getSourceField();
						URI refactoredURI = createRefactoredURI(copySourceField);
						EObject proxyToNewPath = createProxyToNewPath(refactoredURI, affectedComponentURI, ElementPackage.eINSTANCE.getPropertyDefinition());
						copyDataConfig.getExtractor().setSourceField(proxyToNewPath);
					}
					// drillable fields
					List<PropertyDefinition> copyDrillableFields = new LinkedList<PropertyDefinition>(copyActionRule.getDrillableFields());
					int i = 0;
					for (PropertyDefinition copyDrillableField : copyDrillableFields) {
						URI refactoredURI = createRefactoredURI(copyDrillableField);
						EObject proxyToNewPath = createProxyToNewPath(refactoredURI, affectedComponentURI, ElementPackage.eINSTANCE.getPropertyDefinition());
						copyActionRule.getDrillableFields().set(i, (PropertyDefinition) proxyToNewPath);
						i++;
					}
					changed = true;
				}
			}
		}
		return changed;
	}

	private boolean isUsing(DataSource dataSource, Metric metric) {
		Metric referencedMetric = (Metric) dataSource.getSrcElement();
		if (referencedMetric != null) {
			return checkEquals(metric, referencedMetric);
		}
		return dataSource.getQuery().indexOf(metric.getFullPath()) != -1;
	}

	@Override
	protected void deleteReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		// figure out the type of delete
		boolean isRelatedComponent = object instanceof Component;
		boolean isDataSource = object instanceof DataSource;
		boolean isPropertyDefinition = object instanceof PropertyDefinition;
		boolean isMetric = object instanceof Metric;
		// search for users
		SearchResult searchResult = getSearchParticipant().search(object, projectName, null, new NullProgressMonitor());
		List<EObject> exactMatches = searchResult.getExactMatches();
		// update the users, if acceptable
		List<String> supportedExtensions = Arrays.asList(getSupportedExtensions());
		for (EObject match : exactMatches) {
			if (supportedExtensions.contains(BEViewsElementNames.getExtension(match.eClass().getName())) == false) {
				continue;
			}
			Component copyComponent = (Component) EcoreUtil.copy(match);
			if (isRelatedComponent == true) {
				if (deleteRelatedComponentReference(copyComponent, object) == true) {
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyComponent), copyComponent);
					compositeChange.add(change);
					break;
				}
			} else if (isDataSource == true) {
				if (deleteDataSourceReference(copyComponent, object) == true) {
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyComponent), copyComponent);
					compositeChange.add(change);
				}
			} else if (isPropertyDefinition == true) {
				if (deletePropertyDefinitionReference(copyComponent, object)) {
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyComponent), copyComponent);
					compositeChange.add(change);
				}
			} else if (isMetric == true) {
				if (deleteMetricReference(copyComponent, object)) {
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyComponent), copyComponent);
					compositeChange.add(change);
				}
			}
		}
	}

	protected boolean deleteMetricReference(Component affectedComponent, EObject metric) {
		boolean isChanged = false;
		EList<Visualization> copyVisualizations = affectedComponent.getVisualization();
		for (Visualization copyVisualization : copyVisualizations) {
			EList<SeriesConfig> copySeriesConfigs = copyVisualization.getSeriesConfig();
			ListIterator<SeriesConfig> seriesConfigsListIterator = copySeriesConfigs.listIterator();
			while (seriesConfigsListIterator.hasNext()) {
				SeriesConfig copySeriesConfig = seriesConfigsListIterator.next();
				ActionRule actionRule = copySeriesConfig.getActionRule();
				DataSource dataSource = actionRule.getDataSource();
				if (dataSource != null && checkEquals(metric, dataSource.getSrcElement()) == true) {
					// we will keep the series config , but empty all impacted fields
					actionRule.setDataSource(null);
					actionRule.getDrillableFields().clear();
					List<DataConfig> dataConfigs = getDataConfigs(copySeriesConfig);
					for (DataConfig dataConfig : dataConfigs) {
						dataConfig.getExtractor().setSourceField(null);
						DataFormat formatter = dataConfig.getFormatter();
						formatter.setDisplayFormat(null);
						formatter.setToolTipFormat(null);
					}
					isChanged = true;
				}
			}
		}
		return isChanged;
	}

	protected boolean deletePropertyDefinitionReference(Component affectedComponent, EObject propertyDefinition) {
		boolean isChanged = false;
		String regex = "\\{" + ((PropertyDefinition) propertyDefinition).getName() + "[,\\S]*\\}";
		EList<Visualization> copVisualizations = affectedComponent.getVisualization();
		for (Visualization copyVisualization : copVisualizations) {
			EList<SeriesConfig> copySeriesConfigs = copyVisualization.getSeriesConfig();
			for (SeriesConfig copySeriesConfig : copySeriesConfigs) {
				ActionRule actionRule = copySeriesConfig.getActionRule();
				DataSource dataSource = actionRule.getDataSource();
				if (checkEquals(propertyDefinition.eContainer(), dataSource.getSrcElement()) == true) {
					List<DataConfig> dataConfigs = getDataConfigs(copySeriesConfig);
					for (DataConfig dataConfig : dataConfigs) {
						if (checkEquals(propertyDefinition, dataConfig.getExtractor().getSourceField()) == true) {
							dataConfig.getExtractor().setSourceField(null);
						}
						DataFormat formatter = dataConfig.getFormatter();
						String originalFormat = formatter.getDisplayFormat();
						String updatedFormat = originalFormat.replaceAll(regex, "");
						if (updatedFormat.equals(originalFormat) == false) {
							formatter.setDisplayFormat(updatedFormat);
							isChanged = true;
						}
						originalFormat = formatter.getToolTipFormat();
						updatedFormat = originalFormat.replaceAll(regex, "");
						if (updatedFormat.equals(originalFormat) == false) {
							formatter.setToolTipFormat(updatedFormat);
							isChanged = true;
						}
					}
				}
				// drillable fields update
				ListIterator<PropertyDefinition> drillableFieldsListIterator = actionRule.getDrillableFields().listIterator();
				while (drillableFieldsListIterator.hasNext()) {
					PropertyDefinition drillableField = drillableFieldsListIterator.next();
					if (checkEquals(propertyDefinition, drillableField) == true) {
						drillableFieldsListIterator.remove();
						isChanged = true;
						break;
					}
				}
			}
		}
		return isChanged;
	}

	protected boolean deleteDataSourceReference(Component affectedComponent, EObject referredDataSource) {
		boolean isChanged = false;
		EList<Visualization> copyVisualizations = affectedComponent.getVisualization();
		for (Visualization copyVisualization : copyVisualizations) {
			EList<SeriesConfig> copySeriesConfigs = copyVisualization.getSeriesConfig();
			for (SeriesConfig copySeriesConfig : copySeriesConfigs) {
				ActionRule actionRule = copySeriesConfig.getActionRule();
				DataSource dataSource = actionRule.getDataSource();
				if (checkEquals(referredDataSource, dataSource) == true) {
					// we will keep the series config , but empty all impacted fields
					actionRule.setDataSource(null);
					actionRule.getDrillableFields().clear();
					List<DataConfig> dataConfigs = getDataConfigs(copySeriesConfig);
					for (DataConfig dataConfig : dataConfigs) {
						dataConfig.getExtractor().setSourceField(null);
						DataFormat formatter = dataConfig.getFormatter();
						formatter.setDisplayFormat(null);
						formatter.setToolTipFormat(null);
					}
					isChanged = true;
				}
			}
		}
		return isChanged;
	}

	protected abstract boolean deleteRelatedComponentReference(Component component, EObject relatedComponent);

}