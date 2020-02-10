package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The gui panel for {@link WhenBinding}
 */
public class WhenPanel implements StatementPanel {
   public WhenPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new WhenBinding(BindingElementInfo.EMPTY_INFO, "");
   }

   public Class<WhenBinding> getHandlesBindingClass() {
      return WhenBinding.class;
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

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public Binding getDefaultAddAroundBinding() {
      return new ChooseBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public String getDisplayName() {
      return "When";
   }

   public String getDisplayNameFor(TemplateReport report) {
      return "[" + getDisplayName() + "]";
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH;
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getWhenIcon();
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return false;
   }
}

