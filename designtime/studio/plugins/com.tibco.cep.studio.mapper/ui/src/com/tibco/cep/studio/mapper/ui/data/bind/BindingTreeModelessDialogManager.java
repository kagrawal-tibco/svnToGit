package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.swing.JDialog;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditDialog;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeEditHandler;

/**
 * Move some of the relatively self-contained modeless dialog management out of {@link BindingTree}.
 */
public class BindingTreeModelessDialogManager implements EditableTreeEditHandler {
   private BindingEditor m_bindingEditor;
//   private FunctionResolver m_functionResolver;
//   private InputStream m_categoryInputStream;

/*
//commented out by jtb during port from BW to XMLUI -- mFindDialog isn't needed
   private BindingFindDialog mFindDialog; // non null if visible.
   private BindingFindDialog mGlobalFindDialog; // non null if
*/   
   private JToggleButton m_editButton;
   private BindingNode m_currentEditNode;

   private XPathEditDialog m_xPathEditDialog;
   /**
    * To prevent re-entrant situation, see {@link #applyFormulaChange}
    */
   private boolean m_isApplyingFormulaChange;
   private UIAgent uiAgent;

   /**
    *
    * @param editor
    * @param tree
    * @param designerDocument
    * @param xmluiAgent
    * @param fResolver
    * @param categoryInputStream the InputStream from which to retrieve the
    * xpath function categories; if null, will use default functions
    */
   public BindingTreeModelessDialogManager(BindingEditor editor,
                                           BindingTree tree,
                                           UIAgent uiAgent,
                                           FunctionResolver fResolver,
                                           InputStream categoryInputStream) {
      m_bindingEditor = editor;
      this.uiAgent = uiAgent;
      m_editButton = tree.getButtonManager().getEditButton();
//      m_functionResolver = fResolver;
//      m_categoryInputStream = categoryInputStream;
   }
   public BindingTreeModelessDialogManager(BindingEditor editor,
                                           BindingTree tree,
                                           UIAgent uiAgent,
                                           FunctionResolver fResolver) {
      this(editor, tree, uiAgent, fResolver, null);
   }

/*
//commented out by jtb during port from BW to XMLUI -- is NOT used, but contains references
//to BindingFindDialog which
   public void find(boolean isGlobal) {
      closeEditor();
      // shows it:
      if (isGlobal) {
         if (mGlobalFindDialog != null) {
            mGlobalFindDialog.reshow();
         }
         else {
            ObjectProvider op = m_xmluiAgent.getObjectProvider();
            ActionListener al = new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  mGlobalFindDialog = null;
               }
            };
            mGlobalFindDialog = new BindingFindDialog(m_bindingEditor, m_designerDocument.getApplication(), al, op);
         }
      }
      else {
         if (mFindDialog != null) {
            mFindDialog.reshow();
         }
         else {
            ActionListener al = new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  mFindDialog = null;
               }
            };
            mFindDialog = new BindingFindDialog(m_bindingEditor, m_designerDocument.getApplication(), al);
         }
      }
   }
*/

   public void closeEditor() {
      if (m_xPathEditDialog != null) {
         m_xPathEditDialog.dispose();
         m_xPathEditDialog = null;
      }
      m_editButton.setSelected(false);
   }

   /**
    * IF the editor is showing, refreshes it with the new selection, otherwise does nothing.
    *
    * @param onNode
    */
   public void refreshEditor(BindingNode onNode) {
      if (m_xPathEditDialog == null) {
         return;
      }
      showEditor(onNode);
   }

   /**
    * Implementation for {@link EditableTreeEditHandler}.
    */
   public void hideEditor() {
      closeEditor();
   }

   /**
    * Implementation for {@link EditableTreeEditHandler}.
    */
   public void showEditor(Object node) {
      showEditor((BindingNode) node);
   }

   public void showEditor(BindingNode onNode) {
      m_bindingEditor.getBindingTree().waitForReport(); // ensure report is handy, o.w. editable may not be correct.
      if (m_xPathEditDialog == null) {
         Component comp = SwingUtilities.getWindowAncestor(m_bindingEditor);
         if(comp instanceof JDialog) {
            m_xPathEditDialog = new XPathEditDialog(
                    uiAgent,
                    (JDialog) comp,
                    false);
         }
         else {
            m_xPathEditDialog = new XPathEditDialog(uiAgent,
                                                    (Frame) comp,
                                                    false);
         }
         // restore sizes.
         m_xPathEditDialog.setVisible(true);
         m_xPathEditDialog.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
               closeEditor();
            }

            public void windowClosing(WindowEvent e) {
               closeEditor();
            }

            public void windowActivated(WindowEvent e) {
               // Need to stop any inline editing, o.w. will be out of sync.
               m_bindingEditor.stopInlineEditing();
            }
         });
         m_xPathEditDialog.addApplyListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               applyFormulaChange();
            }
         });
      }
      m_currentEditNode = onNode;
      boolean ed = m_bindingEditor.getBindingTree().isTreeEditable();
      m_xPathEditDialog.setEditable(ed);
      if (onNode == null || !onNode.isEditable()) {
         m_xPathEditDialog.setMode(StatementPanel.FIELD_TYPE_NOT_EDITABLE);
         return;
      }
      TemplateReport tr = onNode.getTemplateReport();
      if (tr == null) {
         m_xPathEditDialog.setMode(StatementPanel.FIELD_TYPE_NOT_EDITABLE);
         return; // shouldn't happen.
      }
      m_xPathEditDialog.setMode(onNode.getEditableFieldType());
      m_xPathEditDialog.setSelection(tr.getContext(),
                                     tr.getContext(),
                                     BindingNamespaceManipulationUtils.createNamespaceImporter(onNode.getTemplateReport().getBinding()),
                                     onNode.getDataValue());
   }

   /**
    * Applies the change in the modeless formula dialog, if any, to the formula shown in the tree (equivalent to
    * hitting the apply button in the modeless edit dialog)
    */
   void applyFormulaChange() {
      // Because, in certain circumstances, a formula change can also trigger a re-selection (which can trigger this)
      // we need to guard against that; use a simple boolean flag.
      if (m_isApplyingFormulaChange) {
         return;
      }
      m_isApplyingFormulaChange = true;
      try {
         if (m_xPathEditDialog == null) {
            return;
         }
         BindingNode node = m_currentEditNode;
         if (node == null) {
            return;
         }
         if (!node.isEditable()) {
            return;
         }
         String formula = m_xPathEditDialog.getFormula();
         if (formula == null) {
            formula = ""; // for the purposes of 'change'
         }
         String existingFormula = node.getBinding().getFormula();
         if (!formula.equals(existingFormula)) {
            node.setDataValue(formula);
            Rectangle r = node.getTree().getPathBounds(node.getTreePath());
            if (r != null) // can happen somehow..
            {
               node.getTree().repaint(r);
            }
            node.getTree().markReportDirty();
            node.getTree().contentChanged();
         }
      }
      finally {
         m_isApplyingFormulaChange = false;
      }
   }

   void close() {
/*
//commented out by jtb during port from BW to XMLUI -- mFindDialog isn't needed
      if (mFindDialog != null) {
         mFindDialog.dispose();
         mFindDialog = null;
      }
*/
      closeEditor();
   }
}

