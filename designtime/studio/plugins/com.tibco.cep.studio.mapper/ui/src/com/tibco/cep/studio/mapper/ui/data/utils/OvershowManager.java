package com.tibco.cep.studio.mapper.ui.data.utils;

import javax.swing.JComponent;

/**
 * Allows setting of 'overshow' (i.e. popup details when stuff won't fit) components.
 */
public interface OvershowManager
{
    public void setOvershowComponent(JComponent component);
    public JComponent getRoot();
}
