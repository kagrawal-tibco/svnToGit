package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * A wizard panel for performing auto-fill (allows selection/deselection).
 */
public class AutoFillWizardPanel extends DefaultWizardPanel {
   private BindingWizardState m_state;
   private TemplateReport m_on;
   private AutoFillSelectionComponent m_component;

   public AutoFillWizardPanel(final BindingWizardState state, final TemplateReport on) {
      if (on == null) {
         throw new NullPointerException("Null report");
      }
      m_state = state;
      m_on = on;

      setLabel(BindingWizardResources.AUTOMAP_TITLE);
      m_component = new AutoFillSelectionComponent(state.getUiAgent(), on);
      setComponent(m_component);
   }

   public void finish() {
      NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(m_on.getBinding());
      m_component.enact(ni);
      m_state.wizardFinished(m_on.getBinding());
   }

   public void enter() {
      super.enter();
      m_state.showStep(m_on.getBinding());
   }
}

;
