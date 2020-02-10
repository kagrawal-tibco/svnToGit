package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.SwingUtilities;

import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportErrorFormatter;
import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.edittree.simple.SimpleTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Purposefully package private. Not yet documented.
 */
public final class BindingNode extends SimpleTreeNode {
   private final Binding mBinding;
   private TemplateReport mCachedReport; // built in separate thread & set here.
   private Icon mIcon;
   private boolean mEditable;
   private boolean mAllowsEmptyFormula;
   private int mMin;
   private int mMax;
   private boolean m_isNil;
   private boolean mIsDisabled;
   private boolean mIsLeaf;
   private boolean m_isSubstituted;
   private boolean m_isMarker;
   private ErrorMessageList mErrors;
   private int m_editableFieldType;
   private BindingTree m_tree;

   private String mDisplayName;

   private static final Color CONSTANT_FIELD_DATA_BACKGROUND_COLOR = new Color(245, 245, 255);
   private static final Color COMMENT_FIELD_DATA_BACKGROUND_COLOR = new Color(245, 255, 245);
   private static final Color MARKER_BACKGROUND_COLOR = new Color(225, 255, 255);
   private static final Color DATA_MISSING_BACKGROUND_COLOR = new Color(240, 240, 240);

   public BindingNode(BindingTree tree, Binding binding) {
      m_tree = tree;
      if (binding == null) {
         throw new NullPointerException("Null binding passed in");
      }
      mBinding = binding;
      if (mBinding instanceof MarkerBinding) {
         //WCETODO setBackgroundColor(MARKER_BACKGROUND_COLOR);
      }
      else {
         // Because the binding lines rely on the nodes being expanded & the reports being there, we need to go ahead
         // & expand now (only expand those with children, though).
         if (binding.getChildCount() > 0) {
            getChildCount(); // force expansion.
         }
      }
   }

   public BindingTree getTree() {
      return m_tree;
   }

   public boolean isSubstituted() {
      return m_isSubstituted;
   }

   public boolean isMarker() {
      return m_isMarker;
   }

   public Color getBackgroundColor() {
      return m_isMarker ? MARKER_BACKGROUND_COLOR : null;
   }

   public TemplateReport getTemplateReport() {
      return mCachedReport;
   }

   public SmSequenceType getXType() {
      return mCachedReport.getExpectedType();
   }

   public boolean isLeaf() {
      // Say that anything w/ zero children (except for a marker, which is handled with mIsLeaf) is a leaf;
      // otherwise, winds up with stupid little '+' signs around leaves a lot.
      if (!m_isMarker && mBinding.getChildCount() == 0) {
         return true;
      }
      return mIsLeaf;
   }

   public boolean canHaveChildren() {
      // Need to re-label mIsLeaf, not exactly accurate.
      return !mIsLeaf;
   }

   public boolean isDisabled() {
      return mIsDisabled;
   }

   /**
    * Smart clears
    */
   public void clearContent() {
      if (mCachedReport == null) {
         return; // just in case.
      }
      if (BindUtilities.isContextChangingBinding(mBinding)) {
         // Safe remove of children.
//WCETODO redo            BindingDisplayUtils.deleteAndMoveOut((BindingTree)getTree(),mCachedReport);

         return;
      }
      // Otherwise, just clear.
      setDataValue("");
   }

   public void delete() {
      Binding p = mBinding.getParent();
      int ic = p.getIndexOfChild(mBinding);
      p.removeChild(ic);
   }

   /**
    * Deletes everything.
    */
   public void deleteContent() {
      getBinding().setFormula("");
      getBinding().removeAllChildren();
      super.childrenChanged();
   }

   public boolean hasChildContent() {
      // ugh... in the process of expanding, the reports won't get filled in.  Do a quick check:
      return hasChildContent(getBinding());
   }

   private static boolean hasChildContent(Binding b) {
      if (!b.hasChildren()) {
         return false;
      }
      Binding[] c = b.getChildren();
      for (int i = 0; i < c.length; i++) {
         Binding cc = c[i];
         if (cc.getFormula() != null) {
            return true;
         }
         if (hasChildContent(c[i])) {
            return true;
         }
      }
      return false;
   }

