package com.tibco.cep.studio.cluster.topology.ui;

import java.util.List;

import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.util.shared.TSProperty;


/**
 * This class defines a node UI that represents a network bus.
 */
public class BusNodeUI extends TSENodeUI {
	private static final long serialVersionUID = 1L;
	private static TSEColor BUS_COLOR = new TSEColor(147, 199, 255);
	
	public void reset() {
		// first initialize parent's properties

		super.reset();

		// now initialize additional properties

		this.setEndColor(this.getDefaultEndColor());
		this.setFillColor(BUS_COLOR);
	}


	/**
	 * This method copies all properties of the specified UI to
	 * this UI. The copying occurs only if both UIs are of
	 * the same type - if they are not a ClassCastException is thrown.
	 * Overriding classes must call the superclass' implementation
	 * of this method.
	 */
	public void copy(TSEObjectUI sourceUI) {
		// copy properties of the superclass

		super.copy(sourceUI);

		// still here? Must be the right type. To make things easier
		// convert a generic TSEObjectUI to our specific type

		BusNodeUI sourceBusUI = (BusNodeUI) sourceUI;

		// object properties must be set

		this.setEndColor(sourceBusUI.getEndColor());
	}


	//---------------------------------------------------------------------
	//Section: Drawing
	//---------------------------------------------------------------------


	/**
	 * This method draws the object represented by this UI.
	 * @param graphics the <code>TSEGraphics</code> object onto which
	 * the UI is being drawn.
	 */
	public void draw(TSEGraphics graphics) {
		TSTransform transform = graphics.getTSTransform();
		TSENode owner = this.getOwnerNode();

		// determine the bounds of the node

		int nodeDeviceLeft = transform.xToDevice(
				owner.getLocalLeft());
		int nodeDeviceTop = transform.yToDevice(
				owner.getLocalTop());
		int nodeDeviceWidth = transform.widthToDevice(
				owner.getLocalWidth());
		int nodeDeviceHeight = transform.heightToDevice(
				owner.getLocalHeight());

		// fill the UI if it is not transparent

		if (!this.isTransparent()) {
			if (nodeDeviceWidth > 2 * nodeDeviceHeight)
			{
				graphics.setColor(this.getEndColor());

				graphics.fillRect(owner.getLocalBounds());

				graphics.fillRect(
						nodeDeviceLeft + nodeDeviceWidth - nodeDeviceHeight,
						nodeDeviceTop,
						nodeDeviceHeight,
						nodeDeviceHeight);

				graphics.setColor(this.getFillColor());

				graphics.fillRect(
						nodeDeviceLeft + nodeDeviceHeight,
						nodeDeviceTop,
						nodeDeviceWidth - 2 * nodeDeviceHeight,
						nodeDeviceHeight);
			}
			else {
				graphics.setColor(this.getEndColor());

				graphics.fillRect(owner.getLocalBounds());
			}
		}

		// draw the border if necessary

		if (this.isBorderDrawn()) {
			graphics.setColor(this.getBorderColor().getColor());

			graphics.drawRect(owner.getLocalBounds());

			if (nodeDeviceWidth > 2 * nodeDeviceHeight)
			{
				graphics.drawLine(nodeDeviceLeft + nodeDeviceHeight,
						nodeDeviceTop,
						nodeDeviceLeft + nodeDeviceHeight,
						nodeDeviceTop + nodeDeviceHeight);

				graphics.drawLine(
						nodeDeviceLeft + nodeDeviceWidth - nodeDeviceHeight,
						nodeDeviceTop,
						nodeDeviceLeft + nodeDeviceWidth - nodeDeviceHeight,
						nodeDeviceTop + nodeDeviceHeight);
			}
		}
		this.drawHighlight(graphics);
	}


	//---------------------------------------------------------------------
	//Section: Default properties
	//---------------------------------------------------------------------


	/**
	 * This method returns whether or not the text is editable by the user.
	 * @return false
	 */
	public boolean isAnnotationEditable() {
		return false;
	}


