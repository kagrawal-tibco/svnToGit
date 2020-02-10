package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.ui.forms.AlertSettingsForm;

public class ContentAlertSettingsForm extends IndicatorAlertSettingsForm {

	private Text txt_MinValueRef;
	private Text txt_ShownAs;
	private Text txt_MaxValueRef;

	private List<Control> progressBarControls;

	public ContentAlertSettingsForm(FormToolkit formToolKit, Composite parent) {
		super(formToolKit, parent);
	}

	@Override
	protected void layoutValueFieldReference(Composite composite) {
		super.layoutValueFieldReference(composite);

		createLabel(composite, "Show As:", SWT.NONE);
		txt_ShownAs = createText(composite, "", SWT.READ_ONLY | SWT.SINGLE);
		txt_ShownAs.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));

		Label lbl_MinValue = createLabel(composite, "Minimum Value:", SWT.NONE);
		txt_MinValueRef = createText(composite, "", SWT.READ_ONLY | SWT.SINGLE);
		txt_MinValueRef.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));

		Label lbl_MaxValue = createLabel(composite, "Maximum Value:", SWT.NONE);
		txt_MaxValueRef = createText(composite, "", SWT.READ_ONLY | SWT.SINGLE);
		txt_MaxValueRef.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));

		progressBarControls = new ArrayList<Control>(4);
		progressBarControls.add(lbl_MinValue);
		progressBarControls.add(txt_MinValueRef);
		progressBarControls.add(lbl_MaxValue);
		progressBarControls.add(txt_MaxValueRef);
	}

	@Override
	protected AlertSettingsForm createAlertSettingsForm() {
		return new AlertSettingsForm(formToolKit, formComposite, SWT.HORIZONTAL);
	}

	@Override
	public void refreshSelections() {
		super.refreshSelections();
		String min = "";
		String max = "";
		String type = "";
		try {
			boolean isProgressBar = StateMachineComponentHelper.isProgressBarContentSeriesConfig(localStateSeriesConfig);
			if (isProgressBar == true){
				type = "Progress Bar";
				min = localStateSeriesConfig.getPropertyValue("ProgressMinValue");
				if (min.equals("") == true){
					min = localStateSeriesConfig.getPropertyValue("ProgressMinField");
				}
				max = localStateSeriesConfig.getPropertyValue("ProgressMaxValue");
				if (max.equals("") == true){
					max = localStateSeriesConfig.getPropertyValue("ProgressMaxField");
				}
			}
			else {
				type = "Text";
				txt_ShownAs.setText("Text");
			}
			for (Control control : progressBarControls) {
				control.setVisible(isProgressBar);
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
		} finally {
			txt_ShownAs.setText(type);
			txt_MinValueRef.setText(min);
			txt_MaxValueRef.setText(max);
		}
	}

	@Override
	protected String getValueFieldPropertyName() {
		try {
			if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(localStateSeriesConfig) == true){
				return "ProgressValueField";
			}
			return "TextValueField";
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not determine the content type of " + localStateSeriesConfig + " in "+this.getClass().getName(), e));
			disableAll();
			return "";
		}
	}

}
