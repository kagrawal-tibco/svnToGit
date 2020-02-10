package com.tibco.cep.webstudio.client.palette;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author sasahoo
 *
 */
public class PaletteToolbarCallback implements AsyncCallback<Palette> {
	
	private PalettePane pane;
	
	public PaletteToolbarCallback(PalettePane pane) {
		this.pane = pane;
	}
	
	public void onSuccess(Palette result) {
//		while (toolStrip.getChildren().length > 0) {
//			toolStrip.removeChild(TSPerspectivesDiagram.this.toolStrip.getChildren()[0]);
//		}
		pane.populateToolbar(result);
	}

	public void onFailure(Throwable throwable) {
	}
}
