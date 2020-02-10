package com.tibco.cep.webstudio.client.model;

import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

/**
 * Tree Node representation for Rule Template Instance
 * 
 * @author Vikram Patil
 */
public class RuleTemplateInstanceNavigatorResource extends NavigatorResource {

//	public RuleTemplateInstanceNavigatorResource(String name,
//													String parent,
//													String id,
//													String type,
//													String icon,
//													IEditorFactory factory,
//													ARTIFACT_TYPES editorType) {
//		super(name, parent, id, type, icon, factory, editorType);
//	}

	public RuleTemplateInstanceNavigatorResource(String name,
													String parent,
													String id,
													String type,
													String icon,
													ARTIFACT_TYPES editorType) {
		this(name, parent, parent, id, type, icon, editorType);
	}

	public RuleTemplateInstanceNavigatorResource(String name,
			String parent,
			String uiParent,
			String id,
			String type,
			String icon,
			ARTIFACT_TYPES editorType) {
	super(name, parent, uiParent, id, type, icon, editorType);
}
	/**
	 * Copy constructor;
	 * @param resource
	 */
	public RuleTemplateInstanceNavigatorResource (
			RuleTemplateInstanceNavigatorResource resource) {
		this(resource.getName(), resource.getParent(), resource.getUIParent(),resource.getId(),
				resource.getType(), resource.getIcon(), resource
						.getEditorType());
	}
}
