package com.tibco.cep.studio.mapper.ui.data.basic;

import javax.swing.JComponent;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;

/**
 * The details part of a master-details view.
 */
public interface DetailsViewFactory
{
    /**
     * Gets the details view for a node.
     * @param node The node for which to display details, or null for none.
     * @return The component to display as the details view (may return the same component over-and-over, if so inclined)
     */
    JComponent getComponentForNode(Object node);
    void writePreferences(JComponent component, String keyprefix, UIAgent prefWriter);
    void readPreferences(JComponent component, String keyprefix, UIAgent prefWriter);
}
