package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tibco.cep.studio.mapper.ui.StudioStrings;

/**
 * A simple panel containing OK and Cancel buttons that lays itself out correctly.<br>
 * (Also supports other labels such as 'apply').<br>
 * This does not add any listeners to the buttons; the calling dialog must do that.
 * (Later add methods to add help buttons, etc.)
 */
public class OKCancelPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton mOK;
    private JButton mCancel;
    private JPanel m_panel;

    public OKCancelPanel() {
        super(new BorderLayout());
        mOK = new JButton(StudioStrings.OK);
        mCancel = new JButton(StudioStrings.CANCEL);
        mOK.setDefaultCapable(true);
        JPanel okcb = new JPanel(new GridLayout(1,2,8,0));
        okcb.add(mOK);
        okcb.add(mCancel);
        add(okcb,BorderLayout.EAST);
        m_panel = okcb;
        okcb.setBorder(DisplayConstants.getInternalBorder());
    }

    /**
     * Calling this makes it into an 'Apply' panel.
     */
    public void setApplyMode(boolean apply)
    {
        if (apply)
        {
            mOK.setText(StudioStrings.APPLY);
            mCancel.setVisible(false);
            m_panel.removeAll();
            m_panel.setLayout(new BorderLayout());
            m_panel.add(mOK,BorderLayout.EAST);
        }
    }

    /**
     * Calling this makes it into an 'Apply' panel w/ a close button.
     */
    public void setApplyCloseMode()
    {
        mOK.setText(StudioStrings.APPLY);
        mCancel.setText(StudioStrings.CLOSE);
    }

    /**
     * Calling this makes it into an 'Yes/No' panel.
     */
    public void setYesNoMode(boolean apply)
    {
        if (apply)
        {
            mOK.setText(StudioStrings.YES);
            mCancel.setText(StudioStrings.NO);
        }
    }

    public JButton getOKButton()
    {
        return mOK;
    }

    public JButton getCancelButton()
    {
        return mCancel;
    }
}

