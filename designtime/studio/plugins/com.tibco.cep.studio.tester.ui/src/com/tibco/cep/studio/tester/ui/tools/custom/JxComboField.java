package com.tibco.cep.studio.tester.ui.tools.custom;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboPopup;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 */

@SuppressWarnings("serial")
public class JxComboField extends JComboBox{

    JComponent component;
    public JxComboField()
    {
    }
    private BasicComboPopup getComboPopup()
    {
        int i = 0;
        for(int j = getUI().getAccessibleChildrenCount(this); i < j; i++)
        {
            javax.accessibility.Accessible accessible = getUI().getAccessibleChild(this, i);
            if(accessible instanceof BasicComboPopup)
            {
                return (BasicComboPopup)accessible;
            }
        }

        return null;
    }

    public JComponent getPopupComponent()
    {
        return component;
    }

    /**
     * @param jcomponent
     * @throws ComboBoxTableException
     */
    public void setPopupComponent(JComponent jcomponent)
        throws ComboBoxTableException
    {
        BasicComboPopup basiccombopopup = getComboPopup();
        if(basiccombopopup != null)
        {
            basiccombopopup.removeAll();
            basiccombopopup.add(jcomponent);
            component = jcomponent;
        } else
        {
            throw new ComboBoxTableException("The combo box popup menu cannot be modified.");
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.JComboBox#updateUI()
     */
    public void updateUI()
    {
        super.updateUI();
        try
        {
            if(component != null)
            {
                SwingUtilities.updateComponentTreeUI(component);
                setPopupComponent(component);
            }
        }
        catch(ComboBoxTableException incompatiblelookandfeelexception)
        {
            throw new RuntimeException(incompatiblelookandfeelexception);
        }
    }
}
