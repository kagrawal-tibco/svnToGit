package com.tibco.be.model.rdf;

import org.xml.sax.InputSource;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.IAddOnLoader;

public interface TnsEntityHandler {
	
	void setAddOnLoader(IAddOnLoader l);
	
	Entity getEntity(InputSource input,String uri) throws Exception;

}
