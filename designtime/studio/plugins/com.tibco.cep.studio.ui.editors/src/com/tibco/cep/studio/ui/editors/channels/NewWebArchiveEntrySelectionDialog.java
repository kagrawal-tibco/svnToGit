package com.tibco.cep.studio.ui.editors.channels;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.classpathvar.IVariableDialogFieldListener;
import com.tibco.cep.studio.ui.preferences.classpathvar.IVariableStringButtonAdapter;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableDialogField;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableSelectButtonDialogField;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableStringButtonDialogField;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;
import com.tibco.cep.studio.ui.views.StatusInfo;

/**
 * 
 * @author sasahoo
 *
 */
public class NewWebArchiveEntrySelectionDialog extends StatusDialog {

	private IDialogSettings fDialogSettings;

	private VariableStringButtonDialogField fPathField;
	private StatusInfo fPathStatus;
	private VariableSelectButtonDialogField fDirButton;
	public static final String[] WAR_ARCHIVES_FILTER_EXTENSIONS= new String[] {"*.war"}; //$NON-NLS-1$ //$NON-NLS-2$
	public String path = "";

	public NewWebArchiveEntrySelectionDialog(Shell parent, String path) {
		super(parent);
		setTitle("New Web Application Entry");

		fDialogSettings= StudioUIPlugin.getDefault().getDialogSettings();
		fPathStatus= new StatusInfo();

		NewVariableAdapter adapter= new NewVariableAdapter();

		fPathField= new VariableStringButtonDialogField(adapter);
		fPathField.setDialogFieldListener(adapter);
		fPathField.setLabelText("&Path:");
		fPathField.setButtonLabel("&File...");
		
		fDirButton= new VariableSelectButtonDialogField(SWT.PUSH);
		fDirButton.setDialogFieldListener(adapter);
		fDirButton.setLabelText("F&older...");
		if (path != null) {
			this.path = path;
			fPathField.setText(path);
		} else {
			fPathField.setText(""); //$NON-NLS-1$
		}
	}
	
	public Path getPath() {
		return new Path(fPathField.getText());
	}

	/*
	 * @see Windows#configureShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
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

		fPathField.doFillIntoGrid(inner, 3);
		ClasspathVariableUiUtils.setWidthHint(fPathField.getTextControl(null), fieldWidthHint);

		fDirButton.doFillIntoGrid(inner, 1);

		VariableDialogField focusField= fPathField;
		focusField.postSetFocusOnDialogField(parent.getDisplay());
		applyDialogFont(composite);
		
		fPathField.getTextControl(null).addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				String path= fPathField.getText();
				getButton(IDialogConstants.OK_ID).setEnabled(!path.isEmpty());

			}
		});
		
		return composite;
	}
	
	public void create() {
	  super.create();
	  getButton(IDialogConstants.OK_ID).setEnabled(!path.isEmpty());
	}


	private class NewVariableAdapter implements IVariableDialogFieldListener, IVariableStringButtonAdapter {

		/* (non-Javadoc)
		 * @see com.tibco.cep.studio.ui.preferences.classpathvar.dialogfields.IDialogFieldListener#dialogFieldChanged(com.tibco.cep.studio.ui.preferences.classpathvar.dialogfields.DialogField)
		 */
		public void dialogFieldChanged(VariableDialogField field) {
			doFieldUpdated(field);
		}

		/* (non-Javadoc)
		 * @see com.tibco.cep.studio.ui.preferences.classpathvar.dialogfields.IStringButtonAdapter#changeControlPressed(com.tibco.cep.studio.ui.preferences.classpathvar.dialogfields.DialogField)
		 */
		public void changeControlPressed(VariableDialogField field) {
			doChangeControlPressed(field);
		}
	}

	private void doChangeControlPressed(VariableDialogField field) {
		if (field == fPathField) {
			IPath path= chooseExtWarFile();
			if (path != null) {
				fPathField.setText(path.toString());
			}
		}
	}

	private void doFieldUpdated(VariableDialogField field) {
		if (field == fPathField) {
			fPathStatus= pathUpdated();
		} else if (field == fDirButton) {
			IPath path= chooseExtDirectory();
			if (path != null) {
				fPathField.setText(path.toString());
			}
		}
		updateStatus(fPathStatus);
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
			initPath= fDialogSettings.get("dialogstore.last.ext.war");
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
	 * Open a dialog to choose a War from the file system
	 */
	private IPath chooseExtWarFile() {
		String initPath= getInitPath();

		FileDialog dialog= new FileDialog(getShell());
		dialog.setText("WAR Selection");
		dialog.setFilterExtensions(WAR_ARCHIVES_FILTER_EXTENSIONS);
		dialog.setFilterPath(initPath);
		String res= dialog.open();
		if (res != null) {
			fDialogSettings.put("dialogstore.last.ext.war", dialog.getFilterPath());
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
			fDialogSettings.put("dialogstore.last.ext.war", dialog.getFilterPath());
			return Path.fromOSString(res);
		}
		return null;
	}
}