package com.tibco.cep.studio.ui.widgets;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IElementFilter {

    public void filterElements(Collection<Object> elements, IProgressMonitor monitor)
            throws InterruptedException;

    public void filterElements(Object[] elements, IProgressMonitor monitor)
            throws InterruptedException;

}
