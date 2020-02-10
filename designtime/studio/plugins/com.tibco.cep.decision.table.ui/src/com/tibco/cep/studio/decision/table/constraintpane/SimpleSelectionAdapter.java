

package com.tibco.cep.studio.decision.table.constraintpane;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * This class is an adapter for the SelectionListener. Both behaviours (DefaultSelected and Selected) are doing the same thing
 */
public abstract class SimpleSelectionAdapter implements SelectionListener {

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public void widgetDefaultSelected(final SelectionEvent e) {
        this.handle(e);

    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public void widgetSelected(final SelectionEvent e) {
        this.handle(e);

    }

    /**
     * Sent when selection occurs in the control.
     * 
     * @param e - an event containing information about the selection
     */
    public abstract void handle(SelectionEvent e);

}
