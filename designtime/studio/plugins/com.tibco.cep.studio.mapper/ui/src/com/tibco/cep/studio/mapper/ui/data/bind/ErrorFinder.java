package com.tibco.cep.studio.mapper.ui.data.bind;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportSupport;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeReportRunner;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmWildcard;

/**
 * Purposefully package private.
 */
class ErrorFinder implements EditableTreeReportRunner {
   private TemplateEditorConfiguration mConfiguration;
   private TemplateReportFormulaCache m_formulaCache;

   public ErrorFinder(TemplateReportFormulaCache fc) {
      //mTree = tree;
      m_formulaCache = fc;
   }

   public Object getReportChild(Object report, int index) {
      return ((TemplateReport) report).getChild(index);
   }

   public boolean getReportHasChildContent(Object report) {
      if (report == null) {
         return false;
      }
      return ((TemplateReport) report).hasChildren();
   }

   public void setTemplateEditorConfiguration(TemplateEditorConfiguration config) {
      mConfiguration = config;
      if (config != null && config.getExprContext().getInputSchemaProvider() == null) {
         throw new NullPointerException("Null schema provider");
      }
      if (config != null && config.getExprContext().getOutputSchemaProvider() == null) {
         throw new NullPointerException("Null output schema provider");
      }
      if (config != null && config.getStylesheetResolver() == null) {
         throw new NullPointerException("Null stylesheet resolver");
      }
   }

   public void setReport(Object node, Object report) {
      ((BindingNode) node).setReport((TemplateReport) report);
   }

   public Object getReport(Object node) {
      return ((BindingNode) node).getTemplateReport();
   }

   public TemplateEditorConfiguration getTemplateEditorConfiguration() {
      return mConfiguration;
   }

   public boolean isUnexpanded(Object node) {
      return !((BindingNode) node).hasBeenExpanded();
   }

   public Object buildReport(Object root, CancelChecker cancelChecker) {
      TemplateReport rootReport;
      if (mConfiguration == null) {
         rootReport = null;
      }
      else {
         try {
            TemplateReportArguments tra = new TemplateReportArguments();
            tra.setSkippingTemplateParams(true); // these aren't shown in the tree, so we don't want them in the report.
            tra.setCancelChecker(cancelChecker);
            //long ct = System.currentTimeMillis();
            rootReport = TemplateReport.create(mConfiguration, m_formulaCache, tra);
            //long ct2 = System.currentTimeMillis();
            //System.out.println("Report building took:" + (ct2-ct));
         }
         catch (RuntimeException e) {
            e.printStackTrace(System.out); // shouldn't happen.
            rootReport = null;
         }
      }

      final TemplateReport frootReport = rootReport;

      /*if (mInputData!=null && getSchemaProvider()!=null && rootReport!=null) {
          try {
              BindingRunner runner = new BindingRunner(mConfiguration,new SharedResourceProvider(mXmluiAgent),getFunctionResolver(),false); // false -> no fix.
              XData ret = runner.run(mInputData.getVariables());
              ((BindingTree)mTree).setResultData(ret); //hack.
          } catch (Exception e) {
              //System.out.println("Got exception " + e.getClass().getName());
              String msg = "<html>" + e.getMessage();
              if (e instanceof BindingException) {
                  BindingException be = (BindingException) e;
                  //System.out.println("Here with chained " + be.mChained);
                  if (be.mChained!=null) {
                      msg = msg + "<br>" + be.mChained.getMessage();
                  }
              }
              ((BindingTree)mTree).setResultError(msg);
              //e.printStackTrace();
          }
      }*/
      return frootReport;
   }

   public void ensureReportExpanded(Object report) {
      TemplateReport r = (TemplateReport) report;
      if (!r.getBinding().hasChildren()) {
         // not required.
         return;
      }
      if (r.getChildCount() > 0) {
         // already built...
         return;
      }
      // Must be a marker:
      SmSequenceType contentType = r.getChildOutputContext();
      TemplateReportSupport.traverseChildren(r,
                                             contentType,
                                             SmWildcard.STRICT,
                                             new TemplateReportFormulaCache(), // also won't matter here.
                                             new TemplateReportArguments() // won't matter here.
      );
   }
}

