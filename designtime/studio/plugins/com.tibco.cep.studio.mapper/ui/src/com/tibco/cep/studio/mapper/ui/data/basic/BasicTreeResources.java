package com.tibco.cep.studio.mapper.ui.data.basic;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;


/**
 * Helper class for maintaining/loading all string resources for the editable tree editor.
 */
public class BasicTreeResources {
   private BasicTreeResources() {
   }

   public static final String CUT;
   public static final String COPY;
   public static final String PASTE;

   public static final String CLEAR;

   public static final String EDIT;
   public static final String INSERT;

   public static final String ADD_CHILD;

   public static final String DELETE;

   public static final String MOVE;
   public static final String MOVE_UP;
   public static final String MOVE_DOWN;
   public static final String MOVE_IN;
   public static final String MOVE_OUT;

   public static final String CANNOT_PASTE_GENERIC;
   public static final String CANNOT_PASTE;

   public static final String EXPAND;
   public static final String EXPAND_ALL;
   public static final String EXPAND_CONTENT;
   public static final String EXPAND_ERRORS;

   static {

      CUT = loadTreeEditLabel("cut");
      COPY = loadTreeEditLabel("copy");
      PASTE = loadTreeEditLabel("paste");
      CLEAR = loadTreeEditLabel("clear");

      EDIT = loadTreeEditLabel("edit");
      INSERT = loadTreeEditLabel("insert");

      ADD_CHILD = loadTreeEditLabel("addchild");

      DELETE = loadTreeEditLabel("delete");
      MOVE = loadTreeEditLabel("move");
      MOVE_UP = loadTreeEditLabel("moveup");
      MOVE_DOWN = loadTreeEditLabel("movedown");
      MOVE_IN = loadTreeEditLabel("movein");
      MOVE_OUT = loadTreeEditLabel("moveout");

      CANNOT_PASTE_GENERIC = loadTreeEditLabel("cannotpastegeneric");
      CANNOT_PASTE = loadTreeEditLabel("cannotpaste");

      EXPAND = loadTreeEditLabel("expand");

      EXPAND_ALL = loadTreeEditLabel("expandall");
      EXPAND_CONTENT = loadTreeEditLabel("expandcontent");
      EXPAND_ERRORS = loadTreeEditLabel("expanderrors");

      /*
      DELETE_TREE_NODE_TITLE = loadTreeEditTitle("deletetreenode");
      DELETE_MOVE_OUT = loadTreeEditLabel("deletemoveout");
      DELETE_NODE_AND_CONTENTS = loadTreeEditLabel("deletenodeandcontents");
      */
   }

   /**
    * Helper method that loads a tree editor string by just the 'local' name --- it auto-prepends the common
    * part and 'label' suffix.
    *
    * @param key The local part of the resource key.
    */
   private static String loadTreeEditLabel(String key) {
      String fullKey = "ae.treeedit." + key + ".label";
      return DataIcons.getString(fullKey);
   }

   /*
    * Helper method that loads a tree editor string by just the 'local' name --- it auto-prepends the common
    * part and 'title' suffix.
    * @param key The local part of the resource key.
    *
   private static String loadTreeEditTitle(String key)
   {
       String fullKey = "ae.treeedit." + key + ".title";
       return DataIcons.getString(fullKey);
   }*/
}
