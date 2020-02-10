package com.tibco.cep.studio.ui.editors.utils;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/*
@author ssailapp
@date Dec 21, 2009 2:01:24 AM
 */

public class BlockUtil {
	
	public static void refreshViewer(TreeViewer treeViewer) {
		if (treeViewer == null || treeViewer.getTree().isDisposed())
			return;
		Object expObjs[] = treeViewer.getExpandedElements();
		//ISelection selection = treeViewer.getSelection();
		treeViewer.refresh();
		treeViewer.setExpandedElements(expObjs);
		//treeViewer.setSelection(selection); //This is not needed
	}
	
	public static void refreshViewerForError(TreeViewer treeViewer,int error, Text tRuleGrp) {
		
		if (treeViewer == null || treeViewer.getTree().isDisposed())
			return;
		Object expObjs[] = treeViewer.getExpandedElements();
		//ISelection selection = treeViewer.getSelection();
		treeViewer.refresh();
		treeViewer.setExpandedElements(expObjs);
		if(error==0){
			tRuleGrp.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		}
		else{
			tRuleGrp.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		}
		
		
		
		
		//treeViewer.setSelection(selection); //This is not needed
	}

	public static void refreshViewer(TreeViewer treeViewer, int level) {
		refreshViewer(treeViewer);
		expandViewer(treeViewer, level);
	}
	
	public static void refreshViewer(TreeViewer treeViewer, Object projectGroup, 
			                                                Object parentObject, 
			                                                Object selectedObject) {
		if (projectGroup != null) {
			treeViewer.refresh(projectGroup);
			treeViewer.expandToLevel(projectGroup, 1);
		}
		if (parentObject != null) {
			treeViewer.refresh(parentObject);
			treeViewer.expandToLevel(parentObject, 1);
		}
		if (selectedObject != null) {
			selectObject(treeViewer, selectedObject);
		}
	}
	
	public static void refreshViewer(TreeViewer treeViewer, Object parentObject, Object selectedObject) {
		refreshViewer(treeViewer);
		if (parentObject != null) {
			treeViewer.expandToLevel(parentObject, 1);
		}
		selectObject(treeViewer, selectedObject);
	}

	public static void refreshViewer(TreeViewer treeViewer, Object selectedObject) {
		refreshViewer(treeViewer);
		if (selectedObject != null) {
			treeViewer.expandToLevel(selectedObject, 0);
			treeViewer.setSelection(new StructuredSelection(selectedObject), true);
		}
	}
	
	public static void expandViewer(TreeViewer treeViewer, int level) {
		treeViewer.expandToLevel(level);
	}
	
	public static void selectObject(TreeViewer treeViewer, Object selectedObject) {
		if (selectedObject != null) {
			treeViewer.setSelection(new StructuredSelection(selectedObject), true);
		}
	}
}
