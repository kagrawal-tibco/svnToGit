package com.tibco.cep.studio.mapper.ui.data.param;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.UtilitySchema;
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
 * The category representing an element where the type is declared by reference.<br>
 * WCETODO --- Replace most of the implementation with {@link com.tibco.cep.studio.mapper.ui.xmlui.XsdTypePanel}.
 */
public class ElementTypeRefCategory extends ContentModelCategory {

   private static String ICNAMESPACE = "http://www.tibco.com/namespaces/tnt/plugins/inconcert/config";
   public static final ContentModelCategory INSTANCE = new ElementTypeRefCategory();

   private ElementTypeRefCategory() {
   }

   public JComponent createEditor(ParameterNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return node.getTypeCategory().createEditor(changeListener, detailsValue, uiAgent,
                                                 namespaceContextRegistry, importRegistry);
   }

   public SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      // ElementTypeRef isn't allowed in XTypes.
      throw new IllegalArgumentException("Not supported here");
   }

   private String getTypeQName(ParameterNode node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
      return node.getTypeCategory().getTypeQName(node.getContentModelDetails(), mapper, uiAgent);
   }

   public String getDisplayName(ParameterNode node) {
      return node.getName();
   }

   public XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni) {
      XiNode el = factory.createElement(XsdSchema.ELEMENT_NAME);
      String qname = getTypeQName(node, ni, null);
      XiAttribute.setStringValue(el, "name", node.getName());
      XiAttribute.setStringValue(el, "type", qname);
      if (node.getDefaultValue() != null) {
         XiAttribute.setStringValue(el, "default", node.getDefaultValue());
      }
      XsdSchema.writeOccurs(el, node.getMin(), node.getMax());
      return el;
   }

   public ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni) {
      if (node.getName().equals(XsdSchema.ELEMENT_NAME)) {
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
         readOccursAttributes(pn, node);
         pn.setName(name);
         String defValue = XiAttribute.getStringValue(node, "default");
         pn.setDefaultValue(defValue);
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

         //WCETODO --- remove this hack!
         if ((XSDL.NAMESPACE.equals(en.getNamespaceURI())) || ICNAMESPACE.equals(en.getNamespaceURI())
                 || UtilitySchema.UTILITY_SCHEMA_NS.equals(en.getNamespaceURI())) {
            convertXsdType(pn, en.getLocalName());
         }
         else {
            convertType(pn, en);
         }


         return pn;
      }
      return null;
   }

   static void convertType(ParameterNode node, ExpandedName name) {
      //String loc = (String) mNamespaceToLocationMap.get(namespace);
      Object details = TypeRefCategory.INSTANCE.readRefDetails(name);
      node.setContentModelDetails(details);
      node.setTypeCategory(TypeRefCategory.INSTANCE);
   }

   static void convertXsdType(ParameterNode node, String name) {
      TypeCategory cat = null;
      ParameterEditor ed = node.getEditor();
      TypeCategory[] cats = ed.getTypeCategories();
      for (int i = 0; i < cats.length; i++) {
         String[] types = cats[i].getXsdTypes();
         for (int xi = 0; xi < types.length; xi++) {
            if (types[xi].equals(name)) {
               cat = cats[i];
               break;
            }
         }
         if (cat != null) {
            break;
         }
      }
      if (cat == null) {
         node.setTypeCategory(StringCategory.INSTANCE);
         return;
      }
      Object details = cat.readDetails(name);
      node.setTypeCategory(cat);
      node.setContentModelDetails(details);
   }


   public ParameterNode fromXType(SmSequenceType type, SmCardinality oc, ParameterEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   public Object readDetails(ParameterNode node, String typeName) {
      return node.getTypeCategory().readDetails(typeName);
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
      return ParameterEditorResources.ELEMENT_OF_TYPE;
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
