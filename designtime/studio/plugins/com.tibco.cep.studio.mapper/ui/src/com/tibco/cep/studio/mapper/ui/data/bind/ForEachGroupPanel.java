package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.SortBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualGroupingBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class ForEachGroupPanel implements StatementPanel {

   public ForEachGroupPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      String f = null;
      if (optionalStartingBinding instanceof ForEachBinding || optionalStartingBinding instanceof VirtualForEachGroupBinding) {
         f = optionalStartingBinding.getFormula();
      }
      if (f == null) {
         f = "";
      }
      return new VirtualForEachGroupBinding(BindingElementInfo.EMPTY_INFO, f);
   }

   public Class<VirtualForEachGroupBinding> getHandlesBindingClass() {
      return VirtualForEachGroupBinding.class;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof TemplateContentBinding || childBinding instanceof SortBinding || childBinding instanceof VirtualGroupingBinding;
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
      return "For-Each-Group";
   }

   public String getDisplayNameFor(TemplateReport report) {
      String base = "[" + getDisplayName() + "]";
      String dn = CopyOfPanel.getOptionalTypeDisplayName(report.getExpectedType());
      if (dn == null) {
         return base;
      }
      return dn + " - " + base;
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getForEachGroupIcon();
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH;
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return false;
   }
}

