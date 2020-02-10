package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.xmlui.ScannerFilter;
import com.tibco.cep.studio.mapper.ui.xmlui.SchemaScanner;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdTypePanel;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;

/**
 * A panel to pick a xsi:type substitution, used by {@link ElementPanel}.
 */
class ElementTypeSubstitutionPanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox m_typeSubEnabled;
   private XsdTypePanel m_typeSub;
   private VirtualElementBinding m_binding;
   private UIAgent uiAgent;
   private SmSequenceType m_outputContext;

   public ElementTypeSubstitutionPanel(UIAgent uiAgent, NamespaceContextRegistry ni, ImportRegistry ir, VirtualElementBinding veb, SmSequenceType outputContext) {
      super(new BorderLayout(0, 4));
      this.uiAgent = uiAgent;
      m_binding = veb;
      m_typeSubEnabled = new JCheckBox(BindingEditorResources.TYPE_SUBSTITUTION);
      m_typeSub = new XsdTypePanel(uiAgent, ni, ir);
      m_typeSub.setNamespaceEditable(true);
      m_outputContext = outputContext;

      add(m_typeSubEnabled, BorderLayout.NORTH);
      add(m_typeSub, BorderLayout.CENTER);
      ExpandedName ts = veb.getTypeSubstitution();
      if (ts != null) {
         m_typeSub.setExpandedName(ts);
      }
      else {
         // default it to non-xsd primitive.
         m_typeSub.setExpandedName(ExpandedName.makeName(""));
      }
      m_typeSubEnabled.setSelected(ts != null);

      reenable();
      m_typeSubEnabled.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            reenable();
         }
      });
      m_typeSub.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            if (m_typeSubEnabled.isSelected()) {
               m_binding.setTypeSubstitution(m_typeSub.getExpandedName());
            }
         }
      });
      updateSuggestions();
   }

   public void updateSuggestions() {
      m_typeSub.setSuggestions(new ExpandedName[0]); // set it to this while we work on it.
      final DefaultCancelChecker cc = new DefaultCancelChecker();
      cc.setNiceMode(true); // we never cancel, but do this so it sleeps while running a bit.
      Thread th = new Thread(new Runnable() {
         public void run() {
            updateSuggestionsInternal(cc);
         }
      }, "element-sub-panel-type-scanner");
      th.setPriority(Thread.MIN_PRIORITY);
      th.start();
   }

   private void updateSuggestionsInternal(CancelChecker cc) {

      ExpandedName ename;
      try {
         ename = m_binding.computeComponentExpandedName(BindingNamespaceManipulationUtils.createNamespaceImporter(m_binding));
      }
      catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe) {
         ename = null;
      }
      SmElement el;
      if (ename != null) {
         SmSequenceType t = m_outputContext.getElementInContext(ename);
         if (t != null && t.getParticleTerm() instanceof SmElement) {
            el = (SmElement) t.getParticleTerm();
         }
         else {
            el = null;
         }
      }
      else {
         el = null;
      }
      final ExpandedName[] en;
      if (el != null) {
         en = findAllSubtypes(uiAgent, el.getType(), cc);
      }
      else {
         en = null;
      }
      // ... and set it:
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            if (en == null || en.length == 0) {
               m_typeSub.setSuggestions(null); // none.
            }
            else {
               m_typeSub.setSuggestions(en);
            }
         }
      });
   }

   private ExpandedName[] findAllSubtypes(UIAgent uiAgent,
                                          final SmType type, CancelChecker cc) {
      if (!SmSupport.isGlobalComponent(type)) {
         // can't override this...
         return new ExpandedName[0];
      }
      if (type.getAllowedDerivation() == 0) // none
      {
         // can't override this...
         return new ExpandedName[0];
      }
      ScannerFilter filter = new ScannerFilter() {
         public boolean isMember(Object candidate) {
            SmType t = (SmType) candidate;
            while (t != null) {
               t = t.getBaseType();
               if (t == type) {
                  return true;
               }
            }
            return false;
         }
      };
      ExpandedName[] r = SchemaScanner.scanComponents(uiAgent, SmComponent.TYPE_TYPE, filter, cc);
      if (r.length > 250) // too many results WCETODO --- make scanner cut off.
      {
         return new ExpandedName[0];
      }
      return r;
   }

   private void reenable() {
      m_typeSub.setEnabled(m_typeSubEnabled.isSelected());
      if (m_typeSubEnabled.isSelected()) {
         m_binding.setTypeSubstitution(m_typeSub.getExpandedName());
      }
      else {
         m_binding.setTypeSubstitution(null);
      }
   }
}

