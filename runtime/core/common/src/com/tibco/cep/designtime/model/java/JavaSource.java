package com.tibco.cep.designtime.model.java;

import com.tibco.cep.designtime.model.Entity;

public interface JavaSource extends Entity {
	
	String getPackageName();
	
	byte[] getSource();
}
