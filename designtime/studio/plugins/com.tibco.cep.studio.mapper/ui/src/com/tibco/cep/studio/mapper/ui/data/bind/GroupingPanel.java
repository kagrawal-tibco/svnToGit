package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualGroupingBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class GroupingPanel implements StatementPanel {

   public GroupingPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new VirtualGroupingBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Class<VirtualGroupingBinding> getHandlesBindingClass() {
      return VirtualGroupingBinding.class;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return false;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VirtualGroupingBinding m_grouping;

      public Editor(VirtualGroupingBinding binding) {
         super(new BorderLayout());
         m_grouping = binding;

         final JComboBox combox = new JComboBox();
         combox.addItem("Group-By");
         combox.addItem("Group-Adjacent");
         combox.addItem("Group-Starting With");
         combox.addItem("Group-Ending With");
         combox.setSelectedIndex(m_grouping.getGroupType());
         combox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               m_grouping.setGroupType(combox.getSelectedIndex());
            }
         });
         JLabel l = new JLabel("Grouping Type:  ");
         setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
         JPanel t = new JPanel(new BorderLayout());
         t.add(l, BorderLayout.WEST);
         JPanel sp = new JPanel(new BorderLayout());
         sp.add(combox, BorderLayout.WEST);
         t.add(sp, BorderLayout.CENTER);
         add(t, BorderLayout.NORTH);
      }
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new Editor((VirtualGroupingBinding) binding);
   }

   public Binding getDefaultAddAroundBinding() {
      return new VirtualForEachGroupBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public String getDisplayName() {
      return "Grouping";
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getGroupByIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH;
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }
}

