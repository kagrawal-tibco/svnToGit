package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.TypeCopyOfBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdQNamePlugin;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The panel for the virtual {@link TypeCopyOfBinding}.
 */
public class CopyTypePanel implements StatementPanel {
   public CopyTypePanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      TypeCopyOfBinding tcob = new TypeCopyOfBinding(BindingElementInfo.EMPTY_INFO, ExpandedName.makeName(""));
      if (optionalStartingBinding instanceof MarkerBinding) {
         // if we can, initialize w/ a decent name:
         MarkerBinding mb = (MarkerBinding) optionalStartingBinding;
         SmSequenceType xt = mb.getMarkerType();
         ExpandedName ename = xt.prime().getName();
         if (ename != null) {
            tcob.setLiteralName(ename);
         }
      }
      if (optionalStartingBinding instanceof ElementBinding) {
         // if we can, initialize w/ a decent name:
         ElementBinding mb = (ElementBinding) optionalStartingBinding;
         if (!mb.isExplicitXslRepresentation()) {
            ExpandedName ename = mb.getName();
            if (ename != null) {
               tcob.setLiteralName(ename);
            }
         }
      }
      return tcob;
   }

   public Class<TypeCopyOfBinding> getHandlesBindingClass() {
      return TypeCopyOfBinding.class;
   }

   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Editor(final TypeCopyOfBinding binding,
                    ImportRegistry importRegistry,
                    UIAgent uiAgent,
                    SmSequenceType remainingType) {
         super(new BorderLayout());
         if (importRegistry == null) {
            throw new NullPointerException("Null import registry");
         }
         JPanel literalOrExplicitNamePanel = new LiteralOrExplicitNamePanel(uiAgent,
                                                                            BindingNamespaceManipulationUtils.createNamespaceImporter(binding),
                                                                            importRegistry,
                                                                            XsdQNamePlugin.ELEMENT,
                                                                            binding,
                                                                            remainingType);
         add(literalOrExplicitNamePanel);
         JPanel includeNs = new JPanel(new BorderLayout());
         includeNs.add(new JLabel(BindingEditorResources.COPY_ALL_NAMESPACES + ": "), BorderLayout.WEST);
         final JCheckBox jc = new JCheckBox();
         jc.setSelected(binding.getIncludeNamespaceCopies());
         jc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               binding.setIncludeNamespaceCopies(jc.isSelected());
            }
         });
         includeNs.add(jc);
         add(includeNs, BorderLayout.SOUTH);
      }
   }

   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType) {
      return new Editor((TypeCopyOfBinding) binding, importRegistry, uiAgent, remainderType);
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      // no children allowed, not even comments (maybe relax later).
      return false;
   }

   public boolean getIsNilled(TemplateReport report) {
      return false;
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      return SmCardinality.EXACTLY_ONE;
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
   }

   public String getDisplayName() {
      return "Copy-Contents-Of";
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      return DataIcons.getTypeCopyIcon();
   }

   public String getDisplayNameFor(TemplateReport report) {
      String base = "[" + getDisplayName() + "]";
      SmSequenceType ct = report.getComputedType();
      if (ct == null) {
         return base;
      }
      String t = SmSequenceTypeSupport.getDisplayName(ct);
      return t + " - " + base; // Put type name first (copy contents of can be seen easily w/ icon)
   }

   public int getEditableFieldType(TemplateReport templateReport) {
      return FIELD_TYPE_XPATH; // the formula.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      return true;
   }
}

