package com.tibco.cep.studio.dashboard.ui.wizards.skin;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalChartComponentColorSet;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionTable;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.TableColumnInfo;

public class ChartColorSetWizardSeriesColorPage extends WizardPage {

	private LocalChartComponentColorSet dummyChartComponentColorSet;

	private LocalChartComponentColorSet chartComponentColorSet;

	private ElementCheckBoxSelectionViewer seriesColorViewer;

	public ChartColorSetWizardSeriesColorPage(){
		super(ChartColorSetWizardSeriesColorPage.class.getName());
		dummyChartComponentColorSet = new LocalChartComponentColorSet(null, "Dummy Chart Color Set");
	}

	@Override
	public void createControl(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new GridLayout());

		pageComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		ElementCheckBoxSelectionTable table = new ElementCheckBoxSelectionTable(pageComposite, TableColumnInfo.get(false, false));
		seriesColorViewer = new ElementCheckBoxSelectionViewer(parent, dummyChartComponentColorSet, "SeriesColor", table);

		setControl(pageComposite);
	}

	public void setLocalChartComponentColorSet(LocalChartComponentColorSet chartComponentColorSet) {
		this.chartComponentColorSet = chartComponentColorSet;
		seriesColorViewer.setParentElement(chartComponentColorSet);
		seriesColorViewer.setElementChoices(this.chartComponentColorSet.getRoot().getChildren("SeriesColor"));
		seriesColorViewer.setSelectedElements(this.chartComponentColorSet.getChildren("SeriesColor"));
	}

	@Override
	public void dispose() {
		dummyChartComponentColorSet = null;
	}
}
