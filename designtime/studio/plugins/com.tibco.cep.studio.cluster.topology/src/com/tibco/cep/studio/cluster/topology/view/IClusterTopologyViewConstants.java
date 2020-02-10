package com.tibco.cep.studio.cluster.topology.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author sasahoo
 *
 */
interface IClusterTopologyViewConstants {

	public static final String DEPLOYMENT_PAGE = "DEPLOYMENT";
	public static final String DEPLOYMENT_CONFIGURATION_PAGE = "CONFIGURATION";
	public static final String DEPLOYMENT_INPUT_PAGE = "INPUT";
	public static final String DEPLOYMENT_OUTPUT_PAGE = "OUTPUT";
	
	public static int X = 0; 
	public static int Y = 0;
	public static int TOPh = 30; 
	public static int WIDTH = 99; 
	public static int HIEGHT = 25;
	public static int BOTTOMh = 1000;
	public static int Sy = 101;
	
	public static final Color COLOR_WIDGET_BACKGROUND = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
	public static final Color COLOR_WHITE = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	public static final Color COLOR_INFO_BACKGROUND = Display.getDefault().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
	
	
	public static final Color[] titleHeaderColor = new Color[] {COLOR_WIDGET_BACKGROUND,COLOR_WIDGET_BACKGROUND,COLOR_WIDGET_BACKGROUND,COLOR_WHITE};
	public static int[] gradPercents= new int[] { 33, 67, 100 };
}
