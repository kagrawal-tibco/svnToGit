package com.tibco.cep.studio.ui.palette.parts;

public class SeparatorPaletteEntry extends PaletteEntry {

	public SeparatorPaletteEntry(PaletteDrawer drawer) {
		this(null, "", "", null, drawer);
	}
	
	public SeparatorPaletteEntry(String id, String title, String toolTip,
			String image, PaletteDrawer drawer) {
		super(id, title, toolTip, image, drawer, false);
	}

	@Override
	protected void stateChanged() {

	}

}
