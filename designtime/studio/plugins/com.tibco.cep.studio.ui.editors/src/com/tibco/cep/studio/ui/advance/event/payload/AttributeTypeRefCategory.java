package com.tibco.cep.studio.ui.advance.event.payload;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditorResources;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelChild;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelParent;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * The category representing an attribute where the type is declared by reference.
 */
public class AttributeTypeRefCategory extends ContentModelCategory {

   public static final ContentModelCategory INSTANCE = new AttributeTypeRefCategory();

   private AttributeTypeRefCategory() {
   }

/*   public JComponent createEditor(ParameterPayloadNode node,
                                  ChangeListener changeListener,
                                  Object detailsValue,
                                  UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return node.getTypeCategory().createEditor(changeListener, detailsValue, uiAgent, namespaceContextRegistry, importRegistry);
   }
*/
   public SmSequenceType computeXType(Object node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      // ElementTypeRef isn't allowed in XTypes.
      throw new IllegalArgumentException("Not supported here");
   }

   public boolean canRepeat() {
      // attributes don't repeat.
      return false;
   }

   public XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni) {
		if (node instanceof PayloadTreeModelChild) {
			XiNode el = factory.createElement(XsdSchema.ATTRIBUTE_NAME);
			String qname = getTypeQName(node, ni, null);
			XiAttribute.setStringValue(el, "name", ((PayloadTreeModelChild)node).getName());
			XiAttribute.setStringValue(el, "type", qname);
			XsdSchema.writeAttrOccurs(el, ((PayloadTreeModelChild)node).getMin(), ((PayloadTreeModelChild)node).getMax());
			return el;
		}
		if (node instanceof PayloadTreeModelParent) {
			XiNode el = factory.createElement(XsdSchema.ATTRIBUTE_NAME);
			String qname = getTypeQName(node, ni, null);
			XiAttribute.setStringValue(el, "name", ((PayloadTreeModelParent)node).getName());
			XiAttribute.setStringValue(el, "type", qname);
			XsdSchema.writeAttrOccurs(el, ((PayloadTreeModelParent)node).getMin(), ((PayloadTreeModelParent)node).getMax());
			return el;
		}
		return null;
   }

   public PayloadTreeModelParent fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni,Object parentNode) {
      if (node.getName().equals(XsdSchema.ATTRIBUTE_NAME)) {
         // we don't handle this case:
         if (XiAttribute.getNode(node, "ref") != null) {
            return null;
         }
         ;
         // we do handle this one:
         if (XiAttribute.getNode(node, "type") == null) {
            return null;
         }
         ;
         String name = XiAttribute.getStringValue(node, "name");
         PayloadTreeModelParent pn = new PayloadTreeModelParent(null, null, null, null, ed);
         pn.setContentModelCategory(this);
         readAttrOccursAttributes(pn, node);
         pn.setName(name);

         String type = XiAttribute.getStringValue(node, "type");
         QName qn = new QName(type);
         ExpandedName en;
         try {
            en = qn.getExpandedName(ni);
            if (en == null) {
               en = ExpandedName.makeName(qn.getLocalName());
            }
         }
         catch (Exception e) {
            en = ExpandedName.makeName(qn.getLocalName());
         }
         if (XSDL.NAMESPACE.equals(en.getNamespaceURI())) {
            ElementTypeRefCategory.convertXsdType(pn, en.getLocalName());
         }
         else {
            ElementTypeRefCategory.convertType(pn, en);
         }

         return pn;
      }
      return null;
   }

   public ParameterPayloadNode fromXType(SmSequenceType type, SmCardinality oc, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   public String getTypeQName(Object node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
	   if(node instanceof PayloadTreeModelChild)
		   return ((PayloadTreeModelChild)node).getTypeCategory().getTypeQName(((PayloadTreeModelChild)node).getContentModelDetails(), mapper, uiAgent);
	   if(node instanceof PayloadTreeModelParent)
		   return ((PayloadTreeModelParent)node).getTypeCategory().getTypeQName(((PayloadTreeModelParent)node).getContentModelDetails(), mapper, uiAgent);
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
      return "@" + ((PayloadTreeModelChild)node).getName();
	   if(node instanceof PayloadTreeModelChild)
		      return "@" + ((PayloadTreeModelParent)node).getName();
	   return null;
	   
   }

   public boolean hasType() {
      return true;
   }

   public Object createDetails(Object node) {
		if (node instanceof PayloadTreeModelChild) {
			if (((PayloadTreeModelChild)node).getTypeCategory() == null) {
				((PayloadTreeModelChild)node).setTypeCategory(((PayloadTreeModelChild)node).getEditor().getDefaultTypeCategory());
			}
			return ((PayloadTreeModelChild)node).getTypeCategory().createDetails();
		}
		if (node instanceof PayloadTreeModelParent) {
			if (((PayloadTreeModelParent)node).getTypeCategory() == null) {
				((PayloadTreeModelParent)node).setTypeCategory(((PayloadTreeModelParent)node).getEditor().getDefaultTypeCategory());
			}
			return ((PayloadTreeModelParent)node).getTypeCategory().createDetails();
		}
		return null;
   }

/*   public Object getEditorValue(ParameterPayloadNode node, JComponent c) {
      return node.getTypeCategory().getEditorValue(c);
   }
*/
   public String getDisplayName() {
      return ParameterEditorResources.ATTRIBUTE_OF_TYPE;
   }

/*   public Icon getDisplayIcon(ParameterPayloadNode node) {
      if (node == null) {
         return DataIcons.getTypeReferenceIcon();
      }
      return node.getTypeCategory().getDisplayIcon();
   }
*/
   public boolean isLeaf() {
      return true;
   }

@Override
public JComponent createEditor(ParameterPayloadNode node,
		ChangeListener changeListener, Object detailsValue, UIAgent uiAgent,
		NamespaceContextRegistry namespaceContextRegistry,
		ImportRegistry importRegistry) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Object getEditorValue(ParameterPayloadNode node, JComponent editor) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Icon getDisplayIcon(ParameterPayloadNode node) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Object createDetails(ParameterPayloadNode node) {
	// TODO Auto-generated method stub
	return null;
}
}
