package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentCopyMode;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding;
import com.tibco.cep.studio.mapper.ui.StudioStrings;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditResources;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A panel used by {@link ElementPanel} and {@link AttributePanel} for configuring the virtual-ness.
 */
class VirtualDataComponentPanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private VirtualDataComponentBinding m_dcBinding;
   private JComboBox m_inlineFormula = new JComboBox();
   private JCheckBox m_explicitNil;
   private JComboBox m_copyMode;
   private Pair[] m_modePairs;

   private Binding m_clonedChildren; // used to backup children when switching between nil/not-nil (switching to nil auto clears out some marker children)

   private boolean m_showingAVTMode; // if the 'inline' formula is set up with AVT as the choice or if it has the proper choices.

   private static class Pair {
      public String m_displayName;
      public VirtualDataComponentCopyMode m_code;

      public Pair(String dn, VirtualDataComponentCopyMode code) {
         m_displayName = dn;
         m_code = code;
      }

      public String toString() {
         return m_displayName;
      }
   }

   public VirtualDataComponentPanel(VirtualDataComponentBinding binding) {
      super(new GridBagLayout());
      setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // Would like a standard border constants somewhere...
      m_dcBinding = binding;
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 0;
      gbc.weighty = 0;
      gbc.insets = new Insets(4, 0, 0, 0);

      if (binding instanceof VirtualElementBinding) {
         add(new JLabel(BindingEditorResources.EXPLICIT_NIL + ":"), gbc);
         gbc.gridx++;
         gbc.weightx = 1;
         m_explicitNil = new JCheckBox("");
         add(m_explicitNil, gbc);
         final VirtualElementBinding veb = (VirtualElementBinding) binding;
         m_explicitNil.setSelected(veb.isExplicitNil());
         m_explicitNil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // reeanble buttons.
               boolean sel = m_explicitNil.isSelected();
               veb.setExplicitNil(sel);
               if (sel) {
                  // save the children in case we decide to switch back to not-nil.
                  m_clonedChildren = ((Binding) m_dcBinding).cloneDeep();
                  removeAllElementMarkers();
               }
               else {
                  if (m_clonedChildren != null) {
                     // restore any children (in case we've switched back & forth)
                     BindingManipulationUtils.copyBindingContents(m_clonedChildren, (Binding) m_dcBinding);
                  }
               }
               refreshEnabled();
            }
         });
         gbc.gridy++;
      }
      gbc.gridx = 0;
      gbc.weightx = 0;
      add(new JLabel(BindingEditorResources.FORMULA + ": "), gbc);
      gbc.gridx = 1;
      gbc.weightx = 1;
      add(m_inlineFormula, gbc);

      gbc.gridy++;
      gbc.gridx = 0;
      gbc.weightx = 0;
      if (binding instanceof AttributeBinding) {
         // Only a subset are allowed (or make sense) for attributes (cannot nil an attribute, thank god)
         m_modePairs = new Pair[]
         {
            new Pair(BindingEditorResources.REQUIRED_TO_REQUIRED, VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED),
            new Pair(BindingEditorResources.OPTIONAL_TO_OPTIONAL, VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL),
            new Pair(BindingEditorResources.NIL_TO_OPTIONAL, VirtualDataComponentCopyMode.NIL_TO_OPTIONAL)
         };
      }
      if (binding instanceof VirtualElementBinding) {
         m_modePairs = new Pair[]
         {
            new Pair(BindingEditorResources.REQUIRED_TO_REQUIRED, VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED),
            new Pair(BindingEditorResources.OPTIONAL_TO_OPTIONAL, VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL),
            new Pair(BindingEditorResources.NIL_TO_NIL, VirtualDataComponentCopyMode.NIL_TO_NIL),
            new Pair(BindingEditorResources.OPTIONAL_TO_NIL, VirtualDataComponentCopyMode.OPTIONAL_TO_NIL),
            new Pair(BindingEditorResources.NIL_TO_OPTIONAL, VirtualDataComponentCopyMode.NIL_TO_OPTIONAL),
            new Pair(BindingEditorResources.OPTIONALNIL_TO_OPTIONALNIL, VirtualDataComponentCopyMode.OPTIONALNIL_TO_OPTIONALNIL)
         };
      }
      gbc.gridy++;

      gbc.gridx = 0;
      add(new JLabel(BindingEditorResources.COPY_MODE + ": "), gbc);
      gbc.gridx++;
      gbc.weightx = 1;
      m_copyMode = new JComboBox();
      for (int i = 0; i < m_modePairs.length; i++) {
         m_copyMode.addItem(m_modePairs[i]);
      }
      m_copyMode.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Pair p = (Pair) m_copyMode.getSelectedItem();
            m_dcBinding.setCopyMode(p.m_code);
         }
      });
      add(m_copyMode, gbc);
      gbc.gridy++;
      VirtualDataComponentCopyMode cm = m_dcBinding.getCopyMode();
      for (int i = 0; i < m_modePairs.length; i++) {
         if (m_modePairs[i].m_code == cm) {
            m_copyMode.setSelectedIndex(i);
         }
      }

      gbc.gridy++;
      gbc.gridx = 0;
      gbc.weightx = 0;
      gbc.weighty = 1;

      add(new JLabel(), gbc); // spacer.

      // set up w/ initial values (may change immediately if this is already an AVT)
      initStandardInlineFormulas();

      if (binding.getHasInlineFormula()) {
         if (binding.getInlineIsText()) {
            m_inlineFormula.setSelectedIndex(1);
         }
         else {
            m_inlineFormula.setSelectedIndex(2);
         }
      }
      else {
         m_inlineFormula.setSelectedIndex(0);
      }

      m_inlineFormula.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (m_inlineFormula.getSelectedIndex() == 0) {
               m_dcBinding.setHasInlineFormula(false);
               refreshEnabled();
            }
            if (m_inlineFormula.getSelectedIndex() == 1) {
               m_dcBinding.setHasInlineFormula(true);
               m_dcBinding.setInlineIsText(true);
               refreshEnabled();
            }
            if (m_inlineFormula.getSelectedIndex() == 2) {
               m_dcBinding.setHasInlineFormula(true);
               m_dcBinding.setInlineIsText(false);
               refreshEnabled();
            }
         }
      });
      refreshEnabled();
   }

   private void addStandardInlineFormulas() {
      if (!m_showingAVTMode && m_inlineFormula.getItemCount() > 0) {
         return;
      }
      initStandardInlineFormulas();
   }

   private void initStandardInlineFormulas() {
      m_showingAVTMode = false;
      m_inlineFormula.removeAllItems();
      m_inlineFormula.addItem(StudioStrings.NONE);
      m_inlineFormula.addItem(BindingEditorResources.CONSTANT_VALUE);
      m_inlineFormula.addItem(XPathEditResources.XPATH_FORMULA_LABEL);
   }

   private void addAVTInlineFormula() {
      if (m_showingAVTMode) {
         return;
      }
      m_showingAVTMode = true;
      m_inlineFormula.removeAllItems();
      m_inlineFormula.addItem(XPathEditResources.AVT_FORMULA_LABEL);
   }

   private void removeAllInlineFormulas() {
      m_inlineFormula.setSelectedItem(""); // for some reason required; o.w.
      m_inlineFormula.removeAllItems();
      m_showingAVTMode = false;
   }

   /**
    * Refreshes the buttons based on the binding state.
    */
   public void refreshEnabled() {
      boolean explicitNil;
      if (m_dcBinding instanceof VirtualElementBinding) {
         VirtualElementBinding veb = (VirtualElementBinding) m_dcBinding;
         explicitNil = veb.isExplicitNil();
      }
      else {
         explicitNil = false;
      }
      boolean canHaveInline = m_dcBinding.getCanHaveInline() && !explicitNil;
      boolean canHaveCopyMode = canHaveInline && m_dcBinding.getHasInlineFormula() && !m_dcBinding.getInlineIsText(); // xpath.
      if (m_copyMode != null) {
         m_copyMode.setEnabled(canHaveCopyMode); // same logic as 'if'.
      }
      if (!canHaveCopyMode) {
         if (m_copyMode != null) {
            m_copyMode.setEnabled(false);
            m_copyMode.setSelectedIndex(0);
            m_dcBinding.setCopyMode(VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED);
         }
      }
      m_inlineFormula.setEnabled(canHaveInline);
      if (!canHaveInline && m_dcBinding instanceof AttributeBinding) {
         addAVTInlineFormula();
      }
      else {
         if (explicitNil) {
            removeAllInlineFormulas();
         }
         else {
            addStandardInlineFormulas();
         }
      }
   }

   private void removeAllElementMarkers() {
      Binding b = (Binding) m_dcBinding;
      // we're removing, so go backwards:
      for (int i = b.getChildCount() - 1; i >= 0; i--) {
         Binding c = b.getChild(i);
         if (c instanceof MarkerBinding) {
            MarkerBinding mb = (MarkerBinding) c;
            SmSequenceType t = mb.getMarkerType();
            if (SmSequenceTypeSupport.containsAnyElements(t)) {
               b.removeChild(i);
            }
         }
      }
   }
}

