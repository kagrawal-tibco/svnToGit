package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;

public class HighlightedPosition extends InclusivePosition implements Comparable<HighlightedPosition> {

	TextAttribute fAttribute;

	public HighlightedPosition(int offset, int length, TextAttribute att) {
		super(offset, length);
		this.fAttribute = att;
	}
	
	public StyleRange createStyleRange() {

		int style= fAttribute.getStyle();
		int fontStyle= style & (SWT.ITALIC | SWT.BOLD | SWT.NORMAL);
		StyleRange styleRange= new StyleRange(getOffset(), getLength(), fAttribute.getForeground(), fAttribute.getBackground(), fontStyle);
		styleRange.strikeout= (style & TextAttribute.STRIKETHROUGH) != 0;
		styleRange.underline= (style & TextAttribute.UNDERLINE) != 0;
//		styleRange.underlineStyle = SWT.UNDERLINE_SQUIGGLE;
//		styleRange.underlineColor = ColorConstants.red;
		
		return styleRange;
	}

	public TextAttribute getStyle() {
		return fAttribute;
	}

	public int compareTo(HighlightedPosition o) {
		return getOffset() > o.getOffset() ? 1 : -1;
	}
	
}
