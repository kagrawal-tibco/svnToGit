package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.core.builder.AbstractBPMNProcessor;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.util.DependencyUtils;

/*
@author ssailapp
@date Apr 28, 2011
 */

public class ProcessSelectionDialog extends StudioProjectElementSelectionDialog {

	private static final String title = "Processes";
	private static final String imageId = ClusterConfigImages.IMG_CLUSTER_PROCESS;
	
	boolean isAgentGroup = false;
	boolean isFunctionGroup = false;

	public ProcessSelectionDialog(Shell parent, 
			                      String parentGrp, 
			                      boolean isAgentGroup,
			                      boolean isFunctionGroup, 
			                      boolean showReferenceNode) {
		super(parent, parentGrp, title, imageId,showReferenceNode);
		this.isAgentGroup = isAgentGroup;
		this.isFunctionGroup = isFunctionGroup;
	}
	
	public ProcessSelectionDialog(Shell parent, String parentGrp) {
		super(parent, parentGrp, title, imageId);
	}

	protected ArrayList<?> getProjectElements(IProject project) {
		ArrayList<String> uris = new ArrayList<String>();
		ArrayList<String> fnuris = new ArrayList<String>();
		for (AbstractBPMNProcessor processor : DependencyUtils.getBPMNProcessors()) {
			uris.addAll(processor.getProcesses(project));
			fnuris.addAll(processor.getStartupShudownFunctions());

		}
		if (isAgentGroup) {
			return uris;
		} else if (isFunctionGroup) {
			return fnuris;
		}
		return uris;
	}
}