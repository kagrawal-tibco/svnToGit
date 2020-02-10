package com.tibco.cep.studio.ui.util;

import java.net.URL;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.StudioUIPlugin;



public class StudioImages {
	
	private static final String STUDIO_UI_IMAGES_INIT = "com.tibco.cep.studio.ui.util.images.";

	public static final String IMG_HORIZONTAL = STUDIO_UI_IMAGES_INIT
			+ "horizontal";
	public static final String IMG_VERTICAL = STUDIO_UI_IMAGES_INIT + "vertical";

	public static final String IMG_FORM_BG = STUDIO_UI_IMAGES_INIT + "formBg";

	private static final String ICON_PATH = "icons/";

	private static ImageRegistry StudioUIImageRegistry;

	private static boolean loadImage(String key, String name) {
		URL url = StudioUIPlugin.getDefault().find(new Path(name));
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		StudioUIImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (StudioUIImageRegistry == null)
			initialize();
		return StudioUIImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (StudioUIImageRegistry == null)
			initialize();
		return StudioUIImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		StudioUIImageRegistry = new ImageRegistry();

		loadImage(IMG_HORIZONTAL, ICON_PATH + "th_horizontal.gif");
		loadImage(IMG_VERTICAL, ICON_PATH + "th_vertical.gif");
		loadImage(IMG_FORM_BG, ICON_PATH + "form_banner.gif");
	}
}
