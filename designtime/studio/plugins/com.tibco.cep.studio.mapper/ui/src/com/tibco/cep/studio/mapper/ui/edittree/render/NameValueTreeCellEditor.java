package com.tibco.cep.studio.mapper.ui.edittree.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
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
import com.tibco.cep.studio.mapper.ui.data.basic.RendererConstants;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTree;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxDocument;

/**
 * A tree cell editor works in tandem with {@link NameValueTreeCellRenderer}.<br>
 * To add editing to {@link NameValueTreeCellRenderer} simply call
 * {@link JTree#setCellEditor} with this; it takes care of the rest, assuming that
 * the {@link NameValueTreeCellPlugin} is set up for editing.
 */
public final class NameValueTreeCellEditor extends JPanel implements TreeCellEditor {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private EditableTree m_tree;
   private NameValueTreeCellRenderer m_renderer;
   private NameValueTreeCellPlugin m_plugin;
   private Object m_currentNode;
   private Point m_startEventMousePoint;
   JExtendedEditTextArea mCurrentField;
   private boolean m_isExpanded;
   //private boolean mExpanded;
   private Vector<CellEditorListener> m_listeners = new Vector<CellEditorListener>();
   /**
    * Dummy component sized to fill up the same size as the icon.
    */
   private JLabel m_spacer = new JLabel();
   //private String mStartingValue;
   private long mLastEditingClick;
   private boolean m_isNameEditing; // either name or data.

   boolean mJustStarted; // hack for repaint clean

   private KeyAdapter m_keys;

   public NameValueTreeCellEditor(EditableTree tree, NameValueTreeCellRenderer renderer) {
      super(new BorderLayout());
      m_keys = new Keys();
      m_tree = tree;
      m_renderer = renderer;
      m_plugin = renderer.getPlugin();

      add(m_spacer, BorderLayout.WEST);
      setOpaque(false);
      m_spacer.setOpaque(false);
   }

   private void setupTextField(JExtendedEditTextArea textField) {
      if (mCurrentField == textField) {
         return;
      }
      if (mCurrentField != null) {
         remove(mCurrentField);
      }
      mCurrentField = textField;
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
      m_renderer.m_node = m_currentNode;
      m_renderer.mSelected = true;
      m_renderer.mFocus = false;
      m_renderer.mExpanded = m_isExpanded;
      m_renderer.paintLabel(g, width, height, m_renderer.getRelativeDataOffset(m_currentNode));
      super.paint(g);
   }

   public Component getTreeCellEditorComponent(JTree tree, Object value,
                                               boolean sel, boolean exp,
                                               boolean leaf, int row) {
      m_currentNode = value;
      m_isExpanded = exp;
      m_renderer.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, true);
      Dimension ps = m_renderer.getPreferredSize();
      int bottomBorderSize = 1;
      setPreferredSize(new Dimension(ps.width, ps.height + bottomBorderSize));
      Dimension ms = m_renderer.getMinimumSize();
      setMinimumSize(new Dimension(ms.width, ms.height + bottomBorderSize));

      int dataOffset = m_renderer.getRelativeDataOffset(m_currentNode);
      boolean isNameEditing = !m_plugin.isDataEditable(m_currentNode);
      m_isNameEditing = isNameEditing;
      if (!isNameEditing) {
         m_spacer.setPreferredSize(new Dimension(dataOffset, getHeight() + bottomBorderSize));
      }
      else {
         int inset = 2; // this is for a name, refactor into a constant somewhere.
         m_spacer.setPreferredSize(new Dimension(m_renderer.mNameOffset - inset, getHeight() + bottomBorderSize));
      }
      if (m_startEventMousePoint != null) {
         // Reselect cursor location based on mouse click:
         TreePath path = m_tree.getPathForLocation(m_startEventMousePoint.x, m_startEventMousePoint.y);
         if (path != null) {
            Rectangle b = m_tree.getPathBounds(path);
            if (b != null) {
               int atx = b.x + m_spacer.getPreferredSize().width;
               int aty = b.y;
               final Point p = new Point(m_startEventMousePoint.x - atx, m_startEventMousePoint.y - aty);

               SwingUtilities.invokeLater(new Runnable() {
                  public void run() {
                     int pixelSlop = 2; // makes the selections work better --- better 'rounds' to nearest character.
                     if (mCurrentField == null) {
                        return; // just in case.
                     }
                     int offset = mCurrentField.getOffsetForPoint(new Point(Math.max(0, p.x - pixelSlop), p.y));
                     mCurrentField.setSelectionStart(offset);
                     mCurrentField.setSelectionEnd(offset);
                  }
               });
            }
         }
      }

      JExtendedEditTextArea nt;
      if (isNameEditing) {
         nt = m_plugin.getNameEditor(m_currentNode);
      }
      else {
         nt = m_plugin.getDataEditor(m_currentNode);
      }
      setupTextField(nt);

