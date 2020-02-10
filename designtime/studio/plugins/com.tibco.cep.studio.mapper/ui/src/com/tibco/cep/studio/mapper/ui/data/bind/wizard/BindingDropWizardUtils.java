package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingNode;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingTree;

/**
 * Move this elsewhere, utils for the GUI drop only.
 */
class BindingDropWizardUtils {
   /*
   public static void updateNestedFormula(BindingReport changedParent, ExprContext oldContext, String newParentFormula, List changed) {
       updateNestedChildren(changedParent, oldContext, changedParent.getChildContext(), newParentFormula, changed);
   }*/

   /*
   public static Binding insertStatement(Binding beforeBinding) {
       ForEachBinding feb = null;//@@new ForEachBinding(beforeBinding.getSpecifiedOutputType());
       Binding parent = beforeBinding.getParent();
       int index = getIndexOfChild(parent, beforeBinding);
       parent.setChildAt(index, feb);
       feb.addChild(beforeBinding);
       return feb;
   }*/

   /*
   public static String getParentFormula(Binding beforeBinding, String formula, TransformResolvers transformResolvers) {
       Binding[] path = buildPath(beforeBinding);

       // Otherwise, things get interesting.
       int max = path.length;
       StylesheetBinding sb = beforeBinding.getContainingStylesheetBinding();

       BindingReport[] reportTree = getReportPathFor(transformResolvers, path);
       BindingReport initialReport = reportTree[reportTree.length - 1];

       String modFormula = computeDropFormula(initialReport, formula);
       if (modFormula != null) {
           String[] pieces = Utilities.getAsArray(modFormula);
           XType actualOutputType = null;//@@initialReport.getBinding().getActualOutputType();
           if (pieces != null) {
               // Pass 1, match repeating:
               for (int i = pieces.length - 1; i >= 1; i--) {
                   String ppath = Utilities.fromArray(pieces, i);
                   Expr e = new Parser(Lexer.lex(ppath)).getExpression();
                   ExprContext context = initialReport.getContext();
                   EvalTypeInfo info = new EvalTypeInfo();
                   XType xtype = e.evalType(context, info);
                   if (xtype.getMaxOccurs() > 1 && actualOutputType.getMaxOccurs() > 1) {
                       // Both are repeating, guess that they match up (as a suggestion)
                       return computeDropFormula(initialReport, ppath);
                   }
               }
               // Pass 2, try optional:
               for (int i = pieces.length - 1; i >= 1; i--) {
                   String ppath = Utilities.fromArray(pieces, i);
                   Expr e = new Parser(Lexer.lex(ppath)).getExpression();
                   ExprContext context = initialReport.getContext();
                   EvalTypeInfo info = new EvalTypeInfo();
                   XType xtype = e.evalType(context, info);
                   if (xtype.getMinOccurs() == 0 && actualOutputType.getMaxOccurs() == 0) {
                       // Both are repeating, guess that they match up (as a suggestion)
                       return computeDropFormula(initialReport, ppath);
                   }
               }
               // Pass 3, try the first one:
               for (int i = pieces.length - 1; i >= 1; i--) {
                   String ppath = Utilities.fromArray(pieces, i);
                   return computeDropFormula(initialReport, ppath);
               }
           }
       }
       return null;
   }*/

//   private static int getIndexOfChild(Binding parent, Binding child) {
//      int cc = parent.getChildCount();
//      for (int i = 0; i < cc; i++) {
//         if (parent.getChild(i) == child) {
//            return i;
//         }
//      }
//      return -1;
//   }

