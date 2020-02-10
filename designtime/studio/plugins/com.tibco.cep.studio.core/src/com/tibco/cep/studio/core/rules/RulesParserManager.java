package com.tibco.cep.studio.core.rules;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;

import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

/**
 * Helper classes for parsing IFile objects
 * @see CommonRulesParserManager
 * @author rhollom
 *
 */
public class RulesParserManager extends CommonRulesParserManager {

	public static CommonTree parseRuleFile(IFile file) {
		return parseRuleFile(file, null, false);
	}
	
	public static CommonTree parseRuleFile(IFile file, boolean includeTokensInTree) {
		return parseRuleFile(file, null, includeTokensInTree);
	}

	public static CommonTree parseRuleFile(IFile file, IProblemHandler collector) {
		return parseRuleFile(file, collector, false);
	}
	
	public static CommonTree parseRuleFile(IFile file, IProblemHandler collector, boolean includeTokensInTree) {
		if (file.getLocation() == null || file.isLinked(IResource.CHECK_ANCESTORS)) {
			if (!file.exists() || !file.isSynchronized(IResource.DEPTH_ZERO)) {
				return null;
			}
			InputStream contents = null;
			try {
				contents = file.getContents();
				return parseRuleInputStream(file.getProject().getName(), contents, collector, includeTokensInTree, true);
			} catch (CoreException e) {
				e.printStackTrace();
			} finally {
				if (contents != null) {
					try {
						contents.close();
					} catch (IOException e) {
					}
				}
			}
		}

		return parseRuleFile(file.getProject().getName(), file.getLocation().toString(), collector, includeTokensInTree);
	}

	/**
	 * Helper function to get an AST based off of a String for a given source type
	 * @param document
	 * @param fSourceType
	 * @param fProjectName
	 * @return
	 */
	public static RulesASTNode getTree(IDocument document, 
			                           int sourceType, 
			                           String projectName) {
		return getTree(document, sourceType, projectName, null);
	}
	
	/**
	 * Helper function to get an AST based off of a String for a given source type
	 * @param document
	 * @param fSourceType
	 * @param fProjectName
	 * @return
	 */
	public static RulesASTNode getTree(IDocument document, 
			                           int sourceType, 
			                           String projectName,
			                           IProblemHandler handler) {
		RulesASTNode rulesAST = null;
		switch (sourceType) {
		case IRulesSourceTypes.FULL_SOURCE:
			rulesAST = (RulesASTNode) RulesParserManager.parseRuleString(projectName, document.get(), handler);
			break;

		case IRulesSourceTypes.CONDITION_SOURCE:
			rulesAST = (RulesASTNode) RulesParserManager.parseConditionsBlockString(projectName, document.get(), handler);
			break;

		case IRulesSourceTypes.PRE_CONDITION_SOURCE:
			rulesAST = (RulesASTNode) RulesParserManager.parsePreConditionsBlockString(projectName, document.get(), handler);
			break;
			
		case IRulesSourceTypes.ACTION_SOURCE:
			rulesAST = (RulesASTNode) RulesParserManager.parseActionBlockString(projectName, document.get(), handler);
			break;

		case IRulesSourceTypes.ACTION_CONTEXT_SOURCE:
			rulesAST = (RulesASTNode) RulesParserManager.parseActionContextBlockString(projectName, document.get(), handler);
			break;
			
		default:
			break;
		}

		return rulesAST;
	}

}
