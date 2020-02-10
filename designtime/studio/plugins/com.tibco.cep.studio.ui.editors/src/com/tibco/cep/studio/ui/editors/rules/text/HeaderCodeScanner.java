package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

import com.tibco.cep.studio.ui.editors.rules.ColorManager;
import com.tibco.cep.studio.ui.editors.rules.IColorConstants;


public class HeaderCodeScanner extends RuleBasedScanner {

	private static HeaderCodeScanner fInstance;
	private Token fCommentToken;
	private Token fHeaderToken;
	
	private HeaderCodeScanner(ColorManager manager) {

		// TODO : extract this information from preference settings
		// TODO : change these upon preference change
		fCommentToken = new Token(new TextAttribute(manager.getColor(IColorConstants.COMMENT)));
		fHeaderToken = new Token(new TextAttribute(manager.getColor(IColorConstants.HEADER)));
		
		List<IRule> rulesList = new ArrayList<IRule>();
		rulesList.add(new MultiLineRule("/**", "*/", fHeaderToken));
		rulesList.add(new MultiLineRule("/*", "*/", fCommentToken, (char) 0, true));
		rulesList.add(new EndOfLineRule("//", fCommentToken));
		rulesList.add(new WhitespaceRule(new WhitespaceDetector()));
		
		IRule[] rules = new IRule[rulesList.size()];
		rulesList.toArray(rules);
		setRules(rules);
	}
	
	public static HeaderCodeScanner getInstance(ColorManager manager) {
		if (fInstance == null) {
			fInstance = new HeaderCodeScanner(manager);
		}
		return fInstance;
	}
	
}
