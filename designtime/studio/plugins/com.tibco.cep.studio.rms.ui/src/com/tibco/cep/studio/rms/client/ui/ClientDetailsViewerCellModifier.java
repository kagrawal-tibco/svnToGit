package com.tibco.cep.studio.rms.client.ui;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

/**
 * 
 * @author sasahoo
 * 
 */
public class ClientDetailsViewerCellModifier implements ICellModifier {
	
	protected static final String REVEIWER_PROPERTY = "reveiwer";
	protected static final String COMMENTS_PROPERTY = "comments";
	protected static final  String OLD_STATUS_PROPERTY = "oldStatus";
	protected static final  String NEW_STATUS_PROPERTY = "newStatus";
	protected static final  String TIME_PROPERTY= "time";
	protected static final String ROLE_PROPERTY = "role";
	
	private TableViewer viewer;


	/**
	 * @param viewer
	 */
	public ClientDetailsViewerCellModifier(TableViewer viewer) {
		this.viewer = viewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		if (COMMENTS_PROPERTY.equals(property)) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {
		if (REVEIWER_PROPERTY.equals(property)) {
			return ((AuditTrailItem) element).reveiwer;
		} else if (ROLE_PROPERTY.equals(property)) {
			return ((AuditTrailItem) element).role;
		} else if (COMMENTS_PROPERTY.equals(property)) {
			return ((AuditTrailItem) element).comment;
		} else if (OLD_STATUS_PROPERTY.equals(property)) {
			return ((AuditTrailItem) element).oldStatus;
		} else if (NEW_STATUS_PROPERTY.equals(property)) {
			return ((AuditTrailItem) element).newStatus;
		} else if (TIME_PROPERTY.equals(property)) {
			return ((AuditTrailItem) element).time;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {
		TableItem tableItem = (TableItem) element;
		AuditTrailItem data = (AuditTrailItem) tableItem.getData();
		if (REVEIWER_PROPERTY.equals(property)) {
			data.reveiwer = value.toString();
		} else if (ROLE_PROPERTY.equals(property)) {
			data.role = value.toString();
		} else if (COMMENTS_PROPERTY.equals(property)) {
			data.comment = value.toString();
		} else if (OLD_STATUS_PROPERTY.equals(property)) {
			data.oldStatus = value.toString();
		} else if (NEW_STATUS_PROPERTY.equals(property)) {
			data.newStatus = value.toString();
		} else if (TIME_PROPERTY.equals(property)) {
			data.time = value.toString();
		}
		viewer.refresh(data);
	}
}
