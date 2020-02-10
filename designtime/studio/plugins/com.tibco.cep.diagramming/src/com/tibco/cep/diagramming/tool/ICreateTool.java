package com.tibco.cep.diagramming.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;

public interface ICreateTool {

	//Override this for reseting palette selection
	public void resetPaletteSelection();
	public Map<Control,CreateToolListener> controlKeylistnerMap = new HashMap<Control, CreateToolListener>();
	public List<PaletteEntry> paletteEntryList = new ArrayList<PaletteEntry>();
	void dispose();
}
