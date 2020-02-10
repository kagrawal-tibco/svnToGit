package com.tibco.cep.decision.table.test.combotable;

import javax.swing.UIManager;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class ComboBoxTableException extends Exception{
    /**
     * @param string
     */
    public ComboBoxTableException(String string)
    {
        super(string);
    }
    public String getLookAndFeelName()
    {
        return UIManager.getLookAndFeel().getName();
    }
}