package com.tibco.cep.studio.mapper.ui.data.param;

import java.util.ArrayList;

import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.bind.fix.XsdContentModelPath;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.cep.studio.mapper.ui.data.basic.ExtendedTreeModelListener;
import com.tibco.cep.studio.mapper.ui.data.utils.DocumentNameValidator;
import com.tibco.cep.studio.mapper.ui.data.utils.NameValidatingDocument;
import com.tibco.cep.studio.mapper.ui.data.utils.XMLNameDocument;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTree;
import com.tibco.cep.studio.mapper.ui.edittree.render.NameValueTreeCellEditor;
import com.tibco.cep.studio.mapper.ui.edittree.render.NameValueTreeCellRenderer;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;

/**
 * Used by {@link ParameterEditor}
 */
class ParameterEditorTree extends EditableTree {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JExtendedEditTextArea mField;
   private UIAgent uiAgent;
   private NameValidatingDocument mNameValidatingDocument;
   private ParameterEditorTreeModel m_model;
//   private ParameterEditor m_owner;
   private ArrayList<XsdContentModelChangeListener> m_changeListeners = new ArrayList<XsdContentModelChangeListener>();
//   private SmParticleTerm m_lastSnapshot;

   public ParameterEditorTree(UIAgent uiAgent,
                              ParameterEditor owner) {
      super(uiAgent);
      super.setTreeMode(true); // we are a true for sure.

      NameValueTreeCellRenderer renderer = new NameValueTreeCellRenderer(this, new ParameterEditorTreeCellRendererPlugin(this, uiAgent));
      super.setCellRenderer(renderer);
      NameValueTreeCellEditor editor = new NameValueTreeCellEditor(this, renderer);
      super.setCellEditor(editor);
      super.setEditable(true);
      this.uiAgent = uiAgent;
//      m_owner = owner;
      mNameValidatingDocument = new NameValidatingDocument(XMLNameDocument.VALIDATOR); // default to xml names.
      ParameterNode root = new ParameterNode(owner);
      m_model = new ParameterEditorTreeModel(owner, root);

      super.setModel(m_model);
      super.setDragNDropHandler(m_model);
      super.setCopyPasteHandler(m_model);

      super.setClickOutsideClearsSelection(false);
      super.addExtendedTreeModelListener(new ExtendedTreeModelListener() {
         public void treeNodesMovedInParent(TreePath parentPath, int[] children, int direction) {
            /* Disable this for now.
            if (m_owner.hasContentModelChangeListeners())
            {
                XsdContentModelPath path = getContentModelPathForTreePath(parentPath);
                if (m_lastSnapshot!=null)
                {
                    XsdContentModelChangeEvent ce = new XsdContentModelChangeEvent(m_lastSnapshot,path,children,direction);
                    m_owner.getMasterChangeListener().contentModelChange(ce);
                }
                snapshot();
            }*/
         }
      });
   }

   ParameterEditorTreeModel getParameterEditorTreeModel() {
      return m_model;
   }

   /**
    * Takes a snapshot of the content-model in order for change deltas to have a base-line reference.
    */
/* jtb commented out; 3/1/05: not used & requires unnecessary TypeReference coupling
   public SmParticleTerm snapshot() {
      SmParticleTerm l = m_lastSnapshot;
      XiNode n = m_owner.writeSchemaNode(ExpandedName.makeName("temp"));
      TypeReference tr = new TypeReference(n);
      try {
         m_lastSnapshot = tr.resolve(m_xmluiAgent, m_owner.getNamespaceImporter());
      }
      catch (Exception e) {
         //e.printStackTrace(System.err);
         m_lastSnapshot = null;
      }
      return l;
   }
*/

   XsdContentModelPath getContentModelPathForTreePath(TreePath path) {
      if (path == null) {
         return null;
      }
      XsdContentModelPath pp = getContentModelPathForTreePath(path.getParentPath());
      ParameterNode pn = (ParameterNode) path.getLastPathComponent();
      ContentModelCategory tc = pn.getContentModelCategory();
      if (pn.getParent() != null) {
         // simple 1 step:
         pp = new XsdContentModelPath(pp, pn.getParent().getIndexOfChild(pn));
      }
      if (tc == ElementSequenceCategory.INSTANCE && pn.getChildCount() != 1) // check for implicit sequence:
      {
         // extra step:
         pp = new XsdContentModelPath(pp, true);
      }
      return pp;
   }

   public void setDocumentNameValidator(DocumentNameValidator nameValidator) {
      mNameValidatingDocument.setDocumentNameValidator(nameValidator);
   }

   /*
    * For the paste part of cut-n-paste:
    *
   public BasicTreeNode buildFromXML(BasicTreeNode node, String xml) throws org.xml.sax.SAXException
   {
       return null;//WCETODO redo.XMLConverter.convert(m_owner,xml);
   }

   /*
    * Not applicable, no editor
    *
   public ErrorMessageList getEditorErrorMessageList() {
       return null;
   }*/

   /**
    * n/a
    */
   public JExtendedEditTextArea initializeEditor(BasicTreeNode node) {
      if (mField == null) {
         // really name editor:
         JExtendedEditTextArea jtf = new JExtendedEditTextArea();
         jtf.setFont(uiAgent.getAppFont());
         jtf.setFieldMode(); // single line.
         jtf.setInlineEditingMode(true);
         jtf.setDocument(mNameValidatingDocument);
         mField = jtf;
      }
      return mField;
   }

   public void addXsdContentModelChangeListener(XsdContentModelChangeListener contentModelChangeListener) {
      m_changeListeners.add(contentModelChangeListener);
   }

   public void removeXsdContentModelChangeListener(XsdContentModelChangeListener contentModelChangeListener) {
      m_changeListeners.remove(contentModelChangeListener);
   }
}
