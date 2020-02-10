package com.tibco.cep.studio.ui.property.page.classpath.dialog;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.ui.preferences.classpathvar.IVariableDialogFieldListener;
import com.tibco.cep.studio.ui.preferences.classpathvar.IVariableStringButtonAdapter;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableDialogField;
import com.tibco.cep.studio.ui.property.page.classpath.VariableStatusInfo;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;
import com.tibco.cep.studio.ui.util.Messages;


public class EditVariableEntryDialog extends StatusDialog {

	/**
	 * The path to which the archive variable points.
	 * Null if invalid path or not resolvable. Must not exist.
	 */
	private IPath fFileVariablePath;

	private IStatus fNameStatus;

	private Set<IPath> fExistingEntries;
	private VariablePathDialogField fFileNameField;
	private CLabel fFullPathResolvedLabel;

	private boolean projectLib;

	public EditVariableEntryDialog(Shell parent, IPath initialEntry, IPath[] existingEntries, boolean projectLib) {
		super(parent);
		setTitle(Messages.getString("EditVariableEntryDialog_title"));

		this.projectLib = projectLib;
		
		fExistingEntries= new HashSet<IPath>();
		if (existingEntries != null) {
			for (int i = 0; i < existingEntries.length; i++) {
				IPath curr= existingEntries[i];
				if (!curr.equals(initialEntry)) {
					fExistingEntries.add(curr);
				}
			}
		}

		SourceAttachmentAdapter adapter= new SourceAttachmentAdapter();

		fFileNameField= new VariablePathDialogField(adapter);
		fFileNameField.setDialogFieldListener(adapter);
		fFileNameField.setLabelText(Messages.getString("EditVariableEntryDialog_filename_varlabel"));
		fFileNameField.setButtonLabel(Messages.getString("EditVariableEntryDialog_filename_external_varbutton"));
		fFileNameField.setVariableButtonLabel(Messages.getString("EditVariableEntryDialog_filename_variable_button"));
		String initialString= initialEntry != null ? initialEntry.toString() : ""; //$NON-NLS-1$
		fFileNameField.setText(initialString);
	}

	/*
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 * @since 3.4
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	public IPath getPath() {
		return Path.fromOSString(fFileNameField.getText());
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite= (Composite) super.createDialogArea(parent);

		GridLayout layout= (GridLayout) composite.getLayout();
		layout.numColumns= 3;

		int widthHint= convertWidthInCharsToPixels(50);

		GridData gd= new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan= 3;

		// archive name field
		fFileNameField.doFillIntoGrid(composite, 4);
		ClasspathVariableUiUtils.setHorizontalSpan(fFileNameField.getLabelControl(null), 3);
		ClasspathVariableUiUtils.setWidthHint(fFileNameField.getTextControl(null), widthHint);
		ClasspathVariableUiUtils.setHorizontalGrabbing(fFileNameField.getTextControl(null));

		// label that shows the resolved path for variable jars
		//DialogField.createEmptySpace(composite, 1);
		fFullPathResolvedLabel= new CLabel(composite, SWT.LEFT);
		fFullPathResolvedLabel.setText(getResolvedLabelString());
		fFullPathResolvedLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		VariableDialogField.createEmptySpace(composite, 2);


		fFileNameField.postSetFocusOnDialogField(parent.getDisplay());

//		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IJavaHelpContextIds.SOURCE_ATTACHMENT_BLOCK);
		applyDialogFont(composite);
		return composite;
	}

	private class SourceAttachmentAdapter implements IVariableStringButtonAdapter, IVariableDialogFieldListener {

		// -------- IStringButtonAdapter --------
		public void changeControlPressed(VariableDialogField field) {
			attachmentChangeControlPressed(field);
		}

		// ---------- IDialogFieldListener --------
		public void dialogFieldChanged(VariableDialogField field) {
			attachmentDialogFieldChanged(field);
		}
	}

	private void attachmentChangeControlPressed(VariableDialogField field) {
		if (field == fFileNameField) {
			IPath jarFilePath= chooseExtJarFile();
			if (jarFilePath != null) {
				fFileNameField.setText(jarFilePath.toString());
			}
		}
	}

	// ---------- IDialogFieldListener --------

	private void attachmentDialogFieldChanged(VariableDialogField field) {
		if (field == fFileNameField) {
			fNameStatus= updateFileNameStatus();
		}
		doStatusLineUpdate();
	}


	private IPath chooseExtJarFile() {
		IPath currPath= getPath();
		IPath resolvedPath= getResolvedPath(currPath);
		File initialSelection= resolvedPath != null ? resolvedPath.toFile() : null;

		String currVariable= currPath.segment(0);
		ArchiveFileSelectionDialog dialog= new ArchiveFileSelectionDialog(getShell(), false, false, true, projectLib);
		dialog.setTitle(Messages.getString("EditVariableEntryDialog_extvardialog_title"));
		dialog.setMessage(Messages.getString("EditVariableEntryDialog_extvardialog_description"));
		dialog.setInput(fFileVariablePath.toFile());
		dialog.setInitialSelection(initialSelection);
		if (dialog.open() == Window.OK) {
			File result= (File) dialog.getResult()[0];
			IPath returnPath= Path.fromOSString(result.getPath()).makeAbsolute();
			return modifyPath(returnPath, currVariable);
		}
		return null;
	}

	/**
	 * @param path
	 * @return
	 */
	private IPath getResolvedPath(IPath path) {
		if (path != null) {
			String varName= path.segment(0);
			if (varName != null) {
				IPath varPath= StudioCore.getClasspathVariable(varName);
				if (varPath != null) {
					return varPath.append(path.removeFirstSegments(1));
				}
			}
		}
		return null;
	}

