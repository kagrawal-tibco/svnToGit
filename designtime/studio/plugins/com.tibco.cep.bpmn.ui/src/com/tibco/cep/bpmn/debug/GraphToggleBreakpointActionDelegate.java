package com.tibco.cep.bpmn.debug;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.tibco.cep.bpmn.ui.editor.IGraphEditor;
import com.tibco.cep.bpmn.ui.editor.IGraphInfo;

public class GraphToggleBreakpointActionDelegate extends AbstractGraphActionDelegate implements IEditorActionDelegate {
	
	private IEditorPart fEditor = null;
	private GraphToggleBreakpointAction fDelegate = null;
	
	
	@Override
	protected IAction createAction(IGraphEditor editor,IGraphInfo info) {
		fDelegate = new GraphToggleBreakpointAction(editor,info);
		return fDelegate;
	}
	
	@Override
	public void init(IAction action) {
		super.init(action);
	}
	
	



	@Override
	public void setActiveEditor(IAction callerAction, IEditorPart targetEditor) {
		if (fEditor != null) {
			if (fDelegate != null) {
				fDelegate.dispose();
				fDelegate = null;
			}
		}
		fEditor = targetEditor;
		super.setActiveEditor(callerAction, targetEditor);

	}
	
	public void dispose() {
		if (fDelegate != null) {
			fDelegate.dispose();
		}
		fDelegate = null;
		fEditor = null;
	}

}