   /*
   private static void updateNested(BindingReport report, ExprContext oldContext, ExprContext newContext, String newParentFormula, List changed) {
       Binding instructionBinding = report.getBinding();
       boolean hadFormula = instructionBinding.getFormula() != null;
       if (hadFormula) {
           String updatedFormula = updateFormula(oldContext, newContext, instructionBinding.getFormula(), newParentFormula);
           if (!updatedFormula.equals(instructionBinding.getFormula())) {
               instructionBinding.setFormula(updatedFormula);
               changed.add(report.getBinding());
           }
       }
       if (report.getBinding() instanceof TagGeneratingBinding && hadFormula) {
           // this is changed the context anyway, no need to go into children
           return;
       }
       updateNestedChildren(report, oldContext, newContext, newParentFormula, changed);
   }

   private static void updateNestedChildren(BindingReport report, ExprContext oldPrefix, ExprContext newPrefix, String newParentFormula, List changed) {
       if (!report.getHasChildContent()) {
           // no child content, stop updating:
           return;
       }
       // update all children.
       BindingReport[] children = report.getChildren();
       for (int i = 0; i < children.length; i++) {
           updateNested(children[i], oldPrefix, newPrefix, newParentFormula, changed);
       }
   }*/

//   private static String updateFormula(ExprContext oldContext, ExprContext newContext, String formula, String newParentFormula) {
//      if (newParentFormula != null && formula.startsWith(newParentFormula)) { // shortcut that works some, but not all of the time.
//         // This is here to preserve filters which wouldn't otherwise be preserved (they'll be lost if they are inside a concat or something, for now...)
//         if (formula.equals(newParentFormula)) {
//            return ".";
//         }
//         if (formula.startsWith(newParentFormula + "/")) {
//            return formula.substring(newParentFormula.length() + 1);
//         }
//      }
//      String absFormula = null;//@@@Utilities.makeAbsoluteXPath(oldContext.getInput(), formula);
//      return Utilities.makeRelativeXPath(newContext, "", absFormula, null);
//   }

   /**
    * When filling in a filter for a parent structure, see if any of the children already has a filter filled-in,
    * if so, use it:
    */
//   private static String substituteActualFilters(ExprContext context, String formula, Binding at) {
//      int cc = at.getChildCount();
//      int filterStart = formula.indexOf("<<");
//      if (filterStart < 0) {
//         return formula;
//      }
//      int filterEnd = formula.indexOf(">>");
//      if (filterEnd < 0) {
//         return formula;
//      }
//      String filterMatch = formula.substring(0, filterStart);
//      String filterAfter = formula.substring(filterEnd + 2);
//      for (int i = 0; i < cc; i++) {
//         Binding c = at.getChild(i);
//         /*if (!(c instanceof TagGeneratingBinding)) {
//             // too complicated, just skip these.
//             continue;
//         }*/
//         String cformula = c.getFormula();
//         if (cformula == null) {
//            continue;
//         }
//         // just do a quick string compare, nothing fancy, this isn't a big deal feature:
//         if (cformula.startsWith(filterMatch)) {
//            // ok we matched, now extract predicate:
//            String predform = extractFirstFilter(cformula);
//            return filterMatch + predform + filterAfter;
//         }
//      }
//      return formula;
//   }

//   private static String extractFirstFilter(String formula) {
//      Expr e = new Parser(Lexer.lex(formula)).getExpression();
//      return extractFirstFilter(e);
//   }

//   private static String extractFirstFilter(Expr expr) {
//      if (expr.getExprTypeCode() == ExprTypeCode.EXPR_PREDICATE) {
//         String extraWhite = expr.getRepresentationClosure();
//         return extraWhite + expr.getChildren()[1].toExactString();
//      }
//      Expr[] e = expr.getChildren();
//      for (int i = 0; i < e.length; i++) {
//         String g = extractFirstFilter(e[i]);
//         if (g != null) {
//            return g;
//         }
//      }
//      return null;
//   }

   /*private static BindingReport getChildFor(BindingReport report, Binding child) {
       for (int i = 0; i < report.getChildCount(); i++) {
           if (report.getChild(i).getBinding() == child) {
               return report.getChild(i);
           }
       }
       return null;
   }

   private static BindingReport[] getReportPathFor(TransformResolvers resolvers, Binding[] bindingTree) {
       StylesheetBinding stylesheet = bindingTree[0].getContainingStylesheetBinding();
       BindingReport report = null;//@@@BindingReport.create(stylesheet, resolvers);
       //report = report.getChild(0); // step through the TemplateBinding (ugly...)
       ArrayList temp = new ArrayList();
       temp.add(report); // the root.
       for (int i = 1; i < bindingTree.length; i++) {
           Binding b = bindingTree[i];
           BindingReport c = getChildFor(report, b);
           if (c == null) {
               throw new NullPointerException("Null report, on ");// + XTypeSupport.getDisplayName(report.getBinding().getSpecifiedOutputType()) + " child " + XTypeSupport.getDisplayName(b.getSpecifiedOutputType()));
           }
           temp.add(c);
           report = c;
       }
       BindingReport[] reportTree = (BindingReport[]) temp.toArray(new BindingReport[temp.size()]);
       return reportTree;
   }*/

