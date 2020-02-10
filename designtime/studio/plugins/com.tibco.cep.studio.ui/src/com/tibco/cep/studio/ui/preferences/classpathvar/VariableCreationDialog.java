package com.tibco.cep.studio.ui.preferences.classpathvar;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;
import com.tibco.cep.studio.ui.views.StatusInfo;

public class VariableCreationDialog extends StatusDialog {

	private IDialogSettings fDialogSettings;

	private VariableStringDialogField fNameField;
	private StatusInfo fNameStatus;

	private VariableStringButtonDialogField fPathField;
	private StatusInfo fPathStatus;
	private VariableSelectButtonDialogField fDirButton;

	private ClasspathVariableElement fElement;

	private List<ClasspathVariableElement> fExistingNames;

	public VariableCreationDialog(Shell parent, ClasspathVariableElement element, List<ClasspathVariableElement> existingNames) {
		super(parent);
		if (element == null) {
			setTitle("New Variable Entry");
		} else {
			setTitle("Edit Variable Entry");
		}

		fDialogSettings= StudioUIPlugin.getDefault().getDialogSettings();

		fElement= element;

		fNameStatus= new StatusInfo();
		fPathStatus= new StatusInfo();

		NewVariableAdapter adapter= new NewVariableAdapter();
		fNameField= new VariableStringDialogField();
		fNameField.setDialogFieldListener(adapter);
		fNameField.setLabelText("&Name:");

		fPathField= new VariableStringButtonDialogField(adapter);
		fPathField.setDialogFieldListener(adapter);
		fPathField.setLabelText("&Path:");
		fPathField.setButtonLabel("&File...");

		fDirButton= new VariableSelectButtonDialogField(SWT.PUSH);
		fDirButton.setDialogFieldListener(adapter);
		fDirButton.setLabelText("F&older...");

		fExistingNames= existingNames;

		if (element != null) {
			fNameField.setText(element.getName());
			fPathField.setText(element.getPath().toString());
			fExistingNames.remove(element.getName());
		} else {
			fNameField.setText(""); //$NON-NLS-1$
			fPathField.setText(""); //$NON-NLS-1$
		}
	}

	/*
	 * @see Windows#configureShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, "org.eclipse.jdt.ui.variable_creation_dialog_context");
	}


	public ClasspathVariableElement getClasspathElement() {
		return new ClasspathVariableElement(fNameField.getText(), new Path(fPathField.getText()));
	}

	/*
	 * @see Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite= (Composite) super.createDialogArea(parent);

		Composite inner= new Composite(composite, SWT.NONE);
		inner.setFont(composite.getFont());

		GridLayout layout= new GridLayout();
		layout.marginWidth= 0;
		layout.marginHeight= 0;
		layout.numColumns= 4;
		inner.setLayout(layout);

		int fieldWidthHint= convertWidthInCharsToPixels(50);

		fNameField.doFillIntoGrid(inner, 2);
		ClasspathVariableUiUtils.setWidthHint(fNameField.getTextControl(null), fieldWidthHint);
		ClasspathVariableUiUtils.setHorizontalGrabbing(fNameField.getTextControl(null));

		VariableDialogField.createEmptySpace(inner, 2);

		fPathField.doFillIntoGrid(inner, 3);
		ClasspathVariableUiUtils.setWidthHint(fPathField.getTextControl(null), fieldWidthHint);

		fDirButton.doFillIntoGrid(inner, 1);

		VariableDialogField focusField= (fElement == null) ? fNameField : fPathField;
		focusField.postSetFocusOnDialogField(parent.getDisplay());
		applyDialogFont(composite);
		return composite;
	}


	// -------- NewVariableAdapter --------

	private class NewVariableAdapter implements IVariableDialogFieldListener, IVariableStringButtonAdapter {

		// -------- IDialogFieldListener
		public void dialogFieldChanged(VariableDialogField field) {
			doFieldUpdated(field);
		}

		// -------- IStringButtonAdapter
		public void changeControlPressed(VariableDialogField field) {
			doChangeControlPressed(field);
		}
	}

	private void doChangeControlPressed(VariableDialogField field) {
		if (field == fPathField) {
			IPath path= chooseExtJarFile();
			if (path != null) {
				fPathField.setText(path.toString());
			}
		}
	}

	private void doFieldUpdated(VariableDialogField field) {
		if (field == fNameField) {
			fNameStatus= nameUpdated();
		} else if (field == fPathField) {
			fPathStatus= pathUpdated();
		} else if (field == fDirButton) {
			IPath path= chooseExtDirectory();
			if (path != null) {
				fPathField.setText(path.toString());
			}
		}
		updateStatus(ClasspathVariableUiUtils.getMoreSevere(fPathStatus, fNameStatus));
	}

	private StatusInfo nameUpdated() {
		StatusInfo status= new StatusInfo();
		String name= fNameField.getText();
		if (name.length() == 0) {
			status.setError("Enter a variable name.");
			return status;
		}
		if (name.trim().length() != name.length()) {
			status.setError("The variable name starts or ends with white spaces.");
		} else if (!Path.ROOT.isValidSegment(name)) {
			status.setError("The variable name contains ':', '/' or '\\'.");
		} else if (nameConflict(name)) {
			status.setError("Variable name already exists.");
		}
		return status;
	}

	private boolean nameConflict(String name) {
		if (fElement != null && fElement.getName().equals(name)) {
			return false;
		}
		for (int i= 0; i < fExistingNames.size(); i++) {
			ClasspathVariableElement elem= fExistingNames.get(i);
			if (name.equals(elem.getName())){
				return true;
			}
		}
		return false;
	}


	private StatusInfo pathUpdated() {
		StatusInfo status= new StatusInfo();

		String path= fPathField.getText();
		if (path.length() > 0) { // empty path is ok
			if (!Path.ROOT.isValidPath(path)) {
				status.setError("The path is invalid.");
			} else if (!new File(path).exists()) {
				status.setWarning("Path does not exist.");
			}
		}
		return status;
	}


	private String getInitPath() {
		String initPath= fPathField.getText();
		if (initPath.length() == 0) {
			initPath= fDialogSettings.get("dialogstore.last.ext.jar");
			if (initPath == null) {
				initPath= ""; //$NON-NLS-1$
			}
		} else {
			IPath entryPath= new Path(initPath);
			if (ClasspathVariableUiUtils.isArchivePath(entryPath, true)) {
				entryPath.removeLastSegments(1);
			}
			initPath= entryPath.toOSString();
		}
		return initPath;
	}


	/*
	 * Open a dialog to choose a jar from the file system
	 */
	private IPath chooseExtJarFile() {
		String initPath= getInitPath();

		FileDialog dialog= new FileDialog(getShell());
		dialog.setText("JAR Selection");
		dialog.setFilterExtensions(ClasspathVariableUiUtils.ALL_ARCHIVES_FILTER_EXTENSIONS);
		dialog.setFilterPath(initPath);
		String res= dialog.open();
		if (res != null) {
			fDialogSettings.put("dialogstore.last.ext.jar", dialog.getFilterPath());
			return Path.fromOSString(res).makeAbsolute();
		}
		return null;
	}

	private IPath chooseExtDirectory() {
		String initPath= getInitPath();

		DirectoryDialog dialog= new DirectoryDialog(getShell());
		dialog.setText("Folder Selection");
		dialog.setMessage("Specify the folder to be represented by the variable:");
		dialog.setFilterPath(initPath);
		String res= dialog.open();
		if (res != null) {
			fDialogSettings.put("dialogstore.last.ext.jar", dialog.getFilterPath());
			return Path.fromOSString(res);
		}
		return null;
	}
}