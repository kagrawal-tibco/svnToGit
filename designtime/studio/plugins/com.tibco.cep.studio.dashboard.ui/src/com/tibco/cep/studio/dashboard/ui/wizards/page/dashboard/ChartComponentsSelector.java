package com.tibco.cep.studio.dashboard.ui.wizards.page.dashboard;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDashboardPage;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPanel;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPartition;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.widgets.MemberComponentsEditor;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

class ChartComponentsSelector extends Composite {

	private MemberComponentsEditor memberChartComponentsEditor;

	private LocalPanel dummyPanel;

	private IProject project;

	ChartComponentsSelector(Composite parent, int style) {
		super(parent, style);
		LocalDashboardPage page = new LocalDashboardPage(null,"PlaceHolderPage");
		LocalPartition partition = new LocalPartition(page, "PlaceHolderPartition");
		dummyPanel = new LocalPanel(partition, "PlaceHolderPanel");
		createControl();
	}

	private void createControl() {
		setLayout(new FillLayout());
		memberChartComponentsEditor = new MemberComponentsEditor("Chart ",BEViewsElementNames.COMPONENT, null, this, SWT.VERTICAL | SWT.MULTI, true);
	}

	public void setProject(IProject project){
		if (project == null){
			throw new IllegalArgumentException("project cannot be null");
		}
		this.project = project;
		this.dummyPanel.setParent(LocalECoreFactory.getInstance(project));
	}

	public void populateControl() throws Exception {
		List<LocalElement> components = LocalECoreFactory.getInstance(project).getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
		memberChartComponentsEditor.setEnumeration(components);
		memberChartComponentsEditor.setLocalElement(dummyPanel);
	}

	public void setSelection(List<LocalElement> selections) {
		dummyPanel.setBulkOperation(true);
		try {
			for (LocalElement selection : selections) {
				dummyPanel.addElement(BEViewsElementNames.COMPONENT, selection);
			}
			memberChartComponentsEditor.setLocalElement(dummyPanel);
		} finally {
			dummyPanel.setBulkOperation(false);
		}

	}

	public List<LocalElement> getChartComponents() {
		try {
			return dummyPanel.getChildren(BEViewsElementNames.COMPONENT);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

}
