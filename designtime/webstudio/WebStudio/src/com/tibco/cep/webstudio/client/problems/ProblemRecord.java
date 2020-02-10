package com.tibco.cep.webstudio.client.problems;

import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 
 * @author sasahoo
 */
public class ProblemRecord extends ListGridRecord {

	private ProblemMarker marker;

	/**
	 * Default constructor for Problem Record
	 * 
	 * @param marker
	 */
	public ProblemRecord(ProblemMarker marker) {
		this.marker = marker;
		setAttribute(ProblemMarker.MESSAGE, (marker.getMessage() == null) ? "" : marker.getMessage());
//		setAttribute(ProblemMarker.RESOURCE, (marker.getResource() == null) ? "" : marker.getResource());
		setAttribute(ProblemMarker.PROJECT, (marker.getProject() == null) ? "" : marker.getProject());
		setAttribute(ProblemMarker.PATH, (marker.getPath() == null) ? "" : marker.getPath());
		setAttribute(ProblemMarker.LOCATION, (marker.getLocation() == null) ? "" : marker.getLocation());
		setAttribute(ProblemMarker.TYPE, (marker.getType() == null) ? "" : marker.getType());
	}
	
	public ProblemMarker getMarker() {
		return marker;
	}
}