package com.tibco.cep.webstudio.client.process;

import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessNavigatorResource extends NavigatorResource {
	private String processType;

	/**
	 * @param name
	 * @param parent
	 * @param id
	 * @param type
	 * @param icon
	 * @param editorType
	 */
	public ProcessNavigatorResource(String name,
			String parent,
			String id,
			String type,
			String icon,
			ARTIFACT_TYPES editorType) {
		super(name, parent, id, type, icon, editorType);
	}

	/**
	 * Copy constructor;
	 * @param resource
	 */
	public ProcessNavigatorResource(ProcessNavigatorResource resource) {
		this(resource.getName(), resource.getParent(), resource.getId(),
				resource.getType(), resource.getIcon(), resource
						.getEditorType());
	}

	/**
	 * @return
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * @param processType
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}
}
