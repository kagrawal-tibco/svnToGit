package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.DataTypeTreeNode;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeResources;
import com.tibco.cep.studio.mapper.ui.data.bind.coerce.CoercionDialog;
import com.tibco.cep.studio.mapper.ui.data.utils.ButtonUtils;
import com.tibco.cep.studio.mapper.ui.data.utils.InlineToolBar;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * The left hand side tree for the {@link BindingEditor}.<br>
 * Adds a few extra things.
 */
class BindingDataTree extends DataTree {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//JToggleButton mShowDataButton;
   private JToggleButton mShowDocumentationButton;
   private JButton mSubstitutionButton;
   private BindingEditor mBindingEditor;
   private InlineToolBar m_toolBar;
   BindingLines m_bindingLines;

   public BindingDataTree(BindingEditor ed, UIAgent uiAgent) {
      super(uiAgent);
      mBindingEditor = ed;
   }

   /**
    * Override to add more context menu items at the top:
    */
   public void augmentTopContextMenu(JPopupMenu menu) {
      JMenuItem item = new JMenuItem(BindingEditorResources.SHOW_CONNECTED);
      item.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            m_bindingLines.showLeftConnected();
         }
      });
      item.setEnabled(m_bindingLines.canShowLeftConnected());
      menu.add(item);

      JMenuItem delete = new JMenuItem(BasicTreeResources.DELETE);
      delete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            m_bindingLines.deleteLeftSelected(getSelectionPath());
         }
      });
      menu.addSeparator();
      menu.add(delete);
      delete.setEnabled(!getSelectionModel().isSelectionEmpty());
   }

   public void setIsDebuggerActive(boolean val) {
      /* not applicable right now.if (val) {
          if (!mShowDataButton.isSelected()) { // not in data mode.
              // can't show data
              mShowDataButton.setEnabled(false);
              mShowDataButton.setToolTipText(BindingEditorResources."Activity not reached, no process data");//WCETODO resourceize
          } else {
              mShowDataButton.setToolTipText("Show Process Data");//WCETODO resourceize
          }
      }*/
   }

   /**
    * Gets the unsubstituted (prior to any/wildcard substitution) path.
    *
    * @return The xpath to this node.
    */
   public String getUnsubstitutedXPath(TreePath path) {
      if (path == null) {
         return ".";
      }
      StringBuffer sb = new StringBuffer();
      BasicTreeNode n = (BasicTreeNode) path.getLastPathComponent();
      getUnsubstitutedXPath(sb, n, true);
      String xp = sb.toString();
      return xp;
   }


   private void getUnsubstitutedXPath(StringBuffer sb, BasicTreeNode node, boolean isLastStep) {
      if (node == null) {
         return;
      }
      String step;
      if (!(node instanceof DataTypeTreeNode)) {
         step = node.getXStepName(isLastStep, getNamespaceImporter());
      }
      else {
         step = ((DataTypeTreeNode) node).getUnsubstitutedXStepName(isLastStep, getNamespaceImporter());
      }
      getUnsubstitutedXPath(sb, node.getParent(), step == null && isLastStep);
      if (sb.length() > 0 && step != null) {
         sb.append("/");
      }
      if (step != null) {
         sb.append(step);
      }
   }

   public boolean nodeHasChildContent(BasicTreeNode node) {
      TreePath rpath = node.getTreePath();
      if (!isExpanded(rpath)) {
         // If it's not expanded, there will be lines sticking out of it:
         TreePath[] p = m_bindingLines.getRightPathsFor(rpath);
         if (p == null || p.length == 0) {
            return false;
         }
         return true;
      }
      else {
         int cc = node.getChildCount();
         for (int i = 0; i < cc; i++) {
            BasicTreeNode child = node.getChild(i);
            if (nodeHasChildContent(child)) {
               return true;
            }
         }
         return false;
      }
   }

   public void substitute() {
      BasicTreeNode btn = getSelectionNode();
      String xp = null;
      if (btn != null) {
         xp = BindingDataTree.this.getUnsubstitutedXPath(btn.getTreePath());
      }
      NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(mBindingEditor.getTemplateEditorConfiguration().getBinding());
      // Hacky, but the way the namespace importer gets passed around isn't good (ExprContext duplicates what is elsewhere).
      // The 'master' is really the root importer off the binding, so ensure that that one is set here:
      // (Without this the first added coercion where a new namespace is used wouldn't get displayed)
      ExprContext ec = getExprContext().createWithNamespaceMapper(ni);

      boolean changed = CoercionDialog.showDialog(SwingUtilities.getWindowAncestor(BindingDataTree.this),
                                                  uiAgent,
                                                  this,
                                                  xp,
                                                  mBindingEditor.getTemplateEditorConfiguration().getCoercionSet(),
                                                  getSchemaProvider(),
                                                  ec,
                                                  mBindingEditor.getTemplateEditorConfiguration().getImportRegistry(),
                                                  mBindingEditor.isEnabled());
      if (changed) {
         BindingDataTree.this.setExprContext(ec, mBindingEditor.getTemplateEditorConfiguration().getCoercionSet());
         mBindingEditor.getBindingTree().markReportDirty();
         mBindingEditor.fireContentChanged();
      }
   }

