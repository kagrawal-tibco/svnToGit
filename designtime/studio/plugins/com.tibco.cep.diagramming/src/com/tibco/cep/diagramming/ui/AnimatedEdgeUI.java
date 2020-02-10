package com.tibco.cep.diagramming.ui;


import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;

import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tomsawyer.canvas.swing.TSBaseSwingCanvas;
import com.tomsawyer.drawing.events.TSBaseDrawingChangeListener;
import com.tomsawyer.drawing.events.TSDrawingChangeEvent;
import com.tomsawyer.drawing.geometry.shared.TSPoint;
import com.tomsawyer.graph.events.TSBaseGraphChangeListener;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.events.TSEAnyChangeAdapter;
import com.tomsawyer.graphicaldrawing.events.TSEEventManager;
import com.tomsawyer.graphicaldrawing.events.TSEViewportChangeEvent;
import com.tomsawyer.graphicaldrawing.events.TSEViewportChangeListener;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;
import com.tomsawyer.interactive.swing.editing.tool.TSEReconnectEdgeTool;
import com.tomsawyer.util.events.TSEvent;


/**
 * @author ggrigore
 * 
 * This class defines an animated dashed edge UI whose pattern is
 * constantly "crawling" towards the edge's target node.
 */
public class AnimatedEdgeUI extends TSEPolylineEdgeUI implements ActionListener,
		MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8501594346179058365L;

	/**
	 * This method resets the properties of this UI object to their
	 * default values. Overriding classes must call the parent's
	 * implementation of this method.  This is called by the
	 * <code>TSEObjectUI</code> constructor and by <code>clone
	 * </code>.
	 */
	public void reset()
	{
		// first initialize parent's properties

		super.reset();

		// now initialize additional properties
		
		// consider the mouse cursor not over the source node.

		this.mouseIsIn = false;
		this.listening = false;
		
		// start by cycling the phase slowly.

		this.phaseStep = AnimatedEdgeUI.SLOW_STEP;
		
		this.eventListener = new AnimatedEdgeEventListener();
		this.parentCanvas = null;
	}


	/**
	 * This method draws the object represented by this UI.
	 * It is extended to initialize the UI's animation system if needed.
	 * @param graphics the <code>TSEGraphics</code> object onto which
	 * the UI is being drawn.
	 */
	public void draw(TSEGraphics graphics)
	{
		if (graphics.getCanvas() instanceof TSBaseSwingCanvas)
		{
			TSBaseSwingCanvas canvas =
				(TSBaseSwingCanvas) graphics.getCanvas();
		
			// if we haven't initialized parentCanvas yet, do it now if
			// possible.

			if ((this.parentCanvas == null) &&
				(!canvas.isOverview()) &&
				(canvas instanceof DrawingCanvas))
			{
				this.parentCanvas = (DrawingCanvas) canvas;
			}

			// if we're being drawn, we should always be listening for
			// events. This will set us up to start paying attention to the
			// mouse and to graph change events.

			this.setListening(true);
		}
		
		// do the actual draw.

		super.draw(graphics);
	}
	
	
	/**
	 * This method draws the object represented by this UI in a selected
	 * state. It is extended to initialize the UI's animation system if
	 * needed.
	 * @param graphics the <code>TSEGraphics</code> object onto which
	 * the UI is being drawn.
	 */
	public void drawSelected(TSEGraphics graphics) {
		if (graphics.getCanvas() instanceof TSBaseSwingCanvas)	{
			TSBaseSwingCanvas canvas = (TSBaseSwingCanvas) graphics.getCanvas();
	
			// if we haven't initialized parentCanvas yet, do it now if possible.
			if ((this.parentCanvas == null) &&
				(!canvas.isOverview()) &&
				(canvas instanceof DrawingCanvas)) {
				this.parentCanvas = (DrawingCanvas) canvas;
			}

			// if we're being drawn, we should always be listening for
			// events. This will set us up to start paying attention to the
			// mouse and to graph change events.
			this.setListening(true);
		}

		super.drawSelected(graphics);
	}
	
	
	/**
	 * This method adds or removes the UI as a
	 * <code>MouseMotionListener</code> from the swing canvas.
	 * Being a <code>MouseMotionListener</code> allows us to monitor
	 * the mouse position and respond accordingly.  We also register
	 * or unregister the event listener that enables us to respond to
	 * changes in the graph that affect the behavior of the UI.
	 */
	protected void setListening(boolean listening)
	{
		// if we're already in the state requested or we
		// don't know about the swing canvas yet, do nothing.
		// Paying attention to the current state prevents the UI
		// from being added to the listener lists twice, which would
		// cause duplicate messages to be received.
		
		if ((this.listening != listening) && (this.parentCanvas != null))
		{
			// remember the state
			
			this.listening = listening;

			// if we're to start listening, add the UI as a listener.

			if (listening) {
				// listen to the animation timer.
				this.parentCanvas.addTimerListener(this);

				// listen to the mouse.
				this.parentCanvas.getInnerCanvas().addMouseMotionListener(this);

				// listen for graph changes.
				this.registerListeners();
			}
			
			// if we're to stop listening, remove the UI as a listener.
			else {
				// ignore the animation timer.
				this.parentCanvas.removeTimerListener(this);

				// ignore the mouse.
				this.parentCanvas.getInnerCanvas().removeMouseMotionListener(this);

				// ignore graph changes.
				this.unregisterListeners();
			}
		}
	}


	/**
	 * This is the timer callback, called every time the canvas's timer
	 * fires and the UI is listening.
	 */
	public void actionPerformed(ActionEvent event)
	{
		// check to see if the event is actually the canvas's timer.

		if ((this.parentCanvas != null) &&
			(this.parentCanvas.isTimerEvent(event)) &&
			!((this.parentCanvas.getToolManager().getActiveTool() instanceof
				TSEReconnectEdgeTool) &&
				((TSEReconnectEdgeTool)
					this.parentCanvas.getToolManager().getActiveTool()).
						isBuildingEdge()))
		{
			float phase = this.getDashPhase();
			
			phase += this.phaseStep;

			if (phase > 1)
			{
				phase -= 1;
			}
			
			else if (phase < 0)
			{
				phase += 1;
			}

			this.setDashPhase(phase);

			// make sure the edgeUI is redrawn on the next timer
			// tick since it's changed.
			TSEGraph ownerGraph = (TSEGraph) this.getOwnerEdge().getOwnerGraph();
			
			if (ownerGraph.getOwnerGraphManager() == this.parentCanvas.getGraphManager()) {
				this.invalidateWorldBounds();
			}
		}
	}

	
	/**
	 * This method determines if the mouse has entered or exited the
	 * source node UI. If entry or exit is detected, the corresponding
	 * method (<code>onMouseEnter</code> or
	 * <code>onMouseExit</code>) is called.
	 */
	protected void updateMouse()
	{
		TSPoint point = new TSPoint(
			this.parentCanvas.getWorldPoint(new Point(this.mouseX,
				this.mouseY)));
		
		TSENode source = (TSENode) this.getOwnerEdge().getSourceNode();
		
		if (source != null)
		{
			TSEGraph transformedGraph =
				(TSEGraph) source.getTransformGraph();
		
			// transform the point to the local coordinate system of
			// the source node. Then, do local calculations.
			
			transformedGraph.inverseExpandedTransformPoint(point);
		
			boolean isMouseInsideSource;
			
			// if the source node is expanded, then the node's frame
			// determines whether or not the point is inside.
			// Otherwise, the node's bounds do.
			
			if (source.isExpanded())
			{
				isMouseInsideSource =
					source.locallyIntersectsNodeFrame(point);
			}
			else
			{
				isMouseInsideSource = source.locallyContains(point);
			}
			
			// mouse entered?

			if ((!this.mouseIsIn) && isMouseInsideSource)
			{
				// remember that it's in so we don't call onMouseEnter()
				// more than once.
				
				this.mouseIsIn = true;
				this.onMouseEnter();
			}
			
			// mouse exited?
			else if ((this.mouseIsIn) && !isMouseInsideSource)
			{
				// remember that it's out so we don't call onMouseExit()
				// more than once.
				
				this.mouseIsIn = false;
				this.onMouseExit();
			}
		}
	}


	/**
	 * This method is called whenever the mouse enters the edge's
	 * source node.
	*/
	public void onMouseEnter()
	{
		// save the old line width and make the line 3x wider.

		this.widthMultiplier = 3;
		
		this.mouseIsIn = true;
		
		// animate quickly.

		this.phaseStep = AnimatedEdgeUI.FAST_STEP;
	}
	
	
	/**
	 * This method is called whenever the mouse leaves the edge's
	 * source node.
	 */
	public void onMouseExit()
	{
		// restore the old line width.

		this.widthMultiplier = 1;
		
		this.mouseIsIn = false;

		// animate slowly.

		this.phaseStep = AnimatedEdgeUI.SLOW_STEP;

		// since we just changed the line width, redraw.

		TSEGraph ownerGraph =
			(TSEGraph) this.getOwnerEdge().getOwnerGraph();
			
		if (ownerGraph.getOwnerGraphManager()
			== this.parentCanvas.getGraphManager())
		{
			this.invalidateWorldBounds();
		}
	}
	

	/**
	 * This method is called whenever the mouse is moved and the
	 * UI is listening. This remembers the mouse position and
	 * calls <code>updateMouse</code>.
	 */
	public void mouseMoved(MouseEvent event)
	{
		this.processMouseMotion(event);
	}


	/**
	 * This method is called whenever the mouse is dragged and the
	 * UI is listening. This remembers the mouse position and
	 * calls <code>updateMouse</code>.
	 */
	public void mouseDragged(MouseEvent event)
	{
		this.processMouseMotion(event);
	}
	
	
	/**
	 * This remembers the mouse position and calls <code>updateMouse</code>.
	 */
	public void processMouseMotion(MouseEvent event)
	{
		// remember the mouse position.

		this.mouseX = event.getX();
		this.mouseY = event.getY();

		// check to see if we need to call any onMouseXxx... methods.

		this.updateMouse();

		// consume the event so we don't get it again.

		event.consume();
	}
	
	
	/**
	 * This method overrides the base class method. It casts the
	 * graphics object to a <code>Graphics2D</code> object if possible
	 * and customizes and returns it.
	 * @param graphics the <code>TSEGraphics</code> object which
	 * is being converted.
	 */
	public TSEGraphics convertGraphics(TSEGraphics graphics)
	{
		TSEGraphics newGraphics = super.convertGraphics(graphics);
		
		if (this.mouseIsIn)
		{
			BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
			
			Stroke newStroke = new BasicStroke(
				oldStroke.getLineWidth() * this.widthMultiplier,
				oldStroke.getEndCap(),
				oldStroke.getLineJoin(),
				oldStroke.getMiterLimit(),
				oldStroke.getDashArray(),
				oldStroke.getDashPhase());
				
			graphics.setStroke(newStroke);
		}
		
		return newGraphics;
	}


	/**
	 * This method invalidates the area that this UI occupies
	 * in the swing canvas.
	 */
	public void invalidateWorldBounds()
	{
		// figure out the pixel coordinates of the invalid
		// rectangle
		
		TSEEdge ownerEdge = this.getOwnerEdge();
		
		if (ownerEdge.isViewable())
		{
			this.parentCanvas.addInvalidRegion(ownerEdge);
						
			// tell the parent canvas it should do an update next tick.
		
			this.parentCanvas.requestUpdateOnTimer();
		}
		else
		{
			// if owner of this UI is not viewable, then no need to listen
			// it any more.
			
			this.setListening(false);
		}
	}

	
	/**
	 * This method registers this class as a listener to appropriate events.
	 */
	public void registerListeners()
	{
		TSEGraphManager graphManager =
			this.parentCanvas.getGraphManager();
		TSEEventManager eventManager = (TSEEventManager)
			graphManager.getEventManager();
		
		eventManager.addGraphChangeListener(graphManager, (TSBaseGraphChangeListener) this.eventListener);
			
		eventManager.addDrawingChangeListener(
			graphManager,(TSBaseDrawingChangeListener) this.eventListener,TSDrawingChangeEvent.MAIN_DISPLAY_GRAPH_CHANGED);
		
		eventManager.addViewportChangeListener(this.parentCanvas,(TSEViewportChangeListener) this.eventListener);
	}
	
	
	/**
	 * This method unregisters this class as a listener to any events.
	 */
	public void unregisterListeners()
	{
		this.parentCanvas.getGraphManager().
			getEventManager().removeListener(this.eventListener);
	}
	
	
	public class AnimatedEdgeEventListener extends TSEAnyChangeAdapter
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 954530300413244818L;
		

		AnimatedEdgeEventListener()
		{
		}
		
		
		public void drawingChanged(TSDrawingChangeEvent event)
		{
			// We receive an event if the swing canvas's main display
			// graph has been changed.
			
			if (event.getType() ==
				TSDrawingChangeEvent.MAIN_DISPLAY_GRAPH_CHANGED)
			{
				// remove all listeners because this graph has become
				// inactive.
				
				AnimatedEdgeUI.this.setListening(false);
				
				// discard reference to parent canvas so that next time this
				// UI is drawn it can be refetched.
				
				AnimatedEdgeUI.this.parentCanvas = null;
			}
		}
		
		
		/** 
		 * This method will call the anyChange method of this
		 * class.
		 */
		public void viewportChanged(TSEViewportChangeEvent event)
		{
			// a viewport change means that the canvas may have panned or
			// zoomed. If it panned, we need to check to see if the mouse
			// has entered or exited the source node -- it may have panned
			// over to the position of the cursor and now should be
			// considered 'in'.
			
			AnimatedEdgeUI.this.updateMouse();
		}
	
	
		/**
		 * This method updates the inspector.
		 */
		public void anyChange(TSEvent event)
		{
			// check to see if the owner edge has been deleted/undeleted,
			// hidden/shown, or any other change that should affect whether
			// or not we should be listening for events. If the owner has
			// been deleted we need to stop listening here to prevent
			// potential memory leaks.
			
			AnimatedEdgeUI.this.updateMouse();
			TSEEdge ownerEdge = AnimatedEdgeUI.this.getOwnerEdge();
			
			boolean shouldBeListening =
				((ownerEdge != null) &&
				(ownerEdge.isOwned()) &&
				(ownerEdge.isVisible()) &&
				(ownerEdge.isViewable()));
				
			AnimatedEdgeUI.this.setListening(shouldBeListening);
		}
	}


