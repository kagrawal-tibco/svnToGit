package com.tibco.cep.webstudio.client.palette;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class PaletteTool implements Serializable,IsSerializable {
	private  String id;
	private String parentGroup;
	private String toolName;
	private String toolType;
	private String tooltip;
	private String iconPath;
	private String elementType;
	private String emfType;
	private String extndType;

	public PaletteTool() {
		
	}
	
	/**
	 * @param toolName
	 * @param toolType
	 */
	public PaletteTool(String toolName, String toolType) {
		this(toolName, toolType, null, null, null, null, null, "General",null);
	}

	/**
	 * @param toolName
	 * @param toolType
	 * @param iconPath
	 * @param deviceType
	 * @param emfType
	 * @param extndType
	 * @param parentGroup
	 */
	public PaletteTool(String toolName,
						String toolType,
						String tooltip,
						String iconPath,
						String elementType, 
						String emfType, 
						String extndType, 
						String parentGroup,String id) {
		this.toolName = toolName;
		this.toolType = toolType;
		this.tooltip = tooltip;
		this.iconPath = iconPath;
		this.elementType = elementType;
		this.emfType = emfType;
		this.extndType = extndType;
		this.parentGroup = parentGroup;
		this.id=id;
	}

	public String getToolName() {
		return this.toolName;
	}

	public String getToolType() {
		return this.toolType;
	}
	
	public String getTooltip() {
		return tooltip;
	}

	public String getIconPath() {
		return this.iconPath;
	}

	public String getElementType() {
		return elementType;
	}
	
	public String getEmfType() {
		return emfType;
	}

	public String getExtendedType() {
		return extndType;
	}

	public String getParentGroup() {
		return parentGroup;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