   /**
    * Returns changed bindings (excluding the root)
    */
   public static void drop(ExprContext exprContext, Binding onBinding, String xpath) {
      // If there are any markers, just do a simple insert:
      /*if (xpath!=null) {
          Token[] t = Lexer.lex(xpath);
          for (int i=0;i<t.length;i++) {
              if (t[i].getType()==Token.TYPE_MARKER_STRING) {
                  Binding last = bindingTree[bindingTree.length-1];
                  last.setFormula(xpath);
                  return new Binding[] {last};
              }
          }
      }*/
//      Binding[] path = buildPath(onBinding);

      // Otherwise, things get interesting.
//      int max = path.length;
      /*BindingReport initialReport = null;
      ArrayList changedList = new ArrayList();
      boolean mapFilters = false;

      BindingReport[] reportTree = getReportPathFor(transformResolvers, path);
      initialReport = reportTree[reportTree.length - 1];*/

      /*
          boolean updatedContext;
          if (xpath!=null) {
              String[] pieces = Utilities.getAsArray(xpath);
              if (pieces!=null && pieces.length>1 && reportTree.length>1) {
                  String remaining = Utilities.fromArray(pieces,pieces.length-1);
                  updatedContext = autoUpdateStructureMaps(reportTree,reportTree.length-2,remaining,mapFilters,changedList);
              } else {
                  updatedContext = false;
              }
          } else {
              updatedContext = false;
          }
          if (!updatedContext) {
              if (!mapFilters) {
                  mapFilters = true; // make another pass doing filters.
              } else {
                  break;
              }
          }
          if (max--==0) {
              // This shouldn't be needed, but if there's some sort of bug inside, the
              // last thing we want is an infinite loop.
              break;
          }
      }
      // set remaining (leaf).
      BindingReport[] reportTree = getReportPathFor(stylesheet,resolvers,bindingTree);
      String modFormula = computeDropFormula(reportTree[reportTree.length-1],xpath);
      String oldFormula = bindingTree[bindingTree.length-1].getFormula();
      bindingTree[bindingTree.length-1].setFormula(modFormula);

      BindingReport[] reportTreea = getReportPathFor(stylesheet,resolvers,bindingTree);
      updateNestedFormula(reportTreea[reportTreea.length-1],initialReport.getChildContext(),modFormula,changedList);

      return (Binding[]) changedList.toArray(new Binding[0]);*/
   }

   /**
    * Fixes the tree-expansion-state of the tree to reflect a parent node insertion.
    * (It preserves the old-child expansion state).
    * Note that this does, as a side effect, do a tree rebuild.
    *
    * @param tree         The binding tree.
    * @param originalNode The original node whose expansion state is to be preserved
    * @param insertedInto The new parent (or any ancestor) that the old-child is inserted into.
    * @param newOriginal  The new (copy) of the original node (assumes that the old-child is cloned before inserting)
    */
   public static void preserveTreeStateAfterInsertion(BindingTree tree, Binding originalNode, Binding insertedInto, Binding newOriginal) {
      BindingNode n = ((BindingNode) tree.getRootNode()).findForBinding(originalNode, true);
      if (n == null) {
         // just in case.
         return;
      }
      TreeState tstate = tree.getTreeStateForNode(n);
      tree.rebuild();
      // re-expand portion:
      BindingNode fobn = ((BindingNode) tree.getRootNode()).findForBinding(insertedInto, true);
      if (fobn == null) {
         // just in case.
         return;
      }
      tree.expandPath(fobn.getTreePath());
      BindingNode nn = ((BindingNode) tree.getRootNode()).findForBinding(newOriginal, true);
      if (nn == null) {
         // just in case.
         return;
      }
      tree.setTreeStateForNode(nn, tstate);

   }

//   private static Binding[] buildPath(Binding end) {
//      ArrayList<Binding> ar = new ArrayList<Binding>();
//      buildPath(ar, end);
//      return ar.toArray(new Binding[0]);
//   }

//   private static void buildPath(List<Binding> toList, Binding end) {
//      if (end != null) {
//         buildPath(toList, end.getParent());
//         toList.add(end);
//      }
//   }

