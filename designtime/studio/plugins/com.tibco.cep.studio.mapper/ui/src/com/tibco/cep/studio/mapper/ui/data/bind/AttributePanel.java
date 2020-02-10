package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.AVTUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualAttributeBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdQNamePlugin;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class AttributePanel implements StatementPanel {
   public AttributePanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      VirtualAttributeBinding vab = new VirtualAttributeBinding(BindingElementInfo.EMPTY_INFO, null, ""); // default to explicit style.
      if (optionalStartingBinding instanceof MarkerBinding) {
         // if we can, initialize w/ a decent name:
         MarkerBinding mb = (MarkerBinding) optionalStartingBinding;
         SmSequenceType xt = mb.getMarkerType();
         ExpandedName ename = xt.prime().getName();
         if (ename != null) {
            vab.setLiteralName(ename);
         }
         // give it a formula!
         vab.setHasInlineFormula(true);
         vab.setInlineIsText(false);
      }
      return vab;
   }

   public Class<VirtualAttributeBinding> getHandlesBindingClass() {
      return VirtualAttributeBinding.class;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      VirtualAttributeBinding vab = (VirtualAttributeBinding) binding;
      if (vab.isExplicitXslRepresentation()) {
         return childBinding instanceof TemplateContentBinding;
      }
      else {
         return false;
      }
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      VirtualAttributeBinding vab = (VirtualAttributeBinding) report.getBinding();
      return vab.getCopyMode().isOutputOptional() ? SmCardinality.OPTIONAL : SmCardinality.EXACTLY_ONE;
   }

   public Binding getDefaultAddAroundBinding() {
      return new ElementBinding(BindingElementInfo.EMPTY_INFO, ExpandedName.makeName("someElement"));
   }

   public Binding getDefaultAddChildBinding() {
      return new ValueOfBinding(BindingElementInfo.EMPTY_INFO);
   }

   static class Editor extends JTabbedPane {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Editor(VirtualAttributeBinding binding, NamespaceContextRegistry ni,
                    ImportRegistry importRegistry, UIAgent uiAgent,
                    SmSequenceType outputContext, SmSequenceType remainderType) {
         if (importRegistry == null) {
            throw new NullPointerException("Null import registry");
         }
         JPanel literalOrExplicitNamePanel = new LiteralOrExplicitNamePanel(uiAgent,
                                                                            ni,
                                                                            importRegistry,
                                                                            XsdQNamePlugin.ATTRIBUTE,
                                                                            binding,
                                                                            remainderType);
         addTab(DataIcons.getAttributeLabel(), literalOrExplicitNamePanel);
         final VirtualDataComponentPanel vdcp = new VirtualDataComponentPanel(binding);
         addTab(BindingEditorResources.FORMULA_CONTENT, vdcp);
         addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
               // just refresh this a bunch (because changes on attribute tab can affect it
               vdcp.refreshEnabled();
            }
         });
      }
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new Editor((VirtualAttributeBinding) binding,
                        contextNamespaceContextRegistry, importRegistry,
                        uiAgent, outputContext, remainderType);
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      VirtualAttributeBinding vab = (VirtualAttributeBinding) templateReport.getBinding();
      if (!vab.isExplicitXslRepresentation()) {
         return FIELD_TYPE_ATTRIBUTE_VALUE_TEMPLATE;
      }
      if (vab.getHasInlineFormula()) {
         if (vab.getInlineIsText()) {
            return FIELD_TYPE_CONSTANT;
         }
         else {
            return FIELD_TYPE_XPATH;
         }
      }
      // otherwise, nothing to edit.
      return FIELD_TYPE_NOT_EDITABLE;
   }

   public String getDisplayName() {
      return "Attribute";
   }

   public String getDisplayNameFor(TemplateReport report) {
      AttributeBinding ab = (AttributeBinding) report.getBinding();
      if (!ab.isExplicitXslRepresentation()) {
         // literal
         return "@" + report.getBinding().getName().getLocalName();
      }
      else {
         // explicit
         String ln = ab.getExplicitNameAVT();
         if (ab.getExplicitNamespaceAVT() == null && !AVTUtilities.isAVTString(ln)) {
            QName qn = new QName(ln);
            return "@" + qn.getLocalName();
         }
      }
      return "(Attribute)";
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      if (templateReport == null) {
         return DataIcons.getAttributeIcon();
      }
      SmSequenceType ct = templateReport.getComputedType();
      if (ct == null) {
         return getGenericIcon();
      }
      if (ct.getParticleTerm() == null) {
         // in the case of no schema, display something more generic.
         return getGenericIcon();
      }
      return DataIcons.getIcon(ct);
   }

   /**
    * Gets an icon that is appropriate for the element given that the computed type is unknown really (i.e. no schema)
    *
    * @return The icon, never null.
    */
   private static Icon getGenericIcon() {
      return DataIcons.getStringIcon();
   }

   public boolean isLeaf(TemplateReport templateReport) {
      // check type of it...
      return false;
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }
}

