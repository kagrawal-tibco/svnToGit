package com.tibco.cep.studio.ui.forms.components;

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

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditorInput;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;

/**
 * 
 * @author sasahoo
 *
 */
public class PropertyDialogWizardPage extends WizardPage {

	private Text conceptText;
	private PropertyTypeCombo propertyCombo;
	private Group composite;
  
	private Label cLabel;
    private Button button;
    
    private PropertyDialogWizard wizard;
    
	protected PropertyDialogWizardPage(PropertyDialogWizard wizard) {
		super(Messages.getString("CONCEPT_PROPERTY_WIZARD_PAGE_TITLE"));
		setTitle(Messages.getString("CONCEPT_PROPERTY_WIZARD_PAGE_TITLE"));
		setDescription(Messages.getString("CONCEPT_PROPERTY_WIZARD_DESCRIPTION"));
		setImageDescriptor(EditorsUIPlugin.getImageDescriptor("icons/property_wizard.png"));
				
		this.wizard = wizard;
	}

	public void createControl(Composite parent) {

		setPageComplete(false);
		composite = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);

		Label propertyTextLabel = new Label(composite, SWT.NONE);
		propertyTextLabel.setText(Messages.getString("CONCEPT_PROPERTY_WIZARD_PAGE_PROPERTY_TEXT"));
		
		propertyCombo = new PropertyTypeCombo(composite, SWT.BORDER | SWT.MULTI | SWT.DROP_DOWN,true);
		
		propertyCombo.getTextWidget().setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		propertyCombo.getContainer().setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		for(PROPERTY_TYPES property_types:PROPERTY_TYPES.values()){
			EditorUtils.createImage(property_types, propertyCombo);
		}
		propertyCombo.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				
				if(propertyCombo.getText().equalsIgnoreCase(PROPERTY_TYPES.CONCEPT.getName())||
						propertyCombo.getText().equalsIgnoreCase(PROPERTY_TYPES.CONCEPT_REFERENCE.getName())){
					
					cLabel.setVisible(true);
					conceptText.setVisible(true);
					button.setVisible(true);
				
				}else{
					
					cLabel.setVisible(false);
					conceptText.setVisible(false);
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
		
		conceptText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		conceptText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		GridData data_dtext = new GridData(GridData.FILL_HORIZONTAL);
		conceptText.setLayoutData(data_dtext);
		conceptText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});

		button = new Button(composite, SWT.NONE);
		button.setText(Messages.getString("BROWSE_BUTTON_TEXT"));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				ConceptFormEditor formeditor = (ConceptFormEditor)window.getActivePage().getActiveEditor();
				Concept concept = ((ConceptFormEditorInput)formeditor.getEditorInput()).getConcept();
				ConceptSelector picker = new ConceptSelector( Display.getDefault().getActiveShell(),
						                                      formeditor.getProject().getName(),concept.getFullPath(), 
						                                      conceptText.getText(),
						                                      propertyCombo.getText().equalsIgnoreCase(PROPERTY_TYPES.CONCEPT_REFERENCE.getName()));
				if (picker.open() == Dialog.OK) {
					if(picker.getFirstResult() instanceof Concept){
		                Concept selectedConcept = (Concept) picker.getFirstResult();
		                conceptText.setText(selectedConcept.getFolder() + selectedConcept.getName());
					}
				}
			}
		});
		
		init();
		validatePage();
		setControl(composite);
	}

	private void init(){
		int index = PROPERTY_TYPES.get(wizard.getPropertyType()).getValue();
		propertyCombo.select(index);
		String type = wizard.getPropertyType();
		if(type.equalsIgnoreCase(PROPERTY_TYPES.CONCEPT.getName())||
				type.equalsIgnoreCase(PROPERTY_TYPES.CONCEPT_REFERENCE.getName())){
			
			cLabel.setVisible(true);
			conceptText.setVisible(true);
			button.setVisible(true);
			
			conceptText.setText(wizard.getValue());
		
		}else{
			
			cLabel.setVisible(false);
			conceptText.setVisible(false);
			button.setVisible(false);
			
		}
		
	}
	
	private void validatePage() {
		if(conceptText.isVisible()){
		String concept = conceptText.getText();
			if ((concept != null && concept.trim().length() > 0)) {
					setMessage(Messages.getString("CONCEPT_PROPERTY_WIZARD_MESSAGE"), NONE);
					setPageComplete(true);
			} else {
				setMessage(Messages.getString("CONCEPT_PROPERTY_WIZARD_ERROR"), ERROR);
				setPageComplete(false);
			}
		}else{
			setMessage(Messages.getString("CONCEPT_PROPERTY_WIZARD_MESSAGE"), NONE);
			setPageComplete(true);
		}
	}
	  public Text getConceptText() {
			return conceptText;
		}

	public void setConceptText(Text conceptText) {
			this.conceptText = conceptText;
	  }

	public PropertyTypeCombo getPropertyCombo() {
			return propertyCombo;
	}

	public void setPropertyCombo(PropertyTypeCombo propertyCombo) {
			this.propertyCombo = propertyCombo;
	}
	
}