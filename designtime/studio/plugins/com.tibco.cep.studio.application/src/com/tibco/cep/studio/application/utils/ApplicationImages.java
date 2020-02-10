package com.tibco.cep.studio.application.utils;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.application.StudioApplicationPlugin;
/**
 * 
 * @author ggrigore
 *
 */
		
public final class ApplicationImages {

	public static final String APP_IMAGES_INIT = "com.tibco.cep.decision.table.utils.images.";
	
	public static final String APP_IMAGE_NEW_PERSPECTIVE = APP_IMAGES_INIT
	+ "new";
	public static final String APP_IMAGE_OPEN_VIEW = APP_IMAGES_INIT
	+ "open";
	public static final String APP_IMAGE_SHOW_STD_VIEW = APP_IMAGES_INIT
	+ "showstnd";
	public static final String APP_IMAGE_SHOW_ONT_VIEW = APP_IMAGES_INIT
	+ "showont";
	public static final String APP_IMAGE_SHOW_CUSTOM_VIEW = APP_IMAGES_INIT
	+ "showcustom";
	public static final String APP_IMAGE_SHOW_PROJECT_EXPLORER_VIEW = APP_IMAGES_INIT
	+ "showproexp";
	public static final String APP_IMAGE_SHOW_OVERVIEW_VIEW = APP_IMAGES_INIT
	+ "showoverview";
	public static final String APP_IMAGE_SHOW_PROBLEMS_VIEW = APP_IMAGES_INIT
	+ "showproblems";
	public static final String APP_IMAGE_SHOW_CONSOLE_VIEW = APP_IMAGES_INIT
	+ "showconsole";
	public static final String APP_IMAGE_SHOW_PRPERTIES_VIEW = APP_IMAGES_INIT
	+ "properties";
	public static final String APP_IMAGE_SHOW_PALETTE_VIEW = APP_IMAGES_INIT
	+ "palette";
	public static final String APP_PREFERENCE_ACTION = APP_IMAGES_INIT
	+ "preference";
	public static final String APP_FILE_NEW_ACTION = APP_IMAGES_INIT
	+ "fileNew";
	public static final String APP_FILE_OPEN_ACTION = APP_IMAGES_INIT
	+ "fileopen";
	public static final String APP_WELCOME = APP_IMAGES_INIT
	+ "welcome";
	public static final String APP_COMMIT_ACTION = APP_IMAGES_INIT
	+ "commit";
	private static final String ICON_PATH = "icons/";

	private static ImageRegistry APPImageRegistry;

	public static boolean loadImage(String key, String name) {
		URL url = FileLocator.find(StudioApplicationPlugin.getDefault().getBundle(), new Path(name), null);
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		APPImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (APPImageRegistry == null)
			initialize();
		return APPImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (APPImageRegistry == null)
			initialize();
		return APPImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		APPImageRegistry = new ImageRegistry();

		loadImage(APP_IMAGE_NEW_PERSPECTIVE, ICON_PATH + "open_resource.gif");
		loadImage(APP_IMAGE_OPEN_VIEW, ICON_PATH + "tibco16-32.gif");
		loadImage(APP_IMAGE_SHOW_PROJECT_EXPLORER_VIEW, ICON_PATH + "navigation.gif");
		loadImage(APP_IMAGE_SHOW_STD_VIEW, ICON_PATH + "standardfunction_16x16.png");
		loadImage(APP_IMAGE_SHOW_ONT_VIEW, ICON_PATH + "ontologyfunction_16x16.png");
		loadImage(APP_IMAGE_SHOW_CUSTOM_VIEW, ICON_PATH + "customfunction_16x16.png");
		loadImage(APP_IMAGE_SHOW_OVERVIEW_VIEW, ICON_PATH + "overview.gif");
		loadImage(APP_IMAGE_SHOW_CONSOLE_VIEW, ICON_PATH + "console_view.gif");
		loadImage(APP_IMAGE_SHOW_PROBLEMS_VIEW, ICON_PATH + "problems_view.gif");
		loadImage(APP_PREFERENCE_ACTION, ICON_PATH + "greenarr.gif");
		loadImage(APP_FILE_NEW_ACTION, ICON_PATH + "tbnew.gif");
		loadImage(APP_FILE_OPEN_ACTION, ICON_PATH + "open_project_16x16.png");
		loadImage(APP_WELCOME,ICON_PATH + "welcome16.gif");
		loadImage(APP_COMMIT_ACTION, ICON_PATH + "commit.gif");
		loadImage(APP_IMAGE_SHOW_PRPERTIES_VIEW, ICON_PATH + "property_entity.png");
		loadImage(APP_IMAGE_SHOW_PALETTE_VIEW, ICON_PATH + "palette.gif");
	}
}
