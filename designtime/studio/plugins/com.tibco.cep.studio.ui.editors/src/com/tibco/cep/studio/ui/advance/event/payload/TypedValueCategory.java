package com.tibco.cep.studio.ui.advance.event.payload;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelChild;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelParent;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * The category representing an xsl typed value.
 * (Currently not in use because XSLT editor is not in use)
 */
public class TypedValueCategory extends ContentModelCategory {

   public static final ContentModelCategory INSTANCE = new TypedValueCategory();

   private TypedValueCategory() {
   }

   public JComponent createEditor(ParameterPayloadNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry,
                                  ImportRegistry importRegistry) {
      // has primitives:
   //   return node.getTypeCategory().createEditor(changeListener, detailsValue,
   // 		  uiAgent,
   //                                              namespaceContextRegistry, importRegistry);
	   return null;
   }

   public String getTypeQName(Object node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
	   if(node instanceof PayloadTreeModelChild)
		   return ((PayloadTreeModelChild)node).getTypeCategory().getTypeQName(((PayloadTreeModelChild)node).getContentModelDetails(), mapper, uiAgent);
	   if(node instanceof PayloadTreeModelParent)
		   return ((PayloadTreeModelChild)node).getTypeCategory().getTypeQName(((PayloadTreeModelChild)node).getContentModelDetails(), mapper, uiAgent);
	   return null;

	   
   }

   public Object readDetails(Object node, String typeName) {
	   if(node instanceof PayloadTreeModelChild)
		   return ((PayloadTreeModelChild)node).getTypeCategory().readDetails(typeName);
	   if(node instanceof PayloadTreeModelParent)
		   return ((PayloadTreeModelParent)node).getTypeCategory().readDetails(typeName);
	   return null;
	   
   }

   public String getDisplayName(Object node) {

		if(node instanceof PayloadTreeModelChild)
			return "(" + ((PayloadTreeModelChild)node).getTypeCategory().getDisplayName() + ")";

		if(node instanceof PayloadTreeModelParent)
			return "(" + ((PayloadTreeModelParent)node).getTypeCategory().getDisplayName() + ")";
		return null;
		
   }

   public SmSequenceType computeXType(Object node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
		if (node instanceof PayloadTreeModelChild) {
			String t = ((PayloadTreeModelChild)node).getTypeCategory().getXsdType(
					((PayloadTreeModelChild)node).getContentModelDetails());
			SmSequenceType base = computeBaseType(t);
			SmCardinality xo = SmCardinality.create(((PayloadTreeModelChild)node).getMin(),
					((PayloadTreeModelChild)node).getMax());
			if (!xo.equals(SmCardinality.EXACTLY_ONE)) {
				return SmSequenceTypeFactory.createRepeats(base, xo);
			}
			return base;
		}
		if (node instanceof PayloadTreeModelParent) {
			String t = ((PayloadTreeModelParent)node).getTypeCategory().getXsdType(
					((PayloadTreeModelParent)node).getContentModelDetails());
			SmSequenceType base = computeBaseType(t);
			SmCardinality xo = SmCardinality.create(((PayloadTreeModelParent)node).getMin(),
					((PayloadTreeModelParent)node).getMax());
			if (!xo.equals(SmCardinality.EXACTLY_ONE)) {
				return SmSequenceTypeFactory.createRepeats(base, xo);
			}
			return base;
		}
		return null;
   }

   public PayloadTreeModelParent fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni,Object parentNode) {
      return null;
   }

   public ParameterPayloadNode fromXType(SmSequenceType type, SmCardinality oc, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   public XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni) {
      return null;
   }

   private SmSequenceType computeBaseType(String t) {
      if (t == null || t.equals("string")) {
         return SMDT.STRING;
      }
      if (t.equals("int")) {
         return SMDT.INTEGER;
      }
      return SMDT.STRING;
   }

   public boolean hasType() {
      return true;
   }

   public Object createDetails(Object node) {
	   if(node instanceof PayloadTreeModelChild)
      return ((PayloadTreeModelChild)node).getTypeCategory().createDetails();
	   if(node instanceof PayloadTreeModelParent)
		      return ((PayloadTreeModelParent)node).getTypeCategory().createDetails();
	   return null;
   }

   public Object getEditorValue(Object node, JComponent c) {
     // return node.getTypeCategory().getEditorValue(c);
	   return null;
   }

   public String getDisplayName() {
      return "Simple Value";
   }

   public Icon getDisplayIcon(Object node) {
     /* if (node == null) {
         return DataIcons.getTypeReferenceIcon();// need another icon.
      }
      return node.getTypeCategory().getDisplayIcon();*/
	   return null;
   }

   public boolean isLeaf() {
      return true;
   }

@Override
public Object getEditorValue(ParameterPayloadNode node, JComponent editor) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Object createDetails(ParameterPayloadNode node) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Icon getDisplayIcon(ParameterPayloadNode node) {
	// TODO Auto-generated method stub
	return null;
}

}
