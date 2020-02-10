package com.tibco.cep.studio.ui.xml.wizards;

/**
 * 
 * @author sasahoo
 *
 */
public interface IMapperConstants {

	public static final int WSfn = 50;
	public static final int WSin = 200;
	public static final int Wst = WSfn+WSin;
	public static final int Wsm = 10;
	public static final int Wsc = 60; //the minimum gap When both collapsed  
	
	//don't change the below unless the weights of the sections adjusted 
	public static final int WSfnv = 50;//horizontal orientation function sash wieghts 
	public static final int WSinv = 40;//horizontal orientation input sash wieghts 
	
	public static String OK = "ok";
	public static String CANCEL = "cancel";
}
