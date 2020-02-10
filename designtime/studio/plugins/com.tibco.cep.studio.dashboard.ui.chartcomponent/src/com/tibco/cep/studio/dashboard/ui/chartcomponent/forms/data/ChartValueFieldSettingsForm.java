package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.FontStyleWidget;
import com.tibco.cep.studio.dashboard.ui.forms.LocalElementLabelProvider;
import com.tibco.cep.studio.dashboard.ui.forms.PropertyEnumerationContentProvider;
import com.tibco.cep.studio.dashboard.ui.forms.ValueFieldSettingsBean;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartValueFieldSettingsForm extends BaseForm {

	private Combo cmb_Field;
	private ComboViewer cmbViewer_Field;
	private ISelectionChangedListener cmb_FieldSelectionListener;

	protected Button btn_ShowDisplayLabel;
	private SelectionListener btn_ShowDisplayLabelSelectionListener;

	private Combo cmb_DisplayLabelFontSize;
	private ComboViewer cmbViewer_DisplayLabelFontSize;
	private ISelectionChangedListener cmb_DisplayLabelFontSizeSelectionChangedListener;

	private FontStyleWidget fontStyleWidget;
	private PropertyChangeListener fontStyleWidget_PropertyChangeListener;

	private Combo cmb_DisplayLabelFormatType;
	private ComboViewer cmbViewer_DisplayLabelFormatType;
	private ISelectionChangedListener cmbViewer_DisplayLabelFormatTypeSelectionChangedListener;

	private Text txt_DisplayLabelFormatPattern;
	private ModifyListener txt_DisplayLabelFormatPatternModifyListener;

	private LocalActionRule localActionRule;
	private LocalDataSource localDataSource;

	private Map<String, ValueFieldSettingsBean> fieldNameSettingsCache;

	public ChartValueFieldSettingsForm(FormToolkit formToolKit, Composite parent) {
		super("Value Field Settings", formToolKit, parent, true);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2, false));

		// value field Value
		createLabel(formComposite, "Value Field:", SWT.NONE);
		// value field drop down
		cmb_Field = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Field.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// hook up control to model
		cmbViewer_Field = new ComboViewer(cmb_Field);
		cmbViewer_Field.setContentProvider(new ArrayContentProvider());
		cmbViewer_Field.setLabelProvider(new LocalElementLabelProvider(false));

		//show display value button
		btn_ShowDisplayLabel = createButton(formComposite, "Show Data Value", SWT.CHECK);
		btn_ShowDisplayLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Composite cmp_DisplayLabelSettingsComposite = createDisplayLabelComposite();

		GridData cmp_DataValueSettingsCompositeGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		cmp_DataValueSettingsCompositeGridData.horizontalIndent = 18;
		cmp_DisplayLabelSettingsComposite.setLayoutData(cmp_DataValueSettingsCompositeGridData);

	}

	protected Composite createDisplayLabelComposite() {
		Composite cmp_DataValueSettingsComposite = createComposite(formComposite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cmp_DataValueSettingsComposite.setLayout(layout);

		//display value font size label
		createLabel(cmp_DataValueSettingsComposite, "Font Size:", SWT.NONE);
		//display value font size drop down
		cmb_DisplayLabelFontSize = createCombo(cmp_DataValueSettingsComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_DisplayLabelFontSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		cmbViewer_DisplayLabelFontSize = new ComboViewer(cmb_DisplayLabelFontSize);
		cmbViewer_DisplayLabelFontSize.setContentProvider(new PropertyEnumerationContentProvider());

		//display value font style
		createLabel(cmp_DataValueSettingsComposite, "Font Style:", SWT.NONE);
		//display value font style widget
		fontStyleWidget = new FontStyleWidget(formToolKit,cmp_DataValueSettingsComposite, SWT.NONE);
		fontStyleWidget.setFontStyle(FontStyleEnum.NORMAL);

		//display value pattern type label
		createLabel(cmp_DataValueSettingsComposite, "Format:", SWT.NONE);
		//display value pattern drop down
		cmb_DisplayLabelFormatType = createCombo(cmp_DataValueSettingsComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_DisplayLabelFormatType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		cmbViewer_DisplayLabelFormatType = new ComboViewer(cmb_DisplayLabelFormatType);
		cmbViewer_DisplayLabelFormatType.setContentProvider(new ArrayContentProvider());
		cmbViewer_DisplayLabelFormatType.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof DisplayValueFormat) {
					return ((DisplayValueFormat) element).getLabel();
				}
				return super.getText(element);
			}

		});

		//display value pattern label
		createLabel(cmp_DataValueSettingsComposite, "Pattern:", SWT.NONE);
		//display value pattern text
		txt_DisplayLabelFormatPattern = createText(cmp_DataValueSettingsComposite, null, SWT.SINGLE);
		txt_DisplayLabelFormatPattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		return cmp_DataValueSettingsComposite;
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		fieldNameSettingsCache = new HashMap<String, ValueFieldSettingsBean>();
		localActionRule = null;
		localDataSource = null;
		if (newLocalElement != null) {
			localActionRule = (LocalActionRule) newLocalElement.getElement(BEViewsElementNames.ACTION_RULE);
			localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
		}
	}

	@Override
	protected void doEnableListeners() {
		if (cmb_FieldSelectionListener == null) {
			cmb_FieldSelectionListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					try {
						disableListeners();
						fieldSelectionChanged();
					} finally {
						enableListeners();
					}
				}

			};
		}
		cmbViewer_Field.addSelectionChangedListener(cmb_FieldSelectionListener);

		if (btn_ShowDisplayLabelSelectionListener == null) {
			btn_ShowDisplayLabelSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						disableListeners();
						showDisplayClicked();
					} finally {
						enableListeners();
					}
				}

			};
		}
		btn_ShowDisplayLabel.addSelectionListener(btn_ShowDisplayLabelSelectionListener);

		if (cmb_DisplayLabelFontSizeSelectionChangedListener == null){
			cmb_DisplayLabelFontSizeSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					try {
						disableListeners();
						displayLabelFontSizeSelectionChanged();
					} finally {
						enableListeners();
					}
				}

			};
		}
		cmbViewer_DisplayLabelFontSize.addSelectionChangedListener(cmb_DisplayLabelFontSizeSelectionChangedListener);

		if (fontStyleWidget_PropertyChangeListener == null){
			fontStyleWidget_PropertyChangeListener = new PropertyChangeListener(){

				@Override
				public void propertyChange(java.beans.PropertyChangeEvent evt) {
					try {
						disableListeners();
						displayLabelFontStyleChanged();
					}  finally {
						enableListeners();
					}
				}


			};
		}
		fontStyleWidget.addPropertyChangeListener(fontStyleWidget_PropertyChangeListener);

		if (cmbViewer_DisplayLabelFormatTypeSelectionChangedListener == null) {
			cmbViewer_DisplayLabelFormatTypeSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					try {
						disableListeners();
						formatTypeSelectionChanged();
					}  finally {
						enableListeners();
					}
				}

			};
		}
		cmbViewer_DisplayLabelFormatType.addSelectionChangedListener(cmbViewer_DisplayLabelFormatTypeSelectionChangedListener);

		if (txt_DisplayLabelFormatPatternModifyListener == null) {
			txt_DisplayLabelFormatPatternModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					try {
						disableListeners();
						patternModified();
					}  finally {
						enableListeners();
					}
				}

			};
		}
		txt_DisplayLabelFormatPattern.addModifyListener(txt_DisplayLabelFormatPatternModifyListener);
	}

	@Override
	protected void doDisableListeners() {
		cmbViewer_Field.removeSelectionChangedListener(cmb_FieldSelectionListener);
		btn_ShowDisplayLabel.removeSelectionListener(btn_ShowDisplayLabelSelectionListener);
		cmbViewer_DisplayLabelFontSize.removeSelectionChangedListener(cmb_DisplayLabelFontSizeSelectionChangedListener);
		fontStyleWidget.removePropertyChangeListener(fontStyleWidget_PropertyChangeListener);
		cmbViewer_DisplayLabelFormatType.removeSelectionChangedListener(cmbViewer_DisplayLabelFormatTypeSelectionChangedListener);
		txt_DisplayLabelFormatPattern.removeModifyListener(txt_DisplayLabelFormatPatternModifyListener);
	}

	@Override
	public void refreshEnumerations() {
		try {
			// set the value fields
			List<Object> valueFields = localDataSource == null ? Collections.emptyList() : localDataSource.getEnumerations(LocalDataSource.ENUM_FIELD);
			cmbViewer_Field.setInput(valueFields);
			// create the settings for all category fields
			for (Object valueFieldAsObj : valueFields) {
				String valueField = (String) valueFieldAsObj;
				// find the field and its data type
				String dataType = localDataSource.getFieldByName(LocalDataSource.ENUM_FIELD, valueField).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
				fieldNameSettingsCache.put(valueField, new ValueFieldSettingsBean(valueField,dataType));
			}
			//font size enumeration
			cmbViewer_DisplayLabelFontSize.setInput(localElement == null ? Collections.emptyList() : localElement.getProperty(getFontSizePropertyName()));
			//display format enumeration is based selection
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh enumerations in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	@Override
	public void refreshSelections() {
		cmbViewer_Field.setSelection(StructuredSelection.EMPTY);
		btn_ShowDisplayLabel.setSelection(false);
		cmbViewer_DisplayLabelFontSize.setSelection(StructuredSelection.EMPTY);
		cmbViewer_DisplayLabelFormatType.setSelection(StructuredSelection.EMPTY);
		txt_DisplayLabelFormatPattern.setText("");
		if (localElement != null) {
			String propertyName = getFieldPropertyName();
			try {
				String fieldName = localElement.getPropertyValue(propertyName);
				if (fieldName == null || fieldName.trim().length() == 0) {
					cmbViewer_Field.setSelection(StructuredSelection.EMPTY);
				} else {
					cmbViewer_Field.setSelection(new StructuredSelection(fieldName));
				}

				propertyName = getShowDataLabelPropertyName();
				boolean showDataValue = Boolean.valueOf(localElement.getPropertyValue(propertyName));
				//update the show data value button
				btn_ShowDisplayLabel.setSelection(showDataValue);

				//update the font size
				propertyName = getFontSizePropertyName();
				cmbViewer_DisplayLabelFontSize.setSelection(new StructuredSelection(localElement.getPropertyValue(propertyName)));

				//update the font style
				propertyName = getFontStylePropertyName();
				String fontStyle = localElement.getPropertyValue(propertyName);
				FontStyleEnum fontStyleNum = FontStyleEnum.NORMAL;
				for (FontStyleEnum styleEnum : FontStyleEnum.VALUES) {
					if (styleEnum.getLiteral().equals(fontStyle) == true){
						fontStyleNum = styleEnum;
						break;
					}
				}
				fontStyleWidget.setFontStyle(fontStyleNum);

				//update the format styles
				ValueFieldSettingsBean fieldSettings = fieldNameSettingsCache.get(fieldName);
				// get supported formats for the data types
				List<DisplayValueFormat> displayValueFormats = fieldSettings == null ? new ArrayList<DisplayValueFormat>() : fieldSettings.getDisplayValueFormats();
				// update the format style drop down
				cmbViewer_DisplayLabelFormatType.setInput(displayValueFormats);
				if (displayValueFormats.size() != 0) {
					// get the display label format value
					propertyName = getDisplayLabelPropertyName();
					String displayLabelFormat = localElement.getPropertyValue(propertyName);
					// update the field settings to use the current display label format
					fieldSettings.update(displayLabelFormat);
					//update the format type to current display value format
					cmbViewer_DisplayLabelFormatType.setSelection(fieldSettings.getDisplayValueFormat() == null ? StructuredSelection.EMPTY : new StructuredSelection(fieldSettings.getDisplayValueFormat()));
					// enable or disable the pattern text field
					txt_DisplayLabelFormatPattern.setEnabled(fieldSettings.isUsingPattern());
					// update the pattern
					txt_DisplayLabelFormatPattern.setText(fieldSettings.getPattern());
				} else {
					// disable the format type drop down
					cmb_DisplayLabelFormatType.setEnabled(false);
					// reset the pattern text field
					txt_DisplayLabelFormatPattern.setText("");
					// disable the pattern text field
					txt_DisplayLabelFormatPattern.setEnabled(false);
				}

				//enable/disable label specific widgets
				cmb_DisplayLabelFontSize.setEnabled(showDataValue);
				fontStyleWidget.setEnabled(showDataValue);
				cmb_DisplayLabelFormatType.setEnabled(showDataValue);
				if (showDataValue == false){
					// disable the pattern text field
					txt_DisplayLabelFormatPattern.setEnabled(false);
				}
			} catch (Exception e) {
				log(new Status(IStatus.ERROR,getPluginId(),"could not read "+propertyName+" from "+localElement,e));
				disableAll();
			}
		}
	}

	private void fieldSelectionChanged() {
		disableListeners();
		try {
			// get current field name and its state
			String currentFieldName = localElement.getPropertyValue(getFieldPropertyName());
			ValueFieldSettingsBean currentFieldSettings = this.fieldNameSettingsCache.get(currentFieldName);

			// get selected field name
			String selectedFieldName = (String) ((IStructuredSelection) cmbViewer_Field.getSelection()).getFirstElement();
			// get selected field settings
			ValueFieldSettingsBean selectedFieldSettings = this.fieldNameSettingsCache.get(selectedFieldName);
			if (selectedFieldSettings.haveSameDisplayValueFormats(currentFieldSettings) == true) {
				//we have equivalent display value formats, no need to update the style drop down or pattern field
				//transfer the display value format and pattern
				selectedFieldSettings.setDisplayValueFormat(currentFieldSettings.getDisplayValueFormat());
				selectedFieldSettings.setPattern(currentFieldSettings.getPattern());
			}
			else {
				//we don't have a match, update the widgets for the selected field settings
				// update the format style drop down
				cmbViewer_DisplayLabelFormatType.setInput(selectedFieldSettings.getDisplayValueFormats());
				DisplayValueFormat selectedDisplayValueFormat = selectedFieldSettings.getDisplayValueFormat();
				if (selectedDisplayValueFormat == null){
					selectedDisplayValueFormat = selectedFieldSettings.getDefaultDisplayValueFormat();
					selectedFieldSettings.setDisplayValueFormat(selectedDisplayValueFormat);
				}
				//update the format style drop down selection
				cmbViewer_DisplayLabelFormatType.setSelection(selectedDisplayValueFormat == null ? StructuredSelection.EMPTY : new StructuredSelection(selectedDisplayValueFormat));
				//enable/disable the format style drop down
				cmb_DisplayLabelFormatType.setEnabled(selectedFieldSettings.getDisplayValueFormats().isEmpty() == false);
				// enable/disable the pattern text field
				txt_DisplayLabelFormatPattern.setEnabled(selectedFieldSettings.isUsingPattern());
				// update the pattern text
				txt_DisplayLabelFormatPattern.setText(selectedFieldSettings.getPattern() == null ? "" : selectedFieldSettings.getPattern());
			}
			// update the category display label format
			localElement.setPropertyValue(getDisplayLabelPropertyName(), selectedFieldSettings.getDisplayLabelFormat(selectedFieldSettings.getDisplayValueFormat()));
			// update the category axis field (we trigger this last so that all dependent values are uptodate
			localElement.setPropertyValue(getFieldPropertyName(), selectedFieldName);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process value field settings change", e));
		} finally {
			enableListeners();
		}
	}

	protected void showDisplayClicked(){
		try {
			boolean displayEnabled = btn_ShowDisplayLabel.getSelection();
			localElement.setPropertyValue(getShowDataLabelPropertyName(), String.valueOf(displayEnabled));
			cmb_DisplayLabelFontSize.setEnabled(displayEnabled);
			fontStyleWidget.setEnabled(displayEnabled);
			cmb_DisplayLabelFormatType.setEnabled(displayEnabled);
			if (displayEnabled == false) {
				txt_DisplayLabelFormatPattern.setEnabled(displayEnabled);
			}
			else {
				IStructuredSelection selection = (IStructuredSelection) cmbViewer_DisplayLabelFormatType.getSelection();
				DisplayValueFormat displayValueFormat = (DisplayValueFormat) selection.getFirstElement();
				txt_DisplayLabelFormatPattern.setEnabled(displayValueFormat.isPattern());
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update 'Show Data Value' in "+this,e));
		}
	}

	private void displayLabelFontSizeSelectionChanged(){
		try {
			IStructuredSelection selection = (IStructuredSelection) cmbViewer_DisplayLabelFontSize.getSelection();
			String fontSize = String.valueOf(selection.getFirstElement());
			localElement.setPropertyValue(getFontSizePropertyName(), fontSize);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update 'Font Size' in "+this,e));
		}
	}

	private void displayLabelFontStyleChanged(){
		try {
			localElement.setPropertyValue(getFontStylePropertyName(), fontStyleWidget.getFontStyle().toString());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update 'Font Style' in "+this,e));
		}
	}

	private void formatTypeSelectionChanged() {
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_Field.getSelection()).getFirstElement();
			DisplayValueFormat displayValueFormat = (DisplayValueFormat) ((IStructuredSelection) cmbViewer_DisplayLabelFormatType.getSelection()).getFirstElement();
			ValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			currFieldSettings.setDisplayValueFormat(displayValueFormat);
			// enable or disable the pattern text field
			if (currFieldSettings.isUsingPattern() == true) {
				txt_DisplayLabelFormatPattern.setEnabled(true);
				if (currFieldSettings.getPattern() == null){
					currFieldSettings.setPattern("");
				}
				// reset the pattern text field
				txt_DisplayLabelFormatPattern.setText(currFieldSettings.getPattern());
			}
			else {
				txt_DisplayLabelFormatPattern.setEnabled(false);
				// reset the pattern text field
				txt_DisplayLabelFormatPattern.setText("");
			}
			// update the CategoryAxisFieldDisplayFormat
			localElement.setPropertyValue(getDisplayLabelPropertyName(), currFieldSettings.getDisplayLabelFormat(currFieldSettings.getDisplayValueFormat()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process value field display value changes", e));
		}
	}

	private void patternModified() {
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_Field.getSelection()).getFirstElement();
			ValueFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			// update the pattern
			currFieldSettings.setPattern(txt_DisplayLabelFormatPattern.getText());
			// update the CategoryAxisFieldDisplayFormat
			localElement.setPropertyValue(getDisplayLabelPropertyName(), currFieldSettings.getDisplayLabelFormat(currFieldSettings.getDisplayValueFormat()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process value field display value pattern changes", e));
		}
	}

	protected String getFieldPropertyName() {
		return "ValueAxisField";
	}

	protected String getFontStylePropertyName() {
		return "ValueLabelFontStyle";
	}

	protected String getFontSizePropertyName() {
		return "ValueLabelFontSize";
	}

	protected String getDisplayLabelPropertyName() {
		return "ValueAxisFieldDisplayFormat";
	}

	protected String getShowDataLabelPropertyName() {
		return "ShowDataValue";
	}
}