package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.LocalElementLabelProvider;
import com.tibco.cep.studio.dashboard.ui.forms.ValueFieldSettingsBean;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms.ContentValueFieldSettingsBean.VALUE_OPTION;

public class ContentValueFieldSettingsForm extends IndicatorValueFieldSettingsForm {

	public static final String PROP_KEY_FORMAT_TYPE = "formatType";

	private Button btn_Text;
	private AbstractSelectionListener btn_Text_SelectionListener;

	private Button btn_ProgressBar;
	private AbstractSelectionListener btn_ProgressBarSelectionListener;

	private Combo cmb_Format;
	private ComboViewer cmbViewer_Format;
	private AbstractSelectionListener cmb_FormatSelectionListener;

	private Text txt_Pattern;
	private ModifyListener txt_PatternModifyListener;

	private Button btn_Min_Constant;
	private AbstractSelectionListener btn_Min_Constant_SelectionListener;
	private Text txt_Min_Constant;
	private ModifyListener txt_Min_ConstantModifyListener;

	private Button btn_Min_Field;
	private AbstractSelectionListener btn_Min_Field_SelectionListener;
	private Combo cmb_Min_Field;
	private ComboViewer cmbViewer_Min_Field;
	private AbstractSelectionListener cmb_Min_FieldSelectionListener;

	private Button btn_Max_Constant;
	private AbstractSelectionListener btn_Max_Constant_SelectionListener;
	private Text txt_Max_Constant;
	private ModifyListener txt_Max_ConstantModifyListener;

	private Button btn_Max_Field;
	private AbstractSelectionListener btn_Max_Field_SelectionListener;
	private Combo cmb_Max_Field;
	private ComboViewer cmbViewer_Max_Field;
	private AbstractSelectionListener cmb_Max_FieldSelectionListener;

	private Map<String, ContentValueFieldSettingsBean> fieldNameSettingsCache;

	private Label lbl_MinValue;

	private Label lbl_MaxValue;

	public ContentValueFieldSettingsForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super(formToolKit, parent, showGroup);
	}

	@Override
	public void init() {
		super.init();

		// show as
		createLabel(formComposite, "Show as:", SWT.NONE);
		Composite showAsComposite = createComposite(formComposite, SWT.NONE);
		RowLayout showAsCompositeLayout = new RowLayout(SWT.HORIZONTAL);
		showAsCompositeLayout.marginBottom = 0;
		showAsCompositeLayout.marginLeft = 0;
		showAsCompositeLayout.marginRight = 0;
		showAsCompositeLayout.marginTop = 0;
		showAsComposite.setLayout(showAsCompositeLayout);
		btn_Text = createButton(showAsComposite, "Text", SWT.RADIO);
		btn_ProgressBar = createButton(showAsComposite, "Progress Bar", SWT.RADIO);
		showAsComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// format
		createLabel(formComposite, "Format:", SWT.NONE);
		cmb_Format = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Format.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// pattern
		createLabel(formComposite, "Pattern:", SWT.NONE);
		txt_Pattern = createText(formComposite, "", SWT.NONE);
		txt_Pattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		lbl_MinValue = createLabel(formComposite, "Minimum Value:", SWT.NONE);
		lbl_MinValue.setLayoutData(new GridData(SWT.DEFAULT, SWT.TOP, false, false));
		Composite minValueComposite = createComposite(formComposite, SWT.NONE);
		GridLayout minValueCompositeLayout = new GridLayout(2, false);
		minValueCompositeLayout.marginHeight = 0;
		minValueCompositeLayout.marginWidth = 0;
		minValueComposite.setLayout(minValueCompositeLayout);
		btn_Min_Constant = createButton(minValueComposite, "Constant", SWT.RADIO);
		txt_Min_Constant = createText(minValueComposite, "", SWT.NONE);
		txt_Min_Constant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		btn_Min_Field = createButton(minValueComposite, "Field", SWT.RADIO);
		cmb_Min_Field = createCombo(minValueComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Min_Field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		minValueComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		lbl_MaxValue = createLabel(formComposite, "Maximum Value:", SWT.NONE);
		lbl_MaxValue.setLayoutData(new GridData(SWT.DEFAULT, SWT.TOP, false, false));
		Composite maxValueComposite = createComposite(formComposite, SWT.NONE);
		GridLayout maxValueCompositeLayout = new GridLayout(2, false);
		maxValueCompositeLayout.marginHeight = 0;
		maxValueCompositeLayout.marginWidth = 0;
		maxValueComposite.setLayout(maxValueCompositeLayout);
		btn_Max_Constant = createButton(maxValueComposite, "Constant", SWT.RADIO);
		txt_Max_Constant = createText(maxValueComposite, "", SWT.NONE);
		txt_Max_Constant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		btn_Max_Field = createButton(maxValueComposite, "Field", SWT.RADIO);
		cmb_Max_Field = createCombo(maxValueComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Max_Field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		maxValueComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		cmbViewer_Format = new ComboViewer(cmb_Format);
		cmbViewer_Format.setContentProvider(new ArrayContentProvider());
		cmbViewer_Format.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof DisplayValueFormat) {
					return ((DisplayValueFormat) element).getLabel();
				}
				return super.getText(element);
			}

		});

		cmbViewer_Min_Field = new ComboViewer(cmb_Min_Field);
		cmbViewer_Min_Field.setContentProvider(new ArrayContentProvider());
		cmbViewer_Min_Field.setLabelProvider(new LocalElementLabelProvider(false));

		cmbViewer_Max_Field = new ComboViewer(cmb_Max_Field);
		cmbViewer_Max_Field.setContentProvider(new ArrayContentProvider());
		cmbViewer_Max_Field.setLabelProvider(new LocalElementLabelProvider(false));

		cmb_Format.setEnabled(false);
		txt_Pattern.setEnabled(false);
		txt_Min_Constant.setEnabled(false);
		cmb_Min_Field.setEnabled(false);
		txt_Max_Constant.setEnabled(false);
		cmb_Max_Field.setEnabled(false);

	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		fieldNameSettingsCache = new HashMap<String, ContentValueFieldSettingsBean>();
		super.inputChanged(oldLocalElement, newLocalElement);
	}

	@Override
	protected void doEnableListeners() {
		super.doEnableListeners();
		if (btn_Text_SelectionListener == null) {
			btn_Text_SelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_Text.getSelection() == true) {
						try {
							//is the current a text
							if (StateMachineComponentHelper.isTextContentSeriesConfig(localStateSeriesConfig) == false) {
								//we are not text , switch to text
								switchToText();
							}
						} catch (Exception ex) {
							logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not determine type of content ", ex));
						}
					}
				}

			};
		}
		btn_Text.addSelectionListener(btn_Text_SelectionListener);

		if (btn_ProgressBarSelectionListener == null) {
			btn_ProgressBarSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_ProgressBar.getSelection() == true){
						try {
							//is the current a progress bar
							if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(localStateSeriesConfig) == false) {
								//we are not progress bar , switch to progress bar
								switchToProgressBar();
							}
						} catch (Exception ex) {
							logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not determine type of content ", ex));
						}
					}
				}

			};
		}
		btn_ProgressBar.addSelectionListener(btn_ProgressBarSelectionListener);

		if (cmb_FormatSelectionListener == null) {
			cmb_FormatSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					formatChanged();
				}

			};
		}
		cmb_Format.addSelectionListener(cmb_FormatSelectionListener);

		if (txt_PatternModifyListener == null) {
			txt_PatternModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					patternTextChanged();
				}

			};
		}
		txt_Pattern.addModifyListener(txt_PatternModifyListener);

		if (btn_Min_Constant_SelectionListener == null) {
			btn_Min_Constant_SelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_Min_Constant.getSelection() == true) {
						minConstantSelected();
					}
				}

			};
		}
		btn_Min_Constant.addSelectionListener(btn_Min_Constant_SelectionListener);

		if (txt_Min_ConstantModifyListener == null) {
			txt_Min_ConstantModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					minConstantChanged();
				}

			};
		}
		txt_Min_Constant.addModifyListener(txt_Min_ConstantModifyListener);

		if (btn_Min_Field_SelectionListener == null) {
			btn_Min_Field_SelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_Min_Field.getSelection() == true) {
						minFieldSelected();
					}
				}

			};
		}
		btn_Min_Field.addSelectionListener(btn_Min_Field_SelectionListener);

		if (cmb_Min_FieldSelectionListener == null) {
			cmb_Min_FieldSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					minFieldChanged();
				}

			};
		}
		cmb_Min_Field.addSelectionListener(cmb_Min_FieldSelectionListener);

		if (btn_Max_Constant_SelectionListener == null) {
			btn_Max_Constant_SelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_Max_Constant.getSelection() == true) {
						maxConstantSelected();
					}
				}

			};
		}
		btn_Max_Constant.addSelectionListener(btn_Max_Constant_SelectionListener);

		if (txt_Max_ConstantModifyListener == null) {
			txt_Max_ConstantModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					maxConstantChanged();
				}

			};
		}
		txt_Max_Constant.addModifyListener(txt_Max_ConstantModifyListener);

		if (btn_Max_Field_SelectionListener == null) {
			btn_Max_Field_SelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_Max_Field.getSelection() == true) {
						maxFieldSelected();
					}
				}

			};
		}
		btn_Max_Field.addSelectionListener(btn_Max_Field_SelectionListener);

		if (cmb_Max_FieldSelectionListener == null) {
			cmb_Max_FieldSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					maxFieldChanged();
				}

			};
		}
		cmb_Max_Field.addSelectionListener(cmb_Max_FieldSelectionListener);
	}



	@Override
	protected void doDisableListeners() {
		super.doDisableListeners();

		btn_Text.removeSelectionListener(btn_Text_SelectionListener);
		btn_ProgressBar.removeSelectionListener(btn_ProgressBarSelectionListener);

		cmb_Format.removeSelectionListener(cmb_FormatSelectionListener);
		txt_Pattern.removeModifyListener(txt_PatternModifyListener);

		btn_Min_Constant.removeSelectionListener(btn_Min_Constant_SelectionListener);
		txt_Min_Constant.removeModifyListener(txt_Min_ConstantModifyListener);
		btn_Min_Field.removeSelectionListener(btn_Min_Field_SelectionListener);
		cmb_Min_Field.removeSelectionListener(cmb_Min_FieldSelectionListener);

		btn_Max_Constant.removeSelectionListener(btn_Max_Constant_SelectionListener);
		txt_Max_Constant.removeModifyListener(txt_Max_ConstantModifyListener);
		btn_Max_Field.removeSelectionListener(btn_Max_Field_SelectionListener);
		cmb_Max_Field.removeSelectionListener(cmb_Max_FieldSelectionListener);
	}

	@Override
	public void refreshEnumerations() {
		super.refreshEnumerations();
		try {
			//get the value fields
			List<Object> valueFields = localDataSource == null ? Collections.emptyList() : localDataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD);
			// create the settings for all category fields
			for (Object valueFieldAsObj : valueFields) {
				String valueField = (String) valueFieldAsObj;
				// find the field and its data type
				String dataType = localDataSource.getFieldByName(LocalDataSource.ENUM_NUMERIC_FIELD, valueField).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
				fieldNameSettingsCache.put(valueField, new ContentValueFieldSettingsBean(valueField,dataType));
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh enumerations in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	@Override
	public void refreshSelections() {
		try {
			//initialize the content value field settings for the current field
			String valueFieldName = localStateSeriesConfig.getPropertyValue(getValueFieldPropertyName());
			ContentValueFieldSettingsBean fieldSettings = fieldNameSettingsCache.get(valueFieldName);
			if (fieldSettings != null) {
				//update the display format
				fieldSettings.update(localStateSeriesConfig.getPropertyValue(getValueFieldDisplayFormatPropertyName()));
				//update min/max if localStateSeriesConfig is progress bar based
				if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(localStateSeriesConfig) == true) {
					if (localStateSeriesConfig.isPropertyValueSameAsDefault("ProgressMinValue") == false) {
						fieldSettings.setMinValueOption(VALUE_OPTION.CONSTANT);
						fieldSettings.setMinValue(localStateSeriesConfig.getPropertyValue("ProgressMinValue"));
					}
					else {
						fieldSettings.setMinValueOption(VALUE_OPTION.FIELD);
						fieldSettings.setMinValue(localStateSeriesConfig.getPropertyValue("ProgressMinField"));
					}
					if (localStateSeriesConfig.isPropertyValueSameAsDefault("ProgressMaxValue") == false) {
						fieldSettings.setMaxValueOption(VALUE_OPTION.CONSTANT);
						fieldSettings.setMaxValue(localStateSeriesConfig.getPropertyValue("ProgressMaxValue"));
					}
					else {
						fieldSettings.setMaxValueOption(VALUE_OPTION.FIELD);
						fieldSettings.setMaxValue(localStateSeriesConfig.getPropertyValue("ProgressMaxField"));
					}
				}
			}
			//now update the value field
			super.refreshSelections();
			//now update the selections in our form
			updateSelections(fieldSettings);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	private void updateSelections(ContentValueFieldSettingsBean fieldSettingsBean) throws Exception {
		disableListeners();
		try {
			if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(localStateSeriesConfig) == true) {
				//enable & update progress bar controls
				btn_ProgressBar.setSelection(true);
				btn_Text.setSelection(false);
				//we do not allow formatting of progress field , so we set the format type empty
				cmbViewer_Format.setInput(Collections.emptyList());
				//remove any selections for the format viewer
				cmbViewer_Format.setSelection(StructuredSelection.EMPTY);
				//clear the pattern text
				txt_Pattern.setText("");
				//update min & max fields to value field collection
				List<Object> valueFields = localDataSource == null ? Collections.emptyList() : localDataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD);
				cmbViewer_Min_Field.setInput(valueFields);
				cmbViewer_Max_Field.setInput(valueFields);
				if (fieldSettingsBean != null) {
					//enable the progress bar controls
					setProgressBarControlsEnable(true);
					//update the min value options
					if (fieldSettingsBean.getMinValueOption().compareTo(VALUE_OPTION.CONSTANT) == 0) {
						//we are dealing with a constant
						btn_Min_Constant.setSelection(true);
						txt_Min_Constant.setEnabled(true);
						txt_Min_Constant.setText(fieldSettingsBean.getMinValue());
						btn_Min_Field.setSelection(false);
						cmb_Min_Field.setEnabled(false);
						cmbViewer_Min_Field.setSelection(StructuredSelection.EMPTY);
						//update the error indication
						SynProperty property = (SynProperty) localStateSeriesConfig.getProperty("ProgressMinValue");
						if (property.isValid(fieldSettingsBean.getMinValue()) == false) {
							//we have a invalid value , do something
							indicateError(lbl_MinValue, property.getValidationMessage().getMessage());
						}
						else {
							resetError(lbl_MinValue);
						}
					}
					else {
						//we are dealing with a field
						btn_Min_Constant.setSelection(false);
						txt_Min_Constant.setEnabled(false);
						txt_Min_Constant.setText("");
						btn_Min_Field.setSelection(true);
						cmb_Min_Field.setEnabled(true);
						cmbViewer_Min_Field.setSelection(new StructuredSelection(fieldSettingsBean.getMinValue()));
					}
					//update max value
					if (fieldSettingsBean.getMaxValueOption().compareTo(VALUE_OPTION.CONSTANT) == 0) {
						//we are dealing with a constant
						btn_Max_Constant.setSelection(true);
						txt_Max_Constant.setEnabled(true);
						txt_Max_Constant.setText(fieldSettingsBean.getMaxValue());
						btn_Max_Field.setSelection(false);
						cmb_Max_Field.setEnabled(false);
						cmbViewer_Max_Field.setSelection(StructuredSelection.EMPTY);
						//update the error indication
						SynProperty property = (SynProperty) localStateSeriesConfig.getProperty("ProgressMaxValue");
						if (property.isValid(fieldSettingsBean.getMaxValue()) == false) {
							//we have a invalid value , do something
							indicateError(lbl_MaxValue, property.getValidationMessage().getMessage());
						}
						else {
							resetError(lbl_MaxValue);
						}
					}
					else {
						//we are dealing with a field
						btn_Max_Constant.setSelection(false);
						txt_Max_Constant.setEnabled(false);
						txt_Max_Constant.setText("");
						btn_Max_Field.setSelection(true);
						cmb_Max_Field.setEnabled(true);
						cmbViewer_Max_Field.setSelection(new StructuredSelection(fieldSettingsBean.getMaxValue()));
					}
				}
			}
			else {
				//enable & update text controls
				btn_ProgressBar.setSelection(false);
				btn_Text.setSelection(true);
				//reset the inputs min , max type
				cmbViewer_Min_Field.setInput(Collections.emptyList());
				cmbViewer_Max_Field.setInput(Collections.emptyList());
				//reset the selections for min and max fields
				cmbViewer_Min_Field.setSelection(StructuredSelection.EMPTY);
				cmbViewer_Max_Field.setSelection(StructuredSelection.EMPTY);
				//clear the constant fields
				txt_Min_Constant.setText("");
				txt_Max_Constant.setText("");
				//disable the progress bar controls
				setProgressBarControlsEnable(false);
				// value display format
				// get supported formats for the data types
				List<DisplayValueFormat> displayValueFormats = fieldSettingsBean == null ? new ArrayList<DisplayValueFormat>() : fieldSettingsBean.getDisplayValueFormats();
				// update the format style drop down
				cmbViewer_Format.setInput(displayValueFormats);
				if (displayValueFormats.size() != 0) {
					//update the format type to current display value format
					cmbViewer_Format.setSelection(fieldSettingsBean.getDisplayValueFormat() == null ? StructuredSelection.EMPTY : new StructuredSelection(fieldSettingsBean.getDisplayValueFormat()));
					// enable or disable the pattern text field
					boolean isUsingPattern = fieldSettingsBean.isUsingPattern();
					txt_Pattern.setEnabled(isUsingPattern);
					if (isUsingPattern == true) {
						// update the pattern
						txt_Pattern.setText(fieldSettingsBean.getPattern());
					}
					else {
						txt_Pattern.setText("");
					}
				} else {
					// disable the format type drop down
					cmb_Format.setEnabled(false);
					// reset the pattern text field
					txt_Pattern.setText("");
					// disable the pattern text field
					txt_Pattern.setEnabled(false);
				}
				//reset the min and max labels
				resetError(lbl_MinValue);
				resetError(lbl_MaxValue);
			}
			//enable/disable the progress bar / text buttons
			btn_ProgressBar.setEnabled(fieldSettingsBean != null);
			btn_Text.setEnabled(fieldSettingsBean != null);
		} finally {
			enableListeners();
		}
	}

	@Override
	protected void valueFieldChanged() {
		disableListeners();
		try {
			// get current field name and its state
			String currentFieldName = localElement.getPropertyValue(getValueFieldPropertyName());
			ContentValueFieldSettingsBean currentFieldSettings = this.fieldNameSettingsCache.get(currentFieldName);
			// get selected field name
			String selectedFieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			// get selected field settings
			ContentValueFieldSettingsBean selectedFieldSettings = this.fieldNameSettingsCache.get(selectedFieldName);
			//handle format & pattern
			if (selectedFieldSettings.haveSameDisplayValueFormats(currentFieldSettings) == true) {
				//we have equivalent display value formats, no need to update the style drop down or pattern field
				//transfer the display value format and pattern
				selectedFieldSettings.setDisplayValueFormat(currentFieldSettings.getDisplayValueFormat());
				selectedFieldSettings.setPattern(currentFieldSettings.getPattern());
			}
			else if (selectedFieldSettings.getDisplayValueFormat() == null){
				selectedFieldSettings.setDisplayValueFormat(selectedFieldSettings.getDefaultDisplayValueFormat());
			}
			//transfer all matching options
			if (selectedFieldSettings.getMinValueOption() == null) {
				selectedFieldSettings.setMinValueOption(currentFieldSettings.getMinValueOption());
				selectedFieldSettings.setMinValue(currentFieldSettings.getMinValue());
			}
			if (selectedFieldSettings.getMaxValueOption() == null) {
				selectedFieldSettings.setMaxValueOption(currentFieldSettings.getMaxValueOption());
				selectedFieldSettings.setMaxValue(currentFieldSettings.getMaxValue());
			}
			// update the value display label format
			localStateSeriesConfig.setPropertyValue(getValueFieldDisplayFormatPropertyName(), selectedFieldSettings.getDisplayLabelFormat(selectedFieldSettings.getDisplayValueFormat()));
			// update the field (we trigger this last so that all dependent values are up to date
			localStateSeriesConfig.setPropertyValue(getValueFieldPropertyName(),selectedFieldName);
			//update the selections
			updateSelections(selectedFieldSettings);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process value field settings change", e));
		} finally {
			enableListeners();
		}
	}

	@Override
	protected String getValueFieldPropertyName() throws Exception {
		if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(localStateSeriesConfig) == true) {
			return "ProgressValueField";
		}
		return "TextValueField";
	}

	private String getValueFieldDisplayFormatPropertyName() throws Exception {
		if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(localStateSeriesConfig) == true) {
			return "ProgressDisplayFormat";
		}
		return "TextDisplayFormat";
	}

	protected void switchToProgressBar() {
		try {
			//we are switching from text to progress
			String fieldName = localStateSeriesConfig.getPropertyValue("TextValueField");
			localStateSeriesConfig.setPropertyValue("ProgressValueField",fieldName);
			localStateSeriesConfig.setPropertyValue("ProgressDisplayFormat",localStateSeriesConfig.getPropertyValue("TextDisplayFormat"));
			localStateSeriesConfig.setPropertyValue("ProgressTooltipFormat",localStateSeriesConfig.getPropertyValue("TextTooltipFormat"));
			localStateSeriesConfig.setPropertyValue("TextValueField",null);
			localStateSeriesConfig.setPropertyValue("TextDisplayFormat",null);
			localStateSeriesConfig.setPropertyValue("TextTooltipFormat",null);
			ContentValueFieldSettingsBean fieldSettingsBean = fieldNameSettingsCache.get(fieldName);
			if (fieldSettingsBean.getMinValueOption() == null) {
				fieldSettingsBean.setMinValueOption(ContentValueFieldSettingsBean.VALUE_OPTION.CONSTANT);
				fieldSettingsBean.setMinValue("0");
				//also update the series config
				localStateSeriesConfig.setPropertyValue("ProgressMinValue","0");
			}
			if (fieldSettingsBean.getMaxValueOption() == null) {
				fieldSettingsBean.setMaxValueOption(ContentValueFieldSettingsBean.VALUE_OPTION.CONSTANT);
				fieldSettingsBean.setMaxValue("100");
				//also update the series config
				localStateSeriesConfig.setPropertyValue("ProgressMaxValue","100");
			}
			//update the selections
			updateSelections(fieldSettingsBean);
			//fire the change
			propertyChangeSupport.firePropertyChange(PROP_KEY_FORMAT_TYPE, "Text", "ProgressBar");
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not switch from text to progress bar", e));
		}
	}

	protected void switchToText() {
		try {
			//we are switching from progress to text
			String fieldName = localStateSeriesConfig.getPropertyValue("ProgressValueField");
			localStateSeriesConfig.setPropertyValue("TextValueField",fieldName);
			localStateSeriesConfig.setPropertyValue("TextDisplayFormat",localStateSeriesConfig.getPropertyValue("ProgressDisplayFormat"));
			localStateSeriesConfig.setPropertyValue("TextTooltipFormat",localStateSeriesConfig.getPropertyValue("ProgressTooltipFormat"));
			localStateSeriesConfig.setPropertyValue("ProgressValueField",null);
			localStateSeriesConfig.setPropertyValue("ProgressDisplayFormat",null);
			localStateSeriesConfig.setPropertyValue("ProgressTooltipFormat",null);
			localStateSeriesConfig.setPropertyValue("ProgressMinValue",null);
			localStateSeriesConfig.setPropertyValue("ProgressMinField",null);
			localStateSeriesConfig.setPropertyValue("ProgressMaxValue",null);
			localStateSeriesConfig.setPropertyValue("ProgressMaxField",null);
			//update the selections
			updateSelections(fieldNameSettingsCache.get(fieldName));
			//fire the change
			propertyChangeSupport.firePropertyChange(PROP_KEY_FORMAT_TYPE, "ProgressBar", "Text");
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not switch from progress bar to text", e));
		}
	}

	protected void formatChanged() {
		disableListeners();
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			DisplayValueFormat displayValueFormat = (DisplayValueFormat) ((IStructuredSelection) cmbViewer_Format.getSelection()).getFirstElement();
			ValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			currFieldSettings.setDisplayValueFormat(displayValueFormat);
			// enable or disable the pattern text field
			if (currFieldSettings.isUsingPattern() == true) {
				txt_Pattern.setEnabled(true);
				if (currFieldSettings.getPattern() == null){
					currFieldSettings.setPattern("");
				}
				// reset the pattern text field
				txt_Pattern.setText(currFieldSettings.getPattern());
			}
			else {
				txt_Pattern.setEnabled(false);
				// reset the pattern text field
				txt_Pattern.setText("");
			}
			// update the DisplayFormat
			localElement.setPropertyValue(getValueFieldDisplayFormatPropertyName(), currFieldSettings.getDisplayLabelFormat(currFieldSettings.getDisplayValueFormat()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process value field display value changes", e));
		} finally {
			enableListeners();
		}
	}

	protected void patternTextChanged() {
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			// update the pattern
			currFieldSettings.setPattern(txt_Pattern.getText());
			// update the CategoryAxisFieldDisplayFormat
			localStateSeriesConfig.setPropertyValue(getValueFieldDisplayFormatPropertyName(), currFieldSettings.getDisplayLabelFormat(currFieldSettings.getDisplayValueFormat()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process value field display value pattern changes", e));
		}
		StructuredSelection selection = (StructuredSelection) cmbViewer_Format.getSelection();
		if (selection.isEmpty() == true){
			return;
		}
	}

	protected void minConstantSelected() {
		//reset the label widget
		resetError(lbl_MinValue);
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ContentValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			//update the bean
			currFieldSettings.setMinValueOption(ContentValueFieldSettingsBean.VALUE_OPTION.CONSTANT);
			currFieldSettings.setMinValue(txt_Min_Constant.getText());
			//remove minimum field reference
			localStateSeriesConfig.setPropertyValue("ProgressMinField", null);
			//enable the constant text field
			txt_Min_Constant.setEnabled(true);
			//disable the field combo box
			cmb_Min_Field.setEnabled(false);
			//check if value is valid
			SynProperty property = (SynProperty) localStateSeriesConfig.getProperty("ProgressMinValue");
			if (property.isValid(txt_Min_Constant.getText()) == false) {
				//we have a invalid value , do something
				indicateError(lbl_MinValue, property.getValidationMessage().getMessage());
				return;
			}
			//everything is OK, update the minimum constant value
			localStateSeriesConfig.setPropertyValue("ProgressMinValue", txt_Min_Constant.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process switch to constant minimum value", e));
		}
	}

	protected void minConstantChanged() {
		//reset the label widget
		resetError(lbl_MinValue);
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ContentValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			//update the bean
			currFieldSettings.setMinValue(txt_Min_Constant.getText());
			//check if value is valid
			SynProperty property = (SynProperty) localStateSeriesConfig.getProperty("ProgressMinValue");
			if (property.isValid(txt_Min_Constant.getText()) == false) {
				//we have a invalid value , do something
				indicateError(lbl_MinValue, property.getValidationMessage().getMessage());
				return;
			}
			//everything is OK, update the series config
			localStateSeriesConfig.setPropertyValue("ProgressMinValue", txt_Min_Constant.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process minimum constant changes", e));
		}
	}

	protected void minFieldSelected() {
		//reset the label widget
		resetError(lbl_MinValue);
		try {
			String minFieldName = (String) ((StructuredSelection) cmbViewer_Min_Field.getSelection()).getFirstElement();

			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ContentValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			//update the bean
			currFieldSettings.setMinValueOption(ContentValueFieldSettingsBean.VALUE_OPTION.FIELD);
			currFieldSettings.setMinValue(minFieldName);

			//remove minimum constant
			localStateSeriesConfig.setPropertyValue("ProgressMinValue", null);
			//update the minimum field reference
			localStateSeriesConfig.setPropertyValue("ProgressMinField", minFieldName);
			//disable the constant text field
			txt_Min_Constant.setEnabled(false);
			//enable the field combo box
			cmb_Min_Field.setEnabled(true);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process switch to minimum field", e));
		}
	}

	protected void minFieldChanged() {
		try {
			String minFieldName = (String) ((StructuredSelection) cmbViewer_Min_Field.getSelection()).getFirstElement();
			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ContentValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			//update the bean
			currFieldSettings.setMinValue(minFieldName);
			//update the minimum field reference
			localStateSeriesConfig.setPropertyValue("ProgressMinField", minFieldName);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process progress bar minimum field selection", e));
		}
	}

	protected void maxConstantSelected() {
		//reset the label widget
		resetError(lbl_MaxValue);
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ContentValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			//update the bean
			currFieldSettings.setMaxValueOption(ContentValueFieldSettingsBean.VALUE_OPTION.CONSTANT);
			currFieldSettings.setMaxValue(txt_Max_Constant.getText());
			//remove maximum field reference
			localStateSeriesConfig.setPropertyValue("ProgressMaxField", null);
			//enable the constant text field
			txt_Max_Constant.setEnabled(true);
			//disable the field combo box
			cmb_Max_Field.setEnabled(false);
			//check if value is valid
			SynProperty property = (SynProperty) localStateSeriesConfig.getProperty("ProgressMaxValue");
			if (property.isValid(txt_Min_Constant.getText()) == false) {
				//we have a invalid value , do something
				indicateError(lbl_MaxValue, property.getValidationMessage().getMessage());
				return;
			}
			//everything is OK, update the maximum constant value
			localStateSeriesConfig.setPropertyValue("ProgressMaxValue", txt_Max_Constant.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process switch to constant maximum value", e));
		}
	}

	protected void maxConstantChanged() {
		//reset the label widget before each call
		resetError(lbl_MaxValue);
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ContentValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			//update the bean
			currFieldSettings.setMaxValue(txt_Max_Constant.getText());
			SynProperty property = (SynProperty) localStateSeriesConfig.getProperty("ProgressMaxValue");
			if (property.isValid(txt_Max_Constant.getText()) == false) {
				//we have a invalid value , do something
				indicateError(lbl_MaxValue, property.getValidationMessage().getMessage());
				return;
			}
			localStateSeriesConfig.setPropertyValue("ProgressMaxValue", txt_Max_Constant.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process progress bar maximum constant changes", e));
		}
	}

	protected void maxFieldSelected() {
		//reset the label widget
		resetError(lbl_MaxValue);
		try {
			String maxFieldName = (String) ((StructuredSelection) cmbViewer_Max_Field.getSelection()).getFirstElement();

			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ContentValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			//update the bean
			currFieldSettings.setMaxValueOption(ContentValueFieldSettingsBean.VALUE_OPTION.FIELD);
			currFieldSettings.setMaxValue(maxFieldName);

			//remove maximum constant
			localStateSeriesConfig.setPropertyValue("ProgressMaxValue", null);
			//update the maximum field reference
			localStateSeriesConfig.setPropertyValue("ProgressMaxField", maxFieldName);
			//disable the constant text field
			txt_Max_Constant.setEnabled(false);
			//enable the field combo box
			cmb_Max_Field.setEnabled(true);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process switch to maximum field", e));
		}
	}

	protected void maxFieldChanged() {
		try {
			String maxFieldName = (String) ((StructuredSelection) cmbViewer_Max_Field.getSelection()).getFirstElement();

			String fieldName = (String) ((IStructuredSelection) cmbViewer_ValueField.getSelection()).getFirstElement();
			ContentValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			//update the bean
			currFieldSettings.setMaxValue(maxFieldName);

			localStateSeriesConfig.setPropertyValue("ProgressMaxField", maxFieldName);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process progress bar maximum field selection", e));
		}
	}

	private void setProgressBarControlsEnable(boolean enable) {
		cmb_Format.setEnabled(!enable);
		txt_Pattern.setEnabled(!enable);
		btn_Min_Constant.setEnabled(enable);
		btn_Min_Field.setEnabled(enable);
		btn_Max_Constant.setEnabled(enable);
		btn_Max_Field.setEnabled(enable);
		if (enable == false) {
			btn_Min_Constant.setSelection(enable);
			btn_Min_Field.setSelection(enable);
			txt_Min_Constant.setEnabled(enable);
			cmb_Min_Field.setEnabled(enable);

			btn_Max_Constant.setSelection(enable);
			btn_Max_Field.setSelection(enable);
			txt_Max_Constant.setEnabled(enable);
			cmb_Max_Field.setEnabled(enable);

		}
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