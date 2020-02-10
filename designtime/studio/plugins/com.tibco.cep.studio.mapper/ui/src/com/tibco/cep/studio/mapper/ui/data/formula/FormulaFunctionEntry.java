package com.tibco.cep.studio.mapper.ui.data.formula;


/**
 * The description for a single functions, works with {@link FormulaFunctionCategory}.
 */
public class FormulaFunctionEntry {
   private final String mKey;
   private final String mName;
   private final String mNamespace;

   private final String mDragString;
   private final boolean mIsCharacter;
   private final String mHelpText;
   private final FormulaFunctionExample[] mExamples;

   /**
    * Very much like the javadoc 'see'.  Contains the name (as in XPathBuilderEntry name) that this refers to.
    */
   public final String[] mSeeReferences;
   /**
    * These use the logical names, not the key names (which is what see references uses)
    */
   public String[] mSeeResolvedReferences;

   public FormulaFunctionEntry(String key, String name, String namespace, String dragString, boolean isChar, String helpText, FormulaFunctionExample[] examples, String[] seeReferences) {
      mKey = key;
      mName = name;
      mNamespace = namespace;
      mDragString = dragString;
      mIsCharacter = isChar;
      if (helpText == null) {
         helpText = "";
      }
      mHelpText = helpText;
      if (examples == null) {
         examples = new FormulaFunctionExample[0];
      }
      mExamples = examples;
      // be forgiving here.
      if (seeReferences == null) {
         seeReferences = new String[0];
      }
      mSeeReferences = seeReferences;
   }

   /**
    * Resolves & checks the integrity of the {@link #mSeeReferences}.<br>
    * For now, just prints to System.err if there's an issue.
    *
    * @param cats  All the categories
    * @param inCat The category this entry is used in (for local reference checking)
    */
   public void resolveReferences(FormulaFunctionCategory[] cats, FormulaFunctionCategory inCat) {
      if (mSeeReferences.length == 0) {
         mSeeResolvedReferences = mSeeReferences;
         return;
      }
      // Resolve them:
      mSeeResolvedReferences = new String[mSeeReferences.length];
      for (int i = 0; i < mSeeReferences.length; i++) {
         String r = mSeeReferences[i];
         mSeeResolvedReferences[i] = resolveReference(r, cats, inCat);
      }
   }

   /**
    * Gets the drag string, or null if none.
    *
    * @return The string, or null if none.
    */
   public String getDragString() {
      return mDragString;
   }

   /**
    * Gets the local-name part of the name.
    *
    * @return The name
    */
   public String getName() {
      return mName;
   }

   public String getHelpText() {
      return mHelpText;
   }

   public FormulaFunctionExample[] getExamples() {
      return mExamples;
   }

   /**
    * If this 'formula' actually just represents character text (a constant)
    *
    * @return true if this is for a constant.
    */
   public boolean getIsCharacter() {
      return mIsCharacter;
   }

   /**
    * Gets the namespace part of the name.
    *
    * @return The namespace.
    */
   public String getNamespace() {
      return mNamespace;
   }

   public String getKey() {
      return mKey;
   }

   private String resolveReference(String ref, FormulaFunctionCategory[] cats, FormulaFunctionCategory inCat) {
      StringBuffer sb = new StringBuffer();
      int si = ref.indexOf('/');
      if (si < 0) {
         String entryKey = ref;
         FormulaFunctionEntry entry = inCat.getEntryForKey(entryKey);
         if (entry == null) {
            referenceError("Couldn't find local entry key '" + entryKey + "'");
            sb.append(entryKey);
         }
         else {
            sb.append(entry.mName);
         }
      }
      else {
         String catName = ref.substring(0, si);
         String entryKey = ref.substring(si + 1);
         // get the category:
         boolean foundCat = false;
         for (int i = 0; i < cats.length; i++) {
            if (catName.equals(cats[i].getKey())) {
               // found it, now check in category ref:
               FormulaFunctionEntry entry = cats[i].getEntryForKey(entryKey);
               if (entry == null) {
                  referenceError("Couldn't find entry key '" + entryKey + "' in category '" + catName + "'");
               }
               else {
                  sb.append(cats[i].getName() + "/" + entry.mName);
               }
               foundCat = true;
               break;
            }
         }
         if (!foundCat) {
            referenceError("Category '" + catName + "' not found");
         }
      }
      return sb.toString();
   }

   /**
    * Used in {@link #resolveReference}
    * to handle errors uniformly.<br>
    * For now, just dumps an error to System.err.
    *
    * @param msg The error message (will be augmented with entry specific info)
    */
   private void referenceError(String msg) {
      System.err.println("See reference error in entry '" + mName + "' --- " + msg);
   }

   public String toString() {
      return mName;
   }
}
