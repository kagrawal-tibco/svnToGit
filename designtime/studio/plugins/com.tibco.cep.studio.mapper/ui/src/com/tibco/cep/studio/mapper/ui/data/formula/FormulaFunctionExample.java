package com.tibco.cep.studio.mapper.ui.data.formula;

import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditResources;


/**
 * The contents of an example use of a function, used with {@link FormulaFunctionEntry}.
 */
public class FormulaFunctionExample {

   // either this is set:
   public final String mText;

   // or these:
   public final String mInput;
   public final String mResult;

   public FormulaFunctionExample(String text) {
      mText = text;
      mInput = null;
      mResult = null;
   }

   public FormulaFunctionExample(String input, String result) {
      mText = null;
      mInput = input;
      mResult = result;
   }

   public String toString() {
      if (mText != null) {
         return mText;
      }
      else {
         return mInput + " -> " + mResult;
      }
   }

   public String formatHTML() {
      StringBuffer sb = new StringBuffer();
      sb.append("<h3>");
      sb.append(XPathEditResources.EXAMPLE_HEADER);
      sb.append("</h3>");
      sb.append("<font color='blue' face='monospaced'>");
      if (mText != null) {
         sb.append(mText);
         sb.append("</font>");
      }
      else {
         sb.append("<code>");
         sb.append(mInput);
         if (mResult != null) {
            sb.append("<br></br>");
            sb.append("<font color='black'>");
            sb.append("<b>");
            sb.append(XPathEditResources.RETURNS_HEADER);
            sb.append("</b>");
            sb.append("</font>");
            sb.append("<br></br>");
            sb.append(mResult);
         }
         sb.append("</code>");
      }
      sb.append("</font>");
      return sb.toString();
   }
}
