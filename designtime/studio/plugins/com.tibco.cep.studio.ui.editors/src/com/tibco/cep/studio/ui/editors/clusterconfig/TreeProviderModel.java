package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/*
@author ssailapp
@date Feb 2, 2010 1:23:34 PM
 */

public interface TreeProviderModel {
	public TreeItem addGroupItem(Tree tree, TreeItem parItem);
	public TreeItem addItem(Tree tree, TreeItem parItem);
	public void deleteItem(Tree tree, TreeItem item);
	public void moveUpItem(Tree tree, int index, TreeItem parentTreeItem);
	public void moveDownItem(Tree tree, int index, TreeItem parentTreeItem);
    public void setPropertyIconImage(TreeItem propItem);
}
