package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

public class PrimitiveTypeTreeNode extends DataTypeTreeNode {
   private final SmSequenceType mPrimitiveType;
   private final String mName;
   private final Icon mIcon;

   public PrimitiveTypeTreeNode(SmSequenceType primitiveType, SmCardinality occurs) {
      super(occurs);
      mPrimitiveType = primitiveType;
      SmType t = primitiveType.getSimpleType();
      String name = t.getName();
      //if (primitiveType.isBoolean()) {
      mName = "(" + name + ")";
      mIcon = DataIcons.getIcon(t);
   }

   public SmSequenceType getAsXType() {
      return SmSequenceTypeFactory.createRepeats(mPrimitiveType, SmCardinality.create(getMin(), getMax()));
   }

   public Icon getIcon() {
      return mIcon;
   }

   public boolean isLeaf() {
      return true;
   }

   public BasicTreeNode[] buildChildren() {
      return new BasicTreeNode[0];
   }

   public boolean isEditable() {
      return false;
   }

   public String getName() {
      return mName;
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      return mName;
   }

   public Object getIdentityTerm() {
      return mPrimitiveType;
   }
}
