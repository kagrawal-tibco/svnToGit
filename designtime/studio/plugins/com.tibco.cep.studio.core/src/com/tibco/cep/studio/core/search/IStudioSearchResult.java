package com.tibco.cep.studio.core.search;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface IStudioSearchResult {

	public void addExactMatch(EObject match);
	public void addInexactMatch(EObject match);
	public List<EObject> getExactMatches();
	public List<EObject> getInexactMatches();
	public void clear();
	public void merge(IStudioSearchResult result);

}
