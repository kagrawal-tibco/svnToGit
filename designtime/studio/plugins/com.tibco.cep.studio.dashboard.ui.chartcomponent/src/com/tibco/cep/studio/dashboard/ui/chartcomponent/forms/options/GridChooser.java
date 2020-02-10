package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * <code>GridChooser</code> divides a region of space into cells of size ({@link #CELL_SIZE},
 * {@link #CELL_SIZE}). For a 3x3 grid the dimensions of the
 * <code>GridChooser</code> should be set to (30,30).
 * <code>ISelectionListener</code>s may be registered to receive a
 * notification whenever the selected cell changes.
 * 
 * @author dchesney
 * 
 */
public class GridChooser extends Canvas implements ISelectionProvider{

	/**
	 * Size of a cell (value: 10).
	 */
	public final static int CELL_SIZE = 15;
	private Display _display;
	/**
	 * The selected cell which may be <code>null</code>.
	 */
	private Point _selectedCell;
	/**
	 * All registered <code>SelectionListener</code>s.
	 */
	private List<ISelectionChangedListener> _selectionListeners;
	
	public GridChooser(Composite parent, int style) {
		super(parent, SWT.BORDER | SWT.NO_BACKGROUND |
				SWT.NO_REDRAW_RESIZE);
		_display = Display.getCurrent();
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent event) {
				onPaint(event);
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				setSelection(findCell(e.x, e.y));
			}
		});
		_selectionListeners = new ArrayList<ISelectionChangedListener>();
		setSelection(findCell(1,1));
	}
	/**
	 * Description : Method to find the cell point based on the X,Y coordinates
	 * @param x X Coordinate
	 * @param y Y Coordinate
	 * @return
	 */
	public Point findCell(int x, int y) {
		int cellX = x / CELL_SIZE;
		int cellY = y / CELL_SIZE;
		return new Point(cellX, cellY);
	}
	/**
	 * Description : Method to select the cell based on the Point
	 * @param cell
	 */
	public void setSelection(Point cell) {
		_selectedCell = cell;
		redraw();
		fireSelectionChanged();		
	}
	
	protected void onPaint(PaintEvent event) {
		Rectangle rect = getClientArea();
		GC gc = event.gc;
		// clear
		gc.fillRectangle(rect);
		if (_selectedCell != null) {
			// paint all cells where x < _selectedCell.x and y < _selectedCell.y
			Color bground = gc.getBackground();
			gc.setBackground(_display.getSystemColor(SWT.COLOR_LIST_SELECTION));
			gc.fillRectangle(
					0,
					0,
					CELL_SIZE * (_selectedCell.x + 1),
					CELL_SIZE * (_selectedCell.y + 1));
			// restore background
			gc.setBackground(bground);
		}
		gc.setForeground(_display.getSystemColor(SWT.COLOR_BLACK));
		// vertical lines
		for (int x=CELL_SIZE; x<rect.width-CELL_SIZE+1; x += CELL_SIZE) {
			gc.drawLine(x, 0, x, rect.height);
		}
		// horizontal lines
		for (int y=CELL_SIZE; y<rect.height-CELL_SIZE+1; y += CELL_SIZE) {
			gc.drawLine(0, y, rect.width, y);
		}
	}
	
	/**
	 * Delivers <code>event</code> to all registered
	 * <code>SelectionListener</code>s. The data of the delivered
	 * <code>Event</code> identifies the cell that is selected.
	 * 
	 */
	protected void fireSelectionChanged() {
        final SelectionChangedEvent e = new SelectionChangedEvent(this, new StructuredSelection(_selectedCell));
        Object[] listenersArray = _selectionListeners.toArray();
        for (int i = 0; i < listenersArray.length; ++i) {
            final ISelectionChangedListener l = (ISelectionChangedListener) listenersArray[i];
            SafeRunnable.run(new SafeRunnable() {
                public void run() {
                    l.selectionChanged(e);
                }
            });
        }
    }

    /**
	 * Registers <code>listener</code> to receive <code>SelectionEvent</code>s.
	 * 
	 * @param listener <code>ISelectionChangedListener</code> to register
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (listener != null &&
				_selectionListeners.contains(listener) == false) {
			_selectionListeners.add(listener);
		}
	}
	public ISelection getSelection() {
		return new StructuredSelection(_selectedCell);
	}

	/**
	 * Unregisters <code>listener</code>.
	 * 
	 * @param listener <code>ISelectionChangedListener</code> to unregister
	 */
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		if (listener != null) {
			_selectionListeners.remove(listener);
		}
	}
	public void setSelection(ISelection selection) {
		if (selection != null) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			setSelection((Point) structuredSelection.getFirstElement());
		}
	}

}