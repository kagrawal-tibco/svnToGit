package com.tibco.cep.studio.ui.views;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.be.model.functions.impl.ModelJavaFunction;
import com.tibco.be.model.functions.utils.FunctionHelpBundle;
import com.tibco.cep.studio.core.util.FunctionHoverUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.cep.studio.ui.util.Messages;

public class FunctionHover {

	Shell fMainShell;
	Display display;
	Shell fHoverShell;
	Browser browser;

	private boolean fInsideHoverWindow;
	private boolean fHoverScrolling;

	public static final int HOVER_WIDTH = 800;
	private int HOVER_HEIGHT = 300;
	private int VIEWER_WIDTH;
	private int MARGIN_WIDTH = 20;
	private int MARGIN_HEIGHT = 20;
	private int MAX_WIDTH;
	private int MAX_HEIGHT;

	private static FunctionHover fnHover;

	private FunctionHover() {
		fMainShell = Display.getDefault().getActiveShell();
		if (fMainShell == null) {
			fMainShell = new Shell();
		}
		display = fMainShell.getDisplay();
		fHoverShell = new Shell(fMainShell.getDisplay(), SWT.ON_TOP | SWT.TOOL);
		fHoverShell.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		fHoverShell.setForeground(display.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		fHoverShell.setSize(HOVER_WIDTH, HOVER_HEIGHT);
		browser = new Browser(fHoverShell, SWT.NONE);
		browser.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				fInsideHoverWindow = false;
				fHoverScrolling = false;
				clearHover();
			}
		});
		browser.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent e) {
				fHoverScrolling = true;
			}
		});
		browser.addMouseTrackListener(new MouseTrackListener() {

			@Override
			public void mouseHover(MouseEvent e) {
			}

			@Override
			public void mouseExit(MouseEvent e) {
				if (fHoverScrolling) {
					return;
				}
				fInsideHoverWindow = false;
				clearHover();
			}

			@Override
			public void mouseEnter(MouseEvent e) {
				fInsideHoverWindow = true;
				fHoverScrolling = false;
				fHoverShell.setActive();
				fHoverShell.setFocus();
			}
		});

		browser.setSize(HOVER_WIDTH, HOVER_HEIGHT);
	}

	public static FunctionHover getInstance() {
		if (fnHover == null)
			fnHover = new FunctionHover();
		return fnHover;
	}

	private boolean isEnabled() {
		return StudioUIPlugin.getDefault().getPreferenceStore().getBoolean(StudioUIPreferenceConstants.CATALOG_FUNCTION_SHOW_TOOLTIPS);
	}

	public void drawHover(TreeItem treeItem, Point pt, FunctionHelpBundle bundle) {
		if (!isEnabled()) {
			return;
		}
		String htmlText = getDisplayText(treeItem.getData(), bundle);
		if (htmlText != null && !browser.isDisposed()) {
			browser.setText(htmlText);
			fHoverShell.pack();
			setHoverLocation(pt);
			fHoverShell.open();
			fHoverShell.setVisible(true);
			// fHoverShell.setFocus();
		}
	}

	public void clearHover() {
		if (fInsideHoverWindow) {
			return; // do not clear the window if the user places the cursor in
					// the hover window
		}
		if (!fHoverShell.isDisposed() && !browser.isDisposed()) {
			fHoverShell.setVisible(false);
			browser.setText("");
		}
	}

	/**
	 * @param treeItem
	 * @param bundle
	 * @return
	 */
	public String getDisplayText(Object data, FunctionHelpBundle bundle) {
		String s = null;
		// VIEWER_WIDTH =
		// treeItem.getDisplay().getFocusControl().getBounds().width;
		if (bundle != null && data != null && data instanceof Predicate) {
			Predicate node = (Predicate) data;
			if (node instanceof JavaStaticFunction) {
				JavaStaticFunction function = (JavaStaticFunction) node;
				if (function.getToolTip() != null && function.getToolTip().length() > 0) {
					s = function.getToolTip();
				} else {
					s = bundle.tooltip(function);
					if (s != null && s.endsWith(Messages.getString("catalog.functionsView.tooltip"))) {
						if (function.getMethod() != null && function.getMethod().isAnnotationPresent(BEFunction.class)) {
							BEFunction ann = function.getMethod().getAnnotation(BEFunction.class);
							String categoryName = function.getName().getNamespaceURI();
							s = FunctionHoverUtil.getDynamicToolTip(categoryName, ann);
						} else if(function.getMethod() != null && isAnnotationPresent(function.getMethod(),BEFunction.class.getName())){
							
							Annotation ann = getAnnotation(function.getMethod(),BEFunction.class.getName());
							if(ann != null) {
								String categoryName = function.getName().getNamespaceURI();
								s = FunctionHoverUtil.getDynamicToolTip(categoryName,ann);
							}
							
						}else {
							s = Messages.getString("catalog.functionsView.functionhover.defaultText", function.getName().localName);
						}
					}
				}
			}
			if (node instanceof ModelFunction) {
				if(node instanceof ModelJavaFunction){
					ModelJavaFunction function = (ModelJavaFunction) node;
					if (function.getAnnotation() != null) {
						BEFunction ann = function.getAnnotation();
						String categoryName = function.getName().getNamespaceURI();
						s = FunctionHoverUtil.getDynamicToolTip(categoryName, ann);
					}else {
						s = Messages.getString("catalog.functionsView.functionhover.defaultText", function.getName().localName);
					}
				} else {
					ModelFunction function = (ModelFunction) node;
					s = function.inline();
				}
			}
		}
		if (s != null && !s.endsWith(".tooltip")) {
			return s;
		}
		if (s != null && s.endsWith(".tooltip")) {
			// the function was found, but the help text was not found
			return null;// "<html>Help Text Not Found</html>";
		}
		return "<html>Function Not Found</html>";
	}

	public static boolean isAnnotationPresent(Method method, String name) {
		for(Annotation ann:method.getAnnotations()) {
			if(ann.annotationType().getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	public static Annotation getAnnotation(Method method, String name) {
		for(Annotation ann:method.getAnnotations()) {
			if(ann.annotationType().getName().equals(name)) {
				return ann;
			}
		}
		return null;
	}

	private interface ParamDescriptor {

		public String getDescription();

		public String getName();

		public String getType();

	}

	private void setHoverLocation(Point position) {
		Rectangle displayBounds = fHoverShell.getDisplay().getBounds();
		Rectangle shellBounds = fHoverShell.getBounds();
		MAX_WIDTH = displayBounds.width;
		MAX_HEIGHT = displayBounds.height;
		if ((MAX_WIDTH - VIEWER_WIDTH - HOVER_WIDTH - MARGIN_WIDTH) < 0) {
			shellBounds.x = position.x + MARGIN_WIDTH + HOVER_WIDTH;
		} else {
			// shellBounds.x = MAX_WIDTH - VIEWER_WIDTH - HOVER_WIDTH;
			shellBounds.x = position.x;
			if (shellBounds.x + HOVER_WIDTH > MAX_WIDTH) {
				shellBounds.x = MAX_WIDTH - HOVER_WIDTH;
			}
		}

		if (position.y + HOVER_HEIGHT > MAX_HEIGHT) {
			shellBounds.x = MAX_WIDTH - VIEWER_WIDTH - HOVER_WIDTH;
			shellBounds.y = MAX_HEIGHT - HOVER_HEIGHT - MARGIN_HEIGHT;
		} else {
			shellBounds.y = position.y;
		}
		fHoverShell.setBounds(shellBounds);
	}

}