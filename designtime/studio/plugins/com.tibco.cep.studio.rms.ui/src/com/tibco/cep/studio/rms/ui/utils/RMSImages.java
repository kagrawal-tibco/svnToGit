package com.tibco.cep.studio.rms.ui.utils;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.rms.ui.RMSUIPlugin;



public class RMSImages {

	public static final String RMS_IMAGES_INIT = "com.tibco.cep.studio.rms.ui.utils.images.";
	
	public static final String RMS_IMAGES_AUTHENTICATION = RMS_IMAGES_INIT + "authentication";
	
	private static final String ICON_PATH = "icons/";
	
	private static ImageRegistry dtImageRegistry;
	

	public static boolean loadImage(String key, String name) {
		URL url = FileLocator.find(RMSUIPlugin.getDefault().getBundle(), new Path(name), null);
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		dtImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (dtImageRegistry == null)
			initialize();
		return dtImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (dtImageRegistry == null)
			initialize();
		return dtImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		dtImageRegistry = new ImageRegistry();
		
		loadImage(RMS_IMAGES_AUTHENTICATION, ICON_PATH + "authentication.gif");
	}
}
