package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.rms.history.model.HistoryRevisionItem;

/**
 * 
 * @author sasahoo
 *
 */
public class HistoryRevisionViewerLabelProvider implements ITableLabelProvider,IColorProvider {
	
	public HistoryRevisionViewerLabelProvider(){
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
//		if (columnIndex == 1) {
//			String action = ((HistoryRevisionItem) element).getCheckinActions();
//			return RMSHistoryRevisionUtils.getRevisionDetailsActionImageColumn(action);
//		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return ((HistoryRevisionItem) element).getRevisionId();
		case 1:
			return ((HistoryRevisionItem) element).getCheckinActions();
		case 2:
			return ((HistoryRevisionItem) element).getAuthor();
		case 3:
			return ((HistoryRevisionItem) element).getCheckinTime();
		case 4:
			return ((HistoryRevisionItem) element).getCheckinComments();
		default:
			return "Invalid column: " + columnIndex;
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
		return null;
	}

	public Color getForeground(Object element) {
		return null;
	}
}