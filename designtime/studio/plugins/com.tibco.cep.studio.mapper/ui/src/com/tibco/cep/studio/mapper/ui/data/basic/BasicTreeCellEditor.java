package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

import com.tibco.cep.studio.mapper.ui.PaintUtils;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxDocument;

public class BasicTreeCellEditor extends JPanel implements TreeCellEditor {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private BasicTree mTree;
   private BasicTreeCellRenderer mRenderer;
   private BasicTreeNode mNode;
   JExtendedEditTextArea mCurrentField;
   private boolean mExpanded;
   private Vector<CellEditorListener> mListeners = new Vector<CellEditorListener>();
   private JLabel mSpacer = new JLabel();
   private String mStartingValue;
   private long mLastEditingClick;
   boolean mJustStarted; // hack for repaint clean

   public BasicTreeCellEditor(BasicTree tree) {
      super(new BorderLayout());
      mTree = tree;
      mRenderer = (BasicTreeCellRenderer) mTree.getCellRenderer();

      add(mSpacer, BorderLayout.WEST);
      setOpaque(false);
      mSpacer.setOpaque(false);
   }

   private void setupTextField(JExtendedEditTextArea textField) {
      if (mCurrentField == textField) {
         return;
      }
      if (mCurrentField != null) {
         remove(mCurrentField);
      }
      mCurrentField = textField;
/* WCETODO this used to be required; hopefully it's not anymore.       if (!textField.isManagingFocus())
        {
            throw new RuntimeException("Editing in basic tree must manage focus (isManagingFocus==true), component name is " + textField.getClass().getName());
        }*/
      mCurrentField.setFieldMode();
      mCurrentField.setBorder(new InlineTextBorder());

      add(mCurrentField, BorderLayout.CENTER);
      setOpaque(false);

      setupKeys();
      setupPopup();
   }

   public void setEditorDocument(SyntaxDocument doc) {
      mCurrentField.setDocument(doc);
   }

   public void paint(Graphics g) {
      int width = getWidth();
      int height = getHeight();
      mRenderer.mNode = mNode;
      mRenderer.mSelected = true;
      mRenderer.mFocus = false;
      mRenderer.mExpanded = mExpanded;
      mRenderer.paintLabel(g, width, height, mRenderer.getRelativeDataOffset(mNode));
      super.paint(g);
   }

   public Component getTreeCellEditorComponent(JTree tree, Object value,
                                               boolean sel, boolean exp,
                                               boolean leaf, int row) {
      mNode = (BasicTreeNode) value;
      mRenderer.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, true);
      Dimension ps = mRenderer.getPreferredSize();
      int bottomBorderSize = 1;
      setPreferredSize(new Dimension(ps.width, ps.height + bottomBorderSize));
      Dimension ms = mRenderer.getMinimumSize();
      setMinimumSize(new Dimension(ms.width, ms.height + bottomBorderSize));

      mExpanded = exp;
      int dataOffset = mRenderer.getRelativeDataOffset(mNode);
      boolean isNameEditing = !mNode.isEditable();
      if (!isNameEditing) {
         mSpacer.setPreferredSize(new Dimension(dataOffset, getHeight() + bottomBorderSize));
      }
      else {
         int inset = 2; // this is for a name, refactor into a constant somewhere.
         mSpacer.setPreferredSize(new Dimension(mRenderer.mNameOffset - inset, getHeight() + bottomBorderSize));
      }

      if (isNameEditing) {
         mStartingValue = mNode.getDisplayName();
      }
      else {
         mStartingValue = mNode.getDataValue();
      }
      mTree.waitForReport();
      JExtendedEditTextArea nt = mTree.initializeEditor(mNode);
      setupTextField(nt);
      // (setupTextField sets mCurrentField)
      mCurrentField.setText(mStartingValue != null ? mStartingValue : "");
      mCurrentField.setSelectionStart(0);
      mCurrentField.setSelectionEnd(0);

      revalidate();

