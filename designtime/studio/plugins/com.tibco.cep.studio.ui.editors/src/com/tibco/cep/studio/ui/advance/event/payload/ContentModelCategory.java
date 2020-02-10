package com.tibco.cep.studio.ui.advance.event.payload;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelChild;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelParent;
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
   public abstract JComponent createEditor(ParameterPayloadNode node, ChangeListener changeListener,
                                           Object detailsValue, UIAgent uiAgent,
                                           NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry);

   public abstract Object getEditorValue(ParameterPayloadNode node, JComponent editor);

   public abstract Object createDetails(ParameterPayloadNode node); // null for none.

   public abstract String getDisplayName();

   public abstract Icon getDisplayIcon(ParameterPayloadNode node);

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
   public abstract SmSequenceType computeXType(Object node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smComponentProviderEx);

   public abstract Object fromXType(SmSequenceType type, SmCardinality oc, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni);

   public abstract XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni);

   /**
    * @param parentNode 
 * @return A parameter node, or null if this category is not appropriate for this input.
    */
   public abstract Object fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni, Object parentNode);

   /*
    * Overloaded for both type & ref.
    *
   public String getTypeQName(ParameterPayloadNode node, NamespaceImporter mapper, DesignerDocument designerDocument)
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

   public abstract String getDisplayName(Object node);

   public Object readDetails(ParameterPayloadNode node, String typeName) {
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

   protected void writeChildNodes(XiFactory factory, Object parent, XiNode parentXi, NamespaceContextRegistry ni) {
      writeChildNodes(factory, parent, parentXi, ni, null);
   }

   public PayloadTreeModelChild createNewChild(ParameterPayloadEditor editor) {
	   PayloadTreeModelChild n = new PayloadTreeModelChild(null, null, null, null, editor);
       n.setContentModelCategory(ElementTypeRefCategory.INSTANCE);
       return n;
   }

   protected void writeChildNodes(XiFactory factory, Object parent, XiNode parentXi, NamespaceContextRegistry ni, Boolean writeAtts) {
	   
	  if(parent instanceof PayloadTreeModelChild){ 
//		  int cc = ((PayloadTreeModelChild)parent).getChildCount();
		  ParameterPayloadEditor ed = ((PayloadTreeModelChild)parent).getEditor();
//		  for (int i = 0; i < cc; i++) {
//			  PayloadTreeModelChild child = ((PayloadTreeModelChild)parent).getChild(i);
//			  if (writeAtts != null && child != null) {
//				  boolean isAttr = child.getContentModelCategory() == AttributeTypeRefCategory.INSTANCE;
//				  if (isAttr != writeAtts.booleanValue()) {
//					  continue; // skip it!
//				  }
//			  }
			  if(((PayloadTreeModelChild)parent)!= null){
				  XiNode c = ed.toNode(factory, ((PayloadTreeModelChild)parent), ni);
				  parentXi.appendChild(c);
			  }
	  }
	  if(parent instanceof PayloadTreeModelParent){ 
		  int cc = ((PayloadTreeModelParent)parent).getObjList().size();
		  ParameterPayloadEditor ed = ((PayloadTreeModelParent)parent).getEditor();
		  for (int i = 0; i < cc; i++) {
			  PayloadTreeModelChild child = (PayloadTreeModelChild) ((PayloadTreeModelParent)parent).getObjList().get(i);
			  if (writeAtts != null && child != null) {
				  boolean isAttr = child.getContentModelCategory() == AttributeTypeRefCategory.INSTANCE;
				  if (isAttr != writeAtts.booleanValue()) {
					  continue; // skip it!
				  }
			  }
			  if(child!= null){
				  XiNode c = ed.toNode(factory, child, ni);
				  parentXi.appendChild(c);
			  }
		  }
	  }
   }

   protected void readOccursAttributes(Object node, XiNode attrs) {
      for (XiNode atAttr = attrs.getFirstAttribute(); atAttr != null; atAttr = atAttr.getNextSibling()) {
         String rawName = atAttr.getName().getLocalName();
         String str = atAttr.getStringValue();
			if (node instanceof PayloadTreeModelChild) {

				if ("minOccurs".equals(rawName)) {
					((PayloadTreeModelChild)node).setMin(Integer.parseInt(str));
				}
				if ("maxOccurs".equals(rawName)) {
					if (str.equals("unbounded")) {
						((PayloadTreeModelChild)node).setMax(SmParticle.UNBOUNDED);
					} else {
						((PayloadTreeModelChild)node).setMax(Integer.parseInt(str));
					}
				}
			}
			if (node instanceof PayloadTreeModelParent) {

				if ("minOccurs".equals(rawName)) {
					((PayloadTreeModelParent)node).setMin(Integer.parseInt(str));
				}
				if ("maxOccurs".equals(rawName)) {
					if (str.equals("unbounded")) {
						((PayloadTreeModelParent)node).setMax(SmParticle.UNBOUNDED);
					} else {
						((PayloadTreeModelParent)node).setMax(Integer.parseInt(str));
					}
				}
			}
      }
   }

   protected void readAttrOccursAttributes(Object node, XiNode attrs) {
      // the default:
		if (node instanceof PayloadTreeModelChild) {
			((PayloadTreeModelChild)node).setMin(0);
			((PayloadTreeModelChild)node).setMax(1);
			for (XiNode atAttr = attrs.getFirstAttribute(); atAttr != null; atAttr = atAttr
					.getNextSibling()) {
				String rawName = atAttr.getName().getLocalName();
				String str = atAttr.getStringValue();

				if ("use".equals(rawName)) {
					if (str.equals("required")) {
						((PayloadTreeModelChild)node).setMin(1);
					}
				}
			}
		}
		if (node instanceof PayloadTreeModelParent) {
			((PayloadTreeModelParent)node).setMin(0);
			((PayloadTreeModelParent)node).setMax(1);
			for (XiNode atAttr = attrs.getFirstAttribute(); atAttr != null; atAttr = atAttr
					.getNextSibling()) {
				String rawName = atAttr.getName().getLocalName();
				String str = atAttr.getStringValue();

				if ("use".equals(rawName)) {
					if (str.equals("required")) {
						((PayloadTreeModelParent)node).setMin(1);
					}
				}
			}
		}
   }

   @SuppressWarnings("rawtypes")
protected void readChildren(ParameterPayloadEditor ed, Object node, XiNode n, NamespaceContextRegistry ni) {
      Iterator c = XiChild.getIterator(n);
      while (c.hasNext()) {
         XiNode cn = (XiNode) c.next();
         if (cn.getNodeKind() == XmlNodeKind.ELEMENT) {
            Object pn = ed.buildTree(cn, ni,node);
            if (pn != null && ((PayloadTreeModelParent) node).getObjList() != null) {
               ((PayloadTreeModelParent) node).getObjList().add((PayloadTreeModelChild)pn);
            } else {
            	((PayloadTreeModelParent) node).setObjList(new ArrayList<Object>());
            	((PayloadTreeModelParent) node).getObjList().add((PayloadTreeModelChild)pn);
            }
         }
      }
   }

   protected void readChildren(ParameterPayloadEditor ed, Object node, SmSequenceType[] children, NamespaceContextRegistry ni) {
      for (int i = 0; i < children.length; i++) {
         SmSequenceType c = children[i];
         PayloadTreeModelChild pn = ed.buildNode(c, ni);
         if (pn != null) {
            ((PayloadTreeModelChild) node).addNode(pn);
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
