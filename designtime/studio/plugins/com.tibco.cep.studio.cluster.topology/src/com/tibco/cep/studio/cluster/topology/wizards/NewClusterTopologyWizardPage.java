package com.tibco.cep.studio.cluster.topology.wizards;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

/**
 * @author ggrigore 
 */
public class NewClusterTopologyWizardPage extends EntityFileCreationWizard implements SelectionListener, ModifyListener{

	private Text cddText;
	private String projectName;
	private IProject project;
	private String cddPath;
	private IFile CDDFile;
	
	/**
	 * @param pageName
	 * @param selection
	 * @param projectName
	 * @param cddPath
	 * @param type
	 */
	public NewClusterTopologyWizardPage(String pageName,
			IStructuredSelection selection, 
			IProject project, 
			String type) {
		super(pageName, selection, type);
		this.project = project;
		if (project != null) {
			this.projectName = project.getName();
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createResourceContainer(container);
		
		createLabel(container, Messages.getString("wizard.desc"));
		_typeDesc = new Text(container, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		_typeDesc.setLayoutData(gd);
		
		createLabel(container, com.tibco.cep.studio.cluster.topology.utils.Messages.getString("new.deployment.wizard.cdd"));

		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));

		cddText = createText(childContainer);
		cddText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		cddText.addModifyListener(this);

		Button browseButton = new Button(childContainer, SWT.NONE);
		browseButton.setText(Messages.getString("Browse"));
		browseButton.addSelectionListener(this);
		
		setErrorMessage(null);
		setMessage(null);
		
		setControl(container);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		if(cddPath != null && !cddPath.trim().equals("")){
			IPath cddLocation = Path.fromOSString(cddPath);
			IPath projLocation = project.getLocation();
			if (cddLocation.isEmpty() || cddLocation.segmentCount() <= projLocation.segmentCount()) {
				setErrorMessage(com.tibco.cep.studio.cluster.topology.utils.Messages.getString("select.cdd.error"));
				setPageComplete(false);
				return false;
			} else {
				IPath location = Path.fromOSString(cddPath).removeFirstSegments(projLocation.segmentCount());
				IFile file = project.getFile(location);
				if(!file.exists() || !file.getFileExtension().equals("cdd")){
					setErrorMessage(com.tibco.cep.studio.cluster.topology.utils.Messages.getString("select.cdd.error"));
					setPageComplete(false);
					return false;
				}
				CDDFile = file;
			}
		}
		
		boolean valid = super.validatePage();
		if (valid) {
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}
		return valid;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(getContainerFullPath() !=  null){
			IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
			if(resource.exists()){
				if(resource instanceof IProject){
					project = (IProject)resource;
					projectName = resource.getName();
					
				}else{
					project = resource.getProject();
					projectName = resource.getProject().getName();
				}
			}
		}
		if(projectName == null) {
			return;
		}
		Set<String> extn = new HashSet<String>();
		extn.add("cdd");
	/*	ResourceSelector picker = new ResourceSelector(
				Display.getDefault().getActiveShell(),
				"Select Cluster Deployoment Descriptor File",
				"Select CDD to be associated with", project, cddText.getText(),extn);*/
		ObjectCacheCDDResourceSelector picker = new ObjectCacheCDDResourceSelector(Display
				.getDefault().getActiveShell(), "Select CDD",
				"Select CDD to be associated with",
				project, cddText.getText(), extn);
		if (picker.open() == Dialog.OK) {
			if (picker.getFirstResult() != null) {
				//CDDFile = picker.getSelectResource();
				cddText.setText(picker.getFirstResult().toString());
			}
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		if(cddText.getText() !=null && !cddText.getText().trim().equals("")){
			cddPath = cddText.getText();
			validatePage();
		} else {
			
		}
	}
	
	public IFile getCDDFile(){
		return CDDFile;
	}
}