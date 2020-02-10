package com.tibco.cep.sharedresource.ui.util;

import java.net.URL;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import com.tibco.cep.sharedresource.ASConnectionSharedResourcePlugin;


/*
@author Huabin Zhang (huzhang@tibco-support.com)
@date Feb 23, 2012 3:17:53 PM
 */

public class ASConnectionSharedResourceImages {

	public static final String		IMG_SHAREDRES_WIZ_AS_CON	= "com.tibco.cep.sharedresource.ui.util.wizAsCon";

	private static final String		ICON_PATH					= "icons/";
	private static final String		WIZ_PATH					= ICON_PATH + "/wizban/";

	private static ImageRegistry SharedResourceImageRegistry;

	public static ImageDescriptor getImageDescriptor(String key) {
		if (SharedResourceImageRegistry == null) {
			initialize();
		}
		return SharedResourceImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		SharedResourceImageRegistry = new ImageRegistry();
		loadImage(IMG_SHAREDRES_WIZ_AS_CON, WIZ_PATH + "asconnection.png");
	}

	private static boolean loadImage(String key, String name) {
		URL url = ASConnectionSharedResourcePlugin.getDefault().find(new Path(name));
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		SharedResourceImageRegistry.put(key, descr);
		return true;
	}

}
