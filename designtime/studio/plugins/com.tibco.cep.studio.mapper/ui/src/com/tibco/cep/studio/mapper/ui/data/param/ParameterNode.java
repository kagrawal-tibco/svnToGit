package com.tibco.cep.studio.mapper.ui.data.param;

import javax.swing.Icon;
import javax.swing.tree.TreePath;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.DocumentNameValidator;
import com.tibco.cep.studio.mapper.ui.data.utils.NameValidatingDocument;
import com.tibco.cep.studio.mapper.ui.edittree.simple.SimpleTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The PRIVATE class for {@link ParameterEditor}... it is public now because the editor isn't properly set up with
 * a model.  Fix that soon.
 */
public final class ParameterNode extends SimpleTreeNode {
   private ContentModelCategory mCategory = ElementSequenceCategory.INSTANCE;
   private TypeCategory m_typeCategory = null; // only relevant when above is element or attribute ref.
   private int mMin = 1;
   private int mMax = 1;
   private String m_name = "root";
   private String mDefaultVal;
   private Object mContentModelDetails = null;
   private ParameterEditor mEditor;
   private boolean mFixedName = false;

   public ParameterNode(ParameterEditor editor) {
      mEditor = editor;
   }

   public boolean isNameEditable() {
      if (mFixedName) {
         return false;
      }
      if (mEditor.getNamedVariablesMode()) {
         return true;
      }
      return mCategory.canHaveName();
   }

   public void setFixedName(boolean val) {
      mFixedName = val;
   }

   /**
    * A type-safe version of {@link #getChild}.
    */
   public ParameterNode getChildParameter(int index) {
      return (ParameterNode) getChild(index);
   }

   public ParameterEditor getEditor() {
      return mEditor;
   }

   public boolean getFixedName() {
      return mFixedName;
   }

   public boolean isArray() {
      return !isLeaf();
   }

   public ParameterNode createNewChild() {
      ParameterNode n = mCategory.createNewChild(mEditor);
      int ac = mEditor.getAllowsOnlyCardinality();
      if (ac != -1) {
         n.setCardinalityByCode(ac);
      }
/* JTB TEMPORARY COMMENT OUT
      // WCETODO HACKY, fix, make api somewhere, somehow.
      if (mEditor instanceof XsltParamTreeEditor) {
         n.setContentModelCategory(ElementRefCategory.INSTANCE);
      }
*/
//        n.mCategory = ElementTypeRefCategory.INSTANCE;
      n.m_typeCategory = mEditor.getDefaultTypeCategory();
      n.m_name = makeValidName("param");
      getTree().setSelectionPath(new TreePath(n));
      return n;
   }

   private String makeValidName(String name) {
      DocumentNameValidator dnv = mEditor.getDocumentNameValidator();
      if (dnv == null) {
         return name;
      }
      return NameValidatingDocument.makeValidName(name, dnv);
   }

   /**
    * Sets min and max based on the cardinality code given in {@link ParameterEditor}.
    *
    * @param code The integer code.
    */
   public void setCardinalityByCode(int code) {
      switch (code) {
         case ParameterEditor.CARDINALITY_REQUIRED:
            setCardinality(1, 1);
            break;
         case ParameterEditor.CARDINALITY_OPTIONAL:
            setCardinality(0, 1);
            break;
         case ParameterEditor.CARDINALITY_REPEATING:
            setCardinality(0, SmParticle.UNBOUNDED);
            break;
         default:
            setCardinality(1, SmParticle.UNBOUNDED);
            break;
      }
   }

   public void setDefaultValue(String val) {
      // check for no change:
      if (mDefaultVal == val) {
         return;
      }
      if (mDefaultVal != null && mDefaultVal.equals(val)) {
         return;
      }
      mDefaultVal = val;
      contentChanged();
   }

   public String getDefaultValue() {
      return (mDefaultVal);
   }

   public void setCardinality(int min, int max) {
      setMin(min);
      setMax(max);
   }

   public boolean remove(int index) {
      return true;
   }

   public boolean addAt(int index, SimpleTreeNode node) {
      if (!(node instanceof ParameterNode)) {
         // can't happen.
         return false;
      }
      ParameterNode ett = (ParameterNode) node;
      ett.setName(uniquifyChildName(ett.getName(), ett));
      return true;
   }

   public boolean isLeaf() {
      return mCategory.isLeaf();
   }

   /**
    * Creates the pre-existing children (on expand) --- this tree doesn't use that feature.
    *
    * @return
    */
   public SimpleTreeNode[] buildChildren() {
      return new SimpleTreeNode[0];
   }

   public Icon getIcon() {
      return mCategory.getDisplayIcon(this);
   }

   public String toXML() {
      return mEditor.getSchemaText(this);
   }

   public int getMin() {
      return mMin;
   }

   public void setMin(int min) {
      if (min != mMin) {
         mMin = min;
         contentChanged();
      }
   }

   public int getMax() {
      return mMax;
   }

   public void setMax(int max) {
      if (mMax != max) {
         mMax = max;
         contentChanged();
      }
   }

   public String getName() {
      return m_name;
   }

   public String getDisplayName() {
      if (mFixedName || (mEditor.getNamedVariablesMode() && isFirstLevelNode())) {
         return m_name;
      }
      return mCategory.getDisplayName(this);
   }

