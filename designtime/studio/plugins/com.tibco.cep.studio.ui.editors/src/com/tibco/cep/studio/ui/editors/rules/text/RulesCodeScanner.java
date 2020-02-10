package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

import com.tibco.cep.studio.ui.editors.rules.ColorManager;
import com.tibco.cep.studio.ui.editors.rules.IColorConstants;


public class RulesCodeScanner extends RuleBasedScanner {

	private static RulesCodeScanner fInstance;
	private Token fCommentToken;
	private Token fHeaderToken;
	private Token fInvisibleToken;
	private Token fStringToken;
//	private Token fTagToken;
	private Token fKeywordToken;
	private Token fDefaultTextToken;
	
	// TODO : these should be calculated from the language itself
	private static final String[] DEFAULT_KEYWORDS = new String[] { "exists", "and", "not", "lock", "key", "alias", "validity", "rank", "return", "double", "long", "void", "instanceof", "int", "boolean", "for", "while", "try", "catch", "finally", "attribute", "priority", "forwardChain", "backwardChain", "if", "else", "true", "false", "null", "virtual", "rule", "rulefunction", "scope", "body", "declare", "when", "then" };
	private static final String[] TEMPLATE_KEYWORDS = new String[] { "display", "views", "bindings", "to", "ruletemplate", "actionContext", "create", "modify", "call" };
//	private static final String[] SM_KEYWORDS = new String[] { "statemachine", "composite", "concurrent", "state", "region", "simple", "pseudo-end", "pseudo-start", "onEntry", "onExit", "onTimeOut", "transition"};
	
	private RulesCodeScanner(ColorManager manager) {

		// TODO : extract this information from preference settings
		// TODO : change these upon preference change
		fCommentToken = new Token(new TextAttribute(manager.getColor(IColorConstants.COMMENT)));
		fHeaderToken = new Token(new TextAttribute(manager.getColor(IColorConstants.HEADER)));
		fInvisibleToken = new Token(new TextAttribute(manager.getColor(IColorConstants.WHITE)));
		fStringToken = new Token(new TextAttribute(manager.getColor(IColorConstants.STRING)));
//		fTagToken = new Token(new TextAttribute(manager.getColor(IColorConstants.TAG)));
		fKeywordToken = new Token(new TextAttribute(manager.getColor(IColorConstants.KEYWORD), null, SWT.BOLD));
		fDefaultTextToken = new Token(new TextAttribute(manager.getColor(IColorConstants.DEFAULT)));
		
		List<IRule> rulesList = new ArrayList<IRule>();
		rulesList.add(new MultiLineRule("/**", "*/", fHeaderToken));
		rulesList.add(new MultiLineRule("/*", "*/", fCommentToken, (char) 0, true));
		rulesList.add(new EndOfLineRule("//", fCommentToken));
		rulesList.add(new MultiLineRule("<mapping>", "</mapping>", fInvisibleToken));
		rulesList.add(new SingleLineRule("\"", "\"", fStringToken, '\\'));
//		rulesList.add(new TagRule(fTagToken));
		rulesList.add(new WhitespaceRule(new WhitespaceDetector()));
		
		KeywordRule wordRule = new KeywordRule(new RuleWordDetector(), fDefaultTextToken);
		for (String keyword : DEFAULT_KEYWORDS) {
			wordRule.addWord(keyword, fKeywordToken);
		}
		for (String keyword : TEMPLATE_KEYWORDS) {
			wordRule.addWord(keyword, fKeywordToken);
		}
//		for (String keyword : SM_KEYWORDS) {
//			wordRule.addWord(keyword, fKeywordToken);
//		}
		rulesList.add(wordRule);
		
		IRule[] rules = new IRule[rulesList.size()];
		rulesList.toArray(rules);
		setRules(rules);
	}
	
	public static RulesCodeScanner getInstance(ColorManager manager) {
		if (fInstance == null) {
			fInstance = new RulesCodeScanner(manager);
		}
		return fInstance;
	}
	
}
