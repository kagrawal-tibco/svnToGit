package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Panel (i.e. GUI information) for a stylesheet binding.
 */
public class StylesheetPanel implements StatementPanel {

   public StylesheetPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new StylesheetBinding(BindingElementInfo.EMPTY_INFO);
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof TemplateContentBinding;
   }

   public Class<StylesheetBinding> getHandlesBindingClass() {
      return StylesheetBinding.class;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
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
      return "Stylesheet"; // never displayed.
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getForEachIcon();
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_NOT_EDITABLE;
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return false;
   }
}

