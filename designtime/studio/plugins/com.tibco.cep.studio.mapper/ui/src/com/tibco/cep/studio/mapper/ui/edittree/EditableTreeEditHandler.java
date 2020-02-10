package com.tibco.cep.studio.mapper.ui.edittree;




/**
 * Works with {@link EditableTree} to allow plug-able edit action on a per-node basis.<br>
 */
public interface EditableTreeEditHandler {
   /**
    * Show the editor for the node.
    */
   void showEditor(Object node);

   void hideEditor();
}

