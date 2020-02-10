package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public class DocumentRootTreeNode extends DataTypeTreeNode {
   private final SmSequenceType mDoc;
   private final String mName;
   private final Icon mIcon;
//   private final boolean mIsCoerced;

   public DocumentRootTreeNode(SmSequenceType type, SmCardinality occurrence, String coercedXPath) {
      super(occurrence);
      mDoc = type;
      mName = DataIcons.getName(type);
      mIcon = DataIcons.getIfIcon();//WCETODO replace this.
//      mIsCoerced = coercedXPath != null;
   }

   public boolean isSubstituted() {
      return false;
   }

   public SmSequenceType getAsXType() {
      return mDoc;
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

   public BasicTreeNode[] buildChildren() {
      return super.createChildNodesForXType(mDoc);
   }


   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      return "/";
   }

   public boolean isLeaf() {
      return false;//mIsLeaf;
   }

   public Object getIdentityTerm() {
      return mDoc;
   }
}
