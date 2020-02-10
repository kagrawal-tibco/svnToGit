package com.tibco.cep.decision.table.codegen;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.Ontology;

public class DTCodegenGlobalContext {
	public Ontology o;
	public File targetDir;
	public List<RuleError> errors;
	public Properties oversizeStringConstants;
	@SuppressWarnings("rawtypes")
	public Map ruleFnUsages;
	boolean enableDebug;
	public JavaFolderLocation targetLoc;
	public Map<String, Map<String, int[]>> propInfoCache;
	
	@SuppressWarnings("rawtypes")
	public DTCodegenGlobalContext(Ontology o, File targetDir,
			List<RuleError> errors, Properties oversizeStringConstants,
			Map ruleFnUsages, boolean enableDebug, Map<String, Map<String, int[]>> propInfoCache) {
		this.o = o;
		this.targetDir = targetDir;
		this.errors = errors;
		this.oversizeStringConstants = oversizeStringConstants;
		this.ruleFnUsages = ruleFnUsages;
		this.enableDebug = enableDebug;
		this.propInfoCache = propInfoCache;
	}
	
	@SuppressWarnings("rawtypes")
	public DTCodegenGlobalContext(Ontology o, JavaFolderLocation targetDir,
			List<RuleError> errors, Properties oversizeStringConstants,
			Map ruleFnUsages, boolean enableDebug, Map<String, Map<String, int[]>> propInfoCache) {
		this.o = o;
		this.targetLoc = targetDir;
		this.errors = errors;
		this.oversizeStringConstants = oversizeStringConstants;
		this.ruleFnUsages = ruleFnUsages;
		this.enableDebug = enableDebug;
		this.propInfoCache = propInfoCache;
	}
}
