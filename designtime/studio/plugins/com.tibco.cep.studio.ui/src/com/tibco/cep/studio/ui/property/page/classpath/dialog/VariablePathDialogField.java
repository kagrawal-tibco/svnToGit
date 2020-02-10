package com.tibco.cep.studio.ui.property.page.classpath.dialog;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.classpathvar.ClasspathVariableElement;
import com.tibco.cep.studio.ui.preferences.classpathvar.IVariableStringButtonAdapter;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableBlock;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableStringButtonDialogField;
import com.tibco.cep.studio.ui.property.page.classpath.VariableStatusInfo;
import com.tibco.cep.studio.ui.util.Messages;

public class VariablePathDialogField extends VariableStringButtonDialogField {

	public static class ChooseVariableDialog extends StatusDialog implements ISelectionChangedListener, IDoubleClickListener {
		private VariableBlock fVariableBlock;

		public ChooseVariableDialog(Shell parent, String variableSelection) {
			super(parent);

			setTitle(Messages.getString("VariablePathDialogField_variabledialog_title"));
			fVariableBlock= new VariableBlock(false, variableSelection);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite composite= (Composite) super.createDialogArea(parent);
			Control control= fVariableBlock.createContents(composite);

			GridData data= new GridData(GridData.FILL_BOTH);
			data.widthHint= convertWidthInCharsToPixels(80);
			data.heightHint= convertHeightInCharsToPixels(15);
			control.setLayoutData(data);

			fVariableBlock.addDoubleClickListener(this);
			fVariableBlock.addSelectionChangedListener(this);
			applyDialogFont(composite);
			return composite;
		}

		/*
		 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
		 * @since 3.4
		 */
		@Override
		protected boolean isResizable() {
			return true;
		}

		@Override
		protected void okPressed() {
			fVariableBlock.performOk();
			super.okPressed();
		}

		public String getSelectedVariable() {
			List<ClasspathVariableElement> elements= fVariableBlock.getSelectedElements();
			return elements.get(0).getName();
		}

		/*
		 * @see IDoubleClickListener#doubleClick(DoubleClickEvent)
		 */
		public void doubleClick(DoubleClickEvent event) {
			if (getStatus().isOK()) {
				okPressed();
			}
		}

		/* (non-Javadoc)
		 * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			List<ClasspathVariableElement> elements= fVariableBlock.getSelectedElements();
			VariableStatusInfo status= new VariableStatusInfo();
			if (elements.size() != 1) {
				status.setError(""); //$NON-NLS-1$
			}
			updateStatus(status);
		}
		/*
		 * @see org.eclipse.jface.window.Window#configureShell(Shell)
		 */
		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
//			PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, IJavaHelpContextIds.CHOOSE_VARIABLE_DIALOG);
		}
	}

	private Button fBrowseVariableButton;
	private String fVariableButtonLabel;

	public VariablePathDialogField(IVariableStringButtonAdapter adapter) {
		super(adapter);
	}

	public void setVariableButtonLabel(String label) {
		fVariableButtonLabel= label;
	}

	// ------- layout helpers

	@Override
	public Control[] doFillIntoGrid(Composite parent, int nColumns) {
		Label label= getLabelControl(parent);
		label.setLayoutData(gridDataForLabel(1));
		Text text= getTextControl(parent);
		text.setLayoutData(gridDataForText(nColumns - 3));
		Button variableButton= getBrowseVariableControl(parent);
		variableButton.setLayoutData(gridDataForButton(variableButton, 1));
		Button browseButton= getChangeControl(parent);
		browseButton.setLayoutData(gridDataForButton(browseButton, 1));
		return new Control[] { label, text, variableButton, browseButton };
	}

	@Override
	public int getNumberOfControls() {
		return 4;
	}
	public Button getBrowseVariableControl(Composite parent) {
		if (fBrowseVariableButton == null) {
			fBrowseVariableButton= new Button(parent, SWT.PUSH);
			fBrowseVariableButton.setText(fVariableButtonLabel);
			fBrowseVariableButton.setEnabled(isEnabled());
			fBrowseVariableButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					chooseVariablePressed();
				}
				public void widgetSelected(SelectionEvent e) {
					chooseVariablePressed();
				}
			});

		}
		return fBrowseVariableButton;
	}

	public IPath getPath() {
		return new Path(getText());
	}

	public String getVariable() {
		IPath path= getPath();
		if (!path.isEmpty()) {
			return path.segment(0);
		}
		return null;
	}

	public IPath getPathExtension() {
		return new Path(getText()).removeFirstSegments(1).setDevice(null);
	}

	public IPath getResolvedPath() {
		String variable= getVariable();
		if (variable != null) {
			IPath path= StudioCore.getClasspathVariable(variable);
			if (path != null) {
				return path.append(getPathExtension());
			}
		}
		return null;
	}

	private Shell getShell() {
		if (fBrowseVariableButton != null) {
			return fBrowseVariableButton.getShell();
		}
		return StudioUIPlugin.getShell();
	}

	private void chooseVariablePressed() {
		String variable= getVariable();
		ChooseVariableDialog dialog= new ChooseVariableDialog(getShell(), variable);
		if (dialog.open() == Window.OK) {
			IPath newPath= new Path(dialog.getSelectedVariable()).append(getPathExtension());
			setText(newPath.toString());
		}
	}

	@Override
	protected void updateEnableState() {
		super.updateEnableState();
		if (isOkToUse(fBrowseVariableButton)) {
			fBrowseVariableButton.setEnabled(isEnabled());
		}
	}

}
