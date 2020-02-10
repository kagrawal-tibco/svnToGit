package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.xml.sax.InputSource;

import com.tibco.cep.mapper.xml.xdata.CharacterEntityMap;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionCategory;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionCategoryHandler;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionEntry;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionExample;

/**
 * Holders the XPath functions.
 */
public class XPathBuilderCategory {
   private static FormulaFunctionCategory[] FUNCTION_CATEGORIES;
   private static FormulaFunctionCategory[] CONSTANT_CATEGORIES;

   public static FormulaFunctionCategory[] getFunctionCategories(FunctionResolver fr) {
      if (FUNCTION_CATEGORIES == null) {
         InputStream is = null;
         try {
            is = XPathBuilderCategory.class.getResourceAsStream("resources/XPathFunctionDocumentation.xml");
            FUNCTION_CATEGORIES = FormulaFunctionCategory.parse(new InputSource(is), "ae.xpath.func");
         }
         catch (Throwable t) {
            t.printStackTrace(System.out);
            FUNCTION_CATEGORIES = new FormulaFunctionCategory[0];
         }
         finally {
            try {
               if (is != null) {
                  is.close();
               }
            }
            catch (Exception e) {
            }
         }
      }
      // Add custom functions:
      ArrayList<FormulaFunctionCategory> temp = new ArrayList<FormulaFunctionCategory>();
      for (int i = 0; i < FUNCTION_CATEGORIES.length; i++) {
         temp.add(FUNCTION_CATEGORIES[i]);
      }
      if (fr != null) {
         addCustomFunction(temp, fr);
      }

      return temp.toArray(new FormulaFunctionCategory[temp.size()]);
   }

   @SuppressWarnings("rawtypes")
   private static void addCustomFunction(ArrayList<FormulaFunctionCategory> temp, FunctionResolver fr) {
      Iterator iter = fr.getNamespaces();
      while (iter.hasNext()) {
         FunctionNamespace namespace = (FunctionNamespace) iter.next();
         if (!namespace.isBuiltIn()) {
            FormulaFunctionCategory cat = XPathBuilderCustomFunctions.makeCategory(namespace, "<html>Custom Function");
            temp.add(cat);
         }
      }
   }

   private static FormulaFunctionCategory makeCategory(CharacterEntityMap cem, String name, String comment) {
      FormulaFunctionEntry[] e = new FormulaFunctionEntry[cem.getRecordCount()];
      for (int i = 0; i < e.length; i++) {
         char c = cem.getRecord(i);
         String entity = cem.getEntityString(c);
         String ecomment = cem.getComment(c);
         String ename = c + " - " + entity;
         e[i] = new FormulaFunctionEntry(ename, ename, null, "" + c, true, ecomment, new FormulaFunctionExample[0], new String[0]);
      }
      return new FormulaFunctionCategory(name, name, e, "", comment);
   }

   static FormulaFunctionCategory[] getConstantCategories() {
      if (CONSTANT_CATEGORIES == null) {
         InputStream is = null;
         try {

            is = XPathBuilderCategory.class.getResourceAsStream("resources/SpecialCharacters.xmldata");
            ArrayList<FormulaFunctionCategory> cats = new ArrayList<FormulaFunctionCategory>();
            String resourcePrefix = "ae.xpath.constant";
            FormulaFunctionCategory[] category = FormulaFunctionCategory.parse(new InputSource(is), resourcePrefix);
            for (int i = 0; i < category.length; i++) {
               cats.add(category[i]);
            }
            CharacterEntityMap cem = new CharacterEntityMap();
            cem.addXHTMLLatin();
            cats.add(makeCategory(cem,
                                  FormulaFunctionCategoryHandler.getFuncResource(resourcePrefix, "latin.label"),
                                  FormulaFunctionCategoryHandler.getFuncResource(resourcePrefix, "latin.description.label")));
            cem = new CharacterEntityMap();
            cem.addXHTMLSpecial();
            cats.add(makeCategory(cem,
                                  FormulaFunctionCategoryHandler.getFuncResource(resourcePrefix, "special.label"),
                                  FormulaFunctionCategoryHandler.getFuncResource(resourcePrefix, "special.description.label")));

            cem = new CharacterEntityMap();
            cem.addXHTMLSymbol();
            cats.add(makeCategory(cem,
                                  FormulaFunctionCategoryHandler.getFuncResource(resourcePrefix, "symbol.label"),
                                  FormulaFunctionCategoryHandler.getFuncResource(resourcePrefix, "symbol.description.label")));


            CONSTANT_CATEGORIES = cats.toArray(new FormulaFunctionCategory[0]);
            FormulaFunctionCategory.resolveReferences(CONSTANT_CATEGORIES);
         }
         catch (Throwable t) {
            t.printStackTrace(System.out);
            CONSTANT_CATEGORIES = new FormulaFunctionCategory[0];
         }
         finally {
            try {
               if (is != null) {
                  is.close();
               }
            }
            catch (Exception e) {
            }
         }
      }
      return CONSTANT_CATEGORIES;
   }
}
