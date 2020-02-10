package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.ReturnTypeSelector;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class RuleFunctionFileCreationWizard extends EntityFileCreationWizard {

	private Button virtualButton;

	@SuppressWarnings("unused")
	private IStructuredSelection _selection;

	private PropertyTypeCombo returnTypeText;

	private String returnType;

	private boolean arrayReturnType;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */

	public RuleFunctionFileCreationWizard(String pageName,IStructuredSelection selection, String type, String currentProjectName) {
		super(pageName, selection, type, currentProjectName);
		_selection = selection;
	}

	@Override
	public void createControl(Composite parent) {

		super.createControl(parent);
		Composite controls = (Composite) super.getControl();
		
		Composite retTypeComposite = new Composite(controls, SWT.NULL);
		GridLayout retTypeGL = new GridLayout(2, false);
		retTypeComposite.setLayout(retTypeGL);
		GridData retTypeGD = new GridData(GridData.FILL_HORIZONTAL);
		retTypeGD.horizontalSpan = 2;
		retTypeComposite.setLayoutData(retTypeGD);
		retTypeGL.marginWidth = 0;
		retTypeGL.marginHeight = 0;
		retTypeGL.marginTop = 0;
		retTypeGL.marginBottom = 0;
		
		Label retTypeLabel = new Label(retTypeComposite, SWT.NULL);
		retTypeLabel.setText("Return type:");
		GridData labelData = new GridData();
		labelData.horizontalSpan = 2;
		retTypeLabel.setLayoutData(labelData);
		
		
		returnTypeText = new PropertyTypeCombo(retTypeComposite, SWT.BORDER | SWT.NONE, false, true);
		returnTypeText.setImage(StudioUIPlugin.getDefault().getImage("icons/no_type.png"));
		returnTypeText.setText("void");
		returnTypeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Button retTypeBrowseButton = new Button(retTypeComposite, SWT.NULL);
		retTypeBrowseButton.setText("Browse...");
		retTypeBrowseButton.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ReturnTypeSelector returnTypeSelector = new ReturnTypeSelector(getShell(),
							getProject().getName(),
							returnTypeText.getText(),
							getPropertyReturnPropertyType(),
							arrayReturnType);
					if (returnTypeSelector.open() == Dialog.OK) {
						StudioUIUtils.setPopertyField(returnTypeText, returnTypeSelector.getPropertyType(), returnTypeSelector.getValue(), returnTypeSelector.isArray());
						arrayReturnType = returnTypeSelector.isArray();
						setPropertyReturnType(returnTypeSelector.getPropertyType());
					}
				} catch(Exception e1){
					e1.printStackTrace();
				}
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		virtualButton = new Button(controls,SWT.CHECK | SWT.RIGHT_TO_LEFT);
		virtualButton.setText(Messages.getString("new.rulefunction.wizard.isVirtual"));
		virtualButton.setSelection(false);
		virtualButton.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				NewRuleFunctionWizard wizard = (NewRuleFunctionWizard)getWizard();
				if(virtualButton.getSelection()){
					wizard.setWindowTitle(Messages.getString("new.vrulefunction.wizard.title"));
					setDescription(Messages.getString("new.vrulefunction.wizard.desc"));
					setTitle(Messages.getString("new.vrulefunction.wizard.title"));
					setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/wizard/vrulefunctionWizard.png"));
					returnTypeText.setImage(StudioUIPlugin.getDefault().getImage("icons/no_type.png"));
					returnTypeText.setText("void");
					retTypeBrowseButton.setEnabled(false);
				}else{
					wizard.setWindowTitle(Messages.getString("new.rulefunction.wizard.title"));
					setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/wizard/rulefunctionWizard.png"));
					setDescription(Messages.getString("new.rulefunction.wizard.desc"));
					setTitle(Messages.getString("new.rulefunction.wizard.title"));
					retTypeBrowseButton.setEnabled(true);
				}
			}});
		
		setControl(controls);
	}
	
	public void setPropertyReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getPropertyReturnPropertyType(){
		return returnType;
	}

	private String getReturnTypeAsString() {
		
		arrayReturnType = false;
		String text = returnTypeText.getText();
		
		if (text.endsWith("[]")) {
			arrayReturnType = true;
			text = text.substring(0, text.indexOf("[]"));
		}
		//setting the image for return type field
		Entity entity = IndexUtils.getEntity(getProject().getName(), text);
		if (entity != null) {
			if (entity instanceof Concept) {
				setPropertyReturnType("concept");
			} else if (entity instanceof Event) {
				setPropertyReturnType("event");
			}
		} else {
			IPath cpath = Path.fromOSString(text + ".concept");
			IPath epath = Path.fromOSString(text + ".event");
			if (getProject().getFile(cpath).exists()) {
				setPropertyReturnType("concept");
			} else if(getProject().getFile(epath).exists()) {
				setPropertyReturnType("event");
			} else {
				setPropertyReturnType(text);
			}
		}

		text =  ModelUtils.convertPathToPackage(text);
		
		if (arrayReturnType) {
			text = text + "[]";
		}
		return text;
	}

	public IProject getProject() {
		IProject proj = super.getProject();
		if (proj == null) {
			String projName = getContainerFullPath().segment(0);
			proj = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
		}
		return proj;
	}
	
	public Button getVirtualButton() {
		return virtualButton;
	}

	public String getReturnType() {
		return returnTypeText.getText();
	}
}
