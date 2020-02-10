package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.nsutils.SimpleNamespaceContextRegistry;
import com.tibco.cep.mapper.xml.xdata.DefaultImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.decision.DecisionWizardPanel;
import com.tibco.cep.studio.mapper.ui.xmlui.QNamePanel;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdQNamePlugin;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The choice wizard panel for when a choice element is instantiated.
 */
public class InstantiateWildcardChoicePanel extends DecisionWizardPanel {
   private BindingWizardState mState;
   private TemplateReport mOn;
//   private String mXPath;
//   private SmSequenceType m_choice; // if null means 'fill-in-later'.
//   private QNamePanel m_panel;

   /**
    * @param state
    * @param on
    * @param wildcard The wildcard to be filled in.
    */
   public InstantiateWildcardChoicePanel(final BindingWizardState state,
                                         final TemplateReport on,
                                         SmSequenceType wildcard) {
      mState = state;
      mOn = on;
      setLabel("Choose Element");
//      m_panel = 
    		  new QNamePanel(state.getUiAgent(),
                               XsdQNamePlugin.ELEMENT,
                               new SimpleNamespaceContextRegistry(),
                               new DefaultImportRegistry());
   }

   public void finish() {
      super.finish();
   }

   public void enter() {
      super.enter();
      mState.showStep(mOn.getBinding());
   }
}

;
