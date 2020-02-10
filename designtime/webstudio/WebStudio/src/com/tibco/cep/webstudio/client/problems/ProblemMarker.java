package com.tibco.cep.webstudio.client.problems;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author sasahoo
 */
public class ProblemMarker {
	public static final String MARKER = "com.tibco.cep.webstudio.marker"; //$NON-NLS-1$
	public static final String SEVERITY = "severity"; //$NON-NLS-1$
	
	public static final String MESSAGE = "Description"; //$NON-NLS-1$
	public static final String LOCATION = "Location"; //$NON-NLS-1$
	public static final String LINE_NUMBER = "LineNumber"; //$NON-NLS-1$
	
	public static final int SEVERITY_ERROR = 2;//$NON-NLS-1$
	public static final int SEVERITY_WARNING = 1;//$NON-NLS-1$
	public static final int SEVERITY_INFO = 0;//$NON-NLS-1$
	
	public static final String RESOURCE = "Resource";
	public static final String PATH = "Path";
	public static final String TYPE = "Type";
	public static final String URI = "URI";
	public static final String PROJECT = "Project";

	private String resource, uri, path, extension, type, location, message, project;
	private int severity;
	private Map<String, Object> attributes = new HashMap<String, Object>();

	public ProblemMarker(String resource, String uri, String path, String extension, String type) {
		this(resource, uri, null, path, extension, type, null, null);
	}
	
	public ProblemMarker(String uri, 
			             String project, 
			             String path, 
			             String extension, 
			             String type, 
			             String location, 
			             String message) {
		this(null, uri, project, path, extension, type, location, message);
	}
	
	public ProblemMarker(String resource, 
			             String uri, 
			             String project, 
			             String path, 
			             String extension, 
			             String type, 
			             String location, 
			             String message) {
		setProject(project);
		setType(type);
		setResource(resource);
		setPath(path);
		setURI(uri);
		setLocation(location);
		setMessage(message);
		setExtension(extension);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		
		ProblemMarker problemMarker = (ProblemMarker) obj;
		return (problemMarker.getURI().equals(this.getURI()));
	}


	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
	public int getSeverity() {
		return severity;
	}
}