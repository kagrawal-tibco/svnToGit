package com.tibco.cep.webstudio.client.palette;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author sasahoo
 *
 */
public class PaletteToolGroup implements Serializable,IsSerializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2604294917102775182L;
	private String grpName, iconPath,groupId;
	private List<PaletteTool> paletteTools = new ArrayList<PaletteTool>();

	public PaletteToolGroup() {
		
	}
	
	public PaletteToolGroup(String grpName, String iconPath,String groupId) {
		this.grpName = grpName;
		this.iconPath = iconPath;
		this.groupId=groupId;
	}

	public String getToolGroupName() {
		return grpName;
	}

	public String getIconPath() {
		return iconPath;
	}

	public List<PaletteTool> getPaletteTools() {
		return paletteTools;
	}

	public void setPaletteTools(List<PaletteTool> paletteTools) {
		this.paletteTools = paletteTools;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
