package com.tibco.cep.studio.ui.editors.domain;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

/**
 * 
 * @author sasahoo
 * 
 */
public class DomainViewerCellModifier implements ICellModifier {
	private static final String NAME_PROPERTY = "name";
	private TableViewer viewer;


	public DomainViewerCellModifier(TableViewer viewer) {
		this.viewer = viewer;
	}

	public boolean canModify(Object element, String property) {
		return true;
	}

	public Object getValue(Object element, String property) {
		if (NAME_PROPERTY.equals(property)) {
			return ((DomainViewerItem) element).entryName;
		} else
			return ((DomainViewerItem) element).entryValue;
	}
	
	public void modify(Object element, String property, Object value) {
			TableItem tableItem = (TableItem) element;
			DomainViewerItem data = (DomainViewerItem) tableItem.getData();
			if (NAME_PROPERTY.equals(property)) {
				data.entryName = value.toString();
			} else {
				data.entryValue = value.toString();
			}
			viewer.refresh(data);
	}
}
