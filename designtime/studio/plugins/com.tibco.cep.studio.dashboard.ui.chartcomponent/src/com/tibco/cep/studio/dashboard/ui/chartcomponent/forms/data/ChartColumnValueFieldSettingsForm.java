package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum;

public class ChartColumnValueFieldSettingsForm extends ChartBarValueFieldSettingsForm {

	private Anchor[] anchors;

	public ChartColumnValueFieldSettingsForm(FormToolkit formToolKit, Composite parent) {
		super(formToolKit, parent);
		anchors = new Anchor[]{new Anchor("Up",SeriesAnchorEnum.Q1),new Anchor("Down",SeriesAnchorEnum.Q4)};
	}
	
	@Override
	protected String getType() {
		return "Column";
	}
	
	protected Anchor[] getAnchorEnumerations(){
		return anchors;
	}

}
