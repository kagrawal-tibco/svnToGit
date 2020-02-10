package com.tibco.cep.webstudio.client.palette;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author sasahoo
 *
 */
@RemoteServiceRelativePath("PaletteToolbarService")
public interface PaletteToolbarService extends RemoteService
{
	String getMessage(String msg);
	Palette getToolbar(String palette);
    String getPalette();
	/**
	 * Utility/Convenience class.
	 * Use PaletteToolbarService.Toolbar.getInstance() to access static instance of PaletteToolbarServiceAsync
	 */
	public static class Toolbar {
		
		private static PaletteToolbarServiceAsync instance = GWT.create(PaletteToolbarService.class);
		
		/**
		 * @return
		 */
		public static synchronized PaletteToolbarServiceAsync getInstance() {
			return instance;
		}
	}
}
