package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;

import com.tibco.cep.mapper.xml.schema.xtype.VarRefSmSequenceTypeSource;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.xquery.XQuery;

/**
 * A tree node in the type-tree representing a function.
 */
public class FunctionDefinitionTreeNode extends DataTypeTreeNode {
   private final String mFunctionName;
   private final DataTypeTreeNode mForwardTo;
   private final String mName;

   /**
    * @param functionName The name of the function (not including parens).
    * @param ofVar        A node to which requests are forwarded.
    */
   public FunctionDefinitionTreeNode(String functionName, DataTypeTreeNode ofVar) {
      super(SmCardinality.EXACTLY_ONE);
      mFunctionName = functionName;
      mForwardTo = ofVar;
      mName = mFunctionName + "()";
   }

   public boolean isSubstituted() {
      return mForwardTo.isSubstituted();
   }

   public Icon getIcon() {
      return DataIcons.getVariableIcon();
   }

   public boolean isEditable() {
      return false;
   }

   public SmSequenceType getAsXType() {
      SmSequenceType t = mForwardTo.getAsXType();
      if (t == null) {
         t = SMDT.PREVIOUS_ERROR;
      }
      ExpandedName exName = ExpandedName.makeName(XQuery.FUNCTIONS_NAMESPACE, mName);
      return t.createWithContext(t.getContext().createWithSource(
              new VarRefSmSequenceTypeSource(exName)));
   }

   public boolean hasChildContent() {
      return ((DataTree) getTree()).nodeHasChildContent(this);
   }

   public String getName() {
      return mName;
   }

   public BasicTreeNode[] buildChildren() {
      return mForwardTo.buildChildren();
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      return mName;
   }

   public Object getIdentityTerm() {
      return null;
   }

   public boolean isLeaf() {
      return mForwardTo.isLeaf();
   }
}
