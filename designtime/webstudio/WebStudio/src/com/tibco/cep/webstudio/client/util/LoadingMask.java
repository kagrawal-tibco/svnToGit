/**
 * 
 */
package com.tibco.cep.webstudio.client.util;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.ClickMaskMode;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VLayout;


/**
 * Utility class to show and clear loading mask.
 * 
 * @author Vikram Patil
 */
public class LoadingMask {
	private static VLayout loadingLayout;
	private static Canvas loadingCanvas;
	
	/**
	 * Shows the loading mask
	 */
	public static void showMask() {
		loadingLayout = new VLayout();
		loadingLayout.setAutoHeight();
		loadingLayout.setAutoWidth();

		Img loadingImg = new Img();
		loadingImg.setSrc(Page.getAppImgDir() + "icons/32/loading.gif");
		loadingImg.setWidth(32);
		loadingImg.setHeight(32);

		loadingLayout.addMember(loadingImg);
		loadingLayout.showClickMask(null, ClickMaskMode.HARD, null);
		loadingLayout.setTop((Window.getClientHeight() / 2) - 32);
		loadingLayout.setLeft((Window.getClientWidth() / 2) - 32);

		loadingCanvas = new Canvas();
		loadingCanvas.setWidth100();
		loadingCanvas.setHeight100();
		loadingCanvas.setBackgroundColor("#BCBBBB");
		loadingCanvas.setOpacity(60);

		loadingLayout.moveAbove(loadingCanvas);
		
		loadingCanvas.draw();
		loadingLayout.draw();
	}
	
	/**
	 * Clears the loading mask
	 */
	public static void clearMask() {
		if (loadingCanvas != null) {
			loadingCanvas.destroy();
			loadingCanvas = null;
		}
		
		if (loadingLayout != null) { 
			loadingLayout.destroy();
			loadingLayout = null;
		}
	}
}
