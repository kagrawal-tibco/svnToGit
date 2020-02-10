package com.tibco.cep.studio.ui.advance.event.payload;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.SimpleNamespaceContextRegistry;
import com.tibco.cep.mapper.xml.xdata.UtilitySchema;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
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
 * The category representing an element where the type is declared by reference.<br>
 * WCETODO --- Replace most of the implementation with {@link com.tibco.cep.studio.mapper.ui.xmlui.XsdTypePanel}.
 */
public class ElementTypeRefCategory extends ContentModelCategory {

   private static String ICNAMESPACE = "http://www.tibco.com/namespaces/tnt/plugins/inconcert/config";
   public static final ContentModelCategory INSTANCE = new ElementTypeRefCategory();

   private ElementTypeRefCategory() {
   }

   public JComponent createEditor(ParameterPayloadNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
     // return node.getTypeCategory().createEditor(changeListener, detailsValue, uiAgent,
       //                                          namespaceContextRegistry, importRegistry);
	   return null;
   }

   public SmSequenceType computeXType(Object node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      // ElementTypeRef isn't allowed in XTypes.
      throw new IllegalArgumentException("Not supported here");
   }

   private String getTypeQName(Object node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
	   if(node instanceof PayloadTreeModelChild)
      return ((PayloadTreeModelChild)node).getTypeCategory().getTypeQName(((PayloadTreeModelChild)node).getContentModelDetails(), mapper, uiAgent);
	   if(node instanceof PayloadTreeModelParent)
		      return ((PayloadTreeModelParent)node).getTypeCategory().getTypeQName(((PayloadTreeModelParent)node).getContentModelDetails(), mapper, uiAgent);
	   return null;
   }

   public String getDisplayName(Object node) {
	  if(node instanceof PayloadTreeModelChild)
		  return ((PayloadTreeModelChild)node).getName();
	  if(node instanceof PayloadTreeModelParent)
		  return ((PayloadTreeModelParent)node).getName();
	  return null;
   }

