package com.tibco.cep.studio.ui;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioUIManager {
	
	private static boolean isLookAndFeelInitialized = false;
	private static StudioUIManager studioUILookAndFeel;
	private Map<String, IAction> globalActions = new HashMap<String, IAction>();
	private Map<String, IContributionItem> globalContributionItems = new HashMap<String, IContributionItem>();
	
	public static StudioUIManager getInstance(){
		if(studioUILookAndFeel == null){
			studioUILookAndFeel = new StudioUIManager();
		}
		return studioUILookAndFeel;
	}
	
	private StudioUIManager(){
		try {
			if (SWT.getPlatform().equals("cocoa")) {
				SWT_AWT.embeddedFrameClass = "sun.lwawt.macosx.CViewEmbeddedFrame";
				// SWT_AWT.embeddedFrameClass =
				// "com.tibco.cep.studio.ui.BEViewEmbeddedFrame";
				setSystemLookAndFeel();
			} else {
				EventQueue.invokeAndWait(new Runnable() {
					public void run() {
						setSystemLookAndFeel();
					}
				});
			}
		} catch (InterruptedException e) {
			SWT.error(SWT.ERROR_FAILED_EXEC, e);
		} catch (InvocationTargetException e) {
			SWT.error(SWT.ERROR_FAILED_EXEC, e);
		}
	}
	
	protected void setSystemLookAndFeel() {
		assert EventQueue.isDispatchThread();
		if (!isLookAndFeelInitialized) {
			isLookAndFeelInitialized = true;
			try {
				String systemLaf = UIManager.getSystemLookAndFeelClassName();
//				String xplatLaf = UIManager.getCrossPlatformLookAndFeelClassName();
				// Java makes metal the system look and feel if running under a
				// non-gnome Linux desktop. Fix that here, if the RCP itself is
				// running with the GTK windowing system set.
				/*
				if (xplatLaf.equals(systemLaf) && SWT.getPlatform().equals("gtk")) {
					systemLaf = GTK_LOOK_AND_FEEL_NAME;
				}
				UIManager.setLookAndFeel(systemLaf);
				*/
				
				if (SWT.getPlatform().equals("gtk")) {
					//System.out.println("System L&F: " + systemLaf);
					if (systemLaf.equalsIgnoreCase(com.tibco.cep.tpcl.Activator.GTK_LOOK_AND_FEEL_NAME)) {
						//UIManager.setLookAndFeel(METAL_LOOK_AND_FEEL_NAME);
						// The following is same as Metal
						UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
						System.out.println("Setting L&F: " + UIManager.getLookAndFeel());
					}
				} else {
					UIManager.setLookAndFeel(systemLaf);
				}
				
				//Setting No Erase Background
				//TODO - To avoid flicker -- this setting works only on Windows 
				if ("win32".equals(SWT.getPlatform())) {
					System.setProperty("sun.awt.noerasebackground", "true");
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/* (non-Javadoc)
	 * This is to add all the actions registered in the map
	 */
	public void addAction(String id, IAction action){
		if(!(globalActions.keySet().contains(id))){
			globalActions.put(id, action);
		}
	}
	
	public void addContributionItem(String id, IContributionItem item){
		if(!(globalContributionItems.keySet().contains(id))){
			globalContributionItems.put(id, item);
		}
	}
	
	/* (non-Javadoc)
	 * This is to retrieve all the actions added in the map
	 */
	public Map<String, IAction> getGlobalActions() {
		return globalActions;
	}

	public Map<String, IContributionItem> getGlobalContributionItems() {
		return globalContributionItems;
	}
}