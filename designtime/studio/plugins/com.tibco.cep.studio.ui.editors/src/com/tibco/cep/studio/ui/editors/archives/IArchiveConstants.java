package com.tibco.cep.studio.ui.editors.archives;

/**
 * 
 * @author sasahoo
 *
 */
public interface IArchiveConstants {

	public static final int CONFIGURATION = 0;
	public static final int BUSINNESS_EVENTS_ARCHIVE = 1;
	public static final int SHARED_ARCHIVE = 2;
	
	public static final int Smin = 15;
	public static final int SIN = 75;
	public static final int SRLS = 75;
	public static final int SFUNC = 75;
	public static final int SOM = 75;
	public static final int St = SIN + SRLS + SFUNC + SOM;
	public static final int SFUNCinit = 270;
}
