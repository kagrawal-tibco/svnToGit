package com.tibco.cep.studio.mapper.ui.data.param;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
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

   public JComponent createEditor(ParameterNode node,
                                  ChangeListener changeListener,
                                  Object detailsValue,
                                  UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return node.getTypeCategory().createEditor(changeListener, detailsValue, uiAgent, namespaceContextRegistry, importRegistry);
   }

   public SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      // ElementTypeRef isn't allowed in XTypes.
      throw new IllegalArgumentException("Not supported here");
   }

   public boolean canRepeat() {
      // attributes don't repeat.
      return false;
   }

   public XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni) {
      XiNode el = factory.createElement(XsdSchema.ATTRIBUTE_NAME);
      String qname = getTypeQName(node, ni, null);
      XiAttribute.setStringValue(el, "name", node.getName());
      XiAttribute.setStringValue(el, "type", qname);
      XsdSchema.writeAttrOccurs(el, node.getMin(), node.getMax());
      return el;
   }

   public ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni) {
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
         ParameterNode pn = new ParameterNode(ed);
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

   public ParameterNode fromXType(SmSequenceType type, SmCardinality oc, ParameterEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   public String getTypeQName(ParameterNode node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
      return node.getTypeCategory().getTypeQName(node.getContentModelDetails(), mapper, uiAgent);
   }

   public Object readDetails(ParameterNode node, String typeName) {
      return node.getTypeCategory().readDetails(typeName);
   }

   public String getDisplayName(ParameterNode node) {
      return "@" + node.getName();
   }

   public boolean hasType() {
      return true;
   }

   public Object createDetails(ParameterNode node) {
      if (node.getTypeCategory() == null) {
         node.setTypeCategory(node.getEditor().getDefaultTypeCategory());
      }
      return node.getTypeCategory().createDetails();
   }

   public Object getEditorValue(ParameterNode node, JComponent c) {
      return node.getTypeCategory().getEditorValue(c);
   }

   public String getDisplayName() {
      return ParameterEditorResources.ATTRIBUTE_OF_TYPE;
   }

   public Icon getDisplayIcon(ParameterNode node) {
      if (node == null) {
         return DataIcons.getTypeReferenceIcon();
      }
      return node.getTypeCategory().getDisplayIcon();
   }

   public boolean isLeaf() {
      return true;
   }
}
