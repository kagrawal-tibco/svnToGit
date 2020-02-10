/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.studio.ui.diagrams.IFormEditorContributor;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

/**
 *
 */
public class FormEditorUtils {

	private static final String FORM_EDITOR_CONTRIBUTOR = "formEditorContributor";
	private static final String ATTR_CONTRIBUTOR = "contributor";
	private static IFormEditorContributor[] fFormEditorContributors;
	
	public static synchronized IFormEditorContributor[] getFormEditorContributors() {
		if (fFormEditorContributors == null) {
			List<IFormEditorContributor> editorContributors = new ArrayList<IFormEditorContributor>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(EditorsUIPlugin.PLUGIN_ID, FORM_EDITOR_CONTRIBUTOR);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute(ATTR_CONTRIBUTOR);
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_CONTRIBUTOR);
						if (executableExtension instanceof IFormEditorContributor) {
							editorContributors.add((IFormEditorContributor) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			
			fFormEditorContributors = (IFormEditorContributor[]) editorContributors
					.toArray(new IFormEditorContributor[editorContributors
							.size()]);
		}
		return fFormEditorContributors;
	}

}
