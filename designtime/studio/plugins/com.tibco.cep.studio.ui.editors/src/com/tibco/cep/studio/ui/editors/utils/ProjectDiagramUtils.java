/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.studio.ui.diagrams.IProjectDiagramContributor;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

/**
 * @author hitesh
 *
 */
public class ProjectDiagramUtils {

	private static final String PROJECT_DIAGRAM_CONTRIBUTOR = "projectDiagramContributor";
	private static final String ATTR_CONTRIBUTOR = "contributor";
	private static IProjectDiagramContributor[] fProjectDiagramContributors;
	
	public static synchronized IProjectDiagramContributor[] getProjectDiagramContributors() {
		if (fProjectDiagramContributors == null) {
			List<IProjectDiagramContributor> diagramContributors = new ArrayList<IProjectDiagramContributor>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(EditorsUIPlugin.PLUGIN_ID, PROJECT_DIAGRAM_CONTRIBUTOR);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute(ATTR_CONTRIBUTOR);
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_CONTRIBUTOR);
						if (executableExtension instanceof IProjectDiagramContributor) {
							diagramContributors.add((IProjectDiagramContributor) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			
			fProjectDiagramContributors = (IProjectDiagramContributor[]) diagramContributors
					.toArray(new IProjectDiagramContributor[diagramContributors
							.size()]);
		}
		return fProjectDiagramContributors;
	}

}
