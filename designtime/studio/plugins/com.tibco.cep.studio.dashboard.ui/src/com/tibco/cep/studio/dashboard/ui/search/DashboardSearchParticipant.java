package com.tibco.cep.studio.dashboard.ui.search;

import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.DashboardSearcher;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.SearchPathResult;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;

public class DashboardSearchParticipant implements ISearchParticipant {

	@Override
	public SearchResult search(Object resolvedElement, String projectName, String nameToFind, IProgressMonitor monitor) {

		Entity targetEntity = null;
		SearchResult searchResult = new DashboardSearchResult();
		if (resolvedElement instanceof EntityElement) {
			targetEntity = ((EntityElement) resolvedElement).getEntity();
		} else if (resolvedElement instanceof Entity) {
			targetEntity = (Entity) resolvedElement;
		} else {
			// could be a FolderImpl
//			throw new IllegalArgumentException(resolvedElement + " is not of type " + Entity.class.getName());
			return searchResult;
		}
		try {
			DashboardSearcher searcher = DashboardSearcher.getInstance();
			HashSet<LocalElement> referencingElements = new HashSet<LocalElement>();
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			LocalECoreFactory localECoreFactory = LocalECoreFactory.getInstance(project);
			LocalElement targetElement = convertToLocalElement(localECoreFactory, targetEntity);
			if (targetElement instanceof LocalSkin) {
				LocalSkin localSkin = (LocalSkin) targetElement;
				if (localSkin.isSystem() == true) {
					//we are dealing with system skin elements, we need to explode them and search for each child
					URI resource = targetElement.getEObject().eResource().getURI();
					List<LocalElement> family = localSkin.getFamily();
					for (LocalElement member : family) {
						List<SearchPathResult> results = searcher.search(localECoreFactory,member);
						for (SearchPathResult searchPathResult : results) {
							LocalElement actualResult = searchPathResult.getActualResult();
							if (resource.equals(actualResult.getEObject().eResource().getURI()) == false) {
								referencingElements.add(actualResult);
							}
						}
					}
				}
			} else if (targetElement instanceof LocalMetric){
				//do a search for all metric fields
				List<LocalElement> fields = targetElement.getChildren(LocalMetric.ELEMENT_KEY_FIELD);
				for (LocalElement targetElementField : fields) {
					List<SearchPathResult> results = searcher.search(localECoreFactory,targetElementField);
					for (SearchPathResult searchPathResult : results) {
						referencingElements.add(searchPathResult.getActualResult());
					}
				}
			}
			//do a search for the target element
			List<SearchPathResult> results = searcher.search(localECoreFactory,targetElement);
			for (SearchPathResult searchPathResult : results) {
				referencingElements.add(searchPathResult.getActualResult());
			}
			for (LocalElement localElement : referencingElements) {
				searchResult.addExactMatch(localElement.getEObject());
			}
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING,DashboardUIPlugin.PLUGIN_ID,"Exception occured when searching for references of "+targetEntity.getFullPath(),e));
		}
		return searchResult;
	}

	private LocalElement convertToLocalElement(LocalECoreFactory localECoreFactory, Entity entity) throws Exception {
//		String eClassName = entity.eClass().getName();
//		if (eClassName.equals(BEViewsElementNames.CHART_COMPONENT) || eClassName.equals(BEViewsElementNames.TEXT_COMPONENT)) {
//			eClassName = BEViewsElementNames.TEXT_CHART_COMPONENT;
//		}
		if (entity instanceof BEViewsElement || entity instanceof Metric) {
			return LocalECoreFactory.toLocalElement(localECoreFactory, entity);
		}
		if (entity instanceof PropertyDefinition) {
			Entity parent = (Entity) entity.eContainer();
			if (parent instanceof Metric) {
				LocalElement parentElement = LocalECoreFactory.toLocalElement(localECoreFactory, parent);
				HashSet<LocalElement> family = new HashSet<LocalElement>();
				addChildrenTo(parentElement, family);
				for (LocalElement child : family) {
					if (child.getID().equals(entity.getGUID()) == true) {
						return child;
					}
				}
			}
			return null;
		}
		return new LocalExternalReference(entity);
	}

	private void addChildrenTo(LocalElement localElement, HashSet<LocalElement> family) throws Exception {
		List<LocalParticle> particles = localElement.getParticles(true);
		for (LocalParticle localParticle : particles) {
			if (localParticle.isInitialized() == false){
				localElement.loadChildren(localParticle.getName());
				localParticle.setInitialized(true);
			}
			List<LocalElement> elements = localParticle.getElements();
			family.addAll(elements);
			for (LocalElement element : elements) {
				addChildrenTo(element, family);
			}
		}
	}


}
