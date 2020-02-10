package com.tibco.cep.studio.ui.navigator.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.studio.ui.navigator.NavigatorPlugin;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;
import com.tibco.cep.studio.ui.views.GlobalVariablesView;

public class ProjectExplorerUtils {

	private static final String ATTR_FILTER = "staticViewerFilter";
	private static final String STATIC_VIEWER_FILTER = "staticViewerFilter";
	
	private static ViewerFilter[] fProjectExplorerViewerFilters;
	
	public static synchronized ViewerFilter[] getProjectViewerFilters() {
		if (fProjectExplorerViewerFilters == null) {
			List<ViewerFilter> viewerFilters = new ArrayList<ViewerFilter>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(NavigatorPlugin.PLUGIN_ID, STATIC_VIEWER_FILTER);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute(ATTR_FILTER);
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_FILTER);
						if (executableExtension instanceof ViewerFilter) {
							viewerFilters.add((ViewerFilter) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				
			}
			fProjectExplorerViewerFilters = new ViewerFilter[viewerFilters.size()];
			return viewerFilters.toArray(fProjectExplorerViewerFilters);
		}
		return fProjectExplorerViewerFilters;
	}

	/**
	 * @param page
	 * @param resource
	 */
	public static void refreshView(final IWorkbenchPage page, final IResource resource) {
		IViewPart view = page.findView(ProjectExplorer.ID);
		if(view == null) return;
		if (resource != null) {
			((ProjectExplorer)view).getCommonViewer().refresh(resource);
		} else {
			((ProjectExplorer)view).getCommonViewer().refresh();
		}
	}
	
	public static void refreshGVView(final IWorkbenchPage page) {
		IViewPart view = page.findView(GlobalVariablesView.ID);
		if(view == null) return;
		GlobalVariablesView gvView = (GlobalVariablesView)view;
		gvView.reloadView();
	}
	
	/**
	 * @param page
	 * @param resource
	 */
	public static void linkToStudioExplorer(final IWorkbenchPage page, final IResource resource) {
		IViewPart view = page.findView(ProjectExplorer.ID);
		if(view == null) {
			return;
		}
		if (resource != null) {
			((ProjectExplorer)view).selectReveal(new StructuredSelection(resource));
		}
	}
	
}
