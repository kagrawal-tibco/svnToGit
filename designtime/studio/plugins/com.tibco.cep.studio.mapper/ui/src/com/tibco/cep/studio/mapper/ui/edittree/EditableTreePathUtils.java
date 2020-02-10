package com.tibco.cep.studio.mapper.ui.edittree;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * A set of based-based utility methods both used by {@link EditableTree} but also externally useful.
 */
@SuppressWarnings("rawtypes")
public final class EditableTreePathUtils {
   /**
    * Gets the tree path for the node.
    *
    * @param model  The model containing the node.
    * @param object The node.
    * @return The tree path for the node.
    */
   public static TreePath getTreePath(EditableTreeModel model, Object object) {
      Object p = model.getParent(object);
      if (p == null) {
         return new TreePath(object);
      }
      return getTreePath(model, p).pathByAddingChild(object);
   }

   /**
    * Gets the depth of a node.
    *
    * @return The depth, root is depth 0.
    */
   public static int getDepth(EditableTreeModel model, Object node) {
      Object p = model.getParent(node);
      if (p == null) {
         return 0;
      }
      return getDepth(model, p) + 1;
   }

   /**
    * Turns all the expanded paths into a List of String[] paths.
    */
   public static ArrayList<int[]> getAllExpandedPosPaths(EditableTree tree) {
      Enumeration<TreePath> e = tree.getExpandedDescendants(new TreePath(tree.getModel().getRoot()));
      ArrayList<int[]> al = new ArrayList<int[]>();
      if (e != null) {
         while (e.hasMoreElements()) {
            TreePath p = (TreePath) e.nextElement();
            int[] pp = toPosPath(tree.getEditableModel(), p);
            al.add(pp);
         }
      }
      return al;
   }

   /**
    * Collapses all paths below a path.
    *
    * @param tree
    * @param path
    */
   public static void collapseAllPathsBelow(JTree tree, TreePath path) {
      for (; ;) {
         // For some reason, unless this is called until there's no change, it doesn't properly get all the
         // paths.  Whatever, it works now.
         boolean any = collapseAllPathsBelowInternal(tree, path);
         if (!any) {
            break;
         }
      }
   }

   private static boolean collapseAllPathsBelowInternal(JTree tree, TreePath path) {
      Enumeration<TreePath> e = tree.getExpandedDescendants(path);
      if (e == null) { // can return null, how nice.
         return false;
      }
      // copy list before potentially modifying things:
      ArrayList<TreePath> temp = new ArrayList<TreePath>();
      while (e.hasMoreElements()) {
         TreePath tp = (TreePath) e.nextElement();
         if (tp.getPathCount() > 1) {
            // Without above check, if you call this on the root, it will keep loop forever as this can't be collapsed...
            temp.add(tp);
         }
      }
      for (int i = 0; i < temp.size(); i++) {
         TreePath p = temp.get(i);
         tree.collapsePath(p);
      }
      return temp.size() > 0;
   }

   /**
    * Turns all the expanded paths below the given node into a List of String[] paths.
    */
   public static ArrayList<int[]> getAllExpandedPosPaths(EditableTree tree, Object node) {
      TreePath path = getTreePath(tree.getEditableModel(), node);
      Enumeration<TreePath> e = tree.getExpandedDescendants(path);
      ArrayList<int[]> al = new ArrayList<int[]>();
      if (e != null) {
         while (e.hasMoreElements()) {
            TreePath p = e.nextElement();
            int[] pp = toPosPath(tree.getEditableModel(), p, node);
            al.add(pp);
         }
      }
      return al;
   }

   /**
    * Turns all the expanded paths into a List of String[] paths.
    */
   public static ArrayList<int[]> getAllSelectedPosPaths(EditableTree tree) {
      TreePath[] paths = tree.getSelectionPaths();
      ArrayList<int[]> al = new ArrayList<int[]>();
      if (paths != null) {
         for (int i = 0; i < paths.length; i++) {
            TreePath p = paths[i];
            int[] pp = toPosPath(tree.getEditableModel(), p);
            al.add(pp);
         }
      }
      return al;
   }

