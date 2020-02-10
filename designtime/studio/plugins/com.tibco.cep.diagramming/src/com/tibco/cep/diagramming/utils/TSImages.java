package com.tibco.cep.diagramming.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.diagramming.DiagrammingPlugin;

public class TSImages {

	public static final String TS_IMAGES_INIT = "com.tibco.cep.diagramming.utils.images.";

	public static final String TS_IMAGES_PRINT_PREVIEW = TS_IMAGES_INIT
	+ "print preview";
	
	public static final String TS_IMAGES_PAGE_SETUP = TS_IMAGES_INIT
	+ "page setup";
	
	private static final String ICON_PATH = "icons/";

	private static ImageRegistry tsImageRegistry;

	@SuppressWarnings("deprecation")
	public static boolean loadImage(String key, String name) {
		URL url = DiagrammingPlugin.getDefault().find(new Path(name));
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		tsImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (tsImageRegistry == null)
			initialize();
		return tsImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (tsImageRegistry == null)
			initialize();
		return tsImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		tsImageRegistry = new ImageRegistry();
		
		loadImage(TS_IMAGES_PRINT_PREVIEW, ICON_PATH + "printPreview.gif");
		loadImage(TS_IMAGES_PAGE_SETUP, ICON_PATH + "loop.gif");
		
	}
	
	/**
	 * @param icon
	 * @return
	 */
	public static ImageIcon  createIcon(String icon)  {
		ImageIcon imageIcon = null;
		try{
			InputStream resource = DiagrammingPlugin.class.getClassLoader().getResourceAsStream(icon);
			if (resource == null) {
				System.err.println("Image file " + icon + " is missing");
			}
			return new ImageIcon(ImageIO.read(resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageIcon;
	}
}
