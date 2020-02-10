package com.tibco.cep.studio.mapper.ui.data.param;

import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
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

   public JComponent createEditor(ParameterNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry,
                                  ImportRegistry importRegistry) {
      return null;
   }

   public Object createDetails(ParameterNode node) {
      return null;
   }

   public boolean hasType() {
      return false;
   }

   public String getDisplayName(ParameterNode node) {
      return node.getName();
   }

   public SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      // doesn't work w/ xtype editor.
      throw new IllegalStateException("XType not supported");
   }

   public XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni) {
      XiNode el = factory.createElement(XsdSchema.ELEMENT_NAME);

      XiNode ct = el.getNodeFactory().createElement(XsdSchema.COMPLEX_TYPE_NAME);
      el.appendChild(ct);
      XiAttribute.setStringValue(el, "name", node.getName());

      XsdSchema.writeOccurs(el, node.getMin(), node.getMax());

      XiNode ctr;
      // With 1 node content, don't write out an implicit sequence:
      if (!isInlineCandidate(node)) {
         // Write out implicit sequence:
         ctr = el.getNodeFactory().createElement(XsdSchema.SEQUENCE_NAME);
         ct.appendChild(ctr);
         writeChildNodes(factory, node, ctr, ni, Boolean.FALSE);
      }
      else {
         writeChildNodes(factory, node, ct, ni, Boolean.FALSE);
      }
      // write Attributes last:
      writeChildNodes(factory, node, ct, ni, Boolean.TRUE);
      return el;
   }

   public ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni) {
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
         ParameterNode pn = new ParameterNode(ed);
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
private void readAttributes(ParameterEditor ed, XiNode n, ParameterNode node, NamespaceContextRegistry ni) {
      Iterator c = XiChild.getIterator(n);
      while (c.hasNext()) {
         XiNode cn = (XiNode) c.next();
         if (cn.getNodeKind() == XmlNodeKind.ELEMENT) {
            ExpandedName en = cn.getName();
            if (en.equals(XsdSchema.SEQUENCE_NAME) || en.equals(XsdSchema.CHOICE_NAME) || en.equals(XsdSchema.ALL_NAME) || en.equals(XsdSchema.GROUP_NAME)) {
               continue;
            }
            ParameterNode pn = ed.buildTree(cn, ni);
            if (pn != null) {
               node.addNode(pn);
            }
         }
      }
   }

   private boolean hasNeitherMinNorMaxOccurs(XiNode attrs) {
      return XiAttribute.getNode(attrs, "minOccurs") == null && XiAttribute.getNode(attrs, "maxOccurs") == null;
   }

   public ParameterNode fromXType(SmSequenceType type, SmCardinality oc, ParameterEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   /**
    * Indicates if the element node has just a single child of type sequence, choice, or all.
    *
    * @param node
    */
   private static boolean isInlineCandidate(ParameterNode node) {
      if (getNonAttributeCount(node) != 1) {
         return false;
      }
      ParameterNode ch = getFirstNonAttribute(node);
      ContentModelCategory cmc = ch.getContentModelCategory();
      if (cmc == ChoiceCategory.INSTANCE || cmc == SequenceCategory.INSTANCE || cmc == GroupRefCategory.INSTANCE) {
         //WCETODO add group, make this a method on category...
         return true;
      }
      return false;
   }

   private static int getNonAttributeCount(ParameterNode node) {
      int ct = 0;
      for (int i = 0; i < node.getChildCount(); i++) {
         ParameterNode pn = (ParameterNode) node.getChild(i);
         if (pn.getContentModelCategory() != AttributeTypeRefCategory.INSTANCE) {
            ct++;
         }
      }
      return ct;
   }

   private static ParameterNode getFirstNonAttribute(ParameterNode node) {
      for (int i = 0; i < node.getChildCount(); i++) {
         ParameterNode pn = (ParameterNode) node.getChild(i);
         if (pn.getContentModelCategory() != AttributeTypeRefCategory.INSTANCE) {
            return pn;
         }
      }
      return null;
   }

   public Object getEditorValue(ParameterNode node, JComponent c) {
      return null;
   }

   public String getDisplayName() {
      return ParameterEditorResources.COMPLEX_ELEMENT;
   }

   public Icon getDisplayIcon(ParameterNode node) {
      return DataIcons.getSequenceIcon();
   }

   public boolean isLeaf() {
      return false;
   }
}
