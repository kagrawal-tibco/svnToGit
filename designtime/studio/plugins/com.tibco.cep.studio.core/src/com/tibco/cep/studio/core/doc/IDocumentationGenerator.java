package com.tibco.cep.studio.core.doc;

import java.util.ArrayList;

import org.antlr.stringtemplate.StringTemplateGroup;

/*
@author ssailapp
@date Dec 8, 2011
 */

public interface IDocumentationGenerator {
	
	public void setDocumentationDescriptor(DocumentationDescriptor docDesc);
	
	public ArrayList<PackageElement> getPackageElements();
	
	public ArrayList<ClassElement> getClassElements();
	
	public StringTemplateGroup getStringTemplateGroup();
	
	public boolean generateDocumentation(Callback callback);
	
}