   public XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni) {
		if (node instanceof PayloadTreeModelChild) {
			XiNode el = factory.createElement(XsdSchema.ELEMENT_NAME);
			String qname = getTypeQName(node, ni, null);
			XiAttribute.setStringValue(el, "name", ((PayloadTreeModelChild)node).getName());
			XiAttribute.setStringValue(el, "type", qname);
			if (((PayloadTreeModelChild)node).getDefaultValue() != null) {
				XiAttribute.setStringValue(el, "default",
						((PayloadTreeModelChild)node).getDefaultValue());
			}
			XsdSchema.writeOccurs(el, ((PayloadTreeModelChild)node).getMin(), ((PayloadTreeModelChild)node).getMax());
			return el;
		}
		if (node instanceof PayloadTreeModelParent) {
			XiNode el = factory.createElement(XsdSchema.ELEMENT_NAME);
			String qname = getTypeQName(node, ni, null);
			XiAttribute.setStringValue(el, "name", ((PayloadTreeModelParent)node).getName());
			XiAttribute.setStringValue(el, "type", qname);
			if (((PayloadTreeModelParent)node).getDefaultValue() != null) {
				XiAttribute.setStringValue(el, "default",
						((PayloadTreeModelParent)node).getDefaultValue());
			}
			XsdSchema.writeOccurs(el, ((PayloadTreeModelParent)node).getMin(), ((PayloadTreeModelParent)node).getMax());
			return el;
		}
		return null;
   }

   public Object fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni,Object parentNode) {
		if (parentNode == null) {
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
				PayloadTreeModelParent pn = new PayloadTreeModelParent(null,
						null, null, null, ed);
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
				} catch (Exception e) {
					en = ExpandedName.makeName(qn.getLocalName());
				}

				// WCETODO --- remove this hack!
				if ((XSDL.NAMESPACE.equals(en.getNamespaceURI()))
						|| ICNAMESPACE.equals(en.getNamespaceURI())
						|| UtilitySchema.UTILITY_SCHEMA_NS.equals(en
								.getNamespaceURI())) {
					convertXsdType(pn, en.getLocalName());
				} else {
					convertType(pn, en);
				}

				return pn;
			}
		}
		else{
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
				PayloadTreeModelChild pn = new PayloadTreeModelChild(null,null, null, null, ed);
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
				} catch (Exception e) {
					en = ExpandedName.makeName(qn.getLocalName());
				}

				// WCETODO --- remove this hack!
				if ((XSDL.NAMESPACE.equals(en.getNamespaceURI()))
						|| ICNAMESPACE.equals(en.getNamespaceURI())
						|| UtilitySchema.UTILITY_SCHEMA_NS.equals(en
								.getNamespaceURI())) {
					convertXsdType(pn, en.getLocalName());
				} else {
					convertType(pn, en);
				}

				return pn;
			}
			
		}
      return null;
   }

   static void convertType(Object node, ExpandedName name) {
      //String loc = (String) mNamespaceToLocationMap.get(namespace);
		if (node instanceof PayloadTreeModelChild) {
			Object details = TypeRefCategory.INSTANCE.readRefDetails(name);
			((PayloadTreeModelChild)node).setContentModelDetails(details);
			((PayloadTreeModelChild)node).setTypeCategory(TypeRefCategory.INSTANCE);
		}
		if (node instanceof PayloadTreeModelParent) {
			Object details = TypeRefCategory.INSTANCE.readRefDetails(name);
			((PayloadTreeModelParent)node).setContentModelDetails(details);
			((PayloadTreeModelParent)node).setTypeCategory(TypeRefCategory.INSTANCE);
		}
   }

   static void convertXsdType(Object node, String name) {
		if (node instanceof PayloadTreeModelChild) {
			TypeCategory cat = null;
			ParameterPayloadEditor ed = ((PayloadTreeModelChild)node).getEditor();
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
				((PayloadTreeModelChild)node).setTypeCategory(StringCategory.INSTANCE);
				return;
			}
			Object details = cat.readDetails(name);
			((PayloadTreeModelChild)node).setTypeCategory(cat);
			((PayloadTreeModelChild)node).setContentModelDetails(details);
		}
		if (node instanceof PayloadTreeModelParent) {
			TypeCategory cat = null;
			ParameterPayloadEditor ed = ((PayloadTreeModelParent)node).getEditor();
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
				((PayloadTreeModelParent)node).setTypeCategory(StringCategory.INSTANCE);
				return;
			}
			Object details = cat.readDetails(name);
			((PayloadTreeModelParent)node).setTypeCategory(cat);
			((PayloadTreeModelParent)node).setContentModelDetails(details);
		}
   }


   public ParameterPayloadNode fromXType(SmSequenceType type, SmCardinality oc, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   public Object readDetails(Object node, String typeName) {
	   if(node instanceof PayloadTreeModelChild)
      return ((PayloadTreeModelChild)node).getTypeCategory().readDetails(typeName);
	   if(node instanceof PayloadTreeModelChild)
		      return ((PayloadTreeModelParent)node).getTypeCategory().readDetails(typeName);
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
		if (node instanceof PayloadTreeModelChild) {

			if (((PayloadTreeModelParent)node).getTypeCategory() == null) {
				((PayloadTreeModelParent)node).setTypeCategory(((PayloadTreeModelParent)node).getEditor().getDefaultTypeCategory());
			}
			return ((PayloadTreeModelParent)node).getTypeCategory().createDetails();
		}
		return null;
   }

   public Object getEditorValue(ParameterPayloadNode node, JComponent c) {
    //  return node.getTypeCategory().getEditorValue(c);
	   return null;
   }

   public String getDisplayName() {
      return ParameterEditorResources.ELEMENT_OF_TYPE;
   }

   public Icon getDisplayIcon(Object node) {
		if (node instanceof PayloadTreeModelChild) {
			if (node == null) {
				return DataIcons.getTypeReferenceIcon();
			}
			return ((PayloadTreeModelChild)node).getTypeCategory().getDisplayIcon();
		}
		if (node instanceof PayloadTreeModelParent) {
			if (node == null) {
				return DataIcons.getTypeReferenceIcon();
			}
			return ((PayloadTreeModelParent)node).getTypeCategory().getDisplayIcon();
		}
		return null;
   }

   public boolean isLeaf() {
      return true;
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
