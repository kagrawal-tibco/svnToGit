package com.tibco.cep.studio.mapper.ui.data.bind;


//import java.awt.dnd.*;
//import java.awt.datatransfer.*;

/**
 * Purposefully package private.
 * Separate to keep BindingWindow's size down.
 */
class BindingWindowActions {

//   private static class DropOnEdit extends AbstractUndoableEdit {
//      private Binding mBinding;
//      private String mNewScriptValue;
//      private String mOldScriptValue;
//      private boolean mReplaceChildren;
//
//      /**
//       * Maybe store as root & path to be more resilient of tree changes.
//       */
//      public DropOnEdit(Binding binding, String oldScriptValue, String newScriptValue, boolean replaceChildren) {
//         mBinding = binding;
//         mNewScriptValue = newScriptValue;
//         mOldScriptValue = oldScriptValue;
//         mReplaceChildren = replaceChildren;
//      }
//
//      public String getRedoPresentationName() {
//         return "Redoit";
//      }
//
//      public String getUndoPresentationName() {
//         return "Undoit";
//      }
//
//      public String getPresentationName() {
//         return "Drop On";
//      }
//
//      void doit() {
//         mBinding.setFormula(mNewScriptValue);
//      }
//
//      public void redo() throws CannotRedoException {
//         super.redo();
//
//         doit();
//      }
//
//      public void undo() throws CannotUndoException {
//         super.undo();
///*
//            BindingNode node = getNodeForPath(mPath);
//            if (node==null) return; // shouldn't happen.
//            node.getBinding().setScript(mOldBindingValue);
//            node.mCachedReport = null;//ErrorList = null;
//            mErrorFinder.run();
//
//            Rectangle b = mTree.getPathBounds(node.getTreePath());
//            repaint(new Rectangle(b.x,b.y,b.width,b.height+mConstants.ERROR_OVERLAP));*/
//      }
//   }

//   private static class CopyEdit extends AbstractUndoableEdit {
//      private Binding mOnGroup;
//      private String mSuggestedName;
//      private int mSuggestedPosition;
//      private Binding mItem;
//      private boolean mIsCopy;
//      private String mSetScript;
//
//      /**
//       * Maybe store as root & path to be more resilient of tree changes.
//       */
//      public CopyEdit(Binding onGroup, String suggestedName, int suggestedPosition, Binding item, String setScript) {
//         mOnGroup = onGroup;
//         mSuggestedName = suggestedName;
//         mSuggestedPosition = suggestedPosition;
//         mItem = item;
//         mSetScript = setScript;
//         if (mSuggestedName == null) {
//            throw new IllegalArgumentException("Null suggested name");
//         }
//      }
//
//      public String getRedoPresentationName() {
//         return "Redoit";
//      }
//
//      public String getUndoPresentationName() {
//         return "Undoit";
//      }
//
//      public String getPresentationName() {
//         return "Drop On";
//      }
//
////      private static String findAvailableName(Binding group, String suggestedName) {
////         for (int i = 0; ; i++) {
////            String workingName = suggestedName;
////            if (i > 0) {
////               workingName = suggestedName + i;
////            }
/////*                if (group.findResource(workingName)==null) {
////                    return workingName;
////                }*/
////         }
////      }
//
//      void doit() {
//         // not yet complete..
//         /*
//         if (!(mItem instanceof Binding)) {
//             return;
//         }
//         Binding b = (Binding) mItem;
//         String useName = findAvailableName(mOnGroup,mSuggestedName);
//         Binding clone = new Binding(b.getType(),useName);
//         try {
//             if (mSuggestedPosition==-1) {
//                 mOnGroup.addResource(clone);
//             } else {
//                 mOnGroup.addResourceAt(clone,mSuggestedPosition);
//             }
//             if (mSetScript!=null) {
//                 clone.setScript(mSetScript);
//             }
//         } catch (NameConflictException nce) {
//             // can't happen, we picked a unique name.
//             throw new RuntimeException("Shouldn't happen " + nce);
//         } catch (IllegalChildResourceException e) {
//             throw new RuntimeException("Shouldn't happen " + e);
//         }
//         */
//      }
//
//
//      public void redo() throws CannotRedoException {
//         super.redo();
//
//         doit();
//      }
//
//      public void undo() throws CannotUndoException {
//         super.undo();
///*
//            BindingNode node = getNodeForPath(mPath);
//            if (node==null) return; // shouldn't happen.
//            node.getBinding().setScript(mOldBindingValue);
//            node.mCachedReport = null;//ErrorList = null;
//            mErrorFinder.run();
//
//            Rectangle b = mTree.getPathBounds(node.getTreePath());
//            repaint(new Rectangle(b.x,b.y,b.width,b.height+mConstants.ERROR_OVERLAP));*/
//      }
//   }

//   private static class MoveEdit extends AbstractUndoableEdit {
//      private Binding mSource;
//      private Binding mToGroup;
//      private String mSuggestedName;
//      private int mSuggestedPosition;
//      private boolean mIsCopy;
//      private String mSetScript;
//
//      /**
//       * Maybe store as root & path to be more resilient of tree changes.
//       */
//      public MoveEdit(Binding sourceItem, Binding toGroup, String suggestedName, int suggestedPosition) {
//         mToGroup = toGroup;
//         mSource = sourceItem;
//         mSuggestedName = suggestedName;
//         mSuggestedPosition = suggestedPosition;
//         if (mSuggestedName == null) {
//            throw new IllegalArgumentException("Null suggested name");
//         }
//      }
//
//      public String getRedoPresentationName() {
//         return "Redoit";
//      }
//
//      public String getUndoPresentationName() {
//         return "Undoit";
//      }
//
//      public String getPresentationName() {
//         return "Drop On";
//      }
//
//      private static String findAvailableName(Binding group, String suggestedName) {
////         for (int i = 0; ; i++) {
////            String workingName = suggestedName;
////            if (i > 0) {
////               workingName = suggestedName + i;
////            }
///*                if (group.findResource(workingName)==null) {
//                    return workingName;
//                }*/
////         }
//    	  return null;
//      }
//
//      void doit() {
//         // not yet complete..
////         String useName = findAvailableName(mToGroup, mSuggestedName);
////            AEResource parent = mSource.getParent();
//         /*
//         try {
//              int childAt = parent.getIndexOfChild(mSource);
//              parent.removeResource(mSource);
//              if (mSuggestedPosition==-1) {
//                  mToGroup.addResource(mSource);
//              } else {
//                  int actualDrop = mSuggestedPosition;
//                  if (parent==mToGroup) { // if we're dropping in the same folder, adjust:
//                      if (childAt<=mSuggestedPosition) {
//                          actualDrop--;
//                      }
//                  }
//                  mToGroup.addResourceAt(mSource,actualDrop);
//              }
//          } catch (NameConflictException nce) {
//              // can't happen, we picked a unique name.
//              throw new RuntimeException("Shouldn't happen " + nce);
//          } catch (IllegalChildResourceException e) {
//              throw new RuntimeException("Shouldn't happen " + e);
//          }
//          */
//      }
//
//      public void redo() throws CannotRedoException {
//         super.redo();
//
//         doit();
//      }
//
//      public void undo() throws CannotUndoException {
//         super.undo();
///*
//            BindingNode node = getNodeForPath(mPath);
//            if (node==null) return; // shouldn't happen.
//            node.getBinding().setScript(mOldBindingValue);
//            node.mCachedReport = null;//ErrorList = null;
//            mErrorFinder.run();
//
//            Rectangle b = mTree.getPathBounds(node.getTreePath());
//            repaint(new Rectangle(b.x,b.y,b.width,b.height+mConstants.ERROR_OVERLAP));*/
//      }
//   }

/*
    public String[] getDrop(Point at, boolean allowByValue) {
        DropLocation dl = computeDropLocation(at,allowByValue);
        if (dl==null) return null;
        return dl.getNode().convertToPathStrings();
    }

    public UndoableEdit dropReplace(Point at, String[] namePath, boolean remove) {
        clearDropHint();
        TreePath tpath = mTree.getClosestPathForLocation(at.x,at.y);
        if (tpath==null) return null;
        BindingNode node = getTypeNode(tpath);
        Binding bo = node.getBinding();
        StringBuffer script = new StringBuffer();
        for (int i=0;i<namePath.length;i++) {
            if (i>0) script.append('.');
            script.append(namePath[i]);
        }

        // drop by value:
        // Make the path into a script expression (really easy!)
        String newBinding = script.toString();
        String oldBinding = bo.getScript();
        String[] selNamePath = node.convertToPathStrings();
        DropOnEdit edit = new DropOnEdit(selNamePath,oldBinding,newBinding,remove);
        edit.doit();
        return edit;
    }
    */