   /*
   private static boolean autoUpdateStructureMaps(TemplateReport[] reportTree, int at, String xpath, boolean mapFilters, List changed) {
       if (at < 0) {
           return false;
       }
       BindingReport pr = reportTree[at];
       if (pr == null) {
           throw new NullPointerException("Null report");
       }
       // Once we hit an already filled in, context setting formula, stop.
       Binding binding = pr.getBinding();
       if (binding instanceof TagGeneratingBinding && binding.getFormula() != null) {
           return false;
       }
       String remainingPath = xpath;
       boolean updated = false;
       if (binding.getFormula() == null && pr.getBinding() instanceof TagGeneratingBinding) {
           String modFormula = computeDropFormula(pr, xpath);
           if (modFormula != null) {
               String[] pieces = Utilities.getAsArray(modFormula);
               boolean isStructureMapping = pr.getEXType().getMaxOccurs() > 1;
               if (pieces != null && (isStructureMapping || mapFilters)) {
                   for (int i = pieces.length; i >= 1; i--) {
                       String ppath = Utilities.fromArray(pieces, i);
                       Expr e = new Parser(Lexer.lex(ppath)).getExpression();
                       ExprContext context = pr.getContext();
                       EvalTypeInfo info = new EvalTypeInfo();
                       XType xtype = e.evalType(context, info);
                       if (xtype.getMaxOccurs() > 1) {
                           // guess that this is it.
                           String localModFormula = computeDropFormula(pr, ppath);
                           binding.setFormula(localModFormula);
                           changed.add(pr.getBinding());
                           updated = true;
                           ExprContext newContext = null;//@@@@@context.createWithInput(xtype.createWithCardinality(XOccurrence.EXACTLY_ONE));
                           pr.setHasChildContent(true); // hack, makes call below work:
                           updateNestedChildren(pr, context, newContext, localModFormula, changed);
                           if (i > 1) {
                               remainingPath = Utilities.fromArray(pieces, i - 1);
                           } else {
                               return true;
                           }
                           break;
                       }
                   }
               }
           }
       }
       if (autoUpdateStructureMaps(reportTree, at - 1, remainingPath, mapFilters, changed)) {
           return true;
       }
       return updated;
   }*/

   /*private static String computeDropFormula(BindingReport r, String path) {
       if (path == null) {
           // can be clearing it.
           return null;
       }
       ExprContext context = r.getContext();
       if (context == null) {
           System.out.println("Context is null on " + r.getBinding());
       }
       XType targetType = null;//@@@@r.getBinding().getActualOutputType();
       String rel = Utilities.makeRelativeXPath(context, "", path, targetType);
       if (rel == null) {
           return null;
       }
       return substituteActualFilters(context, rel, r.getBinding());
   }

   public static ExprContext computeExprContext(Binding onTo, TransformResolvers transformResolvers) {
       TemplateBinding tb = getContainingTemplate(onTo);
       BindingReport report = null;//@@@BindingReport.create(tb,transformResolvers);
       return getReportForBinding(report,onTo).getContext();
   }

   //@@@ Move this to BindingReport somewhere...
   private static BindingReport getReportForBinding(BindingReport report, Binding b) {
       if (report.getBinding()==b) {
           return report;
       }
       BindingReport report2 = getReportForBinding(report,b.getParent());
       for (int i=0;i<report2.getChildCount();i++) {
           if (report2.getChild(i).getBinding()==b) {
               return report2.getChild(i);
           }
       }
       return null;
   }*/

//   private static TemplateBinding getContainingTemplate(Binding b) {
//      if (b == null) {
//         return null;
//      }
//      if (b instanceof TemplateBinding) {
//         return (TemplateBinding) b;
//      }
//      return getContainingTemplate(b.getParent());
//   }
}

