package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.Position;

/**
 * This updater is inclusive of DocumentEvents that are at the
 * offset of the position's start, or at the offset of the
 * position's end.  The DefaultPositionUpdater does not do
 * this
 * 
 * @author rhollom
 *
 */
public class InclusivePositionUpdater implements IPositionUpdater {

	private String fPositionCategory;

	public InclusivePositionUpdater(String positionCategory) {
		this.fPositionCategory = positionCategory;
	}

	public void update(DocumentEvent event) {
		IDocument document = event.getDocument();
		if (document == null) {
			return;
		}
		int offset = event.getOffset();
		int length = event.getLength();
		try {
			Position[] positions = document.getPositions(fPositionCategory);
			for (Position position : positions) {
				updatePosition((InclusivePosition) position, offset, length, event);
			}
		} catch (BadPositionCategoryException e) {
			e.printStackTrace();
		}
	}

	private void updatePosition(InclusivePosition position, int eventOffset, int eventLength, DocumentEvent event) {
		int eventEnd = eventOffset + eventLength;
		int positionStart = position.getOffset();
		int positionLength = position.getLength();
		int positionEnd = positionStart + positionLength;
		
		if (positionStart > eventEnd) {
			processEventBeforePosition(position, event);
		} else if (positionEnd < eventOffset) {
			processEventAfterPosition(position, event);
		} else if (positionStart <= eventOffset && positionEnd >= eventEnd) {
			processEventWithinPosition(position, event);
		} else if (positionStart <= eventOffset) {
			processEventAfterPositionStart(position, event);
		} else if (positionEnd >= eventEnd) {
			processEventOverlappingPositionStart(position, event);
		} else {
			processPositionWithinEvent(position, event);
		}
	}
	
	/*
	 * The event occurred before the position, need to update
	 * the offset of the position based on the text typed
	 */
	private void processEventBeforePosition(InclusivePosition position, DocumentEvent event) {
		String text= event.getText();
		int length= text != null ? text.length() : 0;
		int deltaLength= length - event.getLength();
		position.setOffset(position.getOffset() + deltaLength);
	}

	/*
	 * The event occurred after the position.  No action is needed
	 */
	private void processEventAfterPosition(InclusivePosition position, DocumentEvent event) {
	}

	/*
	 * The event occurred fully within the position, inclusive.  Need to update
	 * the length and potentially the offset of the position
	 */
	private void processEventWithinPosition(InclusivePosition position, DocumentEvent event) {
		int eventOffset= event.getOffset();
		String newText= event.getText();
		if (newText == null)
			newText= ""; //$NON-NLS-1$
		int eventNewLength= newText.length();

		int deltaLength= eventNewLength - event.getLength();

		int offset= position.getOffset();
		int length= position.getLength();
		int end= offset + length;

		int includedLength= 0;
		while (includedLength < eventNewLength && Character.isJavaIdentifierPart(newText.charAt(includedLength)))
			includedLength++;
		if (includedLength == eventNewLength)
			position.setLength(length + deltaLength);
		else {
			int newLeftLength= eventOffset - offset + includedLength;

			int excludedLength= eventNewLength;
			while (excludedLength > 0 && Character.isJavaIdentifierPart(newText.charAt(excludedLength - 1)))
				excludedLength--;
			int newRightOffset= eventOffset + excludedLength;
			int newRightLength= end + deltaLength - newRightOffset;

			if (newRightLength == 0) {
				position.setLength(newLeftLength);
			} else {
				if (newLeftLength == 0) {
					position.update(newRightOffset, newRightLength);
				} else {
					position.setLength(newLeftLength);
//					addPositionFromUI(newRightOffset, newRightLength, position.getStyle());
				}
			}
		}
	}

	/*
	 * The event occurred after the start of the position, but overlaps part
	 * of the position.  Need to update the position's length
	 */
	private void processEventAfterPositionStart(InclusivePosition position, DocumentEvent event) {
		String newText= event.getText();
		if (newText == null)
			newText= ""; //$NON-NLS-1$
		int eventNewLength= newText.length();

		int includedLength= 0;
		while (includedLength < eventNewLength && Character.isJavaIdentifierPart(newText.charAt(includedLength)))
			includedLength++;
		position.setLength(event.getOffset() - position.getOffset() + includedLength);
	}

	/*
	 * The event overlaps with the start of the position, but not the end.  
	 * Need to update the position's length and offset
	 */
	private void processEventOverlappingPositionStart(InclusivePosition position, DocumentEvent event) {
		int eventOffset= event.getOffset();
		int eventEnd= eventOffset + event.getLength();

		String newText= event.getText();
		if (newText == null)
			newText= ""; //$NON-NLS-1$
		int eventNewLength= newText.length();

		int excludedLength= eventNewLength;
		while (excludedLength > 0 && Character.isJavaIdentifierPart(newText.charAt(excludedLength - 1)))
			excludedLength--;
		int deleted= eventEnd - position.getOffset();
		int inserted= eventNewLength - excludedLength;
		position.update(eventOffset + excludedLength, position.getLength() - deleted + inserted);
	}

	/*
	 * The position is within the event, delete the position
	 */
	private void processPositionWithinEvent(InclusivePosition position, DocumentEvent event) {
		position.delete();
		position.update(event.getOffset(), 0);
	}
}
