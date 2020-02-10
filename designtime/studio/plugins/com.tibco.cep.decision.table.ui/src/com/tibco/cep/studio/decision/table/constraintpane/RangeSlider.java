package com.tibco.cep.studio.decision.table.constraintpane;

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

	private Object min;
	private Object max;
	private Object lowerValue;
	private Object upperValue;
	private Object oldUpperValue;
	private Object oldLowerValue;
	private final List<SelectionListener> listeners;
	private RANGESLIDER_KNOB lastSelectedKnob;
	private boolean isDragInProgress;
	private boolean isLowerSelected;
	private boolean isUpperSelected;
	private boolean isMiddleSelected;
	private Rectangle knobUpperRect = null, knobMiddleRect = null, knobLowerRect = null; 
	private Rectangle scaleRect = null; 
	private final Image sliderKnobs;
	
	public RangeSlider(Composite parent, int style, Object[] range) {
		super(parent, SWT.DOUBLE_BUFFERED | ((style & SWT.BORDER) == SWT.BORDER ? SWT.BORDER : SWT.NONE));
		if(range[0] instanceof Integer && range[1] instanceof Integer){
			this.min = this.lowerValue = 0;
			this.max = this.upperValue = 100;
		}else if(range[0] instanceof Long && range[1] instanceof Long){
			this.min = this.lowerValue = 0L;
			this.max = this.upperValue = 100L;
		}else if(range[0] instanceof Double && range[1] instanceof Double){
			this.min = this.lowerValue = 0d;
			this.max = this.upperValue = 100d;
		}
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
			if(this.min instanceof Double && this.max instanceof Double){
				   double mouseValue = (double) ((x - 9f) / computePixelSizeForSlider()) + (double)this.min;
				   mouseValue = mouseValue * 100;
				   mouseValue = Math.round(mouseValue);
				   mouseValue = mouseValue/100;
				
				if (this.lastSelectedKnob == RANGESLIDER_KNOB.LOWER) {
					this.lowerValue = mouseValue;
					checkLowerValue();
				} else if (this.lastSelectedKnob == RANGESLIDER_KNOB.UPPER) {
					this.upperValue = mouseValue;
					checkUpperValue();
				} else if (this.lastSelectedKnob == RANGESLIDER_KNOB.MIDDLE) {
					double tmpUpperValue = mouseValue;
					double tmpLowerValue = (double)this.lowerValue + (tmpUpperValue - (double)this.upperValue);
					if (tmpLowerValue >= (double)this.min && tmpUpperValue <= (double)this.max) {
						this.upperValue = tmpUpperValue;					
						this.lowerValue = tmpLowerValue;
						checkUpperValue();
						checkLowerValue();
					}
				}
			}else{
				final long mouseValue = (long) ((x - 9f) / computePixelSizeForSlider()) + (long)this.min;
				if (this.lastSelectedKnob == RANGESLIDER_KNOB.LOWER) {
					this.lowerValue = (long) (Math.ceil(mouseValue));
					checkLowerValue();
				} else if (this.lastSelectedKnob == RANGESLIDER_KNOB.UPPER) {
					this.upperValue = (long) (Math.ceil(mouseValue));
					checkUpperValue();
				} else if (this.lastSelectedKnob == RANGESLIDER_KNOB.MIDDLE) {
					long tmpUpperValue = (long) (Math.ceil(mouseValue));
					long tmpLowerValue = (long)this.lowerValue + (tmpUpperValue - (long)this.upperValue);
					if (tmpLowerValue >= (long)this.min && tmpUpperValue <= (long)this.max) {
						this.upperValue = tmpUpperValue;					
						this.lowerValue = tmpLowerValue;
						checkUpperValue();
						checkLowerValue();
					}
				}
			}
		}
		redraw();
	}

	
	
	private void checkLowerValue() {
		if(lowerValue instanceof Integer){
			if ((int)this.lowerValue > (int)this.max) {
				this.lowerValue = this.max;
			}

			if ((int)this.lowerValue < (int)this.min) {
				this.lowerValue = this.min;
			}

			if ((int)this.lowerValue > (int)this.upperValue) {
				this.lowerValue = this.upperValue;
			}
		}else if(lowerValue instanceof Long){
			if ((long)this.lowerValue > (long)this.max) {
				this.lowerValue = this.max;
			}

			if ((long)this.lowerValue < (long)this.min) {
				this.lowerValue = this.min;
			}

			if ((long)this.lowerValue > (long)this.upperValue) {
				this.lowerValue = this.upperValue;
			}
		}else if(lowerValue instanceof Double){
			if ((double)this.lowerValue > (double)this.max) {
				this.lowerValue = this.max;
			}

			if ((double)this.lowerValue < (double)this.min) {
				this.lowerValue = this.min;
			}

			if ((double)this.lowerValue > (double)this.upperValue) {
				this.lowerValue = this.upperValue;
			}
		}
	}

	
	private void checkUpperValue() {
		if(upperValue instanceof Integer){
			if ((int)this.upperValue < (int)this.min) {
				this.upperValue = this.min;
			}
			if ((int)this.upperValue > (int)this.max) {
				this.upperValue = this.max;
			}
			if ((int)this.upperValue < (int)this.lowerValue) {
				this.upperValue = this.lowerValue;
			}
		}else if(upperValue instanceof Long){
			if ((long)this.upperValue < (long)this.min) {
				this.upperValue = this.min;
			}
			if ((long)this.upperValue > (long)this.max) {
				this.upperValue = this.max;
			}
			if ((long)this.upperValue < (long)this.lowerValue) {
				this.upperValue = this.lowerValue;
			}
		}else if(upperValue instanceof Double){
			if ((double)this.upperValue < (double)this.min) {
				this.upperValue = this.min;
			}
			if ((double)this.upperValue > (double)this.max) {
				this.upperValue = this.max;
			}
			if ((double)this.upperValue < (double)this.lowerValue) {
				this.upperValue = this.lowerValue;
			}
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
		if(this.upperValue instanceof Double && this.lowerValue instanceof Double){
			drawKnob(gc, Math.round((double)this.upperValue), true);
			drawKnob(gc, Math.round((double)this.lowerValue), false);
		}else{
			drawKnob(gc, (long)this.upperValue, true);
			drawKnob(gc, (long)this.lowerValue, false);
		}
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
		
		if (isEnabled()) {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLUE));
		} else {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE));
		}
		if(this.max instanceof Double && this.min instanceof Double){
			final int startXnew = (int) (pixelSize * (Math.round((double)this.lowerValue) - Math.round((double)this.min)));
			final int endXnew = (int) (pixelSize * (Math.round((double)this.upperValue) - Math.round((double)this.min)));
			gc.fillRectangle(10 + startXnew, 15, endXnew - startXnew , cArea.height - 27);
		}else{
			final int startX = (int) (pixelSize * ((long)this.lowerValue - (long)this.min));
			final int endX = (int) (pixelSize * ((long)this.upperValue - (long)this.min));
			gc.fillRectangle(10 + startX, 15, endX - startX , cArea.height - 27);
		}
		
	}

	
	private float computePixelSizeForSlider() {
		if(this.max instanceof Double && this.min instanceof Double){
			long minnew = Math.round((double)this.min);
			long maxnew = Math.round((double)this.max);
			return (getClientArea().width - 20f) / (maxnew - minnew);
		}else{
			return (getClientArea().width - 20f) / ((long)this.max - (long)this.min);
		}
	}

	
	private void drawBars(GC gc) {
		if (isEnabled()) {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		}

		final float pixelSize = computePixelSizeForSlider();
		if(this.min instanceof Double && this.max instanceof Double){
			for (int i = 1; i < 10; i++) {
				final int x = (int) (12 + pixelSize * (Math.round((double)this.max) - Math.round((double)this.min)) / 10 * i);
				gc.drawLine(x, scaleRect.y - 5, x, scaleRect.y - 2);
				gc.drawLine(x, scaleRect.y + scaleRect.height + 2, x, scaleRect.y + scaleRect.height + 5);
			}
		}else{
			for (int i = 1; i < 10; i++) {
				final int x = (int) (12 + pixelSize * ((long)this.max - (long)this.min) / 10 * i);
				gc.drawLine(x, scaleRect.y - 5, x, scaleRect.y - 2);
				gc.drawLine(x, scaleRect.y + scaleRect.height + 2, x, scaleRect.y + scaleRect.height + 5);
			}
		}
	}

	
	private Point drawKnob(GC gc, long value, boolean upper) {
		final float pixelSize = computePixelSizeForSlider();
		int x;
		if(this.min instanceof Double){
			x = (int) (pixelSize * (value - Math.round((double)this.min)));
		}else{
			x = (int) (pixelSize * (value - (long)this.min));
		}
		
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

	
	public Object getLowerValue() {
		return this.lowerValue;
	}

	
	public Object[] getSelection() {
		final Object[] selection = new Object[2];
		selection[0] = this.lowerValue;
		selection[1] = this.upperValue;
		return selection;
	}

	
	public Object getUpperValue() {
		return this.upperValue;
	}

	
	public void removeSelectionListener(SelectionListener listener) {
		this.listeners.remove(listener);
	}

	
	public void setLowerValue(Object value) {
		if(value instanceof Integer){
			if ((int)this.min <= (int)value && (int)value <= (int)this.max && (int)value <= (int)this.upperValue) {
				this.lowerValue = value;
			}
		}else if(value instanceof Long){
			if ((long)this.min <= (long)value && (long)value <= (long)this.max && (long)value <= (long)this.upperValue) {
				this.lowerValue = value;
			}
		}else if(value instanceof Double){
			if ((double)this.min <= (double)value && (double)value <= (double)this.max && (double)value <= (double)this.upperValue) {
				this.lowerValue = value;
			}
		}
		redraw();
	}

	
	public void setMaximum(Object value) {
		if(value instanceof Integer){
			if ((int)this.min <= (int)value) {
				this.max = value;
				if ((int)this.lowerValue >= (int)this.max) {
					this.lowerValue = this.max;
				}
				if ((int)this.upperValue >= (int)this.max) {
					this.upperValue = this.max;
				}
			}
		}else if(value instanceof Long){
			if ((long)this.min <= (long)value) {
				this.max = value;
				if ((long)this.lowerValue >= (long)this.max) {
					this.lowerValue = this.max;
				}
				if ((long)this.upperValue >= (long)this.max) {
					this.upperValue = this.max;
				}
			}
		}else if(value instanceof Double){
			if ((double)this.min <= (double)value) {
				this.max = value;
				if ((double)this.lowerValue >= (double)this.max) {
					this.lowerValue = this.max;
				}
				if ((double)this.upperValue >= (double)this.max) {
					this.upperValue = this.max;
				}
			}
		}
		redraw();
	}

	
	public void setMinimum(Object value) {
		if(value instanceof Integer){
			if ((int)this.max >= (int)value) {
				this.min = value;
				if ((int)this.lowerValue <= (int)this.min) {
					this.lowerValue = this.min;
				}
				if ((int)this.upperValue <= (int)this.min) {
					this.upperValue = this.min;
				}
			}
		}else if(value instanceof Long){
			if ((long)this.max >= (long)value) {
				this.min = value;
				if ((long)this.lowerValue <= (long)this.min) {
					this.lowerValue = this.min;
				}
				if ((long)this.upperValue <= (long)this.min) {
					this.upperValue = this.min;
				}
			}
		}else if(value instanceof Double){
			if ((double)this.max >= (double)value) {
				this.min = value;
				if ((double)this.lowerValue <= (double)this.min) {
					this.lowerValue = this.min;
				}
				if ((double)this.upperValue <= (double)this.min) {
					this.upperValue = this.min;
				}
			}
		}
		redraw();
	}
	
	public void setUpperValue(Object value) {
		if(value instanceof Integer){
			if ((int)this.min <= (int)value && (int)value <= (int)this.max && (int)value >= (int)this.lowerValue) {
				this.upperValue = value;
			}
		}else if(value instanceof Long){
			if ((long)this.min <= (long)value && (long)value <= (long)this.max && (long)value >= (long)this.lowerValue) {
				this.upperValue = value;
			}
		}else if(value instanceof Double){
			if ((double)this.min <= (double)value && (double)value <= (double)this.max && (double)value >= (double)this.lowerValue) {
				this.upperValue = value;
			}
		}
		redraw();
	}
}
