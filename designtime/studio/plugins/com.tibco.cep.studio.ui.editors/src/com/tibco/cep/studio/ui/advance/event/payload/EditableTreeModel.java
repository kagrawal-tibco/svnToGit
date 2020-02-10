package com.tibco.cep.studio.ui.advance.event.payload;



/**
 * An extension of {@link TreeModel} to allow editing.
 */
public interface EditableTreeModel{
   /**
    * Create a new node which will 'surround' the given node (which may be null).<br>
    * Note that the optionalAroundNode should <b>only</b> be used for defaulting types, etc. of the
    * new node, it should not be added as a child.
    *
    * @param optionalAroundNode The node to surround, or null.
    * @return A new node, should be 'free' -- i.e. not connected to tree.
    */
   Object createNewParent(Object optionalAroundNode);

   /**
    * Create a new node which will 'surround' the given node (which may be null).<br>
    * Note that the optionalAroundNode should <b>only</b> be used for defaulting types, etc. of the
    * new node, it should not be added as a child.
    *
    * @param parent The parent node, never null.
    * @return A new node, should be 'free' -- i.e. not connected to tree.
    */
   Object createNewChild(Object parent);

   /**
    * The editable tree model <b>requires</b> parent access unlike the base TreeModel.
    */
   Object getParent(Object child);

   /**
    * Allows a delete operation to replace the node (with a marker, for instance).<br>
    *
    * @param node The node that is being deleted.
    * @return The replacement, or null for none.
    */
   Object getDeleteReplacement(Object node);

   /**
    * Can the given node possibly have children?
    *
    * @param node The node, never null.
    */
   boolean canHaveChildren(Object node);

   /**
    * @param parent
    * @param index
    * @param newChild
    */
   void addAt(Object parent, int index, Object newChild);

   /**
    * Removes the child at index.
    *
    * @param parent The parent.
    * @param index  The index.
    */
   void remove(Object parent, int index);

   /**
    * If true, a 'null' root is allowed otherwise not.<br>
    * If set, the methods {@link #isRootNull} and {@link #setRootNull} must be defined.
    */
   boolean getAllowsRootNull();

   /**
    * Is the root 'null' (i.e. no tree).<br>
    * Not all models may support is root null, if not, they should return
    * false in {@link #getAllowsRootNull}.
    */
   boolean isRootNull();

   /**
    * Sets if the root 'null' (i.e. no tree).<br>
    * Not all models may support is root null, if not, return false in {@link #getAllowsRootNull}.
    */
   void setRootNull(boolean nullRoot);

   void setRoot(Object node);

   Object getRoot();

   int getChildCount(Object obj);
}

