package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ERROR_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SIGNAL_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TIMER_EVENT_DEFINITION;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;

import org.eclipse.emf.ecore.EClass;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class IntermediateEventNodeUI extends EventNodeUI {

	private static final long serialVersionUID = 12L;

	public IntermediateEventNodeUI(EClass eventDefType) {
		super(eventDefType);
		this.setOuterBorderMultiplier(4);
	}
	
	public void drawExtended(TSEGraphics graphics) {
		this.drawIntermediate(graphics);
		
		if(getEventDefinitionType() == null)
			return;
		
		if (MESSAGE_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {		
			super.drawMessageBadge(graphics);
		}
		else if (TIMER_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawTimerBadge(graphics);
		}
		else if (ERROR_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawErrorBadge(graphics);
		}else if (SIGNAL_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawSignalBadge(graphics);
		}
	}
	
	private void drawIntermediate(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();
		TSConstRect bounds = owner.getLocalBounds();		
		TSRect inner = new TSRect(bounds);
		inner.setWidth(inner.getWidth() * INNER_SIZE);
		inner.setHeight(inner.getHeight() * INNER_SIZE);

		// fill in inner circle
		TSTransform transform = graphics.getTSTransform();
		GradientPaint gradient = new GradientPaint(
			transform.xToDevice(this.getOwner().getLocalLeft()),
			transform.yToDevice(this.getOwner().getLocalTop()),
			new Color(255,203,17), // RoundRectNodeUI.START_COLOR.getColor(), //START_COLOR,
			transform.xToDevice(this.getOwner().getLocalRight()),
			transform.yToDevice(this.getOwner().getLocalBottom()),
			new Color(255,255,255)); // RoundRectNodeUI.END_COLOR.getColor()); // END_COLOR);
		graphics.setPaint(gradient);
		graphics.fillOval(inner);
		
		// draw inner circle
		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
			oldStroke.getLineWidth() * this.outerBorderMultiplier,
			oldStroke.getEndCap(),
			oldStroke.getLineJoin(),
			oldStroke.getMiterLimit(),
			oldStroke.getDashArray(),
			oldStroke.getDashPhase());
		graphics.setStroke(newStroke);
		graphics.setColor(GLOW_OUTER_HIGH_COLOR);
		graphics.drawOval(inner);
		graphics.setStroke(oldStroke);
	}
}
