package com.tibco.cep.studio.mapper.ui.data;

import java.util.ArrayList;

import javax.swing.Icon;

import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public class InterleaveTreeNode extends DataTypeTreeNode {
   private final SmSequenceType mSeq;
   private final String mName;
   private final Icon mIcon;
//   private final CoercionSet mCoercionSet;

   public InterleaveTreeNode(SmSequenceType seq, SmCardinality occurs, CoercionSet set) {
      super(occurs);
      mSeq = seq;
      mIcon = DataIcons.getAnonymousSequenceIcon(); //WCETODO need a new icon.
      mName = DataIcons.getName(seq);
//      mCoercionSet = set;
   }

   public boolean hasChildContent() {
      return ((DataTree) getTree()).nodeHasChildContent(this);
   }

   public Icon getIcon() {
      return mIcon;
   }

   public boolean isEditable() {
      return false;
   }

   public String getName() {
      return mName;
   }

   public SmSequenceType getAsXType() {
      return mSeq;
   }

   public BasicTreeNode[] buildChildren() {
      SmSequenceType[] seq = SmSequenceTypeSupport.getAllInterleaves(mSeq);
      ArrayList<DataTypeTreeNode> al = new ArrayList<DataTypeTreeNode>();
      for (int i = 0; i < seq.length; i++) {
         al.add(createNodeForXType(seq[i], SmCardinality.EXACTLY_ONE, null));
      }
      return al.toArray(new BasicTreeNode[0]);
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      return null;
   }

   public boolean isLeaf() {
      return false;
   }

   public Object getIdentityTerm() {
      return mSeq;
   }
}
