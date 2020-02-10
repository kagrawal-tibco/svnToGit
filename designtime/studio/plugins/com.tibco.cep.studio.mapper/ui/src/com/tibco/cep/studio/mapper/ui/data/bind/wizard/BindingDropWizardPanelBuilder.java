package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * Base interface for building all the panels in the binding drop GUI (it's a complicated wizard...)
 */
public interface BindingDropWizardPanelBuilder {
   public WizardPanel build(BindingWizardState state, TemplateReport report, String xpath);

   public Runnable buildFinisher(BindingWizardState state, TemplateReport report, String xpath);
}
