package com.tibco.be.parser.codegen;

import com.tibco.cep.designtime.model.java.JavaResource;

public interface IJavaResourceGenerator {
	public void generateJavaResource( JavaResource res, CodeGenContext context) throws Exception;
	public void generateJavaResourceStream( JavaResource res, CodeGenContext context) throws Exception;
}
