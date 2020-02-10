package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.WordRule;

public class KeywordRule extends WordRule {

	private StringBuffer fBuffer = new StringBuffer();

	public KeywordRule(IWordDetector detector, IToken defaultToken) {
		super(detector, defaultToken);
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
        int c = scanner.read();
        if (fDetector.isWordStart((char) c)) {
        	if (fColumn == UNDEFINED || (fColumn == scanner.getColumn() - 1)) {
        		fBuffer.setLength(0);
        		do {
        			while (fDetector.isWordPart((char) c)) {
        				fBuffer.append((char) c);
        				c = scanner.read();
        			}
        			
        		} while (c != ICharacterScanner.EOF
        				&& fDetector.isWordPart((char) c));
        		scanner.unread();
        		
        		String buffer = fBuffer.toString();
        		IToken token = (IToken) fWords.get(buffer);
        		if (token != null)
        			return token;

        		while (fBuffer.length() > 0) {
        			// unread a word at a time until we get a token, 
        			// only makes sense if we have multi-word keywords,
        			// but still necessary for single word keywords
        			while (fBuffer.length() > 0
        					&& fDetector.isWordPart(fBuffer.charAt(fBuffer
        							.length() - 1))) {
        				scanner.unread();
        				fBuffer.setLength(fBuffer.length() - 1);
        			}
        			if (fBuffer.length() > 0) {
        				// one more time
        				scanner.unread();
        				fBuffer.setLength(fBuffer.length() - 1);
        			}
        			token = (IToken) fWords.get(fBuffer.toString());
        			if (token != null)
        				return token;
        		}
        		return super.evaluate(scanner);
        	}
        }

        scanner.unread();
        return super.evaluate(scanner);
    }
	
}
