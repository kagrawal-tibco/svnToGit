package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.ApplyTemplatesBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.SortBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.WithParamBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class ApplyTemplatesPanel implements StatementPanel {
   public ApplyTemplatesPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new ApplyTemplatesBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Binding getDefaultAddAroundBinding() {
      // nothing special
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return new WithParamBinding(BindingElementInfo.EMPTY_INFO, "");
   }

   public String getDisplayName() {
      return "Apply Templates";
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      if (childBinding instanceof CommentBinding || childBinding instanceof SortBinding || childBinding instanceof WithParamBinding) {
         return true;
      }
      return false;
   }

   public String getDisplayNameFor(TemplateReport report) {
      return getDisplayName();
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Class<ApplyTemplatesBinding> getHandlesBindingClass() {
      return ApplyTemplatesBinding.class;
   }

   public Binding buildResult(Binding[] old, Binding blankTemplate) {
      /*XsltTemplateDataModel template = (XsltTemplateDataModel) mComboBox.getSelectedItem();
      CallTemplateBinding ret = template.createCallTemplateBinding();
      return ret;*/
      return null;
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getApplyTemplatesIcon();
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return null;
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH; // the xpath.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }
}

