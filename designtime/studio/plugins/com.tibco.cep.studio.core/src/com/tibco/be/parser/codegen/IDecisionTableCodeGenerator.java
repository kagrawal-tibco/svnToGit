package com.tibco.be.parser.codegen;


public interface IDecisionTableCodeGenerator {

	void generateDecisionTables(CodeGenContext ctx) throws Exception;
	void generateDecisionTablesStream(CodeGenContext ctx) throws Exception;

}
