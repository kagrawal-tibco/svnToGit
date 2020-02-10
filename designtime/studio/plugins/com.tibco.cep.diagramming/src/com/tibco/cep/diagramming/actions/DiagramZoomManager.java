package com.tibco.cep.diagramming.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import com.tibco.cep.diagramming.drawing.IZoomConstants;


/**
 * @author sasahoo
 *
 */
@SuppressWarnings("unchecked")
public class DiagramZoomManager implements IZoomConstants{

	private List<IZoomListener> listeners = new ArrayList<IZoomListener>();
	private List<String> zoomLevelContributions = Collections.EMPTY_LIST;
	private double zoom = 1.0;
	
	public DiagramZoomManager() {/*TODO*/}

	/**
	 * @param listener
	 */
	public void addZoomListener(IZoomListener listener) {
		listeners.add(listener);
	}

	/**
	 * Notifies listeners that the zoom level has changed.
	 */
	protected void fireZoomChanged() {
		Iterator<IZoomListener> iter = listeners.iterator();
		while (iter.hasNext())
			((IZoomListener)iter.next()).zoomChanged(zoom);
	}

	public double getUIMultiplier() {
		return multiplier;
	}

	public double getZoom() {
		return zoom;
	}

	public String getZoomAsText() {
		String newItem = format.format(zoom * multiplier);
		return newItem;
	}

	public List<String> getZoomLevelContributions() {
		return zoomLevelContributions;
	}

	/**
	 * @return double[]
	 */
	public double[] getZoomLevels() {
		return zoomLevels;
	}

	/**
	 * @return
	 */
	public String[] getZoomLevelsAsText() {
		String[] zoomLevelStrings = new String[zoomLevels.length + zoomLevelContributions.size()];
		for (int i = 0; i < zoomLevels.length; i++) {
			zoomLevelStrings[i] = format.format(zoomLevels[i] * multiplier);
		}
		if (zoomLevelContributions != null) {
			for (int i = 0; i < zoomLevelContributions.size(); i++) {
				zoomLevelStrings[i + zoomLevels.length] = (String)zoomLevelContributions.get(i);
			}
		}
		return zoomLevelStrings;
	}

	/**
	 * @param listener
	 */
	public void removeZoomListener(IZoomListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param zoom
	 */
	public void setZoom(double zoom) {
		this.zoom =  zoom;
	}

	/**
	 * @param zoomString
	 */
	public void setZoomAsText(String zoomString) {
		try {
			//Trim off the '%'
			if (zoomString.charAt(zoomString.length() - 1) == '%')
				zoomString = zoomString.substring(0, zoomString.length() - 1);
			double newZoom = Double.parseDouble(zoomString) / 100;
			setZoom(newZoom / multiplier);
			fireZoomChanged();
		} catch (Exception e) {
			Display.getCurrent().beep();
		}
	}

	/**
	 * @param contributions
	 */
	public void setZoomLevelContributions(List<String> contributions) {
		zoomLevelContributions = contributions;
	}
}