package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * The base class for nodes that work with {@link BasicTree}.
 */
public abstract class BasicTreeNode {
   private BasicTreeNode mParent;
   protected BasicTreeModel mModel; // set by model.
   private BasicTreeNode[] mChildren;
   private Color mBackgroundColor;
   private TreePath mCachedPath;

   public BasicTreeNode() {
   }

   public final BasicTree getTree() {
      if (mModel == null) {
         return null;
      }
      return mModel.getTree();
   }

   public final TreePath getTreePath() {
      // Computing tree paths is expensive & creates tons of garbage (especially, for example, in the mapper where
      // tons of paths are created, so cache them)
      if (mCachedPath == null) {
         // Lazily computed:
         if (mParent == null) {
            mCachedPath = new TreePath(this);
         }
         else {
            mCachedPath = mParent.getTreePath().pathByAddingChild(this);
         }
      }
      return mCachedPath;
   }

   void setParent(BasicTreeNode p) {
      mParent = p;
      // Clear cached path
      mCachedPath = null;
   }

   public final int getDepth() {
      if (mParent == null) {
         return 0;
      }
      return mParent.getDepth() + 1;
   }

   public final boolean hasBeenExpanded() {
      return mChildren != null;
   }

   public abstract BasicTreeNode[] buildChildren();

   public abstract boolean isEditable();

   /**
    * For expandAll(), etc., the object that uniquely identifies this node's contents,
    * used to prevent infinite recursion on expands.  Returning null is ok if there is no
    * such thing.
    */
   public abstract Object getIdentityTerm();

   public final int getChildCount() {
      // Do not do this optimization: if (isLeaf()) {
      //    return 0;
      //}.. makes it tough for simpl
      if (mChildren == null) {
         /* Not true for parameter editor right now, fix...if (mModel==null) {
             throw new NullPointerException("Model not set on " + this);
         }*/
         mChildren = buildChildren();
         for (int i = 0; i < mChildren.length; i++) {
            mChildren[i].mModel = mModel;
            mChildren[i].setParent(this);
         }
      }
      return mChildren.length;
   }

   public final BasicTreeNode getChild(int index) {
      if (mChildren == null) {
         getChildCount(); // builds 'em.
      }
      return mChildren[index];
   }

   /**
    * Indicates if the node can legally have children; a leaf does not necessarily mean !{@link #canHaveChildren}, it may
    * indicate that it's a leaf 'right-now' (and for display purposes, it's usually better to say isLeaf when it has no children)
    */
   public boolean canHaveChildren() {
      return !isLeaf();
   }

   /**
    * Indicates if the node is a leaf; a leaf does not necessarily mean !{@link #canHaveChildren}, it may
    * indicate that it's a leaf 'right-now' (and for display purposes, it's usually better to say isLeaf when it has no children)
    */
   public abstract boolean isLeaf();

   public final BasicTreeNode getParent() {
      return mParent;
   }

   public final int getIndexOfChild(Object child) {
      if (isLeaf()) {
         return 0;
      }
      int ct = getChildCount();
      for (int i = 0; i < ct; i++) {
         if (child == getChild(i)) {
            return i;
         }
      }
      return -1;
   }

   /**
    * By default, prepends an array index to array children.
    */
   public String getDisplayName() {
      if (mParent != null && mParent.isArray()) {
         int index = mParent.getIndexOfChild(this) + 1;
         return index + " - " + getName();
      }
      return getName();
   }

   /**
    * Indicates that the display name is a logical control name & should be
    * displayed differently (i.e. italics)
    * (Actually, currently only displays (in binding tree) markers this way; should be renamed.
    */
   public boolean getDisplayControl() {
      return false;
   }

   /**
    * For name editable fields:
    */
   public void setDisplayName(String name) {
      // ignore..
   }

   /**
    * For a delete, indicates if a marker node can be substituted in, returns null if n/a.
    *
    * @return The marker node or null.
    */
   public BasicTreeNode createReplacementMarker() {
      return null;
   }

   // Display stuff:
   public abstract String getName();

   public abstract Icon getIcon();

   public boolean getShowsCardinality() {
      return false;
   }

   public int getMin() {
      return 1;
   }

   public int getMax() {
      return 1;
   }

   public boolean getNillable() {
      return false;
   }

   /**
    * Indicates if it should super-impose an error icon over the primary icon.
    */
   public boolean isErrorIcon() {
      return false;
   }

   public String getDataValue() {
      return null;
   }

   public void setDataValue(String value) {
   }

   /**
    * If true, the children of this node show up with array indexes.
    */
   public boolean isArray() {
      return false;
   }

   /**
    * If true, the data values can legally be null.
    */
   public boolean isOptional() {
      return false;
   }

   public String toXML() {
      return "<xml not yet implemented here>";
   }

