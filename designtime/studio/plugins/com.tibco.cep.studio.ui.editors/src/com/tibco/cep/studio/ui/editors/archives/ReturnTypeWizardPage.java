package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.RuleFunctionFormEditor;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.components.ConceptSelector;
import com.tibco.cep.studio.ui.forms.components.EventSelector;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;

/**
 *
 * @author sasahoo
 *
 */
public class ReturnTypeWizardPage extends WizardPage {

	private Text typeText;
	public Text getTypeText() {
		return typeText;
	}

	public void setTypeText(Text typeText) {
		this.typeText = typeText;
	}

	private PropertyTypeCombo propertyCombo;
	private Group composite;
  
	private Label cLabel;
    private Button button;
    
    private ReturnTypeWizard wizard;
    
	protected ReturnTypeWizardPage(ReturnTypeWizard wizard) {
		super(Messages.getString("rulefunction.return.type.wizard.title"));
		setTitle(Messages.getString("rulefunction.return.type.wizard.title"));
		setDescription(Messages.getString("rulefunction.return.type.wizard.description"));
//		setImageDescriptor(EditorsUIPlugin.getImageDescriptor("icons/property_wizard.png"));
		this.wizard = wizard;
	}

	public void createControl(Composite parent) {

		setPageComplete(false);
		composite = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);

		Label propertyTextLabel = new Label(composite, SWT.NONE);
		propertyTextLabel.setText(Messages.getString("rulefunction.return.type.wizard.label"));
		
		propertyCombo = new PropertyTypeCombo(composite, SWT.BORDER | SWT.MULTI | SWT.DROP_DOWN, true);
		
		propertyCombo.getTextWidget().setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		propertyCombo.getContainer().setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		for(PROPERTY_TYPES property_types:PROPERTY_TYPES.values()){
			if(!property_types.equals(PROPERTY_TYPES.CONCEPT) && !property_types.equals(PROPERTY_TYPES.CONCEPT_REFERENCE))
				EditorUtils.createImage(property_types, propertyCombo);
		}
		propertyCombo.add(EditorsUIPlugin.getDefault().getImage("icons/concept.png"), "Concept");
		propertyCombo.add(EditorsUIPlugin.getDefault().getImage("icons/event.png"), "Event");
		propertyCombo.add(EditorsUIPlugin.getDefault().getImage("icons/no_type.png"), "void");
		propertyCombo.add(EditorsUIPlugin.getDefault().getImage("icons/no_type.png"), "Object");
		
		propertyCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if(propertyCombo.getText().equalsIgnoreCase("Concept")|| propertyCombo.getText().equalsIgnoreCase("Event")){
					cLabel.setVisible(true);
					cLabel.setText(propertyCombo.getText().equalsIgnoreCase("Concept")?Messages.getString("rulefunction.return.type.wizard.concept")
							:Messages.getString("rulefunction.return.type.wizard.event"));
					typeText.setVisible(true);
					typeText.setText("");
					button.setVisible(true);
				}else{
					cLabel.setVisible(false);
					typeText.setText("");
					typeText.setVisible(false);
					button.setVisible(false);
				}
				validatePage();
			}});
		
		GridData data_property = new GridData(GridData.FILL_HORIZONTAL);
		propertyCombo.setLayoutData(data_property);
		
		@SuppressWarnings("unused")
		Label emptyLabel = new Label(composite, SWT.NONE);
		
		cLabel = new Label(composite, SWT.NONE);
		cLabel.setLayoutData(new GridData(SWT.CENTER));
		cLabel.setText(Messages.getString("CONCEPT_PROPERTY_WIZARD_PAGE_PROPERTY_CONCEPT"));
		
		typeText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		typeText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		GridData data_dtext = new GridData(GridData.FILL_HORIZONTAL);
		typeText.setLayoutData(data_dtext);
		typeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});

		button = new Button(composite, SWT.NONE);
		button.setText(Messages.getString("BROWSE_BUTTON_TEXT"));
		button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				RuleFunctionFormEditor formeditor = (RuleFunctionFormEditor)window.getActivePage().getActiveEditor();
				IProject project = ((FileEditorInput)formeditor.getEditorInput()).getFile().getProject();
				if(propertyCombo.getText().equalsIgnoreCase("Concept")){
					ConceptSelector picker = new ConceptSelector(Display.getDefault().getActiveShell(),project.getName(),typeText.getText().trim());
					if (picker.open() == Dialog.OK) {
						if(picker.getFirstResult() instanceof Concept){
			                Concept selectedConcept = (Concept) picker.getFirstResult();
			                typeText.setText(selectedConcept.getFullPath());
						}
					}
				}else if(propertyCombo.getText().equalsIgnoreCase("Event")){
					
					EventSelector picker = new EventSelector(Display.getDefault().getActiveShell(),project.getName(),typeText.getText().trim(), true);
					if (picker.open() == Dialog.OK) {
						if(picker.getFirstResult() instanceof Event){
			                Event selectedEvent = (Event) picker.getFirstResult();
			                typeText.setText(selectedEvent.getFullPath());
						}
					}
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		init();
		validatePage();
		setControl(composite);
	}

	private void init(){
		String type = wizard.getReturnType();
		if(type.trim().equalsIgnoreCase("void")){
			propertyCombo.select(8);
		}else if(type.trim().equalsIgnoreCase("Object")){
			propertyCombo.select(9);
		}else if(type.trim().equalsIgnoreCase("event")){
			propertyCombo.select(7);
		}else if(type.trim().equalsIgnoreCase("concept")){
			propertyCombo.select(6);
		}else{
			String ret = wizard.getReturnType().trim();
//			 if(ret.equals("int")){
//				 ret = "Integer";
//	    	 }else{
//	    		 ret = Character.toUpperCase(ret.charAt(0)) + ret.substring(1);
//	    	 }
			PROPERTY_TYPES propType = PROPERTY_TYPES.get(ret);
			if (propType != null) {
				int index = propType.getValue();
				propertyCombo.select(index);
			}
		}
		if(type.trim().equalsIgnoreCase("concept")|| type.trim().equalsIgnoreCase("event")){
			cLabel.setVisible(true);
			typeText.setVisible(true);
			button.setVisible(true);
			typeText.setText(wizard.getReturnTypeText().getText());
			cLabel.setText(type.trim().equalsIgnoreCase("concept") ? Messages.getString("rulefunction.return.type.wizard.concept")
					:Messages.getString("rulefunction.return.type.wizard.event"));
		}
		else{
			cLabel.setVisible(false);
			typeText.setVisible(false);
			button.setVisible(false);
		}
	}
	
	private void validatePage() {
		if(typeText.isVisible()){
		String concept = typeText.getText();
			if ((concept != null && concept.trim().length() > 0)) {
					setMessage(Messages.getString("rulefunction.return.type.wizard.description"), NONE);
					setPageComplete(true);
			} else {
				setMessage(Messages.getString("rulefunction.return.type.wizard.description"), ERROR);
				setPageComplete(false);
			}
		}else{
			setMessage(Messages.getString("rulefunction.return.type.wizard.description"), NONE);
			setPageComplete(true);
		}
	}

	public PropertyTypeCombo getPropertyCombo() {
			return propertyCombo;
	}

	public void setPropertyCombo(PropertyTypeCombo propertyCombo) {
			this.propertyCombo = propertyCombo;
	}
	
}