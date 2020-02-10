/**
 * 
 */
package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.viewers.IElementComparer;

import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;

/**
 * @author aathalye
 *
 */
public class HistoryViewItemComparer implements IElementComparer {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IElementComparer#equals(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean equals(Object a, Object b) {
		//Compare artifact paths
		if (!(a instanceof RevisionDetailsItem)) {
			return false;
		}
		if (!(b instanceof RevisionDetailsItem)) {
			return false;
		}
		RevisionDetailsItem item1 = (RevisionDetailsItem)a;
		RevisionDetailsItem item2 = (RevisionDetailsItem)b;
		if (item1.getArtifactPath().equals(item2.getArtifactPath())) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IElementComparer#hashCode(java.lang.Object)
	 */
	@Override
	public int hashCode(Object element) {
		return element.hashCode();
	}

}
