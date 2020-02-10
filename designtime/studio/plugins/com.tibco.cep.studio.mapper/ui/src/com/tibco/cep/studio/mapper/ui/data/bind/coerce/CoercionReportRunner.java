package com.tibco.cep.studio.mapper.ui.data.bind.coerce;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.MutableTreeNode;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.xpath.Coercion;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionChoiceGroup;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeReportRunner;

/**
 * The report runner used by the coercion tree.
 */
class CoercionReportRunner implements EditableTreeReportRunner {
   private ExprContext m_startingContext;

   static class Report {
      private final ErrorMessage m_optionalError;
      private ArrayList<Report> m_children = new ArrayList<Report>();
      private ExprContext m_initialContext;

      public Report(ExprContext initialContext, ErrorMessage em) {
         m_initialContext = initialContext;
         m_optionalError = em;
      }

      public void addChild(Report r) {
         m_children.add(r);
      }

      public Report getChild(int index) {
         return m_children.get(index);
      }

      /**
       * Gets the context <b>before</b> this coercion is evaluated.
       *
       * @return The context, never null.
       */
      public ExprContext getInitialContext() {
         return m_initialContext;
      }

      /**
       * Gets the error associated with this coercion, or null if none.
       */
      public ErrorMessage getError() {
         return m_optionalError;
      }
   }

   public CoercionReportRunner(ExprContext context) {
      m_startingContext = context;
   }

   @SuppressWarnings("rawtypes")
public Object buildReport(Object rootNode, CancelChecker cancelChecker) {
      MutableTreeNode r = (MutableTreeNode) rootNode;
      Report[] reportArray = new Report[r.getChildCount()];
      ExprContext context = m_startingContext;

      if (r.getChildCount() == 1) {
         CoercionXPathList.Node c = (CoercionXPathList.Node) r.getChildAt(0);
         Coercion cc = c.createCoercion(context.getNamespaceMapper());

         // text range not applicable here.
         ErrorMessage optMsg = cc.checkApplyTo(context, new TextRange(0, 0));

         // Pass in initial context + error:
         reportArray[0] = new Report(context, optMsg);

         // Get the next context:
//do i need this?           context = cc.applyTo(context);
      }
      else if (r.getChildCount() > 1) {
         // Build a CoercionSet
         CoercionSet coercionSet = new CoercionSet();
         for (int i = 0; i < r.getChildCount(); i++) {
            CoercionXPathList.Node c = (CoercionXPathList.Node) r.getChildAt(i);
            Coercion cc = c.createCoercion(context.getNamespaceMapper());
            coercionSet.add(cc);
         }

         List coercionGroupList = CoercionSet.createCoercionChoices(context,
                                                                    coercionSet);
         for (int icnt = 0; icnt < coercionGroupList.size(); icnt++) {
            Coercion coercion = (Coercion) (coercionGroupList.get(icnt));

            if (coercion instanceof CoercionChoiceGroup) {
               List originalCoercions = ((CoercionChoiceGroup) coercion).getOriginalCoercions();

               // Note: this checkApplyTo will add ErrorMessages to report
               List groupErrorMessages =
                       ((CoercionChoiceGroup) coercion).checkGroupApplyTo(context,
                                                                          new TextRange(0, 0));

               for (int jcnt = 0; jcnt < groupErrorMessages.size(); jcnt++) {
                  ErrorMessage optMsg = (ErrorMessage) (groupErrorMessages.get(jcnt));
                  Coercion originalCoercion = (Coercion) (originalCoercions.get(jcnt));
                  int originalIndex = coercionSet.getCoercionList().indexOf(originalCoercion);
                  reportArray[originalIndex] = new Report(context, optMsg);
               }
            }
            else {
               // text range not applicable here.
               ErrorMessage optMsg = coercion.checkApplyTo(context, new TextRange(0, 0));
               int originalIndex = coercionSet.getCoercionList().indexOf(coercion);
               reportArray[originalIndex] = new Report(context, optMsg);

               // Get the next context:
//do i need this?                 context = coercion.applyTo(context);
            }
         }
      }
      // Add each report in the array as children of an initial report.
      Report report = new Report(m_startingContext, null);
      for (int icnt = 0; icnt < reportArray.length; icnt++) {
         report.addChild(reportArray[icnt]);
      }

      return report;
/*
       for (int i=0;i<r.getChildCount();i++)
       {
           CoercionXPathList.Node c = (CoercionXPathList.Node) r.getChildAt(i);
           Coercion cc = c.createCoercion(context.getNamespaceMapper());
           ErrorMessage optMsg = cc.checkApplyTo(context,new TextRange(0,0)); // text range not applicable here.

           // Pass in initial context + error:
           report.addChild(new Report(context,optMsg));

           // Get the next context:
           context = cc.applyTo(context);
       }
       return report;
*/
   }

   public void ensureReportExpanded(Object report) {
      // not applicable.
   }

   public Object getReport(Object node) {
      CoercionXPathList.Node n = (CoercionXPathList.Node) node;
      return n.getReport();
   }

   public Object getReportChild(Object report, int index) {
      Report r = (Report) report;
      return r.getChild(index);
   }

   public boolean getReportHasChildContent(Object report) {
      return true;
   }

   public boolean isUnexpanded(Object node) {
      return false;
   }

   public void setReport(Object node, Object report) {
      ((CoercionXPathList.Node) node).setReport((Report) report);
   }
}

