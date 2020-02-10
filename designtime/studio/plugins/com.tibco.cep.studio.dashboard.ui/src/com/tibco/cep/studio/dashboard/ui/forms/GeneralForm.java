package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class GeneralForm extends BaseForm {

	private Text txt_DisplayName;
	private ModifyListener txt_DisplayNameModifyListener;

	protected LocalConfig localConfig;

	public GeneralForm(FormToolkit formToolKit, Composite parent) {
		super("General", formToolKit, parent, false);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2, false));
		// display name label
		createLabel(formComposite, "Display Name:", SWT.NONE);
		txt_DisplayName = createText(formComposite, "", SWT.SINGLE);
		txt_DisplayName.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		if (newLocalElement instanceof LocalConfig){
			localConfig = (LocalConfig)newLocalElement;
		}
		else {
			throw new IllegalArgumentException(newLocalElement+" is not a beviews config element");
		}
	}

	@Override
	protected void doEnableListeners() {
		if (txt_DisplayNameModifyListener == null){
			txt_DisplayNameModifyListener = new ModifyListener(){

				@Override
				public void modifyText(ModifyEvent e) {
					displayNameChanged();
				}

			};
		}
		txt_DisplayName.addModifyListener(txt_DisplayNameModifyListener);
	}

	@Override
	protected void doDisableListeners() {
		txt_DisplayName.removeModifyListener(txt_DisplayNameModifyListener);
	}

	@Override
	public void refreshEnumerations() {
		//do nothing
	}

	@Override
	public void refreshSelections() {
		try {
			txt_DisplayName.setText(localConfig.getPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME));
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		}
	}


	private void displayNameChanged(){
		try {
			localConfig.setPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME,txt_DisplayName.getText());
		} catch (Exception e) {
			disableAll();
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update display name",e));
		}
	}

}
