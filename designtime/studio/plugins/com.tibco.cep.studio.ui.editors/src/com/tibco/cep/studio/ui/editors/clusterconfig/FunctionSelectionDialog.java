package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;

/*
@author ssailapp
@date Jan 26, 2010 12:30:11 PM
 */

public class FunctionSelectionDialog extends StudioProjectElementSelectionDialog {

	private static final String title = "Rule Functions";
	private static final String imageId = ClusterConfigImages.IMG_CLUSTER_FUNCTION;
	
	public FunctionSelectionDialog(Shell parent, String parentGrp) {
		super(parent, parentGrp, title, imageId);
	}

	protected ArrayList<?> getProjectElements(IProject project) {
		return ClusterConfigProjectUtils.getProjectRuleFunctionNames(project, ClusterConfigProjectUtils.RF_ARGS_TYPE_STARTUP);
	}
}
