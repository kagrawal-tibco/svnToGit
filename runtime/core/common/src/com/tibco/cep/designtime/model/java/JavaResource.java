package com.tibco.cep.designtime.model.java;

import com.tibco.cep.designtime.model.Entity;

public interface JavaResource extends Entity {
	
	String getPackageName();
	
	byte[] getContent();
	
	String getExtension();

}
