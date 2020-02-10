package com.tibco.cep.studio.mapper.ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;

/**
 * Misc. string painting related utility functions.
 */
public class StringPaintUtils
{

    private static final String DEFAULT_OVERFLOW_SUFFIX = "...";

    public static void paintDisplayableString(Graphics g, String displayString, int availableWidth, int x, int y)
    {
        String actualPaint = computeDisplayableString(g.getFontMetrics(), displayString, availableWidth);
        g.drawString(actualPaint, x, y);
    }

    /**
     * If the string fits in the given width, returns the string, unchanged.
     * Otherwise, it returns as much as will fit with a '...' appended.
     */
    public static String computeDisplayableString(FontMetrics fontMetrics, String displayString, int availableWidth)
    {
        //if (fontMetrics.hasUniformLineMetrics()) {
        //  (Could optimize for this case...)
        //
        int len = computeDisplayStringLength(fontMetrics, displayString, DEFAULT_OVERFLOW_SUFFIX, availableWidth);
        if (len == displayString.length())
        {
            return displayString;
        }
        return displayString.substring(0, len) + DEFAULT_OVERFLOW_SUFFIX;
    }

    /**
     * Returns displayString.length() if the entire string fits.
     */
    public static int computeDisplayStringLength(FontMetrics fontMetrics, String displayString, String overflowSuffixString, int availableWidth)
    {
        int requiredWidth = fontMetrics.stringWidth(displayString);
        if (requiredWidth < availableWidth)
        {
            // hurray!
            return displayString.length();
        }
        // rats, doesn't fit:
        int clipAvailableWidth = availableWidth - fontMetrics.stringWidth(overflowSuffixString);
        int numChars;
        int textLen = displayString.length();
        int widthSoFar = 0;
        for (numChars = 0; numChars < textLen; numChars++)
        {
            //? Is there any fancy (don't know the name)
            widthSoFar += fontMetrics.charWidth(displayString.charAt(numChars));
            if (widthSoFar > clipAvailableWidth)
            {
                break;
            }
        }
        return numChars;
    }


    /**
     * Returns true if the size of character i equals the character M. This test
     * can be performed by using the system provided fonts (1pt size) and with
     * fractional widths turned off.
     */
    public static boolean isMonospacedFont(FontRenderContext ctx, Font f)
    {
        double iw = f.getStringBounds("i", ctx).getWidth();
        double mw = f.getStringBounds("M", ctx).getWidth();

        return iw == mw;
    }
}


