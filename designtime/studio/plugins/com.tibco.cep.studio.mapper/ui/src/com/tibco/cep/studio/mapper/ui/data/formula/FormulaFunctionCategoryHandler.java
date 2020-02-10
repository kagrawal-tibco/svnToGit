package com.tibco.cep.studio.mapper.ui.data.formula;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;

/**
 * The xml parser for building {@link FormulaFunctionCategoryHandler}.
 */
public class FormulaFunctionCategoryHandler extends DefaultHandler {
   private ArrayList<FormulaFunctionCategory> mCategories = new ArrayList<FormulaFunctionCategory>();
   private ArrayList<FormulaFunctionEntry> mEntries = new ArrayList<FormulaFunctionEntry>();
   private String mCatKey;
   private String mCatName;
   private String mEntryKey; // key version of name.
   private String mEntryName;
   private String mDragString;
   private String mHelpText;
   private String mCatDragString;
   private String mCatHelpText;
   private ArrayList<String> mSeeReferences = new ArrayList<String>();
   private ArrayList<FormulaFunctionExample> mExamples = new ArrayList<FormulaFunctionExample>();
   private boolean mInEntry;
   private String mVersionText;
   private boolean mIsChar = false;

   private String mExampleInput;
   private String mExampleReturns;

   private StringBuffer mBuffer = new StringBuffer();

   public FormulaFunctionCategory[] build() {
      return mCategories.toArray(new FormulaFunctionCategory[mCategories.size()]);
   }

   private final String m_resourcePrefix;

   public FormulaFunctionCategoryHandler(String resourcePrefix) {
      m_resourcePrefix = resourcePrefix;
   }

   public void startElement(String p0, String p1, String elName, Attributes p3) throws SAXException {
      if (elName.equals("category")) {
         mCatName = p3.getValue("name");
         mCatKey = p3.getValue("key");
         if (mCatName == null) {
            // new style, use key:
            mCatName = getFuncResource(mCatKey + ".label");
         }
         return;
      }
      if (elName.equals("entry")) {
         mEntryName = p3.getValue("name");
         mEntryKey = p3.getValue("key");
         if (mEntryName == null) {
            mEntryName = getFuncResource(mCatKey + "." + mEntryKey + ".label");
         }
         if (mEntryKey == null) {
            mEntryKey = mEntryName; // if it's an xpath function name, it's not localized, so we use the name exactly.
         }
         mInEntry = true;
         mExamples.clear();
         mSeeReferences.clear();
         mDragString = null;
         mHelpText = null;
         return;
      }
      if (elName.equals("drag")) {
         mBuffer.setLength(0);
         String isChar = p3.getValue("is-character");
         if (isChar != null) {
            mIsChar = new Boolean(isChar).booleanValue();
         }
         return;
      }
      if (elName.equals("description")) {
         mBuffer.setLength(0);
         return;
      }
      if (elName.equals("version")) {
         mBuffer.setLength(0);
         return;
      }
      if (elName.equals("input") || elName.equals("returns")) {
         mBuffer.setLength(0);
         return;
      }
      if (elName.equals("example")) {
         mBuffer.setLength(0);
         mExampleInput = null;
         mExampleReturns = null;
         return;
      }
      if (elName.equals("see")) {
         mBuffer.setLength(0);
         return;
      }
      // assume html:
      mBuffer.append("<");
      mBuffer.append(elName);
      mBuffer.append(">");
   }

