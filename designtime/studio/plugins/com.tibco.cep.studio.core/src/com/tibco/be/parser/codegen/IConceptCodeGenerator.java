package com.tibco.be.parser.codegen;

import com.tibco.cep.designtime.model.element.Concept;

public interface IConceptCodeGenerator {
	public void generateConcept( Concept cept, CodeGenContext context) throws Exception;
	public void generateConceptStream( Concept cept, CodeGenContext context) throws Exception;
}
