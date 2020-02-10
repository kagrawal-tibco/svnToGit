package com.tibco.cep.decision.tree.ui.nodes;

import java.awt.Color;


/*
 @author ssailapp
 @date Sep 16, 2011
 */

@SuppressWarnings("serial")
public class StartNodeUI extends TerminalNodeUI {

	private static final Color START_COLOR = new Color(0, 213, 0);
	private static final Color END_COLOR = new Color(235, 247, 235);
	private static Color GLOW_OUTER_HIGH_COLOR = new Color(153, 255, 0);
	
	@Override
	public Color getStartColor() {
		return START_COLOR;
	}

	@Override
	public Color getEndColor() {
		return END_COLOR;
	}

	@Override
	public Color getGlowColor() {
		return GLOW_OUTER_HIGH_COLOR;
	}
	
}