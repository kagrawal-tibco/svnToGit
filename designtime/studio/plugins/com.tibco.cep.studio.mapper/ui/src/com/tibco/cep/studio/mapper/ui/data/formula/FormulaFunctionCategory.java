package com.tibco.cep.studio.mapper.ui.data.formula;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

/**
 * Hold descriptions of functions for use in {@link FormulaFunctionPanel}.
 */
public class FormulaFunctionCategory {
   private final String mKey;
   private final String mName;
   private final FormulaFunctionEntry[] mEntries;
   private final FormulaFunctionEntry mCategoryEntry;

   public FormulaFunctionCategory(String key, String name, FormulaFunctionEntry[] entries, String dragString, String description) {
      mKey = key;
      mName = name;
      mEntries = entries;
      mCategoryEntry = new FormulaFunctionEntry(key, mName, null, dragString, false, description, new FormulaFunctionExample[0], new String[0]);
   }

   public FormulaFunctionEntry getCategoryEntry() {
      return mCategoryEntry;
   }

   /**
    * A utility query function that finds an entry matching the name specified.
    *
    * @param name The name to match.
    * @return The entry in this category matching that name, or null if none matches.
    */
   public FormulaFunctionEntry getEntryForName(String name) {
      for (int i = 0; i < mEntries.length; i++) {
         if (mEntries[i].getName().equals(name)) {
            return mEntries[i];
         }
      }
      return null;
   }

   /**
    * A utility query function that finds an entry matching the key specified.
    *
    * @param key The key to match.
    * @return The entry in this category matching that key, or null if none matches.
    */
   public FormulaFunctionEntry getEntryForKey(String key) {
      for (int i = 0; i < mEntries.length; i++) {
         if (mEntries[i].getKey().equals(key)) {
            return mEntries[i];
         }
      }
      return null;
   }

   /**
    * Does an internal scan of 'see' references, printing warnings on any issues (for sanity checking only)
    *
    * @param cats
    */
   public static void resolveReferences(FormulaFunctionCategory[] cats) {
      // do 'see' reference check (will just print 'warning' messages to System.err for now):
      for (int i = 0; i < cats.length; i++) {
         FormulaFunctionCategory cat = cats[i];
         FormulaFunctionEntry[] entries = cat.getEntries();
         for (int j = 0; j < entries.length; j++) {
            FormulaFunctionEntry entry = entries[j];
            // not very efficient for now...
            entry.resolveReferences(cats, cat);
         }
      }
   }

   public String getName() {
      return mName;
   }

   public String getKey() {
      return mKey;
   }

   public FormulaFunctionEntry[] getEntries() {
      return mEntries;
   }

   /**
    * Loads the function descriptions from an XML format (see XPath version for documentation) and
    * from properties (for internationlizable bits).
    *
    * @param is             The input soure.
    * @param resourcePrefix The prefix of the resource key to use for looking up localized descriptions.
    * @return The function categories
    * @throws Exception If there's a failure in parsing, IO, etc.
    */
   public static FormulaFunctionCategory[] parse(InputSource is, String resourcePrefix) throws Exception {
      SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
      FormulaFunctionCategoryHandler h = new FormulaFunctionCategoryHandler(resourcePrefix);
      parser.parse(is, h);
      FormulaFunctionCategory[] ffc = h.build();
      resolveReferences(ffc);
      return ffc;
   }

   public static FormulaFunctionCategory[] parse(String s, String resourcePrefix) throws Exception {
      SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
      FormulaFunctionCategoryHandler h = new FormulaFunctionCategoryHandler(resourcePrefix);
      parser.parse(new InputSource(new StringReader(s)), h);
      return h.build();
   }

   public String toString() {
      return mName;
   }
}
