package com.tibco.cep.webstudio.client.decisiontable;

import com.tibco.cep.webstudio.client.problems.ProblemMarker;

public class DecisionTableProblemMarker extends ProblemMarker {

	private String pageNum = null;
	private int rowNum = -1; 

	public DecisionTableProblemMarker(String resource, String uri,
			String project, String path, String extension, String type,
			String location, String message, String pageNum) {
		super(resource, uri, project, path, extension, type, location, message);
		this.pageNum = pageNum;
	}

	public DecisionTableProblemMarker(String uri, String project, String path,
			String extension, String type, String location, String message, String pageNum) {
		super(uri, project, path, extension, type, location, message);
		this.pageNum = pageNum;
	}

	public DecisionTableProblemMarker(String resource, String uri, String path,
			String extension, String type, String pageNum) {
		super(resource, uri, path, extension, type);
		this.pageNum = pageNum;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	public String[] getRuleAndColumnId() {
		String location = getLocation();
		if (location != null) {
			return location.split("_");
		}
		
		return new String[0];
	}
}
