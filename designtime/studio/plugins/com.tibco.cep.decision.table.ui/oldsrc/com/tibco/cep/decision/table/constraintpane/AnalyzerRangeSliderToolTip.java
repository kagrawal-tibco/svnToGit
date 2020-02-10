package com.tibco.cep.decision.table.constraintpane;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jidesoft.swing.RangeSlider;

public class AnalyzerRangeSliderToolTip {
	
	/**
	 * @param slider
	 */
	public static void setSliderToolTip(final RangeSlider slider){ 
		slider.addChangeListener(new ChangeListener(){ 
			private boolean adjusting = false; 
			private String oldTooltip; 
			/* (non-Javadoc)
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			public void stateChanged(ChangeEvent e){ 
				if(slider.getModel().getValueIsAdjusting()){ 
					if(!adjusting){ 
						oldTooltip = slider.getToolTipText(); 
						adjusting = true; 
					} 
					slider.setToolTipText(String.valueOf(slider.getValue())); 
					hideToolTip(slider); 
					postToolTip(slider); 
				}
				else{ 
					hideToolTip(slider); 
					slider.setToolTipText(oldTooltip); 
					adjusting = false; 
					oldTooltip = null; 
				} 
			} 
		}); 
	} 

	/**
	 * @param comp
	 */
	public static void postToolTip(JComponent comp){ 
		Action action = comp.getActionMap().get("postTip"); 
		if(action==null) 
			return; 
		ActionEvent ae = new ActionEvent(comp, ActionEvent.ACTION_PERFORMED, "postTip", EventQueue.getMostRecentEventTime(), 0); 
		action.actionPerformed(ae); 
	} 

	/**
	 * @param comp
	 */
	public static void hideToolTip(JComponent comp){ 
		Action action = comp.getActionMap().get("hideTip"); 
		if(action==null) 
			return; 
		ActionEvent ae = new ActionEvent(comp, ActionEvent.ACTION_PERFORMED, 
				"hideTip", EventQueue.getMostRecentEventTime(), 0); 
		action.actionPerformed(ae); 
	} 
}