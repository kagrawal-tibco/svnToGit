package com.tibco.cep.studio.dashboard.ui.viewers.attribute;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * @
 *  
 */
public class LocalFilterByStatus_Removed extends ViewerFilter {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (parentElement instanceof LocalElement) {
            if (true == ((LocalElement) parentElement).getInternalStatus().equals(InternalStatusEnum.StatusRemove)) {
                return false;
            }
        }

        if (element instanceof LocalElement) {
            if (true == ((LocalElement) element).getInternalStatus().equals(InternalStatusEnum.StatusRemove)) {
                return false;
            }
        }
        return true;
    }

}