package com.tibco.cep.studio.ui.editors.rules;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager {

	private static ColorManager fInstance;
	protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

	public static ColorManager getInstance() {
		if (fInstance == null) {
			fInstance = new ColorManager();
		}
		return fInstance;
	}
	
	private ColorManager() {
		super();
	}

	public void dispose() {
		Iterator<Color> e = fColorTable.values().iterator();
		while (e.hasNext())
			 e.next().dispose();
	}
	
	public Color getColor(RGB rgb) {
		Color color = fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}
}
