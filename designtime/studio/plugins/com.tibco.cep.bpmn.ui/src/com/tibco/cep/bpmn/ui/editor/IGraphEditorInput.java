package com.tibco.cep.bpmn.ui.editor;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IFileEditorInput;

public interface IGraphEditorInput extends IAdaptable,IFileEditorInput{
	
	IGraphEditor getGraphEditor();

}
