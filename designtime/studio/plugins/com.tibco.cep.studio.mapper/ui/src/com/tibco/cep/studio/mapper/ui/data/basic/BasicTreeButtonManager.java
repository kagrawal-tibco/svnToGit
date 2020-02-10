package com.tibco.cep.studio.mapper.ui.data.basic;

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

import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.utils.ButtonUtils;

/**
 * Implementation utility base class for several data related trees.
 * (Replaced by {@link com.tibco.cep.studio.mapper.ui.edittree.EditableTree}
 */
public final class BasicTreeButtonManager {
   private final BasicTree mTree;

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
   private JMenuItem mDeleteAllMenu;
   private JButton mMoveUpButton;
   private JMenuItem mMoveUpMenu;
   private JButton mMoveDownButton;
   private JMenuItem mMoveDownMenu;
   private JButton mMoveInButton;
   private JMenuItem mMoveInMenu;
   private JButton mMoveOutButton;
   private JMenuItem mMoveOutMenu;

   private ActionListener mActionListener;

   BasicTreeButtonManager(BasicTree tree) {
      mTree = tree;
      addKeyActions();
   }

   private void addKeyActions() {
      mTree.addKeyListener(new KeyAdapter() {
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
                        mTree.copy();
                        break;
                     }
                  case KeyEvent.VK_X:
                     {
                        ke.consume();
                        mTree.cut();
                        break;
                     }
                  case KeyEvent.VK_V:
                     {
                        ke.consume();
                        mTree.paste();
                        break;
                     }
               }
            }
            if (ke.isShiftDown()) {
               switch (code) {
                  case KeyEvent.VK_INSERT:
                     {
                        ke.consume();
                        mTree.paste();
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
                     boolean all = ke.isControlDown();
                     ke.consume();
                     BasicTreeActions.delete(mTree, all);
                     break;
                  }
               case KeyEvent.VK_INSERT:
                  {
                     ke.consume();
                     BasicTreeActions.addAt(mTree);
                     break;
                  }
               case KeyEvent.VK_PLUS:
                  {
                     ke.consume();
                     BasicTreeActions.addAt(mTree);
                     break;
                  }
               case KeyEvent.VK_EQUALS:
                  {
                     ke.consume();
                     BasicTreeActions.addAt(mTree);
                     break;
                  }
            }
         }
      });
      mActionListener = new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            Object source = ae.getSource();
            if (source == mAddButton || source == mAddMenu) {
               BasicTreeActions.addAt(mTree);
            }
            if (source == mAddChildButton) {//|| source==mAddChildMenu) {
               BasicTreeActions.addChild(mTree);
            }
            if (source == mAddTopOrChildButton) {
               BasicTreeActions.addTopOrChild(mTree);
            }
            if (source == mDeleteButton || source == mDeleteMenu) {
               BasicTreeActions.delete(mTree);
            }
            if (source == mDeleteAllMenu) {
               BasicTreeActions.delete(mTree, true);
            }
            if (source == mMoveUpButton || source == mMoveUpMenu) {
               BasicTreeActions.moveUp(mTree);
            }
            if (source == mMoveDownButton || source == mMoveDownMenu) {
               BasicTreeActions.moveDown(mTree);
            }
            if (source == mMoveInButton || source == mMoveInMenu) {
               BasicTreeActions.moveIn(mTree);
            }
            if (source == mMoveOutButton || source == mMoveOutMenu) {
               BasicTreeActions.moveOut(mTree);
            }
            if (source == mCutMenu) {
               mTree.cut();
            }
            if (source == mCopyMenu) {
               mTree.copy();
            }
            if (source == mPasteMenu) {
               mTree.paste(); // need to resolve the overrideability of these things...
            }
            if (source == mExpandAllMenu) {
               BasicTreeActions.expandAll(mTree);
            }
            if (source == mExpandContentMenu) {
               BasicTreeActions.expandAllContent(mTree);
            }
            if (source == mExpandErrorsMenu) {
               BasicTreeActions.expandAllErrors(mTree);
            }
            if (source == mEditMenu || source == mEditButton) {
               if (source == mEditButton && !mEditButton.isSelected()) {
                  BasicTreeActions.hideEditor(mTree);
               }
               else {
                  BasicTreeActions.showEditor(mTree);
               }
            }
         }
      };
   }

   void showPopup(int x, int y) {
      boolean enabled = mTree.isEnabled();

      TreePath path = mTree.getPathForRow(mTree.getRowForYLocation(y));
      mTree.setSelectionPath(path);

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

      JPopupMenu menu = new JPopupMenu();

      if (mTree.isEditable()) {
         menu.add(mEditMenu);
      }
      JMenu expand = new JMenu(BasicTreeResources.EXPAND);
      //expand.setIcon(DataIcons.getBlankIcon());
      if (mTree.hasContent()) {
         expand.add(mExpandContentMenu);
      }
      if (mTree.hasErrors()) {
         expand.add(mExpandErrorsMenu);
      }
      if (expand.getMenuComponentCount() == 0) {
         // forget it, just have 'expand.
         mExpandAllMenu.setText(BasicTreeResources.EXPAND);
         menu.add(mExpandAllMenu);
      }
      else {
         expand.add(mExpandAllMenu);
         menu.add(expand);
      }

      mTree.augmentTopContextMenu(menu);

      menu.addSeparator();

      if (mTree.isEditable()) {
         menu.add(mCutMenu);
         mCutMenu.setEnabled(enabled && BasicTreeActions.canCut(mTree));
      }
      menu.add(mCopyMenu);
      mCopyMenu.setEnabled(BasicTreeActions.canCopy(mTree));

      if (mTree.isEditable()) {
         menu.add(mPasteMenu);
         mPasteMenu.setEnabled(enabled && BasicTreeActions.canPaste(mTree));
         menu.add(mDeleteMenu);
         mDeleteMenu.setEnabled(enabled && BasicTreeActions.canDelete(mTree));
         if (mTree.mHasDeleteAll) {
            mDeleteAllMenu.setEnabled(enabled && BasicTreeActions.canDelete(mTree));
            menu.add(mDeleteAllMenu);
         }
      }

      if (mTree.allowsCrossBranchDrops() && mTree.isEditable()) {
         mMoveInMenu.setEnabled(enabled && BasicTreeActions.canMoveIn(mTree));
         mMoveOutMenu.setEnabled(enabled && BasicTreeActions.canMoveOut(mTree));
         menu.add(mMoveOutMenu);
         menu.add(mMoveInMenu);
      }

      menu.show(mTree, x, y);
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
      boolean enabled = mTree.isEnabled();
      if (mAddButton != null) {
         mAddButton.setEnabled(enabled && BasicTreeActions.canAddAt(mTree));
      }
      if (mAddTopOrChildButton != null) {
         mAddTopOrChildButton.setEnabled(enabled && (BasicTreeActions.canAddChild(mTree) || mTree.isRootNull()));
      }
      if (mAddChildButton != null) {
         mAddChildButton.setEnabled(enabled && BasicTreeActions.canAddChild(mTree));
      }
      if (mDeleteButton != null) {
         mDeleteButton.setEnabled(enabled && BasicTreeActions.canDelete(mTree));
      }
      if (mMoveUpButton != null) {
         mMoveUpButton.setEnabled(enabled && BasicTreeActions.canMoveUp(mTree));
      }
      if (mMoveDownButton != null) {
         mMoveDownButton.setEnabled(enabled && BasicTreeActions.canMoveDown(mTree));
      }
      if (mMoveOutButton != null) {
         mMoveOutButton.setEnabled(enabled && BasicTreeActions.canMoveOut(mTree));
      }
      if (mMoveInButton != null) {
         mMoveInButton.setEnabled(enabled && BasicTreeActions.canMoveIn(mTree));
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

