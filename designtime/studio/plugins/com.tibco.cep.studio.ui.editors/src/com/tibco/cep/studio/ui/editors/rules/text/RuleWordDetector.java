package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.rules.IWordDetector;

public class RuleWordDetector implements IWordDetector {

	public boolean isWordPart(char c) {
//		return Character.isLetterOrDigit(c);
		return Character.isJavaIdentifierStart(c);
	}

	public boolean isWordStart(char c) {
//		return Character.isLetterOrDigit(c);
		return Character.isJavaIdentifierPart(c);
	}

}
