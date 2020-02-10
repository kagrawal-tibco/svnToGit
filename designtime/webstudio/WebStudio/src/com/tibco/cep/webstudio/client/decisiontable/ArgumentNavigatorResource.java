package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getArgumentIcon;

import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentResource;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
/**
 * 
 * @author sasahoo
 *
 */
public class ArgumentNavigatorResource extends NavigatorResource {

	protected ArgumentResource resource;

	public ArgumentResource getResource() {
		return resource;
	}

	public void setResource(ArgumentResource resource) {
		this.resource = resource;
	}

	public ArgumentNavigatorResource(String name, String parent,
			String id, String type, String icon, ARTIFACT_TYPES editorType, ArgumentResource resource) {
		super(name, parent, id, type, icon, editorType);
		this.resource = resource;
		setIcon(getArgumentIcon(PROPERTY_TYPES.get(resource.getType())));
	}
	
	public boolean isParent() {
		return false;
	}
	
	/**
	 * Copy constructor;
	 * @param resource
	 */
	public ArgumentNavigatorResource(ArgumentNavigatorResource resource) {
		this(resource.getName(), resource.getParent(), resource.getId(),
				resource.getType(), resource.getIcon(), resource
						.getEditorType(), resource.getResource());
	}

}