	/**
	 * Takes a path and replaces the beginning with a variable name
	 * (if the beginning matches with the variables value).
	 *
	 * @param path the path
	 * @param varName the variable name
	 * @return the modified path
	 */
	private IPath modifyPath(IPath path, String varName) {
		if (varName == null || path == null) {
			return null;
		}
		if (path.isEmpty()) {
			return new Path(varName);
		}

		IPath varPath= StudioCore.getClasspathVariable(varName);
		if (varPath != null) {
			if (varPath.isPrefixOf(path)) {
				path= path.removeFirstSegments(varPath.segmentCount());
			} else {
				path= new Path(path.lastSegment());
			}
		} else {
			path= new Path(path.lastSegment());
		}
		return new Path(varName).append(path);
	}

	private IStatus updateFileNameStatus() {
		VariableStatusInfo status= new VariableStatusInfo();
		fFileVariablePath= null;

		String fileName= fFileNameField.getText();
		if (fileName.length() == 0) {
			status.setError(Messages.getString("EditVariableEntryDialog_filename_empty"));
			return status;
		} else {
			if (!Path.EMPTY.isValidPath(fileName)) {
				status.setError(Messages.getString("EditVariableEntryDialog_filename_error_notvalid"));
				return status;
			}
			IPath filePath= Path.fromOSString(fileName);
			IPath resolvedPath;


			if (filePath.getDevice() != null) {
				status.setError(Messages.getString("EditVariableEntryDialog_filename_error_deviceinpath"));
				return status;
			}
			String varName= filePath.segment(0);
			if (varName == null) {
				status.setError(Messages.getString("EditVariableEntryDialog_filename_error_notvalid"));
				return status;
			}
			fFileVariablePath= StudioCore.getClasspathVariable(varName);
			if (fFileVariablePath == null) {
				status.setError(Messages.getString("EditVariableEntryDialog_filename_error_varnotexists"));
				return status;
			}

			String deprecationMessage= ClasspathVariableUiUtils.getDeprecationMessage(varName);

			resolvedPath= fFileVariablePath.append(filePath.removeFirstSegments(1));
			if (resolvedPath.isEmpty()) {
				String message= Messages.getString("EditVariableEntryDialog_filename_warning_varempty");
				if (deprecationMessage != null) {
					message= deprecationMessage + "\n" + message; //$NON-NLS-1$
				}
				status.setWarning(message);
				return status;
			}
			File file= resolvedPath.toFile();
			if (!file.exists()) {
				String message= Messages.getString("EditVariableEntryDialog_filename_error_filenotexists", ClasspathVariableUiUtils.getPathLabel(resolvedPath, true));
				if (deprecationMessage != null) {
					message= deprecationMessage + "\n" + message; //$NON-NLS-1$
					status.setWarning(message);
				} else {
					status.setInfo(message);
				}
				return status;
			}
			if (deprecationMessage != null) {
				status.setWarning(deprecationMessage);
				return status;
			}
		}
		return status;
	}

	private String getResolvedLabelString() {
		IPath resolvedPath= getResolvedPath(getPath());
		if (resolvedPath != null) {
			return ClasspathVariableUiUtils.getPathLabel(resolvedPath, true);
		}
		return ""; //$NON-NLS-1$
	}

	private boolean canBrowseFileName() {
		// to browse with a variable JAR, the variable name must point to a directory
		if (fFileVariablePath != null) {
			return fFileVariablePath.toFile().isDirectory();
		}
		return false;
	}

	private void doStatusLineUpdate() {
		fFileNameField.enableButton(canBrowseFileName());

		// set the resolved path for variable jars
		if (fFullPathResolvedLabel != null) {
			fFullPathResolvedLabel.setText(getResolvedLabelString());
		}

		IStatus status= fNameStatus;
		if (!status.matches(IStatus.ERROR)) {
			IPath path= getPath();
			if (fExistingEntries.contains(path)) {
				String message= Messages.getString("EditVariableEntryDialog_filename_error_alreadyexists");
				status= new VariableStatusInfo(IStatus.ERROR, message);
			}
		}
		updateStatus(status);
	}

	/*
	 * overridden to ensure full message is visible
	 * @see org.eclipse.jface.dialogs.StatusDialog#updateStatus(org.eclipse.core.runtime.IStatus)
	 */
	@Override
	protected void updateStatus(IStatus status) {
		super.updateStatus(status);
		Shell shell= getShell();
		if (shell != null && ! shell.isDisposed()) {
			Point size= shell.getSize();
			Point minSize= shell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			if (minSize.x > size.x || minSize.y > size.y) {
				shell.setSize(minSize);
			}
		}
	}
}
