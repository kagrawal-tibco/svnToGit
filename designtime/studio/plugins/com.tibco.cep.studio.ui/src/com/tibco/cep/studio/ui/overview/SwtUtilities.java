package com.tibco.cep.studio.ui.overview;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Panel;
import java.awt.Toolkit;
import java.lang.reflect.Array;

import javax.swing.JRootPane;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tibco.cep.studio.ui.StudioUIManager;

public class SwtUtilities {
	public static void initSwing() {
//		LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
		System.setProperty("sun.awt.noerasebackground", "true");
		StudioUIManager.getInstance(); // will set look and feel
		GraphicsEnvironment.getLocalGraphicsEnvironment();
		Toolkit.getDefaultToolkit();	
	}

	public static Container getSwingContainer(Composite parent) {
		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
		new SyncXErrorHandler().installHandler();
		Panel panel = new Panel(new BorderLayout()) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void update(java.awt.Graphics g) {
				/* Do not erase the background */
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}

	public static class CleanResizeListener extends ControlAdapter {
		  private Rectangle oldRect = null;
		  public void controlResized(ControlEvent e) {
		      // Prevent garbage from Swing lags during resize. Fill exposed areas 
		      // with background color. 
		      Composite composite = (Composite)e.widget;
		      Rectangle newRect = composite.getClientArea();
		      if (oldRect != null) {
		          int heightDelta = newRect.height - oldRect.height;
		          int widthDelta = newRect.width - oldRect.width;
		          if ((heightDelta > 0) || (widthDelta > 0)) {
		              GC gc = new GC(composite);
		              try {
		                  gc.fillRectangle(newRect.x, oldRect.height, newRect.width, heightDelta);
		                  gc.fillRectangle(oldRect.width, newRect.y, widthDelta, newRect.height);
		              } finally {
		                  gc.dispose();
		              }
		          }
		      }
		      oldRect = newRect;
		  }
		}

	
	 public static boolean equals(Object paramObject1, Object paramObject2) {
	    return equals(paramObject1, paramObject2, false);
	  }
	  
	  public static boolean equals(Object paramObject1, Object paramObject2, boolean paramBoolean) {
	    if (paramObject1 == paramObject2) {
	      return true;
	    }
	    if ((paramObject1 != null) && (paramObject2 == null)) {
	      return false;
	    }
	    if (paramObject1 == null) {
	      return false;
	    }
	    if (((paramObject1 instanceof Comparable)) && ((paramObject2 instanceof Comparable)) && (paramObject1.getClass().isAssignableFrom(paramObject2.getClass()))) {
	      return ((Comparable)paramObject1).compareTo(paramObject2) == 0;
	    }
	    if (((paramObject1 instanceof Comparable)) && ((paramObject2 instanceof Comparable)) && (paramObject2.getClass().isAssignableFrom(paramObject1.getClass()))) {
	      return ((Comparable)paramObject2).compareTo(paramObject1) == 0;
	    }
	    if ((paramBoolean) 
	    		&& (paramObject1.getClass().isArray()) 
	    		&& (paramObject2.getClass().isArray())) {
	      int i = Array.getLength(paramObject1);
	      int j = Array.getLength(paramObject2);
	      if (i != j) {
	        return false;
	      }
	      for (int k = 0; k < i; k++) {
	        boolean bool = equals(Array.get(paramObject1, k), Array.get(paramObject2, k));
	        if (!bool) {
	          return false;
	        }
	      }
	      return true;
	    }
	    return paramObject1.equals(paramObject2);
	  }
}
