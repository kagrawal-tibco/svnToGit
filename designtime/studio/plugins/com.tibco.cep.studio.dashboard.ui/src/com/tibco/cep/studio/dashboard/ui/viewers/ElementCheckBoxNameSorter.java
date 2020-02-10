package com.tibco.cep.studio.dashboard.ui.viewers;

import java.text.Collator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * @
 *  
 */
public class ElementCheckBoxNameSorter extends ViewerSorter {

    /**
     *  
     */
    public ElementCheckBoxNameSorter() {
        super();
    }

    /**
     * @param collator
     */
    public ElementCheckBoxNameSorter(Collator collator) {
        super(collator);
    }

    public int compare(Viewer viewer, Object e1, Object e2) {

        if (e1 instanceof ElementCheckBoxWrapper && e2 instanceof ElementCheckBoxWrapper) {
            return ((ElementCheckBoxWrapper) e1).getLocalElement().compareTo(((ElementCheckBoxWrapper) e2).getLocalElement());
        }
        return super.compare(viewer, e1, e2);
    }
}
