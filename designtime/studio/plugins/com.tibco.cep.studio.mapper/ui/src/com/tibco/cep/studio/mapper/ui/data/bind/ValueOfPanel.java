package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class ValueOfPanel implements StatementPanel {

   public ValueOfPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new ValueOfBinding(BindingElementInfo.EMPTY_INFO, "");
   }

   public Class<ValueOfBinding> getHandlesBindingClass() {
      return ValueOfBinding.class;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof CommentBinding;
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return null;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Binding getDefaultAddAroundBinding() {
      return new ElementBinding(BindingElementInfo.EMPTY_INFO, ExpandedName.makeName(""));
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public static String getBindingDisplayName() {
      return "Value-Of";
   }

   public String getDisplayName() {
      return getBindingDisplayName();
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getValueOfIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH;
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }
}