      return this;
   }

   private void setupKeys() {
      mCurrentField.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();
            if (ke.isConsumed()) {
               return;
            }
            switch (keyCode) {
               case KeyEvent.VK_TAB:
                  if (ke.isShiftDown()) {
                     BasicTreeActions.moveEditingUp(mTree, ke.isControlDown());
                  }
                  else {
                     BasicTreeActions.moveEditingDown(mTree, ke.isControlDown());
                  }
                  break;
               case KeyEvent.VK_UP:
                  if (isCaretOnFirstLine()) {
                     BasicTreeActions.moveEditingUp(mTree, ke.isControlDown());
                  }
                  break;
               case KeyEvent.VK_DOWN:
                  if (isCaretOnLastLine()) {
                     BasicTreeActions.moveEditingDown(mTree, ke.isControlDown());
                  }
                  break;
               case KeyEvent.VK_LEFT:
                  if (ke.isControlDown()) {
                     ke.consume();
                     BasicTreeActions.expandAndEdit(mTree, false);
                  }
                  break;
               case KeyEvent.VK_RIGHT:
                  if (ke.isControlDown()) {
                     ke.consume();
                     BasicTreeActions.expandAndEdit(mTree, true);
                  }
                  break;
               case KeyEvent.VK_ENTER:
                  if (ke.isControlDown()) {
                     ke.consume();
                     BasicTreeActions.showEditor(mTree);
                  }
                  else {
                     if (!ke.isAltDown()) {
                        // If inside a dialog (or pseudo-dialog), may caused 'apply' to be hit,
                        // so consume it first:
                        stopCellEditing();
                        ke.consume();
                     }
                  }
                  break;
            }
         }
      });
   }

   private boolean isCaretOnFirstLine() {
      return mCurrentField.getCaretLine() == 0;
   }

   private boolean isCaretOnLastLine() {
      return mCurrentField.getCaretLine() >= mCurrentField.getLineCount() - 1;
   }

   private void setupPopup() {
      mCurrentField.addMouseListener(new MouseAdapter() {
         // 1-20KD29 -- Windows systems trigger popups on mouse release
         public void mouseReleased(MouseEvent me) {
            maybeShowPopup(me);
         }

         // 1-20KD29 -- X-Windows systems trigger popups on mouse press
         public void mousePressed(MouseEvent me) {
            maybeShowPopup(me);
         }

         private void maybeShowPopup(MouseEvent me) {
            if (me.isPopupTrigger()) {
               Point at = SwingUtilities.convertPoint((Component) me.getSource(), me.getPoint(), mTree);
               mTree.getButtonManager().showPopup(at.x, at.y);
            }
         }
      });
   }

   private BasicTreeNode getEditingObject(EventObject eo) {
      if (eo instanceof MouseEvent) {
         MouseEvent me = (MouseEvent) eo;
         TreePath tp = mTree.getPathForLocation(me.getX(), me.getY());
         if (tp == null) {
            return null;
         }
         return (BasicTreeNode) tp.getLastPathComponent();
      }
      else {
         TreePath tp = mTree.getSelectionPath();
         if (tp == null) {
            return null;
         }
         return (BasicTreeNode) tp.getLastPathComponent();
      }
   }

   public void cancelCellEditing() {
   }

   public boolean stopCellEditing() {
      String text = mCurrentField.getText();

      for (int i = 0; i < mListeners.size(); i++) {
         mListeners.get(i).editingStopped(new ChangeEvent(this));
      }
      if (!mNode.allowsEmptyDataValue() && text.length() == 0) {
         // If it doesn't allow an empty formula & the text is blank, assume they meant 'delete'
         text = null;
      }
      boolean same = mStartingValue == text || (mStartingValue != null && mStartingValue.equals(text));
      if (!same) {
         if (mNode.isEditable()) { // data editing:
            mNode.setDataValue(text);
            mTree.contentChanged();
            mTree.markReportDirty();
         }
         else {
            mNode.setDisplayName(text);
            mTree.contentChanged();
            mTree.markReportDirty();
         }
      }

      return true;
   }

   public boolean shouldSelectCell(EventObject event) {
      return true;
   }

   public boolean isCellEditable(EventObject event) {
      // Must wait for the reports here, o.w. won't allow it:
      mTree.waitForReport();

      final BasicTreeNode node = getEditingObject(event);
      if (node == null) {
         return false;
      }
      if (!node.isEditable()) {
         if (node.isNameEditable()) {
            // for name editing, make it require 2 clicks:
            if (event instanceof MouseEvent) {
               MouseEvent me = (MouseEvent) event;
               if (me.isPopupTrigger()) { // popup just hit is a hack so that a popup can't trigger an editing.
                  return false;
               }
               if ((me.getModifiers() & MouseEvent.BUTTON1_MASK) == 0) {
                  return false;
               }
               int x = me.getX();
               int y = me.getY();
               if (me.isPopupTrigger()) {
                  return false;
               }
               int row = mTree.getRowForLocation(x, y);
               if (row < 0) {
                  return false;
               }
               TreePath path = mTree.getSelectionPath();
               if (path == null) {
                  return false;
               }
               if (mTree.getRowForPath(path) != row) {
                  mLastEditingClick = System.currentTimeMillis();
                  return false;
               }
               if (x < mTree.getPathBounds(path).x + mRenderer.mNameOffset) {
                  return false;
               }
               // double click, etc. doesn't work real well in java, allow a very gentle full second to start editing.
               if (mLastEditingClick + 1000 < System.currentTimeMillis()) {
                  mLastEditingClick = System.currentTimeMillis();
                  return false;
               }
               return true;
            }
            return false;
         }
         return false;
      }
      if (!mTree.isInlineEditable()) {
         return false;
      }
      if (event instanceof MouseEvent) {
         MouseEvent me = (MouseEvent) event;
         int x = me.getX();
         int offset = mRenderer.getDataOffset(node);
         if (offset > x) {
            return false;
         }
         if (me.isPopupTrigger()) {
            return false;
         }
      }

      // To make sure the text field gets focus even if the click misses a bit:
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            mCurrentField.requestFocus();
         }
      });
      mJustStarted = true; // set so the repaints don't flash as much.

      return true;
   }

   public Object getCellEditorValue() {
      return null;
   }

   public void addCellEditorListener(CellEditorListener listener) {
      mListeners.add(listener);
   }

   public void removeCellEditorListener(CellEditorListener listener) {
      mListeners.remove(listener);
   }

   private static class InlineTextBorder implements Border {
      private static Insets MY_INSETS = new Insets(2, 2, 2, 2);

      public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
         PaintUtils.drawEtchedRect(g, 0, 0, width, height,
                                                RendererConstants.SHADOW_COLOR,
                                                Color.white, //.SHADOW_COLOR,
                                                RendererConstants.HILITE_COLOR,
                                                RendererConstants.LITE_HILITE_COLOR);
      }

      public Insets getBorderInsets(Component c) {
         return MY_INSETS;
      }

      public boolean isBorderOpaque() {
         return true;
      }

   }
}