   public void endElement(String p0, String p1, String elName) throws SAXException {
      if (elName.equals("category")) {
         FormulaFunctionEntry[] ent = mEntries.toArray(new FormulaFunctionEntry[mEntries.size()]);
         if (mCatDragString == null) {
            mCatDragString = getFuncResource(mCatKey + ".signature");
         }
         if (mCatHelpText == null) {
            mCatHelpText = getFuncResource(mCatKey + ".description.label");
         }
         FormulaFunctionCategory cat = new FormulaFunctionCategory(mCatKey, mCatName, ent, mCatDragString, mCatHelpText);
         mCategories.add(cat);
         mCatHelpText = null;
         mEntries.clear();
         return;
      }
      if (elName.equals("entry")) {
         String ns = "tib".equals(mVersionText) ? TibExtFunctions.NAMESPACE : null;
         if (mDragString == null) {
            mDragString = getFuncResource(mCatKey + "." + mEntryKey + ".signature");
         }
         String dragString;
         if ("tib".equals(mVersionText)) {
            dragString = "tib:" + mDragString; // seed w/ recommended prefix.
         }
         else {
            dragString = mDragString;
         }
         if (mHelpText == null) {
            mHelpText = getFuncResource(mCatKey + "." + mEntryKey + ".description.label");
         }
         FormulaFunctionEntry entry = new FormulaFunctionEntry(mEntryKey,
                                                               mEntryName,
                                                               ns,
                                                               dragString,
                                                               mIsChar,
                                                               mHelpText,
                                                               mExamples.toArray(new FormulaFunctionExample[0]),
                                                               mSeeReferences.toArray(new String[0]));
         mEntries.add(entry);
         mInEntry = false;
         mIsChar = false;
         return;
      }
      if (elName.equals("drag")) {
         if (mInEntry) {
            mDragString = mBuffer.toString().trim();
         }
         else {
            mCatDragString = mBuffer.toString().trim();
         }
         return;
      }
      if (elName.equals("description")) {
         if (mInEntry) {
            mHelpText = mBuffer.toString().trim();
         }
         else {
            mCatHelpText = mBuffer.toString().trim();
         }
         return;
      }
      if (elName.equals("version")) {
         if (mInEntry) {
            mVersionText = mBuffer.toString().trim();
         }
         return;
      }
      if (elName.equals("example")) {
         FormulaFunctionExample ex;
         String exampleText = mBuffer.toString().trim();
         if (mExampleInput != null) {
            ex = new FormulaFunctionExample(mExampleInput, mExampleReturns);
         }
         else {
            ex = new FormulaFunctionExample(exampleText);
         }
         mExamples.add(ex);
         return;
      }
      if (elName.equals("input")) {
         mExampleInput = mBuffer.toString().trim();
         return;
      }
      if (elName.equals("returns")) {
         mExampleReturns = mBuffer.toString().trim();
         return;
      }
      if (elName.equals("see")) {
         mSeeReferences.add(mBuffer.toString().trim());
         return;
      }
      // assume html:
      mBuffer.append("</");
      mBuffer.append(elName);
      mBuffer.append(">");
   }

   public void characters(char[] c, int c1, int c2) {
      mBuffer.append(c, c1, c2);
   }

   private String getFuncResource(String key) {
      String akey = m_resourcePrefix + "." + key.toLowerCase();
      //ResourceBundle rb = ResourceBundle.getBundle("com.tibco.ui.data.Resources");
      //String val = rb.getString(key);
      String val = ResourceBundleManager.getMessage(akey);
      if (val == null || val.startsWith("#MISSING:") || val.startsWith("*MISSING:")) // (Used to use #, now seems to use * to lead missing; no other way to tell if something is missing it seems.)
      {
         // Print it out now so that we can see it easily.
         System.err.println("Missing resource ----> " + akey);
         return "# missing: " + akey;
      }
      return val;
   }

   public static String getFuncResource(String resourcePrefix, String key) {
      String akey = resourcePrefix + "." + key.toLowerCase();
      //ResourceBundle rb = ResourceBundle.getBundle("com.tibco.ui.data.Resources");
      //String val = rb.getString(key);
      String val = ResourceBundleManager.getMessage(akey);
      if (val == null || val.startsWith("#MISSING:") || val.startsWith("*MISSING:")) // (Used to use #, now seems to use * to lead missing; no other way to tell if something is missing it seems.)
      {
         // Print it out now so that we can see it easily.
         System.err.println("Missing resource ----> " + akey);
         return "# missing: " + akey;
      }
      return val;
   }


   static {
	   ResourceBundleManager.addResourceBundle("com.tibco.cep.studio.mapper.ui.data.xpath.Resources", null);
   }
//      ResourceManager man = ResourceManager.manager;
//      MultiResourceManager mrm = (MultiResourceManager) man;
//      //@ need to get the locale here?
//      //ClassLoader myCL = DataIcons.class.getClassLoader();
//      ResourceBundle rb = ResourceBundle.getBundle("com.tibco.ui.data.xpath.Resources");
//      mrm.addResource(rb);
//   }

}
