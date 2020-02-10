package com.tibco.cep.studio.core.search;

import org.eclipse.core.runtime.IProgressMonitor;

public interface ISourceSearchParticipant extends ISearchParticipant {

	public SearchResult searchSource(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor);
	
}
