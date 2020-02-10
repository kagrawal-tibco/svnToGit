package com.tibco.cep.studio.dashboard.ui.editors.views.skin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalChartComponentColorSet;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalTextComponentColorSet;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionTable;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.TableColumnInfo;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 *
 * @author rgupta
 */
public class SkinPage extends AbstractEntityEditorPage {

	private ElementCheckBoxSelectionViewer chartColorSetViewer;
	private ElementCheckBoxSelectionViewer textColorSetViewer;

	public SkinPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
	}

	@Override
	protected void createFieldSection(IManagedForm mform, Composite parent) throws Exception {
		Section section = createSection(mform, parent, Messages.getString("EventMetadataEditorPage.fieldSection.title"));

		CTabFolder tabFolder = new CTabFolder(section, SWT.BOTTOM);
		mform.getToolkit().adapt(tabFolder);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		tabFolder.setLayoutData(gd);

		createDefaultChartColorSetPage(mform, tabFolder);
		createDefaultTextColorSetPage(mform, tabFolder);
		// the first tab is the default.
		tabFolder.setSelection(0);
		section.setClient(tabFolder);
	}

	private void createDefaultChartColorSetPage(IManagedForm mform, CTabFolder tabFolder) throws Exception {
		CTabItem defaultTab = new CTabItem(tabFolder, SWT.NONE);
		defaultTab.setText("Chart Color Set");

		ElementCheckBoxSelectionTable table = new ElementCheckBoxSelectionTable(tabFolder, TableColumnInfo.get(false, true));
		chartColorSetViewer = new ElementCheckBoxSelectionViewer(tabFolder, getLocalElement(), "ComponentColorSet", "DefaultComponentColorSet", table);

		defaultTab.setControl(table.getControl());
	}

	private void createDefaultTextColorSetPage(IManagedForm mform, CTabFolder tabFolder) throws Exception {
		CTabItem defaultTab = new CTabItem(tabFolder, SWT.NONE);
		defaultTab.setText("Text Color Set");

		ElementCheckBoxSelectionTable table = new ElementCheckBoxSelectionTable(tabFolder, TableColumnInfo.get(false, true));
		textColorSetViewer = new ElementCheckBoxSelectionViewer(tabFolder, getLocalElement(), BEViewsElementNames.COMPONENT_COLOR_SET, BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET, table);

		defaultTab.setControl(table.getControl());
	}

	private List<LocalElement> getListByType(List<LocalElement> localConfigList, String typeName) throws Exception {
		List<LocalElement> result = new ArrayList<LocalElement>();
		for (int i = 0; i < localConfigList.size(); i++) {
			LocalConfig localConfig = (LocalConfig) localConfigList.get(i);
			if (localConfig.getInsightType().equals(typeName)) {
				result.add(localConfig);
			}
		}
		return result;
	}

	@Override
	protected void doPopulateControl(LocalElement localElement) throws Exception {
		super.doPopulateControl(localElement);
		try {
			populateChartColorSet(localElement);
			populateTextColorSet(localElement);
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not initialize " + getEditorInput().getName(), e));
		}
	}

	protected void populateChartColorSet(LocalElement localElement) throws Exception {
		List<LocalElement> selectedElements = getListByType(localElement.getChildren(BEViewsElementNames.COMPONENT_COLOR_SET), BEViewsElementNames.CHART_COLOR_SET);
		List<LocalElement> defaultList = getListByType(localElement.getChildren(BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET), BEViewsElementNames.CHART_COLOR_SET);
		LocalElement defaultItem = null;
		if (!defaultList.isEmpty()) {
			defaultItem = defaultList.get(0);
		}

		chartColorSetViewer.setElementChoices(localElement.getRoot().getChildren(BEViewsElementNames.CHART_COLOR_SET));
		chartColorSetViewer.setSelectedElements(selectedElements, defaultItem);
	}

	protected void populateTextColorSet(LocalElement localElement) throws Exception {
		List<LocalElement> selectedElements = getListByType(localElement.getChildren(BEViewsElementNames.COMPONENT_COLOR_SET), BEViewsElementNames.TEXT_COLOR_SET);
		List<LocalElement> defaultList = getListByType(localElement.getChildren(BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET), BEViewsElementNames.TEXT_COLOR_SET);
		LocalElement defaultItem = null;
		if (!defaultList.isEmpty()) {
			defaultItem = defaultList.get(0);
		}

		textColorSetViewer.setElementChoices(localElement.getRoot().getChildren(BEViewsElementNames.TEXT_COLOR_SET));
		textColorSetViewer.setSelectedElements(selectedElements, defaultItem);
	}

	@Override
	protected void handleOutsideElementChange(int change, LocalElement element) {
		try {
			//we will only process chart/text color set add & delete changes
			if (element instanceof LocalChartComponentColorSet || element instanceof LocalTextComponentColorSet){
				if (change == IResourceDelta.REMOVED){
					getLocalElement().refresh(BEViewsElementNames.COMPONENT_COLOR_SET);
					getLocalElement().refresh(BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET);
				}
				populateChartColorSet(getLocalElement());
				populateTextColorSet(getLocalElement());
			}
			//we will only be getting skin updates due to a re-factoring on chart/text color sets
			if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true){
				//reset all the properties
				getLocalElement().refresh(element.getEObject());
				//reset all referenced children
				getLocalElement().refresh(BEViewsElementNames.COMPONENT_COLOR_SET);
				getLocalElement().refresh(BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET);
				populateControl(getLocalElement());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