   /**
    * Finds the node associated with the given binding.
    *
    * @param b               The given binding
    * @param checkUnexpanded If it should traverse into unexpanded nodes.
    * @return The node for that binding, or null if not found.
    */
   public BindingNode findForBinding(Binding b, boolean checkUnexpanded) {
      ArrayList<Binding> temp = new ArrayList<Binding>();
      Binding at = b;
      while (at != null && at != mBinding) {
         temp.add(0, at);
         at = at.getParent();
      }
      Binding[] ar = temp.toArray(new Binding[0]);
      BindingNode atn = this;
      // Skips root:
      for (int i = 0; i < ar.length; i++) {
         Binding findNext = ar[i];
         if (!atn.hasBeenExpanded()) {
            if (!checkUnexpanded) {
               return null;
            }
         }
         int cc = atn.getChildCount();
         BindingNode next = null;
         for (int xi = 0; xi < cc; xi++) {
            if (((BindingNode) atn.getChild(xi)).mBinding == findNext) {
               next = (BindingNode) atn.getChild(xi);
               break;
            }
         }
         atn = next;
         if (atn == null) {
            return null;
         }
      }
      return atn;
   }

   /**
    * Equivalent to getBinding().getExpandedName().getLocalName()
    */
   public String getName() {
      return mBinding.getName().getLocalName();
   }

   public String getDisplayName() {
      return mDisplayName;
   }

/*    public String getDisplaySuffix() {
        return mDisplaySuffix;
    }*/

   public Binding getBinding() {
      return mBinding;
   }

   public Icon getIcon() {
      return mIcon;
   }

   public SimpleTreeNode[] buildChildren() {
      if (!mBinding.hasChildren()) {
         // mCachedReport can be null at startup...
         if (mCachedReport != null) {
            BindingManipulationUtils.expandBinding(mCachedReport);
         }
         else {
            if (mBinding instanceof MarkerBinding) {
               // In certain (startup) cases, can wind up with null reports (as we expand previously expanded markers),
               // just plow out here.
               BindingManipulationUtils.expandBinding((MarkerBinding) mBinding);
            }
            // (Otherwise, it's not a marker, so there's no auto-expansion)
            // (NOTE: This code DOES get called in the absense of reports, so it must be handleable.!)
         }
      }
      int sz = mBinding.getChildCount();
      ArrayList<BindingNode> ret = new ArrayList<BindingNode>();
      int realStart = BindingManipulationUtils.getTemplateFirstNonParameter(mBinding);
      for (int i = realStart; i < sz; i++) {
         Binding b = mBinding.getChild(i);
         ret.add(new BindingNode(m_tree, b));
      }
      return ret.toArray(new BindingNode[ret.size()]);
   }

   public String toString() {
      return "BindingNode " + getName();
   }

   /**
    * If the user can type a formula on this binding.
    */
   public boolean isEditable() {
      return mEditable;
   }

   public boolean isDialogEditable() {
      return isEditable() || mBinding instanceof MarkerBinding;
   }

   /**
    * Too many of these...
    */
   public final Binding[] getBindingArray() {
      ArrayList<Binding> temp = new ArrayList<Binding>();
      getBindingArray(temp);
      return temp.toArray(new Binding[temp.size()]);
   }

   public boolean remove(int index) {
      mBinding.removeChild(index);
      return true;
   }

   public boolean canMoveUp() {
      if (getParent() == null) {
         return false;
      }
      return !(((BindingNode) getParent()).getBinding() instanceof CommentBinding);
   }

   public boolean canMoveDown() {
      return canMoveUp();
   }

   public boolean canMoveOut() {
      return canMoveUp();
   }

   /**
    * Asks if this node can move in a level (if it makes sense).
    */
   public boolean canMoveIn() {
      if (getParent() == null) {
         // the root here can't be moved around.
         return false;
      }
      // ... but otherwise, yes, provided we're not inside a comment.
      return !(((BindingNode) getParent()).getBinding() instanceof CommentBinding);
   }

