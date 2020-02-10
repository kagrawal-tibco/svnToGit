package com.tibco.cep.studio.dashboard.ui.search;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.search.SearchResult;

public class DashboardSearchResult extends SearchResult {

	public void addExactMatch(EObject match) {
		List<EObject> exactMatches = getExactMatches();
		for (EObject existing : exactMatches) {
			if (existing instanceof Entity && match instanceof Entity){
				if (((Entity)existing).getGUID().equals(((Entity) match).getGUID())){
					//Already existing
					return;
				}
			}
		}
		super.addExactMatch(match);
	}
	
	public void addInexactMatch(EObject match) {
		List<EObject> inexactMatches = getInexactMatches();
		for (EObject existing : inexactMatches) {
			if (existing instanceof Entity && match instanceof Entity){
				if (((Entity)existing).getGUID().equals(((Entity) match).getGUID())){
					//Already existing
					return;
				}
			}
		}
		super.addExactMatch(match);
	}
}
