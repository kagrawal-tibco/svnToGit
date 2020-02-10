package com.tibco.cep.studio.ui.refactoring;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.refactoring.EntityElementRefactoring;
import com.tibco.cep.studio.core.refactoring.EntityRenameProcessor;

public class RenameElementRefactoringPage extends UserInputWizardPage {

	private boolean modified = false;
	private String newName = null;

	protected RenameElementRefactoringPage(String name) {
		super(name);
	}

	@Override
	protected Refactoring getRefactoring() {
		return super.getRefactoring();
	}

	@Override
	public void createControl(Composite parent) {
		setTitle("Rename element");
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		Label label = new Label(composite, SWT.NULL);
		label.setText("New name:");
		final Text text = new Text(composite, SWT.BORDER);
		EntityElementRefactoring refactoring = (EntityElementRefactoring) getRefactoring();
		newName = ((EntityRenameProcessor) refactoring.getProcessor()).getNewName();
		if (newName == null) {
			newName = "NewName";
		}
		text.setText(newName);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if (newName.equals(text.getText())) {
					setModified(false);
				} else {
					setModified(true);
				}

				if (validateName(text.getText())) {
					setNewName(text.getText());
				}
			}
		});
		
		Label updateLabel = new Label(composite, SWT.NULL);
		updateLabel.setText("Update references");
		final Button updateCheckBox = new Button(composite, SWT.CHECK);
		updateCheckBox.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setUpdateReferences(updateCheckBox.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		updateCheckBox.setSelection(true);
		setControl(composite);
		setPageComplete(false); // user needs to change the name first
	}
	
	
	
	protected boolean validateName(String text) {
		if (text == null || text.length() == 0) {
			setErrorMessage("Please enter a new element name");
			setPageComplete(false);
			return false;
		}
		if (isNonEntityResourceRefactor()){
			String invalidCharRegex = "[^\\!\\[\\]\\*\\(\\)\\-`\\'\\\"~&%$#@:;?/.,+=]*";  // Bug fix - handling for BE-18915.
	    	boolean valid = Pattern.matches(invalidCharRegex, text);
	    	if (!valid) {
	    		setErrorMessage(com.tibco.cep.studio.core.util.Messages.getString("Resource_invalidFilename", text));
				setPageComplete(false);
				return false;	    	}
		}
		IResource resource = getResource();
		if(resource instanceof IProject){
			if(!EntityNameHelper.isValidBEProjectIdentifier(text)){
				setErrorMessage(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_invalidFilename", text));
				setPageComplete(false);
				return false;
			}
		}
		else if(!isNonEntityResourceRefactor() && !EntityNameHelper.isValidBEEntityIdentifier(text)){
			setErrorMessage(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_invalidFilename", text));
			setPageComplete(false);
			return false;
		}
		
		if (isDuplicateContainer(resource, text)) {
			if (resource instanceof IProject) {
				setErrorMessage(com.tibco.cep.studio.core.util.Messages.getString("BE_Project_FilenameExists", text));
				setPageComplete(false);
				return false;
			} else {
				setErrorMessage(com.tibco.cep.studio.core.util.Messages.getString("BE_Folder_FilenameExists", text));
				setPageComplete(false);
				return false;
			}
		}
		
		if (!validateInherits(text)) {
			return false; 
		} 
		
		if(isDuplicateFile(resource, text)){
			setErrorMessage(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_DupFilenameExists", text));
			setPageComplete(false);
			return false;
		}
		
		if(isDisplayModel()) {
			setErrorMessage(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_DisplayModelRename"));
			setPageComplete(false);
			return false;
		}
		
		setErrorMessage(null);
		setPageComplete(true);
		return true;
	}
	
	private boolean isDisplayModel() {
		IResource resource = getResource();
		if(resource instanceof IFile){
			if(CommonIndexUtils.DISPLAY_EXTENSION.equals(resource.getFileExtension())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param text
	 */
	protected boolean validateInherits(String text) {
		return true;
	}

	protected IResource getResource() {
		if(getRefactoring() instanceof EntityElementRefactoring){
			EntityElementRefactoring entityRenameRefactoring = (EntityElementRefactoring)getRefactoring();
			EntityRenameProcessor processor = (EntityRenameProcessor)entityRenameRefactoring.getProcessor();
			IResource resource = processor.getResource();
			return resource;
		}
		return null;
	}
	
	private boolean isDuplicateContainer(IResource resource, String newName) {
		if(resource instanceof IContainer){
			IContainer container = (IContainer)resource;
			IContainer parent = container.getParent();
			if (parent instanceof IWorkspaceRoot) {
				IProject project = ((IWorkspaceRoot)parent).getProject(newName);
				if (project.exists()) {
					return true;
				}
			} else {
				IFolder folder = parent.getFolder(new Path(newName));
				if (folder.exists()) {
					return true;
				}
			}
		}

		return false;
	}
	
	private boolean isDuplicateFile(IResource resource, String newName) {
		if(resource instanceof IFile){
			IFile file = resource.getParent().getFile(new Path(newName + "." + resource.getFileExtension()));
			if(file.exists()){
				return true;
			}
		}
		return false;
		
	}

	private void setNewName(String name) {
		EntityElementRefactoring refactoring = (EntityElementRefactoring) getRefactoring();
		((EntityRenameProcessor) refactoring.getProcessor()).setNewName(name);
	}
	
	private void setUpdateReferences(boolean update) {
		EntityElementRefactoring refactoring = (EntityElementRefactoring) getRefactoring();
		((EntityRenameProcessor) refactoring.getProcessor()).setUpdateReferences(update);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#performFinish()
	 */
	protected boolean performFinish() {
		IResource resource = getResource();
		boolean performFinish = super.performFinish();
		boolean success = true;
		if (performFinish && resource instanceof IProject) {
			try {
				IProject oldProject = (IProject) resource;
				String oldName = oldProject.getName();
				IWorkspace workSpace = ResourcesPlugin.getWorkspace();
				EntityElementRefactoring refactoring = (EntityElementRefactoring) getRefactoring();
				String newName = ((EntityRenameProcessor) refactoring.getProcessor()).getNewName();
				IProject newproject = workSpace.getRoot().getProject(newName);
				String projectPath = newproject.getLocation().toString();

				//Creating new ones
				File newconceptviewFile = new File(projectPath + "/" + newName + ".conceptview");
				File neweventviewfile = new File(projectPath + "/" + newName + ".eventview");
				File newprojviewfile = new File(projectPath + "/" + newName + ".projectview");
				if (!newconceptviewFile.exists()) {
					newconceptviewFile.createNewFile();
				}
				if (!neweventviewfile.exists()) {
					neweventviewfile.createNewFile();
				}
				if (!newprojviewfile.exists()) {
					newprojviewfile.createNewFile();
				}
				
				//Removing the old files
				IFile oldconceptviewFile = newproject.getFile(oldName + ".conceptview");
				IFile oldeventviewFile =   newproject.getFile(oldName + ".eventview");
				IFile oldprojviewFile = newproject.getFile(oldName + ".projectview");
				if (oldconceptviewFile.exists()) {
					oldconceptviewFile.delete(true, null);
				}
				if (oldeventviewFile.exists()) {
					oldeventviewFile.delete(true, null);
				}
				if (oldprojviewFile.exists()) {
					oldprojviewFile.delete(true, null);
				}
				
				//Refreshing the Project
				newproject.refreshLocal(IProject.DEPTH_INFINITE, null);
				
			} catch (IOException e) {
				e.printStackTrace();
				success = false;
			} catch (CoreException e) {
				e.printStackTrace();
				success = false;
			}
		}
		return success;
	}
	
	protected boolean isNonEntityResourceRefactor() {
		Set<String> extn = new HashSet<String>();
		extn.add("sharedhttp");
		extn.add("id");
		extn.add("sharedjdbc");
		extn.add("sharedjmsapp");
		extn.add("sharedjmscon");
		extn.add("sharedjndiconfig");
		extn.add("sharedrsp");
		extn.add("sharedtcp");
		extn.add("rvtransport");
		extn.add("cdd");
		extn.add("testdata");
		extn.add("ear");
		IResource resource = getResource();
		if(resource instanceof IFile){
			IFile file = (IFile)resource;
			if(extn.contains(file.getFileExtension())){
				return true;
			}
		}
		return false;
	}
	
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean getModified() {
		return modified;
	}
}
