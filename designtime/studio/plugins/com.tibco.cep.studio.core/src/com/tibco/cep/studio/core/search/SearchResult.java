package com.tibco.cep.studio.core.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class SearchResult implements IStudioSearchResult {

	private List<EObject> fExactMatches = new ArrayList<EObject>();
	private List<EObject> fInexactMatches = new ArrayList<EObject>();
	
	public SearchResult() {
		super();
	}
	
	public void addExactMatch(EObject match) {
		fExactMatches.add(match);
	}
	
	public void addInexactMatch(EObject match) {
		fInexactMatches.add(match);
	}

	public List<EObject> getExactMatches() {
		return fExactMatches;
	}

	public List<EObject> getInexactMatches() {
		return fInexactMatches;
	}
	
	public void clear() {
		fExactMatches.clear();
		fInexactMatches.clear();
	}

	public void merge(IStudioSearchResult result) {
		if (result == null) {
			return;
		}
		List<EObject> exactMatches = result.getExactMatches();
		for (EObject object : exactMatches) {
			if (!this.getExactMatches().contains(object)) {
				this.addExactMatch(object);
			}
		}
		List<EObject> inexactMatches = result.getInexactMatches();
		for (EObject object : inexactMatches) {
			if (!this.getInexactMatches().contains(inexactMatches)) {
				this.addInexactMatch(object);
			}
		}

	}
	
}
