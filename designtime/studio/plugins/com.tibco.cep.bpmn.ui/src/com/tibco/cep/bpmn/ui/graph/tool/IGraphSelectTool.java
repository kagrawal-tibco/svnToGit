package com.tibco.cep.bpmn.ui.graph.tool;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.widgets.Control;

public interface IGraphSelectTool extends IAdaptable{

	Control getControl();

	public abstract void removeContextMenuListener(IGraphMenuListener l);

	public abstract void addMenuListener(IGraphMenuListener l);

	public abstract void removeMouseListener(IGraphMouseListener l);

	public abstract void addMouseListener(IGraphMouseListener l);

}
