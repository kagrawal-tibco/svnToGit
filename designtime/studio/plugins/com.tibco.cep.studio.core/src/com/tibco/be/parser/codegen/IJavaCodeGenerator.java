package com.tibco.be.parser.codegen;

import com.tibco.cep.designtime.model.java.JavaSource;

public interface IJavaCodeGenerator {
	public void generateJava( JavaSource cept, CodeGenContext context) throws Exception;
	public void generateJavaStream( JavaSource cept, CodeGenContext context) throws Exception;
}
