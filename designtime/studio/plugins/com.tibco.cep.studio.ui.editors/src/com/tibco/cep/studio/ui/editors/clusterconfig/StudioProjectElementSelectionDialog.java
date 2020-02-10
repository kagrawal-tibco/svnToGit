package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Jan 26, 2010 12:27:08 PM
 */

public abstract class StudioProjectElementSelectionDialog extends Dialog  {

	protected Shell shell, dialog;
	protected ArrayList<String> selItems;
	protected String parentGrp;	
	protected Tree tree;
	protected TreeItem projNode, refNode;
	protected Button bOK, bCancel;
	private String title, imageId;
	private boolean showReferenceNode = true;
	
	public StudioProjectElementSelectionDialog(Shell parent, String parentGrp, String title, String imageId, boolean showReferenceNode) {
		this(parent, parentGrp, title, imageId);
		this.showReferenceNode = showReferenceNode;
	}
	
	public StudioProjectElementSelectionDialog(Shell parent, String parentGrp, String title, String imageId) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		selItems = new ArrayList<String>();
		this.parentGrp = parentGrp;
		this.title = title;
		this.imageId = imageId;
	}
	
	public void initDialog(String title) {
		shell = getParent();
		dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
		dialog.setText(title);
		dialog.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
		dialog.setLayout(new GridLayout(1, false));
		
		tree = new Tree (dialog, SWT.BORDER | SWT.CHECK | SWT.MULTI);
		tree.setSize(300, 400);
		
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite butComp = new Composite(dialog, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		butComp.setLayoutData(gd);
		butComp.setLayout(new GridLayout(3, false));
		
		Label lFiller = PanelUiUtil.createLabelFiller(butComp);
		
		bOK = new Button(butComp, SWT.PUSH);
		bOK.setText("OK");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bOK.setLayoutData(gd);
		bOK.setEnabled(false);
		
		bCancel = new Button(butComp, SWT.PUSH);
		bCancel.setText("Cancel");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bCancel.setLayoutData(gd);
		butComp.pack();
	}

	public void open(IProject project, String[] refGroups, ArrayList<?> filter) {
		
		if(dialog==null){
			initDialog("Select " + title);
			projNode = new TreeItem(tree, SWT.NONE);
		}
		// Project elements
		projNode.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROJECT));
		projNode.setText(title + " [Project: " + project.getName() + "]");

		//TODO - Show folders in tree view, to enable selection of entire folders
		ArrayList<?> projElements = getProjectElements(project);
		for (Object projElement: projElements) {
			if (!filter.contains(projElement)) {
				TreeItem item = new TreeItem(projNode, SWT.NONE);
				item.setImage(ClusterConfigImages.getImage(imageId));
				item.setText((String)projElement);
				item.setData(projElement);
			}
		}
		projNode.setExpanded(true);
		if (projNode.getItemCount()==0)
			projNode.dispose();
		
		if (showReferenceNode) {
			// Cdd Reference Elements
			refNode = new TreeItem(tree, SWT.NONE);
			refNode.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_GROUP_REFERENCE));
			refNode.setText("Reference Collections");

			for (String refGroup: refGroups) {
				if (!filter.contains(refGroup) && !refGroup.equals(parentGrp)) {
					TreeItem item = new TreeItem(refNode, SWT.NONE);
					item.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_GROUP));
					item.setText(refGroup);
				}
			}
			refNode.setExpanded(true);
			if (refNode.getItemCount()==0)
				refNode.dispose();
		}
		if (projNode.isDisposed() && refNode != null && refNode.isDisposed()) {
			//tree.setEnabled(false);
			tree.setToolTipText("There are no " + title.toLowerCase() + " or groups to display");
		}
		
		openDialog();
	}
	
	protected abstract ArrayList<?> getProjectElements(IProject project);
	
	public void openDialog() {
		
		tree.addListener(SWT.Selection, getTreeListener());
		
		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (projNode != null && !projNode.isDisposed()) {
					for (TreeItem item: projNode.getItems()) {
						if (item.getChecked()) 
							selItems.add(item.getText());
					}
				}
				if (refNode != null && !refNode.isDisposed()) {
					for (TreeItem item: refNode.getItems()) {
						if (item.getChecked()) 
							selItems.add(item.getText());
					}
				}
				dialog.dispose();
			}
		});

		bCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				dialog.dispose();
			}
		});
		
		dialog.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE) {
					dialog.dispose();
					event.detail = SWT.TRAVERSE_NONE;
					event.doit = false;
				}
			}
		});
		
		dialog.pack();

		dialog.setSize(300, 400);
		Rectangle shellBounds = shell.getBounds();
        Point dialogSize = dialog.getSize();
        dialog.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2, shellBounds.y + (shellBounds.height - dialogSize.y) / 4);

        dialog.open();
        
		Display display = shell.getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public ArrayList<String> getSelectedItems() {
		return selItems;
	}
	
	private void enableActionButtons() {
		bOK.setEnabled(false);
		for (TreeItem item: tree.getItems()) {
			if (item.getChecked() || item.getGrayed()) {
				bOK.setEnabled(true);
				break;
			}
		}
	}
	
	private Listener getTreeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
	            TreeItem item = (TreeItem) event.item;
	            boolean checked = item.getChecked();
	            checkMarkChildren(item, checked);
	            checkPathForGrayItems(item.getParentItem(), checked, false);
	            enableActionButtons();
			}
		};
		return listener;
	}
	
	private void checkMarkChildren(TreeItem item, boolean checked) {
	    item.setGrayed(false);
	    item.setChecked(checked);
	    TreeItem[] children = item.getItems();
	    for (int i=0; i<children.length; i++) {
	        checkMarkChildren(children[i], checked);
	    }
	}
	
	private void checkPathForGrayItems(TreeItem item, boolean checked, boolean grayed) {
	    if (item == null) return;
	    if (grayed) {
	        checked = true;
	    } else {
	        int index = 0;
	        TreeItem[] children = item.getItems();
	        while (index < children.length) {
	            TreeItem child = children[index];
	            if (child.getGrayed() || checked != child.getChecked()) {
	                checked = grayed = true;
	                break;
	            }
	            index++;
	        }
	    }
	    item.setChecked(checked);
	    item.setGrayed(grayed);
	    checkPathForGrayItems(item.getParentItem(), checked, grayed);
	}
}
