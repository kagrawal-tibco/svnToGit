package com.tibco.cep.webstudio.client.util;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;


/**
 * 
 * @author sasahoo
 *
 */
public interface GlobalImages extends ClientBundle {

	  @Source("error_icon.gif")  
	  ImageResource error(); 
	  
	  @Source("error_warning.png")  
	  ImageResource warning(); 
	  
}
