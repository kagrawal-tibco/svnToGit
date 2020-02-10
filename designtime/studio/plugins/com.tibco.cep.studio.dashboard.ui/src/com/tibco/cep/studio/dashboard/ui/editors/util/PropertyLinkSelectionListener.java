package com.tibco.cep.studio.dashboard.ui.editors.util;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;

/**
 * @
 *  
 */
public class PropertyLinkSelectionListener implements Listener {

    private SynProperty property;

    public PropertyLinkSelectionListener(SynProperty property) {
        this.property = property;
    }

    public void handleEvent(Event event) {
        String message;
        try {
            if (null != property.getValidationMessage()) {
                message = property.getValidationMessage().getMessage(true);
                MessageDialog.openInformation(null, "Property Validation Detail", message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
