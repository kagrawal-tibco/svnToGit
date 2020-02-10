package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.tibco.cep.mapper.util.prof.Profile;

/**
 * A utility subclass of JScrollPane which has a hook to allow the viewport view
 * a chance to resize, horizontally, based on the amount of real estate available.<BR>
 * Works with HorzSizeable
 */
public class PaintTimedPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Profile mProfile;

    public PaintTimedPanel(String name) {
        super();
        mProfile = new Profile(name);
    }

    public PaintTimedPanel(LayoutManager manager, String name) {
        super(manager);
        mProfile = new Profile(name);
    }

    public void paint(Graphics g) {
        mProfile.start();
        super.paint(g);
        mProfile.stop();
    }

    public Profile prof_getPaintProfile() {
        return mProfile;
    }
}
