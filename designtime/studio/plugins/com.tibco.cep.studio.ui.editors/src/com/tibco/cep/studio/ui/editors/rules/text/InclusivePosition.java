package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.Position;

public abstract class InclusivePosition extends Position {

	public InclusivePosition(int offset, int length) {
		super(offset, length);
	}

	public void update(int offset, int length) {
		setOffset(offset);
		setLength(length);
	}
	
}
