package com.tibco.cep.studio.mapper.ui.data.param;

import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmSequenceType;

/**
 * A base super-interface for type categories.  Because it handles both scalars
 * & complex, types and elements, it has alot of methods that only apply in certain
 * cases.
 */
public abstract class ContentModelCategory {
   public abstract JComponent createEditor(ParameterNode node, ChangeListener changeListener,
                                           Object detailsValue, UIAgent uiAgent,
                                           NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry);

   public abstract Object getEditorValue(ParameterNode node, JComponent editor);

   public abstract Object createDetails(ParameterNode node); // null for none.

   public abstract String getDisplayName();

   public abstract Icon getDisplayIcon(ParameterNode node);

   public abstract boolean hasType();

   /**
    * Converts the node into an XType.
    *
    * @param node         The node data.
    * @param nm           The namespace importer.
    * @param doc          The designer document
    * @param smComponentProviderEx a properly scoped component provider
    * @return An XType, never null.
    */
   public abstract SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smComponentProviderEx);

   public abstract ParameterNode fromXType(SmSequenceType type, SmCardinality oc, ParameterEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni);

   public abstract XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni);

   /**
    * @return A parameter node, or null if this category is not appropriate for this input.
    */
   public abstract ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni);

   /*
    * Overloaded for both type & ref.
    *
   public String getTypeQName(ParameterNode node, NamespaceImporter mapper, DesignerDocument designerDocument)
   {
       return "wrong!"; // should be called.
   }*/

   /**
    * Returns true if the category is specifically designed for this type.<br>
    * This is checked before checking {@link #canHandleRefType(String, String)}.
    */
   public boolean isSpecificForRefType(String namespace, String location) {
      return false;
   }

   /**
    * Returns true if the category is equiped to handle the type (not element) reference
    */
   public boolean canHandleRefType(String namespace, String location) {
      return false;
   }

   /**
    * Indicates if this allows repetition (e.g. attributes do not, everything else does)
    */
   public boolean canRepeat() {
      return true;
   }

   /**
    * Returns true if this category handles element references.
    */
   public boolean canHandleElementReferences() {
      return false;
   }

   public abstract String getDisplayName(ParameterNode node);

   public Object readDetails(ParameterNode node, String typeName) {
      return null;
   }

   /**
    * For a 'ref' element, if it is the root, we don't even have a schema, just an element reference.  This call
    * returns null for non-ref elements.  ref-elements return a string in this format<br>
    * {schema-location}element-name
    */
   public String getAsRootReference(NamespaceContextRegistry namespaceContextRegistry, Object details) {
      return null;
   }

   protected void writeChildNodes(XiFactory factory, ParameterNode parent, XiNode parentXi, NamespaceContextRegistry ni) {
      writeChildNodes(factory, parent, parentXi, ni, null);
   }

   public ParameterNode createNewChild(ParameterEditor editor) {
      ParameterNode n = new ParameterNode(editor);
      n.setContentModelCategory(ElementTypeRefCategory.INSTANCE);
      return n;
   }

   protected void writeChildNodes(XiFactory factory, ParameterNode parent, XiNode parentXi, NamespaceContextRegistry ni, Boolean writeAtts) {
      int cc = parent.getChildCount();
      ParameterEditor ed = parent.getEditor();
      for (int i = 0; i < cc; i++) {
         ParameterNode child = (ParameterNode) parent.getChild(i);
         if (writeAtts != null) {
            boolean isAttr = child.getContentModelCategory() == AttributeTypeRefCategory.INSTANCE;
            if (isAttr != writeAtts.booleanValue()) {
               continue; // skip it!
            }
         }
         XiNode c = ed.toNode(factory, child, ni);
         parentXi.appendChild(c);
      }
   }

   protected void readOccursAttributes(ParameterNode node, XiNode attrs) {
      for (XiNode atAttr = attrs.getFirstAttribute(); atAttr != null; atAttr = atAttr.getNextSibling()) {
         String rawName = atAttr.getName().getLocalName();
         String str = atAttr.getStringValue();

         if ("minOccurs".equals(rawName)) {
            node.setMin(Integer.parseInt(str));
         }
         if ("maxOccurs".equals(rawName)) {
            if (str.equals("unbounded")) {
               node.setMax(SmParticle.UNBOUNDED);
            }
            else {
               node.setMax(Integer.parseInt(str));
            }
         }
      }
   }

   protected void readAttrOccursAttributes(ParameterNode node, XiNode attrs) {
      // the default:
      node.setMin(0);
      node.setMax(1);
      for (XiNode atAttr = attrs.getFirstAttribute(); atAttr != null; atAttr = atAttr.getNextSibling()) {
         String rawName = atAttr.getName().getLocalName();
         String str = atAttr.getStringValue();

         if ("use".equals(rawName)) {
            if (str.equals("required")) {
               node.setMin(1);
            }
         }
      }
   }

   @SuppressWarnings("rawtypes")
protected void readChildren(ParameterEditor ed, ParameterNode node, XiNode n, NamespaceContextRegistry ni) {
      Iterator c = XiChild.getIterator(n);
      while (c.hasNext()) {
         XiNode cn = (XiNode) c.next();
         if (cn.getNodeKind() == XmlNodeKind.ELEMENT) {
            ParameterNode pn = ed.buildTree(cn, ni);
            if (pn != null) {
               node.addNode(pn);
            }
         }
      }
   }

   protected void readChildren(ParameterEditor ed, ParameterNode node, SmSequenceType[] children, NamespaceContextRegistry ni) {
      for (int i = 0; i < children.length; i++) {
         SmSequenceType c = children[i];
         ParameterNode pn = ed.buildNode(c, ni);
         if (pn != null) {
            node.addNode(pn);
         }
      }
   }


   /**
    * For a 'ref' element:
    */
   public Object readRefDetails(ExpandedName name) {
      return null;
   }

   public boolean canHaveName() {
      return true;
   }

   public boolean isLeaf() {
      return true;
   }
}
