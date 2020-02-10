package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartCategoryFieldSettingsForm extends BaseForm {

	private Combo cmb_Field;
	private ComboViewer cmbViewer_Field;
	private ISelectionChangedListener cmbViewer_FieldSelectionChangedListener;

	private Combo cmb_DisplayValueFormatType;
	private ComboViewer cmbViewer_DisplayValueFormatType;
	private ISelectionChangedListener cmbViewer_DisplayValueFormatTypeSelectionChangedListener;

	private Text txt_DisplayValueFormatPattern;
	private boolean programmaticTextModification;
	private ModifyListener txt_DisplayValueFormatPatternModifyListener;

	private LocalActionRule localActionRule;
	private LocalDataSource localDataSource;

	private Map<String, CategoryFieldSettingsBean> fieldNameSettingsCache;

	private String categoryFieldPropertyName;
	private String categoryFieldDisplayFormatPropertyName;

	public ChartCategoryFieldSettingsForm(FormToolkit formToolKit, Composite parent) {
		super("Category Field Settings", formToolKit, parent, true);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2, false));

		// category field Value
		createLabel(formComposite, "Category Field:", SWT.NONE);
		// category field drop down
		cmb_Field = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// category field viewer
		cmbViewer_Field = new ComboViewer(cmb_Field);
		cmbViewer_Field.setContentProvider(new ArrayContentProvider());

		// display value pattern type label
		createLabel(formComposite, "Format:", SWT.NONE);
		// display value pattern drop down
		cmb_DisplayValueFormatType = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_DisplayValueFormatType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// display value viewer
		cmbViewer_DisplayValueFormatType = new ComboViewer(cmb_DisplayValueFormatType);
		cmbViewer_DisplayValueFormatType.setContentProvider(new ArrayContentProvider());
		cmbViewer_DisplayValueFormatType.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof DisplayValueFormat) {
					return ((DisplayValueFormat) element).getLabel();
				}
				return super.getText(element);
			}

		});

		// display value pattern label
		createLabel(formComposite, "Pattern:", SWT.NONE);
		// display value pattern text
		txt_DisplayValueFormatPattern = createText(formComposite, null, SWT.SINGLE);
		txt_DisplayValueFormatPattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		fieldNameSettingsCache = new HashMap<String, CategoryFieldSettingsBean>();
		localActionRule = null;
		localDataSource = null;
		categoryFieldPropertyName = "CategoryColumnField";
		categoryFieldDisplayFormatPropertyName = "CategoryColumnFieldDisplayFormat";
		if (newLocalElement != null){
			localActionRule = (LocalActionRule) newLocalElement.getElement(BEViewsElementNames.ACTION_RULE);
			localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
			// seriesconfig's parent is visualization whose parent is
			if (newLocalElement.getParent().getParent().getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE).equals("Table") == true) {
				categoryFieldPropertyName = "CategoryColumnField";
				categoryFieldDisplayFormatPropertyName = "CategoryColumnFieldDisplayFormat";
			} else {
				categoryFieldPropertyName = "CategoryAxisField";
				categoryFieldDisplayFormatPropertyName = "CategoryAxisFieldDisplayFormat";
			}
		}
	}

	@Override
	protected void doEnableListeners() {
		if (cmbViewer_FieldSelectionChangedListener == null) {
			cmbViewer_FieldSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					fieldSelectionChanged();

				}

			};
		}
		cmbViewer_Field.addSelectionChangedListener(cmbViewer_FieldSelectionChangedListener);

		if (cmbViewer_DisplayValueFormatTypeSelectionChangedListener == null) {
			cmbViewer_DisplayValueFormatTypeSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					if (programmaticTextModification == false) {
						formatTypeSelectionChanged();
					}
				}

			};
		}
		cmbViewer_DisplayValueFormatType.addSelectionChangedListener(cmbViewer_DisplayValueFormatTypeSelectionChangedListener);

		if (txt_DisplayValueFormatPatternModifyListener == null) {
			txt_DisplayValueFormatPatternModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					if (programmaticTextModification == false) {
						patternModified();
					}
				}

			};
		}
		txt_DisplayValueFormatPattern.addModifyListener(txt_DisplayValueFormatPatternModifyListener);

		programmaticTextModification = false;
	}

	@Override
	protected void doDisableListeners() {
		cmbViewer_Field.removeSelectionChangedListener(cmbViewer_FieldSelectionChangedListener);
		cmbViewer_DisplayValueFormatType.removeSelectionChangedListener(cmbViewer_DisplayValueFormatTypeSelectionChangedListener);
		txt_DisplayValueFormatPattern.removeModifyListener(txt_DisplayValueFormatPatternModifyListener);
	}

	@Override
	public void refreshEnumerations() {
		try {
			// set the category fields
			List<Object> categoryFields = localDataSource == null ? Collections.emptyList() : localDataSource.getEnumerations(LocalDataSource.ENUM_GROUP_BY_FIELD);
			cmbViewer_Field.setInput(categoryFields);
			// create the settings for all category fields
			for (Object categoryFieldAsObj : categoryFields) {
				String categoryField = (String) categoryFieldAsObj;
				// find the field and its data type
				String dataType = localDataSource.getFieldByName(LocalDataSource.ENUM_GROUP_BY_FIELD, categoryField).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
				fieldNameSettingsCache.put(categoryField, new CategoryFieldSettingsBean(categoryField,dataType));
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not enumerate "+title, e));
		}
	}

	@Override
	public void refreshSelections() {
		programmaticTextModification = true;
		if (localElement == null){
			cmbViewer_Field.setSelection(StructuredSelection.EMPTY);
			cmbViewer_DisplayValueFormatType.setSelection(StructuredSelection.EMPTY);
			txt_DisplayValueFormatPattern.setText("");
			return;
		}
		String propertyName = categoryFieldPropertyName;
		try {
			String fieldName = localElement.getPropertyValue(propertyName);
			cmbViewer_Field.setSelection(new StructuredSelection(fieldName));
			CategoryFieldSettingsBean fieldSettings = fieldNameSettingsCache.get(fieldName);
			// get supported formats for the data types
			List<DisplayValueFormat> displayValueFormats = fieldSettings == null ? new ArrayList<DisplayValueFormat>() : fieldSettings.getDisplayValueFormats();
			// update the format style drop down
			cmbViewer_DisplayValueFormatType.setInput(displayValueFormats);
			if (displayValueFormats.size() != 0) {
				// get the display label format value
				propertyName = categoryFieldDisplayFormatPropertyName;
				String displayLabelFormat = localElement.getPropertyValue(propertyName);
				// update the field settings to use the current display label format
				fieldSettings.update(displayLabelFormat);
				//update the format type to current display value format
				cmbViewer_DisplayValueFormatType.setSelection(fieldSettings.getDisplayValueFormat() == null ? StructuredSelection.EMPTY : new StructuredSelection(fieldSettings.getDisplayValueFormat()));
				// enable or disable the pattern text field
				txt_DisplayValueFormatPattern.setEnabled(fieldSettings.isUsingPattern());
				// update the pattern
				txt_DisplayValueFormatPattern.setText(fieldSettings.getPattern());
			} else {
				// disable the format type drop down
				cmb_DisplayValueFormatType.setEnabled(false);
				// reset the pattern text field
				txt_DisplayValueFormatPattern.setText("");
				// disable the pattern text field
				txt_DisplayValueFormatPattern.setEnabled(false);
			}
			if (fieldSettings != null) {
				// update the current settings for threshold & threshold unit
				fieldSettings.setThreshold(localActionRule.getPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD));
				fieldSettings.setThresholdUnit(localActionRule.getPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD_UNIT));
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not read '" + propertyName + "'", e));
		} finally {
			programmaticTextModification = false;
		}
	}

	private void fieldSelectionChanged() {
		programmaticTextModification = true;
		try {
			// get current field name and its state
			String currentFieldName = localElement.getPropertyValue(categoryFieldPropertyName);
			CategoryFieldSettingsBean currentFieldSettings = this.fieldNameSettingsCache.get(currentFieldName);

			if (currentFieldSettings != null) {
				// update current settings to latest threshold/threshold unit settings (they could have been changed)
				currentFieldSettings.setThreshold(localActionRule.getPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD));
				currentFieldSettings.setThresholdUnit(localActionRule.getPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD_UNIT));
			}
			// get selected field name
			String selectedFieldName = (String) ((IStructuredSelection) cmbViewer_Field.getSelection()).getFirstElement();
			// get selected field settings
			CategoryFieldSettingsBean selectedFieldSettings = this.fieldNameSettingsCache.get(selectedFieldName);
			if (selectedFieldSettings.haveSameDisplayValueFormats(currentFieldSettings) == true) {
				//we have equivalent display value formats, no need to update the style drop down or pattern field
				//transfer the display value format and pattern
				selectedFieldSettings.setDisplayValueFormat(currentFieldSettings.getDisplayValueFormat());
				selectedFieldSettings.setPattern(currentFieldSettings.getPattern());
				//transfer the threshold & threshold unit
				selectedFieldSettings.setThreshold(currentFieldSettings.getThreshold());
				selectedFieldSettings.setThresholdUnit(currentFieldSettings.getThresholdUnit());
			}
			else {
				//we don't have a match, update the widgets for the selected field settings
				// update the format style drop down
				cmbViewer_DisplayValueFormatType.setInput(selectedFieldSettings.getDisplayValueFormats());
				DisplayValueFormat selectedDisplayValueFormat = selectedFieldSettings.getDisplayValueFormat();
				if (selectedDisplayValueFormat == null){
					selectedDisplayValueFormat = selectedFieldSettings.getDefaultDisplayValueFormat();
					selectedFieldSettings.setDisplayValueFormat(selectedDisplayValueFormat);
				}
				//update the format style drop down selection
				cmbViewer_DisplayValueFormatType.setSelection(selectedDisplayValueFormat == null ? StructuredSelection.EMPTY : new StructuredSelection(selectedDisplayValueFormat));
				//enable/disable the format style drop down
				cmb_DisplayValueFormatType.setEnabled(selectedFieldSettings.getDisplayValueFormats().isEmpty() == false);
				// enable/disable the pattern text field
				txt_DisplayValueFormatPattern.setEnabled(selectedFieldSettings.isUsingPattern());
				// update the pattern text
				txt_DisplayValueFormatPattern.setText(selectedFieldSettings.getPattern() == null ? "" : selectedFieldSettings.getPattern());
			}
			// update the category display label format
			localElement.setPropertyValue(categoryFieldDisplayFormatPropertyName, selectedFieldSettings.getDisplayLabelFormat(selectedFieldSettings.getDisplayValueFormat()));
			// update the threshold
			localActionRule.setPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD, selectedFieldSettings.getThreshold());
			// update the threshold unit
			localActionRule.setPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD_UNIT, selectedFieldSettings.getThresholdUnit());
			// update the category axis field (we trigger this last so that all dependent values are uptodate
			localElement.setPropertyValue(categoryFieldPropertyName, selectedFieldName);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process category field settings change", e));
		} finally {
			programmaticTextModification = false;
		}
	}

	private void formatTypeSelectionChanged() {
		programmaticTextModification = true;
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_Field.getSelection()).getFirstElement();
			DisplayValueFormat displayValueFormat = (DisplayValueFormat) ((IStructuredSelection) cmbViewer_DisplayValueFormatType.getSelection()).getFirstElement();
			CategoryFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			currFieldSettings.setDisplayValueFormat(displayValueFormat);
			// enable or disable the pattern text field
			if (currFieldSettings.isUsingPattern() == true) {
				txt_DisplayValueFormatPattern.setEnabled(true);
				if (currFieldSettings.getPattern() == null){
					currFieldSettings.setPattern("");
				}
				// reset the pattern text field
				txt_DisplayValueFormatPattern.setText(currFieldSettings.getPattern());
			}
			else {
				txt_DisplayValueFormatPattern.setEnabled(false);
				// reset the pattern text field
				txt_DisplayValueFormatPattern.setText("");
			}
			// update the CategoryAxisFieldDisplayFormat
			localElement.setPropertyValue(categoryFieldDisplayFormatPropertyName, currFieldSettings.getDisplayLabelFormat(currFieldSettings.getDisplayValueFormat()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process category field display value changes", e));
		} finally {
			programmaticTextModification = false;
		}
	}

	private void patternModified() {
		try {
			String fieldName = (String) ((IStructuredSelection) cmbViewer_Field.getSelection()).getFirstElement();
			CategoryFieldSettingsBean currFieldSettings = fieldNameSettingsCache.get(fieldName);
			// update the pattern
			currFieldSettings.setPattern(txt_DisplayValueFormatPattern.getText());
			// update the CategoryAxisFieldDisplayFormat
			localElement.setPropertyValue(categoryFieldDisplayFormatPropertyName, currFieldSettings.getDisplayLabelFormat(currFieldSettings.getDisplayValueFormat()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process category field display value pattern changes", e));
		}
	}

}