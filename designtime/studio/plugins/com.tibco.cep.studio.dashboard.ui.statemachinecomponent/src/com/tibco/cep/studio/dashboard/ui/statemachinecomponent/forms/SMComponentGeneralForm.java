package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.GeneralForm;

public class SMComponentGeneralForm extends GeneralForm {

	private Text txt_Description;
	private ModifyListener txt_DescriptionModifyListener;

	private Button btn_100Size;
	private Button btn_125Size;
	private Button btn_150Size;
	private Button btn_175Size;
	private Button btn_200Size;
	private Button btn_CustomSize;

	private SelectionListener btn_SizeSelectionListener;

	private Spinner spn_Width;
	private SelectionListener spn_WidthSelectionListener;

	private Spinner spn_Height;
	private SelectionListener spn_HeightSelectionListener;

	private Button btn_AspectRatio;
	private SelectionListener btn_AspectRatioSelectionListener;

	public SMComponentGeneralForm(FormToolkit formToolKit, Composite parent) {
		super(formToolKit, parent);
	}

	@Override
	public void init() {
		super.init();
		// description
		Label lbl_Description = createLabel(formComposite, "Description:", SWT.NONE);
		lbl_Description.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false));
		txt_Description = createText(formComposite, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridData txtDescriptionLayoutData = new GridData(SWT.FILL,SWT.FILL,true,false);
		txtDescriptionLayoutData.heightHint = 50;
		txt_Description.setLayoutData(txtDescriptionLayoutData);
		//state size
		Label lbl_StateSize = createLabel(formComposite, "State Size:", SWT.NONE);
		lbl_StateSize.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false));

		Composite cmp_StateSize = createComposite(formComposite, SWT.NONE);
		cmp_StateSize.setLayout(new GridLayout());

		Composite cmp_Buttons = createComposite(cmp_StateSize, SWT.NONE);
		cmp_Buttons.setLayout(new FillLayout(SWT.HORIZONTAL));

		btn_100Size = createButton(cmp_Buttons, "Default", SWT.RADIO);
		btn_100Size.setToolTipText("Set the state size to default dimensions");
		btn_125Size = createButton(cmp_Buttons, "+25%", SWT.RADIO);
		btn_125Size.setToolTipText("Increase the state size by 25%");
		btn_150Size = createButton(cmp_Buttons, "+50%", SWT.RADIO);
		btn_150Size.setToolTipText("Increase the state size by 50%");
		btn_175Size = createButton(cmp_Buttons, "+75%", SWT.RADIO);
		btn_175Size.setToolTipText("Increase the state size by 75%");
		btn_200Size = createButton(cmp_Buttons, "+100%", SWT.RADIO);
		btn_200Size.setToolTipText("Increase the state size by 100%");
		btn_CustomSize = createButton(cmp_Buttons, "Custom", SWT.RADIO);
		btn_CustomSize.setToolTipText("Set the state size to custom dimensions");

		Composite cmp_Size = createComposite(cmp_StateSize, SWT.NONE);
		GridLayout cmp_SizeLayout = new GridLayout(5,false);
		cmp_SizeLayout.marginHeight = 0;
		cmp_SizeLayout.marginWidth = 0;
		cmp_Size.setLayout(cmp_SizeLayout);

		createLabel(cmp_Size, "Width:", SWT.NONE);
		spn_Width = createSpinner(cmp_Size, SWT.BORDER);


		createLabel(cmp_Size, " ", SWT.NONE);

		createLabel(cmp_Size, "Height:", SWT.NONE);
		spn_Height  = createSpinner(cmp_Size, SWT.BORDER);
		spn_Width.setMaximum(Integer.MAX_VALUE);

		btn_AspectRatio = createButton(cmp_StateSize, "Preserve aspect ratio", SWT.CHECK);

	}

	@Override
	protected void doEnableListeners() {
		super.doEnableListeners();
		if (txt_DescriptionModifyListener == null){
			txt_DescriptionModifyListener = new ModifyListener(){

				@Override
				public void modifyText(ModifyEvent e) {
					descriptionChanged();
				}

			};
		}
		txt_Description.addModifyListener(txt_DescriptionModifyListener);

		if (btn_SizeSelectionListener == null) {
			btn_SizeSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Button source = (Button) e.getSource();
					if (source.getSelection() == true) {
						btnSizeSelected(source);
					}
				}
			};
		}

		btn_100Size.addSelectionListener(btn_SizeSelectionListener);
		btn_125Size.addSelectionListener(btn_SizeSelectionListener);
		btn_150Size.addSelectionListener(btn_SizeSelectionListener);
		btn_175Size.addSelectionListener(btn_SizeSelectionListener);
		btn_200Size.addSelectionListener(btn_SizeSelectionListener);
		btn_CustomSize.addSelectionListener(btn_SizeSelectionListener);

		if (spn_WidthSelectionListener == null) {
			spn_WidthSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					widthChanged();
				}
			};
		}
		spn_Width.addSelectionListener(spn_WidthSelectionListener);

		if (spn_HeightSelectionListener == null) {
			spn_HeightSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					heightChanged();
				}
			};
		}
		spn_Height.addSelectionListener(spn_HeightSelectionListener);

		if (btn_AspectRatioSelectionListener == null) {
			btn_AspectRatioSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					aspectRatioSelected();
				}
			};
		}

		btn_AspectRatio.addSelectionListener(btn_AspectRatioSelectionListener);

	}

	@Override
	protected void doDisableListeners() {
		super.doDisableListeners();

		txt_Description.removeModifyListener(txt_DescriptionModifyListener);

		btn_100Size.removeSelectionListener(btn_SizeSelectionListener);
		btn_125Size.removeSelectionListener(btn_SizeSelectionListener);
		btn_150Size.removeSelectionListener(btn_SizeSelectionListener);
		btn_175Size.removeSelectionListener(btn_SizeSelectionListener);
		btn_200Size.removeSelectionListener(btn_SizeSelectionListener);
		btn_CustomSize.removeSelectionListener(btn_SizeSelectionListener);

		spn_Width.removeSelectionListener(spn_WidthSelectionListener);
		spn_Height.removeSelectionListener(spn_HeightSelectionListener);

		btn_AspectRatio.removeSelectionListener(btn_AspectRatioSelectionListener);
	}


	@Override
	public void refreshEnumerations() {
		int defaultHeight = Integer.parseInt(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT)).getDefault());
		int defaultWidth = Integer.parseInt(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH)).getDefault());

		spn_Width.setMinimum(defaultWidth);
		spn_Width.setMaximum(Integer.MAX_VALUE);

		spn_Height.setMinimum(defaultHeight);
		spn_Height.setMaximum(Integer.MAX_VALUE);

	}

	@Override
	public void refreshSelections() {
		super.refreshSelections();
		try {
			txt_Description.setText(localConfig.getPropertyValue(LocalConfig.PROP_KEY_DESCRIPTION));

			double defaultHeight = Integer.parseInt(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT)).getDefault());
			double defaultWidth = Integer.parseInt(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH)).getDefault());

			double height = Integer.parseInt(localConfig.getPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT));
			double width = Integer.parseInt(localConfig.getPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH));

			double heightIncreaseRatio = height/defaultHeight;
			double widthIncreaseRatio = width/defaultWidth;

			//decide which size button to select
			if (heightIncreaseRatio != widthIncreaseRatio) {
				btn_CustomSize.setSelection(true);
			} else if (heightIncreaseRatio == 1.00) {
				btn_100Size.setSelection(true);
			} else if (heightIncreaseRatio == 1.25) {
				btn_125Size.setSelection(true);
			} else if (heightIncreaseRatio == 1.50) {
				btn_150Size.setSelection(true);
			} else if (heightIncreaseRatio == 1.75) {
				btn_175Size.setSelection(true);
			} else if (heightIncreaseRatio == 2.00) {
				btn_200Size.setSelection(true);
			} else {
				btn_CustomSize.setSelection(true);
			}

			//update the spinner with current values
			spn_Width.setSelection((int) Math.round(width));
			spn_Height.setSelection((int) Math.round(height));

			//decide whether to enable the spinners or not
			spn_Width.setEnabled(btn_CustomSize.getSelection());
			spn_Height.setEnabled(btn_CustomSize.getSelection());

			//decide whether aspect ratio is still intact or not
			btn_AspectRatio.setSelection(defaultHeight/defaultWidth == height/width);

		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	private void descriptionChanged(){
		try {
			localConfig.setPropertyValue(LocalConfig.PROP_KEY_DESCRIPTION,txt_Description.getText());
		} catch (Exception e) {
			disableAll();
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update description",e));
		}
	}

	private void btnSizeSelected(Button btn) {
		double height = Double.parseDouble(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT)).getDefault());
		double width = Double.parseDouble(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH)).getDefault());
		if (btn == btn_100Size) {
			//do nothing
		}
		else if (btn == btn_125Size) {
			height = height * 1.25;
			width = width * 1.25;
		}
		else if (btn == btn_125Size) {
			height = height * 1.25;
			width = width * 1.25;
		}
		else if (btn == btn_150Size) {
			height = height * 1.50;
			width = width * 1.50;
		}
		else if (btn == btn_175Size) {
			height = height * 1.75;
			width = width * 1.75;
		}
		else if (btn == btn_200Size) {
			height = height * 2.00;
			width = width * 2.00;
		}
		if (btn != btn_CustomSize) {
			int roundedHeight = (int) Math.round(height);
			int roundedWidth = (int) Math.round(width);
			localConfig.setPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT, String.valueOf(roundedHeight));
			spn_Height.setSelection(roundedHeight);
			localConfig.setPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH, String.valueOf(roundedWidth));
			spn_Width.setSelection(roundedWidth);
		}
		spn_Height.setEnabled(btn_CustomSize.getSelection());
		spn_Width.setEnabled(btn_CustomSize.getSelection());
	}

	private void widthChanged() {
		double defaultHeight = Double.parseDouble(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT)).getDefault());
		double defaultWidth = Double.parseDouble(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH)).getDefault());

		int width = spn_Width.getSelection();
		int height = spn_Height.getSelection();
		if (btn_AspectRatio.getSelection() == true) {
			height = (int) Math.round(width * (defaultHeight/defaultWidth));
			spn_Height.setSelection(height);
		}

		localConfig.setPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT, String.valueOf(height));
		localConfig.setPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH, String.valueOf(width));
	}

	private void heightChanged() {
		double defaultHeight = Double.parseDouble(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT)).getDefault());
		double defaultWidth = Double.parseDouble(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH)).getDefault());

		int height = spn_Height.getSelection();
		int width = spn_Width.getSelection();
		if (btn_AspectRatio.getSelection() == true) {
			width = (int) Math.round(height * (defaultWidth/defaultHeight));
			spn_Width.setSelection(width);
		}

		localConfig.setPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT, String.valueOf(height));
		localConfig.setPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH, String.valueOf(width));
	}

	private void aspectRatioSelected() {
		if (btn_AspectRatio.getSelection() == true) {
			double defaultHeight = Double.parseDouble(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT)).getDefault());
			double defaultWidth = Double.parseDouble(((SynProperty)localConfig.getProperty(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_WIDTH)).getDefault());

			int width = spn_Width.getSelection();
			int height = (int) Math.round(width * (defaultHeight/defaultWidth));
			spn_Height.setSelection(height);

			localConfig.setPropertyValue(LocalStateMachineComponent.PROP_STATE_VISUALIZATION_HEIGHT, String.valueOf(height));

		}
	}

}