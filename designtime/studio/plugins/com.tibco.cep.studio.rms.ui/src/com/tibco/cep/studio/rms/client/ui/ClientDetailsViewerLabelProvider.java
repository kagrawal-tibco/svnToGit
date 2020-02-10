package com.tibco.cep.studio.rms.client.ui;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class ClientDetailsViewerLabelProvider implements ITableLabelProvider,IColorProvider {
	
	private boolean onlyComment;
	
	public ClientDetailsViewerLabelProvider(boolean onlyComment){
	  this.onlyComment = onlyComment;	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		
		if(onlyComment){
			switch (columnIndex) {
			case 0:
				return ((AuditTrailItem) element).comment;
			default:
				return "Invalid column: " + columnIndex;
			}
		}else{
			switch (columnIndex) {
			case 0:
				return ((AuditTrailItem) element).reveiwer;
			case 1:
				return ((AuditTrailItem) element).role;
			case 2:
				return ((AuditTrailItem) element).comment;
			case 3:
				return ((AuditTrailItem) element).oldStatus;
			case 4:
				return ((AuditTrailItem) element).newStatus;
			case 5:
				return ((AuditTrailItem) element).time;
			default:
				return "Invalid column: " + columnIndex;
			}
		}
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener lpl) {
	}

	public Color getBackground(Object element) {
		return ColorConstants.tooltipBackground;
	}

	public Color getForeground(Object element) {
		return null;
	}

}
