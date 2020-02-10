package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.XslPIBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The panel for representing XML comments in the XSLT editor.  Special versions of these comments are used as markers.
 */
public class GeneratePIPanel implements StatementPanel {
   public GeneratePIPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new XslPIBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Class<XslPIBinding> getHandlesBindingClass() {
      return XslPIBinding.class;
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

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return null;
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public String getDisplayName() {
      return "Generate PI";
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getGeneratePIIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_NOT_EDITABLE;
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }
}

