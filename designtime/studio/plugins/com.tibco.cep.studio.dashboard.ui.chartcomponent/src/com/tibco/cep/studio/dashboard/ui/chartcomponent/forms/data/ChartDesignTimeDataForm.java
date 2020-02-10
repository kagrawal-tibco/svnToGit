package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import java.util.Arrays;

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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartDesignTimeDataForm extends BaseForm {

	private List lst_categoryFields;
	private SelectionListener lst_categoryFieldsSelectionListener;

	private Button btn_AddCategoryField;
	private SelectionListener btn_AddCategoryFieldSelectionListener;

	private Button btn_DeleteCategoryField;
	private SelectionListener btn_DeleteCategoryFieldSelectionListener;

	private Button btn_UpCategoryField;
	private SelectionListener btn_UpCategoryFieldSelectionListener;

	private Button btn_DownCategoryField;
	private SelectionListener btn_DownCategoryFieldSelectionListener;

	private Text txt_CategoryField;
	private ModifyListener txt_CategoryFieldModifyListener;

	private Text txt_minValueField;
	private ModifyListener txt_minValueFieldModifyListener;

	private Text txt_maxValueField;
	private ModifyListener txt_maxValueFieldModifyListener;

	private int orientation;

	public ChartDesignTimeDataForm(FormToolkit formToolKit, Composite parent, int orientation) {
		super("Preview Settings", formToolKit, parent, false);
		if (orientation != SWT.VERTICAL && orientation != SWT.HORIZONTAL) {
			throw new IllegalArgumentException("Invalid orientation");
		}
		this.orientation = orientation;

	}

	@Override
	public void init() {
		if (orientation == SWT.HORIZONTAL) {
			formComposite.setLayout(new GridLayout(2, false));
		}
		else {
			formComposite.setLayout(new GridLayout(1, true));
		}

		//category field settings
		Group categoryFieldGroup = createGroup(formComposite, "Category Fields", SWT.NONE);
		categoryFieldGroup.setLayout(new GridLayout(4,false));

		//category field listing
		lst_categoryFields = createList(categoryFieldGroup, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE);
		GridData lst_CategoryFieldsLayoutData = null;
		if (orientation == SWT.HORIZONTAL) {
			lst_CategoryFieldsLayoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		}
		else {
			lst_CategoryFieldsLayoutData = new GridData(SWT.FILL, SWT.FILL, false, true);
		}
		lst_CategoryFieldsLayoutData.widthHint = 100;
		lst_categoryFields.setLayoutData(lst_CategoryFieldsLayoutData);

		//category field buttons
		Composite buttonsComposite = createComposite(categoryFieldGroup, SWT.NONE);
		FillLayout buttonsCompositeLayout = new FillLayout(SWT.VERTICAL);
		buttonsCompositeLayout.spacing = 5;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		btn_AddCategoryField = createButton(buttonsComposite, "Add", SWT.PUSH);
		btn_DeleteCategoryField = createButton(buttonsComposite, "Delete", SWT.PUSH);
		btn_UpCategoryField = createButton(buttonsComposite, "Up", SWT.PUSH);
		btn_DownCategoryField = createButton(buttonsComposite, "Down", SWT.PUSH);
		GridData buttonsCompositeLayoutData = null;
		if (orientation == SWT.HORIZONTAL) {
			buttonsCompositeLayoutData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		}
		else {
			buttonsCompositeLayoutData = new GridData(SWT.CENTER, SWT.CENTER, false, true);
		}
		buttonsCompositeLayoutData.widthHint = 75;
		buttonsComposite.setLayoutData(buttonsCompositeLayoutData);

		//category entry label
		Label lbl_CategoryName = createLabel(categoryFieldGroup, "Category Name:", SWT.END);
		lbl_CategoryName.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false));

		//category entry field
		txt_CategoryField = createText(categoryFieldGroup, null, SWT.SINGLE);
		txt_CategoryField.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,false));

		categoryFieldGroup.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));

		//value field settings
		Group valueFieldGroup = createGroup(formComposite, "Value Field Range", SWT.NONE);
		valueFieldGroup.setLayout(new GridLayout(2,false));
		valueFieldGroup.setToolTipText("All values are generated randomly between the specified range");

		createLabel(valueFieldGroup, "Minimum Data Value:", SWT.END);
		txt_minValueField = createText(valueFieldGroup, null, SWT.SINGLE);
		txt_minValueField.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));

		createLabel(valueFieldGroup, "Maximum Data Value:", SWT.END);
		txt_maxValueField = createText(valueFieldGroup, null, SWT.SINGLE);
		txt_maxValueField.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));

		valueFieldGroup.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));

	}

	@Override
	protected void doEnableListeners() {
		if (lst_categoryFieldsSelectionListener == null) {
			lst_categoryFieldsSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					categoryFieldListSelectionChanged();

				}

			};
		}
		lst_categoryFields.addSelectionListener(lst_categoryFieldsSelectionListener);

		if (btn_AddCategoryFieldSelectionListener == null) {
			btn_AddCategoryFieldSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnAddClicked();
				}

			};
		}
		btn_AddCategoryField.addSelectionListener(btn_AddCategoryFieldSelectionListener);

		if (btn_DeleteCategoryFieldSelectionListener == null) {
			btn_DeleteCategoryFieldSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnDeleteClicked();
				}

			};
		}
		btn_DeleteCategoryField.addSelectionListener(btn_DeleteCategoryFieldSelectionListener);

		if (btn_UpCategoryFieldSelectionListener == null) {
			btn_UpCategoryFieldSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnUpClicked();
				}

			};
		}
		btn_UpCategoryField.addSelectionListener(btn_UpCategoryFieldSelectionListener);

		if (btn_DownCategoryFieldSelectionListener == null) {
			btn_DownCategoryFieldSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					btnDownClicked();
				}

			};
		}
		btn_DownCategoryField.addSelectionListener(btn_DownCategoryFieldSelectionListener);

		if (txt_CategoryFieldModifyListener == null) {
			txt_CategoryFieldModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					categoryFieldTextModified();

				}

			};
		}
		txt_CategoryField.addModifyListener(txt_CategoryFieldModifyListener);

		if (txt_minValueFieldModifyListener == null) {
			txt_minValueFieldModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					minValueFieldTextModified();
				}

			};
		}
		txt_minValueField.addModifyListener(txt_minValueFieldModifyListener);

		if (txt_maxValueFieldModifyListener == null) {
			txt_maxValueFieldModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					maxValueFieldTextModified();
				}

			};
		}
		txt_maxValueField.addModifyListener(txt_maxValueFieldModifyListener);
	}

	@Override
	protected void doDisableListeners() {
		lst_categoryFields.removeSelectionListener(lst_categoryFieldsSelectionListener);
		btn_AddCategoryField.removeSelectionListener(btn_AddCategoryFieldSelectionListener);
		btn_DeleteCategoryField.removeSelectionListener(btn_DeleteCategoryFieldSelectionListener);
		btn_UpCategoryField.removeSelectionListener(btn_UpCategoryFieldSelectionListener);
		btn_DownCategoryField.removeSelectionListener(btn_DownCategoryFieldSelectionListener);
		txt_CategoryField.removeModifyListener(txt_CategoryFieldModifyListener);
		txt_minValueField.removeModifyListener(txt_minValueFieldModifyListener);
		txt_maxValueField.removeModifyListener(txt_maxValueFieldModifyListener);
	}

	@Override
	public void refreshEnumerations() {
		// do nothing
	}

	@Override
	public void refreshSelections() {
		lst_categoryFields.setItems(new String[0]);
		txt_minValueField.setText("");
		txt_maxValueField.setText("");
		if (localElement != null) {
			String propertyName = "DesignCategories";
			try {
				SynProperty property = (SynProperty) localElement.getProperty(propertyName);
				//force the parsing by calling getValue() first which will then set the alreadyset flag
				property.getValue();
				java.util.List<String> values = property.getValues();
				if ((values.size() == 1 && values.contains(property.getDefault()) == true) == false) {
					lst_categoryFields.setItems(values.toArray(new String[values.size()]));
				}

				propertyName = "MinValue";
				property = (SynProperty) localElement.getProperty(propertyName);
				String value = property.getValue();
				if (value != null && value.trim().length() != 0) {
					txt_minValueField.setText(value);
				}

				propertyName = "MaxValue";
				property = (SynProperty) localElement.getProperty(propertyName);
				value = property.getValue();
				if (value != null && value.trim().length() != 0) {
					txt_maxValueField.setText(value);
				}

				btn_DeleteCategoryField.setEnabled(false);
				btn_UpCategoryField.setEnabled(false);
				btn_DownCategoryField.setEnabled(false);
				txt_CategoryField.setEnabled(false);
			} catch (Exception e) {
				log(new Status(IStatus.ERROR, getPluginId(), "could not read " + propertyName, e));
				disableAll();
			}
		}
	}

	private void categoryFieldListSelectionChanged() {
		int selectionIndex = lst_categoryFields.getSelectionIndex();
		String selectedCategoryFieldValue = (selectionIndex == -1) ? "" : lst_categoryFields.getItem(selectionIndex);
		// set the category field text
		txt_CategoryField.setText(selectedCategoryFieldValue);
		// enable the category field text iff we have a selection
		txt_CategoryField.setEnabled(selectionIndex != -1);
		// enable remove button if we have a selection
		btn_DeleteCategoryField.setEnabled(selectionIndex != -1);
		// enable up button if selection is not the first
		btn_UpCategoryField.setEnabled(selectionIndex > 0);
		// enable down button if selection is not the last
		btn_DownCategoryField.setEnabled(selectionIndex + 1 != lst_categoryFields.getItemCount());
	}

	private void btnAddClicked() {
		// get the next value
		int i = 0;
		boolean foundMatch = true;
		while (foundMatch == true) {
			foundMatch = false;
			for (String item : lst_categoryFields.getItems()) {
				if (item.endsWith("_" + i) == true) {
					foundMatch = true;
					i++;
					break;
				}
			}

		}
		// add a new category is the list
		lst_categoryFields.add("category_" + i);
		// select it
		lst_categoryFields.select(lst_categoryFields.getItemCount() - 1);
		// trigger the category field selection changed
		categoryFieldListSelectionChanged();
		// update the local element
		try {
			localElement.setPropertyValues("DesignCategories", Arrays.asList(lst_categoryFields.getItems()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not add a new category field", e));
			disableAll();
		}
	}

	private void btnDeleteClicked() {
		// remove current selection from the list
		int selectionIndex = lst_categoryFields.getSelectionIndex();
		String categoryFieldValue = lst_categoryFields.getItem(selectionIndex);
		lst_categoryFields.remove(selectionIndex);
		// select a category field
		if (selectionIndex == lst_categoryFields.getItemCount()) {
			selectionIndex--;
		}
		lst_categoryFields.select(selectionIndex);
		// trigger the category field selection changed
		categoryFieldListSelectionChanged();
		// update the local element
		try {
			localElement.setPropertyValues("DesignCategories", Arrays.asList(lst_categoryFields.getItems()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not add remove " + categoryFieldValue, e));
			disableAll();
		}
	}

	private void btnUpClicked() {
		// remove current selection from the list
		int selectionIndex = lst_categoryFields.getSelectionIndex();
		String categoryFieldValue = lst_categoryFields.getItem(selectionIndex);
		// remove the item
		lst_categoryFields.remove(selectionIndex);
		// add it back one up
		lst_categoryFields.add(categoryFieldValue, --selectionIndex);
		// select a category field
		lst_categoryFields.select(selectionIndex);
		// trigger the category field selection changed
		categoryFieldListSelectionChanged();
		// update the local element
		try {
			localElement.setPropertyValues("DesignCategories", Arrays.asList(lst_categoryFields.getItems()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not move " + categoryFieldValue + " up ", e));
			disableAll();
		}
	}

	private void btnDownClicked() {
		// remove current selection from the list
		int selectionIndex = lst_categoryFields.getSelectionIndex();
		String categoryFieldValue = lst_categoryFields.getItem(selectionIndex);
		// remove the item
		lst_categoryFields.remove(selectionIndex);
		// add it back one up
		lst_categoryFields.add(categoryFieldValue, ++selectionIndex);
		// select a category field
		lst_categoryFields.select(selectionIndex);
		// trigger the category field selection changed
		categoryFieldListSelectionChanged();
		// update the local element
		try {
			localElement.setPropertyValues("DesignCategories", Arrays.asList(lst_categoryFields.getItems()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not move " + categoryFieldValue + " down ", e));
			disableAll();
		}
	}

	private void categoryFieldTextModified() {
		// remove current selection from the list
		int selectionIndex = lst_categoryFields.getSelectionIndex();
		if (selectionIndex == -1) {
			return;
		}
		String categoryFieldValue = lst_categoryFields.getItem(selectionIndex);
		if (categoryFieldValue.equals(txt_CategoryField.getText()) == false) {
			// remove the item
			lst_categoryFields.remove(selectionIndex);
			// add it back at same location
			lst_categoryFields.add(txt_CategoryField.getText(), selectionIndex);
			// select it
			lst_categoryFields.select(selectionIndex);
			// trigger the category field selection changed
			// categoryFieldListSelectionChanged();
			// update the local element
			try {
				localElement.setPropertyValues("DesignCategories", Arrays.asList(lst_categoryFields.getItems()));
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not add update " + categoryFieldValue, e));
				disableAll();
			}
		}
	}

	private void minValueFieldTextModified() {
		try {
			localElement.setPropertyValue("MinValue", txt_minValueField.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not set minimum data value to " + txt_minValueField.getText(), e));
			disableAll();
		}
	}

	private void maxValueFieldTextModified() {
		try {
			localElement.setPropertyValue("MaxValue", txt_maxValueField.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not set maximum data value to " + txt_maxValueField.getText(), e));
			disableAll();
		}
	}
}
