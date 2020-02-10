package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DrillDownTreePath {
	
	private String rawPath;
	
	private LinkedList<DrillDownTreePathElement> pathElements;
	
	public DrillDownTreePath(String rawPath, DrillDownTreePathElement[] pathElements) {
		this.rawPath = rawPath;
		this.pathElements = new LinkedList<DrillDownTreePathElement>(Arrays.asList(pathElements));
	}
	
	public String getRawPath() {
		return rawPath;
	}
	
	public List<DrillDownTreePathElement> getPathElements() {
		return Collections.unmodifiableList(pathElements);
	}
	
	public DrillDownTreePathElement getRoot() {
		return pathElements.getFirst();
	}
	
	public DrillDownTreePathElement getLeaf() {
		return pathElements.getLast();
	}
	
	public DrillDownTreePathElement getParent(DrillDownTreePathElement pathElement){
		int idx = pathElements.indexOf(pathElement);
		if (idx <= 0) {
			return null;
		}
		return pathElements.get(idx - 1);
	}
	
	public DrillDownTreePathElement getChild(DrillDownTreePathElement pathElement){
		int idx = pathElements.indexOf(pathElement);
		if (idx == pathElements.size() - 1) {
			return null;
		}
		return pathElements.get(idx + 1);
	}
	
	public DrillDownTreePathElement readTrailingFieldValuePathElements(List<FieldValueDrillDownTreePathElement> trailingFieldValuePathElements) {
		DrillDownTreePathElement pathElement = getLeaf();
		while (pathElement instanceof FieldValueDrillDownTreePathElement) {
			trailingFieldValuePathElements.add((FieldValueDrillDownTreePathElement) pathElement);
			pathElement = getParent(pathElement);
		}
		return pathElement;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rawPath == null) ? 0 : rawPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DrillDownTreePath other = (DrillDownTreePath) obj;
		if (rawPath == null) {
			if (other.rawPath != null)
				return false;
		} else if (!rawPath.equals(other.rawPath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return rawPath;		
	}

}