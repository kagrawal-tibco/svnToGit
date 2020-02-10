package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.editors.views.queryparams.QueryParamEditor;
import com.tibco.cep.studio.dashboard.ui.forms.AlertSettingsForm;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class IndicatorAlertSettingsForm extends BaseForm {

	protected Text txt_Query;
	protected ReadOnlyQueryParamEditor queryParamViewer;

	protected Text txtValueField;

	protected AlertSettingsForm alertSettingsForm;

	protected LocalStateSeriesConfig localStateSeriesConfig;
	protected LocalActionRule localActionRule;
	protected LocalDataSource localDataSource;

	public IndicatorAlertSettingsForm(FormToolkit formToolKit, Composite parent) {
		super("Reference Data Settings", formToolKit, parent, false);
	}

	@Override
	public void init() {
		formComposite.setLayout(new FillLayout(SWT.VERTICAL));

		Composite referenceComposite = createGroup(formComposite, "Reference Information", SWT.NONE);
		referenceComposite.setLayout(new GridLayout(2, true));

		// query text
		txt_Query = createText(referenceComposite, "", SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
		GridData txt_QueryLayoutData = new GridData(SWT.FILL,SWT.FILL,true,false);
		txt_QueryLayoutData.heightHint = 50;
		txt_Query.setLayoutData(txt_QueryLayoutData);

		// query param viewer
		queryParamViewer = new ReadOnlyQueryParamEditor(formToolKit, referenceComposite);
		GridData queryParamViewerData = new GridData(SWT.FILL, SWT.FILL, true, true);
		queryParamViewerData.heightHint = 50;
		//PATCH added widthHint to compensate for ever widening form when switching selections due to % based layout  on param editor
		queryParamViewerData.widthHint = 150;
		queryParamViewer.setLayoutData(queryParamViewerData);

		// value field reference
		Composite valueFieldReferenceComposite = createComposite(referenceComposite, SWT.NONE);
		valueFieldReferenceComposite.setLayout(new GridLayout(4, false));

		layoutValueFieldReference(valueFieldReferenceComposite);

		valueFieldReferenceComposite.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false,2,1));

		alertSettingsForm = createAlertSettingsForm();
		alertSettingsForm.init();
		addForm(alertSettingsForm);
	}

	protected void layoutValueFieldReference(Composite composite) {
		createLabel(composite, "Value Field:", SWT.NONE);
		txtValueField = createText(composite, "", SWT.READ_ONLY | SWT.SINGLE);
		txtValueField.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));
	}

	protected AlertSettingsForm createAlertSettingsForm() {
		return new BasicDisplayAlertSettingsForm(formToolKit, formComposite, SWT.HORIZONTAL);
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		if (newLocalElement instanceof LocalStateSeriesConfig){
			localStateSeriesConfig = (LocalStateSeriesConfig) newLocalElement;
			localActionRule = (LocalActionRule) newLocalElement.getElement(BEViewsElementNames.ACTION_RULE);
			localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
		}
		else {
			throw new IllegalArgumentException(newLocalElement+" is not a state series config");
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
		//do nothing
	}

	@Override
	public void refreshSelections() {
		try {
			txt_Query.setText(localDataSource == null ? "" : localDataSource.getPropertyValue(LocalDataSource.PROP_KEY_QUERY));
			queryParamViewer.setLocalElement(localActionRule);
			txtValueField.setText(localStateSeriesConfig.getPropertyValue(getValueFieldPropertyName()));
		} catch (Exception e) {
			log(new Status(IStatus.ERROR,getPluginId(),"could not refresh selections in "+this.getClass().getName(),e));
			disableAll();
		}
	}

	protected String getValueFieldPropertyName() {
		return "IndicatorValueField";
	}

	private class ReadOnlyQueryParamEditor extends QueryParamEditor {

		public ReadOnlyQueryParamEditor(FormToolkit toolKit, Composite parent) {
			super(toolKit, parent);
		}

		@Override
		public void createTableViewer() {
			super.createTableViewer();
			getTableViewer().setCellEditors(null);
			getTableViewer().setCellModifier(null);
		}
	}

	private class BasicDisplayAlertSettingsForm extends AlertSettingsForm {

		public BasicDisplayAlertSettingsForm(FormToolkit formToolKit, Composite parent, int orientation) {
			super(formToolKit, parent, orientation);
		}

		@Override
		public void init() {
			super.init();
			getVisualAlertActionSettingsForm().disableDisplayControls();
		}

	}

}
