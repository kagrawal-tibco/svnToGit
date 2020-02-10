package com.tibco.cep.studio.mapper.ui.data.basic;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;

public abstract class BasicReportRunner {
   private Object mReport;
   private int mCount;
   BasicTreeNode mRoot;

   protected BasicReportRunner() {
   }

   public final int getReportSequenceNumber() {
      return mCount;
   }

   public final void markDirty() {
      if (mReport != null) {
         mReport = null;
         if (mRoot != null) {
            clearReports(mRoot);
         }
      }
   }

   public final Object getReport(CancelChecker cancelChecker) {
      synchronized (this) {
         if (mReport == null) {
            if (mRoot != null) {
               clearReports(mRoot);
            }
            if (cancelChecker.hasBeenCancelled()) {
               return null;
            }
            Object report = buildReport(cancelChecker);
            if (cancelChecker.hasBeenCancelled()) {
               return null;
            }
            mReport = report;
            if (mRoot != null) {
               setReports(mRoot, mReport, cancelChecker);
            }
            if (cancelChecker.hasBeenCancelled()) {
               mReport = null;
               return null;
            }
            // increment count.
            mCount++;
         }
         return mReport;
      }
   }

   private final void clearReports(BasicTreeNode node) {
      synchronized (this) {
         node.setReport(null);
         if (node.hasBeenExpanded()) {
            int ct = node.getChildCount();
            for (int i = 0; i < ct; i++) {
               clearReports(node.getChild(i));
            }
         }
      }
   }

   private final void setReports(BasicTreeNode node, Object report, CancelChecker cancelChecker) {
      synchronized (this) {
         node.setReport(report);
         if (cancelChecker.hasBeenCancelled()) {
            return;
         }
         if (getReportHasChildContent(report) && node.hasBeenExpanded()) {
            int ct = node.getChildCount();
            for (int i = 0; i < ct; i++) {
               Object rep;
               if (report == null) {
                  rep = null;
               }
               else {
                  try {
                     rep = getReportChild(report, i);
                  }
                  catch (Throwable t) {
                     // Shouldn't happen.
                     System.out.println("Failed to set report: parent node is " + node + " cc is " + node.getChildCount() + " idx is " + i);
                     rep = null;
                  }
               }
               setReports(node.getChild(i), rep, cancelChecker);
            }
         }
      }
   }

   void fillInChildReports(BasicTreeNode at) {
      Object rep = at.getReport();
      if (rep != null) {
         ensureReportExpanded(rep);
         int cc = at.getChildCount();
         for (int i = 0; i < cc; i++) {
            // Fill in child reports where required:
            if (at.getChild(i).getReport() == null) {
               Object rc = getReportChild(rep, i);
               at.getChild(i).setReport(rc);
            }
         }
      }
   }

   protected abstract Object buildReport(CancelChecker cancelChecker);

   protected abstract Object getReportChild(Object report, int index);

   protected abstract boolean getReportHasChildContent(Object report);

   /**
    * In some cases, a report may be expanded incrementally; this callback allows those to know
    * when to expand.<br>
    * If the report is always fully expanded or if this particular node is already expanded, this should do nothing.
    *
    * @param report The report that need to be expanded.
    */
   protected abstract void ensureReportExpanded(Object report);
}

