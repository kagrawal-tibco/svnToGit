package com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring;
/*
 * SyntaxStyle.java - A simple text style class
 * Copyright (C) 1999 Slava Pestov
 *
 * You may use and modify this package for any purpose. Redistribution is
 * permitted, in both source and binary form, provided that this notice
 * remains intact in all source distributions of this package.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.text.Segment;
import javax.swing.text.TabExpander;
import javax.swing.text.Utilities;

/**
 * A simple text style class. It can specify the color, italic flag,
 * and bold flag of a run of text.
 * @author Slava Pestov
 * @version $Id$
 */
public class SyntaxStyle
{
	/**
	 * Creates a new SyntaxStyle.
	 * @param color The text color
	 * @param italic True if the text should be italics
	 * @param bold True if the text should be bold
	 */
	public SyntaxStyle(Color color, boolean italic, boolean bold)
	{
		this.color = color;
		this.italic = italic;
		this.bold = bold;
	}

	/**
	 * Returns the color specified in this style.
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Returns true if no font styles are enabled.
	 */
	public boolean isPlain()
	{
		return !(bold || italic);
	}

	/**
	 * Returns true if italics is enabled for this style.
	 */
	public boolean isItalic()
	{
		return italic;
	}

	/**
	 * Returns true if boldface is enabled for this style.
	 */
	public boolean isBold()
	{
		return bold;
	}

	/**
	 * Returns the specified font, but with the style's bold and
	 * italic flags applied.
	 */
	public Font getStyledFont(Font font)
	{
		if(font == null)
			throw new NullPointerException("font param must not"
				+ " be null");
		if(font.equals(lastFont))
			return lastStyledFont;
		lastFont = font;
		lastStyledFont = new Font(font.getFamily(),
			(bold ? Font.BOLD : 0)
			| (italic ? Font.ITALIC : 0),
			font.getSize());
		return lastStyledFont;
	}

	/**
	 * Returns the font metrics for the styled font.
	 */
	public FontMetrics getFontMetrics(Font font)
	{
		if(font == null)
			throw new NullPointerException("font param must not"
				+ " be null");
		if(font.equals(lastFont) && fontMetrics != null)
			return fontMetrics;
		lastFont = font;
		lastStyledFont = new Font(font.getFamily(),
			(bold ? Font.BOLD : 0)
			| (italic ? Font.ITALIC : 0),
			font.getSize());
		fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(
			lastStyledFont);
		return fontMetrics;
	}

	/**
	 * Sets the foreground color and font of the specified graphics
	 * context to that specified in this style.
	 * @param gfx The graphics context
	 * @param font The font to add the styles to
	 */
	public void setGraphicsFlags(Graphics gfx, Font font)
	{
		Font _font = getStyledFont(font);
		gfx.setFont(_font);
		gfx.setColor(color);
	}

	/**
	 * Returns a string representation of this object.
	 */
	public String toString()
	{
		return getClass().getName() + "[color=" + color +
			(italic ? ",italic" : "") +
			(bold ? ",bold" : "") + "]";
	}

	// private members
	private Color color;
	private boolean italic;
	private boolean bold;
	private Font lastFont;
	private Font lastStyledFont;
	private FontMetrics fontMetrics;

    /**
     * This method was added from the original JEdit to allow a SyntaxStyle to do more types of hiliting, background
     * underlining, etc.  Subclasses should override with specialized painting.<br>
     * The default implementation just paints tabbed text according to the graphics flags.
     * @return The x position at the end of the text.
     */
    public int drawText(Segment line, int x, int y, Graphics gfx, TabExpander expander, Font defaultFont) {
        setGraphicsFlags(gfx,defaultFont);
        return Utilities.drawTabbedText(line,x,y,gfx,expander,0);
    }
}
