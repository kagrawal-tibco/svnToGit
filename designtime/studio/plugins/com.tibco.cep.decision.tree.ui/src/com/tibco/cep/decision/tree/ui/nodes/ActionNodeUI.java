package com.tibco.cep.decision.tree.ui.nodes;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;

/*
@author ssailapp
@date May 31, 2011
 */

@SuppressWarnings("serial")
public class ActionNodeUI extends AbstractNodeUI {
    public static final TSEColor START_COLOR = new TSEColor(53,180,255);
    public static final TSEColor END_COLOR = new TSEColor(20,5,207);

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
		return TSDecisionUIConstants.ACTION_NODE_BACKGROUND_COLOR;
		*/
	}
}
