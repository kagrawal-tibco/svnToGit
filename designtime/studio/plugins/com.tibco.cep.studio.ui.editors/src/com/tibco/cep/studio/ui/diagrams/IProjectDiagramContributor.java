package com.tibco.cep.studio.ui.diagrams;

import java.util.HashMap;

public interface IProjectDiagramContributor {

	String getContentType();
	
	void generateContent(ProjectDiagramManager projectDiagramManager);
	void initialize();
	void clear();
	
	void addDiagramInfo(HashMap<String, Integer> defintionsMap);
	void appendDiagramLayoutInfo(StringBuilder builder);
	
	void setShowLinks(boolean showLinks);
	void setShowNodes(boolean showNodes);
	void setShowNodesExpanded(boolean showExpanded);
}
