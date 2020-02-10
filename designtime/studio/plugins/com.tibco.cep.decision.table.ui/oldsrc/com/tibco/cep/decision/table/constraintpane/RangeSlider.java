package com.tibco.cep.decision.table.constraintpane;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


public class RangeSlider extends Composite {

	private enum RANGESLIDER_KNOB {
		NONE, UPPER, LOWER, MIDDLE
	};

	private int min;
	private int max;
	private int lowerValue;
	private int upperValue;
	private int oldUpperValue;
	private int oldLowerValue;
	private final List<SelectionListener> listeners;
	private RANGESLIDER_KNOB lastSelectedKnob;
	private boolean isDragInProgress;
	private boolean isLowerSelected;
	private boolean isUpperSelected;
	private boolean isMiddleSelected;
	private Rectangle knobUpperRect = null, knobMiddleRect = null, knobLowerRect = null; 
	private Rectangle scaleRect = null; 
	private final Image sliderKnobs;
	
	public RangeSlider(Composite parent, int style) {
		super(parent, SWT.DOUBLE_BUFFERED | ((style & SWT.BORDER) == SWT.BORDER ? SWT.BORDER : SWT.NONE));
		this.min = this.lowerValue = 0;
		this.max = this.upperValue = 100;
		this.listeners = new ArrayList<SelectionListener>();
		this.lastSelectedKnob = RANGESLIDER_KNOB.NONE;
		this.sliderKnobs = new Image(getDisplay(), this.getClass().getClassLoader().getResourceAsStream("icons/slider_knobs.gif"));

		addMouseListeners();
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(final PaintEvent e) {
				drawWidget(e);

			}
		});
		
		addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				RangeSlider.this.sliderKnobs.dispose();				
			}
		});		
	}

	/**
	 * Add the mouse listeners (mouse up, mouse down, mouse move, mouse wheel)
	 */
	private void addMouseListeners() {
		
		// handler for mouse move event
		addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(final Event e) {
				handleMouseMove(e);
			}
		});
		
		//handler for mouse up event
		addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(final Event e) {
				handleMouseUp(e);
			}
		});
		
		//handler for mouse Down event
		addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(final Event e) {
				handleMouseDown(e);
			}
		});
	}

	
	private boolean fireSelection(Event event) {
		for (final SelectionListener listener : this.listeners) {
			final SelectionEvent selectionEvent = new SelectionEvent(event);
			listener.widgetSelected(selectionEvent);
			if (!selectionEvent.doit) {
				return false;
			}
		}
		return true;
	}

	private void handleMouseDown(Event e) {
		if (this.isLowerSelected) {
			this.isDragInProgress = true;
			this.lastSelectedKnob = RANGESLIDER_KNOB.LOWER;
			this.oldLowerValue = this.lowerValue;
			return;
		}
		
		if (this.isUpperSelected) {
			this.isDragInProgress = true;
			this.lastSelectedKnob = RANGESLIDER_KNOB.UPPER;
			this.oldUpperValue = this.upperValue;
			return;
		}
		
		if (this.isMiddleSelected) {
			this.isDragInProgress = true;
			this.lastSelectedKnob = RANGESLIDER_KNOB.MIDDLE;
			this.oldUpperValue = this.upperValue;
			this.oldLowerValue = this.lowerValue;
			return;			
		}

		this.isDragInProgress = false;
		this.lastSelectedKnob = RANGESLIDER_KNOB.NONE;
	}

	
	private void handleMouseUp(Event e) {
		this.lastSelectedKnob = RANGESLIDER_KNOB.NONE;
		if (!this.isDragInProgress) {
			return;
		}
		this.isDragInProgress = false;
		if (!fireSelection(e)) {
			if (this.lastSelectedKnob == RANGESLIDER_KNOB.UPPER) {
				this.upperValue = this.oldUpperValue;
			} else if (this.lastSelectedKnob == RANGESLIDER_KNOB.LOWER) {
				this.lowerValue = this.oldLowerValue;
			} else if (this.lastSelectedKnob == RANGESLIDER_KNOB.MIDDLE) {
				this.lowerValue = this.oldLowerValue;
				this.upperValue = this.oldUpperValue;
			}
			redraw();
		}
	}
	
	private void handleMouseMove(Event e) {
		final int x = e.x, y = e.y;
		
		this.isUpperSelected = knobUpperRect.contains(e.x, e.y);
		this.isLowerSelected = knobLowerRect.contains(e.x, e.y);
		this.isMiddleSelected = knobMiddleRect.contains(e.x, e.y);
		
		if (this.isUpperSelected && (this.lastSelectedKnob == RANGESLIDER_KNOB.UPPER || this.lastSelectedKnob == RANGESLIDER_KNOB.NONE)) {
			setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_SIZEE));
		} else if (this.isLowerSelected && (this.lastSelectedKnob == RANGESLIDER_KNOB.LOWER || this.lastSelectedKnob == RANGESLIDER_KNOB.NONE)) {	
			setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_SIZEW));
		} else if (this.isMiddleSelected && (this.lastSelectedKnob == RANGESLIDER_KNOB.MIDDLE || this.lastSelectedKnob == RANGESLIDER_KNOB.NONE)) {
			setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_SIZEALL));
		} else if (!this.isDragInProgress) {
			setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW));
		}
		
		if (this.isDragInProgress) {
			final int mouseValue = (int) ((x - 9f) / computePixelSizeForSlider()) + this.min;
			if (this.lastSelectedKnob == RANGESLIDER_KNOB.LOWER) {
				this.lowerValue = (int) (Math.ceil(mouseValue));
				checkLowerValue();
			} else if (this.lastSelectedKnob == RANGESLIDER_KNOB.UPPER) {
				this.upperValue = (int) (Math.ceil(mouseValue));
				checkUpperValue();
			} else if (this.lastSelectedKnob == RANGESLIDER_KNOB.MIDDLE) {
				int tmpUpperValue = (int) (Math.ceil(mouseValue));
				int tmpLowerValue = this.lowerValue + (tmpUpperValue - this.upperValue);
				if (tmpLowerValue >= this.min && tmpUpperValue <= this.max) {
					this.upperValue = tmpUpperValue;					
					this.lowerValue = tmpLowerValue;
					checkUpperValue();
					checkLowerValue();
				}
			}
		}
		redraw();
	}

	
	
	private void checkLowerValue() {
		if (this.lowerValue > this.max) {
			this.lowerValue = this.max;
		}
		
		if (this.lowerValue < this.min) {
			this.lowerValue = this.min;
		}
		
		if (this.lowerValue > this.upperValue) {
			this.lowerValue = this.upperValue;
		}
	}

	
	private void checkUpperValue() {
		if (this.upperValue < this.min) {
			this.upperValue = this.min;
		}
		if (this.upperValue > this.max) {
			this.upperValue = this.max;
		}
		if (this.upperValue < this.lowerValue) {
			this.upperValue = this.lowerValue;
		}
	}

	
	private void drawWidget(PaintEvent e) {
		final Rectangle rectangle = this.getClientArea();
		if (rectangle.width == 0 || rectangle.height == 0) {
			return;
		}
		
		e.gc.setAntialias(SWT.ON);
		e.gc.setAdvanced(true);
		drawRangeSlider(e.gc);
	}

	
	private void drawRangeSlider(GC gc) {
		drawBackground(gc);
		drawBars(gc);
		drawKnob(gc, this.upperValue, true);
		drawKnob(gc, this.lowerValue, false);
	}

	
	private void drawBackground(GC gc) {
		final Rectangle cArea = this.getClientArea();

		gc.setBackground(getBackground());
		gc.fillRectangle(cArea);

		if (isEnabled()) {
			gc.setForeground(getForeground());
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}
		scaleRect = new Rectangle(9, 15 , cArea.width - 20, cArea.height - 27);
		gc.drawRoundRectangle(9, 15 , cArea.width - 20, cArea.height - 27, 3, 3);

		final float pixelSize = computePixelSizeForSlider();
		final int startX = (int) (pixelSize * (this.lowerValue - this.min));
		final int endX = (int) (pixelSize * (this.upperValue - this.min));
		if (isEnabled()) {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLUE));
		} else {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE));
		}
		gc.fillRectangle(10 + startX, 15, endX - startX , cArea.height - 27);
	}

	
	private float computePixelSizeForSlider() {		
		return (getClientArea().width - 20f) / (this.max - this.min);
	}

	
	private void drawBars(GC gc) {
		if (isEnabled()) {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		}

		final float pixelSize = computePixelSizeForSlider();
		for (int i = 1; i < 10; i++) {
			final int x = (int) (12 + pixelSize * (this.max - this.min) / 10 * i);
			gc.drawLine(x, scaleRect.y - 5, x, scaleRect.y - 2);
			gc.drawLine(x, scaleRect.y + scaleRect.height + 2, x, scaleRect.y + scaleRect.height + 5);
		}
	}

	
	private Point drawKnob(GC gc, int value, boolean upper) {
		final float pixelSize = computePixelSizeForSlider();
		int x = (int) (pixelSize * (value - this.min));
		if(x <= 0){
			x = getClientArea().x;
		}

		if (upper) {
			this.knobMiddleRect = new Rectangle(x + 5, scaleRect.y + scaleRect.height / 2 - 17, 9, 7);
			this.knobUpperRect = new Rectangle(x + 5, scaleRect.y + scaleRect.height / 2 - 10, 9, 10); 
			gc.drawImage(sliderKnobs, 0, 20, 9, 7, x + 5, scaleRect.y + scaleRect.height / 2 - 17, 9, 7);
			gc.drawImage(sliderKnobs, 0, 10, 9, 10, x + 5, scaleRect.y + scaleRect.height / 2 - 10, 9, 10);
		} else {
			this.knobLowerRect = new Rectangle(x + 5, scaleRect.y + scaleRect.height / 2 + 3, 9, 10);
			gc.drawImage(sliderKnobs, 0, 0, 9, 10, x + 5, scaleRect.y + scaleRect.height / 2 + 3, 9, 10);
		}
		return new Point(x + 5, scaleRect.y + scaleRect.height / 2);
	}

	
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		final int width, height;
		
		if (hHint < 30) {
			height = 30;
		} else {
			height = hHint;
		}
		
		if (wHint < 100) {
			width = 100;
		} else {
			width = wHint;
		}

		return new Point(width, height);
	}

	
	public int getLowerValue() {
		return this.lowerValue;
	}

	
	public int[] getSelection() {
		final int[] selection = new int[2];
		selection[0] = this.lowerValue;
		selection[1] = this.upperValue;
		return selection;
	}

	
	public int getUpperValue() {
		return this.upperValue;
	}

	
	public void removeSelectionListener(SelectionListener listener) {
		this.listeners.remove(listener);
	}

	
	public void setLowerValue(int value) {
		if (this.min <= value && value <= this.max && value <= this.upperValue) {
			this.lowerValue = value;
		}
		redraw();
	}

	
	public void setMaximum(int value) {
		if (this.min <= value) {
			this.max = value;
			if (this.lowerValue >= this.max) {
				this.lowerValue = this.max;
			}
			if (this.upperValue >= this.max) {
				this.upperValue = this.max;
			}
		}
		redraw();
	}

	
	public void setMinimum(int value) {
		if (this.max >= value) {
			this.min = value;
			if (this.lowerValue <= this.min) {
				this.lowerValue = this.min;
			}
			if (this.upperValue <= this.min) {
				this.upperValue = this.min;
			}
		}
		redraw();
	}
	
	public void setUpperValue(int value) {
		if (this.min <= value && value <= this.max && value >= this.lowerValue) {
			this.upperValue = value;
		}
		redraw();
	}
}
