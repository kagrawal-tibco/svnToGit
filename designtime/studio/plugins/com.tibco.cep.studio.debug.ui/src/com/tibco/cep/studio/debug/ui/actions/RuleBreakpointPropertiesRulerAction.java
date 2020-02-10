package com.tibco.cep.studio.debug.ui.actions;

import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.actions.RulerBreakpointAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;

import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;

/*
@author ssailapp
@date Jul 15, 2009 2:31:06 PM
 */

public class RuleBreakpointPropertiesRulerAction extends RulerBreakpointAction implements IUpdate {
	
	private IBreakpoint fBreakpoint;

	public RuleBreakpointPropertiesRulerAction(ITextEditor editor, IVerticalRulerInfo info) {
		super(editor, info);
		setText("RuleBreakpoint P&roperties..."); 
	}

	public void run() {
		if (getBreakpoint() != null) {
			PropertyDialogAction action = new PropertyDialogAction(getEditor()
					.getEditorSite(), new ISelectionProvider() {
				public void addSelectionChangedListener(
						ISelectionChangedListener listener) {
				}

				public ISelection getSelection() {
					return new StructuredSelection(getBreakpoint());
				}

				public void removeSelectionChangedListener(
						ISelectionChangedListener listener) {
				}

				public void setSelection(ISelection selection) {
				}
			});
			action.run();
		}
	}
	
	public void update() {
		fBreakpoint = null;
		IBreakpoint breakpoint = getBreakpoint();
		if (breakpoint != null && (breakpoint instanceof IRuleBreakpoint)) {
			fBreakpoint = breakpoint;
		}
		setEnabled(fBreakpoint != null);
	}	
	
}
