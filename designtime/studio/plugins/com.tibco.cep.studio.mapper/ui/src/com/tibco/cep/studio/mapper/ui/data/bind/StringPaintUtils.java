package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Misc. string painting related utility functions.
 *
 */
public class StringPaintUtils {

   private static final String DEFAULT_OVERFLOW_SUFFIX = "...";

   public static void paintDisplayableString(Graphics g, String displayString, int availableWidth, int x, int y) {
      String actualPaint = computeDisplayableString(g.getFontMetrics(), displayString, availableWidth);
      g.drawString(actualPaint, x, y);
   }

   /**
    * If the string fits in the given width, returns the string, unchanged.
    * Otherwise, it returns as much as will fit with a '...' appended.
    */
   public static String computeDisplayableString(FontMetrics fontMetrics, String displayString, int availableWidth) {
      //if (fontMetrics.hasUniformLineMetrics()) {
      //  (Could optimize for this case...)
      //
      int len = computeDisplayStringLength(fontMetrics, displayString, DEFAULT_OVERFLOW_SUFFIX, availableWidth);
      if (len == displayString.length()) {
         return displayString;
      }
      return displayString.substring(0, len) + DEFAULT_OVERFLOW_SUFFIX;
   }

   /**
    * Returns displayString.length() if the entire string fits.
    */
   public static int computeDisplayStringLength(FontMetrics fontMetrics, String displayString, String overflowSuffixString, int availableWidth) {
      int requiredWidth = fontMetrics.stringWidth(displayString);
      if (requiredWidth < availableWidth) {
         // hurray!
         return displayString.length();
      }
      // rats, doesn't fit:
      int clipAvailableWidth = availableWidth - fontMetrics.stringWidth(overflowSuffixString);
      int numChars;
      int textLen = displayString.length();
      int widthSoFar = 0;
      for (numChars = 0; numChars < textLen; numChars++) {
         //? Is there any fancy (don't know the name)
         widthSoFar += fontMetrics.charWidth(displayString.charAt(numChars));
         if (widthSoFar > clipAvailableWidth) {
            break;
         }
      }
      return numChars;
   }
}