   /**
    * Gets the node corresponding to the given XPath.
    * index = -1 means all of repeating.
    */
   public final BasicTreeNode getNodeForXStep(String step, int index, boolean isLastStep, NamespaceContextRegistry namespaceToPrefixResolver) {
      if (isLeaf()) {
         return null;
      }
      if (step.equals("text()")) {
         return this;
      }
      int cc = getChildCount();
      for (int i = 0; i < cc; i++) {
         BasicTreeNode c = getChild(i);
         String xstepName;
         xstepName = c.getXStepName(false, namespaceToPrefixResolver);
         if (xstepName == null) {
            if (step.endsWith("*") && isLastStep) {
               return c;
            }
            // search inside:
            BasicTreeNode n = c.getNodeForXStep(step, index, isLastStep, namespaceToPrefixResolver);
            if (n != null) {
               return n;
            }
         }
         else {
            if (xstepName.equals(step) || ("*".equals(step) && cc == 1)) {
               return c;
            }
         }
      }
      return null;
   }

   /**
    * Override if there is child content (useful child data).<br>
    * Default is to check child count>0.
    *
    * @return
    */
   public boolean hasChildContent() {
      return getChildCount() > 0;
   }

   /**
    * Called before children are adjusted, return 'true' if remove is ok, 'false' if not.
    */
   public boolean remove(int index) {
      return false;
   }

   /**
    * Called before children are adjusted, return 'true' if add is ok, 'false' if not.
    */
   public boolean addAt(int index, BasicTreeNode value) {
      return false;
   }

   public boolean replace(int index, BasicTreeNode value) {
      return false;
   }

   /**
    * Creates a new node for an array element.
    */
   public BasicTreeNode createArrayElement() {
      return null;
   }

   /**
    * when canBeCreated(), creates self. (i.e. optional data node)
    */
   public void create() {
   }

   public void delete() {
   }

   /**
    * Internal to tree.
    */
   final void removed(int index) {
      BasicTreeNode[] c = new BasicTreeNode[mChildren.length - 1];
      int j = 0;
      for (int i = 0; i < mChildren.length; i++) {
         if (i != index) {
            c[j] = mChildren[i];
            j++;
         }
      }
      mChildren[index].setParent(null);
      mChildren[index].mModel = null;
      mChildren = c;
      afterChildrenChanged();
   }

   /**
    * Sort of a hack for type editor, remove later..
    */
   protected void fireStructureChanged() {
      if (mModel != null) {
         mModel.fireStructureChanged(this);
      }
   }

   /**
    * Subclasses call this when the subclass changes the children.
    */
   protected final void childrenChanged() {
      mChildren = null;
      fireStructureChanged();
      afterChildrenChanged();
   }

   /**
    * Subclasses can override to do processing after a child change.
    */
   public void afterChildrenChanged() {
   }

   /**
    * Internal to tree (can be used to build structure...)
    */
   protected final void addedAt(int index, BasicTreeNode node) {
      int cc = getChildCount(); // make sure mChildren not null.
      BasicTreeNode[] c = new BasicTreeNode[cc + 1];
      int j = 0;
      for (int i = 0; i < c.length; i++) {
         if (i != index) {
            c[i] = mChildren[j];
            j++;
         }
         else {
            c[i] = node;
         }
      }
      node.setParent(this);
      node.mModel = mModel;
      mChildren = c;
      afterChildrenChanged();
   }

   /**
    * Gets the error associated with the line as a whole (not the data itself),
    * null means no error.
    */
   public String getLineError() {
      return null;
   }

   public Object getReport() {
      return null;
   }

   public void setReport(Object reportObject) {
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      return getName();
   }

   public BasicTextRenderer getDataRenderer() {
      return getTree().mDefaultDataRenderer;
   }

   /**
    * Is a data-value of length 0 meaningful?
    * If not true, then data-value of length 0 is equivalent to null (data-value)
    * Default is true.
    */
   public boolean allowsEmptyDataValue() {
      return true;
   }

   public ErrorMessageList getErrorMessages() {
      return null;
   }

   public String getDataToolTip(int xPosition) {
      return null;
   }

   /**
    * By default, just clears data text.  Override to do more.
    */
   public void clearContent() {
      setDataValue(null);
   }

   /**
    * By default, just clears data text.  Override to do more.
    */
   public void deleteContent() {
      setDataValue(null);
   }

   public String getBlankText() {
      return "(blank)";
   }

   public boolean isNameEditable() {
      return false;
   }

   public boolean canDelete() {
      return true;
   }

   /**
    * Has this node had a substitution performed on it?
    */
   public boolean isSubstituted() {
      return false;
   }

   protected void contentChanged() {
      if (getTree() != null) {
         getTree().contentChanged();
      }
   }

   /**
    * Asks if this node can move in a level (if it makes sense).
    */
   public boolean canMoveIn() {
      return true;
   }

   public boolean canMoveUp() {
      return true;
   }

   public boolean canMoveDown() {
      return true;
   }

   public boolean canMoveOut() {
      return true;
   }

   /**
    * The background color used in painting the data area (if applicable) for this node.
    *
    * @return The background color, or null to indicate use default.
    */
   public Color getDataBackgroundColor() {
      return null;
   }

   /**
    * Indicates if the 'pencil' (i.e. edit) button should be enabled.<br>
    * By default, it is the same as {@link #isEditable()}, but a subclass may override to change behavior.
    */
   public boolean isDialogEditable() {
      return isEditable();
   }

   public final Color getBackgroundColor() {
      return mBackgroundColor;
   }

   public final void setBackgroundColor(Color c) {
      mBackgroundColor = c;
   }

   /**
    * If true, paints the name in a different color.
    */
   public boolean isDisabled() {
      return false;
   }
}
