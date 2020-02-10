package com.tibco.cep.studio.dashboard.ui.editors.views.datasource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalQueryParam;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalVisualization;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractDBFormEditor;
import com.tibco.cep.studio.dashboard.ui.utils.BEViewsURIHandler;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 *
 * @author rgupta
 */
public class DataSourceEditor extends AbstractDBFormEditor {

	private List<QueryParamInfo> originalQueryParamsInfo;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		try {
			originalQueryParamsInfo = getQueryParamInfo();
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not create initial list of query parameters for " + input, e));
		} finally {
			try {
				getLocalElement().setExisting();
			} catch (Exception ignoreEx) {
			}
		}
	}

	private List<QueryParamInfo> getQueryParamInfo() throws Exception {
		List<LocalElement> queryParams = getLocalElement().getChildren(LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
		List<QueryParamInfo> queryParamsInfo = new ArrayList<QueryParamInfo>(queryParams.size());
		for (LocalElement queryParam : queryParams) {
			queryParamsInfo.add(new QueryParamInfo(queryParam.getName(),queryParam.getPropertyValue(LocalQueryParam.PROP_KEY_TYPE)));
		}
		return queryParamsInfo;
	}

	@Override
	protected void addPages() {
		try {
			addPage(new DataSourcePage(this, getLocalElement()));
		} catch (PartInitException e) {
			Status status = new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not initialize editor for " + getEditorInput().getName(), e);
			DashboardUIPlugin.getInstance().getLog().log(status);
			// TODO do something to prevent any changes to the editors
		}
	}

	@Override
	protected String[] getInterestingElementTypes() {
		return new String[] { BEViewsElementNames.METRIC };
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		try {
			if (getLocalElement().isValid() == false) {
				// the data source is not valid, skip query params sync
				return;
			}
			if (originalQueryParamsInfo != null) {
				List<QueryParamInfo> newQueryParamsInfo = getQueryParamInfo();
				BEViewsElement element = (BEViewsElement) getLocalElement().getEObject();
				if (areQueryParamsSame(originalQueryParamsInfo, newQueryParamsInfo) == false) {
					// we have a discrepancy , find what is different
					List<QueryParamInfo> queryParamsToDelete = new LinkedList<QueryParamInfo>(originalQueryParamsInfo);
					queryParamsToDelete.removeAll(newQueryParamsInfo);
					List<QueryParamInfo> queryParamsToAdd = new LinkedList<QueryParamInfo>(newQueryParamsInfo);
					queryParamsToAdd.removeAll(originalQueryParamsInfo);
					// find all users of this data source
					IProject project = StudioResourceUtils.getProjectForInput(getEditorInput());
					String baseURI = DashboardResourceUtils.getCurrentProjectBaseURI(project);
					LocalECoreFactory localECoreFactory = LocalECoreFactory.getInstance(project);
					XMIResource.URIHandler handler = new BEViewsURIHandler();
					SearchResult searchResult = SearchUtils.searchIndex(element, project.getName(), element.getName(), null);
					List<EObject> exactMatches = searchResult.getExactMatches();
					for (EObject object : exactMatches) {
						LocalElement referringElement = LocalECoreFactory.toLocalElement(localECoreFactory, object);
						if (referringElement instanceof LocalComponent) {
							monitor.subTask("Updating "+referringElement.getFolder()+referringElement.getName()+"...");
							LocalECoreUtils.loadFully(referringElement, true, true);
							syncQueryParams(referringElement, queryParamsToAdd, queryParamsToDelete);
							if (referringElement.isModified() == true) {
								try {
									referringElement.synchronize();
									Entity referringEObject = (Entity) referringElement.getEObject();
									DashboardResourceUtils.persistEntity(referringEObject, baseURI, project, handler, null);
									IResource resource = project.findMember(referringEObject.getFullPath() + "." + LocalECoreUtils.getExtensionFor(referringEObject));
									if (resource != null) {
										resource.refreshLocal(IResource.DEPTH_ZERO, null);
									}
								} catch (Exception e) {
									DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not re-sync query parameters for " + referringElement, e));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not re-sync query parameters changes in " + getEditorInput(), e));
		} finally {
			try {
				originalQueryParamsInfo = getQueryParamInfo();
			} catch (Exception ignoreex) {
				originalQueryParamsInfo = null;
			}
		}
	}

	private boolean areQueryParamsSame(List<QueryParamInfo> oldQueryParams, List<QueryParamInfo> newQueryParams) {
		return new TreeSet<QueryParamInfo>(oldQueryParams).equals(new TreeSet<QueryParamInfo>(newQueryParams));
	}

	private void syncQueryParams(LocalElement referringElement, List<QueryParamInfo> queryParamsToAdd, List<QueryParamInfo> queryParamsToDelete) {
		try {
			for (LocalElement visualization : referringElement.getAllChildren(false)) {
				if (visualization instanceof LocalVisualization) {
					for (LocalElement seriesConfig : visualization.getAllChildren(false)) {
						if (seriesConfig instanceof LocalSeriesConfig) {
							LocalElement actionRule = seriesConfig.getElement(BEViewsElementNames.ACTION_RULE);
							LocalElement dataSource = actionRule.getElement(BEViewsElementNames.DATA_SOURCE);
							if (dataSource.equals(getLocalElement()) == true) {
								// we have a hit
								// remove all unwanted query params
								for (QueryParamInfo queryParam : queryParamsToDelete) {
									actionRule.removeElement(LocalActionRule.ELEMENT_KEY_QUERY_PARAM, queryParam.name, LocalElement.FOLDER_NOT_APPLICABLE);
								}
								// add all new query params
								for (QueryParamInfo queryParam : queryParamsToAdd) {
									//don't use the datasource from the chart, since it may be the old one, use getLocalElement()
									LocalElement param = getLocalElement().getElement(LocalDataSource.ELEMENT_KEY_QUERY_PARAM, queryParam.name, LocalElement.FOLDER_NOT_APPLICABLE);
									actionRule.addElement(LocalActionRule.ELEMENT_KEY_QUERY_PARAM, (LocalElement) param.clone());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not re-sync query parameters for " + referringElement, e));
		}
	}

	private class QueryParamInfo implements Comparable<QueryParamInfo>{

		public String name;

		public String type;

		QueryParamInfo(String name, String type) {
			super();
			this.name = name;
			this.type = type;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			QueryParamInfo other = (QueryParamInfo) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

		@Override
		public int compareTo(QueryParamInfo o) {
			if (this == o) {
				return 0;
			}
			int nameComparision = this.name.compareTo(o.name);
			if (nameComparision != 0) {
				return nameComparision;
			}
			int typeComparision = this.type.compareTo(o.type);
			if (typeComparision != 0) {
				return typeComparision;
			}
			return 0;
		}

	}
}