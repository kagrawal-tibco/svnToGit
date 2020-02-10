package com.tibco.cep.decision.tree.ui.nodes;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSShapeTextUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSTextUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;

/*
@author ssailapp
@date Jun 6, 2011
 */

@SuppressWarnings("serial")
public class BooleanConditionNodeUI extends AbstractNodeUI {

    public static final TSEColor START_COLOR = TSEColor.yellow;
    public static final TSEColor END_COLOR = new TSEColor(255, 232, 138);
	
	@Override
	protected TSTextUIElement getTextUIElement() {
		return new TSShapeTextUIElement("ActionText",
			"$name()",
			new TSUIElementPoint(-0.5, 0, 0.5, 0),
			new TSUIElementPoint(0.5, 0, -0.5, 0));
	}
	
	@Override
	protected TSEColor getGradientStartColor() {
		return START_COLOR;
	}

	@Override
	protected TSEColor getGradientEndColor() {
		return END_COLOR;
	}

	@Override
	protected TSEColor getBackgroundColor() {
		return START_COLOR;
		/* // TODO - TSV 9.2
		return TSDecisionUIConstants.CONDITION_NODE_BACKGROUND_COLOR;
		*/
	}
}
