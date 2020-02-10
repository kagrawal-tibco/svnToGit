package com.tibco.cep.studio.dashboard.ui.viewers.attribute;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * @
 *  
 */
public class LocalSorterByPosition extends ViewerSorter {

    public int compare(Viewer viewer, Object e1, Object e2) {

        try {
            LocalElement f1 = (LocalElement) e1;

            LocalElement f2 = (LocalElement) e2;

            return (f1.getSortingOrder() - f2.getSortingOrder());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}