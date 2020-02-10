package com.tibco.cep.studio.ui.wizards.export;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.DialogPage;
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;

import com.tibco.cep.studio.common.util.ModelNameUtil;
import com.tibco.cep.studio.core.dependency.DefaultDependencyCalculator;
import com.tibco.cep.studio.core.util.DependencyUtils;
import com.tibco.cep.studio.ui.util.Messages;

public class ProjectLibraryExportWizardPage extends WizardExportResourcesPage implements ModifyListener {	
	
	private Text outputLocationText ;
	
	private Text projLibNameText;
	
	private final String PROJ_LIB_EXTENSION = "projlib";
	
	private final static String PROJ_LIB_EXPORT_OUTPUT_LOCATION = "proj_lib_export_output_location"; 

	private List<IResource> fDependentResources = new ArrayList<IResource>();

	protected ProjectLibraryExportWizardPage(String pageName,
			IStructuredSelection selection) {
		super(pageName, selection);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		setTitle(Messages.getString("project.library.export.page.title"));
		setMessage(Messages.getString("project.library.export.description"));
		getShell().setText("Export");
	}

	@Override
	protected void createDestinationGroup(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label label = new Label(composite, SWT.NULL);
		label.setText("Output location");
		
		String loc = getDialogSettings().get(PROJ_LIB_EXPORT_OUTPUT_LOCATION); 
		
		outputLocationText = new Text(composite, SWT.BORDER);
		outputLocationText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		outputLocationText.setText(loc == null ? "" : loc);
		outputLocationText.addModifyListener(this);
		
		Button browseButton = new Button(composite, SWT.NULL);
		browseButton.setText("Browse...");
		
		label = new Label(composite, SWT.NULL);
		label.setText("Name");
		projLibNameText = new Text(composite, SWT.BORDER);
		projLibNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		projLibNameText.addModifyListener(this);
		
		browseButton.addSelectionListener(new SelectionListener(){

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Brows Button is clicked				
				DirectoryDialog dirDialog = new DirectoryDialog(parent.getShell());		
				dirDialog.setFilterPath(outputLocationText.getText());
				String folderPath = dirDialog.open();
				if(folderPath != null && !folderPath.isEmpty()){
					outputLocationText.setText(folderPath);
					getDialogSettings().put(PROJ_LIB_EXPORT_OUTPUT_LOCATION, folderPath);
				}
			}			
		});
	}

	public List<IResource> getDependentResources() {
		fDependentResources.clear();
		DefaultDependencyCalculator.clear();
		collectDependentResources();
		return fDependentResources;
	}

	@Override
	public void handleEvent(Event event) {
		System.out.println("Event type" + event.type);
	}

	@Override
	protected void updateWidgetEnablements() {
		super.updateWidgetEnablements();
	}

	@SuppressWarnings("rawtypes")
	private void collectDependentResources() {
		// find dependent resources for each selected resource
		Iterator selectedResourcesIterator = getSelectedResourcesIterator();
		while (selectedResourcesIterator.hasNext()) {
			IResource selectedResource = (IResource) selectedResourcesIterator.next();
			collectDependentResources(selectedResource);
		}
	}

	private void collectDependentResources(IResource selectedResource) {
		List<IResource> dependentResources = DependencyUtils.getDependentResources(selectedResource);
		for (IResource resource : dependentResources) {
			if (resource == null) {
				continue;
			}
			if (!fDependentResources.contains(resource)) {
				fDependentResources.add(resource);
			}
		}
	}

	@Override
	protected boolean allowNewContainerName() {
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardExportResourcesPage#getSelectedResources()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IResource> getSelectedResources() {
		return super.getSelectedResources();
	}

	/**
	 * @return the targetPath
	 */
	public String getTargetPath() {		
		return outputLocationText.getText();
	}
	
	public String getProjLibName(){
		return projLibNameText.getText();
	}
	
	public String getOutputLocationText() {
		return outputLocationText.getText();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardDataTransferPage#determinePageCompletion()
	*/
	@SuppressWarnings("rawtypes")
	@Override
	protected boolean determinePageCompletion() {
		
		String message = null;
		
		// validate the resources
		List selectedResources = getSelectedResources();
		
		File outputLoc = new File(outputLocationText.getText());
		
		// check if extension is valid, if given
	//	boolean extValid = projLibNameText.getText().indexOf('.') == -1 || projLibNameText.getText().endsWith("." + PROJ_LIB_EXTENSION);
		

		
		if (selectedResources.size() == 0)	{
			message = Messages.getString("project.library.export.resource.error");
			setErrorMessage(message);
			setPageComplete(false);
			return false;
		}
		else if (outputLocationText.getText().equals(""))	{
			message = Messages.getString("project.library.export.output.location.null.error");
			setErrorMessage(message);
			setPageComplete(false);
			return false;
		}
		else if (!outputLoc.isDirectory())	{
			message = Messages.getString("project.library.export.output.location.invalid.error");
			setErrorMessage(message);
			setPageComplete(false);
			return false;
		}
		/*else if (projLibNameText.getText().equals(""))	{
			message = Messages.getString("project.library.export.name.error");
			setErrorMessage(message);
			setPageComplete(false);
			return false;
		} else if (projLibNameText.getText().length() > 0) {
			
			if(!ModelNameUtil.isValidSharedResourceIdentifier(projLibNameText.getText())){
				String problemMessage = Messages.getString("invalidFilename", projLibNameText.getText());
				setPageComplete(false);
				setErrorMessage(problemMessage);
				return false;
			} else if (!outputLocationText.getText().equals("") && 
//					!projLibNameText.getText().equals("") && 
					selectedResources.size() > 0 && 
					//isPageComplete &&
					extValid &&
					outputLoc.isDirectory()){
					setErrorMessage(null);
					File targetFile = new File(getTargetPath() + "/" +  getProjLibName() + "." + PROJ_LIB_EXTENSION);
					boolean depsize = getDependentResources().size() > 0 ;
					if (targetFile.exists()) {
						String str =  depsize ? "\n" + " "
								+ Messages.getString("project.library.export.complete.description") : "";
						setMessage(Messages.getString("project.library.export.duplicate.name") + str , DialogPage.WARNING);
					} else {
						String str = depsize ? Messages.getString("project.library.export.complete.description") : "";
						setMessage( str , Status.OK);
					}
					setPageComplete(true);
					return true;
				}
		}*/

		/*if (!extValid)	{
			message = Messages.getString("project.library.export.name.extension.error", PROJ_LIB_EXTENSION);
			setErrorMessage(message);
			setPageComplete(false);
			return false;
		} */
//		else if (!outputLocationText.getText().equals("") && 
//				!projLibNameText.getText().equals("") && 
//				selectedResources.size() > 0 && 
//				//isPageComplete &&
//				extValid &&
//				outputLoc.isDirectory()){
//				setErrorMessage(null);
//				setMessage(null, Status.OK);
//				setPageComplete(true);
//				return true;
//			}
		boolean validName = validateName();
		if (!validName) {
			setPageComplete(false);
			return false;
		}
		boolean complete = super.determinePageCompletion();
		if (complete) {
			setPageComplete(complete);
			File targetFile = new File(getTargetPath() + "/" +  getProjLibName() + "." + PROJ_LIB_EXTENSION);
			boolean depsize = getDependentResources().size() > 0 ;
			if (targetFile.exists()) {
				String str =  depsize ? Messages.getString("project.library.export.complete.dependency.description") : Messages.getString("project.library.export.complete.description");
						setMessage(Messages.getString("project.library.export.duplicate.name") + str , DialogPage.WARNING);
			} else {
				String str = depsize ? Messages.getString("project.library.export.complete.description") : "";
				setMessage( str , Status.OK);
			}
			setMessage(Messages.getString("project.library.export.complete.description"));
		}
		return complete;
	}
	
		
	protected void createOptionsGroup(Composite parent) {
		//Not needed
	}

	@Override
	public void modifyText(ModifyEvent e) {
		determinePageCompletion();
	}
	
	private boolean validateName(){
		boolean extValid = getProjLibName().indexOf('.') == -1 || getProjLibName().endsWith("." + PROJ_LIB_EXTENSION);
		if (!extValid)	{
			setErrorMessage(Messages.getString("project.library.export.name.extension.error", PROJ_LIB_EXTENSION));
			setPageComplete(false);
			return false;
		} 
		if (getProjLibName().equals(""))	{
			setErrorMessage((Messages.getString("project.library.export.name.error")));
			setPageComplete(false);
			return false;
		}
		if (getProjLibName().length() > 0) {
			if(!ModelNameUtil.isValidSharedResourceIdentifier(getProjLibName())){
				String problemMessage = Messages.getString("invalidFilename", getProjLibName());
				setPageComplete(false);
				setErrorMessage(problemMessage);
				return false;
			}
		}
		return true;

	}

	
}