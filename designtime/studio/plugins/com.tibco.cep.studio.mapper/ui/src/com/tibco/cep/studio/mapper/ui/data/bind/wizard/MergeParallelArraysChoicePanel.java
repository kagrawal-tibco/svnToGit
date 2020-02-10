package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.SetVariableBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.data.utils.DisplayConstants;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * The wizard (choice) panel for merging parallel arrays.
 */
public class MergeParallelArraysChoicePanel extends DefaultWizardPanel {
   private BindingWizardState mState;
   private TemplateReport mOn;
   private String mXPath;

   private MergeParallelArraysChoicePanel(final BindingWizardState state, final TemplateReport on, final String xpath) {
      mState = state;
      mOn = on;
      mXPath = xpath;
      setLabel(BindingWizardResources.MERGE_PARALLEL_TITLE);

      setNextPanel(new Details());
   }

   private class Details extends DefaultWizardPanel {
      private JTextField m_variableName;
      private JTextField m_posVarName;

      public Details() {
         m_variableName = new JTextField();
         m_posVarName = new JTextField();
         final JLabel lbl = new JLabel(BindingWizardResources.MERGE_PARALLEL_POSITION_VAR + ":");
         final JLabel lbl2 = new JLabel(BindingWizardResources.MERGE_PARALLEL_ITEM_VAR + ":");

         JPanel panel = new JPanel(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.fill = GridBagConstraints.BOTH;

         gbc.weightx = 0;
         gbc.gridx = 0;
         gbc.gridy = 0;
         gbc.insets = new Insets(4, 4, 4, 4);
         panel.add(lbl, gbc);

         gbc.weightx = 1;
         gbc.gridx = 1;
         panel.add(m_posVarName, gbc);

         gbc.weightx = 0;
         gbc.gridx = 0;
         gbc.gridy++;
         panel.add(lbl2, gbc);

         gbc.weightx = 1;
         gbc.gridx = 1;
         panel.add(m_variableName, gbc);

         gbc.gridx = 0;
         gbc.weightx = 0;
         gbc.gridy++;
         gbc.weighty = 1;
         panel.add(new JLabel(), gbc);

         m_posVarName.setText(BindingWizardResources.MERGE_PARALLEL_POSITION_VAR_DEFAULT);
         m_variableName.setText(BindingWizardResources.MERGE_PARALLEL_ITEM_VAR_DEFAULT);

         setLabel(BindingWizardResources.MERGE_PARALLEL_TITLE);
         JLabel header = new JLabel(BindingWizardResources.MERGE_PARALLEL_HEADER);
         setComponent(DisplayConstants.createHeaderPanel(header, panel));
      }

      public void finish() {
         String xp = Utilities.makeRelativeXPath(mOn.getChildContext(), null, mXPath, null);

         //WCETODO make both var names valid & unique...
         String posName = m_posVarName.getText();
         String vn = m_variableName.getText();
         if (posName.length() == 0) {
            posName = BindingWizardResources.MERGE_PARALLEL_POSITION_VAR_DEFAULT;
         }
         if (vn.length() == 0) {
            vn = BindingWizardResources.MERGE_PARALLEL_ITEM_VAR_DEFAULT;
         }
         // The position variable gets set to 'position()'.
         SetVariableBinding positionvar = new SetVariableBinding(BindingElementInfo.EMPTY_INFO,
                                                                 ExpandedName.makeName(NoNamespace.URI, posName),
                                                                 "position()");

         String filteredXp = xp + "[$" + posName + "]";
         SetVariableBinding itemVar = new SetVariableBinding(BindingElementInfo.EMPTY_INFO,
                                                             ExpandedName.makeName(NoNamespace.URI, vn),
                                                             filteredXp);

         mOn.getBinding().addChild(0, positionvar);
         mOn.getBinding().addChild(1, itemVar);

         mState.wizardFinished(mOn.getBinding());

         // Expand this path:
         Binding nb = BindingManipulationUtils.getEquivalentBindingInTemplate(mOn.getBinding(), mState.getTree().getTemplateEditorConfiguration().getBinding());
         TreePath path = mState.getTree().getPathForBinding(nb);
         mState.getTree().expandPath(path);
      }
   }

   public static WizardPanel createIfAppropriate(final BindingWizardState state, final TemplateReport on, final String xpath) {
      if (Utilities.isRelativeXPath(on.getChildContext(), xpath)) {
         // doesn't make sense, it is relative (inside) the current for-each, so you couldn't merge.
         return null;
      }
      return new MergeParallelArraysChoicePanel(state, on, xpath);
   }
}

;
