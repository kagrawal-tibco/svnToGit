package com.tibco.cep.studio.dashboard.ui.forms;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class SimplePropertyForm extends BaseForm {

	protected Map<String, PropertyControl> propertyControls;

	protected boolean compact;

	public SimplePropertyForm(String title, FormToolkit formToolKit, Composite parent, boolean showGroup) {
		this(title, formToolKit, parent, showGroup, false);
	}

	public SimplePropertyForm(String title, FormToolkit formToolKit, Composite parent, boolean showGroup, boolean compact) {
		super(title, formToolKit, parent, showGroup);
		propertyControls = new LinkedHashMap<String, PropertyControl>();
		this.compact = compact;
	}

	public PropertyControl addProperty(String displayName, SynProperty property) {
		PropertyControl control = PropertyControlFactory.getInstance().createPropertyControl(this, displayName, property);
		propertyControls.put(property.getName(), control);
		return control;
	}

	public PropertyControl addProperty(PropertyControl control) {
		propertyControls.put(control.getProperty().getName(), control);
		return control;
	}

	public PropertyControl addPropertyAsText(String displayName, SynProperty property, boolean multiLine) {
		PropertyControl control = PropertyControlFactory.getInstance().createTextPropertyControl(this, displayName, property, multiLine);
		propertyControls.put(property.getName(), control);
		return control;
	}

	public PropertyControl addPropertyAsCombo(String displayName, SynProperty property) {
		PropertyControl control = PropertyControlFactory.getInstance().createComboPropertyControl(this, displayName, property);
		propertyControls.put(property.getName(), control);
		return control;
	}

	public PropertyControl addPropertyAsSpinner(String displayName, SynProperty property) {
		PropertyControl control = PropertyControlFactory.getInstance().createSpinnerPropertyControl(this, displayName, property);
		propertyControls.put(property.getName(), control);
		return control;
	}

	// public void addPropertyAsCheckBox(SynProperty property){
	// PropertyControl control = PropertyControlFactory.getInstance().getCheckBoxPropertyControl(this, property);
	// propertyControls.put(property.getName(), control);
	// }

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2, false));

		for (Map.Entry<String, PropertyControl> entry : propertyControls.entrySet()) {
			PropertyControl propertyControl = entry.getValue();
			boolean controlNeedsHeight = (propertyControl instanceof TextPropertyControl && ((TextPropertyControl)propertyControl).isMultiLine());
			boolean controlDoesNotNeedLabel = (propertyControl instanceof CheckBoxPropertyControl && ((CheckBoxPropertyControl)propertyControl).isShowDisplayName());

			if (controlDoesNotNeedLabel == false) {
				String displayName = propertyControl.displayName;
				if (displayName == null){
					displayName = entry.getKey();
				}
				Label label = createLabel(formComposite, displayName+":", SWT.NONE);
				GridData layoutData = new GridData(SWT.BEGINNING, controlNeedsHeight == true ? SWT.TOP : SWT.CENTER, false, false);
				if (compact == false) {
					layoutData.widthHint = 125;
				}
				label.setLayoutData(layoutData);

				Control control = propertyControl.createControl(formComposite);
				if (controlNeedsHeight) {
					layoutData = new GridData(SWT.FILL, SWT.TOP, true, false);
					layoutData.heightHint = 100;
					control.setLayoutData(layoutData);
				}
				else {
					control.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
				}
			}
			else {
				Control control = propertyControl.createControl(formComposite);
				control.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
			}
		}

	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		for (PropertyControl propertyControl : propertyControls.values()) {
			propertyControl.setLocalElement(newLocalElement);
		}
	}

	@Override
	protected void doDisableListeners() {
		for (PropertyControl propertyControl : propertyControls.values()) {
			propertyControl.dontListen();
		}
	}

	@Override
	protected void doEnableListeners() {
		for (PropertyControl propertyControl : propertyControls.values()) {
			propertyControl.listen();
		}
	}

	@Override
	public void refreshEnumerations() {
		for (PropertyControl propertyControl : propertyControls.values()) {
			propertyControl.refreshEnumerations();
		}
	}

	@Override
	public void refreshSelections() {
		for (PropertyControl propertyControl : propertyControls.values()) {
			propertyControl.refreshSelection();
		}
	}

	@Override
	protected void addControl(Control control) {
		if (formToolKit != null){
			formToolKit.adapt(control, true, true);
		}
		super.addControl(control);
	}

}
