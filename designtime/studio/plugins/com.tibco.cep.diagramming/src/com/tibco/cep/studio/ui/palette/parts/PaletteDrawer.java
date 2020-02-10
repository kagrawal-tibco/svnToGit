package com.tibco.cep.studio.ui.palette.parts;

import java.util.ArrayList;
import java.util.List;

public class PaletteDrawer {
	
	private List<PaletteEntry> 	paletteEntries = new ArrayList<PaletteEntry>();
	private String 				title;
	private String				label;
	private String 				toolTip;
	private String 				id;
	private String 				image;
	private Palette 			palette;
	private boolean 			isGlobal;
	private boolean				custom;
	
	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}

	public PaletteDrawer(String title, String toolTip, String image, Palette palette, boolean custom) {
		this(title, null, toolTip, image, palette, custom);
	}
	
	public PaletteDrawer(String title, String label, String toolTip, String image, Palette palette, boolean custom) {
		this.title = title;
		this.label = label;
		this.toolTip = toolTip;
		this.image = image;
		this.palette = palette;
		this.custom = custom;
	}

	public boolean addPaletteEntry(PaletteEntry entry) {
		if (!paletteEntries.contains(entry)) {
			return paletteEntries.add(entry);
		}
		return false;
	}
	
	public boolean removePaletteEntry(PaletteEntry entry) {
		if (paletteEntries.contains(entry)) {
			return paletteEntries.remove(entry);
		}
		return false;
	}
	
	

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<PaletteEntry> getPaletteEntries() {
		return paletteEntries;
	}

	public Palette getPalette() {
		return palette;
	}
	
}
