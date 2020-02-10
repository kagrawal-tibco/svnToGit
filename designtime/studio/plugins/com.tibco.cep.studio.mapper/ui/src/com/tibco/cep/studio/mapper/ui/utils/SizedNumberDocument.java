/*
 * User: jroberts
 * Date: Jan 28, 2002
 * Time: 2:24:13 PM
 * To change template for new class use 
 */
package com.tibco.cep.studio.mapper.ui.utils;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class SizedNumberDocument extends NumberDocument
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int allowedLength;

    public SizedNumberDocument(int digitLength)
    {
        allowedLength = digitLength;
    }
    public void insertString(int offset, String str, AttributeSet set)
    throws BadLocationException
    {
        if (offset > allowedLength-1)
            Toolkit.getDefaultToolkit().beep();
        else
            super.insertString(offset, str, set);
    }
}

