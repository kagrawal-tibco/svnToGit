package com.tibco.cep.studio.ui.advance.event.payload;

import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditorResources;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelChild;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelParent;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The category representing an element w/ a sequence(1,1) immediately underneath it.
 */
public class ElementSequenceCategory extends ContentModelCategory {

   public static final ContentModelCategory INSTANCE = new ElementSequenceCategory();

   private ElementSequenceCategory() {
   }

   public JComponent createEditor(ParameterPayloadNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry,
                                  ImportRegistry importRegistry) {
      return null;
   }

   public Object createDetails(ParameterPayloadNode node) {
      return null;
   }

   public boolean hasType() {
      return false;
   }

   public String getDisplayName(Object node) {
	   if(node instanceof PayloadTreeModelChild)
			
      return ((PayloadTreeModelChild)node).getName();
	   if(node instanceof PayloadTreeModelParent)
			
		      return ((PayloadTreeModelParent)node).getName();
	   return null;
   }

   public SmSequenceType computeXType(ParameterPayloadNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      // doesn't work w/ xtype editor.
      throw new IllegalStateException("XType not supported");
   }

   public XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni) {

		if (node instanceof PayloadTreeModelChild) {
			XiNode el = factory.createElement(XsdSchema.ELEMENT_NAME);

			XiNode ct = el.getNodeFactory().createElement(
					XsdSchema.COMPLEX_TYPE_NAME);
			el.appendChild(ct);
			XiAttribute.setStringValue(el, "name", ((PayloadTreeModelChild)node).getNodeName());

			XsdSchema.writeOccurs(el, ((PayloadTreeModelChild)node).getMin(), ((PayloadTreeModelChild)node).getMax());

			XiNode ctr;
			// With 1 node content, don't write out an implicit sequence:
			if (!isInlineCandidate(((PayloadTreeModelChild)node))) {
				// Write out implicit sequence:
				ctr = el.getNodeFactory()
						.createElement(XsdSchema.SEQUENCE_NAME);
				ct.appendChild(ctr);
				writeChildNodes(factory, node, ctr, ni, Boolean.FALSE);
			} else {
				writeChildNodes(factory, node, ct, ni, Boolean.FALSE);
			}
			// write Attributes last:
			writeChildNodes(factory, node, ct, ni, Boolean.TRUE);
			return el;
		}
		if (node instanceof PayloadTreeModelParent) {
			XiNode el = factory.createElement(XsdSchema.ELEMENT_NAME);

			XiNode ct = el.getNodeFactory().createElement(
					XsdSchema.COMPLEX_TYPE_NAME);
			el.appendChild(ct);
			XiAttribute.setStringValue(el, "name", ((PayloadTreeModelParent)node).getNodeName());

			XsdSchema.writeOccurs(el, ((PayloadTreeModelParent)node).getMin(), ((PayloadTreeModelParent)node).getMax());

			XiNode ctr;
			// With 1 node content, don't write out an implicit sequence:
			if (!isInlineCandidate(((PayloadTreeModelParent)node))) {
				// Write out implicit sequence:
				ctr = el.getNodeFactory()
						.createElement(XsdSchema.SEQUENCE_NAME);
				ct.appendChild(ctr);
				writeChildNodes(factory, node, ctr, ni, Boolean.FALSE);
			} else {
				writeChildNodes(factory, node, ct, ni, Boolean.FALSE);
			}
			// write Attributes last:
			writeChildNodes(factory, node, ct, ni, Boolean.TRUE);
			return el;
		}
		return null;
   }

   public PayloadTreeModelParent fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni,Object parentNode) {
      if (node.getName().equals(XsdSchema.ELEMENT_NAME)) {
         // we don't handle these 2 cases:
         if (XiAttribute.getNode(node, "ref") != null) {
            return null;
         }
         ;
         if (XiAttribute.getNode(node, "type") != null) {
            return null;
         }
         ;
         String name = XiAttribute.getStringValue(node, "name");
         PayloadTreeModelParent pn = new PayloadTreeModelParent(null, null, null, null, ed);
         pn.setContentModelCategory(this);
         readOccursAttributes(pn, node);
         pn.setName(name == null ? "" : name);

         XiNode firstChild = XiChild.getFirstChild(node);
         if (firstChild != null) {
            ExpandedName cn = firstChild.getName();
            if (cn.equals(XsdSchema.COMPLEX_TYPE_NAME)) {
               readAttributes(ed, firstChild, pn, ni);
               XiNode ffc = XiChild.getFirstChild(firstChild);
               // By default, a complex type shows a 1,1 sequence underneath it:
               if (ffc != null && ffc.getName().equals(XsdSchema.SEQUENCE_NAME) && hasNeitherMinNorMaxOccurs(ffc)) {
                  readChildren(ed, pn, ffc, ni);
               }
               else if (ffc != null) {
                  pn.setContentModelCategory(ElementSequenceCategory.INSTANCE);
                  readChildren(ed, pn, firstChild, ni);
               }
            }
         }
         return pn;
      }
      return null;
   }

   @SuppressWarnings("rawtypes")
