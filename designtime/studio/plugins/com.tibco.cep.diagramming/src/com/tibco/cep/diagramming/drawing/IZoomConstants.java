package com.tibco.cep.diagramming.drawing;

import com.ibm.icu.text.DecimalFormat;

/**
 * 
 * @author sasahoo
 *
 */
public interface IZoomConstants {

	public static final double multiplier = 1.0; 
	public static final double[] zoomLevels = {.1,.25,.5, .75, 1.0, 1.5, 2.0, 2.5, 3, 4,5,10,20};
	public static final DecimalFormat format = new DecimalFormat("####.##%"); 
	
}
