package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.ChartEditorBaseSlavePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.PageUtils;
import com.tibco.cep.studio.dashboard.ui.forms.RelatedChartsForm;

public class ChartRelatedEditorPage extends ChartEditorBaseSlavePage implements SlavePage {

	public ChartRelatedEditorPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement, "related", "Related Charts");
	}

	@Override
	protected void createForm(FormToolkit toolkit, Composite parent) throws Exception {
		// set layout for the parent
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		parent.setLayout(layout);

		Section section = PageUtils.createSection(toolkit, parent);
		RelatedChartsForm relatedChartsForm = new RelatedChartsForm(toolkit, ((Composite) section.getClient()), false, SWT.VERTICAL);
		section.setText(relatedChartsForm.getTitle());
		relatedChartsForm.init();
		registerForm(relatedChartsForm);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

}