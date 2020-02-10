package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

/**
 * A utility subclass of JScrollPane which has a hook to allow the viewport view
 * a chance to resize, horizontally, based on the amount of real estate available.<BR>
 * Works with HorzSizeable
 */
public class HorzSizedScrollPane extends JScrollPane {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mLastWidth;

    public HorzSizedScrollPane() {
        setup();
    }

    public HorzSizedScrollPane(Component viewportView) {
        super(viewportView);
        setup();
    }

    private void setup() {
        // The border is just irritating & wasting space in these uses:
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void validate() {
    	try {
    		super.validate();
    		// If the above validate caused the vertical scroll bar to appear or disappear...
    		if (resize()) {
    			// ... we will get here, because the total width changed.
    			// AND, we need to revalidate because we've adjusted sizes.
    			super.validate();
    		}
    	}  catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x,y,w,h);
        resize();
    }

    private boolean resize() {
        if (!(getViewport().getView() instanceof HorzSizeable)) {
            return false;
        }
        Insets i = getInsets();
        int w = getSize().width - (i.left+i.right);
        HorzSizeable bt = (HorzSizeable) getViewport().getView();
        int totalWidth;
        if (getVerticalScrollBar().isVisible()) {
            totalWidth = w - getVerticalScrollBar().getSize().width;
        } else {
            totalWidth = w;
        }
        if (totalWidth==mLastWidth) {
            return false;
        }
        mLastWidth = totalWidth;
        bt.resized(totalWidth);
        return true;
    }
}
