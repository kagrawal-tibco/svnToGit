package com.tibco.cep.studio.common.palette.util;

import java.io.InputStream;
import java.net.URL;

public class CommonPaletteUtils {
	
	private static String PALETTE_XSD="palette.xsd";
	
	public static URL getPaletteSchemaURL(){
		URL url=CommonPaletteUtils.class.getClassLoader().getResource("/schema/"+PALETTE_XSD);
		return url;
	}
	
	public static InputStream getPaletteResAsStream(){
		InputStream resourceAsStream = CommonPaletteUtils.class.getClassLoader().getResourceAsStream("/schema/"+PALETTE_XSD);
		return resourceAsStream;
	}

}
