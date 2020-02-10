package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;

/*
@author ssailapp
@date Jan 27, 2010 2:18:58 AM
 */

public class RuleSelectionDialog extends StudioProjectElementSelectionDialog {

	private static final String title = "Rules";
	private static final String imageId = ClusterConfigImages.IMG_CLUSTER_RULES;
	
	public RuleSelectionDialog(Shell parent, String parentGrp) {
		super(parent, parentGrp, title, imageId);
	}

	protected ArrayList<?> getProjectElements(IProject project) {
		return ClusterConfigProjectUtils.getProjectRuleNames(project);
	}
	
	@Override
	public void open(IProject project, String[] refGroups, ArrayList<?> filter) {
		
		if(dialog==null){
			initDialog("Select " + title);
			projNode = new TreeItem(tree, SWT.NONE);
		}

		ArrayList<?> projElements = ClusterConfigProjectUtils.getProjectRuleTemplateNames(project);
		for (Object projElement: projElements) {
			if (!filter.contains(projElement)) {
				TreeItem item = new TreeItem(projNode, SWT.NONE);
				item.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_RULE_TEMPLATE));
				item.setText((String)projElement);
				item.setData(projElement);
			}
		}
		
		super.open(project, refGroups, filter);
		
	}
}

