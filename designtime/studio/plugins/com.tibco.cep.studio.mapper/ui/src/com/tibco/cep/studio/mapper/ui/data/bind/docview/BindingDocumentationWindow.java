package com.tibco.cep.studio.mapper.ui.data.bind.docview;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.DataTypeTreeNode;
import com.tibco.cep.studio.mapper.ui.data.TypeDocumentationPanel;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorResources;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingNode;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizedScrollPane;
import com.tibco.cep.studio.mapper.ui.xmlui.QNamePanelResources;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * The window part of {@link BindingDocumentationDialog}, the floating dialog that shows documentation.
 */
class BindingDocumentationWindow extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTabbedPane m_tabArea;
   private TypeDocumentationPanel m_documentation;
   private DataTree m_dataTree;
   private JRadioButton m_expected;
   private JRadioButton m_computed;
   private JPanel m_radioPanel;

   private SmSequenceType m_currentExpected; // only if binding mode.
   private SmSequenceType m_currentComputed; // only if binding mode.

   public BindingDocumentationWindow(UIAgent uiAgent) {
      super(new BorderLayout());

      m_tabArea = new JTabbedPane();
      m_documentation = new TypeDocumentationPanel(uiAgent);
      m_dataTree = new DataTree(uiAgent);
      HorzSizedScrollPane hsp = new HorzSizedScrollPane(m_dataTree);
      m_tabArea.addTab(QNamePanelResources.DOCUMENTATION, m_documentation);
      m_tabArea.addTab(QNamePanelResources.TREE, hsp);

      // Add panel for right-hand-side which switches between expected/computed:
      m_radioPanel = new JPanel(new BorderLayout());
      JPanel sp = new JPanel(new GridLayout(1, 2, 4, 0));
      m_expected = new JRadioButton(BindingEditorResources.TYPE_EXPECTED);
      m_computed = new JRadioButton(BindingEditorResources.TYPE_COMPUTED);
      sp.add(m_expected);
      sp.add(m_computed);
      m_computed.setSelected(true);//WCETODO write to prefs.
      m_radioPanel.add(sp, BorderLayout.WEST);

      ActionListener al = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            expectedComputedChanged();
         }
      };
      m_expected.addActionListener(al);
      m_computed.addActionListener(al);
      ButtonGroup bg = new ButtonGroup();
      bg.add(m_expected);
      bg.add(m_computed);

      add(m_radioPanel, BorderLayout.NORTH);
      add(m_tabArea, BorderLayout.CENTER);
   }

   public void clear() {
      showRadioPanel(false);
      clearDisplays();
   }

   private void clearDisplays() {
      m_documentation.clear();
      m_dataTree.setRootType(SMDT.VOID);
   }

   public void set(DataTypeTreeNode dttn) {
      SmSequenceType t = dttn.getAsXType();
      showRadioPanel(false);
      setXType(t);
   }

   public void set(BindingNode bn) {
      TemplateReport br = bn.getTemplateReport();
      if (br == null) {
         // can happen in rebuild callbacks, etc., just ignore.
         return;
      }

      TemplateReport tr = bn.getTemplateReport();
      if (tr == null) {
         // shouldn't happen, if it does, just show nothing
         clear();
         return;
      }
      boolean isMarker = bn.getBinding() instanceof MarkerBinding;
      showRadioPanel(!isMarker);

      if (!isMarker) {
         m_currentExpected = tr.getExpectedType();
         m_currentComputed = tr.getComputedType();
         setBindingType();
      }
      else {
         // Just show the marker as is:
         setXType(tr.getComputedType());
      }
   }

   private void setBindingType() {
      if (m_expected.isSelected()) {
         setXType(m_currentExpected);
      }
      else {
         setXType(m_currentComputed);
      }
   }

   private void setXType(SmSequenceType t) {
      if (t == null) {
         clearDisplays();
      }
      else {
         m_documentation.set(t);
         m_dataTree.setRootType(t);
      }
   }

   private void expectedComputedChanged() {
      // just refresh.
      setBindingType();
   }

   private void showRadioPanel(boolean showme) {
      if (m_radioPanel.isVisible() == showme) {
         return;
      }
      m_radioPanel.setVisible(showme);
      revalidate();
      repaint();
   }
}
