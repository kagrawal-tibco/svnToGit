package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class ContextTypeTreeNode extends DataTypeTreeNode {
   private final DataTypeTreeNode mForwardTo;
   private final String mName;

   public ContextTypeTreeNode(DataTypeTreeNode ofVar) {
      super(SmCardinality.EXACTLY_ONE);
      mForwardTo = ofVar;
      mName = "(" + mForwardTo.getName() + ")";
   }

   public boolean isSubstituted() {
      return mForwardTo.isSubstituted();
   }

   public Icon getIcon() {
      return mForwardTo.getIcon();
   }

   public boolean isEditable() {
      return false;
   }

   public boolean hasChildContent() {
      return ((DataTree) getTree()).nodeHasChildContent(this);
   }

   public SmSequenceType getAsXType() {
      return null;
   }

   public String getName() {
      return mName;
   }

   public BasicTreeNode[] buildChildren() {
      return mForwardTo.buildChildren();
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      return isLastStep ? "." : null;
   }

   public Object getIdentityTerm() {
      return null;
   }

   public boolean isLeaf() {
      return mForwardTo.isLeaf();
   }
}
