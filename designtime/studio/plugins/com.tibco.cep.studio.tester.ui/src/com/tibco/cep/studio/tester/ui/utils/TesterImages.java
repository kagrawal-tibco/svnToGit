/**
 * 
 */
package com.tibco.cep.studio.tester.ui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.ui.util.StudioUIUtils;



/**
 * @author aathalye
 *
 */
public final class TesterImages {
	
	public static final String IMAGES_INIT = "com.tibco.cep.studio.tester.util.images.";

	public static final String TESTER_START = IMAGES_INIT + "Start";
	
	private static final String ICON_PATH = "icons/";

	private static ImageRegistry testerImageRegistry;

	public static boolean loadImage(String key, String name) {
		URL url = FileLocator.find(StudioTesterUIPlugin.getDefault().getBundle(), new Path(name), null);
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		testerImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (testerImageRegistry == null) {
			initialize();
		}
		return testerImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (testerImageRegistry == null) {
			initialize();
		}
		return testerImageRegistry.getDescriptor(key);
	}

	public static void initialize() {
		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				testerImageRegistry = new ImageRegistry();

				loadImage(TESTER_START, ICON_PATH + "starttest.gif");
			}
		}, false);
	}
	
	/**
	 * @param icon
	 * @return
	 */
	public static ImageIcon  createIcon(String icon)  {
		ImageIcon imageIcon = null;
		try{
			InputStream resource = 
				StudioTesterUIPlugin.class.getClassLoader().getResourceAsStream(icon);
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
