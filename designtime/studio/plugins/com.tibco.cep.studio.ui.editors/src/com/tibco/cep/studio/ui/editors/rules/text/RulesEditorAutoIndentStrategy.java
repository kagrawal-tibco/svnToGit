package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;

/**
 * Automatically sets the proper indent level after '{' or '}' 
 * has been pressed, or when a newline is inserted
 *
 */
public class RulesEditorAutoIndentStrategy extends DefaultIndentLineAutoEditStrategy {

	private void autoIndentAfterNewLine(IDocument d, DocumentCommand c) {

		if (c.offset == -1 || d.getLength() == 0)
			return;

		try {
			// find start of line
			int p= (c.offset == d.getLength() ? c.offset  - 1 : c.offset);
			IRegion info= d.getLineInformationOfOffset(p);
			int start= info.getOffset();

			// find white spaces
			int end= findEndOfWhiteSpace(d, start, c.offset);

			StringBuffer buf= new StringBuffer(c.text);
			if (end > start) {
				// append to input
				char lastNonWSChar = d.getChar(findLastNonWhiteSpace(d, start, c.offset));
				String insertString = "";
				if (lastNonWSChar == '{') {
					insertString = d.get(start, end - start)+"\t";
				} else {
					insertString = d.get(start, end - start);
				}
				buf.append(insertString);
			} else if (end == start) {
				// append to input
				char lastNonWSChar = d.getChar(findLastNonWhiteSpace(d, start, c.offset));
				String insertString = "";
				if (lastNonWSChar == '{') {
					insertString = d.get(start, end - start)+"\t";
					buf.append(insertString);
				}
			}

			c.text= buf.toString();

		} catch (Exception excp) {
			// stop work
		}
	}

	protected int findLastNonWhiteSpace(IDocument document, int offset, int end) throws BadLocationException {
		if (end >= document.getLength()) {
			end = document.getLength()-1;
		}
		while (offset < end) {
			char c= document.getChar(end);
			if (c != ' ' && c != '\t'
				&& c != '\n' && c != '\r') {
				return end;
			}
			end--;
		}
		return end;
	}
	
	/*
	 * @see org.eclipse.jface.text.IAutoEditStrategy#customizeDocumentCommand(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.DocumentCommand)
	 */
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
		if (c.length == 0 && c.text != null && TextUtilities.endsWith(d.getLegalLineDelimiters(), c.text) != -1) {
			autoIndentAfterNewLine(d, c);
		} else if (c.text.length() == 1) {
			autoIndentAfterKeypress(d, c);
		}
	}

	private void autoIndentAfterKeypress(IDocument d, DocumentCommand c) {
		switch (c.text.charAt(0)) {
		case '}':
			autoIndentAfterBrace(d, c, true);
			break;
		case '{':
			autoIndentAfterBrace(d, c, false);
			break;
		}		
	}

	private void autoIndentAfterBrace(IDocument d, DocumentCommand c, boolean findOpenBrace) {
		if (c.offset == -1 || d.getLength() == 0)
			return;

		try {
			// find start of line
			int p= (c.offset == d.getLength() ? c.offset  - 1 : c.offset);
			IRegion info= d.getLineInformationOfOffset(p);
			int start= info.getOffset();

			// find white spaces
			int end= findEndOfWhiteSpace(d, start, c.offset);
			// only auto indent if there is only whitespace leading up to the brace
			if (!beginsWithWhitespace(d, start, end)) {
				return;
			}
			int lineNum = d.getLineOfOffset(end);
			if (lineNum == 0) {
				return;
			}
			int matchingLineNum = matchingLineNumber(d, c.offset, findOpenBrace);
			if (matchingLineNum == lineNum) {
				return;
			}
			String currWS = getWhitespace(d, lineNum);
			String prevWS = getWhitespace(d, matchingLineNum);
			if (currWS.length() != prevWS.length()) { 
				StringBuffer replaceText= new StringBuffer(prevWS);
				// add the rest of the current line including the just added close bracket
				replaceText.append(d.get(end, c.offset - end));
				replaceText.append(c.text);
				// modify document command
				c.length += c.offset - start;
				c.offset= start;
				c.text= replaceText.toString();
			}

		} catch (Exception excp) {
			// stop work
		}
	}

	private int matchingLineNumber(IDocument d, int start, boolean findOpenBrace) throws BadLocationException {
		int offset = start;
		int braceCount = 0;
		if (offset >= d.getLength()) {
			offset = d.getLength()-1;
		}
		while (offset >= 0) {
			char ch = d.getChar(offset);
			if (ch == '{') {
				if (findOpenBrace && braceCount == 0) {
					return d.getLineOfOffset(offset);
				}
				braceCount++;
			}
			if (ch == '}') {
				if (!findOpenBrace && braceCount == 0) {
					return d.getLineOfOffset(offset);
				}
				braceCount--;
			}
			offset--;
		}
		return d.getLineOfOffset(start);
	}

	private boolean beginsWithWhitespace(IDocument d, int start, int end) throws BadLocationException {
		while (start < end) {
			char c = d.getChar(start);
			if (c != ' ' && c != '\t') {
				return false;
			}
			start++;
		}
		return true;
	}

	private String getWhitespace(IDocument d, int lineNumber) throws BadLocationException {
		IRegion lineInfo = d.getLineInformation(lineNumber);
		int start = lineInfo.getOffset();
		int end = start + lineInfo.getLength();

		while (start < end) {
			char c = d.getChar(start);
			if (c != ' ' && c != '\t') {
				end = start;
				break;
			}
			start++;
		}
		if (end > lineInfo.getOffset()) {
			return d.get(lineInfo.getOffset(), end-lineInfo.getOffset());
		}
		return "";
	}
	
}
