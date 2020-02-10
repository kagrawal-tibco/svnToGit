package com.tibco.cep.studio.mapper.ui.edittree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.tree.TreePath;
import javax.swing.undo.UndoManager;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeResources;
import com.tibco.cep.studio.mapper.ui.data.utils.ButtonUtils;

/**
 * Manages the buttons and menus for the {@link EditableTree}.
 */
public final class EditableTreeButtonManager {
   private final EditableTree m_tree;

   private JMenuItem mCutMenu;
   private JMenuItem mCopyMenu;
   private JMenuItem mPasteMenu;
   private JToggleButton mEditButton;
   private JMenuItem mEditMenu;

   private JMenuItem mExpandAllMenu;
   private JMenuItem mExpandContentMenu;
   private JMenuItem mExpandErrorsMenu;

   private JButton mAddButton;
   private JButton mAddTopOrChildButton;
   private JMenuItem mAddMenu;
   private JButton mAddChildButton;
//    private JMenuItem mAddChildMenu;
   private JButton mDeleteButton;
   private JMenuItem mDeleteMenu;
   private JButton mMoveUpButton;
   private JMenuItem mMoveUpMenu;
   private JButton mMoveDownButton;
   private JMenuItem mMoveDownMenu;
   private JButton mMoveInButton;
   private JMenuItem mMoveInMenu;
   private JButton mMoveOutButton;
   private JMenuItem mMoveOutMenu;

   private ActionListener mActionListener;

   EditableTreeButtonManager(EditableTree tree) {
      m_tree = tree;
      addKeyActions();
   }

