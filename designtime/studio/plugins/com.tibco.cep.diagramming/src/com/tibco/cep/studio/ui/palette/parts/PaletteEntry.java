package com.tibco.cep.studio.ui.palette.parts;

import org.eclipse.swt.widgets.Control;

public abstract class PaletteEntry {
	
	public static final int STATE_NOT_SELECTED 		= 0;
	public static final int STATE_SELECTED 			= 1;
	public static final int STATE_DEFAULT_SELECTED 	= 2;
	
	private String 			id;
	private String 			title;
	private String			label;
	private int 			state;
	private String 			toolTip;
	private String 			image;
	private PaletteDrawer 	drawer;
	private Control control;
	private boolean custom;
	
	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public PaletteEntry(String id, String title, String toolTip, String image, PaletteDrawer drawer, boolean custom) {
		this(id, title, null, toolTip, image, drawer, custom);
	}
	
	public PaletteEntry(String id, String title, String label, String toolTip, String image, PaletteDrawer drawer, boolean custom) {
		this.id = id;
		this.title = title;
		this.label = label;
		this.toolTip = toolTip;
		this.image = image;
		this.drawer = drawer;
		this.custom = custom;
	}
	
	
	
	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		if (state != this.state) {
			this.state = state;
			fireStateChange();
			stateChanged();
		}
	}

	protected abstract void stateChanged();

	private void fireStateChange() {
		if (getDrawer() == null) {
			return;
		}
		getDrawer().getPalette().fireStateChange(this);
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

	public PaletteDrawer getDrawer() {
		return drawer;
	}
	

}
