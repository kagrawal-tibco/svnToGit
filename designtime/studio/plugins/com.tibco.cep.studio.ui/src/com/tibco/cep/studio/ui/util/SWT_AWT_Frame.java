package com.tibco.cep.studio.ui.util;

import java.awt.Frame;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;


/**
 * 
 * @author sasahoo
 *
 */
public class SWT_AWT_Frame {
	
	private static SWT_AWT_Frame instance = null;
	private static Frame frame = null; 
	
	public static SWT_AWT_Frame getInstance() {
		if(instance == null){
			instance = new SWT_AWT_Frame();
			try {
				frame = AWTEnvironment.getInstance(Display.getDefault()).createDialogParentFrame();
			} catch(IllegalStateException e) {
				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
				frame = AWTEnvironment.getInstance(Display.getDefault()).createDialogParentFrame(win.getShell());
			}
		}
		return instance;
	}
	public Frame getFrame() {
		return frame;
	}
}

