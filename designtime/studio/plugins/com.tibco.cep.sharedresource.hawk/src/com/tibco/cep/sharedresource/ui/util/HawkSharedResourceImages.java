package com.tibco.cep.sharedresource.ui.util;

import java.net.URL;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import com.tibco.cep.sharedresource.HawkSharedResourcePlugin;

public class HawkSharedResourceImages {

	public static final String IMG_SHAREDRES_WIZ_HAWK = "com.tibco.cep.sharedresource.ui.util.wizHawk";

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
		loadImage(IMG_SHAREDRES_WIZ_HAWK, WIZ_PATH + "hawk.gif");
	}

	private static boolean loadImage(String key, String name) {
		URL url = HawkSharedResourcePlugin.getDefault().find(new Path(name));
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		SharedResourceImageRegistry.put(key, descr);
		return true;
	}

}
