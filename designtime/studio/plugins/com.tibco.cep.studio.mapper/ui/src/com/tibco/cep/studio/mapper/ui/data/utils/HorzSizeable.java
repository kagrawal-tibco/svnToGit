package com.tibco.cep.studio.mapper.ui.data.utils;




/**
 * A simple API for components that need to know their width in order to draw correctly.<br>
 * The {@link HorzSizedScrollPane} will issue callbacks on this.
 */
public interface HorzSizeable
{
    public void resized(int availableWidth);
}