/**
    * Always has content (for expand content)
    */
   protected boolean hasContent() {
      return true;
   }
   /**
    *
    * @return
    * @deprecated 1.0.0
    */
   InlineToolBar buildInputButtons() {
      return buildInputButtons(true, true);
   }
   /**
    *
    * @param showSubstitutionButton
    * @param showDocumentationButton
    * @return
    * @since 1.0.0
    */
   InlineToolBar buildInputButtons(boolean showSubstitutionButton,
                                   boolean showDocumentationButton) {
      //mShowDataButton = ButtonUtils.makeBarToggleButton(DataIcons.getDataModeIcon(),"Data Mode");
      //mShowDataButton.setSelected(false);

      InlineToolBar toolBar = new InlineToolBar(uiAgent);
      if(showSubstitutionButton) {
         mSubstitutionButton = ButtonUtils.makeBarButton(DataIcons.getAnySubIcon(), BindingEditorResources.COERCE_TITLE);
         toolBar.addButton(mSubstitutionButton);
         mSubstitutionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               substitute();
            }
         });
      }

      // Disabled data button:
      //toolBar.addSeparator();
      //toolBar.addButton(mShowDataButton);

      /*mShowDataButton.addActionListener(new ActionListener()
      {
          public void actionPerformed(ActionEvent ae)
          {
              mBindingEditor.showData(mShowDataButton.isSelected());
          }
      });*/
      if(showDocumentationButton) {
         mShowDocumentationButton = ButtonUtils.makeBarToggleButton(DataIcons.getInlineHelpIcon(), BindingEditorResources.TYPE_DOCUMENTATION);
         mShowDocumentationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               mBindingEditor.showDocumentation(mShowDocumentationButton.isSelected());
            }
         });
         toolBar.addButton(mShowDocumentationButton);
      }

      toolBar.setLabel("<< fill in me >>"); // (default; needs to be set externally)
      toolBar.setLabelToolTip(null); //
      m_toolBar = toolBar;

      return toolBar;
   }

   public String getLabel() {
      return m_toolBar.getLabel();
   }

   /**
    * Sets the label that appears on the inline buttons.
    */
   public void setLabel(String label) {
      m_toolBar.setLabel(label);
   }

   /**
    * Sets the tooltip that appears on the inline buttons.
    */
   public void setLabelTooltip(String tooltip) {
      m_toolBar.setLabelToolTip(tooltip);
   }

   public String getLabelTooltip() {
      return m_toolBar.getLabelToolTip();
   }

   public void closeDocumentationWindow() {
      if(mShowDocumentationButton != null) {
         mShowDocumentationButton.setSelected(false);
      }
   }

   public void paint(Graphics g) {
      super.paint(g);

      // Overpaint silly lines here (this is an optimization so that the JTree can use regular (blit) scrolling)
      Point p = SwingUtilities.convertPoint(this, new Point(0, 0), mBindingEditor);
      g.translate(-p.x, -p.y);
      try {
         m_bindingLines.drawLines(g, 0, true, false);// true -> only paint right side (over the tree), for optimization.
      }
      catch (Throwable t) {
         // Just in case; usually means lines weren't dirtied.
         m_bindingLines.markLinesDirty();
         t.printStackTrace(System.err);
      }
      g.translate(p.x, p.y);
   }
}

