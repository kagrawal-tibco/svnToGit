package com.tibco.cep.studio.ui.editors.utils;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.StudioUIAgent;
import com.tibco.cep.studio.core.UIAgentExtension;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class StudioUIAgentExtension implements UIAgentExtension {

	@Override
	public void openResource(final StudioUIAgent agent, final String location) {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(agent.getProjectName());
					IFile file = project.getFile(location);
					if (!file.exists()) {
						// look inside project libraries
						if(location.contains(".projlib")){
							String relFilePath = location.split(".projlib!")[1];
							relFilePath = relFilePath.substring(0, relFilePath.lastIndexOf('.'));
							file = IndexUtils.getLinkedResource(project.getName(), relFilePath);
						}
					}
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					if (window == null && PlatformUI.getWorkbench().getWorkbenchWindowCount() > 0) {
						window = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
					}
					if (window.getActivePage() != null) {
						EditorUtils.openEditor(window.getActivePage(), file);
					}
					
				} catch (Exception e) {
				}
			}
		});
	}

}
