package com.tibco.cep.studio.dashboard.ui.viewers;

import java.text.Collator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * @
 *  
 */
public class ElementNameSorter extends ViewerSorter {

    /**
     *  
     */
    public ElementNameSorter() {
        super();
    }

    /**
     * @param collator
     */
    public ElementNameSorter(Collator collator) {
        super(collator);
    }

    public int compare(Viewer viewer, Object e1, Object e2) {

        if (e1 instanceof LocalElement && e2 instanceof LocalElement) {
            int result = 0;

            try {
                result = ((LocalElement) e1).compareTo(((LocalElement) e2));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
        return super.compare(viewer, e1, e2);
    }
}
