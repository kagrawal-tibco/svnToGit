package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TextBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class TextPanel implements StatementPanel {
   public TextPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new TextBinding(BindingElementInfo.EMPTY_INFO, "");
   }

   public Class<TextBinding> getHandlesBindingClass() {
      return TextBinding.class;
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return null;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof CommentBinding;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public String getDisplayName() {
      return "Text";
   }

   public Binding getDefaultAddAroundBinding() {
      return new ElementBinding(BindingElementInfo.EMPTY_INFO, ExpandedName.makeName(""));
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getTextIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_CONSTANT;
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }
}

