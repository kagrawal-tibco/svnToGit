package com.tibco.cep.diagramming.actions;

/**
 * Listens to zoom level changes.
 * @author sasahoo
 */
public interface IZoomListener {

/**
 * Called whenever the ZoomManager's zoom level changes.
 * @param zoom the new zoom level.
 */
void zoomChanged(double zoom);

}