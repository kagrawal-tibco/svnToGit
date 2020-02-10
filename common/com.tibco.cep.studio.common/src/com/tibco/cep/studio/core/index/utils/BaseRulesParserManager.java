package com.tibco.cep.studio.core.index.utils;

import java.io.InputStream;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.IProblemHandler;

/**
 * Helper classes for parsing IFile objects
 * @see CommonRulesParserManager
 * @author rhollom
 *
 */
public class BaseRulesParserManager extends CommonRulesParserManager {

	public static CommonTree parseRuleFile(String projectName,Path file) {
		return parseRuleFile(projectName,file, null, false);
	}
	
	public static CommonTree parseRuleFile(String projectName,Path file, boolean includeTokensInTree) {
		return parseRuleFile(projectName,file, null, includeTokensInTree);
	}

	public static CommonTree parseRuleFile(String projectName,Path file, IProblemHandler collector) {
		return parseRuleFile(projectName,file, collector, false);
	}
	
	public static CommonTree parseRuleFile(String projectName,Path file, IProblemHandler collector, boolean includeTokensInTree) {
		return parseRuleFile(projectName, file.toOSString(), collector, includeTokensInTree);
	}
	
	public static CommonTree parseRuleInputStream(String projectName,InputStream is, boolean includeTokensInTree) {
		return parseRuleInputStream(projectName,is, null, includeTokensInTree, false);
	}

}
