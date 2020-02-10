package com.tibco.cep.studio.dashboard.ui.editors.views.header;

import org.eclipse.swt.widgets.Item;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalHeaderLink;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AbstractLockingCellModifier;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;

public class LocalHeaderLinkCellModifier extends AbstractLockingCellModifier {

	public LocalHeaderLinkCellModifier(AttributeViewer tableViewer) {
		super(tableViewer);
	}

	@Override
	public boolean internalCanModify(Object element, String property) {
		return true;
	}

	
	@Override
	public void internalModify(Object element, String property, Object value) {
		// Find the index of the column
		int columnIndex = attributeViewer.getColumnNamesList().indexOf(property);

		if (element instanceof Item) {
			element = ((Item) element).getData();
		}

		if (element instanceof LocalHeaderLink) {
			try {
				LocalHeaderLink localHeaderLink = (LocalHeaderLink) element;
				switch (columnIndex) {
					case 0: 
						localHeaderLink.setURLName(value.toString());
						break;
					case 1:
						localHeaderLink.setURLLink(value.toString());
						break;
					default:
						break;
				}
				/*
				 * Request the viewer to validate all fields
				 */
				attributeViewer.validate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*
		 * Refresh to update with new values
		 */
		attributeViewer.refresh();
	}
	
	@Override
	public Object getValue(Object element, String property) {
		// Find the index of the column
		int columnIndex = attributeViewer.getColumnNamesList().indexOf(property);
		Object result = "";
		if (element instanceof LocalHeaderLink) {
			try {
				LocalHeaderLink localHeaderLink = (LocalHeaderLink) element;
				switch (columnIndex) {
					case 0:
						return localHeaderLink.getURLName();
					case 1:
						return localHeaderLink.getURLLink();
					default:
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public AttributeViewer getAttributeViewer() {
		return attributeViewer;
	}
	
	public void setAttributeViewer(AttributeViewer attributeViewer) {
		this.attributeViewer = attributeViewer;
	}
}
