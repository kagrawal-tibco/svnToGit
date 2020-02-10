package com.tibco.cep.studio.ui.util;

import java.awt.Frame;

import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.util.IDisplayFrameProvider;

public class DefaultDisplayFrameProvider implements IDisplayFrameProvider {
	private Frame defaultFrame = null;
	
	public DefaultDisplayFrameProvider() {
	}

	@Override
	public Frame getFrame() {
		return defaultFrame;
	}

	@Override
	public void createFrame() {
		if (Display.getCurrent() == null) {
    		// not in the UI thread
    		Display.getDefault().asyncExec(new Runnable() {
			
				@Override
				public void run() {
					defaultFrame = SWT_AWT_Frame.getInstance().getFrame();
				}
			});
    	} else {
    		defaultFrame = SWT_AWT_Frame.getInstance().getFrame();
    	}
	}

}
