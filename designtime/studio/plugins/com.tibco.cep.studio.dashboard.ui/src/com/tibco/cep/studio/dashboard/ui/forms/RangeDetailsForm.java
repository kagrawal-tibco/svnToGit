package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRangeAlert;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class RangeDetailsForm extends BaseForm {

	public static final String PROP_KEY_RANGE_NAME = "RangeName";

	private Button btn_Enabled;
	private AbstractSelectionListener btn_EnabledSelectionListener;

	private Text txt_RangeName;
	private ModifyListener txt_RangeNameModifyListener;

	private Button btn_LowerThreshold;
	private AbstractSelectionListener btn_LowerThresholdSelectionListener;
	
	private Label lbl_LowerThreshold;
	private MouseListener lbl_LowerThresholdMouseListener;

	private Text txt_LowerThreshold;
	private ModifyListener txt_LowerThresholdModifyListener;

	private Button btn_UpperThreshold;
	private AbstractSelectionListener btn_UpperThresholdSelectionListener;

	private Label lbl_UpperThreshold;
	private MouseListener lbl_UpperThresholdMouseListener;
	
	private Text txt_UpperThreshold;
	private ModifyListener txt_UpperThresholdModifyListener;

	private LocalRangeAlert localRangeAlert;

	public RangeDetailsForm(FormToolkit formToolKit, Composite parent) {
		super("Details", formToolKit, parent, true);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2, false));

		// enabled
		btn_Enabled = createButton(formComposite, "Enabled", SWT.CHECK);
		btn_Enabled.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		// range name
		createLabel(formComposite, "Name:", SWT.NONE);
		txt_RangeName = createText(formComposite, "", SWT.NONE);
		txt_RangeName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		// range lower threshold
		Composite lowerThresholdComposite = createComposite(formComposite, SWT.NONE);
		GridLayout lowerThresholdCompLayout = new GridLayout(3, false);
		lowerThresholdCompLayout.marginHeight = 0;
		lowerThresholdCompLayout.marginWidth = 0;
		lowerThresholdComposite.setLayout(lowerThresholdCompLayout);
		btn_LowerThreshold = createButton(lowerThresholdComposite, "", SWT.CHECK);
		lbl_LowerThreshold = createLabel(lowerThresholdComposite, "Lower Threshold", SWT.NONE);
		controls.add(lbl_LowerThreshold);
		txt_LowerThreshold = createText(lowerThresholdComposite, "", SWT.NONE);
		txt_LowerThreshold.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		lowerThresholdComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		// range upper threshold
		Composite upperThresholdComposite = createComposite(formComposite, SWT.NONE);
		GridLayout upperThresholdCompLayout = new GridLayout(3, false);
		upperThresholdCompLayout.marginHeight = 0;
		upperThresholdCompLayout.marginWidth = 0;
		upperThresholdComposite.setLayout(upperThresholdCompLayout);
		btn_UpperThreshold = createButton(upperThresholdComposite, "", SWT.CHECK);
		lbl_UpperThreshold = createLabel(upperThresholdComposite, "Upper Threshold", SWT.NONE);
		controls.add(lbl_UpperThreshold);
		txt_UpperThreshold = createText(upperThresholdComposite, "", SWT.NONE);
		txt_UpperThreshold.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		upperThresholdComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		txt_LowerThreshold.setEnabled(false);
		txt_UpperThreshold.setEnabled(false);
		
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		if (newLocalElement == null){
			localRangeAlert = new LocalRangeAlert();
			return;
		}
		if (newLocalElement instanceof LocalRangeAlert) {
			this.localRangeAlert = (LocalRangeAlert) newLocalElement;
		} else {
			throw new IllegalArgumentException(newLocalElement + " is not a range alert ");
		}
	}

	@Override
	protected void doEnableListeners() {
		if (btn_EnabledSelectionListener == null) {
			btn_EnabledSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					enableDisableRange();
				}

			};
		}
		btn_Enabled.addSelectionListener(btn_EnabledSelectionListener);

		if (txt_RangeNameModifyListener == null) {
			txt_RangeNameModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					rangeNameTextChanged();
				}

			};
		}

		txt_RangeName.addModifyListener(txt_RangeNameModifyListener);

		if (btn_LowerThresholdSelectionListener == null) {
			btn_LowerThresholdSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					enableDisableLowerThreshold();
				}

			};
		}
		btn_LowerThreshold.addSelectionListener(btn_LowerThresholdSelectionListener);
		
		if (lbl_LowerThresholdMouseListener == null) {
			lbl_LowerThresholdMouseListener = new MouseAdapter(){
				
				@Override
				public void mouseUp(MouseEvent e) {
					Rectangle bounds = ((Label)e.widget).getBounds();
					if (e.x > bounds.width || e.y > bounds.height) {
						return;
					}
					btn_LowerThreshold.setSelection(!btn_LowerThreshold.getSelection());
					enableDisableLowerThreshold();
				}
				
			};
		}
		lbl_LowerThreshold.addMouseListener(lbl_LowerThresholdMouseListener);

		if (txt_LowerThresholdModifyListener == null) {
			txt_LowerThresholdModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					lowerThresholdTextChanged();
				}

			};
		}

		txt_LowerThreshold.addModifyListener(txt_LowerThresholdModifyListener);
		
		if (lbl_UpperThresholdMouseListener == null) {
			lbl_UpperThresholdMouseListener = new MouseAdapter(){
				
				@Override
				public void mouseUp(MouseEvent e) {
					Rectangle bounds = ((Label)e.widget).getBounds();
					if (e.x > bounds.width || e.y > bounds.height) {
						return;
					}
					btn_UpperThreshold.setSelection(!btn_UpperThreshold.getSelection());
					enableDisableUpperThreshold();
				}
				
			};
		}
		lbl_UpperThreshold.addMouseListener(lbl_UpperThresholdMouseListener);

		if (btn_UpperThresholdSelectionListener == null) {
			btn_UpperThresholdSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					enableDisableUpperThreshold();
				}

			};
		}
		btn_UpperThreshold.addSelectionListener(btn_UpperThresholdSelectionListener);

		if (txt_UpperThresholdModifyListener == null) {
			txt_UpperThresholdModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					upperThresholdTextChanged();
				}

			};
		}

		txt_UpperThreshold.addModifyListener(txt_UpperThresholdModifyListener);
	}

	@Override
	protected void doDisableListeners() {
		btn_Enabled.removeSelectionListener(btn_EnabledSelectionListener);
		txt_RangeName.removeModifyListener(txt_RangeNameModifyListener);
		btn_LowerThreshold.removeSelectionListener(btn_LowerThresholdSelectionListener);
		lbl_LowerThreshold.removeMouseListener(lbl_LowerThresholdMouseListener);
		txt_LowerThreshold.removeModifyListener(txt_LowerThresholdModifyListener);
		btn_UpperThreshold.removeSelectionListener(btn_UpperThresholdSelectionListener);
		txt_UpperThreshold.removeModifyListener(txt_UpperThresholdModifyListener);
		lbl_UpperThreshold.removeMouseListener(lbl_UpperThresholdMouseListener);
	}

	@Override
	public void refreshEnumerations() {
	}

	@Override
	public void refreshSelections() {
		try {
			btn_Enabled.setSelection(Boolean.valueOf(localRangeAlert.getPropertyValue(LocalRangeAlert.PROP_KEY_ENABLED)));

			txt_RangeName.setText(localRangeAlert.getPropertyValue(LocalRangeAlert.PROP_KEY_NAME));

			String lowerThreshold = localRangeAlert.getPropertyValue(LocalRangeAlert.PROP_KEY_LOWER_VALUE);
			boolean hasLowerThreshold = !lowerThreshold.equals("");
			btn_LowerThreshold.setSelection(hasLowerThreshold);
			txt_LowerThreshold.setEnabled(hasLowerThreshold);
			txt_LowerThreshold.setText(lowerThreshold);

			String upperThreshold = localRangeAlert.getPropertyValue(LocalRangeAlert.PROP_KEY_UPPER_VALUE);
			boolean hasUpperThreshold = !upperThreshold.equals("");
			btn_UpperThreshold.setSelection(hasUpperThreshold);
			txt_UpperThreshold.setEnabled(hasUpperThreshold);
			txt_UpperThreshold.setText(upperThreshold);
			
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	protected void enableDisableRange() {
		try {
			boolean enabled = btn_Enabled.getSelection();
			localRangeAlert.setPropertyValue(LocalRangeAlert.PROP_KEY_ENABLED, Boolean.toString(enabled));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process range enable selection", e));
		}
	}

	protected void rangeNameTextChanged() {
		try {
			String oldName = localRangeAlert.getPropertyValue(LocalRangeAlert.PROP_KEY_NAME);
			String newName = txt_RangeName.getText();
			localRangeAlert.setPropertyValue(LocalRangeAlert.PROP_KEY_NAME, newName);
			propertyChangeSupport.firePropertyChange(PROP_KEY_RANGE_NAME, oldName, newName);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process range name change", e));
		}
	}

	protected void lowerThresholdTextChanged() {
		resetError(lbl_LowerThreshold);
		try {
			SynProperty property = (SynProperty) localRangeAlert.getProperty(LocalRangeAlert.PROP_KEY_LOWER_VALUE);
			if (property.isValid(txt_LowerThreshold.getText()) == false) {
				//we have a invalid value , do something
				indicateError(lbl_LowerThreshold, property.getValidationMessage().getMessage());
				return;
			}			
			localRangeAlert.setPropertyValue(LocalRangeAlert.PROP_KEY_LOWER_VALUE, txt_LowerThreshold.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process lower threshold change", e));
		}
	}

	protected void upperThresholdTextChanged() {
		resetError(lbl_UpperThreshold);
		try {
			SynProperty property = (SynProperty) localRangeAlert.getProperty(LocalRangeAlert.PROP_KEY_UPPER_VALUE);
			if (property.isValid(txt_UpperThreshold.getText()) == false) {
				//we have a invalid value , do something
				indicateError(lbl_UpperThreshold, property.getValidationMessage().getMessage());
				return;
			}
			localRangeAlert.setPropertyValue(LocalRangeAlert.PROP_KEY_UPPER_VALUE, txt_UpperThreshold.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process upper threshold change", e));
		}
	}

	protected void enableDisableLowerThreshold() {
		boolean selection = btn_LowerThreshold.getSelection();
		txt_LowerThreshold.setEnabled(selection);
		//reset the lower threshold label 
		resetError(lbl_LowerThreshold);
		txt_LowerThreshold.setText(selection == true ? "0" : "");
		lowerThresholdTextChanged();
	}

	protected void enableDisableUpperThreshold() {
		boolean selection = btn_UpperThreshold.getSelection();
		txt_UpperThreshold.setEnabled(selection);
		//reset the lower threshold label 
		resetError(lbl_UpperThreshold);			
		txt_UpperThreshold.setText(selection == true ? "0" : "");
		upperThresholdTextChanged();
	}
	
	private void indicateError(Control control, String tooltip) {
		control.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		control.setToolTipText(tooltip);
	}
	
	private void resetError(Control control) {
		control.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
		control.setToolTipText(null);
	}
	
}