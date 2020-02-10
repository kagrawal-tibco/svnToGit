package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextUtilities;

/**
 * Automatically inserts '*' after newlines inside of header sections
 * in rules/rule functions.
 * 
 * Derived from JavaDocAutoIndentStrategy
 *
 */
public class HeaderAutoEditStrategy extends DefaultIndentLineAutoEditStrategy {

	@Override
	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		if (command.text != null && command.length == 0) {
			String[] lineDelimiters = document.getLegalLineDelimiters();
			int index = TextUtilities.endsWith(lineDelimiters, command.text);
			if (index > -1) {
				if (lineDelimiters[index].equals(command.text)) {
					indentAfterNewLine(document, command);
				}
				return;
			}
		}

		super.customizeDocumentCommand(document, command);
	}

	private void indentAfterNewLine(IDocument document, DocumentCommand command) {

		int offset = command.offset;
		if (offset == -1 || document.getLength() == 0) {
			return;
		}

		try {
			int p = (offset == document.getLength() ? offset - 1 : offset);
			IRegion line = document.getLineInformationOfOffset(p);

			int lineOffset = line.getOffset();
			int firstNonWS = findEndOfWhiteSpace(document, lineOffset, offset);
			if (firstNonWS < lineOffset) {
				// can't do anything
				return;
			}

			StringBuffer buf = new StringBuffer(command.text);
			IRegion prefix = findPrefixRange(document, line);
			String indentation = document.get(prefix.getOffset(), prefix
					.getLength());
			int lengthToAdd = Math.min(offset - prefix.getOffset(), prefix
					.getLength());

			buf.append(indentation.substring(0, lengthToAdd));

			if (firstNonWS < offset) {
				if (document.getChar(firstNonWS) == '/') {
					// We're on the first line of the header, can't use previous indent level
					buf.append(" * ");
				}
			}

			// move the caret behind the prefix, even if we do not have to insert it.
			if (lengthToAdd < prefix.getLength()) {
				command.caretOffset = offset + prefix.getLength() - lengthToAdd;
			}
			command.text = buf.toString();

		} catch (BadLocationException e) {
		}
	}

	private IRegion findPrefixRange(IDocument document, IRegion line)
			throws BadLocationException {
		int lineOffset = line.getOffset();
		int lineEnd = lineOffset + line.getLength();
		int indentEnd = findEndOfWhiteSpace(document, lineOffset, lineEnd);
		if (indentEnd < lineEnd && document.getChar(indentEnd) == '*') {
			indentEnd++;
			while (indentEnd < lineEnd && document.getChar(indentEnd) == ' ') {
				indentEnd++;
			}
		}
		return new Region(lineOffset, indentEnd - lineOffset);
	}
	
}
