package com.tibco.cep.sharedresource.ui.util;

import java.net.URL;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import com.tibco.cep.sharedresource.AS3SharedResourcePlugin;


public class AS3SharedResourceImages {

	public static final String IMG_SHAREDRES_WIZ_AS3 = "com.tibco.cep.sharedresource.ui.util.wizAS3";

	private static final String ICON_PATH = "icons/";
	private static final String WIZ_PATH = ICON_PATH + "/wizban/";

	private static ImageRegistry SharedResourceImageRegistry;

	public static ImageDescriptor getImageDescriptor(String key) {
		if (SharedResourceImageRegistry == null) {
			initialize();
		}
		return SharedResourceImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		SharedResourceImageRegistry = new ImageRegistry();
		loadImage(IMG_SHAREDRES_WIZ_AS3, WIZ_PATH + "asconnection.png");
	}

	private static boolean loadImage(String key, String name) {
		URL url = AS3SharedResourcePlugin.getDefault().find(new Path(name));
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		SharedResourceImageRegistry.put(key, descr);
		return true;
	}

}
