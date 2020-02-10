package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.wizards.SettingsWizard;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.AbstractTemplateSettingsWizardPage.TYPE;

public class IndicatorSettingsCreateInSelected extends AbstractStateMachineComponentAction {

	private static final long serialVersionUID = -7724834611339305041L;

	public IndicatorSettingsCreateInSelected() {
		super("Indicator Settings...", TRANSPARENT_ICON);
		putValue(LONG_DESCRIPTION, "Creates Indicator Settings In the Selected State Visualization");
	}

	@Override
	public boolean doIsEnabled(LocalElement targetElement) throws Exception {
		if (targetElement instanceof LocalStateVisualization) {
			LocalStateVisualization localStateVisualization = (LocalStateVisualization) targetElement;
			return StateMachineComponentHelper.getIndicatorSeriesConfig(localStateVisualization) == null;
		}
		return false;
	}

	@Override
	protected void run(LocalElement targetElement, ActionEvent e) {
		try {
			SettingsWizard indicatorSettingsWizard = new SettingsWizard(TYPE.INDICATOR, (LocalStateVisualization) targetElement);
			WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), indicatorSettingsWizard);
			int userClicked = wizardDialog.open();
			if (userClicked == WizardDialog.OK) {
				refresh(targetElement);
			}
		} catch (Exception ex) {
			String message = "could not open indicator settings wizard on " + targetElement.toShortString();
			logWarningAndAlert(message, ex);
		}
	}

}
