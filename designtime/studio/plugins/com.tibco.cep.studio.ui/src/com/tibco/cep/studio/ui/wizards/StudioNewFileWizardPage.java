package com.tibco.cep.studio.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

import com.tibco.cep.studio.common.util.ModelNameUtil;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class StudioNewFileWizardPage extends WizardPage implements Listener {
	
	protected static final int CONTAINER_HEIGHT = 250;
	private IStructuredSelection currentSelection;
	public IFile newFile;
	protected StudioResourceContainer resourceContainer;
	protected String fileName;
	protected String _currentProjectName;
	protected String fileExtension;
	protected String resourceExtension;
	private IPath containerFullPath;

	
	/**
	 * @param pageName
	 * @param selection
	 */
	public StudioNewFileWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		setPageComplete(false);
		this.currentSelection = selection;
	}

	/**
	 * (non-Javadoc) Method declared on IDialogPage.
	 */
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout());
		topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(parent.getFont());

		createResourceContainer(topLevel);
		
		validatePage();
		setErrorMessage(null);
		setMessage(null);
		setControl(topLevel);
	}

	/**
	 * @param fileHandle
	 * @param contents
	 * @param monitor
	 * @throws CoreException
	 */
	protected void createFile(IFile fileHandle, 
			                  InputStream contents,
			                  IProgressMonitor monitor) throws CoreException {
		if (contents == null) {
			contents = new ByteArrayInputStream(new byte[0]);
		}
		try {

				IPath path = fileHandle.getFullPath();
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				int numSegments = path.segmentCount();
				if (numSegments > 2
						&& !root.getFolder(path.removeLastSegments(1)).exists()) {
					for (int i = numSegments - 2; i > 0; i--) {
						IFolder folder = root.getFolder(path
								.removeLastSegments(i));
						if (!folder.exists()) {
							folder.create(false, true, monitor);
						}
					}
				}
				fileHandle.create(contents, false, monitor);
		} catch (CoreException e) {
			if (e.getStatus().getCode() == IResourceStatus.PATH_OCCUPIED) {
				fileHandle.refreshLocal(IResource.DEPTH_ZERO, null);
			} else {
				throw e;
			}
		}

		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	public IPath getContainerFullPath() {
		return resourceContainer.getContainerFullPath();
	}

	public String getFileName() {
		if (resourceContainer == null) {
			return fileName;
		}

		return resourceContainer.getResource();
	}
	
	/**
	 * @param container
	 * @param label
	 * @return
	 */
	protected Label createLabel(Composite container, String label) {
		return createLabel(container, label, 0);
	}
	
	/**
	 * @param container
	 * @param labelstr
	 * @param indent
	 * @return
	 */
	protected Label createLabel(Composite container, String labelstr, int indent) {
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = indent;
		label.setLayoutData(gData);
		label.setText(labelstr);
		return label;
	}
	
	/**
	 * @param container
	 * @return
	 */
	protected Text createText(Composite container) {
		Text text = new Text(container, SWT.BORDER);
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.widthHint = 150;
		text.setLayoutData(gData);
		return text;
	}
	
	/**
	 * @return
	 */
	public String getFileExtension() {
		if (resourceContainer == null) {
			return fileExtension;
		}
		return resourceContainer.getResourceExtension();		
	}

	protected InputStream getInitialContents() {
		return null;
	}

	protected String getNewFileLabel() {
		return "File na&me:";
	}

	public void handleEvent(Event event) {
		setPageComplete(validatePage());
	}

	
	protected void init() {
		if (containerFullPath != null) {
			resourceContainer.setContainerFullPath(containerFullPath);
		} else {
			if(currentSelection ==  null) return;
			@SuppressWarnings("rawtypes")
			Iterator it = currentSelection.iterator();
			if (it.hasNext()) {
				Object object = it.next();
				IResource selectedResource = null;
				if (object instanceof IResource) {
					selectedResource = (IResource) object;
				} else if (object instanceof IAdaptable) {
					selectedResource = (IResource) ((IAdaptable) object).getAdapter(IResource.class);
				}
				if (selectedResource != null) {
					if (selectedResource.getType() == IResource.FILE) {
						selectedResource = selectedResource.getParent();
					}
					if (selectedResource.isAccessible()) {
						resourceContainer.setContainerFullPath(selectedResource.getFullPath());
					}
				}
			}
		}
	}

	public void setContainerFullPath(IPath path) {
		if (resourceContainer == null) {
			containerFullPath = path;
		} else {
			resourceContainer.setContainerFullPath(path);
		}
	}

	public void setFileName(String value) {
		if (resourceContainer == null) {
			fileName = value;
		} else {
			resourceContainer.setResourceName(value);
		}
	}

	public void setFileExtension(String value) {
		if (resourceContainer == null) {
			fileExtension = value;
		} else {
			resourceContainer.setResourceExtension(value);
		}
	}

	protected boolean validatePage() {
		boolean valid = true;
		if (!resourceContainer.areAllValuesValid()) {
			// if blank name then fail silently
			if (resourceContainer.getProblemType() == StudioResourceContainer.PROBLEM_RESOURCE_EMPTY
					|| resourceContainer.getProblemType() == StudioResourceContainer.PROBLEM_CONTAINER_EMPTY) {
				setMessage(resourceContainer.getProblemMessage());
				setErrorMessage(null);
			} else {
				setErrorMessage(resourceContainer.getProblemMessage());
			}
			valid = false;
		}

		String resourceName = resourceContainer.getResource();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (resourceName != null && resourceName.length() > 0) {
			IStatus result = workspace.validateName(resourceName, IResource.FILE);
			if (!result.isOK()) {
				setErrorMessage(result.getMessage());
				return false;
			}
		} else {
			return false;
		}
		if (valid) {
			setMessage(null);
			setErrorMessage(null);
			if (resourceContainer.getAllowExistingResources()) {
				String problemMessage = getFileName() + " already exists";
//				IPath resourcePath = getContainerFullPath().append(getFileName());
//				if (workspace.getRoot().getFolder(resourcePath).exists()) {
//					setErrorMessage(problemMessage);
//					valid = false;
//				}
				IPath resourcePath2 = getContainerFullPath().append(getFileName()+getResourceExtension());
				if (workspace.getRoot().getFile(resourcePath2).exists()) {
//					setMessage(problemMessage, IMessageProvider.WARNING);
					setErrorMessage(problemMessage);
					valid = false;
				}
			}
		}
		return valid;
	}

	public boolean validateSharedResourceFileName() {
		if (getFileName().isEmpty() || !getFileName().endsWith(getFileExtension())) {
			setErrorMessage(Messages.getString("BE_Resource_invalidExtension", getFileExtension()));
			return false;
		}

		String name = getFileName().replaceAll("."+getFileExtension(), "");

    	if (name.length() == 0) {
    		setErrorMessage(Messages.getString("Empty_Name"));
    		return false;
    	}

    	//if (!EntityNameHelper.isValidSharedResourceIdentifier(name)){
    	if (!ModelNameUtil.isValidSharedResourceIdentifier(name)) {
    		String problemMessage = Messages.getString("BE_Resource_invalidFilename", name);
    		setErrorMessage(problemMessage);
    		return false;
    	}

    	//Validation for Bad Folder
    	if (!StudioUIUtils.isValidContainer(getContainerFullPath())) {
    		String problemMessage = null;
    		if(getContainerFullPath() == null){
    			problemMessage = Messages.getString("No_folder_specified");
    			setMessage(problemMessage);
    			setErrorMessage(null);
    		}
    		else if (getContainerFullPath() != null) {
    			problemMessage = com.tibco.cep.studio.core.util.Messages.getString("Resource.folder.bad", getContainerFullPath());
    			setErrorMessage(problemMessage);
    		}
    		return false;
    	}

    	IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
    	StringBuilder duplicateFileName  = new StringBuilder("");
    	if(isDuplicateBEResource(resource,resourceContainer.getResourceName(),duplicateFileName)){
    		String problemMessage = Messages.getString("BE_Resource_FilenameExists", duplicateFileName ,resourceContainer.getResourceName());
    		setErrorMessage(problemMessage);
    		return false;
    	}
    	return true;
	}
	
	public IFile createNewFile() {
		if (newFile != null) {
			return newFile;
		}
		final IPath containerPath = resourceContainer.getContainerFullPath();
		IPath newFilePath = containerPath.append(resourceContainer.getResource());
		final IFile newFileHandle = createFileHandle(newFilePath);
		final InputStream initialContents = getInitialContents();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				CreateFileOperation op = new CreateFileOperation(newFileHandle,
						null, initialContents,
						"New File");
				try {
					PlatformUI.getWorkbench().getOperationSupport()
							.getOperationHistory().execute(
									op,
									monitor,
									WorkspaceUndoUtil
											.getUIInfoAdapter(getShell()));
				} catch (final ExecutionException e) {
					getContainer().getShell().getDisplay().syncExec(
							new Runnable() {
								public void run() {
									if (e.getCause() instanceof CoreException) {
										ErrorDialog
												.openError(
														getContainer()
																.getShell(),"Creation Problems",
														null,  ((CoreException) e
																.getCause())
																.getStatus());
									} else {
										MessageDialog.openError(getContainer().getShell(),
														"Creation Problems",
														"Internal error: " +
													    e.getCause().getMessage());
									}
								}
							});
				}
			}
		};
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			MessageDialog.openError(getContainer().getShell(),
							"Creation problems",
							"Internal error:" +
							e.getTargetException().getMessage());

			return null;
		}

		newFile = newFileHandle;

		return newFile;
	}
	
	
	protected IFile createFileHandle(IPath filePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}
	
	protected void createAdvancedControls(Composite parent) {
		
	}
	
	protected void createResourceContainer(Composite container) {
		resourceContainer = new StudioResourceContainer(container, this,
				getNewFileLabel(),
				"file", false,
				CONTAINER_HEIGHT, _currentProjectName);
		resourceContainer.setAllowExistingResources(true);
		init();
		createAdvancedControls(container);
		if (fileName != null) {
			resourceContainer.setResourceName(fileName);
		}
		if (fileExtension != null) {
			resourceContainer.setResourceExtension(fileExtension);
		}
	}
	
	/**
	 * Checks if this is a duplicate resource.
	 * @param resource
	 * @param resourceName
	 * @param duplicateFileName
	 * @return
	 */
	protected boolean isDuplicateBEResource(IResource resource,
			                                String resourceName, 
			                                StringBuilder duplicateFileName) {
		return StudioResourceUtils.isDuplicateBEResource(resource, resourceName, duplicateFileName);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			resourceContainer.setFocus();
		}
	}

	public String getResourceExtension() {
		return resourceExtension;
	}

	public void setResourceExtension(String resourceExtension) {
		this.resourceExtension = resourceExtension;
	}

	public StudioResourceContainer getResourceContainer() {
		return resourceContainer;
	}

	public IFile getFile() {
		return newFile;
	}
}