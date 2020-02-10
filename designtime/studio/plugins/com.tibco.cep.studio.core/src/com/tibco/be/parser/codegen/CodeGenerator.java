package com.tibco.be.parser.codegen;

import java.io.InputStream;


public interface CodeGenerator {
	
	public void init() throws Exception;
	
	public void generate() throws Exception;
	
	public InputStream compile() throws Exception;
	
	public void close();

}