   /**
    * Expands all int[] paths possible (by position).
    */
   public static void expandAllPosPaths(EditableTree tree, ArrayList l) {
      int sz = l.size();
      for (int i = 0; i < sz; i++) {
         int[] pp = (int[]) l.get(i);
         TreePath tp = fromPosPath(tree.getEditableModel(), pp);
         if (tp != null) {
            tree.expandPath(tp);
         }
      }
   }

   /**
    * Expands all int[] paths possible (by position) where baseNode is considered the root.
    */
   public static void expandAllPosPaths(EditableTree tree, ArrayList l, Object baseNode) {
      int sz = l.size();
      for (int i = 0; i < sz; i++) {
         int[] pp = (int[]) l.get(i);
         TreePath tp = fromPosPath(tree.getEditableModel(), baseNode, pp);
         if (tp != null) {
            tree.expandPath(tp);
         }
      }
   }

   /**
    * Selects all int[] paths possible (by position).
    */
   public static void selectAllPosPaths(EditableTree tree, ArrayList l) {
      int sz = l.size();
      tree.clearSelection();
      for (int i = 0; i < sz; i++) {
         int[] pp = (int[]) l.get(i);
         TreePath tp = fromPosPath(tree.getEditableModel(), pp);
         if (tp != null) {
            tree.addSelectionPath(tp);
         }
      }
   }

   /**
    * Extracts a node by following index-of-child paths.
    *
    * @param tree      The tree.
    * @param positions The index-of-child indexes describing the node.
    * @return The node, or null if not found because of out-of-bounds or whatever.
    * @see #toPosPath
    */
   public static Object nodeFromPosPath(EditableTree tree, int[] positions) {
      TreePath path = fromPosPath(tree.getEditableModel(), positions);
      if (path == null) {
         return null;
      }
      return path.getLastPathComponent();
   }

   public static TreePath fromPosPath(EditableTreeModel treeModel, int[] positions) {
      Object at = treeModel.getRoot();
      return fromPosPath(treeModel, at, positions);
   }

   private static TreePath fromPosPath(EditableTreeModel model, Object root, int[] positions) {
      TreePath tp = getTreePath(model, root);
      Object at = root;
      for (int i = 0; i < positions.length; i++) {
         int pos = positions[i];
         if (pos < 0 || pos >= model.getChildCount(at)) {
            return null;
         }
         at = model.getChild(at, pos);
         tp = tp.pathByAddingChild(at);
      }
      return tp;
   }

   /**
    * Gets the path as described by a set of index-of-childs from the root.
    * This is particularly valuable for
    *
    * @param node The node from which to extract the path.
    * @return A non-null array of child-index steps to get there.
    */
   public static int[] toPosPath(EditableTreeModel model, Object node) {
      TreePath path = getTreePath(model, node);
      return toPosPath(model, path);
   }

   public static int[] toPosPath(EditableTreeModel model, TreePath path) {
      if (path == null) {
         return new int[0];
      }
      int[] t = new int[path.getPathCount() - 1];
      for (int i = 0; i < t.length; i++) {
         Object node = path.getPathComponent(i + 1);
         Object parent = model.getParent(node);
         int index = model.getIndexOfChild(parent, node);
         t[i] = index;
      }
      return t;
   }

   private static int[] toPosPath(EditableTreeModel model, TreePath path, Object depth) {
      if (path == null) {
         return new int[0];
      }
      TreePath trimPath = trimPathTo(path, depth);
      return toPosPath(model, trimPath);
   }

   /**
    * Returns the passed in path shortened to start with the newRootNode as its root.
    *
    * @param path        The original path
    * @param newRootNode The newRootNode that should be the new root.
    * @return The new path which is the same as the original path, but 'stopping' at the new root newRootNode.
    */
   private static TreePath trimPathTo(TreePath path, Object newRootNode) {
      if (newRootNode == null || path == null) {
         return null;
      }
      if (path.getLastPathComponent() == newRootNode) {
         return new TreePath(newRootNode);
      }
      TreePath pp = trimPathTo(path.getParentPath(), newRootNode);
      if (pp != null) {
         return pp.pathByAddingChild(path.getLastPathComponent());
      }
      return new TreePath(path.getLastPathComponent());
   }
}

