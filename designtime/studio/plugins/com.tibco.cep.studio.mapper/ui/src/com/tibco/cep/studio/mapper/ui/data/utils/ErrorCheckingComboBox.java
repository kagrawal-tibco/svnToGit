package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import com.tibco.cep.studio.mapper.ui.PaintUtils;

/**
 * A combo box which checks/underlines for errors in the choice.<br>
 * This is useful, for example, if it represents a non-static set of choices --- a choice that was picked
 * at some point in time may not be valid later, etc.<br>
 * Automatically sets {@link #setEditable} to true because this only makes sense when it is editable
 * (even if the user will mostly pick from drop-down).<br>
 * Ideally this will also validate while typing, but that would require integrating {@link com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea}
 * with a combo box & given that most users will be using the drop-down part, this should be fine for now (even if the user hand-edits,
 * after the user hits enter, underlines will show up)
 */
public class ErrorCheckingComboBox extends JComboBox
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean m_isErr;
    private ErrorCheckingComboBoxChecker m_errorChecker;

    public ErrorCheckingComboBox()
    {
        super.setEditable(true);
        addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                refreshError();
            }
        });
        super.setOpaque(false);
    }

    /**
     * Implementation override.
     */
    public boolean isOptimizedDrawingEnabled()
    {
        return false;
    }

    public void setSelectedItem(Object anObject)
    {
        super.setSelectedItem(anObject);
        refreshError();
    }

    public void setErrorChecker(ErrorCheckingComboBoxChecker errorChecker)
    {
        m_errorChecker = errorChecker;
    }

    public void setEditor(ComboBoxEditor anEditor)
    {

        if (getEditor()!=null)
        {
            getEditor().removeActionListener(this);
        }
        super.setEditor(anEditor);
        if (anEditor != null) //This can be null - javax.swing.plaf.basic.BasicComboBoxUI.uninstallUI(BasicComboBoxUI.java:291)
            anEditor.addActionListener(this);
    }

    /*
    private void updateError()
    {
        if (m_currentTimer!=null)
        {
            m_currentTimer.stop();
        }
        Timer t = new Timer(500,new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                refreshError();
            }
        });
        t.setRepeats(false);
        t.start();
        m_currentTimer = t;
    }*/

    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
    }

    private void refreshError()
    {
        if (m_errorChecker==null)
        {
            setHasError(false);
        }
        setHasError(!m_errorChecker.isValid((String)getSelectedItem()));
    }

    private void setHasError(boolean val)
    {
        if (m_isErr != val)
        {
            m_isErr = val;
            repaint();
            // This extra delayed paint is, for whatever reason, required.  As you can see elsewhere,
            // I tried everything I know to make it paint correctly (opaque, optimizedDrawingEnabled), but
            // those didn't work.
            // My trusty old friend Mr. invokeLater came through once again, though.
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    repaint();
                }
            });
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if (m_isErr && !isPopupVisible())
        {
            int charWidth = g.getFontMetrics(getFont()).charWidth('X');
            String sel = (String)getSelectedItem();
            if (sel==null)
            {
                sel = "";
            }
            int width = g.getFontMetrics(getFont()).charsWidth(sel.toCharArray(),0,sel.length());
            int waveHeight = charWidth/2;
            g.setColor(Color.red);
            PaintUtils.drawWavyLine(g,waveHeight,waveHeight,getInsets().left+1,width,getHeight()-waveHeight);
        }
    }

}
