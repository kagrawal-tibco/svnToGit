package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.metric.MetricEditor;
import com.tibco.cep.studio.dashboard.ui.editors.metric.MetricEditorInput;
import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.ui.forms.AbstractEntityFormViewer;


/**
 * @author ntamhank this is a new editor for metric, based on concept editor.
 *
 */
public class LocalMetricViewer extends AbstractEntityFormViewer {

	protected LocalMetric localMetric;
	private Text metricDescText;

	public LocalMetricViewer(MetricEditor editor) {
		this.editor = editor;
		if (editor != null && editor.getEditorInput() instanceof MetricEditorInput) {
			localMetric = (LocalMetric) ((MetricEditorInput) editor.getEditorInput()).getLocalElement();
		} else {
			localMetric = (LocalMetric) editor.getLocalElement();
		}
		//this.exHandler = DashboardUIPlugin.getInstance().getExceptionHandler();
	}

	public void createPartControl(Composite container) {
		super.createPartControl(container, Messages.getString("metric.editor.title") + " " + localMetric.getName(), ((ContributableImageRegistry) DashboardUIPlugin.getInstance().getImageRegistry()).get(localMetric));
		super.createToolBarActions(); // setting default toolbars
		if (localMetric.getDescription() != null) {
			metricDescText.setText(localMetric.getDescription());
		}
		// Making readonly widgets
		if (!getEditor().isEnabled()) {
			readOnlyWidgets();
		}
	}

	@Override
	protected void createGeneralPart(ScrolledForm form, FormToolkit toolkit) {
		// create description section
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setText(Messages.getString("metric.section.Configuration"));
		// set layout data for description section
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);
		// set the layout on the description section
		section.setLayout(new FillLayout());
		// create client in the section
		Composite sectionClient = toolkit.createComposite(section);
		// set layout in the section client
		sectionClient.setLayout(new GridLayout(2, false));
		// create description label
		Label lbl_Description = toolkit.createLabel(sectionClient, Messages.getString("metric.property.Description"), SWT.NONE);
		lbl_Description.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		// create description text
		metricDescText = toolkit.createText(sectionClient, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridData metricDescTextGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		metricDescTextGridData.heightHint = 50;
		// Fix the width for description
		metricDescTextGridData.widthHint = 600;
		metricDescText.setLayoutData(metricDescTextGridData);
		metricDescText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				String description = metricDescText.getText();
				// User can modify the description from some specific value to a blank string,
				// hence removed blank string check for description.
				if (description != null && description.equals(localMetric.getDescription()) == false) {
					localMetric.setDescription(description);
				}
			}
		});
		toolkit.paintBordersFor(sectionClient);
		// set the section client in the description section
		section.setClient(sectionClient);
	}

	@Override
	protected void createPropertiesPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		// create properties section
		propertiesSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		propertiesSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		propertiesSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		propertiesSection.setText(Messages.getString("metric.section.Fields"));
		propertiesSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				adjustPropertySections(e, true);
			}
		});
		// create properties section client
		Composite sectionClient = toolkit.createComposite(propertiesSection);
		propertiesSection.setClient(sectionClient);
		sectionClient.setLayout(new FillLayout());
		LocalMetricFieldsTable fieldsTable = new LocalMetricFieldsTable(editor, false);
		fieldsTable.createControl(sectionClient);
		fieldsTable.setInput(localMetric);
		toolkit.paintBordersFor(sectionClient);
	}

	@Override
	protected void createExtendedPropertiesPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		// create extended properties section
		extPropSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		extPropSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		extPropSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		extPropSection.setText(Messages.getString("metric.section.TrackingFields"));
		extPropSection.addExpansionListener(new ExpansionAdapter() {

			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				adjustPropertySections(e, false);
			}
		});

		// create extended properties section client
		Composite sectionClient = toolkit.createComposite(extPropSection);
		extPropSection.setClient(sectionClient);
		sectionClient.setLayout(new FillLayout());
		LocalMetricFieldsTable fieldsTable = new LocalMetricFieldsTable(editor, true);
		fieldsTable.createControl(sectionClient);
		fieldsTable.setInput(localMetric);
		toolkit.paintBordersFor(sectionClient);
	}

	private void readOnlyWidgets() {
		metricDescText.setEditable(false);
	}

	@Override
	protected void createPropertiesColumnList() {
	}
}