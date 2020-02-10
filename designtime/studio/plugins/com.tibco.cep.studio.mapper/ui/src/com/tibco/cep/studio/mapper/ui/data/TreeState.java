package com.tibco.cep.studio.mapper.ui.data;

/**
 * A utility class that works with base tree.  Keeps the 'state' of a tree,
 * i.e. the expanded paths.  Records a path as a String[].
 */
public class TreeState {
   public String[][] mPaths;

   public int[][] mIntPaths; // expanded
   public int[][] mIntSelPaths;

   public TreeState(String[][] paths) {
      mPaths = paths;
   }

   public TreeState(int[][] paths, int[][] selPaths) {
      mIntPaths = paths;
      mIntSelPaths = selPaths;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("TreeState {\n");
      sb.append("expanded:");
      for (int i = 0; i < mIntPaths.length; i++) {
         sb.append("\n");
         int[] j = mIntPaths[i];
         for (int x = 0; x < j.length; x++) {
            if (x > 0) {
               sb.append(", ");
            }
            sb.append(j[x]);
         }
      }
      sb.append("\n}");
      return sb.toString();
   }
}

