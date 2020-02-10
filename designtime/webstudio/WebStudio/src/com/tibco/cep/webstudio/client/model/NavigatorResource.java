package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.smartgwt.client.widgets.tree.TreeNode;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.model.rule.instance.DisplayModel;

/**
 * NavigatiorResource extends {@link TreeNode} this defines data fields
 */
public class NavigatorResource extends TreeNode{

	private List<NavigatorResource> children = new ArrayList<NavigatorResource>();
	private boolean shouldShowAsRootNode = false;
	private String UIParent;
	private boolean isLocked = false;

//	/**
//	 * @param name
//	 * @param parent
//	 * @param id
//	 * @param type
//	 * @param icon
//	 * @param factory
//	 *            {@link IEditorFactory}
//	 * @param editorType
//	 *            {@link AbstractEditor}
//	 */
//	public NavigatorResource(String name, String parent, String id, String type, String icon,
//			ARTIFACT_TYPES editorType) {
//		setName(name);
//		setParent(parent);
//		setId(id);
//		setType(type);
//		setIcon(icon);
//		setEditorType(editorType);
//		setDisplayName(name);
//		if (editorType == null) { setEditorType(ProjectExplorerUtil.typeToEditorType(type)); }
//	}

	/**
	 * 
	 * @param name
	 * @param parent
	 * @param id
	 * @param type
	 * @param icon
	 * @param editorType
	 *            {@link AbstractEditor}
	 */
	public NavigatorResource(String name, String parent, String id, String type, String icon, ARTIFACT_TYPES editorType) {
		this(name, parent, parent, id, type, icon, editorType);
	}

	public NavigatorResource(String name, String parent, String uiParent, String id, String type, String icon, ARTIFACT_TYPES editorType) {
		setName(name);
		setParent(parent);
		setUIParent(uiParent);
		setId(id);
		setType(type);
		setIcon(icon);
		setEditorType(editorType);
		setDisplayName(name);
		if (editorType == null) { setEditorType(ProjectExplorerUtil.typeToEditorType(type)); }
	}
	
	/**
	 * Copy constructor;
	 * @param resource
	 */
	public NavigatorResource(NavigatorResource resource) {
		this.setName(resource.getName());
		this.setParent(resource.getParent());
		this.setUIParent(resource.getUIParent());
		this.setId(resource.getId());
		this.setType(resource.getType());
		this.setIcon(resource.getIcon());
		this.setEditorType(resource.getEditorType());
		if (getEditorType()== null) { setEditorType(ProjectExplorerUtil.typeToEditorType(resource.getType())); }
	}

	public void setParent(String parent) {
		setAttribute("parent", parent);
	}

	public void setId(String id) {
		setAttribute("id", id);
	}

	public void setType(String type) {
		setAttribute("type", type);
	}

	public void setName(String appName) {
		setAttribute("name", appName);
	}

//	public void setFactory(IEditorFactory factory) {
//		setAttribute("factory", factory);
//	}

	public void setEditorType(ARTIFACT_TYPES editorType) {
		setAttribute("editorType", editorType);
	}

	public String getName() {
		return getAttributeAsString("name");
	}

	public String getParent() {
			String parentId = getAttributeAsString("parent");
			if (parentId == null) {
				return null;
			}
			else if( (parentId.equals("Root") )){
				return parentId;
			}
			else{
			return getId().substring(0, getId().lastIndexOf("$"));
			}	
	}
	
	public void setUIParent(String parent) {
			UIParent = parent;
	}

	public String getUIParent() {
		return UIParent;
	}
	public String getId() {
		return getAttributeAsString("id");
	}

	public String getType() {
		return getAttributeAsString("type");
	}

//	public IEditorFactory getFactory() {
//		return (IEditorFactory) getAttributeAsObject("factory");
//	}

	public ARTIFACT_TYPES getEditorType() {
		return (ARTIFACT_TYPES) getAttributeAsObject("editorType");
	}

	public List<NavigatorResource> getChildren() {
		return children;
	}
	
	public List<NavigatorResource> getChildren(boolean sort) {
		if (sort)
			Collections.sort(children, new ResourceNameComparator()); 
		return children;
	}
	
	public String getDisplayName() {
		return getAttributeAsString("displayName");
	}
	
	public boolean getShouldShowAsRootNode() {
		return shouldShowAsRootNode;
	}
	
	public void setDisplayName(String displayName) {
		String dName = displayName;
		if (dName.indexOf(".") != -1) {
			dName = dName.substring(0, dName.indexOf("."));
		}
		setAttribute("displayName", dName);
	}
	
	public void setShouldShowAsRootNode(boolean shouldShow) {
		shouldShowAsRootNode = shouldShow;
	}
	
	public boolean isLocked() {
		if (hasLockingSupport()) {
			return isLocked;
		}
		return false;
	}

	public void setLocked(boolean isLocked) {
		if (hasLockingSupport() && this.isLocked != isLocked) {
			this.isLocked = isLocked;
			String iconPath = getIcon();
			if (iconPath != null) {
				iconPath = this.isLocked ? iconPath.replace(".", "_locked.") : iconPath.replace("_locked.", ".");
				setIcon(iconPath);
			}
		}	
	}

	public boolean hasLockingSupport() {
		return ArtifactUtil.isSupportedArtifact(this);
	}
	
	public String parseParentId() {
		String id = getId();
		return (id.indexOf("$") != -1) ? id.substring(0, id.lastIndexOf("$")) : id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof NavigatorResource)) {
			return false;
		}
		NavigatorResource navigatorResource = (NavigatorResource) obj;

		return (getId().equalsIgnoreCase(navigatorResource.getId())
				&& getType().equalsIgnoreCase(navigatorResource.getType()) && getName().equalsIgnoreCase(navigatorResource.getName()));
	}
	
	public boolean contains(String inputValue) {
		DisplayModel model = ProjectExplorerUtil.getDisplayModelForResource(this);
		String nodeName = null;
		if(model != null) {
			nodeName = model.getDisplayText();
		}
		
		if(nodeName == null) {
			nodeName = getAttributeAsString("name");
		}
		
		nodeName = nodeName.toLowerCase();
		int c = nodeName.indexOf(".");
		if(c > 0) {
			nodeName = nodeName.substring(0, nodeName.indexOf("."));
		}
        if(nodeName.contains(inputValue)) {
            return true;
        }
        return false;
    }
	
	public static class ResourceNameComparator implements Comparator<NavigatorResource> {

		@Override
		public int compare(NavigatorResource resource1, NavigatorResource resource2) {
			return resource1.getName().compareToIgnoreCase(resource2.getName());
		}		
	}
	
}