private void readAttributes(ParameterPayloadEditor ed, XiNode n, PayloadTreeModelParent pn2, NamespaceContextRegistry ni) {
      Iterator c = XiChild.getIterator(n);
      while (c.hasNext()) {
         XiNode cn = (XiNode) c.next();
         if (cn.getNodeKind() == XmlNodeKind.ELEMENT) {
            ExpandedName en = cn.getName();
            if (en.equals(XsdSchema.SEQUENCE_NAME) || en.equals(XsdSchema.CHOICE_NAME) || en.equals(XsdSchema.ALL_NAME) || en.equals(XsdSchema.GROUP_NAME)) {
               continue;
            }
            Object pn = ed.buildTree(cn, ni,null);
            if (pn != null) {
               pn2.addNode((PayloadTreeModelChild)pn);
            }
         }
      }
   }

   private boolean hasNeitherMinNorMaxOccurs(XiNode attrs) {
      return XiAttribute.getNode(attrs, "minOccurs") == null && XiAttribute.getNode(attrs, "maxOccurs") == null;
   }

   public ParameterPayloadNode fromXType(SmSequenceType type, SmCardinality oc, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   /**
    * Indicates if the element node has just a single child of type sequence, choice, or all.
    *
    * @param node
    */
   private static boolean isInlineCandidate(Object node) {
		if (node instanceof PayloadTreeModelChild) {
			if (getNonAttributeCount(((PayloadTreeModelChild)node)) != 1) {
				return false;
			}
			PayloadTreeModelChild ch = getFirstNonAttribute(((PayloadTreeModelChild)node));
			ContentModelCategory cmc = ch.getContentModelCategory();
			if (cmc == ChoiceCategory.INSTANCE
					|| cmc == SequenceCategory.INSTANCE
					|| cmc == GroupRefCategory.INSTANCE) {
				// WCETODO add group, make this a method on category...
				return true;
			}
			return false;
		}
		if (node instanceof PayloadTreeModelParent) {
			if (getNonAttributeCount(((PayloadTreeModelParent)node)) != 1) {
				return false;
			}
			PayloadTreeModelChild ch = getFirstNonAttribute(((PayloadTreeModelParent)node));
			ContentModelCategory cmc = ch.getContentModelCategory();
			if (cmc == ChoiceCategory.INSTANCE
					|| cmc == SequenceCategory.INSTANCE
					|| cmc == GroupRefCategory.INSTANCE) {
				// WCETODO add group, make this a method on category...
				return true;
			}
			return false;
		}
		return false;
	   
   }

   private static int getNonAttributeCount(Object node) {
		if (node instanceof PayloadTreeModelChild) {
			int ct = 0;
			/*try {
				for (int i = 0; i < ((PayloadTreeModelChild)node).getChildCount(); i++) {
					PayloadTreeModelChild pn = (PayloadTreeModelChild) ((PayloadTreeModelChild)node).getChild(i);
					if (pn.getContentModelCategory() != AttributeTypeRefCategory.INSTANCE) {
						ct++;
					}
				}
			} catch (Exception e) {
				System.out.println("Errrrr");
			}*/
			return ct;
		}
		if (node instanceof PayloadTreeModelParent) {
			int ct = 0;
			try {
				for (int i = 0; i < ((PayloadTreeModelParent)node).getObjList().size(); i++) {
					PayloadTreeModelChild pn = (PayloadTreeModelChild) ((PayloadTreeModelParent)node).getObjList().get(i);
					if (pn.getContentModelCategory() != AttributeTypeRefCategory.INSTANCE) {
						ct++;
					}
				}
			} catch (Exception e) {
				System.out.println("Errrrr");
			}
			return ct;
		}
		return 0;
		
   }

   private static PayloadTreeModelChild getFirstNonAttribute(Object node) {
		if (node instanceof PayloadTreeModelChild) {
			for (int i = 0; i < ((PayloadTreeModelChild)node).getChildCount(); i++) {
				PayloadTreeModelChild pn = (PayloadTreeModelChild) ((PayloadTreeModelChild)node).getChild(i);
				if (pn.getContentModelCategory() != AttributeTypeRefCategory.INSTANCE) {
					return pn;
				}
			}

		}
		if (node instanceof PayloadTreeModelParent) {
			for (int i = 0; i < ((PayloadTreeModelParent)node).getObjList().size(); i++) {
				PayloadTreeModelChild pn = (PayloadTreeModelChild) ((PayloadTreeModelParent)node).getObjList().get(i);
				if (pn.getContentModelCategory() != AttributeTypeRefCategory.INSTANCE) {
					return pn;
				}
			}

		}
		return null;
   }

   public Object getEditorValue(ParameterPayloadNode node, JComponent c) {
      return null;
   }

   public String getDisplayName() {
      return ParameterEditorResources.COMPLEX_ELEMENT;
   }

   public Icon getDisplayIcon(ParameterPayloadNode node) {
      return DataIcons.getSequenceIcon();
   }

   public boolean isLeaf() {
      return false;
   }

@Override
public SmSequenceType computeXType(Object node, NamespaceContextRegistry nm,
		UIAgent uiAgent, SmComponentProviderEx smComponentProviderEx) {
	// TODO Auto-generated method stub
	return null;
}


}
