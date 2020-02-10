package com.tibco.cep.studio.mapper.ui.edittree;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;

/**
 * Defines how a report (analysis) can be run over the tree.<br>
 * The report is assumed to be a tree structure that mirrors the tree itself, each node gets decorated with the report (through this api).
 */
public interface EditableTreeReportRunner {
   /**
    * Builds the entire report hierarchy.
    *
    * @param rootNode      The root node.
    * @param cancelChecker A cancel checker that can either be ignored or polled to see if it should be aborted.
    * @return The report object.
    */
   public Object buildReport(Object rootNode, CancelChecker cancelChecker);

   public Object getReportChild(Object report, int index);

   /**
    * This will be called once per-node with the report (so the implementation does not need to recurse)
    *
    * @param node
    * @param report
    */
   public void setReport(Object node, Object report);

   public Object getReport(Object node);

   public boolean getReportHasChildContent(Object report);

   /**
    * Can be used to hint that this node is unexpanded & should not be traversed for clearing.<br>
    * Most applications can safely return false all the time, if there are types of nodes that can expand
    * recursively (where reports are expanded), then those need to return true.
    *
    * @param node
    * @return
    */
   public boolean isUnexpanded(Object node);


   /**
    * In some cases, a report may be expanded incrementally; this callback allows those to know
    * when to expand.<br>
    * If the report is always fully expanded or if this particular node is already expanded, this should do nothing.
    *
    * @param report The report that need to be expanded.
    */
   public void ensureReportExpanded(Object report);
}

