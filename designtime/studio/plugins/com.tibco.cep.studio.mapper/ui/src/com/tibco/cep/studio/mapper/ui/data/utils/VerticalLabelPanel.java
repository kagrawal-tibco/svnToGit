package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Utility panel subclass that joins a component and a label, aligned vertically above it.
 */
public class VerticalLabelPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel m_label;

    public VerticalLabelPanel(String label, Component bottom) {
        this(new JLabel(label),bottom);
    }

    public VerticalLabelPanel(JLabel label, Component labelled)
    {
        super(new BorderLayout(0,0));
        m_label = label;
        label.setLabelFor(labelled);
        add(label,BorderLayout.NORTH);
        add(labelled,BorderLayout.CENTER);
    }

    public JLabel getLabel()
    {
        return m_label;
    }
}
