package com.tibco.cep.studio.ui.navigator.actions;

import static com.tibco.cep.studio.common.util.EntityNameHelper.isValidBEEntityIdentifier;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.CopyProjectOperation;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.undo.CopyResourcesOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.part.ResourceTransfer;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.IStudioPasteParticipant;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.RefactoringUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;

public class StudioPasteAction extends SelectionListenerAction {

	private class RenameDialog extends MessageDialog {

		private String fNewName;
		private Label fMessageLabel;
		private IContainer fTarget;
		private IResource fsource;
		private IStudioPasteParticipant fParticipant;
		
		public RenameDialog(Shell parentShell, IResource source, String fileName, IContainer targetContainer, IStudioPasteParticipant participantExtension) {
			super(parentShell, "Name conflict", null, "Enter a new name for '"+fileName+"'",
					0, new String[] { "OK", "Cancel" }, 0);
			this.fTarget = targetContainer;
			this.fNewName = getUniqueName(fileName);
			this.fsource = source;
			this.fParticipant = participantExtension;
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout());
			composite.setLayoutData(new GridData(GridData.FILL_BOTH));
			final Text text = new Text(composite, SWT.BORDER);
			text.setText(fNewName);
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			text.addModifyListener(new ModifyListener() {
			
				@Override
				public void modifyText(ModifyEvent e) {
					getButton(0).setEnabled(true);
					fMessageLabel.setVisible(false);
					String name = text.getText();
					if (name == null || name.length() == 0) {
						getButton(0).setEnabled(false);
						fMessageLabel.setText("Names cannot be empty");
						fMessageLabel.setVisible(true);
						return;
					}
					int idx = name.lastIndexOf('.');
					String fileName = name;
					if (idx > 0) {
						fileName = fileName.substring(0, idx);
					}
					if(idx == 0){
						getButton(0).setEnabled(false);
			    		fMessageLabel.setText(fileName+" is not a valid identifier");
						fMessageLabel.setVisible(true);
						return;
					}
					if (!(fsource instanceof IContainer)) {
						String extn = name.substring(idx + 1);
						if(!extn.equals(fsource.getFileExtension())) {
							getButton(0).setEnabled(false);
							fMessageLabel.setText(fileName+" is not a valid resource");
							fMessageLabel.setVisible(true);
							return;
						}
					}
					if (fParticipant != null) {
						IStatus val = fParticipant.validateName(fsource, fTarget, fileName);
						if (val != null && !val.isOK()) {
				    		getButton(0).setEnabled(false);
				    		fMessageLabel.setText(val.getMessage());
							fMessageLabel.setVisible(true);
							return;
						}
					}
					if (fileExists(fileName)) {
						getButton(0).setEnabled(false);
						fMessageLabel.setText("Resource '"+fileName+"' already exists");
						fMessageLabel.setVisible(true);
						return;
					}
					getButton(0).setEnabled(true);
					fMessageLabel.setVisible(false);
					fNewName = name;
				}
			});
			fMessageLabel = new Label(composite, SWT.NULL);
			fMessageLabel.setVisible(false);
			fMessageLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
			return composite;
		}

		public String getUniqueName(String fileName) {
			int idx = fileName.lastIndexOf('.');
			String ext = "";
			if (idx > 0) {
				ext = fileName.substring(idx);
				fileName = fileName.substring(0, idx);
			}

			String prefix = "CopyOf";
			String newFileName = prefix + fileName;
			int counter = 1;
			while (fileExists(newFileName)) {
				newFileName = prefix + fileName + counter++;
			}
			return newFileName+ext;
		}

