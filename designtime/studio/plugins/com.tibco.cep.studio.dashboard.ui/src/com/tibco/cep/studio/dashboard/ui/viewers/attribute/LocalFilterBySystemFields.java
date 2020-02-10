package com.tibco.cep.studio.dashboard.ui.viewers.attribute;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;

/**
 * @
 *  
 */
public class LocalFilterBySystemFields extends ViewerFilter {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        try {
            if (parentElement instanceof LocalEntity) {
                if (true == ((LocalEntity) parentElement).isSystem()) {
                    return false;
                }
            }

            if (element instanceof LocalEntity) {
                if (true == ((LocalEntity) element).isSystem()) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}