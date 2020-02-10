package com.tibco.cep.studio.ui.palette.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;

/**
 * This class listens for palette entry selection changes and updates
 * the UI appropriately.  It also tracks the mouse cursor position
 * and changes the button appearance as needed.
 */
public class PalettePresentationUpdater implements
		PaletteEntrySelectionChangedListener, MouseTrackListener {

	private Font unselectedFont = new Font(null, "Tahoma", 8, SWT.NONE);
	private Font selectedFont = new Font(null, "Tahoma", 8, SWT.BOLD);

	//private Color defaultColor = new Color(null, 215, 222, 248);
	private Color defaultColor = new Color(null, 255, 255, 255);
	private Color selectedColor = new Color(null, 172, 190, 214);
	private Color mouseOverColor = new Color(null, 198, 211, 240);
	private ExpandBar rootExpandBar;

	public PalettePresentationUpdater(ExpandBar rootExpandBar) {
		this.rootExpandBar = rootExpandBar;
	}

	public void selectionStateChanged(PaletteEntry entry) {
		if (rootExpandBar == null) {
			return;
		}
		CLabel label = findLabel(entry);
		toggleButtonSelection(label);
		//On selection force focusing the control to receive all control events.
		label.forceFocus();
	}

	private CLabel findLabel(PaletteEntry entry) {
		ExpandItem[] children = rootExpandBar.getItems();
		for (ExpandItem item : children) {
			if (item.getData() != null
					&& item.getData().equals(entry.getDrawer())) {
				Control[] controls = ((Composite) item.getControl())
						.getChildren();
				for (Control control : controls) {
					if (control.getData() != null
							&& control.getData().equals(entry)) {
						return (CLabel) control;
					}
				}
			}
		}
		return null;
	}

	private void toggleButtonSelection(final CLabel button) {

		if (button.getData() != null
				&& button.getData() instanceof PaletteEntry) {
			PaletteEntry entry = (PaletteEntry) button.getData();
			if (entry.getState() == PaletteEntry.STATE_SELECTED) {
				button.setBackground(new Color[] { mouseOverColor,
						selectedColor }, new int[] { 100 });
				button.setFont(selectedFont);
			} else {
				button.setBackground(defaultColor);
				button.setFont(unselectedFont);
			}
		}

	}

	public void mouseHover(MouseEvent e) {
	}

	public void mouseExit(MouseEvent e) {
		if (!(e.getSource() instanceof CLabel)) {
			return;
		}
		CLabel button = (CLabel) e.getSource();
		if (button.getData() != null
				&& button.getData() instanceof PaletteEntry) {
			PaletteEntry entry = (PaletteEntry) button.getData();
			if (entry.getState() == PaletteEntry.STATE_SELECTED) {
				return;
			}
		}
		button.setBackground(defaultColor);
	}

	public void mouseEnter(MouseEvent e) {
		if (!(e.getSource() instanceof CLabel)) {
			return;
		}
		CLabel button = (CLabel) e.getSource();
		if (button.getData() != null
				&& button.getData() instanceof PaletteEntry) {
			PaletteEntry entry = (PaletteEntry) button.getData();
			if (entry.getState() == PaletteEntry.STATE_SELECTED) {
				return;
			}
		}
		button.setBackground(mouseOverColor);
	}

}
