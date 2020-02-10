package com.tibco.cep.studio.mapper.ui.edittree;

/**
 * Works with {@link EditableTree} to allow plug-able expand functionality.<br>
 * Works with {@link EditableTreeModel} and {@link EditableTreeActions#expandAll}, etc.
 */
public interface EditableTreeExpansionHandler {
   /**
    * Optionally return a unique identifier to indicate recursive nodes.<br>
    * The objects will be compared by {@link Object#equals}.
    *
    * @param node The tree node from the {@link EditableTreeModel}.
    * @return The object representing this nodes unique identity, or null if n/a.
    */
   Object getNodeIdentity(Object node);

   /**
    * Indicates if the concept of 'content' is supoprted, if so {@link #hasContent}.
    * Content is basically anything where the user has done work (filled in stuff) as opposed to a skeleton or
    * automatic content. (Many trees don't have this concept...)
    */
   boolean isContentSupported();

   /**
    * Indicates if this node should be considered as having content(recursively).
    *
    * @param node The node.
    */
   boolean hasContent(Object node);

   /**
    * Indicates if the concept of 'error' is supoprted, if so {@link #hasError}.
    * Errors are basically anything where the there is an error on a node.
    */
   boolean isErrorSupported();

   /**
    * Indicates if this node should be considered expandable from a hasError (recursively) point of view.
    *
    * @param node The node.
    * @return
    */
   boolean hasError(Object node);
}

