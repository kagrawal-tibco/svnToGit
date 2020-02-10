package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A node representing 'empty' (i.e. nothing).
 */
public class EmptyTreeNode extends DataTypeTreeNode {
   public EmptyTreeNode() {
      super(SmCardinality.EXACTLY_ONE);
   }

   public SmSequenceType getAsXType() {
      return SMDT.VOID;
   }

   public Icon getIcon() {
      return DataIcons.getBlankIcon();
   }

   public String getName() {
      return DataIcons.getEmptyLabel();
   }

   private static final String EMPTY_NODE_IDENTITY_TERM = "empty-node-identity-term";

   public Object getIdentityTerm() {
      return EMPTY_NODE_IDENTITY_TERM;
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
}
