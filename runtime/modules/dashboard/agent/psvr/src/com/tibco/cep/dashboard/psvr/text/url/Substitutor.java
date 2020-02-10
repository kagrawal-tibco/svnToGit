package com.tibco.cep.dashboard.psvr.text.url;

import java.io.Serializable;

public interface Substitutor extends Serializable {
	
	public String substitute(String key, SubstitutionContext context) throws Exception;
	
	public String[] getSupportedKeys();
	

}