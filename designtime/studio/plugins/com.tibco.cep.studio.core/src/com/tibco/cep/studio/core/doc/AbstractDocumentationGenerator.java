package com.tibco.cep.studio.core.doc;


/*
@author ssailapp
@date Dec 9, 2011
 */

public abstract class AbstractDocumentationGenerator implements IDocumentationGenerator {
	protected DocumentationDescriptor desc;
	
	public AbstractDocumentationGenerator() {
	}
	
	public void setDocumentationDescriptor(DocumentationDescriptor docDesc) {
		desc = docDesc;
	}
}
