package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.Component;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;

/**
 * A generic wrapper around the a component that allows some 'loading' computation to be done before displaying.<br>
 * While loading (on a background thread), it displays a messages.
 */
public interface LazyComponentLoaderRunnable
{
    Component run(CancelChecker cancel);
}
