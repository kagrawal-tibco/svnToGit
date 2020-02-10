package com.tibco.cep.studio.ui.viewers;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.CommonViewer;


/**
 * A PopupList is a list of selectable items that appears in its own shell
 * positioned above its parent shell. It is used for selecting items when
 * editing a Table cell (similar to the list that appears when you open a Combo
 * box).
 * 
 * The list will be positioned so that it does not run off the screen and the
 * largest number of items are visible. It may appear above the current cursor
 * location or below it depending how close you are to the edge of the screen.
 * 
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further
 *      information</a>
 */
public class PopupTreeViewer {
	public static final String PROJECT_EXPLORER_ID = "com.tibco.cep.studio.projectexplorer.view";
	Shell shell;
	TreeViewer treeViewer;
	int minimumWidth;

	/**
	 * Creates a PopupList above the specified shell.
	 * 
	 * @param parent
	 *            a Shell control which will be the parent of the new instance
	 *            (cannot be null)
	 */
	public PopupTreeViewer(Shell parent) {
		this(parent, 0);
	}
	
	public PopupTreeViewer(Shell parent,int style) {
		shell = new Shell(parent, checkStyle(style));
		initViewer(new CommonViewer(PROJECT_EXPLORER_ID, shell, SWT.SINGLE
				| SWT.V_SCROLL | SWT.H_SCROLL));
	}
	
	/**
	 * @param parent
	 * @param labelProvider
	 * @param contentProvider
	 * @param style
	 */
	public PopupTreeViewer(Shell parent, 
								IBaseLabelProvider labelProvider,
								ITreeContentProvider contentProvider, 
								int style) {
		shell = new Shell(parent, checkStyle(style));
		final TreeViewer v = new TreeViewer(shell);
		v.setLabelProvider(labelProvider);
		v.setContentProvider(contentProvider);

		initViewer(v);
		
	}

	/**
	 * Creates a PopupList above the specified shell.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot
	 *            be null)
	 * @param style
	 *            the style of widget to construct
	 * 
	 * @since 3.0
	 */
	public void initViewer(TreeViewer viewer) {
		

		// treeViewer = new TreeViewer(shell, SWT.SINGLE | SWT.V_SCROLL |
		// SWT.H_SCROLL);
		 this.treeViewer = viewer;

		// close dialog if user selects outside of the shell
		shell.addListener(SWT.Deactivate, new Listener() {
			public void handleEvent(Event e) {
				shell.setVisible(false);
			}
		});

		// resize shell when list resizes
		shell.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e) {
			}

			public void controlResized(ControlEvent e) {
				Rectangle shellSize = shell.getClientArea();
				treeViewer.getTree().setSize(shellSize.width, shellSize.height);
			}
		});

		// return list selection on Mouse Up or Carriage Return
		treeViewer.getTree().addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
				shell.setVisible(false);
			}
		});
		treeViewer.getTree().addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.character == '\r') {
					shell.setVisible(false);
				}
			}
		});

	}

	private static int checkStyle(int style) {
		int mask = SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT;
		return style & mask;
	}

	/**
	 * Gets the widget font.
	 * <p>
	 * 
	 * @return the widget font
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li> <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                called from the thread that created the receiver</li>
	 *                </ul>
	 */
	public Font getFont() {
		return treeViewer.getTree().getFont();
	}

	public Object[] getVisibleExpandedElements() {
		return treeViewer.getVisibleExpandedElements();
	}

	public Object[] getExpandedElements() {
		return treeViewer.getExpandedElements();
	}

	/**
	 * Gets the minimum width of the list.
	 * 
	 * @return the minimum width of the list
	 */
	public int getMinimumWidth() {
		return minimumWidth;
	}

	/**
	 * Launches the Popup List, waits for an item to be selected and then closes
	 * the PopupList.
	 * 
	 * @param rect
	 *            the initial size and location of the PopupList; the dialog
	 *            will be positioned so that it does not run off the screen and
	 *            the largest number of items are visible
	 * 
	 * @return the text of the selected item or null if no item is selected
	 */
	public ISelection open(Rectangle rect) {

		Point listSize = treeViewer.getTree().computeSize(rect.width,
				SWT.DEFAULT, false);
		Rectangle screenSize = shell.getDisplay().getBounds();

		// Position the dialog so that it does not run off the screen and the
		// largest number of items are visible
		int spaceBelow = screenSize.height - (rect.y + rect.height) - 30;
		int spaceAbove = rect.y - 30;

		int y = 0;
		if (spaceAbove > spaceBelow && listSize.y > spaceBelow) {
			// place popup list above table cell
			if (listSize.y > spaceAbove) {
				listSize.y = spaceAbove;
			} else {
				listSize.y += 2;
			}
			y = rect.y - listSize.y;

		} else {
			// place popup list below table cell
			if (listSize.y > spaceBelow) {
				listSize.y = spaceBelow;
			} else {
				listSize.y += 2;
			}
			y = rect.y + rect.height;
		}

		// Make dialog as wide as the cell
		listSize.x = rect.width;
		// dialog width should not be less than minimumWidth
		if (listSize.x < minimumWidth)
			listSize.x = minimumWidth;

		// Align right side of dialog with right side of cell
		int x = rect.x + rect.width - listSize.x;

		shell.setBounds(x, y, listSize.x, listSize.y);

		shell.open();
		treeViewer.getTree().setFocus();
		Display display = shell.getDisplay();
		while (!shell.isDisposed() && shell.isVisible()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		ISelection result = null;
		if (!shell.isDisposed()) {
			result = treeViewer.getSelection();
			shell.dispose();
		}
		return result;
	}

	/**
	 * Selects an item with text that starts with specified String.
	 * <p>
	 * If the item is not currently selected, it is selected. If the item at an
	 * index is selected, it remains selected. If the string is not matched, it
	 * is ignored.
	 * 
	 * @param string
	 *            the text of the item
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li> <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                called from the thread that created the receiver</li>
	 *                </ul>
	 */
	public void select(ISelection selection, boolean reveal) {
		treeViewer.setSelection(selection, reveal);
	}

	/**
	 * Sets the widget font.
	 * <p>
	 * When new font is null, the font reverts to the default system font for
	 * the widget.
	 * 
	 * @param font
	 *            the new font (or null)
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li> <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                called from the thread that created the receiver</li>
	 *                </ul>
	 */
	public void setFont(Font font) {
		treeViewer.getTree().setFont(font);
	}

	public void setInput(Object input) {
		treeViewer.setInput(input);
	}

	/**
	 * Sets the minimum width of the list.
	 * 
	 * @param width
	 *            the minimum width of the list
	 */
	public void setMinimumWidth(int width) {
		if (width < 0)
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);

		minimumWidth = width;
	}

	public void setContentProvider(
			IContentProvider  contentProvider) {
		treeViewer.setContentProvider(contentProvider);

	}
	
	public void setViewerFilter(ViewerFilter[] filters) {
		treeViewer.setFilters(filters);
	}
	
	public void expandAll() {
		treeViewer.expandAll();
	}
}
