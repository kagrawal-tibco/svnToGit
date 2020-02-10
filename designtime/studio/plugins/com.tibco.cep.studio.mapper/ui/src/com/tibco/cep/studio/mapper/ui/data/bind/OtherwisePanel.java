package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class OtherwisePanel implements StatementPanel {
   public OtherwisePanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new OtherwiseBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Class<OtherwiseBinding> getHandlesBindingClass() {
      return OtherwiseBinding.class;
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
      return childBinding instanceof TemplateContentBinding;
   }

   public Binding getDefaultAddAroundBinding() {
      return new ChooseBinding(BindingElementInfo.EMPTY_INFO);
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public String getDisplayName() {
      return "Otherwise";
   }

   public String getDisplayNameFor(TemplateReport report) {
      return "[" + getDisplayName() + "]";
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_NOT_EDITABLE;
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getOtherwiseIcon();
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return false;
   }
}

