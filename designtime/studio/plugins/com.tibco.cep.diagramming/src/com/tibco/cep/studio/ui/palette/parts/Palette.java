package com.tibco.cep.studio.ui.palette.parts;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.ui.palette.actions.PaletteEntrySelectionChangedListener;

public class Palette {
	
	private List<PaletteDrawer> 	paletteDrawers = new ArrayList<PaletteDrawer>();
	private List<PaletteEntrySelectionChangedListener> listeners = new ArrayList<PaletteEntrySelectionChangedListener>();
	
	private PaletteEntry currentSelection;
	
	public Palette() {
	}

	public boolean addPaletteDrawer(PaletteDrawer drawer) {
		if (!paletteDrawers.contains(drawer)) {
			return paletteDrawers.add(drawer);
		}
		return false;
	}
	
	public boolean removePaletteDrawer(PaletteDrawer drawer) {
		if (paletteDrawers.contains(drawer)) {
			return paletteDrawers.remove(drawer);
		}
		return false;
	}

	public boolean addPaletteEntrySelectionChangedListener(PaletteEntrySelectionChangedListener listener) {
		if (!listeners.contains(listener)) {
			return listeners.add(listener);
		}
		return false;
	}
	
	public boolean removePaletteEntrySelectionChangedListener(PaletteEntrySelectionChangedListener listener) {
		if (listeners.contains(listener)) {
			return listeners.remove(listener);
		}
		return false;
	}
	
	public List<PaletteDrawer> getPaletteDrawers() {
		return paletteDrawers;
	}

	public void fireStateChange(PaletteEntry paletteEntry) {
		if (currentSelection != null && !currentSelection.equals(paletteEntry)) {
			currentSelection.setState(PaletteEntry.STATE_NOT_SELECTED);
		}
		currentSelection = paletteEntry;
		for (PaletteEntrySelectionChangedListener listener : listeners) {
			listener.selectionStateChanged(paletteEntry);
		}
	}

	public PaletteEntry getCurrentSelection() {
		return currentSelection;
	}
	
	public void getPaletteEntries (List<PaletteEntry> list) {
		for (PaletteDrawer drawer : getPaletteDrawers()) {
			 for (PaletteEntry entry : drawer.getPaletteEntries()) {
				 list.add(entry);
			 }
		}
	}
}