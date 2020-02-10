package com.tibco.cep.studio.debug.ui.actions;

import org.eclipse.debug.ui.actions.RulerToggleBreakpointActionDelegate;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/*
@author ssailapp
@date Jul 22, 2009 11:31:57 AM
 */

public class RulerToggleRuleBreakpointActionDelegate extends RulerToggleBreakpointActionDelegate {

	protected IAction createAction(ITextEditor editor, IVerticalRulerInfo rulerInfo) {
		return super.createAction(editor, rulerInfo);
	}
	
	public void runWithEvent(IAction action, Event event) {
		super.runWithEvent(action, event);
	}
	
	@Override
	public void setActiveEditor(IAction callerAction, IEditorPart targetEditor) {
		if(targetEditor != null) {
			ITextEditor editor = (ITextEditor) targetEditor.getAdapter(ITextEditor.class);
			if(editor != null) {
				super.setActiveEditor(callerAction, editor);
				return;
			}
		}
		super.setActiveEditor(callerAction, targetEditor);
	}
	
}
