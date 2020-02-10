package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;

import com.tibco.cep.mapper.xml.schema.xtype.VarRefSmSequenceTypeSource;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

public class VariableDefinitionTreeNode extends DataTypeTreeNode {
   private final VariableDefinition mDefinition;
   private final DataTypeTreeNode mForwardTo;
   private final String mName;

   public VariableDefinitionTreeNode(VariableDefinition vd, DataTypeTreeNode ofVar) {
      super(vd.getType().quantifier());
      mDefinition = vd;
      mForwardTo = ofVar;
      mName = "$" + mDefinition.getName();
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
      ExpandedName exName = ExpandedName.makeName(null, mName);
      return t.createWithContext(t.getContext().createWithSource(
              new VarRefSmSequenceTypeSource(exName)));
   }

   public boolean hasChildContent() {
      return ((DataTree) getTree()).nodeHasChildContent(this);
   }

   public boolean isErrorIcon() {
      return false; //WCETODO make this error-able, too.
   }

   public String getName() {
      return mName;
   }

   public BasicTreeNode[] buildChildren() {
      return mForwardTo.buildChildren();
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      return mName;//"$" + getName();
   }

   public Object getIdentityTerm() {
      return null;
   }

   public boolean isLeaf() {
      return mForwardTo.isLeaf();
   }
}
