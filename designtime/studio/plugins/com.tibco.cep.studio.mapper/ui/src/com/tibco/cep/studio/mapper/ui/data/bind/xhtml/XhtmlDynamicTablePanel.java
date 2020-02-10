package com.tibco.cep.studio.mapper.ui.data.bind.xhtml;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.bind.StatementPanel;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class XhtmlDynamicTablePanel implements StatementPanel {
   public boolean isLeaf(TemplateReport templateReport) {
      return false;
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new XhtmlDynamicTable();
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return false; //WCETODO
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getForEachGroupIcon();//WCETODO
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public String getDisplayName() {
      return "HTML Table";
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return StatementPanel.FIELD_TYPE_NOT_EDITABLE;
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new JLabel("blather");
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public Class<XhtmlDynamicTableMatcher> getHandlesBindingClass() {
      return XhtmlDynamicTableMatcher.class;
   }
}

