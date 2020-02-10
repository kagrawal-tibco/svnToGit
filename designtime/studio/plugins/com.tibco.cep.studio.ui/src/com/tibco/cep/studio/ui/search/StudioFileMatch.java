package com.tibco.cep.studio.ui.search;

import org.eclipse.core.resources.IFile;
import org.eclipse.search.ui.text.Match;

public class StudioFileMatch extends Match implements IStudioMatch {

	private IStudioNestedMatch fNestedMatch;
	
	public StudioFileMatch(IFile element, int offset, int length) {
		super(element, offset, length);
	}

	public IStudioNestedMatch getNestedMatch() {
		return fNestedMatch;
	}

	public void setNestedMatch(IStudioNestedMatch nestedMatch) {
		fNestedMatch = nestedMatch;
	}

}
