package com.tibco.cep.studio.debug.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.AbstractRulerActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;


/*
@author ssailapp
@date Jul 15, 2009 2:29:44 PM
 */

public class RuleBreakpointPropertiesRulerActionDelegate extends AbstractRulerActionDelegate {

	public RuleBreakpointPropertiesRulerActionDelegate() {
		// TODO Auto-generated constructor stub
	}

	protected IAction createAction(ITextEditor editor, IVerticalRulerInfo rulerInfo) {
		ITextEditor textEditor = (ITextEditor) editor.getAdapter(ITextEditor.class);
		if(textEditor != null) {
			return new RuleBreakpointPropertiesRulerAction(textEditor, rulerInfo);
		}
		return new RuleBreakpointPropertiesRulerAction(editor, rulerInfo);
	}
	
	@Override
	public void setActiveEditor(IAction callerAction, IEditorPart targetEditor) {
		if(targetEditor != null) {
			ITextEditor textEditor = (ITextEditor) targetEditor.getAdapter(ITextEditor.class);
			if(textEditor != null) {
				super.setActiveEditor(callerAction, textEditor);
				return;
			}
		}
		super.setActiveEditor(callerAction, targetEditor);
		return;
	}
	
}
