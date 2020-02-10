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
public class Palette implements Serializable,IsSerializable {

	private List<PaletteToolGroup> paletteToolGroup = new ArrayList<PaletteToolGroup>();

	public Palette() {
		
	}
	
	public List<PaletteToolGroup> getPaletteToolGroup() {
		return paletteToolGroup;
	}

	public void setPaletteTools(List<PaletteToolGroup> paletteToolGroup) {
		this.paletteToolGroup = paletteToolGroup;
	}
	
}
