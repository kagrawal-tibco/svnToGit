package com.tibco.cep.studio.ui.editors.domain.data;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * 
 * @author smarathe
 *
 */

public class DBTreeLabelProvider extends LabelProvider {

	private TreeViewer treeViewer;
	
	public DBTreeLabelProvider(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}
	
	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	public String getText(Object element) {
		String label = "";
		try {
			if (element instanceof DBTreeRoot) {
				label = ((DBTreeRoot)element).getRootName();
			} else if (element instanceof DBTreeTables) {
				label = ((DBTreeTables)element).getTableName();
			} else if (element instanceof DBTreeColumns) {
				label = ((DBTreeColumns)element).getColumnName();
			} else if (element instanceof DBTreeValues) {
				label = ((DBTreeValues)element).getValue().toString();
			}
		}catch(Exception e) {
			
		}
		return label;
	}
	
}
