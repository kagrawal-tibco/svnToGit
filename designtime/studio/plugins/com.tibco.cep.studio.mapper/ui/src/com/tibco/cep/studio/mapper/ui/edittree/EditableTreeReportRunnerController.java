package com.tibco.cep.studio.mapper.ui.edittree;

import javax.swing.tree.TreeModel;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;

/**
 * A helper class to {@link EditableTree} which runs reports (if enabled)
 */
final class EditableTreeReportRunnerController {
   private Object mReport;
   private int mCount;
   private EditableTreeReportRunner m_runner;
   private TreeModel m_model;

   protected EditableTreeReportRunnerController() {
   }

   public void setRunner(EditableTreeReportRunner runner) {
      m_runner = runner;
   }

   public void setModel(TreeModel model) {
      m_model = model;
   }

   public final int getReportSequenceNumber() {
      return mCount;
   }

   public final void markDirty() {
      if (mReport != null) {
         mReport = null;
         if (m_model != null) {
            clearReports(m_model.getRoot());
         }
      }
   }

   public final Object getReport(CancelChecker cancelChecker) {
      synchronized (this) {
         if (mReport == null) {
            if (m_model != null) {
               clearReports(m_model.getRoot());
            }
            if (cancelChecker.hasBeenCancelled()) {
               return null;
            }
            if (m_runner == null) {
               return null;
            }
            Object report;
            if (m_model != null) // can be null at startup.
            {
               report = m_runner.buildReport(m_model.getRoot(), cancelChecker);
            }
            else {
               report = null;
            }
            if (cancelChecker.hasBeenCancelled()) {
               return null;
            }
            mReport = report;
            if (m_model != null) {
               setReports(m_model.getRoot(), mReport, cancelChecker);
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

   private final void clearReports(Object node) {
      synchronized (this) {
         if (m_runner == null) {
            return;
         }
         m_runner.setReport(node, null);
         if (m_runner.isUnexpanded(node)) {
            return;
         }
         int ct = m_model.getChildCount(node);
         for (int i = 0; i < ct; i++) {
            clearReports(m_model.getChild(node, i));
         }
      }
   }

   private final void setReports(Object node, Object report, CancelChecker cancelChecker) {
      synchronized (this) {
         m_runner.setReport(node, report);
         if (cancelChecker.hasBeenCancelled()) {
            return;
         }
         if (m_runner.getReportHasChildContent(report) && !m_runner.isUnexpanded(node)) {
            int ct = m_model.getChildCount(node);
            for (int i = 0; i < ct; i++) {
               Object rep;
               if (report == null) {
                  rep = null;
               }
               else {
                  try {
                     rep = m_runner.getReportChild(report, i);
                  }
                  catch (Throwable t) {
                     // Shouldn't happen.
                     System.out.println("Failed to set report: parent node is " + node + " cc is " + m_model.getChildCount(node) + " idx is " + i);
                     rep = null;
                  }
               }
               setReports(m_model.getChild(node, i), rep, cancelChecker);
            }
         }
      }
   }

   void fillInChildReports(Object at) {
      if (m_runner == null) {
         return;
      }
      Object rep = m_runner.getReport(at);
      if (rep != null) {
         m_runner.ensureReportExpanded(rep);
         int cc = m_model.getChildCount(at);
         for (int i = 0; i < cc; i++) {
            // Fill in child reports where required:
            Object ch = m_model.getChild(at, i);
            if (m_runner.getReport(ch) == null) {
               Object rc = m_runner.getReportChild(rep, i);
               m_runner.setReport(ch, rc);
            }
         }
      }
   }
}

