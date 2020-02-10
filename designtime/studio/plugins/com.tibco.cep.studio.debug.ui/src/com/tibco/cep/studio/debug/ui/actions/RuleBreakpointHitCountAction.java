package com.tibco.cep.studio.debug.ui.actions;

import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;

import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleBreakpoint;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;

/**
 * @author pdhar
 *
 */
public class RuleBreakpointHitCountAction extends AbstractObjectActionDelegate {

	public RuleBreakpointHitCountAction() {
		// TODO Auto-generated constructor stub
	}
	private static final String INITIAL_VALUE= "1"; //$NON-NLS-1$

	/**
	 * A dialog that sets the focus to the text area.
	 */
	class HitCountDialog extends InputDialog {
		
		private boolean fHitCountEnabled;
		
		protected  HitCountDialog(Shell parentShell,
									String dialogTitle,
									String dialogMessage,
									String initialValue,
									IInputValidator validator) {
			super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
		}
		

		protected Control createDialogArea(Composite parent) {
			Composite area= (Composite)super.createDialogArea(parent);
			
			final Button checkbox = new Button(area, SWT.CHECK);
			GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL);
			data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
			checkbox.setLayoutData(data);
			checkbox.setFont(parent.getFont());
			checkbox.setText("Enable &Hit Count"); 
			checkbox.setSelection(true);
			fHitCountEnabled = true;
			checkbox.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					fHitCountEnabled = checkbox.getSelection();
					getText().setEnabled(fHitCountEnabled);
					if (fHitCountEnabled) {
						validateInput();
					} else {
						setErrorMessage(null); 
					}
				}
				
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			
			return area;
		}

		protected boolean isHitCountEnabled() {
			return fHitCountEnabled;
		}
	}


	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		IStructuredSelection selection= getCurrentSelection();
		if (selection == null) {
			return;
		}
		Iterator<?> itr= selection.iterator();
		if (!itr.hasNext()) {
			return;
		}

		while (itr.hasNext()) {
			RuleBreakpoint breakpoint= (RuleBreakpoint)itr.next();
			try {
				int oldHitCount= breakpoint.getHitCount();
				int newHitCount= hitCountDialog(breakpoint);
				if (newHitCount != -1) {					
					if (oldHitCount == newHitCount && newHitCount == 0) {
						return;
					}
					breakpoint.setHitCount(newHitCount);
				}
			} catch (CoreException ce) {
				StudioDebugUIPlugin.statusDialog(ce.getStatus()); 
			}
		}
	}
	
	protected int hitCountDialog(IRuleBreakpoint breakpoint) {
		String title= "Set Breakpoint Hit Count"; 
		String message="&Enter the new hit count for the breakpoint:"; 
		IInputValidator validator= new IInputValidator() {
			int hitCount= -1;
			public String isValid(String value) {
				try {
					hitCount= Integer.valueOf(value.trim()).intValue();
				} catch (NumberFormatException nfe) {
					hitCount= -1;
				}
				if (hitCount < 1) {
					return "Hit count must be a positive integer"; 
				}
				//no error
				return null;
			}
		};

		int currentHitCount= 0;
		try {
			currentHitCount = breakpoint.getHitCount();
		} catch (CoreException e) {
			StudioDebugUIPlugin.log(e);
		}
		String initialValue;
		if (currentHitCount > 0) {
			initialValue= Integer.toString(currentHitCount);
		} else {
			initialValue= INITIAL_VALUE;
		}
		Shell activeShell= StudioDebugUIPlugin.getActiveWorkbenchShell();
		HitCountDialog dialog= new HitCountDialog(activeShell, title, message, initialValue, validator);
		if (dialog.open() != Window.OK) {
			return -1;
		}
		if (dialog.isHitCountEnabled()) {
			return Integer.parseInt(dialog.getValue().trim());
		}
		return 0;
	}
}

