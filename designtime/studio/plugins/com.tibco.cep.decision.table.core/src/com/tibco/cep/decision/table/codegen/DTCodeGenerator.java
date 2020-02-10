package com.tibco.cep.decision.table.codegen;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.IDecisionTableCodeGenerator;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.Ontology;

public class DTCodeGenerator implements IDecisionTableCodeGenerator {

	public DTCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void generateDecisionTables(CodeGenContext ctx) throws Exception {
		DTCodegenGlobalContext dtctx = new DTCodegenGlobalContext(
				(Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
				(File)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR),
				(List<RuleError>) ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST),
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE),
				(Boolean)ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG),
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));
		
		DTClassGenerator.generateDecisionTableJavaFiles(dtctx);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void generateDecisionTablesStream(CodeGenContext ctx)
			throws Exception {
		DTCodegenGlobalContext dtctx = new DTCodegenGlobalContext(
				(Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
				(JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR),
				(List<RuleError>) ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST),
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE),
				(Boolean)ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG),
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));
		
		DTClassGenerator.generateDecisionTableJavaStream(dtctx);
		
	}
}