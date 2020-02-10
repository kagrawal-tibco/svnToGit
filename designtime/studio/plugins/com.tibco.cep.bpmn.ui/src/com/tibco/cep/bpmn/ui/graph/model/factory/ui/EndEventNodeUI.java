package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ERROR_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SIGNAL_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TIMER_EVENT_DEFINITION;

import java.awt.Color;

import org.eclipse.emf.ecore.EClass;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class EndEventNodeUI extends EventNodeUI {

	private static final long serialVersionUID = 12L;

	public EndEventNodeUI(EClass eventDefType) {
		super(eventDefType);
		this.setOuterBorderMultiplier(10);
	}
	
	public void drawExtended(TSEGraphics graphics) {
		if (getEventDefinitionType() == null) {		
			return;			
		} else if (MESSAGE_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {		
			super.drawMessageBadge(graphics);
		}
		else if (TIMER_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawTimerBadge(graphics);
		}
		else if (ERROR_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawErrorBadge(graphics);
		}	
		else if (SIGNAL_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawSignalBadge(graphics);
		}	
	}	
	
	/**
	 * @param graphics
	 * @param inputBreakpointHit
	 */
	public void drawBreakpointHit(TSEGraphics graphics, boolean inputBreakpointHit , 
            Color color) {
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
		if (inputBreakpointHit) {

//			drawImage(graphics, RADIO_BRK_HIT_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//			drawOutputBreakpoints(graphics,hasOutBreakpoint, outBrkPoinToggle);
			
			drawInputBreakpoints(graphics, true, false, color);
			drawBreakPointLeft(graphics, ownerBounds, transform, color);
		} 
	}
	
	private void drawBreakPointLeft(TSEGraphics graphics, TSConstRect ownerBounds, TSTransform transform, Color color) {
		Color oldColor = graphics.getColor();
		int x = transform.xToDevice(ownerBounds.getLeft() + INNER_BREAKPOINT_SIZE );
		int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
		graphics.fillOval(x - 2 + 19, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
	}
	
	/**
	 * @param graphics
	 * @param inputBrkPnt
	 * @param inBrkPnToggle
	 * @param outputBrkPnt
	 * @param outBrkPnToggle
	 */
	public void drawInputBreakpoints(TSEGraphics graphics, 
				                    boolean inputBrkPnt, 
				                    boolean inBrkPnToggle, 
				                    Color color) {
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
//		Color color = this.breakpointColor;
		graphics.setColor(color);
		setInputBreakPointBounds( new TSConstRect(
	            	ownerBounds.getLeft() + 1 + 2 + 2 + 2,
	            	ownerBounds.getCenterY() + DEFAULT_BREAKPOINT_SIZE/2,
	            	ownerBounds.getLeft() + 1 + DEFAULT_BREAKPOINT_SIZE + 2 + 2 + 2,
	            	ownerBounds.getCenterY() - DEFAULT_BREAKPOINT_SIZE/2));
		graphics.drawOval(getInputBreakPointBounds());
		//Draw Border Shadow for the outer one
		drawBorderShadow(graphics, 2, getInputBreakPointBounds(), false);

	//Draw Image
//		if (inputBrkPnt && inBrkPnToggle) {
//			drawImage(graphics, RADIO_ON_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//		} else {
//			drawImage(graphics, RADIO_OFF_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//		}
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);

		//Draw Border Shadow for the outer one
//		drawBorderShadow(graphics, 4, inputBreakPointBounds, true);
		
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
		
		if (inputBrkPnt && inBrkPnToggle) {
			int x = transform.xToDevice(ownerBounds.getLeft() + INNER_BREAKPOINT_SIZE );
			int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
			graphics.fillOval(x - 2 + 19, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
			graphics.setColor(oldColor);
			oldColor = graphics.getColor();
			graphics.setColor(color);
		}
	}
}
