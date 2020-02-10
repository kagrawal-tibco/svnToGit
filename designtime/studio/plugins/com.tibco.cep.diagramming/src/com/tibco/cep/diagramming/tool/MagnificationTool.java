package com.tibco.cep.diagramming.tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import com.tomsawyer.canvas.rendering.TSRenderingManager;
import com.tomsawyer.canvas.rendering.awt.TSEDefaultGraphics;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.drawing.geometry.shared.TSDeviceRect;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.interactive.swing.tool.TSEWindowInputTool;


/**
 * @author ggrigore
 * 
 * This class defines a tool that mimics a magnification glass being moved
 * over the graph.
 */
public class MagnificationTool extends TSEWindowInputTool {

	private JComponent magnifiedComponent = null;
	private int windowSize = 250;
	private int zoomLevel = 3;

	public MagnificationTool() { }

	public void setWindowSize(int size) {
		this.windowSize = size;
	}
	
	public void setZoom(int zoom) {
		this.zoomLevel = zoom;
	}
	
	public int getWindowSize() {
		return this.windowSize;
	}
	
	public int getZoom() {
		return this.zoomLevel;
	}
	
	/**
	 * Responds to the mouse cursor being moved. It displays a
	 * zoomed in view of the area directly under the mouse point.
	 */
	public void onMouseMoved(MouseEvent event) {
		if (this == this.getToolManager().getActiveTool()) {

			// Remove the magnified component if it is showing.
			if (this.magnifiedComponent != null) {
				this.getInnerCanvas().remove(this.magnifiedComponent);
				this.magnifiedComponent = null;
			}

			// TODO: add this to a preference
			// Show a component of 200 pixels by 200 pixels with a zoom factor of 5.
			int magnificationValue = this.zoomLevel;
			final int magnificationGlassSize = this.windowSize;

			// Calculate the world bounds to show in the magnified component.
			int reducedDeviceSize =	(magnificationGlassSize / magnificationValue);
			TSDeviceRect smallDeviceBounds = new TSDeviceRect(
				event.getX() - reducedDeviceSize / 2,
				event.getY() - reducedDeviceSize / 2,
				reducedDeviceSize,
				reducedDeviceSize);
			final TSConstRect worldBounds =
				this.getSwingCanvas().getTransform().boundsToWorld(smallDeviceBounds);

			this.magnifiedComponent = new JComponent() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -481050195903672291L;
				

				@SuppressWarnings({ "deprecation"})
				public void paintComponent(Graphics g) {
					TSTransform transform = (TSTransform)
						getSwingCanvas().getTransform().clone();
					transform.setDeviceBounds(0,
						0,
						magnificationGlassSize,
						magnificationGlassSize);
					transform.setWorldSize(worldBounds.getWidth(),
						worldBounds.getHeight());
					transform.setWorldTopLeft(worldBounds.getLeft(),
						worldBounds.getTop());

					TSEGraphics graphics = new TSEDefaultGraphics(g, transform);
					TSRenderingManager renderingManager =
						(TSRenderingManager)getSwingCanvas().newRenderingManager(graphics);
					renderingManager.setClipBounds(worldBounds);

					renderingManager.draw();

					// Draw a border around the magnified component.
					g.setColor(Color.lightGray);
					g.drawRect(0, 0, magnificationGlassSize - 1, magnificationGlassSize - 1);
				}
			};

			this.magnifiedComponent.setSize(magnificationGlassSize,
				magnificationGlassSize);
			this.magnifiedComponent.setLocation(event.getX() - magnificationGlassSize / 2,
				event.getY() - magnificationGlassSize / 2);

			this.getInnerCanvas().repaint();
			this.getInnerCanvas().add(this.magnifiedComponent);
		}
	}

	public void onMousePressed(MouseEvent me) {
		if(me.getButton() == MouseEvent.BUTTON3) {
			finalizeTool();
		}
	}
	
	public void finalizeTool() {
		super.finalizeTool();

		if (this.magnifiedComponent != null) {
			this.getInnerCanvas().remove(this.magnifiedComponent);
			this.magnifiedComponent = null;
			this.getInnerCanvas().repaint();
		}
	}
}
