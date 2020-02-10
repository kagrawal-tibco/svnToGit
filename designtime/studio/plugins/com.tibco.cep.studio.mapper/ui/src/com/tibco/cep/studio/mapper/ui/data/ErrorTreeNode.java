package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Not a real node, for handling errors internally.
 */
public class ErrorTreeNode extends DataTypeTreeNode {
   private final String mMsg;

   public ErrorTreeNode(String msg) {
      super(SmCardinality.EXACTLY_ONE);
      mMsg = msg;
   }

   public SmSequenceType getAsXType() {
      return SMDT.PREVIOUS_ERROR;
   }

   public Icon getIcon() {
      return DataIcons.getIfIcon();
   }

   public String getName() {
      return "Error: " + mMsg;
   }

   private static final String ERROR_NODE_IDENTITY_TERM = "error-node-identity-term";

   public Object getIdentityTerm() {
      return ERROR_NODE_IDENTITY_TERM;
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
