package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.util.ArrayList;
import java.util.Iterator;

import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.func.InvokeJavaXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XFunction;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionCategory;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionEntry;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionExample;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Works with XPathBuilder, builds {@link XPathBuilderCategory} for custom function namespaces.
 */
public class XPathBuilderCustomFunctions {
   @SuppressWarnings("rawtypes")
public static FormulaFunctionCategory makeCategory(FunctionNamespace namespace, String comment) {
      ArrayList<FormulaFunctionEntry> entries = new ArrayList<FormulaFunctionEntry>();
      Iterator fns = namespace.getFunctions();

      while (fns.hasNext()) {
         XFunction function = (XFunction) fns.next();
         entries.add(createBuilderEntry(namespace, function));
      }
      if (namespace.getLoadErrorMessage() != null) {
         comment = comment + "<br/>" + namespace.getLoadErrorMessage();
      }
      FormulaFunctionEntry[] entriesa = entries.toArray(new FormulaFunctionEntry[0]);
      FormulaFunctionCategory cat = new FormulaFunctionCategory(namespace.getSuggestedPrefix(), namespace.getSuggestedPrefix(), entriesa, null, comment);
      return cat;
   }

   /**
    * Creates an XPathBuilderEntry (for custom functions only??)
    */
   private static FormulaFunctionEntry createBuilderEntry(FunctionNamespace namespace, XFunction function) {
      FormulaFunctionEntry entry;
      if (function instanceof InvokeJavaXFunction) {
         entry = createBuilderEntryFromJavaXFunction(namespace, (InvokeJavaXFunction) function);
      }
      else {
         String fname = function.getName().getLocalName();
         entry = new FormulaFunctionEntry(fname, fname, namespace.getNamespaceURI(), null, false, createHelpText(function), null, null);
      }
      return entry;
   }

   /**
    * Searches through the array of help strings to find the match!
    */
   private static FormulaFunctionEntry createBuilderEntryFromJavaXFunction(FunctionNamespace namespace, InvokeJavaXFunction function) {
      String[] helpStrings = function.getHelpStrings();
      if (helpStrings != null) {
         return generateBuilderEntry(namespace.getSuggestedPrefix(), function, helpStrings);
      }
      else {
         FormulaFunctionEntry entry = new FormulaFunctionEntry(function.getName().getLocalName(),
                                                               function.getName().getLocalName(),
                                                               function.getName().getNamespaceURI(),
                                                               function.generateDragString(namespace.getSuggestedPrefix()),
                                                               false,
                                                               createHelpText(function),
                                                               new FormulaFunctionExample[0],
                                                               new String[0]);
         return entry;
      }
   }

   private static FormulaFunctionEntry generateBuilderEntry(String prefix, InvokeJavaXFunction function, String[] helpArray) {
      FormulaFunctionExample[] examples;
      if (helpArray.length > 2) {
         int numberOfExamples = ((helpArray.length - 2) / 2) + (helpArray.length % 2);
         examples = new FormulaFunctionExample[numberOfExamples];
         int length = helpArray.length;
         if (length % 2 == 1) {
            length--;
         }
         for (int i = 2, j = 0; i < length; i++, j++) {
            String input = helpArray[i];
            String result = helpArray[i + 1];
            ;
            examples[j] = new FormulaFunctionExample(input, result);
            i++;
         }
         if (helpArray.length % 2 == 1) {
            examples[numberOfExamples - 1] = new FormulaFunctionExample(helpArray[length]);
         }
      }
      else {
         examples = new FormulaFunctionExample[0];
      }

      FormulaFunctionEntry entry = new FormulaFunctionEntry(helpArray[0],
                                                            helpArray[0],
                                                            function.getName().getNamespaceURI(),
                                                            function.generateDragString(prefix),
                                                            false,
                                                            helpArray[1],
                                                            examples,
                                                            new String[0]);

      return entry;
   }

   /**
    * Returns an HTML string which contains the signature of the function.
    */
   public static String createHelpText(XFunction function) {
      String localName = function.getName().getLocalName();
      StringBuffer buff = new StringBuffer();

      SmSequenceType type = function.getReturnType();
      buff.append(DataIcons.getName(type));
      buff.append(" ");
      buff.append(localName);
      buff.append("(");

      int numArgs = function.getNumArgs();
      if (numArgs > 0) {
         if (numArgs == 1) {
            type = function.getArgType(0, numArgs);
            buff.append(DataIcons.getName(type));
         }
         else {
            for (int i = 0; i < numArgs - 1; i++) {
               type = function.getArgType(i, numArgs);
               buff.append(DataIcons.getName(type));
               buff.append(", ");
            }
            type = function.getArgType(numArgs - 1, numArgs);
            buff.append(DataIcons.getName(type));
         }
      }
      buff.append(")");
      return buff.toString();
   }
}
