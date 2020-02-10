package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class SearchPathResult {
	
	private SearchPath searchPath;
	
//	private List<PathElementResult> resultPath;
	
	private LocalElement actualResult;
	
	SearchPathResult(SearchPath searchPath, LocalElement result) {
		this.searchPath = searchPath;
		this.actualResult = result;
//		this.resultPath = new LinkedList<PathElementResult>();
	}
	
	public final SearchPath getSearchPath() {
		return searchPath;
	}
	
	void setActualResult(LocalElement actualResult) {
		this.actualResult = actualResult;
	}
	
	public final LocalElement getActualResult() {
		return actualResult;
	}
	
//	void addPathElementResult(PathElement pathElement, String actualPathElement){
//		resultPath.add(new PathElementResult(pathElement,actualPathElement));
//	}
//	
//	public List<PathElementResult> getResultPath() {
//		return resultPath;
//	}
//	
//	class PathElementResult {
//		
//		private PathElement pathElement;
//		
//		private String actualPathElement;
//
//		PathElementResult(PathElement pathElement, String actualPathElement) {
//			super();
//			this.pathElement = pathElement;
//			this.actualPathElement = actualPathElement;
//		}
//
//		public final PathElement getPathElement() {
//			return pathElement;
//		}
//
//		public final String getActualPathElement() {
//			return actualPathElement;
//		}
//		
//	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SearchPathResult[");
		sb.append("searchpath="+searchPath);
		sb.append(",result="+actualResult);
		sb.append("]");
		return sb.toString();
	}

}