   /**
    * Returns the undoable edit that this drop caused, null if no action took place.
    */
/*U
    public static UndoableEdit drop(DropLocation dropLocation,  DragObject value) {
        switch (dropLocation.relative) {
            case DropLocation.DROP_ON: {
                Binding bo = dropLocation.getBinding();
                // drop by value:
                // Make the path into a script expression (really easy!)
                StringBuffer script = new StringBuffer();
                for (int i=0;i<namePath.length;i++) {
                    if (i>0) script.append('.');
                    script.append(namePath[i]);
                }
                String newBinding = script.toString();
                String oldBinding = bo.getScript();
                String[] selNamePath = dropLocation.getNode().convertToPathStrings();
                DropOnEdit edit = new DropOnEdit(selNamePath,oldBinding,newBinding,false);
                edit.doit();
                return edit;
            }
            case DropLocation.DROP_BELOW: {
                TreePath path = dropLocation.path;
                BindingNode after = (BindingNode) path.getLastPathComponent();
                BindingNode parentNode = (BindingNode) path.getParentPath().getLastPathComponent();
                int index = parentNode.getIndexOfChild(after);
                if (index==-1) {
                    // shouldn't happen.
                    return null;
                }
                Binding bo = parentNode.getBinding();
                Type undef = UndefinedType.INSTANCE;
                //@ need to check for duplicates.
                bo.addField(endName,index+1,value);
                rebuild(parentNode);//
                String[] selNamePath = parentNode.convertToPathStrings(endName);
                select(selNamePath);
                break;
            }
            case DropLocation.DROP_ON_FOLDER: {
                String[] selNamePath = dropLocation.getNode().convertToPathStrings();
                DropOnFolderEdit edit = new DropOnFolderEdit(selNamePath,endName,value.cloneDeep());
                edit.doit();
                return edit;
            }
            case DropLocation.DROP_ABOVE: {
                TreePath path = dropLocation.path;
                BindingNode after = (BindingNode) path.getLastPathComponent();
                TreePath parentPath = path.getParentPath();
                BindingNode parentNode = getTypeNode(parentPath);
                int index = parentNode.getIndexOfChild(after);
                if (index==-1) {
                    // shouldn't happen.
                    return null;
                }
                Binding bo = parentNode.getBinding();
                bo.addField(endName,index,value);
                rebuild(parentNode);//
                String[] selNamePath = parentNode.convertToPathStrings(endName);
                select(selNamePath);
                break;
            }
        }
        return null;
    }

    public void select(String[] namePath) {
        BindingTreeModel ttm = (BindingTreeModel) mTree.getModel();
        TreePath path = ttm.findTreePath(namePath);
        if (path!=null) {
            mTree.addSelectionPath(path);
        }
    }

    private BindingNode getTypeNode(TreePath path) {
        return (BindingNode) path.getLastPathComponent();
    }*/
}

