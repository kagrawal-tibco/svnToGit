/**
 * 
 */
package com.tibco.cep.studio.ui.wizards.export;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
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
import org.eclipse.ui.ide.dialogs.ResourceTreeAndListGroup;
//import org.eclipse.ui.internal.ide.dialogs.ResourceTreeAndListGroup;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author mgujrath
 *
 */
public class TestDataMultipleExportWizardPage extends WizardExportResourcesPage implements ModifyListener {
	
	private Text outputLocationText ;

	private ResourceTreeAndListGroup resourceGroup;
	
	public TestDataMultipleExportWizardPage(String pageName,
			IStructuredSelection selection) {
		super(pageName, selection);
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
	//	init(parent);
		setTitle(Messages.getString("testdata.export.page.title"));
		setMessage(Messages.getString("testdata.export.description"));
		getShell().setText("Export");
	}
	
	public void init(Composite parent){
		initializeDialogUnits(parent);

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        composite.setFont(parent.getFont());
        this.createTestDataResourcesGroup(composite);
        createButtonsGroup(composite);
        createDestinationGroup(composite);
        restoreResourceSpecificationWidgetValues(); // ie.- local
        restoreWidgetValues(); // ie.- subclass hook
        updateWidgetEnablements();
        setPageComplete(determinePageCompletion());
        setErrorMessage(null);	// should not initially have error message
        setControl(composite);
	}
	
	@Override
	protected void createDestinationGroup(final Composite parent) {
		// TODO Auto-generated method stub
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label label = new Label(composite, SWT.NULL);
		label.setText("Output location");
		outputLocationText = new Text(composite, SWT.BORDER);
		outputLocationText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		outputLocationText.addModifyListener(this);
		Button browseButton = new Button(composite, SWT.NULL);
		browseButton.setText("Browse...");
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
//					getDialogSettings().put(TESTDATA_EXPORT_OUTPUT_LOCATION, folderPath);
				}
			}			
		});
	
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
	
	protected List getSelectedResources() {
		Iterator resourcesToExportIterator = this
				.getSelectedResourcesIterator();
		List resourcesToExport = new ArrayList();
		while (resourcesToExportIterator.hasNext()) {
			resourcesToExport.add(resourcesToExportIterator.next());
		}
		return resourcesToExport;
	}

	/**
	 * @return the targetPath
	 */
	public String getTargetPath() {		
		return outputLocationText.getText();
	}
	
	protected Iterator getSelectedResourcesIterator() {
	    return this.resourceGroup.getAllCheckedListItems().iterator();
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
		boolean complete = super.determinePageCompletion();
		if (complete) {
			setPageComplete(complete);
			setMessage("Press 'Finish' to export test data.");
		}
		return complete;
	}
	
	protected void createTestDataResourcesGroup(Composite parent) {

        //create the input element, which has the root resource
        //as its only child
        List input = new ArrayList();
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
                .getProjects();
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].isOpen()) {
				input.add(projects[i]);
			}
        }
        this.resourceGroup = new ResourceTreeAndListGroup(parent, input,
                getResourceProvider(IResource.FOLDER | IResource.PROJECT),
                WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(),
                getResourceProvider(IResource.FILE), WorkbenchLabelProvider
                        .getDecoratingWorkbenchLabelProvider(), SWT.NONE,
                availableNumRows(parent)>50);
        
        
        ICheckStateListener listener = new ICheckStateListener() {
            public void checkStateChanged(CheckStateChangedEvent event) {
                updateWidgetEnablements();
            }
        };
        this.resourceGroup.addCheckStateListener(listener);
    }
	
	public static int availableNumRows(Composite pComp) {

        int fontHeight = (pComp.getFont().getFontData())[0].getHeight();
        int displayHeight = pComp.getDisplay().getClientArea().height;

        return displayHeight / fontHeight;
    }


	
	private ITreeContentProvider getResourceProvider(final int resourceType) {
		return new WorkbenchContentProvider() {
			public Object[] getChildren(Object o) {
				if (o instanceof IContainer) {
					IResource[] members = null;
					try {
						members = ((IContainer) o).members();
					} catch (CoreException e) {
						// just return an empty set of children
						return new Object[0];
					}

					// filter out the desired resource types
					ArrayList results = new ArrayList();
					for (int i = 0; i < members.length; i++) {
						// And the test bits with the resource types to see if
						// they are what we want
						/*
						 * if ((members[i].getType() & resourceType) > 0) {
						 * results.add(members[i]); }
						 */

						if (members[i].getType() == 1) {
							if (members[i].getFileExtension().equalsIgnoreCase(
									"concepttestdata")
									|| members[i].getFileExtension()
											.equalsIgnoreCase("eventtestdata")
									|| members[i].getFileExtension()
											.equalsIgnoreCase(
													"scorecardtestdata")) {
								results.add(members[i]);
							}
						}
						if (members[i].getType() == 2) {

							iterateFolder((IFolder) members[i], results);

						}

					}
					return results.toArray();
				}
				// input element case
				if (o instanceof ArrayList) {
					return ((ArrayList) o).toArray();
				}
				return new Object[0];
			}
		};
	}
    
	public void iterateFolder(IFolder folder, List results) {
		try {
			IResource members[] = folder.members();
			for (int i = 0; i < members.length; i++) {

				if (members[i].getType() == 1) {
					if (members[i].getFileExtension() != null) {
						if (members[i].getFileExtension().equalsIgnoreCase(
								"concepttestdata")
								|| members[i].getFileExtension()
										.equalsIgnoreCase("eventtestdata")
								|| members[i].getFileExtension()
										.equalsIgnoreCase("scorecardtestdata")) {
							results.add(members[i]);
						}
					}
				}
				if (members[i].getType() == 2) {
					iterateFolder((IFolder) members[i], results);
				}

			}
			return;
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyText(ModifyEvent e) {
		// TODO Auto-generated method stub
		determinePageCompletion();
	}
 
}