   boolean isFirstLevelNode() {
      return getParent() != null && getParent().getParent() == null;
   }

   public void setContentModelCategory(ContentModelCategory cat) {
      if (cat == mCategory) {
         return;
      }
      mCategory = cat;
   }

   public void setContentModelCategoryUpdate(ContentModelCategory category) {
      if (getContentModelCategory() == category) {
         return;
      }
      mCategory = category;
      if (category.isLeaf() && getChildCount() > 0) {
         // removes all the children.
         childrenChanged();
      }
      super.fireStructureChanged();
      // hacky, but need to repaint the tree when this happens (really need to make Node non-gui aware)
      if (getTree() != null) {
         getTree().repaint();
         getTree().setSelectionPath(getTreePath());
      }
      contentChanged();
   }


   public ContentModelCategory getContentModelCategory() {
      return mCategory;
   }

   public void setTypeCategory(TypeCategory category) {
      m_typeCategory = category;
      if (getTree() != null) {
         getTree().repaint();
         getTree().setSelectionPath(getTreePath());
      }
      contentChanged();
   }

   public TypeCategory getTypeCategory() {
      return m_typeCategory;
   }

   public String uniqueifyName(String name) {
      ParameterNode p = (ParameterNode) getParent();
      if (p == null) {
         if (name.length() == 0) {
            name = "_";
         }
         return name;
      }
      return p.uniquifyChildName(name, this);
   }

   public String uniquifyChildName(String name, ParameterNode child) {
      if (name.length() == 0) {
         name = "_";
      }
      String un = name;
      for (int i = 1; ; i++) {
         DocumentNameValidator dv = mEditor.getDocumentNameValidator();
         if (dv != null) {
            un = dv.makeValidName(un);
         }
         // if the new unique name fails
         // the uniqueness check locally
         if (!containsName(un, child)) {
            return un;
         }
         un = name + i;
      }
   }

   private boolean containsName(String name, ParameterNode exclude) {
      int cc = getChildCount();
      for (int i = 0; i < cc; i++) {
         ParameterNode node = (ParameterNode) getChild(i);
         if (node == exclude) {
            continue;
         }
         if (node.getName().equals(name)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Externally added call, should not be here...
    *
    * @param name
    */
   public void setName(String name) {
      if (m_name == null || !m_name.equals(name)) {
         m_name = name;
         contentChanged();
      }
   }

   /**
    * Sets the name (uniquifying it)
    */
   public void setDisplayName(String name) {
      String nn = uniqueifyName(name);
      if (nn.length() == 0) {
         nn = "_"; // hacky, fornow.
      }
      m_name = nn;
      mEditor.refreshDetails();
   }

   public void setContentModelDetailsNoUpdate(Object details) {
      mContentModelDetails = details;
   }

   public void setContentModelDetails(Object details) {
      if (mContentModelDetails == details) {
         return;
      }
      if (mContentModelDetails != null && details != null && mContentModelDetails.equals(details)) {
         return;
      }
      mContentModelDetails = details;
      contentChanged();
   }

   public Object getContentModelDetails() {
      return mContentModelDetails;
   }

   public Object getIdentityTerm() {
      return null;
   }

   /**
    * Used by parse.
    */
   public void addNode(ParameterNode node) {
      if (isLeaf()) {
         return;
      }
      super.addedAt(getChildCount(), node);
   }

   public boolean hasChildContent() {
      // for parameter nodes, it's simple, children=content (there are no formula, etc.)
      return getChildCount() > 0;
   }

   /*
   public boolean canMoveDown()
   {
       return !m_fixedLocation;
   }

   public boolean canMoveUp()
   {
       return !m_fixedLocation;
   }

   public boolean canBeDeleted()
   {
       return !m_fixedLocation;
   }*/

   public SmSequenceType computeXType(NamespaceContextRegistry ni, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      return getContentModelCategory().computeXType(this, ni, uiAgent, smCompProvider);
   }

   public SmSequenceType[] computeChildrenXType(NamespaceContextRegistry ni, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      int cc = getChildCount();
      SmSequenceType[] r = new SmSequenceType[cc];
      for (int i = 0; i < cc; i++) {
         r[i] = ((ParameterNode) getChild(i)).computeXType(ni, uiAgent, smCompProvider);
      }
      return r;
   }

   private void contentChanged() {
      getTree().contentChanged();
   }

   ParameterEditorTree getTree() {
      return mEditor.getParameterTree();
   }

   /**
    * Does a deep copy of the node.
    *
    * @return a new node.
    */
   public ParameterNode copy() {
      ParameterNode pn = new ParameterNode(getEditor());
      pn.m_name = getName();
      pn.mCategory = getContentModelCategory();
      pn.mContentModelDetails = getContentModelDetails();
      pn.m_typeCategory = getTypeCategory();
      pn.mMin = getMin();
      pn.mMax = getMax();
      pn.mDefaultVal = getDefaultValue();
      for (int i = 0; i < getChildCount(); i++) {
         ParameterNode c = getChildParameter(i);
         ParameterNode cc = c.copy();
         pn.addedAt(i, cc);
      }
      return pn;
   }

   public String toString() {
      return "ParameterNode: " + m_name;
   }
}
