/**
 * 
 */
package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author mgujrath
 *
 */
public class NewTestDataWizardPage  extends StudioNewFileWizardPage implements ModifyListener,Listener{
	
	private Composite archiveDetails;
	private SashForm sashForm;
	private IStructuredSelection selection;
	private Text entityNameTextField;
	private Button browseButton;
	private String selectedEntityPath;
	

	public NewTestDataWizardPage(IStructuredSelection selection) {
	
		super("NewTestDataWizardPage", selection);
		this.selection=selection;
		setTitle(Messages.getString("new.testdata.wizard.title"));
        setDescription(Messages.getString("new.testdata.wizard.desc"));
    //    setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/cdd_48x48.gif"));
        
	}
	
	@Override
	public void setFileExtension(String value) {
	
		if (resourceContainer == null) {
			if(entityNameTextField.getText().contains(".concept")){
				fileExtension="concept"+value;
			}
			if(entityNameTextField.getText().contains(".event")){
				fileExtension="event"+value;
			}
			if(entityNameTextField.getText().contains(".scorecard")){
				fileExtension="scorecard"+value;
			}
		} else {
					
			if(entityNameTextField.getText().contains(".concept")){
				resourceContainer.setResourceExtension("concept"+value);
			}
			if(entityNameTextField.getText().contains(".event")){
				resourceContainer.setResourceExtension("event"+value);
			}
			if(entityNameTextField.getText().contains(".scorecard")){
				resourceContainer.setResourceExtension("scorecard"+value);
			}
		}
	}
	
	@Override
	public void createControl(final Composite parent) {
		setPageComplete(false);
		initializeDialogUnits(parent);
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1,false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setFont(parent.getFont());
		
		createLabel(container, "Entity:");
		
		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout1 = new GridLayout(2, false);
		layout1.marginWidth = 0;
		layout1.marginHeight = 0;
		childContainer.setLayout(layout1);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));

		entityNameTextField = createText(childContainer);
		entityNameTextField.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		entityNameTextField.addListener(SWT.Modify, this);
		entityNameTextField.setEditable(false);
		
		this.browseButton = new Button(childContainer, SWT.PUSH);
		browseButton.setText(Messages.getString("Browse"));
		browseButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL| GridData.VERTICAL_ALIGN_BEGINNING));
		browseButton.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				try{
					invlokeConceptSelector(parent);
					setFileExtension("testdata");
				}
				catch(Exception e2){
					e2.printStackTrace();
				}
			}
		});
		
		createResourceContainer(container);

		/*GridLayout layout = new GridLayout(1, false);
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.horizontalSpacing = 0;
		parent.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		// layoutData.heightHint = 500;

		parent.setLayoutData(layoutData);
		sashForm = new SashForm(parent, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		archiveDetails = new Composite(sashForm, SWT.NONE);
		archiveDetails.setLayout(new GridLayout(1, false));
		archiveDetails.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite archiveNameComposite = new Composite(archiveDetails, SWT.NONE);
		archiveNameComposite.setLayout(new GridLayout(2, false));
		archiveNameComposite.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		
		sashForm.setWeights(new int[] { 100 });
		setControl(sashForm);*/
		if(selection.getFirstElement() instanceof IFile){
			selectedEntityPath="/"+((IFile)selection.getFirstElement()).getFullPath().removeFirstSegments(1).toString();
			entityNameTextField.setText(selectedEntityPath);
		}
		else{
			entityNameTextField.setText("");
		}
		setFileExtension("testdata");
		validatePage();
		setControl(container);
	}
	
	@Override
	protected boolean validatePage() {
		
		setErrorMessage(null);
		if(resourceContainer.getResourceName().length() == 0){
			setErrorMessage(Messages.getString("Empty_Name"));
			setPageComplete(false);
			return false;
		}
		if(entityNameTextField.getText()==null || entityNameTextField.getText().isEmpty()){
			setErrorMessage("Entity name cannot be empty");
			setPageComplete(false);
			return false;
		}
		setPageComplete(true);
		return true;
	}
	
	private void invlokeConceptSelector(Composite parent){
		String projectName=null;
		if(selection.getFirstElement() instanceof IFile){
			projectName=((IFile)selection.getFirstElement()).getProject().getName();
		}
		else if(selection.getFirstElement() instanceof IProject){
			projectName=((IProject)selection.getFirstElement()).getName();
		}
		ExistingEntitySelectWizard picker = new ExistingEntitySelectWizard(parent.getShell(),
				projectName,
				"",
				"");
			if (picker.open() == Dialog.OK) {
				selectedEntityPath="/"+((IFile)picker.getFirstResult()).getFullPath().removeFirstSegments(1).toString();
				entityNameTextField.setText(selectedEntityPath);
			}
	}

	

	public String getSelectedEntityPath() {
		return selectedEntityPath;
	}


	@Override
	public void modifyText(ModifyEvent e) {
		validatePage();
	}

	public void handleEvent(Event event) {
		setPageComplete(validatePage());
	}


	
}