		protected boolean fileExists(String fileName) {
			if (fTarget == null) {
				return false;
			}
			try {
				IResource[] members = fTarget.members();
				for (int i=0; i<members.length; i++) {
					if (members[i] instanceof IFile
							&& members[i].getFullPath().removeFileExtension().lastSegment().equalsIgnoreCase(fileName)) {
						return true;
					} else if (members[i] instanceof IProject) {
						if (members[i].getName().equals(fileName)) {
							return true;
						}
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
			return false;
		}

		public String getNewName() {
			return fNewName;
		}
		
	}
	
	private Clipboard fClipboard;
	private Shell fShell;
	private boolean fIsProjectCopy;
	
	/**
	 * @param text
	 * @param clipboard
	 * @param shell
	 */
	public StudioPasteAction(String text, Clipboard clipboard, Shell shell) {
		super(text);
		this.fClipboard = clipboard;
		this.fShell = shell;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_PASTE);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		try {
			setProjectCopy(false);
			IProgressMonitor monitor = new NullProgressMonitor();
			Date start = new Date();
			Object destTransferContents = fClipboard.getContents(DestinationTransfer.getInstance());
			if (destTransferContents != null) {
				IResource target = getChannelTarget();
				if (target != null) {
					Channel targetChannel = (Channel)IndexUtils.getEntity(target.getProject().getName(), IndexUtils.getFullPath(target));
					Object[] contents = (Object[])destTransferContents;
					boolean destinationPasted = false;
					for (Object object:contents) {
						if (object instanceof Destination) {
							Destination destination = (Destination)object;
							String newName = getNewDestinationName(destination, targetChannel);
							if (newName != null) {
								Destination destinationCopy = (Destination)EcoreUtil.copy(destination);
								destinationCopy.setName(newName);
								destination.getDriverConfig().getDestinations().add(destinationCopy);
								destinationPasted = true;
							}
						}
					}
					if (destinationPasted) {
						ModelUtils.saveEObject(targetChannel);
						target.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					}
				}
			}
			final IContainer target = getPasteTarget();
			if (target == null) {
				return;
			}
			
			final Object contents = fClipboard.getContents(ResourceTransfer.getInstance());
			if (contents == null) {
				return; // nothing to paste
			}

			try {
				if (contents instanceof IProject) {
					pasteProject((IProject)contents, ResourcesPlugin.getWorkspace().getRoot());
				} else if (contents instanceof IResource) {
					pasteResources(monitor, new IResource[] { (IResource) contents }, target);
				} else if (contents instanceof List) {
					pasteResources(monitor, ((List) contents).toArray(), target);
				} else if (contents instanceof Object[]) {
					pasteResources(monitor, (Object[]) contents, target);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			target.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			Date end = new Date();
			System.out.println("done took "+(end.getTime() - start.getTime()));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void pasteProject(IProject project, IWorkspaceRoot target) {
		try {
			setProjectCopy(true);
			if (!CommonUtil.isStudioProject(project)) {
				 CopyProjectOperation operation = new CopyProjectOperation(
                         this.fShell);
                 operation.copyProject(project);
                 return;
			}
			String newName = getNewName(project, null);
			if (newName == null || newName.equals(project.getName())) {
				return;
			}
			IProject newProject = target.getProject(newName);
			PasteStudioProjectOperation op = new PasteStudioProjectOperation(newProject, project, target);
			ProgressMonitorDialog pmdialog = new ProgressMonitorDialog(this.fShell);				
			pmdialog.run(true, true, op);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class PasteStudioProjectOperation extends WorkspaceModifyOperation {
		
		private IProject fNewProject;
		private IProject fSourceProject;
		@SuppressWarnings("unused")
		private IWorkspaceRoot fTarget;

		public PasteStudioProjectOperation(IProject newProject, IProject sourceProject, IWorkspaceRoot root) {
			this.fNewProject = newProject;
			this.fSourceProject = sourceProject;
			this.fTarget = root;
		}

		@Override
		protected void execute(IProgressMonitor monitor) throws CoreException,
				InvocationTargetException, InterruptedException {
			setProjectCopy(true);
			checkCanceled(monitor);
			monitor.beginTask("Copying project "+fSourceProject.getName()+" - ", 2);
			monitor.setTaskName("Creating new project "+fNewProject.getName());
			createNewProject(fSourceProject, fNewProject, fNewProject.getName());
			checkCanceled(monitor);
			
			monitor.worked(1);
			IResource[] members = fSourceProject.members();
			try {
				monitor.setTaskName("Pasting project resources");
				int totalSize = getTotalSize(fSourceProject);
				SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
				subMon.beginTask("Copying resources"+fNewProject.getName(), totalSize);
				pasteResources(subMon, members, fNewProject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			monitor.worked(1);
		}

		
	}
	
	private void checkCanceled(IProgressMonitor monitor)
	throws InterruptedException {
		if (monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}
	
	private void setProjectCopy(boolean projectCopy) {
		this.fIsProjectCopy = projectCopy;
	}
	
	private boolean isProjectCopy() {
		return this.fIsProjectCopy;
	}

	/**
	 * @param monitor 
	 * @param monitor 
	 * @param contents
	 * @param target
	 */
	private void pasteResources(IProgressMonitor monitor, Object[] contents, IContainer target) throws Exception {
		checkCanceled(monitor);
		List<IResource> nonEntities = new ArrayList<IResource>();
		for (Object object : contents) {
			if (!(object instanceof IResource)) {
				continue;
			}
			IResource resource = (IResource) object;
			if (resource instanceof IContainer) {
				if (resource instanceof IProject) {
					target = ResourcesPlugin.getWorkspace().getRoot();
					pasteProject((IProject) resource, (IWorkspaceRoot) target);
					continue;
				}
				//Paste Folder
				IContainer container = pasteFolder(monitor, (IContainer)resource, target);
				if (container == null) {
					return;
				}
				IResource[] members;
				try {
					members = ((IContainer) resource).members();
					IPath path = Path.fromOSString("/"+ ((IContainer) resource).getLocation().lastSegment());
					if(!(target instanceof IWorkspaceRoot) && !target.getFolder(path).exists()){
						monitor.subTask("Pasting folder "+path);
						target.getFolder(path).create(true, true, null);
						monitor.worked(1);
					}
//					if (container == null) {
//						pasteResources(monitor, members, target.getFolder(path));
//					} else {
						pasteResources(monitor, members, container);
//					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}else{
				pasteResource(monitor, resource, target, nonEntities);
			}
		}
    	IResource[] nonEntityResources = new IResource[nonEntities.size()];
    	nonEntities.toArray(nonEntityResources);
		pasteNonEntities(monitor, nonEntityResources, target);
	}
	
    public int getTotalSize(IResource parent) {
    	int size = 1;
    	if (parent instanceof IContainer) {
    		try {
				IResource[] members = ((IContainer) parent).members();
				for (IResource resource : members) {
					size += getTotalSize(resource);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
    	}
    	return size;
	}

	/**
     * @param monitor 
     * @param resource
     * @param target
     * @param nonEntities
	 * @throws InterruptedException 
     */
    private void pasteResource(IProgressMonitor monitor, IResource resource, IContainer target, List<IResource> nonEntities) throws InterruptedException {
		checkCanceled(monitor);
    	monitor.subTask("Pasting "+resource.getName());
    	if (resource instanceof IFile) {
    		if (".project".equals(resource.getName())
//    				|| ".beproject".equals(resource.getName())
    				|| ".ars".equals(resource.getName())
    				|| ".svn".equals(resource.getName())) {
    			monitor.worked(1);
    			return; // do not paste project files
    		} else if ("conceptview".equals(resource.getFileExtension())
    				|| "eventview".equals(resource.getFileExtension())
    				|| "projectview".equals(resource.getFileExtension())) {
    			monitor.worked(1);
    			return; // do not paste project view files
    		}
    	}
    	IStudioPasteParticipant pasteParticipant = getSupportedParticipant(resource, target);
    	if (pasteParticipant != null) {
    		String newName = getNewResourceName(pasteParticipant, resource, target);
        	if (newName == null) {
    			monitor.worked(1);
        		return;
        	}
        	boolean overwrite = false;
        	if (!resource.getParent().equals(target)) {
        		IFile file = target.getFile(new Path(newName));
        		if (file.exists()) {
        			if (isProjectCopy()) {
        				//Don't prompt if it is a project copy
        				overwrite = true;
        			} else{
        				// prompt whether to overwrite
        				if (MessageDialog.openQuestion(fShell, "Resource exists", "Resource '"+file.getName()+"' already exists in this folder.  Do you want to overwrite?")) {
        					overwrite = true;
        				}
        			}
        		}
        	}
    		IStatus status = null;
			try {
				pasteParticipant.setProjectPaste(isProjectCopy());
				status = pasteParticipant.pasteElement(newName, resource, target, overwrite, monitor);
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
    		if (status != null && status.isOK()) {
    			monitor.worked(1);
    			return;
    		}
    	} else {
    		nonEntities.add(resource);
    	}
	}
    
    private IStudioPasteParticipant getSupportedParticipant(
			IResource resource, IContainer target) {
    	RefactoringParticipant[] refactoringParticipants = RefactoringUtils.getRefactoringParticipants();
    	for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			if (refactoringParticipant instanceof IStudioPasteParticipant) {
				if (((IStudioPasteParticipant) refactoringParticipant).isSupportedPaste(resource, target)) {
					return (IStudioPasteParticipant) refactoringParticipant;
				}
			}
		}
    	return null;
	}

	private String getNewResourceName(
			IStudioPasteParticipant pasteParticipant,
			IResource resource, IContainer target) {
		String newName = resource.getName();//pasteParticipant.getUniqueName(resource, target);
		if (resource.getParent().equals(target)) {
			newName = getNewName(resource, pasteParticipant);
		}
    	
		return newName;
	}

	/**
	 * @param source
	 * @return
	 */
	private String getNewName(IResource source, IStudioPasteParticipant participant) {
		RenameDialog dialog = new RenameDialog(fShell, source, source.getName(), source.getParent(), participant);
		int result = dialog.open();
		if (result != Status.OK) {
			return null;
		}
		String newName = dialog.getNewName();
		return newName;
	}
	
	/**
	 * @param monitor 
	 * @param source
	 * @param target
	 */
	private IContainer pasteFolder(IProgressMonitor monitor, IContainer source, IContainer target) throws Exception {
		checkCanceled(monitor);
		monitor.subTask("Pasting folder "+source.getName());
		IContainer container = null;
		String newName = null;
		if (source.equals(target)) {
			MessageDialog.openError(this.fShell, "Error", "Unable to paste folder '"+target.getName()+"'.  The source and destination are the same.");
			throw new InterruptedException();
		}
		if (source.getParent().equals(target)) {
			// pasting to the source directory, prompt for a new name
			newName = getNewName(source, null);
			if (newName == null || newName.equals(source.getName())) {
    			monitor.worked(1);
				return null;
			}
			if (source instanceof IFolder) {
				container = target.getFolder(new Path(newName));
			} else if (source instanceof IProject) {
				container = target.getWorkspace().getRoot().getProject(newName);
//				container = target.getFolder(new Path(newName));
			}
		} else {
			container = target.getFolder(new Path(source.getName()));
		}
		if (container.exists()) {
			// prompt for overwrite
		} else {
			if (container instanceof IFolder) {
				((IFolder)container).create(true, true, null);
			} else if (container instanceof IProject) {
				createNewProject((IProject) source, (IProject) container, newName);
			}
		}
		monitor.worked(1);
		return container;
	}

	private void createNewProject(IProject sourceProject, IProject newProject,
			String newName) throws CoreException {
		IProjectDescription desc = ((IProject)sourceProject).getDescription();
		desc.setName(newProject.getName());
		IPath location = sourceProject.getLocation();
		if (location != null && desc.getLocationURI() != null) {
			location = location.removeLastSegments(1);
			if (newName != null) {
				location = location.append(newName);
				desc.setLocationURI(location.toFile().toURI());
			}
		}
		((IProject) newProject).create(desc, new NullProgressMonitor());
		((IProject) newProject).open(new NullProgressMonitor());
	}

	
	/**
	 * @param monitor 
	 * @param resources
	 * @throws InterruptedException 
	 */
	private void pasteNonEntities(IProgressMonitor monitor, IResource[] resources, IContainer target) throws InterruptedException {
		checkCanceled(monitor);
        if (resources != null && resources.length > 0) {
            if (resources[0].getType() == IResource.PROJECT) {
                for (int i = 0; i < resources.length; i++) {
                    CopyProjectOperation operation = new CopyProjectOperation(
                            this.fShell);
                    operation.copyProject((IProject) resources[i]);
                }
            } else {
            	if (isProjectCopy()) {
            		// for project copies, we do not need to worry about duplicate resources/prompting for new resource names
            		CopyResourcesOperation op = new CopyResourcesOperation(resources, target.getFullPath(), "Copying to "+target.getName());
            		try {
            			PlatformUI.getWorkbench().getOperationSupport()
            			.getOperationHistory().execute(op, new NullProgressMonitor(),
            					WorkspaceUndoUtil.getUIInfoAdapter(this.fShell));
            		} catch (ExecutionException e) {
            			e.printStackTrace();
            		}
            	} else {
            		// for non-project copies, CopyFilesAndFoldersOperation will prompt the user for new resource names if they already exist
                	CopyFilesAndFoldersOperation op = new CopyFilesAndFoldersOperation(this.fShell);
                	op.copyResourcesInCurrentThread(resources, target, monitor);
            	}
            }
            return;
        }
	}
    
	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private IContainer getPasteTarget() {
        List selectedResources = getSelectedResources();
        IContainer target = null;
        for (int i = 0; i < selectedResources.size(); i++) {
            IResource resource = (IResource) selectedResources.get(i);

            if (resource instanceof IProject && !((IProject) resource).isOpen()) {
				return null;
			}
            if (resource.getType() == IResource.FILE) {
				resource = resource.getParent();
			}
            if (resource != null) {
				target = (IContainer) resource;
				break;
			}
        }
        return target;
    }
	
	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private IResource getChannelTarget() {
        List selectedResources = getSelectedResources();
        for (int i = 0; i < selectedResources.size(); i++) {
            IResource resource = (IResource) selectedResources.get(i);
            if (resource instanceof IProject && !((IProject) resource).isOpen()) {
				return null;
			}
            if (resource.getType() == IResource.FILE && ((IFile)resource).getFileExtension().equals("channel")) {
				return resource;
			}
        }
        List selectedNonResources = getSelectedNonResources();
        for (int i = 0; i < selectedNonResources.size(); i++) {
        	if (selectedNonResources.get(i) instanceof ChannelDestinationNode) {
        		ChannelDestinationNode destNode = (ChannelDestinationNode)selectedNonResources.get(i);
        		Destination destination = (Destination)destNode.getEntity();
        		Channel  channel = destination.getDriverConfig().getChannel();
        		IResource resource = IndexUtils.getFile(channel.getOwnerProjectName(), channel);
        		return resource;
        	}
        }
        return null;
    }
	
	/**
	 * @param destination
	 * @param targetChannel
	 * @return
	 */
	private String getNewDestinationName(Destination destination, Channel targetChannel) {
		RenameDestinationDialog dialog = new RenameDestinationDialog(fShell, destination, targetChannel);
		int result = dialog.open();
		if (result != Status.OK) {
			return null;
		}
		String newName = dialog.getNewName();
		return newName;
	}
	
	private class RenameDestinationDialog extends MessageDialog {

		private String fNewName;
		private Label fMessageLabel;
		private Channel fTarget;
		private DriverConfig driver;
		private Destination destination;
		private Text text;
		
		/**
		 * @param parentShell
		 * @param destination
		 * @param targetContainer
		 */
		public RenameDestinationDialog(Shell parentShell, Destination destination, Channel targetContainer) {
			super(parentShell, "Name conflict", null, "Enter a new name for '"+ destination.getName() + "'",
				  0, new String[] { "OK", "Cancel" }, 0);
			this.destination = destination;
			this.fTarget = targetContainer;
			this.driver = destination.getDriverConfig();
			this.fNewName = getUniqueName(destination.getName());
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		protected Control createCustomArea(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout());
			composite.setLayoutData(new GridData(GridData.FILL_BOTH));
			text = new Text(composite, SWT.BORDER);
			text.setText(fNewName);
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			text.addModifyListener(new ModifyListener() {
			
				@Override
				public void modifyText(ModifyEvent e) {
					getButton(0).setEnabled(true);
					fMessageLabel.setVisible(false);
					String name = text.getText();
					if (name == null || name.length() == 0) {
						getButton(0).setEnabled(false);
						fMessageLabel.setText("Names cannot be empty");
						fMessageLabel.setVisible(true);
						return;
					}
					if ( !isValidBEEntityIdentifier(name)) {
						getButton(0).setEnabled(false);
						fMessageLabel.setText(name + " is not a valid BE identifier");
						fMessageLabel.setVisible(true);
						return;
					}
					if (destinationExists(name)) {
						getButton(0).setEnabled(false);
						fMessageLabel.setText("File '" + name + "' already exists");
						fMessageLabel.setVisible(true);
						return;
					}
					getButton(0).setEnabled(true);
					fMessageLabel.setVisible(false);
					fNewName = name;
				}
			});
			fMessageLabel = new Label(composite, SWT.NULL);
			fMessageLabel.setVisible(false);
			fMessageLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
			return composite;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.IconAndMessageDialog#createContents(org.eclipse.swt.widgets.Composite)
		 */
		protected Control createContents(Composite parent) {
			Control composite = super.createContents(parent);
			//for invalid channel
			if (destination.getDriverConfig().getChannel() != fTarget) {
				getButton(0).setEnabled(false);
				fMessageLabel.setText("The Channel \"" + fTarget.getFullPath() + ".channel\" not valid.");
				fMessageLabel.setVisible(true);
				text.setEditable(false);
			}
			return composite;
		}

		/**
		 * @param name
		 * @return
		 */
		public String getUniqueName(String name) {
			String prefix = "CopyOf";
			String newFileName = prefix + name;
			int counter = 1;
			while (destinationExists(newFileName)) {
				newFileName = prefix + name + counter++;
			}
			return newFileName;
		}

		/**
		 * @param name
		 * @return
		 */
		protected boolean destinationExists(String name) {
			if (fTarget == null) {
				return false;
			}
			for (Destination destination :  driver.getDestinations()) {
				if (destination.getName().equals(name)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * @return
		 */
		public String getNewName() {
			return fNewName;
		}
	}
}