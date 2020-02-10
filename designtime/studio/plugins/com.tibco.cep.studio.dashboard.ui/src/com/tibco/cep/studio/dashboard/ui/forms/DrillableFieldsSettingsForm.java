package com.tibco.cep.studio.dashboard.ui.forms;

import java.util.ArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionTable;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.TableColumnInfo;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class DrillableFieldsSettingsForm extends BaseForm {

	private ElementCheckBoxSelectionViewer drillableFieldsViewer;
	private LocalActionRule localActionRule;

	public DrillableFieldsSettingsForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Drillable Fields", formToolKit, parent, showGroup);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout());
		ElementCheckBoxSelectionTable table = new ElementCheckBoxSelectionTable(formComposite, TableColumnInfo.get());
		drillableFieldsViewer = new ElementCheckBoxSelectionViewer(formComposite, new LocalActionRule(), LocalActionRule.ELEMENT_KEY_DRILLABLE_FIELDS, table);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		//layoutData.widthHint = 250;
		drillableFieldsViewer.setLayoutData(layoutData);
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		localActionRule = null;
		if (newLocalElement != null) {
//		if (newLocalElement instanceof LocalSeriesConfig) {
			localActionRule = (LocalActionRule) newLocalElement.getElement(BEViewsElementNames.ACTION_RULE);
//		}
//		else {
//			throw new IllegalArgumentException(newLocalElement + " is not acceptable");
//		}
		}
	}

	@Override
	protected void doDisableListeners() {
		//do nothing
	}

	@Override
	protected void doEnableListeners() {
		//do nothing
	}


	@Override
	public void refreshEnumerations() {
		drillableFieldsViewer.setParentElement(new LocalActionRule());
		drillableFieldsViewer.setElementChoices(new ArrayList<LocalElement>());
		if (localActionRule != null) {
			try {
				LocalDataSource localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
				if (localDataSource != null) {
					drillableFieldsViewer.setParentElement(localActionRule);
					drillableFieldsViewer.setElementChoices(localDataSource.getFields(LocalDataSource.ENUM_GROUP_BY_FIELD));
				}
			} catch (Exception e) {
				log(new Status(IStatus.ERROR, getPluginId(), "could not refresh enumerations in " + this.getClass().getName(), e));
				disableAll();
			}
		}
		drillableFieldsViewer.refresh(true);
	}

	@Override
	public void refreshSelections() {
		try {
			drillableFieldsViewer.setSelectedElements(localActionRule == null ? new ArrayList<LocalElement>() : localActionRule.getChildren(LocalActionRule.ELEMENT_KEY_DRILLABLE_FIELDS));
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	@Override
	public void disableAll() {
		super.disableAll();
		drillableFieldsViewer.getControl().setEnabled(false);
	}

	@Override
	public void enableAll() {
		super.enableAll();
		drillableFieldsViewer.getControl().setEnabled(true);
	}
}
