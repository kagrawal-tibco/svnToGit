package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CopyBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The panel corresponding to {@link CopyBinding}.
 */
public class CopyPanel implements StatementPanel {

   public CopyPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new CopyBinding(BindingElementInfo.EMPTY_INFO, null);
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof TemplateContentBinding;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Class<CopyBinding> getHandlesBindingClass() {
      return CopyBinding.class;
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return null;
   }

   public String getDisplayName() {
      return "Copy";
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getCopyIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH;
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }
}