	/**
	 * This method sets the owner of this UI.
	 */
	public void setOwner(TSENode ownerNode) {
		super.setOwner(ownerNode);

		if (ownerNode != null) {
			// we want to change the owner's resizability - make sure
			// the locked bit is not set so we don't change it
			// inappropriately.

			if ((ownerNode.getResizability() &
					TSESolidObject.RESIZABILITY_LOCKED) == 0)
			{
				ownerNode.setResizability(
						this.getDefaultResizability());
			}

			ownerNode.setLocalAdjustedSize(
					this.getDefaultWidth(),
					this.getTightHeight());

			ownerNode.setLocalAdjustedOriginalSize(
					this.getDefaultWidth(),
					this.getTightHeight());
		}
	}


	//---------------------------------------------------------------------
	//Section: Resizability
	//---------------------------------------------------------------------


	/**
	 * This method returns the default resizability of this UI.
	 */
	public int getDefaultResizability()	{
		return TSESolidObject.RESIZABILITY_TIGHT_HEIGHT;
	}


	/**
	 * This method returns the tight height of this UI.
	 */
	public double getTightHeight() {
		return BusNodeUI.HEIGHT;
	}


	/**
	 * This method returns the minimum height of this UI.
	 */
	public double getMinimumHeight() {
		return BusNodeUI.HEIGHT;
	}


	/**
	 * This method returns the minimum width of this UI.
	 */
	public double getMinimumWidth()
	{
		return BusNodeUI.HEIGHT * 2;
	}


	/**
	 * This method returns the default width of this UI.
	 */
	public double getDefaultWidth()	{
		return BusNodeUI.WIDTH;
	}


	/**
	 * This method returns the end color of this UI.
	 */
	public TSEColor getEndColor() {
		return (this.endColor);
	}


	/**
	 * This method sets the end color of this UI.
	 */
	public void setEndColor(TSEColor endColor) {
		TSEColor oldColor = this.endColor;
		this.endColor = endColor;
		this.firePropertyChangedEvent(
				BusNodeUI.END_COLOR,
				oldColor,
				endColor);
	}


	/**
	 * This method returns the default end color of this object.
	 * @return <code>TSEColor.darkGray</code>
	 */
	public TSEColor getDefaultEndColor() {
		return (TSEColor.darkGray);
	}


	/**
	 * This method returns a list of all properties associated with
	 * this object. Overriding classes must call the superclass'
	 * implementation of this method.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getProperties()	{
		List list = super.getProperties();

		list.add(new TSProperty(
				BusNodeUI.END_COLOR,
				this.getEndColor()));

		return (list);
	}


	/**
	 * This method sets the specified property of this UI.
	 * Overriding classes that do not process the specified property
	 * must call the superclass' implementation of this method.
	 */
	public void setProperty(TSProperty property) {
		if (BusNodeUI.END_COLOR.equals(property.getName()))	{
			if (property.getValue() instanceof TSEColor) {
				this.setEndColor((TSEColor) property.getValue());
			}
			else {
				this.setEndColor(new TSEColor(
						property.getValue().toString()));
			}
		}
		else {
			super.setProperty(property);
		}
	}


	/**
	 * This method returns a list of all properties not set to their
	 * default values. Overriding classes must call the superclass'
	 * implementation of this method.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getChangedProperties() {
		List list = super.getChangedProperties();

		// add to the property list only if value if different from
		// that of default

		if (!this.getDefaultEndColor().equals(this.getEndColor())) {
			list.add(
					new TSProperty(
							BusNodeUI.END_COLOR,
							this.getEndColor()));
		}

		return (list);
	}



	//---------------------------------------------------------------------
	//Section: Class variables
	//---------------------------------------------------------------------


	/**
	 * This constant is the name for the ned color property managed
	 * by this class.
	 */
	public static final String END_COLOR = "endColor";


	/**
	 * The default width of the node with this UI.
	 */
	public static final double WIDTH = 100;


	/**
	 * The default height of the node with this UI.
	 */
	public static final double HEIGHT = 10;


	//---------------------------------------------------------------------
	//Section: Instance variables
	//---------------------------------------------------------------------


	/**
	 * This variable stores the end color of this UI.
	 */
	TSEColor endColor;
}
