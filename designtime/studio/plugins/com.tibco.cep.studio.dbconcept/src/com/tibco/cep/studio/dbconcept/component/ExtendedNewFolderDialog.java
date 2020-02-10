package com.tibco.cep.studio.dbconcept.component;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.tibco.cep.studio.dbconcept.ModulePlugin;

public class ExtendedNewFolderDialog extends SelectionStatusDialog {

	private Text folderNameField;
	private IContainer container;

	/**
	 * @param parentShell
	 * @param container
	 */
	public ExtendedNewFolderDialog(Shell parentShell, IContainer container) {
		super(parentShell);
		this.container = container;
		setTitle("New Folder");
		setStatusLineAboveButtons(true);
	}

	/* (non-Javadoc)
	 * Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
	}

	/**
	 * @see org.eclipse.jface.window.Window#create()
	 */
	public void create() {
		super.create();
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		createFolderNameGroup(composite);
		return composite;
	}

	/**
	 * @param parent
	 */
	private void createFolderNameGroup(Composite parent) {
		Font font = parent.getFont();
		Composite folderGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		folderGroup.setLayout(layout);
		folderGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label folderLabel = new Label(folderGroup, SWT.NONE);
		folderLabel.setFont(font);
		folderLabel.setText("&Folder name:");

		folderNameField = new Text(folderGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		folderNameField.setLayoutData(data);
		folderNameField.setFont(font);
		folderNameField.addListener(SWT.Modify, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				validateLinkedResource();
			}
		});
	}

	/**
	 * @param folderName
	 * @return
	 */
	private IFolder createFolderHandle(String folderName) {
		IWorkspaceRoot workspaceRoot = container.getWorkspace().getRoot();
		IPath folderPath = container.getFullPath().append(folderName);
		IFolder folderHandle = workspaceRoot.getFolder(folderPath);

		return folderHandle;
	}

	/**
	 * @param folderName
	 * @param linkTarget
	 * @return
	 */
	private IFolder createNewFolder(String folderName, final URI linkTarget) {
		final IFolder folderHandle = createFolderHandle(folderName);
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			/* (non-Javadoc)
			 * @see org.eclipse.ui.actions.WorkspaceModifyOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
			 */
			public void execute(IProgressMonitor monitor) throws CoreException {
				try {
					monitor
							.beginTask(
									"Creating new folder",
									2000);
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					if (linkTarget == null) {
						folderHandle.create(false, true, monitor);
					} else {
						folderHandle.createLink(linkTarget,
								IResource.ALLOW_MISSING_LOCAL, monitor);
					}
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
				} finally {
					monitor.done();
				}
			}
		};
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(
					operation);
		} catch (InterruptedException exception) {
			return null;
		} catch (InvocationTargetException exception) {
			if (exception.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getShell(),
						"Creation Problems", null, // no special message
						((CoreException) exception.getTargetException())
								.getStatus());
			} else {
				MessageDialog
						.openError(
								getShell(),
								"Creation Problems",
								"Internal error:" +
												exception.getTargetException()
														.getMessage());
			}
			return null;
		}
		return folderHandle;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#updateStatus(org.eclipse.core.runtime.IStatus)
	 */
	protected void updateStatus(IStatus status) {
		if (status != null) {
			Status newStatus = new Status(IStatus.OK, status.getPlugin(),
					status.getCode(), status.getMessage(), status
							.getException());
			super.updateStatus(newStatus);
		} else {
			super.updateStatus(status);
		}
	}

	/**
	 * @param severity
	 * @param message
	 */
	private void updateStatus(int severity, String message) {
		updateStatus(new Status(severity, ModulePlugin.PLUGIN_ID,
				severity, message, null));
	}

	private void validateLinkedResource() {
		boolean valid = validateFolderName();
		if (valid) {
			createFolderHandle(folderNameField.getText());
			getOkButton().setEnabled(true);
		} else {
			getOkButton().setEnabled(false);
		}
	}

	/**
	 * @return
	 */
	private boolean validateFolderName() {
		String name = folderNameField.getText();
		IWorkspace workspace = container.getWorkspace();
		IStatus nameStatus = workspace.validateName(name, IResource.FOLDER);

		if ("".equals(name)) { //$NON-NLS-1$
			updateStatus(IStatus.ERROR,"Folder name must be specified");
			return false;
		}
		
		if (name != null
				&& (name.contains("$") || 
					name.contains("#") || 
					name.contains("&") ||
					name.contains("^") ||
					name.contains("@") ||
					name.contains("%"))) {
			updateStatus(IStatus.ERROR, NLS.bind("Invalid folder name", name));
			return false;
		}
		
		if (nameStatus.isOK() == false) {
			updateStatus(nameStatus);
			return false;
		}
		IPath path = new Path(name);
		if (container.getFolder(path).exists()
				|| container.getFile(path).exists()) {
			updateStatus(IStatus.ERROR, "The folder " + name + " already exists." );
			return false;
		}
		updateStatus(IStatus.OK, ""); //$NON-NLS-1$
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#okPressed()
	 */
	protected void okPressed() {
		IFolder folder = createNewFolder(folderNameField.getText(), null);
		if (folder == null) {
			return;
		}
		setSelectionResult(new IFolder[] { folder });
		super.okPressed();
	}

	@Override
	protected void computeResult() {
		// TODO Auto-generated method stub
	}
}