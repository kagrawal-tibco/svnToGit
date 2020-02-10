package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.ReadFromXSLT;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeCopyPasteHandler;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeDragNDropHandler;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeExpansionHandler;
import com.tibco.cep.studio.mapper.ui.edittree.simple.SimpleTreeModel;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The tree model for the {@link BindingTree}.
 */
public final class BindingTreeModel extends SimpleTreeModel implements EditableTreeDragNDropHandler, EditableTreeCopyPasteHandler, EditableTreeExpansionHandler {
   private BindingTree m_tree;

   public BindingTreeModel(BindingTree tree, BindingNode root) {
      super(root);
      m_tree = tree;
   }

   public boolean canHaveChildren(Object node) {
      BindingNode bn = (BindingNode) node;
      return bn.canHaveChildren();
   }

   public Object createNewChild(Object parent) {
      BindingNode bn = (BindingNode) parent;
      TemplateReport tr = bn.getTemplateReport();
      final Binding b = StatementDialog.showNewDialog(m_tree,
                                                      m_tree.getImportRegistry(),
                                                      m_tree.getUIAgent(),
                                                      m_tree.getStatementPanelManager(),
                                                      tr,
                                                      false // false -> we're building a child for this one.
      );
      if (b == null) {
         return null;
      }
      /*
      return new BasicTree.BasicNodeBuilder()
      {
          public BasicTreeNode buildNode()
          {
              return new BindingNode(b.cloneDeep(),(BasicTreeModel)getModel());
          }
      };*/
      return new BindingNode(bn.getTree(), b); //WCETODO make it a builder.
   }

   public Object createNewParent(Object optionalAroundNode) {
      BindingNode bn = (BindingNode) optionalAroundNode;
      TemplateReport tr = bn == null ? null : bn.getTemplateReport();
      final Binding b = StatementDialog.showNewDialog(m_tree,
                                                      m_tree.getImportRegistry(),
                                                      m_tree.getUIAgent(),
                                                      m_tree.getStatementPanelManager(),
                                                      tr,
                                                      true // true -> we're building a binding to wrap this one.
      );
      if (b == null) {
         return null;
      }
      return new BindingNode(bn.getTree(), b);
   }

   public Object getDeleteReplacement(Object node) {
      BindingNode bn = (BindingNode) node;
      return bn.createReplacementMarker();
   }

   public boolean getAllowsRootNull() {
      return false;
   }

   public boolean isRootNull() {
      return false;
   }

   public void setRootNull(boolean nullRoot) {
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
      if (!(node instanceof BindingNode)) {
         return null; // don't know about these...
      }
      BindingNode pn = (BindingNode) node;
      return pn;//?pn.copy();
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
      final String xml = (String) got;
      if (xml == null || xml.length() == 0) {
         return null;
      }
      Binding root;
      try {
         root = ReadFromXSLT.readFragment(xml);
      }
      catch (Throwable t) {
         throw new SAXException(""); // can't paste that!
      }
      if (root == null) {
         return null;
      }
      // Virtualize it:
      Binding nroot = m_tree.getBindingVirtualizer().virtualize(root, new DefaultCancelChecker());
      // Move all root namespaces north:
      NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(m_tree.getTemplateEditorConfiguration().getBinding());
      BindingNamespaceManipulationUtils.migrateNamespaceDeclarations(nroot, ni);

      BindingNode r = new BindingNode(m_tree, nroot);
      return r;
   }

   public void setRoot(Object node) {
      super.setRoot(node);
       // When we change the root of the binding tree model, we need to update the
       // template editor configuration of the tree because that stores the new binding as well.
       // We also need to make sure the stylesheet binding that contained the old template binding now contains
       // the new template binding. I made this change to fix defect 1-1WE1LL. When a complete XSLT is pasted
       // at the root of the tree, the data model was not getting updated properly. Now it works because
       // of the following code change - Manoj Patwardhan June 20, 2005
       TemplateEditorConfiguration config = m_tree.getTemplateEditorConfiguration();
       Binding newBinding = ((BindingNode)node).getBinding();
       Binding oldBinding = config.getBinding();
       if (oldBinding != newBinding) {
           Binding parent = newBinding.getParent();
           if ( parent == null ) {
               Binding oldParent = oldBinding.getParent();
               if (oldParent != null) {
                   oldParent.removeAllChildren();
                   oldParent.addChild(newBinding);
               }
           }
           config.setBinding((TemplateBinding)newBinding);
       }
      // Major change, just do this & make sure everything is redone:
      m_tree.repair();
      //m_tree.rebuild();
      m_tree.markReportDirty();
   }

   public Transferable getTransferableForNode(Object node) {
      BindingNode pnode = (BindingNode) node;
      Binding binding = pnode.getBinding();
      m_tree.getBindingVirtualizer();
	Binding nb = BindingVirtualizer.normalize(binding, null);
      Binding bp = binding.getParent();
      if (nb == null) {
         return null;
      }
      if (bp != null) {
         // Get context namespace decls:
         NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(bp);
         NamespaceContextRegistry to = BindingNamespaceManipulationUtils.createNamespaceImporter(nb);
         NamespaceManipulationUtils.addAllNamespaceDeclarations(to, ni);
      }
      String strVersion = nb.toString();
      return new StringSelection(strVersion);
   }

   public Object getNodeIdentity(Object node) {
      BindingNode bnode = (BindingNode) node;
      TemplateReport tr = bnode.getTemplateReport();
      if (tr == null) {
         return null;
      }
      SmSequenceType xt = tr.getComputedType();
      if (xt == null) {
         return null;
      }
      // Return the particle term, if there:
      return SmSequenceTypeSupport.stripOccursAndParens(xt).getParticleTerm();
   }

   public boolean hasContent(Object node) {
      BindingNode bnode = (BindingNode) node;
      if (bnode.getBinding() instanceof MarkerBinding) {
         // Markers aren't content.
         return false;
      }
      return true;
   }

   public boolean hasError(Object node) {
      BindingNode bnode = (BindingNode) node;
      TemplateReport tr = bnode.getTemplateReport();
      return tr != null && !tr.isRecursivelyErrorFree();
   }

   public boolean isContentSupported() {
      return true;
   }

   public boolean isErrorSupported() {
      return true;
   }
}

