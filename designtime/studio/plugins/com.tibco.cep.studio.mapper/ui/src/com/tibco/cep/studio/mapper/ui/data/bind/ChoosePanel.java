package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class ChoosePanel implements StatementPanel {
   public ChoosePanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new ChooseBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Class<ChooseBinding> getHandlesBindingClass() {
      return ChooseBinding.class;
   }

   public Binding getDefaultAddAroundBinding() {
      // nothing special.
      return null;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof WhenBinding || childBinding instanceof OtherwiseBinding || childBinding instanceof CommentBinding;
   }

   public Binding getDefaultAddChildBinding() {
      return new WhenBinding(BindingElementInfo.EMPTY_INFO, "");
   }

   public String getDisplayNameFor(TemplateReport report) {
      String base = "[" + getDisplayName() + "]";
      String dn = CopyOfPanel.getOptionalTypeDisplayName(report.getExpectedType());
      if (dn == null) {
         return base;
      }
      return dn + " - " + base;
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
      return "Choose";
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      // nope, only the whens do.
      return FIELD_TYPE_NOT_EDITABLE;
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getChooseIcon();
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return false;
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }
}

