package com.jidesoft.swt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Panel;

import javax.swing.JComponent;
import javax.swing.JRootPane;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSwingUtilities;

public class JideSwtUtilities {
	public static void initSwing() {
		LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
		 System.setProperty("sun.awt.noerasebackground", "true");
	}

	public static Container getSwingContainer(Composite parent) {
		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
		//new SyncXErrorHandler().installHandler();
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

	/**
	 * Calls doLayout methods to all child JComponents in the specified
	 * container. Not sure the exact reason but the Swing container doLayout
	 * doesn't layout all child components when the container is put in a SWT
	 * container through SWT_AWT bridge. The behavior is when the parent
	 * container resized, the child components remain where they were at their
	 * old size as if the parent is not resized. To fix this, we have to call
	 * this doLayoutAll method when the parent is resized.
	 * 
	 * @param container
	 */
	public static void doLayoutAll(Container container) {
		JideSwingUtilities.setRecursively(container,
				new JideSwingUtilities.Handler() {
					public void action(Component c) {
						if (c instanceof JComponent) {
							((JComponent) c).doLayout();
						}
					}

					public boolean condition(Component c) {
						return c instanceof JComponent;
					}

					public void postAction(Component c) {
					}
				});
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

}
