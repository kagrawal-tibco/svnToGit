package com.tibco.cep.studio.core.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

public class MultiSearchParticipant implements ISearchParticipant {

	private List<ISearchParticipant> fParticipants = new ArrayList<ISearchParticipant>();
	
	public MultiSearchParticipant() {
		super();
	}

	@Override
	public SearchResult search(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor) {
		SearchResult result = new SearchResult();
		for (ISearchParticipant participant : fParticipants) {
			result.merge(participant.search(resolvedElement, projectName, nameToFind, monitor));
		}
		return result;
	}

	public void addParticipant(ISearchParticipant participant) {
		if (!fParticipants.contains(participant)) {
			fParticipants.add(participant);
		}
	}
	
	public void removeParticipant(ISearchParticipant participant) {
		if (fParticipants.contains(participant)) {
			fParticipants.remove(participant);
		}
	}
	
}
