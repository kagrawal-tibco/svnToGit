package com.tibco.cep.studio.ui.advance.event.payload;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.nsutils.SimpleNamespaceContextRegistry;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeCopyPasteHandler;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeDragNDropHandler;
import com.tibco.cep.studio.mapper.ui.edittree.simple.SimpleTreeModel;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelParent;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * The tree model used by {@link ParameterPayloadEditorTree}.<br>
 * Also handles drag'n'drop/copy-paste duties.
 */
class ParameterEditorTreeModel extends SimpleTreeModel implements EditableTreeDragNDropHandler, EditableTreeCopyPasteHandler {
   private boolean m_null = false;
   private boolean m_allowsNull = true;
//   private final ParameterPayloadEditor m_editor;

   public ParameterEditorTreeModel(ParameterPayloadEditor ed, PayloadTreeModelParent root) {
     // super(root);
//      m_editor = ed;
   }

   public boolean canHaveChildren(Object node) {
	   PayloadTreeModelParent pn = (PayloadTreeModelParent) node;
      return !pn.isLeaf();
   }

   public Object createNewChild(Object parent) {
	   PayloadTreeModelParent pn = (PayloadTreeModelParent) parent;
      return pn.createNewChild();
   }

   public Object createNewParent(Object optionalAroundNode) {
     /* ParameterPayloadNode root = (ParameterPayloadNode) getRoot();
      ParameterPayloadNode gp = new ParameterPayloadNode(root.getEditor());
      gp.setName("group");
      gp.setContentModelCategory(ElementSequenceCategory.INSTANCE);
      return gp;*/
	   return null;
   }

   public boolean getAllowsRootNull() {
      return m_allowsNull;
   }

   public void setRoot(Object node) {
      super.setRoot(node);
   }

   public void setAllowsRootNull(boolean val) {
      m_allowsNull = val;
   }

   public boolean isRootNull() {
      return m_allowsNull && m_null;
   }

   public void setRootNull(boolean nullRoot) {
      if (m_null != nullRoot) {
         m_null = nullRoot;
         if (nullRoot) {
         //   ParameterPayloadNode pn = (ParameterPayloadNode) getRoot();
            // clear it out:
         //   setRoot(new ParameterPayloadNode(pn.getEditor()));
         }
      }
   }

   /**
    * Drag'n'drop stuff:
    */
   public Object createNodeFromDragObject(Object dragObject) {
      // just return the node itself:
      return dragObject;
   }

   /**
    * Drag'n'drop stuff:
    */
   public Object getDragObjectForNode(Object node) {
      if (!(node instanceof ParameterPayloadNode)) {
         return null; // don't know about these...
      }
      ParameterPayloadNode pn = (ParameterPayloadNode) node;
 //     return pn.copy();
      return null;
   }

   /**
    * Copy paste stuff.
    */
   public Object createNodeFromTransferable(Transferable object) throws SAXException {
      if (!object.isDataFlavorSupported(new StringSelection("").getTransferDataFlavors()[0])) {
         return null;
      }
      Object got;
      try {
         got = object.getTransferData(new StringSelection("").getTransferDataFlavors()[0]);
      }
      catch (Exception ufe) {
         return null;
      }
      final String strGot = (String) got;
      XiNode n;
      try {
         n = XiParserFactory.newInstance().parse(new InputSource(new StringReader(strGot)));
      }
      catch (Exception e) // catch SAX & IO (io is impossible)
      {
         // just throw generic exception (for formatting purposes):
         throw new SAXException("");
      }

      try {
         if (getRoot() == null) {
            return null;
         }
         ParameterPayloadEditor ed = null;// ((ParameterPayloadNode) getRoot()).getEditor();
         NamespaceContextRegistry ni = NamespaceManipulationUtils.createNamespaceImporter(n);
         XiNode firstEl = XiChild.getFirstChild(n);
         if (firstEl == null) {
            return null;
         }
         return ed.buildTree(firstEl, ni,null);
      }
      catch (Exception e) {
         e.printStackTrace(System.err);
         return null;
      }
   }

   public Transferable getTransferableForNode(Object node) {
      ParameterPayloadNode pnode = (ParameterPayloadNode) node;
      ParameterPayloadEditor ed = null;//pnode.getEditor();
      if (ed == null) {
         // sanity.
         return null;
      }
      XiFactory factory = XiFactoryFactory.newInstance();
      NamespaceContextRegistry ni = new SimpleNamespaceContextRegistry();
      XiNode n = ed.toNode(factory, pnode, ni);
      NamespaceManipulationUtils.addAllNamespaceDeclarations(NamespaceManipulationUtils.createNamespaceImporter(n), ni);
      String strVersion = XiSerializer.serialize(n);
      return new StringSelection(strVersion);
   }
}
