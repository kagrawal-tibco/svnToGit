package com.tibco.cep.studio.core.search;

import org.eclipse.core.runtime.IProgressMonitor;

public interface ISearchParticipant {

	/**
	 * Special feature ID indicating that the search match is the definition of the element
	 * itself
	 */
	public static final int DEFINITION_FEATURE = -99;
	
	public SearchResult search(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor);
	
	/**
	 * Returns true if this search participant
	 * can find objects of the resolved element type
	 * @param resolvedElement
	 * @return
	 */
//	public boolean canFind(Object resolvedElement);
	
}
