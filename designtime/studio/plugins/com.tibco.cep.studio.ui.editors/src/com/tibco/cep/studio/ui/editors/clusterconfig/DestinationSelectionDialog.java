package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;

/*
@author ssailapp
@date Jan 27, 2010 6:10:37 PM
 */

public class DestinationSelectionDialog extends StudioProjectElementSelectionDialog {

	private static final String title = "Input Destinations";
	private static final String imageId = ClusterConfigImages.IMG_CLUSTER_DESTINATION;
	
	public DestinationSelectionDialog(Shell parent, String parentGrp) {
		super(parent, parentGrp, title, imageId);
	}

	@Override
	protected ArrayList<?> getProjectElements(IProject project) {
		return ClusterConfigProjectUtils.getProjectDestinationNames(project);
	}
}
