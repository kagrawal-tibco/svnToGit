package com.tibco.cep.diagramming;

import org.eclipse.ui.part.ViewPart;

import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;


public abstract class AbstractOverview extends ViewPart {
	
	public abstract Object getOverview();
	public abstract TSEOverviewComponent getTSOverviewComponent();
	public abstract java.awt.Frame getFrame();
	public abstract void updateOverview();
	
}
