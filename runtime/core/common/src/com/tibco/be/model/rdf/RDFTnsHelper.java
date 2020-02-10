package com.tibco.be.model.rdf;

import org.xml.sax.InputSource;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;

public class RDFTnsHelper {
	
	public static Entity deserializeEntity(InputSource input) {
		return DefaultMutableOntology.deserializeEntity(input);
	}

}
