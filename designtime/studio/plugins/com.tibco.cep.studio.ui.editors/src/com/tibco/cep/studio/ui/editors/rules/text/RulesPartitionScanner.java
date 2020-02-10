package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class RulesPartitionScanner extends RuleBasedPartitionScanner implements IRulesEditorPartitionTypes {

	public RulesPartitionScanner() {

		IToken comment = new Token(COMMENT);
		IToken string = new Token(STRING);
		IToken header = new Token(HEADER);
//		IToken tag = new Token(XML_TAG);

		List<IPredicateRule> predicates = new ArrayList<IPredicateRule>();

		predicates.add(new MultiLineRule("/**", "*/", header));
		predicates.add(new MultiLineRule("/*", "*/", comment, (char) 0, true));
		predicates.add(new EndOfLineRule("//", comment));
		predicates.add(new SingleLineRule("\"", "\"", string, '\\'));
		//		predicateRules[2] = new TagRule(tag);
		IPredicateRule[] predicateRules = new IPredicateRule[predicates.size()];
		predicates.toArray(predicateRules);
		
		setPredicateRules(predicateRules);
	}
}
