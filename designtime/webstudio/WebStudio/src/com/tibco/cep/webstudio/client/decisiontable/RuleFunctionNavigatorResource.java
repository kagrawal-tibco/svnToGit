package com.tibco.cep.webstudio.client.decisiontable;

import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleFunctionNavigatorResource extends NavigatorResource {

	private boolean VRF = false;

	public RuleFunctionNavigatorResource(String name, String parent, String id,
			String type, String icon, ARTIFACT_TYPES editorType) {
		super(name, parent, id, type, icon, editorType);
	}
	
	public boolean isVRF() {
		return VRF;
	}

	public void setVRF(boolean vRF) {
		VRF = vRF;
	}
	
	/**
	 * Copy constructor;
	 * @param resource
	 */
	public RuleFunctionNavigatorResource(RuleFunctionNavigatorResource resource) {
		this(resource.getName(), resource.getParent(), resource.getId(),
				resource.getType(), resource.getIcon(), resource
						.getEditorType());
		this.VRF = resource.isVRF();
	}
}
