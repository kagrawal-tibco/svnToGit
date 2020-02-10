package com.tibco.cep.studio.codegen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.IRuleFunctionCodeGenerator;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.be.parser.codegen.RuleFunctionCodeGeneratorSmap;
import com.tibco.be.parser.codegen.SmapFileWriter;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.semantic.SmapStratum;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleFunction;

public class RuleFunctionCodeGenerator implements IRuleFunctionCodeGenerator {

	public RuleFunctionCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.parser.codegen.IRuleFunctionCodeGenerator#generateRuleFunction(com.tibco.cep.designtime.model.rule.RuleFunction, com.tibco.cep.studio.parser.codegen.CodeGenContext)
	 */
	@Override
	public void generateRuleFunction(RuleFunction fn, CodeGenContext ctx) throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		List ruleErrorList = (List)ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
		List<RuleError> errorList = new ArrayList<RuleError>();
		File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR);
		boolean debug = (Boolean)ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
		generateRuleFunctionJavaFilePair(fn, 
				o, 
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE), 
				targetDir, 
				errorList, 
				debug,
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));
		if (errorList.size() > 0) {
			for (RuleError ruleError : errorList) {
				ruleError.setName(fn.getFullPath());
				ruleErrorList.add(ruleError);
			}
		}

	}
	
	
	/**
	 * @param fn
	 * @param o
	 * @param oversizeStringConstants
	 * @param ruleFnUsages
	 * @param targetDir
	 * @param errorList
	 * @param enableDebug
	 * @throws Exception
	 */
	public void generateRuleFunctionJavaFilePair(RuleFunction fn, Ontology o,
			Properties oversizeStringConstants, Map ruleFnUsages,
			File targetDir, List errorList, boolean enableDebug, Map<String, Map<String, int[]>> propInfoCache)
			throws Exception {
		generateRuleFunctionImplJavaFile(fn, o, oversizeStringConstants,
				ruleFnUsages, targetDir, errorList, enableDebug, propInfoCache);
		generateRuleFunctionWrapperJavaFile(fn, o, oversizeStringConstants,
				ruleFnUsages, targetDir, errorList, enableDebug);
	}
	
	/**
	 * @param fn
	 * @param o
	 * @param oversizeStringConstants
	 * @param ruleFnUsages
	 * @param targetDir
	 * @param errorList
	 * @param enableDebug
	 * @throws IOException
	 */
	public void generateRuleFunctionWrapperJavaFile(RuleFunction fn,
			Ontology o, Properties oversizeStringConstants, Map ruleFnUsages,
			File targetDir, List errorList, boolean enableDebug)
			throws IOException {
		JavaClassWriter jClass = RuleFunctionCodeGeneratorSmap
				.generateRuleFunctionWrapperClass(fn, errorList,
						oversizeStringConstants, ruleFnUsages, o);
		// jClss will be null if there were errors during parsing or generation
		if (jClass != null) {
			Folder folder = fn.getFolder();
			JavaFileWriter jfwriter = CodeGenHelper.setupJavaFileWriter(jClass
					.getName(), ModelNameUtil.modelPathToExternalForm(folder
					.getFullPath()), targetDir,
					ModelNameUtil.RULE_SEPARATOR_CHAR);
			jfwriter.addClass(jClass);
			jfwriter.writeFile();
		}
	}
	
	/**
	 * @param fn
	 * @param o
	 * @param oversizeStringConstants
	 * @param ruleFnUsages
	 * @param targetDir
	 * @param errorList
	 * @param enableDebug
	 * @throws Exception
	 */
	public void generateRuleFunctionImplJavaFile(RuleFunction fn, Ontology o,
			Properties oversizeStringConstants, Map ruleFnUsages,
			File targetDir, List errorList, boolean enableDebug, Map<String, Map<String, int[]>> propInfoCache)
			throws Exception {
		generateRuleFunctionImplJavaFile(fn, o, oversizeStringConstants,
				ruleFnUsages, targetDir, errorList, false, enableDebug, propInfoCache);
	}
	
	/**
	 * @param fn
	 * @param o
	 * @param oversizeStringConstants
	 * @param ruleFnUsages
	 * @param targetDir
	 * @param errorList
	 * @param ignoreVirtual
	 * @param enableDebug
	 * @throws Exception
	 */
	public void generateRuleFunctionImplJavaFile(RuleFunction fn, Ontology o,
			Properties oversizeStringConstants, Map ruleFnUsages,
			File targetDir, List errorList, boolean ignoreVirtual,
			boolean enableDebug, Map<String, Map<String, int[]>> propInfoCache) throws Exception {

		RuleInfo rinfo = new RuleInfo();
		rinfo.setPath(fn.getFullPath());
		JavaClassWriter jClassWriter = RuleFunctionCodeGeneratorSmap
				.generateRuleFunctionClassNew(fn, errorList,
						oversizeStringConstants, ruleFnUsages, ignoreVirtual,
						rinfo, o, propInfoCache);
		JavaFileWriter jfwriter = CodeGenHelper.setupJavaFileWriter(
				jClassWriter.getName(), ModelNameUtil
						.modelPathToExternalForm(fn.getFolder().getFullPath()),
				targetDir, ModelNameUtil.RULE_SEPARATOR_CHAR);
		jfwriter.addClass(jClassWriter);
		jfwriter.writeFile();
		if (enableDebug) {
			rinfo.getActionStratumMap().put(jClassWriter.getClassName(),
					new SmapStratum("RSP"));
			SmapFileWriter smwriter = CodeGenHelper.setupSmapFileWriter(
					jClassWriter.getName(), ModelNameUtil
							.modelPathToExternalForm(fn.getFolder()
									.getFullPath()), targetDir,
					ModelNameUtil.RULE_SEPARATOR_CHAR);
			smwriter.writeSMapXmlFile(rinfo);
		}

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.parser.codegen.IRuleFunctionCodeGenerator#generateRuleFunctionStream(com.tibco.cep.designtime.model.rule.RuleFunction, com.tibco.cep.studio.parser.codegen.CodeGenContext)
	 */
	@Override
	public void generateRuleFunctionStream(RuleFunction fn, CodeGenContext ctx)
			throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		List ruleErrorList = (List)ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
		List<RuleError> errorList = new ArrayList<RuleError>();
		JavaFolderLocation targetDir = (JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR);
		boolean debug = (Boolean)ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
		generateRuleFunctionJavaStreamPair(fn, 
				o, 
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE), 
				targetDir, 
				errorList, 
				debug,
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));
		if (errorList.size() > 0) {
			for (RuleError ruleError : errorList) {
				ruleError.setName(fn.getFullPath());
				ruleErrorList.add(ruleError);
			}
		}
		
	}
	
	/**
	 * @param fn
	 * @param o
	 * @param oversizeStringConstants
	 * @param ruleFnUsages
	 * @param targetDir
	 * @param errorList
	 * @param enableDebug
	 * @throws Exception
	 */
	public void generateRuleFunctionJavaStreamPair(RuleFunction fn, Ontology o,
			Properties oversizeStringConstants, Map ruleFnUsages,
			JavaFolderLocation targetDir, List errorList, boolean enableDebug
			, Map<String, Map<String, int[]>> propInfoCache) throws Exception
	{
		generateRuleFunctionImplJavaStream(fn, o, oversizeStringConstants,
				ruleFnUsages, targetDir, errorList, enableDebug, propInfoCache);
		generateRuleFunctionWrapperJavaStream(fn, o, oversizeStringConstants,
				ruleFnUsages, targetDir, errorList, enableDebug);
	}
	
	/**
	 * @param fn
	 * @param o
	 * @param oversizeStringConstants
	 * @param ruleFnUsages
	 * @param targetDir
	 * @param errorList
	 * @param enableDebug
	 * @throws Exception
	 */
	public void generateRuleFunctionImplJavaStream(RuleFunction fn, Ontology o,
			Properties oversizeStringConstants, Map ruleFnUsages,
			JavaFolderLocation targetDir, List errorList, boolean enableDebug
			, Map<String, Map<String, int[]>> propInfoCache) throws Exception 
	{
		generateRuleFunctionImplJavaStream(fn, o, oversizeStringConstants,
				ruleFnUsages, targetDir, errorList, false, enableDebug, propInfoCache);
	}
	
	/**
	 * @param fn
	 * @param o
	 * @param oversizeStringConstants
	 * @param ruleFnUsages
	 * @param targetDir
	 * @param errorList
	 * @param enableDebug
	 * @throws IOException
	 */
	public void generateRuleFunctionWrapperJavaStream(RuleFunction fn,
			Ontology o, Properties oversizeStringConstants, Map ruleFnUsages,
			JavaFolderLocation targetDir, List errorList, boolean enableDebug)
			throws IOException {
		JavaClassWriter jClass = RuleFunctionCodeGeneratorSmap
				.generateRuleFunctionWrapperClass(fn, errorList,
						oversizeStringConstants, ruleFnUsages, o);
		// jClss will be null if there were errors during parsing or generation
		if (jClass != null) {
			Folder folder = fn.getFolder();
			JavaFileWriter jfwriter = CodeGenHelper.setupJavaStreamWriter(jClass
					.getName(), ModelNameUtil.modelPathToExternalForm(folder
					.getFullPath()), targetDir,
					ModelNameUtil.RULE_SEPARATOR_CHAR);
			jfwriter.addClass(jClass);
			jfwriter.writeStream();
		}
	}
	
	/**
	 * @param fn
	 * @param o
	 * @param oversizeStringConstants
	 * @param ruleFnUsages
	 * @param targetDir
	 * @param errorList
	 * @param ignoreVirtual
	 * @param enableDebug
	 * @throws Exception
	 */
	public void generateRuleFunctionImplJavaStream(RuleFunction fn, Ontology o,
			Properties oversizeStringConstants, Map ruleFnUsages,
			JavaFolderLocation targetDir, List errorList, boolean ignoreVirtual,
			boolean enableDebug, Map<String, Map<String, int[]>> propInfoCache) throws Exception {

		RuleInfo rinfo = new RuleInfo();
		rinfo.setPath(fn.getFullPath());
		JavaClassWriter jClassWriter = RuleFunctionCodeGeneratorSmap
				.generateRuleFunctionClassNew(fn, errorList,
						oversizeStringConstants, ruleFnUsages, ignoreVirtual,
						rinfo, o, propInfoCache);
		JavaFileWriter jfwriter = CodeGenHelper.setupJavaStreamWriter(
				jClassWriter.getName(), ModelNameUtil
						.modelPathToExternalForm(fn.getFolder().getFullPath()),
				targetDir, ModelNameUtil.RULE_SEPARATOR_CHAR);
		jfwriter.addClass(jClassWriter);
		jfwriter.writeStream();
		if (enableDebug) {
			rinfo.getActionStratumMap().put(jClassWriter.getClassName(),
					new SmapStratum("RSP"));
			SmapFileWriter smwriter = CodeGenHelper.setupSmapStreamWriter(
					jClassWriter.getName(), ModelNameUtil
							.modelPathToExternalForm(fn.getFolder()
									.getFullPath()), targetDir,
					ModelNameUtil.RULE_SEPARATOR_CHAR);
			smwriter.writeSMapXmlStream(rinfo);
		}

	}

}
