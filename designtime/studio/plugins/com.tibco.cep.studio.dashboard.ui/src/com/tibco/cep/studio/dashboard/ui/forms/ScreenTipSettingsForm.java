package com.tibco.cep.studio.dashboard.ui.forms;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public abstract class ScreenTipSettingsForm extends BaseForm {

	private Combo cmb_Fields;
	private ComboViewer cmbViewer_Fields;

	private Button btn_Insert;
	private AbstractSelectionListener btn_InsertSelectionListener;

	private Text txt_ScreenTip;
	private ModifyListener txt_ScreenTipModifyListener;
	
//	private LocalSeriesConfig localSeriesConfig;
	private LocalActionRule localActionRule;
	private LocalDataSource localDataSource;

	public ScreenTipSettingsForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Screen Tip Settings", formToolKit, parent, showGroup);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(3, false));
		// measure label
		createLabel(formComposite, "Fields:", SWT.NONE);
		// measure drop down
		cmb_Fields = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Fields.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// insert button
		btn_Insert = createButton(formComposite, "Insert", SWT.PUSH);
		btn_Insert.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		// screen text
		txt_ScreenTip = createText(formComposite, "", SWT.WRAP);
		GridData txt_ScreenTipLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		//txt_ScreenTipLayoutData.heightHint = 50;
		txt_ScreenTip.setLayoutData(txt_ScreenTipLayoutData);

		cmbViewer_Fields = new ComboViewer(cmb_Fields);
		cmbViewer_Fields.setContentProvider(new ArrayContentProvider());
		cmbViewer_Fields.setLabelProvider(new LocalElementLabelProvider(false));
	}

	@Override
	public void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
//		if ((newLocalElement instanceof LocalSeriesConfig) == false) {
//			throw new IllegalArgumentException(newLocalElement + " is not acceptable");
//		}
//		localSeriesConfig = (LocalSeriesConfig) newLocalElement;
		localActionRule = null;
		localDataSource = null;
		if (newLocalElement != null) {
			localActionRule = (LocalActionRule) newLocalElement.getElement(BEViewsElementNames.ACTION_RULE);
			localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
		}
	}

	@Override
	protected void doEnableListeners() {
		if (btn_InsertSelectionListener == null) {
			btn_InsertSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// we only unhide widgets
					insertSelectedFieldInScreenTip();
				}

			};
		}
		btn_Insert.addSelectionListener(btn_InsertSelectionListener);

		if (txt_ScreenTipModifyListener == null) {
			txt_ScreenTipModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					screenTipTextChanged();

				}

			};
		}
		txt_ScreenTip.addModifyListener(txt_ScreenTipModifyListener);

	}



	@Override
	protected void doDisableListeners() {
		btn_Insert.removeSelectionListener(btn_InsertSelectionListener);
		txt_ScreenTip.removeModifyListener(txt_ScreenTipModifyListener);
	}

	@Override
	public void refreshEnumerations() {
		try {
			List<Object> fields = localDataSource == null ? Collections.emptyList() : localDataSource.getEnumerations(LocalDataSource.ENUM_FIELD);
			cmbViewer_Fields.setInput(fields);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh enumerations in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	@Override
	public void refreshSelections() {
		try {
			txt_ScreenTip.setText(getValue());
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	protected void insertSelectedFieldInScreenTip() {
		String fieldName = (String) ((StructuredSelection)cmbViewer_Fields.getSelection()).getFirstElement();
		if (fieldName != null) {
			txt_ScreenTip.insert("{" + fieldName + "}");
			txt_ScreenTip.forceFocus();
		}
	}
	
	protected void screenTipTextChanged() {
		try {
			setValue(txt_ScreenTip.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process screen tip changes", e));
		}		
	}
	
	protected abstract String getValue() throws Exception;
	
	protected abstract void setValue(String value) throws Exception;
}
