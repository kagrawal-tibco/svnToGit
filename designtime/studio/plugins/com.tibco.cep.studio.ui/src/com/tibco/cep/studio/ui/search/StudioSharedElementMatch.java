package com.tibco.cep.studio.ui.search;

import org.eclipse.search.ui.text.Match;

import com.tibco.cep.studio.core.index.model.SharedElement;

public class StudioSharedElementMatch extends Match implements IStudioMatch {

	private IStudioNestedMatch fNestedMatch;
	
	public StudioSharedElementMatch(SharedElement element, int offset, int length) {
		super(element, offset, length);
	}

	public IStudioNestedMatch getNestedMatch() {
		return fNestedMatch;
	}

	public void setNestedMatch(IStudioNestedMatch nestedMatch) {
		fNestedMatch = nestedMatch;
	}

}