// ---------------------------------------------------------------------
// Section: Class variables
// ---------------------------------------------------------------------


	/**
	 * This constant stores the relative amount by which the phase of
	 * the line should change for each frame of "slow" animation.
	 * The range is -1 to 1, inclusive.
	 */
	static final float SLOW_STEP = -0.05f;
	
	/**
	 * This constant stores the relative amount by which the phase of
	 * the line should change for each frame of "fast" animation.
	 * The range is -1 to 1, inclusive.
	 */
	static final float FAST_STEP = -0.1f;
	

// ---------------------------------------------------------------------
// Section: Instance variables
// ---------------------------------------------------------------------

	/**
	 * This variable stores the relative amount by which the phase of
	 * the line changes on each frame. The range is -1 to 1,
	 * inclusive.
	 */
	float phaseStep;

	/**
	 * This variable stores the current mouse X coordinate.
	 */
	int mouseX;

	/**
	 * This variable stores the current mouse Y coordinate.
	 */
	int mouseY;

	/**
	 * This variable stores whether or not the mouse cursor is over the
	 * edge's source node.
	 */
	boolean mouseIsIn;

	/**
	 * This variable stores the swing canvas that owns this UI (via its
	 * owner node)
	 */
	DrawingCanvas parentCanvas;
	
	/**
	 * This variable stores whether or not the UI is currently listening
	 * to events.
	 */
	boolean listening;
	
	
	/**
	 * This variable stores the number by which the width of the edge is
	 * multiplied while the mouse is over the source node of the edge 
	 * that owns this UI.
	 */
	int widthMultiplier = 1;
	
	EventListener eventListener;
}