   private void addKeyActions() {
      m_tree.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke) {
            int code = ke.getKeyCode();
            if (ke.isControlDown()) {
               // NOTE: Designer seems to be also automatically issuing cut/copy/paste.  In future version,
               // maybe eliminate these & require it delegated by designer?
               switch (code) {
                  case KeyEvent.VK_C:
                  case KeyEvent.VK_INSERT:
                     {
                        ke.consume();
                        m_tree.copy();
                        break;
                     }
                  case KeyEvent.VK_X:
                     {
                        ke.consume();
                        m_tree.cut();
                        break;
                     }
                  case KeyEvent.VK_V:
                     {
                        ke.consume();
                        m_tree.paste();
                        break;
                     }
               }
            }
            if (ke.isShiftDown()) {
               switch (code) {
                  case KeyEvent.VK_INSERT:
                     {
                        ke.consume();
                        m_tree.paste();
                        break;
                     }
               }
            }
            // don't keep going if we've already had a control- or shift- consume it:
            if (ke.isConsumed()) {
               return;
            }
            switch (code) {
               case KeyEvent.VK_DELETE:
                  {
                     ke.consume();
                     EditableTreeActions.delete(m_tree);
                     break;
                  }
               case KeyEvent.VK_INSERT:
                  {
                     ke.consume();
                     EditableTreeActions.addAt(m_tree);
                     break;
                  }
               case KeyEvent.VK_PLUS:
                  {
                     ke.consume();
                     EditableTreeActions.addAt(m_tree);
                     break;
                  }
               case KeyEvent.VK_EQUALS:
                  {
                     ke.consume();
                     EditableTreeActions.addAt(m_tree);
                     break;
                  }
            }
         }
      });
      mActionListener = new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            Object source = ae.getSource();
            if (source == mAddButton || source == mAddMenu) {
               EditableTreeActions.addAt(m_tree);
            }
            if (source == mAddChildButton) {
               EditableTreeActions.addChild(m_tree);
            }
            if (source == mAddTopOrChildButton) {
               EditableTreeActions.addTopOrChild(m_tree);
            }
            if (source == mDeleteButton || source == mDeleteMenu) {
               EditableTreeActions.delete(m_tree);
            }
            if (source == mMoveUpButton || source == mMoveUpMenu) {
               EditableTreeActions.moveUp(m_tree);
            }
            if (source == mMoveDownButton || source == mMoveDownMenu) {
               EditableTreeActions.moveDown(m_tree);
            }
            if (source == mMoveInButton || source == mMoveInMenu) {
               EditableTreeActions.moveIn(m_tree);
            }
            if (source == mMoveOutButton || source == mMoveOutMenu) {
               EditableTreeActions.moveOut(m_tree);
            }
            if (source == mCutMenu) {
               m_tree.cut();
            }
            if (source == mCopyMenu) {
               m_tree.copy();
            }
            if (source == mPasteMenu) {
               m_tree.paste(); // need to resolve the overrideability of these things...
            }
            if (source == mExpandAllMenu) {
               EditableTreeActions.expandAll(m_tree);
            }
            if (source == mExpandContentMenu) {
               EditableTreeActions.expandContent(m_tree);
            }
            if (source == mExpandErrorsMenu) {
               EditableTreeActions.expandErrors(m_tree);
            }
            if (source == mEditMenu || source == mEditButton) {
               if (source == mEditButton && !mEditButton.isSelected()) {
                  if (m_tree.getEditHandler() != null) {
                     m_tree.getEditHandler().hideEditor();
                     mEditButton.setSelected(false);
                  }
               }
               else {
                  if (m_tree.getEditHandler() != null) {
                     m_tree.getEditHandler().showEditor(m_tree.getSelectionNode());
                     mEditButton.setSelected(true);
                  }
               }
            }
         }
      };
   }

   /**
    * Adds the standard tree manipulation items to the menu.
    *
    * @param menu The menu.
    */
   private void addStandardContextMenuItems(JPopupMenu menu) {
      if (mCopyMenu == null) {
         mCutMenu = new JMenuItem(BasicTreeResources.CUT);
         mCutMenu.addActionListener(mActionListener);

         mCopyMenu = new JMenuItem(BasicTreeResources.COPY);
         mCopyMenu.addActionListener(mActionListener);

         mPasteMenu = new JMenuItem(BasicTreeResources.PASTE);
         mPasteMenu.addActionListener(mActionListener);

         mEditMenu = new JMenuItem(BasicTreeResources.EDIT);
         mEditMenu.addActionListener(mActionListener);

         mAddMenu = new JMenuItem(BasicTreeResources.INSERT);//, DataIcons.getAddIcon());
         mAddMenu.addActionListener(mActionListener);

         mDeleteMenu = new JMenuItem(BasicTreeResources.DELETE);//, DataIcons.getDeleteIcon());
         mDeleteMenu.addActionListener(mActionListener);

         mMoveUpMenu = new JMenuItem(BasicTreeResources.MOVE_UP);//,DataIcons.getMoveUpIcon());
         mMoveUpMenu.addActionListener(mActionListener);

         mMoveDownMenu = new JMenuItem(BasicTreeResources.MOVE_DOWN);//,DataIcons.getMoveDownIcon());
         mMoveDownMenu.addActionListener(mActionListener);

         mMoveInMenu = new JMenuItem(BasicTreeResources.MOVE_IN);//,DataIcons.getMoveRightIcon());
         mMoveInMenu.addActionListener(mActionListener);

         mMoveOutMenu = new JMenuItem(BasicTreeResources.MOVE_OUT);//,DataIcons.getMoveLeftIcon());
         mMoveOutMenu.addActionListener(mActionListener);

         mExpandAllMenu = new JMenuItem(BasicTreeResources.EXPAND_ALL);
         mExpandAllMenu.addActionListener(mActionListener);

         mExpandContentMenu = new JMenuItem(BasicTreeResources.EXPAND_CONTENT);
         mExpandContentMenu.addActionListener(mActionListener);

         mExpandErrorsMenu = new JMenuItem(BasicTreeResources.EXPAND_ERRORS);
         mExpandErrorsMenu.addActionListener(mActionListener);
      }

      if (m_tree.isEditable()) {
         menu.add(mEditMenu);
      }
      JMenu expand = new JMenu(BasicTreeResources.EXPAND);
      //expand.setIcon(DataIcons.getBlankIcon());
      EditableTreeExpansionHandler eh = m_tree.getExpansionHandler();
      if (eh != null && eh.isContentSupported()) {
         expand.add(mExpandContentMenu);
      }
      if (eh != null && eh.isErrorSupported()) {
         expand.add(mExpandErrorsMenu);
      }
      if (expand.getMenuComponentCount() == 0) {
         // Content/errors not supported, so just have 'expand' to mean expand all.
         mExpandAllMenu.setText(BasicTreeResources.EXPAND);
         menu.add(mExpandAllMenu);
      }
      else {
         expand.add(mExpandAllMenu);
         menu.add(expand);
      }
   }

   private void addBottomContextMenus(JPopupMenu menu) {
      boolean enabled = m_tree.isEnabled();


      final UndoManager um = m_tree.getUndoManager();

      if (um != null) {
         JMenuItem undo = new JMenuItem(um.getUndoPresentationName());
         undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               um.undo();
            }
         });
         undo.setEnabled(um.canUndo());
         menu.add(undo);
         JMenuItem redo = new JMenuItem(um.getRedoPresentationName());
         menu.add(redo);
         redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               um.redo();
            }
         });
         redo.setEnabled(um.canRedo());
         menu.addSeparator();
         menu.add(undo);
         menu.add(redo);
      }

      menu.addSeparator();

      if (m_tree.isEditable()) {
         menu.add(mCutMenu);
         mCutMenu.setEnabled(enabled && EditableTreeActions.canCut(m_tree));
      }
      menu.add(mCopyMenu);
      mCopyMenu.setEnabled(EditableTreeActions.canCopy(m_tree));

      if (m_tree.isEditable()) {
         menu.add(mPasteMenu);
         mPasteMenu.setEnabled(enabled && EditableTreeActions.canPaste(m_tree));
         menu.add(mDeleteMenu);
         mDeleteMenu.setEnabled(enabled && EditableTreeActions.canDelete(m_tree));
      }
   }

   void showPopup(int x, int y) {
	   //Fix for : BE-13243: Project Library : Event payload area for events from library, should not have any options from right click menu enabled as it wont help in editing and copying.
 	   boolean editable = m_tree.isTreeEditable();
	   boolean enabled = m_tree.isEnabled();
	   if (editable) {
		   TreePath path = m_tree.getPathForRow(m_tree.getRowForYLocation(y));
		   m_tree.setSelectionPath(path);

		   JPopupMenu menu = new JPopupMenu();
		   addStandardContextMenuItems(menu);
		   Object node = path == null ? null : path.getLastPathComponent();
		   if (m_tree.getContextMenuHandler() != null) {
			   m_tree.getContextMenuHandler().addContextMenuItems(node, menu);
		   }
		   addBottomContextMenus(menu);

		   menu.show(m_tree, x, y);
	   }
      /*
      if (m_tree.isEditable())
      {
          menu.add(mEditMenu);
      }
      //WCETODO redo m_tree.augmentTopContextMenu(menu);

      /*WCETODO REDOif (m_tree.allowsCrossBranchDrops() && m_tree.isEditable()) {
          mMoveInMenu.setEnabled(enabled && EditableTreeActions.canMoveIn(m_tree));
          mMoveOutMenu.setEnabled(enabled && EditableTreeActions.canMoveOut(m_tree));
          menu.add(mMoveOutMenu);
          menu.add(mMoveInMenu);
      }*
      */
   }


   public JButton getAddAtButton() {
      if (mAddButton == null) {
         mAddButton = makeButton(DataIcons.getAddIcon(), BasicTreeResources.INSERT);
      }
      return mAddButton;
   }

   /**
    * Like {@link #getAddAtButton}, but this button is capable, also, of adding the 'root'.
    */
   public JButton getAddTopOrChildButton() {
      if (mAddTopOrChildButton == null) {
         mAddTopOrChildButton = makeButton(DataIcons.getAddChildIcon(), BasicTreeResources.ADD_CHILD);
      }
      return mAddTopOrChildButton;
   }

   public JButton getAddChildButton() {
      if (mAddChildButton == null) {
         mAddChildButton = makeButton(DataIcons.getAddChildIcon(), BasicTreeResources.ADD_CHILD);
      }
      return mAddChildButton;
   }

   public JToggleButton getEditButton() {
      if (mEditButton == null) {
         mEditButton = makeToggleButton(DataIcons.getEditIcon(), BasicTreeResources.EDIT);
      }
      return mEditButton;
   }

   public JButton getDeleteButton() {
      if (mDeleteButton == null) {
         mDeleteButton = makeButton(DataIcons.getDeleteIcon(), BasicTreeResources.DELETE);
      }
      return mDeleteButton;
   }

   public JButton getMoveUpButton() {
      if (mMoveUpButton == null) {
         mMoveUpButton = makeButton(DataIcons.getMoveUpIcon(), BasicTreeResources.MOVE_UP);
      }
      return mMoveUpButton;
   }

   public JButton getMoveDownButton() {
      if (mMoveDownButton == null) {
         mMoveDownButton = makeButton(DataIcons.getMoveDownIcon(), BasicTreeResources.MOVE_DOWN);
      }
      return mMoveDownButton;
   }

   public JButton getMoveInButton() {
      if (mMoveInButton == null) {
         mMoveInButton = makeButton(DataIcons.getMoveRightIcon(), BasicTreeResources.MOVE_IN);
      }
      return mMoveInButton;
   }

   public JButton getMoveOutButton() {
      if (mMoveOutButton == null) {
         mMoveOutButton = makeButton(DataIcons.getMoveLeftIcon(), BasicTreeResources.MOVE_OUT);
      }
      return mMoveOutButton;
   }

   void reenableButtons() {
      boolean enabled = m_tree.isEnabled() && m_tree.isTreeEditable();
      if (mAddButton != null) {
         mAddButton.setEnabled(enabled && EditableTreeActions.canAddAt(m_tree));
      }
      if (mAddTopOrChildButton != null) {
         mAddTopOrChildButton.setEnabled(enabled && (EditableTreeActions.canAddChild(m_tree) || m_tree.getEditableModel().isRootNull()));
      }
      if (mAddChildButton != null) {
         mAddChildButton.setEnabled(enabled && EditableTreeActions.canAddChild(m_tree));
      }
      if (mDeleteButton != null) {
         mDeleteButton.setEnabled(enabled && EditableTreeActions.canDelete(m_tree));
      }
      if (mMoveUpButton != null) {
         mMoveUpButton.setEnabled(enabled && EditableTreeActions.canMoveUp(m_tree));
      }
      if (mMoveDownButton != null) {
         mMoveDownButton.setEnabled(enabled && EditableTreeActions.canMoveDown(m_tree));
      }
      if (mMoveOutButton != null) {
         mMoveOutButton.setEnabled(enabled && EditableTreeActions.canMoveOut(m_tree));
      }
      if (mMoveInButton != null) {
         mMoveInButton.setEnabled(enabled && EditableTreeActions.canMoveIn(m_tree));
      }
   }

   private JButton makeButton(Icon icon, String tooltip) {
      JButton button = ButtonUtils.makeBarButton(icon, tooltip);
      button.addActionListener(mActionListener);
      return button;
   }

   private JToggleButton makeToggleButton(Icon icon, String tooltip) {
      JToggleButton button = ButtonUtils.makeBarToggleButton(icon, tooltip);
      button.addActionListener(mActionListener);
      return button;
   }
}

