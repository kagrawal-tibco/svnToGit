/*
 * User: jroberts
 * Date: Jan 28, 2002
 * Time: 2:12:30 PM
 * To change template for new class use 
 */
package com.tibco.cep.studio.mapper.ui.utils;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumberDocument extends PlainDocument
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void insertString(int offset, String str, AttributeSet set)
    throws BadLocationException
    {
        if (str.length() > 0)
        {
            Character aChar = new Character(str.charAt(0));

            if ( Character.isDigit(aChar.charValue()))
                super.insertString(offset, str, set);
            else
                Toolkit.getDefaultToolkit().beep();
        }
    }
}

