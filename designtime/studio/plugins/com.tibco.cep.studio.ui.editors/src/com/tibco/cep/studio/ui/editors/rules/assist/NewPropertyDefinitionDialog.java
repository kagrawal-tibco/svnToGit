package com.tibco.cep.studio.ui.editors.rules.assist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.ui.editors.concepts.ConceptPropertySelector;

public class NewPropertyDefinitionDialog extends Dialog {

	private static final String POLICY_CHANGES_ONLY = "Changes Only";
	private static final String POLICY_ALL_VALUES = "All Values";
	private static final int CHANGES_ONLY = 0;
	private static final int ALL_VALUES = 1;
	
	private Entity fOwnerEntity;
	private String fNewName;
	private Combo propertyTypeCombo;
	private Button multipleButton;
	private Combo policyCombo;
	private Text historyText;
	protected String currentPropertyType;
	private Text messageLabel;
	protected int currentSelection;
	private PropertyDefinition fPropertyDefinition;

	public NewPropertyDefinitionDialog(Shell parentShell, Entity ownerEntity, String newPropertyName) {
		super(parentShell);
		this.fOwnerEntity = ownerEntity;
		this.fNewName = newPropertyName;
		this.fPropertyDefinition = ElementFactory.eINSTANCE.createPropertyDefinition();
		fPropertyDefinition.setName(newPropertyName);
		fPropertyDefinition.setOwnerPath(ownerEntity.getFullPath());
		fPropertyDefinition.setOwnerProjectName(ownerEntity.getOwnerProjectName());
	}

	@Override
	protected Control createContents(Composite parent) {
		return super.createContents(parent);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("New Property Definition");
		Composite composite = (Composite) super.createDialogArea(parent);
		((GridLayout)composite.getLayout()).numColumns = 2;
		Label ownerEntityLabel = new Label(composite, SWT.NULL);
		ownerEntityLabel.setText("Owner Entity:");
		Text ownerEntityText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		ownerEntityText.setText(fOwnerEntity.getFullPath()+fOwnerEntity.getName());
		ownerEntityText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label propNameLabel = new Label(composite, SWT.NULL);
		propNameLabel.setText("Property Name:");
		Text propertyNameText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		propertyNameText.setText(fNewName);
		propertyNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Group propertyDetailGroup = new Group(composite, SWT.NULL);
		propertyDetailGroup.setText("Details");
		GridLayout layout = new GridLayout(2, true);
		propertyDetailGroup.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		propertyDetailGroup.setLayoutData(data);
		
		final Text propTypeLabel = new Text(propertyDetailGroup, SWT.READ_ONLY);
		propTypeLabel.setText("Property Type:");
		propTypeLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		propertyTypeCombo = new Combo(propertyDetailGroup, SWT.READ_ONLY);
		propertyTypeCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		PROPERTY_TYPES[] values = PROPERTY_TYPES.values();
		for (PROPERTY_TYPES type : values) {
			if ((type == PROPERTY_TYPES.CONCEPT
					|| type == PROPERTY_TYPES.CONCEPT_REFERENCE)
					&& fOwnerEntity instanceof Event) {
				continue;
			}
			propertyTypeCombo.add(type.getName());
		}
		propertyTypeCombo.select(0);
		propertyTypeCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				getButton(IDialogConstants.OK_ID).setEnabled(true);
				int previousSelection = currentSelection;
				currentSelection = propertyTypeCombo.getSelectionIndex();
				String item = propertyTypeCombo.getItem(propertyTypeCombo.getSelectionIndex());
				PROPERTY_TYPES currentType = PROPERTY_TYPES.get(item);
				fPropertyDefinition.setType(currentType);
				if (item.equals(PROPERTY_TYPES.CONCEPT.getName()) || item.equals(PROPERTY_TYPES.CONCEPT_REFERENCE.getName())) {
					List<PROPERTY_TYPES> filterType = new ArrayList<PROPERTY_TYPES>();
					filterType.add(currentType);
					ConceptPropertySelector selector = new ConceptPropertySelector(getShell(), fOwnerEntity.getOwnerProjectName(), fOwnerEntity.getFullPath(), PROPERTY_TYPES.STRING.getName(), PROPERTY_TYPES.STRING.getName(), filterType);
					if (selector.open() == Dialog.OK) {
						currentPropertyType = selector.getPropertyType();
						propertyTypeCombo.setText(selector.getPropertyType());
						propTypeLabel.setText("Property Type:*");
						propTypeLabel.redraw();
						messageLabel.setText("*"+selector.getValue());
						messageLabel.setVisible(true);
						fPropertyDefinition.setConceptTypePath(selector.getValue());
					} else {
						getButton(IDialogConstants.OK_ID).setEnabled(false);
						messageLabel.setText("Must specify a concept path");
						messageLabel.setVisible(true);
//						propertyTypeCombo.select(previousSelection);
					}
				} else {
					currentPropertyType = item;
					propTypeLabel.setText("Property Type:");
					messageLabel.setVisible(false);
					messageLabel.redraw();
					fPropertyDefinition.setConceptTypePath(null);
				}
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		if (fOwnerEntity instanceof Concept) {
			// add options for Multiple, Policy, and History
			Label policyLabel = new Label(propertyDetailGroup, SWT.NULL);
			policyLabel.setText("Policy");
			policyCombo = new Combo(propertyDetailGroup, SWT.READ_ONLY);
			policyCombo.add(POLICY_CHANGES_ONLY, CHANGES_ONLY);
			policyCombo.add(POLICY_ALL_VALUES, ALL_VALUES);
			policyCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			policyCombo.addSelectionListener(new SelectionListener() {
			
				@Override
				public void widgetSelected(SelectionEvent e) {
					fPropertyDefinition.setHistoryPolicy(policyCombo.getSelectionIndex());
				}
			
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			policyCombo.select(CHANGES_ONLY);
			
			Label historyLabel = new Label(propertyDetailGroup, SWT.NULL);
			historyLabel.setText("History");
			historyText = new Text(propertyDetailGroup, SWT.BORDER);
			historyText.setText("1");
			historyText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			historyText.addModifyListener(new ModifyListener() {
			
				@Override
				public void modifyText(ModifyEvent e) {
					try {
						int i = Integer.parseInt(historyText.getText());
						fPropertyDefinition.setHistorySize(i);
					} catch (NumberFormatException e2) {
						fPropertyDefinition.setHistorySize(1);
					}
				}
			}); 
			multipleButton = new Button(propertyDetailGroup, SWT.CHECK);
			multipleButton.setText("Multiple");
			GridData mbData = new GridData(GridData.FILL_HORIZONTAL);
			mbData.horizontalSpan = 2;
			multipleButton.setLayoutData(mbData);
			multipleButton.addSelectionListener(new SelectionListener() {
			
				@Override
				public void widgetSelected(SelectionEvent e) {
					fPropertyDefinition.setArray(multipleButton.getSelection());
				}
			
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
		messageLabel = new Text(composite, SWT.READ_ONLY);
		messageLabel.setVisible(false);
		GridData lData = new GridData(GridData.FILL_HORIZONTAL);
		lData.horizontalSpan = 2;
		messageLabel.setLayoutData(lData);
		
		return composite;
	}

	public PropertyDefinition getPropertyDefinition() {
		return fPropertyDefinition;
	}

	
	
}
