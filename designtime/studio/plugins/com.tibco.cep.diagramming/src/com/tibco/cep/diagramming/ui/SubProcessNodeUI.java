package com.tibco.cep.diagramming.ui;

import org.eclipse.swt.graphics.RGB;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;

public class SubProcessNodeUI extends ExpandedSubprocessNodeUI {

	/**
	 * 
	 * @author hitesh
	 *
	 */
	public RGB color= new RGB(255,220,81);
	
	private static final long serialVersionUID = 1L;

	public void reset()	{
        super.reset();
        this.setFillColor(new TSEColor(255,220,81));
        this.setBorderDrawn(true);
        this.setDrawChildGraphMark(false);
    }
	public TSEColor getColor(){
		return new TSEColor(color.red,color.blue,color.green);
		
	}
	public void setNodeColor(){
		
		this.setBorderColor(getColor());
	}
}