   public boolean addAt(int index, SimpleTreeNode value) {
      Binding b = ((BindingNode) value).getBinding();

      mBinding.addChild(index, b);

      // hacky --- can't think of a better way to do this, though.
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            Binding nb = BindingDisplayUtils.instantiateAnyAncestorOrSelfMarkers(mBinding);
            if (nb != null && nb != mBinding) {
               m_tree.rebuild();
            }
         }
      });

      return true;
   }

   private final void getBindingArray(ArrayList<Binding> temp) {
      if (getParent() != null) {
         ((BindingNode) getParent()).getBindingArray(temp);
      }
      temp.add(mBinding);
   }

   public final void setReport(TemplateReport r) {
      mCachedReport = r;
      if (mCachedReport != null) {
         if (mCachedReport.getBinding() != mBinding) {
            RuntimeException re = new RuntimeException();
            System.err.println("Report mismatch on " + mBinding.getName());
            System.err.println("Report mismatch node name: " + mCachedReport.getBinding().getName());
            re.printStackTrace(System.err);
            mCachedReport = null;
         }
      }
      try {
         computeNameAndIcons();
      }
      catch (Throwable t) {
         // Shouldn't happen, but if it does, don't freak out too much:
         t.printStackTrace(System.err);
      }
   }

   private void computeNameAndIcons() {
      StatementPanel sd = m_tree.getStatementPanelManager().getStatementPanelFor(mBinding);
      if (mCachedReport == null) {
         mDisplayName = "<no report>";
         mIcon = DataIcons.getErrorIcon();
         mMin = 0;
         mMax = 0;
         m_editableFieldType = StatementPanel.FIELD_TYPE_NOT_EDITABLE;
         mEditable = false;
         mIsLeaf = false;
         m_isMarker = false;
         mAllowsEmptyFormula = false;
         mIsDisabled = true;
         mErrors = null;
         m_isSubstituted = false;
         m_isNil = false;
      }
      else {
         mDisplayName = sd.getDisplayNameFor(mCachedReport);
         mIcon = sd.getDisplayIcon(mCachedReport);
         SmCardinality xo = sd.getOccurrence(mCachedReport);
         if (xo == null) {
            // This is an error; instead of crashing, just muddle on, though:
            System.err.println("Report occurrence null on " + sd.getClass().getName());
            xo = SmCardinality.EXACTLY_ONE;
         }
         mMin = xo.getMinOccurs();
         mMax = xo.getMaxOccurs();
         m_isNil = sd.getIsNilled(mCachedReport);
         m_isMarker = mBinding instanceof MarkerBinding;

         m_editableFieldType = sd.getEditableFieldType(mCachedReport);
         mIsLeaf = sd.isLeaf(mCachedReport) && mBinding.getChildCount() == 0;

         // For elements/attributes with simple types where there is no formula or constant, make the field
         // 'gray' editable, just like for markers, this makes it easier to 'jump-start' a formula.
         SmSequenceType ct = mCachedReport.getComputedType();
         boolean hasTypedValueAndNotAny = ct == null ? false : (SmSequenceTypeSupport.hasTypedValue(ct, true) && !SmSequenceTypeSupport.isAny(ct));
         boolean hasTypedValueWithoutFormula = m_editableFieldType == StatementPanel.FIELD_TYPE_NOT_EDITABLE && mBinding instanceof VirtualDataComponentBinding && hasTypedValueAndNotAny;
         boolean isNilled = ((mBinding instanceof VirtualElementBinding) && ((VirtualElementBinding) mBinding).isExplicitNil());
         if (hasTypedValueWithoutFormula && !isNilled) {
            m_editableFieldType = StatementPanel.FIELD_TYPE_XPATH;
         }

         mAllowsEmptyFormula = mBinding instanceof MarkerBinding || hasTypedValueWithoutFormula;
         mIsDisabled = mBinding instanceof MarkerBinding;
         mErrors = mCachedReport.getFormulaErrors();
         m_isSubstituted = computeSubstituted();

         mEditable = m_editableFieldType != StatementPanel.FIELD_TYPE_NOT_EDITABLE;
      }
   }

   private boolean computeSubstituted() {
      if (mCachedReport.getExpectedType() == null || mCachedReport.getComputedType() == null) {
         return false;
      }
      return !mCachedReport.getExpectedType().prime().equalsType(mCachedReport.getComputedType().prime());
   }

   public final ErrorMessageList getErrorMessages() {
      return mErrors;
   }

   public void setDataValue(String val) {
      if (mBinding instanceof MarkerBinding) {
         if (mCachedReport == null) {
            System.err.println("Null report!!");
            return;
         }
         if (val == null || val.length() == 0) {
            // do nothing.
            return;
         }
         //Binding topParentChanged = null;
         if (mCachedReport.getParent() != null) {
            BindingDisplayUtils.instantiateAnyAncestorOrSelfMarkers(mCachedReport.getParent().getBinding());
         }
         BindingManipulationUtils.convertMarkerCommentToFormula(mCachedReport, val);

         /* // Send notification:
         BindingNode topChangedNode;
         if (topParentChanged!=null) {
             BindingNode rn = (BindingNode) getTree().getRootNode();
             topChangedNode = rn.findForBinding(topParentChanged,false);
         } else {
             // fire notification:
             topChangedNode = (BindingNode) getParent();
         }
         TreeState beforeState = getTree().getTreeState(); // keep everything expanded the same:
         topChangedNode.childrenChanged();
         getTree().setTreeState(beforeState);*/
         // (Instead of sending individual notification, above, which failed in some cases, just rebuild --- which is
         // less error prone & actually not too slow)

         // Required o.w. won't have a new tree.
         getTree().rebuild();
      }
      // Don't allow null anywhere, just an empty string:
      if (val == null) {
         val = "";
      }
      //WCETODO fix this hack; make statement panels deal w/ it maybe:
      if (mBinding instanceof CommentBinding) {
         ((CommentBinding) mBinding).setComment(val);
      }
      else {
         if (mBinding instanceof VirtualDataComponentBinding) {
            // For the case where this was a leaf-without-formula (see computeNamesAndIcons code), we can
            // have a non-formula, go ahead & make it an xpath:
            VirtualDataComponentBinding vdc = (VirtualDataComponentBinding) mBinding;
            if (!vdc.getHasInlineFormula() && val.length() > 0) {
               vdc.setHasInlineFormula(true);
               vdc.setInlineIsText(false);
            }
         }
         // kind of hacky, but users have complained about this; want clearing a formula to delete attr/element (in the same way that it used to...)
         if (val.length() == 0 && canBeCleared(mCachedReport)) {
            // (If the new formula is empty & it was an inline-xpath where we know the expected type):

            // Replace with marker:
            MarkerBinding mb = new MarkerBinding(mCachedReport.getExpectedType());

            BindingManipulationUtils.replaceInParent(mBinding, mb);

            // Send notification:
            TreeState beforeState = getTree().getTreeState(); // keep everything expanded the same:
            ((BindingNode) getParent()).childrenChanged();
            getTree().setTreeState(beforeState);
         }
         else {
            mBinding.setFormula(val);
         }
      }
      m_tree.markReportDirty();

      // Make sure dialog formula editor gets updated (if it is visible)
      m_tree.refreshModelessDialogsForSelection();
   }

   private static boolean canBeCleared(TemplateReport report) {
      Binding b = report.getBinding();
      if (!(b instanceof VirtualDataComponentBinding)) {
         return false;
      }
      if (BindingDisplayUtils.hasAnyNonMarkerChildren(b)) {
         // Had some real content, no clearing it!
         return false;
      }
      VirtualDataComponentBinding vdc = (VirtualDataComponentBinding) b;

      return vdc.getHasInlineFormula() && !vdc.getInlineIsText() && report.getExpectedType() != null;
   }

   /**
    * Override to show different colors for constants and comments.
    *
    * @return
    */
   public Color getDataBackgroundColor() {
      if (isDisabled()) {
         return DATA_MISSING_BACKGROUND_COLOR;
      }
      if (m_editableFieldType == StatementPanel.FIELD_TYPE_CONSTANT || m_editableFieldType == StatementPanel.FIELD_TYPE_ATTRIBUTE_VALUE_TEMPLATE) {
         return CONSTANT_FIELD_DATA_BACKGROUND_COLOR;
      }
      if (m_editableFieldType == StatementPanel.FIELD_TYPE_COMMENT) {
         return COMMENT_FIELD_DATA_BACKGROUND_COLOR;
      }
      // use default:
      return null;
   }

   public String getDataValue() {
      if (mAllowsEmptyFormula) {
         return null;
      }
      String f;
      //WCETODO fix this hack, have statement dialog do it:
      if (mBinding instanceof CommentBinding) {
         f = ((CommentBinding) mBinding).getComment();
      }
      else {
         f = mBinding.getFormula();
      }
      return f;
   }

   public String getBlankText() {
      return "";
   }

   public BindingNode createReplacementMarker() {
      if (mCachedReport == null) {
         return null;
      }
      SmSequenceType t = mCachedReport.getExpectedType();
      if (t == null || SmSequenceTypeSupport.isPreviousError(t)) {
         return null;
      }
      if (mBinding instanceof MarkerBinding) {
         // already is a marker...
         return null;
      }
      Binding mb = new MarkerBinding(t);
//      if (mb == null) {
//         return null;
//      }
      return new BindingNode(m_tree, mb);
   }

   /**
    * A formula can't be null, so treat no-text and missing formula as the same:
    */
   public final boolean allowsEmptyDataValue() {
      return mEditable && mAllowsEmptyFormula;
   }

   public boolean isErrorIcon() {
      if (mCachedReport == null) {
         return false;
      }
      return mCachedReport.getReferencedSchemaError() != null;
   }

   /**
    * Finds the error for this line (but not the formula).
    *
    * @return The error message.
    */
   public final String getLineError() {
      // Since we only show 1 thing at a time inline, search from most serious to least:
      if (mCachedReport == null) {
         return "<internal error: report not present>"; // Can only happen if there's a bug.
      }
      if (mCachedReport.isRecursivelyErrorFree()) {
         // No errors at all, by-by.
         return null;
      }
      if (mCachedReport.getStructuralError() != null) {
         // Indicates this node, at an XSLT element validation level (ignoring expected output, etc), is wrong,
         // so this is serious:
         return mCachedReport.getStructuralError();
      }
      if (mCachedReport.getFormulaErrors().getErrorAndWarningMessages().length > 0) {
         return BindingEditorResources.ERROR_FORMULA_HAS_ERRORS;
      }
      return TemplateReportErrorFormatter.formatErrorIgnoreFormula(mCachedReport, mDisplayName);
   }

   public String toXML() {
      m_tree.getBindingVirtualizer();
      Binding nb = BindingVirtualizer.normalize(mBinding, null);
      Binding bp = mBinding.getParent();
      if (nb == null) {
         return null;
      }
      if (bp != null) {
         // Get context namespace decls:
         NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(bp);
         NamespaceContextRegistry to = BindingNamespaceManipulationUtils.createNamespaceImporter(nb);
         NamespaceManipulationUtils.addAllNamespaceDeclarations(to, ni);
      }
      return nb.toString();
   }

   public int getMin() {
      return mMin;
   }

   public int getMax() {
      return mMax;
   }

   public boolean getNillable() {
      return m_isNil;
   }

   public BindingNode createArrayElement() {
      BindingTree tree = m_tree;
      Binding b = StatementDialog.showNewDialog(tree,
                                                tree.getImportRegistry(),
                                                tree.getUIAgent(),
                                                tree.getStatementPanelManager(),
                                                mCachedReport,
                                                false);
      if (b == null) {
         return null;
      }
      return new BindingNode(m_tree, b);
   }

   public boolean canDelete() {
      // anything goes... except stuff inside marker comments
      return canMoveUp();
   }

   /**
    * If true, the children of this node show up with array indexes.
    */
   public boolean isArray() {
      // hacky, fix --- the basic tree stuff assumes there's such a thing as an 'array'.  Here everything is an array.
      return !(getBinding() instanceof CommentBinding);
   }

   /**
    * Temporary function to keep compatibility... the binding tree is true on this, other stuff, no:
    */
   public boolean isLiteralTree() {
      return true;
   }

   /**
    * Gets the field type as defined from {@link StatementPanel#getEditableFieldType}.
    */
   public int getEditableFieldType() {
      return m_editableFieldType;
   }
}

