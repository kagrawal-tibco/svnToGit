package com.tibco.cep.studio.core.index.utils;

import java.io.File;
import java.io.InputStream;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.rules.IProblemHandler;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class BaseRuleElementCreator {

	private boolean fIncludeBodyText;

	public BaseRuleElementCreator() {
		this(false);
	}

	public BaseRuleElementCreator(boolean includeBodyText) {
		this.fIncludeBodyText = includeBodyText;
	}
	
	public RuleElement createRuleElement(String projectName,File projectPath,File file) {
		RulesASTNode tree = (RulesASTNode) BaseRulesParserManager.parseRuleFile(projectName,file, fIncludeBodyText);
		if (tree == null) {
//			System.out.println("Could not create rule element for "+file.getName());
			return null;
		}
		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, projectName);
		tree.accept(visitor);
		RuleElement element = (RuleElement) tree.getData("element");
		String fileName = new Path(file.getPath()).removeFileExtension().lastSegment();
		if (!fileName.equals(element.getName())) {
			element.setName(fileName);
		}
		if (!CommonIndexUtils.getFileFolder(new Path(projectPath.getPath()),new Path(file.getPath())).equals(element.getFolder())) {
			element.setFolder(CommonIndexUtils.getFileFolder(new Path(projectPath.getPath()),new Path(file.getPath())));
		}
		element.setIndexName(projectName);
		element.setElementType(CommonIndexUtils.getIndexType(new Path(file.getPath())));
		Compilable rule = visitor.getRule();
		if (rule instanceof RuleFunction) {
			element.setVirtual(((RuleFunction)rule).isVirtual());
		} else {
			element.setVirtual(false);
		}
		element.setRule(rule);
		return element;
	}
	
	public RuleElement createRuleElement(String projectName,String rootUri,String entityUri,InputStream is) {
		return createRuleElement(projectName, rootUri, entityUri, is,new DefaultProblemHandler());
	}
	
	public RuleElement createRuleElement(String projectName,String rootUri,String entityUri,InputStream is,IProblemHandler problemHandler) {
		RulesASTNode tree = (RulesASTNode) BaseRulesParserManager.parseRuleInputStream(projectName,is,problemHandler,fIncludeBodyText,false);
		if (tree == null) {
//			System.out.println("Could not create rule element for "+entityUri);
			return null;
		}
		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, projectName);
		tree.accept(visitor);
		RuleElement element = (RuleElement) tree.getData("element");
		String fileName = new Path(entityUri).removeFileExtension().lastSegment();
		if (!fileName.equals(element.getName())) {
			element.setName(fileName);
		}
		if (!CommonIndexUtils.getFileFolder(new Path(rootUri),new Path(entityUri)).equals(element.getFolder())) {
			element.setFolder(CommonIndexUtils.getFileFolder(new Path(rootUri),new Path(entityUri)));
		}
		element.setIndexName(projectName);
		element.setElementType(CommonIndexUtils.getIndexType(new Path(entityUri)));
		Compilable rule = visitor.getRule();
		if (rule instanceof RuleFunction) {
			element.setVirtual(((RuleFunction)rule).isVirtual());
		} else {
			element.setVirtual(false);
		}
		element.setRule(rule);
		return element;
	}
	
}
