package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateContentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The panel for representing XML comments in the XSLT editor.
 */
public class MarkerBindingPanel implements StatementPanel {
   public MarkerBindingPanel() {
   }

   public Binding createNewInstance(Binding optionalStartingBinding) {
      return new MarkerBinding(SMDT.PREVIOUS_ERROR);
   }

   public Class<MarkerBinding> getHandlesBindingClass() {
      return MarkerBinding.class;
   }

   public final static SmType[] primitiveSMTypes =
       new SmType[] {XSDL.STRING, XSDL.INTEGER, XSDL.LONG, XSDL.DOUBLE, XSDL.BOOLEAN, XSDL.DATETIME};

   public boolean getIsNilled(TemplateReport report) {
//	   if (true) {
		   return false; // ryanh - hack for now, as primitive types are marked as nillable (incorrectly)
//	   }
//      SmSequenceType t = ((MarkerBinding) report.getBinding()).getMarkerType();
//      if (t.getFirstChildComponent() == null) {
//    	  return t.isNillable();
//      }
//      SmParticleTerm component = t.getFirstChildComponent().getParticleTerm();
//      
//      if (component instanceof SmDataComponent) {
//    	  SmType type = ((SmDataComponent) component).getType();
//    	  for(int i = 0; i < primitiveSMTypes.length; i++) {
//    		  if (primitiveSMTypes[i] == type) {
//    			  return false;
//    		  }
//    	  }
//      }
//      
//      return t.isNillable();
   }

   public SmCardinality getOccurrence(TemplateReport report) {
      SmSequenceType ct = ((MarkerBinding) report.getBinding()).getMarkerType();
      SmCardinality xo = ct.getOccurrence();
      if (xo == null) {
         return SmCardinality.EXACTLY_ONE;
      }
      return xo;
   }

   public boolean canHaveAsChild(Binding binding, Binding childBinding) {
      // Although no children can be added to the marker itself, if one is added, it will be switched.
      // (Treat as an element more-or-less in terms of what is allowed)
      return childBinding instanceof TemplateContentBinding;
   }

   public Binding getDefaultAddAroundBinding() {
      return null;
   }

   public Binding getDefaultAddChildBinding() {
      return null;
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
      SmSequenceType t = ((MarkerBinding) templateReport.getBinding()).getMarkerType();
      if (SmSequenceTypeSupport.isConcreteDataComponentType(t)) {
         if (!SmSequenceTypeSupport.isVoid(t.typedValue(true))) {
            return FIELD_TYPE_XPATH;
         }
         else {
            return FIELD_TYPE_NOT_EDITABLE;
         }
      }
      else {
         return FIELD_TYPE_NOT_EDITABLE;
      }
   }

   public String getDisplayName() {
      return "Marker";
   }

   public String getDisplayNameFor(TemplateReport templateReport) {
      SmSequenceType t = ((MarkerBinding) templateReport.getBinding()).getMarkerType();
      if (SmSequenceTypeSupport.isPreviousError(t)) {
         String msg = t.getUserMessage();
         if (msg != null) {
            return "Bad Marker: " + msg;
         }
      }
      return DataIcons.getName(t);
   }

   public Icon getDisplayIcon(TemplateReport templateReport) {
      if (templateReport == null) {
         return DataIcons.getSourceCommentIcon();
      }
      SmSequenceType t = ((MarkerBinding) templateReport.getBinding()).getMarkerType();
      return DataIcons.getIcon(t);
   }

   public boolean isAvailable(SmSequenceType term) {
      return true; // always available.
   }

   public boolean isLeaf(TemplateReport templateReport) {
      SmSequenceType t = ((MarkerBinding) templateReport.getBinding()).getMarkerType();
      boolean leaf = SmSequenceTypeSupport.isLeafDataComponent(t, true); // true -> ignore repeats (i.e. *,+,?) in considering this.
      boolean ret = leaf || SmSequenceTypeSupport.isChoice(t) || SmSequenceTypeSupport.isSequence(t);
      return ret;
   }
}

