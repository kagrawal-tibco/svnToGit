package com.tibco.cep.diagramming.tool;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.views.PaletteView;

public class CreateToolUtils {

	/**
	 * @param part
	 * @param diagramManager
	 * @param createTool
	 * @param paletteEntryList
	 * @param controlKeylistnerMap
	 */
	public static void handleOnPaletteToolSelection(final IWorkbenchPart part,
			                                        DiagramManager diagramManager,   
			                                        ICreateTool createTool,
			                                        List<PaletteEntry> paletteEntryList,
			                                        Map<Control,CreateToolListener> controlKeylistnerMap) {
		if (part instanceof PaletteView) {
			PaletteView paletteView = (PaletteView) part;
			if (paletteEntryList.isEmpty()) {
				paletteView.getPalette().getPaletteEntries(paletteEntryList);
				for (PaletteEntry entry : paletteEntryList) {
					if (entry.getControl() != null) {
						if (!controlKeylistnerMap.containsKey(entry.getControl())) {
							if (controlKeylistnerMap.get(entry.getControl()) == null) {
								CreateToolListener paletteToolKeyListener = new CreateToolListener(diagramManager, createTool);
								entry.getControl().addKeyListener(paletteToolKeyListener);
								controlKeylistnerMap.put(entry.getControl(), paletteToolKeyListener);
							}
						}
					}
				} 
			}
		}
	}
	
	/**
	 * @param controlKeylistnerMap
	 */
	public static void removeCreateToolListeners(Map<Control,CreateToolListener> controlKeylistnerMap) {
		for (Control  control : controlKeylistnerMap.keySet()) {
			KeyListener listener = controlKeylistnerMap.get(control);
			if (listener != null && !control.isDisposed()) {
				control.removeKeyListener(listener);
			}
		}
	}
}
