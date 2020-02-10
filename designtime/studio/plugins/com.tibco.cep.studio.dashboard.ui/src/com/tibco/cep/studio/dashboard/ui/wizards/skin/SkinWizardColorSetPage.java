package com.tibco.cep.studio.dashboard.ui.wizards.skin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionTable;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.TableColumnInfo;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class SkinWizardColorSetPage extends WizardPage {

	private LocalSkin dummySkin;

	private ElementCheckBoxSelectionViewer viewer;

	private LocalSkin skin;

	private String targetType;

	protected SkinWizardColorSetPage(String targetType) {
		super(SkinWizardColorSetPage.class.getName()+"."+targetType);
		this.targetType = targetType;
		dummySkin = new LocalSkin(null, "Dummy Skin");
	}

	@Override
	public void createControl(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new GridLayout());

		pageComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		ElementCheckBoxSelectionTable table = new ElementCheckBoxSelectionTable(pageComposite, TableColumnInfo.get(false, true));
		viewer = new ElementCheckBoxSelectionViewer(pageComposite, dummySkin, BEViewsElementNames.COMPONENT_COLOR_SET, BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET, table);

		setControl(pageComposite);

	}

	public void setLocalSkin(LocalSkin skin) {
		this.skin = skin;
		viewer.setParentElement(this.skin);
		List<LocalElement> selectedElements = getListByType(this.skin.getChildren(BEViewsElementNames.COMPONENT_COLOR_SET), targetType);
		List<LocalElement> defaultList = getListByType(this.skin.getChildren(BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET), targetType);
		LocalElement defaultItem = null;
		if (!defaultList.isEmpty()) {
			defaultItem = defaultList.get(0);
		}

		viewer.setElementChoices(this.skin.getRoot().getChildren(targetType));
		viewer.setSelectedElements(selectedElements, defaultItem);
	}

	private List<LocalElement> getListByType(List<LocalElement> localConfigList, String typeName) {
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
	public void dispose() {
		dummySkin = null;
	}
}
