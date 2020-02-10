package com.tibco.cep.webstudio.client.decisiontable;

import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableNavigatorResource extends NavigatorResource {

	/**
	 * @param name
	 * @param parent
	 * @param id
	 * @param type
	 * @param icon
	 * @param editorType
	 */
	public DecisionTableNavigatorResource(String name,
			String parent,
			String uiParent,
			String id,
			String type,
			String icon,
			ARTIFACT_TYPES editorType) {
		super(name, parent, uiParent, id, type, icon, editorType);
	}

	public DecisionTableNavigatorResource(String name,
			String parent,
			String id,
			String type,
			String icon,
			ARTIFACT_TYPES editorType) {
		this(name, parent, parent, id, type, icon, editorType);
	}
	
	public DecisionTableNavigatorResource(DecisionTableNavigatorResource resource) {
		this(resource.getName(), resource.getParent(), resource.getUIParent(), resource.getId(),
				resource.getType(), resource.getIcon(), resource
						.getEditorType());
	}

}
