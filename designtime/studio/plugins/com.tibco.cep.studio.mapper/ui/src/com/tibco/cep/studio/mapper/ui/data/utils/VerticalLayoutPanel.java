package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

/**
 * Utility panel subclass that simplifies vertical layouts.
 */
public class VerticalLayoutPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Spacing is defaulted to 5, bottom gets extra space.
     */
    public VerticalLayoutPanel(Component top, Component bottom) {
        this(top,bottom,5,true);
    }

    public VerticalLayoutPanel(Component top, Component bottom, boolean bottomGetsSpace) {
        this(top,bottom,5,bottomGetsSpace);
    }

    public VerticalLayoutPanel(Component top, Component bottom, int vertSpace, boolean bottomGetsSpace) {
        super(new BorderLayout(0,vertSpace));
        if (bottomGetsSpace) {
            add(top,BorderLayout.NORTH);
            add(bottom,BorderLayout.CENTER);
        } else {
            add(top,BorderLayout.CENTER);
            add(bottom,BorderLayout.SOUTH);
        }
    }
}