      revalidate();
      return this;
   }

   private void setupKeys() {
      // (Remove just in case...)
      mCurrentField.removeKeyListener(m_keys);
      mCurrentField.addKeyListener(m_keys);
   }

   private boolean isCaretOnFirstLine() {
      return mCurrentField.getCaretLine() == 0;
   }

   private boolean isCaretOnLastLine() {
      return mCurrentField.getCaretLine() >= mCurrentField.getLineCount() - 1;
   }

   private void setupPopup() {
      mCurrentField.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent me) {
            if (me.isPopupTrigger()) {
//                    Point at = SwingUtilities.convertPoint((Component)me.getSource(),me.getPoint(),m_tree);
//                    m_tree.getButtonManager().showPopup(at.x,at.y);
            }
         }
      });
   }

   private Object getEditingObject(EventObject eo) {
      m_startEventMousePoint = null;
      if (eo instanceof MouseEvent) {
         MouseEvent me = (MouseEvent) eo;
         TreePath tp = m_tree.getPathForLocation(me.getX(), me.getY());
         if (tp == null) {
            return null;
         }
         m_startEventMousePoint = me.getPoint();
         return tp.getLastPathComponent();
      }
      else {
         TreePath tp = m_tree.getSelectionPath();
         if (tp == null) {
            return null;
         }
         return tp.getLastPathComponent();
      }
   }

   public void cancelCellEditing() {
   }

   public boolean stopCellEditing() {
	   for (int i = 0; i < m_listeners.size(); i++) {
		   try { 
			   m_listeners.get(i).editingStopped(new ChangeEvent(this));
		   } catch (NullPointerException e) {
			   e.printStackTrace();
		   }
	   }
	   if (m_isNameEditing) {
		   m_plugin.nameEditingFinished(m_currentNode, mCurrentField);
	   }
	   else {
		   m_plugin.dataEditingFinished(m_currentNode, mCurrentField);
	   }

	   // Force a reselect:
	   TreePath path = m_tree.getSelectionPath();
	   m_tree.clearSelection();
	   m_tree.setSelectionPath(path);

	   return true;
   }

   public boolean shouldSelectCell(EventObject event) {
      return true;
   }

   public boolean isCellEditable(EventObject event) {
      if (!m_tree.isTreeEditable() || !m_tree.isEnabled()) {
         return false;
      }
      final Object node = getEditingObject(event);
      if (node == null) {
         return false;
      }
      if (!m_plugin.isDataEditable(node)) {
         if (m_plugin.isNameEditable(node)) {
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
               int row = m_tree.getRowForLocation(x, y);
               if (row < 0) {
                  return false;
               }
               TreePath path = m_tree.getSelectionPath();
               if (path == null) {
                  return false;
               }
               if (m_tree.getRowForPath(path) != row) {
                  mLastEditingClick = System.currentTimeMillis();
                  return false;
               }
               if (x < m_tree.getPathBounds(path).x + m_renderer.mNameOffset) {
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
      /*if (!m_tree.isInlineEditable())
      {
          return false;
      }*/
      if (event instanceof MouseEvent) {
         MouseEvent me = (MouseEvent) event;
         int x = me.getX();
         int offset = m_renderer.getDataOffset(node);
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
            if (mCurrentField == null) {
               return; // just in case.
            }
            mCurrentField.requestFocus();
         }
      });
      mJustStarted = true; // set so the repaints don't flash as much.*/
      return true;
   }

   public Object getCellEditorValue() {
      return null;
   }

   public void addCellEditorListener(CellEditorListener listener) {
      m_listeners.add(listener);
   }

   public void removeCellEditorListener(CellEditorListener listener) {
      m_listeners.remove(listener);
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

   private class Keys extends KeyAdapter {
      public void keyPressed(KeyEvent ke) {
         int keyCode = ke.getKeyCode();
         if (ke.isConsumed()) {
            return;
         }
         switch (keyCode) {
            case KeyEvent.VK_TAB:
               if (ke.isShiftDown()) {
//                        EditableTreeActions.moveEditingUp(m_tree,ke.isControlDown());
               }
               else {
//                        EditableTreeActions.moveEditingDown(m_tree,ke.isControlDown());
               }
               break;
            case KeyEvent.VK_UP:
               if (isCaretOnFirstLine()) {
//                        EditableTreeActions.moveEditingUp(m_tree,ke.isControlDown());
               }
               break;
            case KeyEvent.VK_DOWN:
               if (isCaretOnLastLine()) {
//                        BasicTreeActions.moveEditingDown(m_tree,ke.isControlDown());
               }
               break;
            case KeyEvent.VK_LEFT:
               if (ke.isControlDown()) {
                  ke.consume();
//                        BasicTreeActions.expandAndEdit(m_tree,false);
               }
               break;
            case KeyEvent.VK_RIGHT:
               if (ke.isControlDown()) {
                  ke.consume();
//                        BasicTreeActions.expandAndEdit(m_tree,true);
               }
               break;
            case KeyEvent.VK_ENTER:
               if (ke.isControlDown()) {
                  ke.consume();
//                        BasicTreeActions.showEditor(m_tree);
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
   }

   /**
    * Implementation override.
    */
   public Point getLocationOnScreen() {
      // This is in hopes of eliminating a strange exception involving dragging autoscroll that shows up randomly...
      try {
         return super.getLocationOnScreen();
      }
      catch (Throwable t) {
         t.printStackTrace(System.err);
         return new Point(0, 0);
      }
   }
}
