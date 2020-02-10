package com.tibco.cep.studio.mapper.ui.data;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;

public class DataTreeReport {
   private DataTreeReport[] mChildren;
//   private BasicTreeNode mNode;

   private String mLineError;
   private ErrorMessageList mErrors;

   public DataTreeReport(BasicTreeNode node, String msg, ErrorMessageList eml, DataTreeReport[] children) {
      mLineError = msg;
      mChildren = children;
      mErrors = eml;
//      mNode = node;
   }

   public ErrorMessageList getErrorMessages() {
      return mErrors;
   }

   public String getLineError() {
      return mLineError;
   }

   public DataTreeReport getChild(int at) {
      try {
         return mChildren[at];
      }
      catch (RuntimeException re) {
//            System.out.println("Looking for " + at + " on " + mNode.getName() + " cc " + mNode.getChildCount());
//            throw re;
         return null;
      }
   }

   public int getChildCount() {
      return mChildren.length;
   }
}
