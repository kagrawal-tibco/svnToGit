package com.tibco.cep.studio.rms.history;

import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.DETAILS_ACTION_PROPERTY;
import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.DETAILS_APPROVAL_STATUS_PROPERTY;
import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.DETAILS_PATH_PROPERTY;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;

/**
 * 
 * @author sasahoo
 * 
 */
public class RevisionDetailsViewerCellModifier implements ICellModifier {
	


	private TableViewer viewer;

	/**
	 * @param viewer
	 */
	public RevisionDetailsViewerCellModifier(TableViewer viewer) {
		this.viewer = viewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {
		if (DETAILS_ACTION_PROPERTY.equals(property)) {
			return ((RevisionDetailsItem) element).getOperation();
		} else if (DETAILS_PATH_PROPERTY.equals(property)) {
			return ((RevisionDetailsItem) element).getArtifactPath();
		} else if (DETAILS_APPROVAL_STATUS_PROPERTY.equals(property)) {
			return ((RevisionDetailsItem) element).getArtifactPath();
		} 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {
		TableItem tableItem = (TableItem) element;
		RevisionDetailsItem data = (RevisionDetailsItem) tableItem.getData();
		if (DETAILS_ACTION_PROPERTY.equals(property)) {
			data.setOperation(value.toString());
		} else if (DETAILS_PATH_PROPERTY.equals(property)) {
			data.setArtifactPath(value.toString());
		} else if (DETAILS_APPROVAL_STATUS_PROPERTY.equals(property)) {
			data.setApprovalStatus(value.toString());
		} 
		viewer.refresh(data);
	}
}