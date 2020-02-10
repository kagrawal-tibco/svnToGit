package com.tibco.cep.studio.rms.history;

import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.ACTIONS_PROPERTY;
import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.AUTHOR_PROPERTY;
import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.CHECKIN_COMMENTS_PROPERTY;
import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.DATE_PROPERTY;
import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.REVISION_PROPERTY;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.rms.history.model.HistoryRevisionItem;
/**
 * 
 * @author sasahoo
 * 
 */
public class HistoryRevisionViewerCellModifier implements ICellModifier {
	
	private TableViewer viewer;

	/**
	 * @param viewer
	 */
	public HistoryRevisionViewerCellModifier(TableViewer viewer) {
		this.viewer = viewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
//		if (ACTION_PROPERTY.equals(property)) {
//			return true;
//		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {
		if (REVISION_PROPERTY.equals(property)) {
			return ((HistoryRevisionItem) element).getRevisionId();
		} else if (ACTIONS_PROPERTY.equals(property)) {
			return ((HistoryRevisionItem) element).getCheckinActions();
		} else if (AUTHOR_PROPERTY.equals(property)) {
			return ((HistoryRevisionItem) element).getAuthor();
		} else if (DATE_PROPERTY.equals(property)) {
			return ((HistoryRevisionItem) element).getCheckinTime();
		} else if (CHECKIN_COMMENTS_PROPERTY.equals(property)) {
			return ((HistoryRevisionItem) element).getCheckinComments();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {
		TableItem tableItem = (TableItem) element;
		HistoryRevisionItem data = (HistoryRevisionItem) tableItem.getData();
		if (REVISION_PROPERTY.equals(property)) {
			data.setRevisionId(value.toString());
		} else if (ACTIONS_PROPERTY.equals(property)) {
			data.setCheckinActions(value.toString());
		} else if (AUTHOR_PROPERTY.equals(property)) {
			data.setAuthor(value.toString());
		} else if (DATE_PROPERTY.equals(property)) {
			data.setCheckinTime(value.toString());
		} else if (CHECKIN_COMMENTS_PROPERTY.equals(property)) {
			data.setCheckinComments(value.toString());
		}
		viewer.refresh(data);
	}
}