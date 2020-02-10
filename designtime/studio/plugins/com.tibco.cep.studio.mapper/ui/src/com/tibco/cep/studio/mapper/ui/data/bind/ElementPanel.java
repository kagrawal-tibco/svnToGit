package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdQNamePlugin;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public class ElementPanel implements StatementPanel {
   public ElementPanel() {
   }

   static class Editor extends JTabbedPane {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Editor(VirtualElementBinding binding, NamespaceContextRegistry parentNamespaces,
                    ImportRegistry importRegistry, UIAgent uiAgent,
                    SmSequenceType outputContext, SmSequenceType remainingType) {
         if (importRegistry == null) {
            throw new NullPointerException("Null import registry");
         }
         NamespaceContextRegistry bindingNI = BindingNamespaceManipulationUtils.createNamespaceImporter(binding);
         NamespaceContextRegistry chained = NamespaceManipulationUtils.createChainingImporter(bindingNI, parentNamespaces);

         JPanel literalOrExplicitNamePanel = new LiteralOrExplicitNamePanel(uiAgent,
                                                                            chained,
                                                                            importRegistry,
                                                                            XsdQNamePlugin.ELEMENT,
                                                                            binding,
                                                                            remainingType);
         final ElementTypeSubstitutionPanel typeSubPanel = new ElementTypeSubstitutionPanel(uiAgent,
                                                                                            chained,
                                                                                            importRegistry,
                                                                                            binding,
                                                                                            outputContext);
         addTab(DataIcons.getElementLabel(), literalOrExplicitNamePanel);
         addTab(DataIcons.getTypeLabel(), typeSubPanel);
         addTab(BindingEditorResources.FORMULA_CONTENT, new VirtualDataComponentPanel(binding));
         addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
               if (getSelectedComponent() == typeSubPanel) {
                  typeSubPanel.updateSuggestions();
               }
            }
         });
      }
   }

   public Binding getDefaultAddAroundBinding() {
      return new ForEachBinding(BindingElementInfo.EMPTY_INFO);
   }

   public Binding getDefaultAddChildBinding() {
      return new VirtualElementBinding(BindingElementInfo.EMPTY_INFO, ExpandedName.makeName("", ""));
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      return childBinding instanceof TemplateContentBinding;
   }

   public boolean getIsNilled(TemplateReport report) {
      VirtualElementBinding vab = (VirtualElementBinding) report.getBinding();
      if (vab.isExplicitNil()) {
         return true;
      }
      return vab.getCopyMode().isOutputNil();
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      VirtualElementBinding vab = (VirtualElementBinding) report.getBinding();
      return vab.getCopyMode().isOutputOptional() ? SmCardinality.OPTIONAL : SmCardinality.EXACTLY_ONE;
   }

   public Binding createNewInstance(Binding startingWith) {
      VirtualElementBinding veb = new VirtualElementBinding(BindingElementInfo.EMPTY_INFO, ExpandedName.makeName("", ""));
      if (startingWith instanceof MarkerBinding) {
         // if we can, initialize w/ a decent name:
         MarkerBinding mb = (MarkerBinding) startingWith;
         SmSequenceType xt = mb.getMarkerType();
         ExpandedName ename = xt.prime().getName();
         if (ename != null) {
            veb.setLiteralName(ename);
         }
         if (SmSequenceTypeSupport.hasTypedValue(xt, true) && !(SmSequenceTypeSupport.isAny(xt))) {
            // give it a formula!
            veb.setHasInlineFormula(true);
            veb.setInlineIsText(false);
         }
      }
      return veb;
   }

   public Class<VirtualElementBinding> getHandlesBindingClass() {
      return VirtualElementBinding.class;
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new Editor((VirtualElementBinding) binding, contextNamespaceContextRegistry,
                        importRegistry, uiAgent, outputContext, remainderType);
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      VirtualElementBinding veb = (VirtualElementBinding) templateReport.getBinding();
      if (veb.getHasInlineFormula() && !veb.isExplicitNil()) {
         if (veb.getInlineIsText()) {
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
      return DataIcons.getElementLabel();
   }

   public String getDisplayNameFor(TemplateReport report) {
      return report.getBinding().getName().getLocalName();
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      if (templateReport == null) {
         return DataIcons.getElementIcon();
      }
      if (((ElementBinding) templateReport.getBinding()).hasAVTName()) {
         // If we have an AVT, just show the element icon.
         return DataIcons.getElementIcon();
      }
      SmSequenceType ct = templateReport.getComputedType();
      if (ct == null) {
         // can be null if out of context completely (in an illegal area, i.e. under a copy-of)
         return getGenericIcon(templateReport);
      }
      SmParticleTerm t = ct.getParticleTerm();
      if (t == null) {
         // If we're schema free, use generic icons:
         return getGenericIcon(templateReport);
      }
      else {
         return DataIcons.getIcon(ct);
      }
   }

   /**
    * Gets an icon that is appropriate for the element given that the computed type is unknown really (i.e. no schema)
    *
    * @param report The report.
    * @return The icon, never null.
    */
   private static Icon getGenericIcon(TemplateReport report) {
      if (report.getChildCount() > 0) {
         return DataIcons.getSequenceIcon();
      }
      return DataIcons.getStringIcon();
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return false; // always allow children even if it doesn't necessarily make sense by the schema.
   }
}

