package com.tibco.cep.studio.ui.statemachine.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineFileCreationWizardPage extends EntityFileCreationWizard implements SelectionListener, ModifyListener{

	private Text ownerConceptText;
	private String projectName;
	private String ownerConceptPath;
	
	/**
	 * @param pageName
	 * @param selection
	 * @param projectName
	 * @param ownerConceptPath
	 * @param type
	 */
	public StateMachineFileCreationWizardPage(String pageName,
											  IStructuredSelection selection, 
											  String projectName, 
											  String ownerConceptPath, 
											  String type) {
		super(pageName, selection, type);
		this.projectName = projectName;
		this.ownerConceptPath = ownerConceptPath;
	}

	@Override
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createLabel(container, Messages.getString("new.statemachine.wizard.ownerConcept"));
		
		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		ownerConceptText = createText(childContainer);
		ownerConceptText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		ownerConceptText.addModifyListener(this);
		
		Button browseButton = new Button(childContainer, SWT.NONE);
		browseButton.setText(Messages.getString("Browse"));
		browseButton.addSelectionListener(this);
		createResourceContainer(container);
		createLabel(container, Messages.getString("wizard.desc"));
		_typeDesc = new Text(container, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		_typeDesc.setLayoutData(gd);
		
		if(ownerConceptPath != null){
			ownerConceptText.setText(ownerConceptPath);
		}
		
		setErrorMessage(null);
		setMessage(null);
		setControl(container);
		
//		StudioWorkbenchUtils.isStandaloneDecisionManger(this, _type);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		String conceptPath = ownerConceptText.getText();
		Concept concept = IndexUtils.getConcept(projectName, conceptPath);
		if(projectName == null && super.validatePage()) {
			setErrorMessage(Messages.getString("No_folder_specified"));
			setPageComplete(false);
			return false;
		}
		if (concept == null && super.validatePage()) {
			setErrorMessage(Messages.getString("new.statemachine.wizard.ownerConcept.Error"));
			setPageComplete(false);
			return false;
		}
		if (concept != null && super.validatePage()) {
			setErrorMessage(null);
			setMessage(null);
			setPageComplete(true);
			return true;
		}
		return super.validatePage();
	}
	
	public String getOwnerConcept(){
		return ownerConceptText.getText();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(projectName ==  null){
			if(getContainerFullPath() != null){
				IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
				if(resource.exists()){
					if(resource instanceof IProject){
						projectName = resource.getName();
					}else{
						projectName = resource.getProject().getName();
					}
				}
			}
		}
		if(projectName == null) return;
		OwnerConceptSelector picker = new OwnerConceptSelector(getShell(),
				                                               projectName, ownerConceptText.getText().trim());
		if (picker.open() == Dialog.OK) {
			if(picker.getFirstResult() instanceof Concept){
				Concept concept = (Concept) picker.getFirstResult();
				ownerConceptText.setText(concept.getFullPath());
			}
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		validatePage();
	}
}
