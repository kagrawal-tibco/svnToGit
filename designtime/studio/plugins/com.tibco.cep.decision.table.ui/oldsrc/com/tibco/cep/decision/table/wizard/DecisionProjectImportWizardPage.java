package com.tibco.cep.decision.table.wizard;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.WizardImportProjectCreationPage;

/**
 * @author aathalye
 * @author sasahoo
 */
public class DecisionProjectImportWizardPage extends WizardImportProjectCreationPage {
	
	private static final String[] DP_EXTENSIONS = new String[] { "*.dp" } ; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	protected Text editor;
	
	/**
	 * The designer project selected if any selected
	 */
	private IStructuredSelection _selection;

	public DecisionProjectImportWizardPage(String pageName, IStructuredSelection _selection) {
		super(pageName,false);
		this._selection = _selection;
		setTitle(pageName); //NON-NLS-1
		setDescription(Messages.getString("DecisionProjectImportWizardPage.Description")); //NON-NLS-1 //$NON-NLS-1$
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Group group = new Group(composite, SWT.NULL);
		group.setText(Messages.getString("DecisionProjectImportWizardPage.Info.3.0")); //$NON-NLS-1$
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createAdvancedControls(group);
		
		Group targetGroup = new Group(composite, SWT.NULL);
		targetGroup.setText(Messages.getString("DesignerProjectImportWizardPage.Info.4.0")); //$NON-NLS-1$
		targetGroup.setLayout(new GridLayout());
		targetGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		super.createControl(targetGroup);
		projectNameField.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});
		fillProjectsCombo();
		projectNameField.setEditable(false);
		
		setControl(composite);
	}
	
	private void fillProjectsCombo() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		for (IProject project : projects) {
			projectComboField.add(project.getName());
		}
		if (_selection != null) {
			Object o = _selection.getFirstElement();
			if (o instanceof IProject) {
				IProject project = (IProject)o;
				projectComboField.setText(project.getName());
			}
		}
	}

	protected Composite createAdvancedControls(Composite parent) {
		Composite dirSelectionArea = new Composite(parent, SWT.NONE);
		GridData dirSelectionData = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL);
		dirSelectionArea.setLayoutData(dirSelectionData);

		GridLayout dirSelectionLayout = new GridLayout();
		dirSelectionLayout.numColumns = 3;
		dirSelectionLayout.makeColumnsEqualWidth = false;
		dirSelectionLayout.marginWidth = 0;
		dirSelectionLayout.marginHeight = 0;
		dirSelectionArea.setLayout(dirSelectionLayout);

		new Label(dirSelectionArea,SWT.NONE).setText(Messages.getString("DecisionProjectImportWizardPage.Info.projectLocation"));

		editor = new Text(dirSelectionArea,SWT.BORDER);
		editor.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
		editor.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				boolean complete = validatePage();
				setPageComplete(complete);
			}
		});
		Button browse = new Button(dirSelectionArea,SWT.NONE); 
		browse.setText(Messages.getString("Browse"));
		browse.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				try{
					FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
					fd.setText(Messages.getString("select.decision.project"));
					fd.setFilterExtensions(DP_EXTENSIONS);
					String selected = fd.open();
					if (selected != null) {
						File file = new File(selected);
						String fileName = file.getName();
						int dpIndex = file.getName().indexOf(".dp");
						String projectname = 
							(dpIndex != -1) ? fileName.substring(0, dpIndex) : fileName;
						projectNameField.setText(projectname);
						editor.setText(selected);
					}
				} catch(Exception ex ) {
					DecisionTableUIPlugin.log(ex);
				}
			}});
		return dirSelectionArea;
	}
	
    /*
     * see @DialogPage.setVisible(boolean)
     */
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
			editor.setFocus();
		}
    }
    
    /**
     * Full path of the Decision Project
     * @return decision project path
     */
    public String getDecisionProjectPath() {
		if (editor.getText() == null) {
			return null;
		}
		String fileName = editor.getText().trim();
		return fileName;
	}
    
    /**
     * TODO Add some validation on emptiness of a project
     * Get the selected designer project name
     * @return project name
     */
    public String getSelectedDesignerProjectName() {
		String projectName = projectComboField.getText();
		return projectName;
	}
	
	@Override
	protected boolean validatePage() {
		if (editor.getText() == null || editor.getText().trim().length() == 0) {
			setMessage(Messages.getString("DecisionProjectImportWizardPage.Error.dpLocation")); //$NON-NLS-1$
			setPageComplete(false);
			return false;
		}
		if (editor.getText().toLowerCase().endsWith(".dp")){
			setPageComplete(true);
			setErrorMessage(null);
		} else {
			setPageComplete(false);
			setErrorMessage(Messages.getString("Incorrect.decision.project.path"));
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validateLinkedResource()
	 */
	protected IStatus validateLinkedResource() {
		return new Status(IStatus.OK, "com.tibco.cep.studio.ui", IStatus.OK, "", null); //NON-NLS-1 //NON-NLS-2 //$NON-NLS-1$ //$NON-NLS-2$
	}
}