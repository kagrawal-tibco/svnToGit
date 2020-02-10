package com.tibco.cep.decision.tree.ui.nodes;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;


/*
@author ssailapp
@date May 31, 2011
 */

@SuppressWarnings("serial")
public class DescriptionNodeUI extends AbstractNodeUI {

    public static final TSEColor START_COLOR = TSEColor.white;
    public static final TSEColor END_COLOR = TSEColor.paleYellow;
	
	protected TSEColor getGradientStartColor() {
		return START_COLOR;
	}

	protected TSEColor getGradientEndColor() {
		return END_COLOR;
	}
}

