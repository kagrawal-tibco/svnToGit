package com.tibco.cep.webstudio.client.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;

public class WebStudioResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private WebStudioResource parent;
	private List<WebStudioResource> children = new ArrayList<WebStudioResource>();
	private String id;
	private String type;
	private String icon;
	private String editorFactoryID;

	public WebStudioResource() {}

	/**
	 * @param name
	 * @param parent
	 * @param id
	 * @param type
	 * @param icon
	 * @param factory {@link IEditorFactory}
	 * @param clickHandler {@link CellDoubleClickHandler}
	 */
	public WebStudioResource(String name, 
								WebStudioResource parent, 
								String id, 
								String type, 
								String icon, 
								String editorFactoryID) {
		this.name = name;
		this.id = id;
		this.type = type;
		this.icon = icon;
		this.editorFactoryID = editorFactoryID;
		this.parent = parent;
	}

	public void addChild(WebStudioResource webStudioResource) {
		if (!children.contains(webStudioResource)) {
			children.add(webStudioResource);
		}
	}

	public String getName() {
		return name;
	}

	public WebStudioResource getParent() {
		return parent;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getIcon() {
		return icon;
	}

	public String getEditorFactoryID() {
		return editorFactoryID;
	}	

	public List<WebStudioResource> getChildren() {
		return children;
	}

}
