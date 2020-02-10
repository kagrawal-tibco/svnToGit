package com.tibco.cep.webstudio.client.decisiontable;

import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

/**
 * 
 * @author sasahoo
 *
 */
public class ParentArgumentNavigatorResource extends ArgumentNavigatorResource {

	public ParentArgumentNavigatorResource(String name, String parent,
			String id, String type, String icon, ARTIFACT_TYPES editorType, ArgumentResource resource) {
		super(name, parent, id, type, icon, editorType, resource);
	}
	
	public boolean isParent() {
		return true;
	}
	
	/**
	 * Copy constructor;
	 * @param resource
	 */
	public ParentArgumentNavigatorResource(ParentArgumentNavigatorResource resource) {
		this(resource.getName(), resource.getParent(), resource.getId(),
				resource.getType(), resource.getIcon(), resource
						.getEditorType(), resource.getResource());
	}
}
