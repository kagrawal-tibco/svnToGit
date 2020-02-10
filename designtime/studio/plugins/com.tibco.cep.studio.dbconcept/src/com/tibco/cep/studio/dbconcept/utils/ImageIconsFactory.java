package com.tibco.cep.studio.dbconcept.utils;

import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dbconcept.ModulePlugin;

/**
 * Creating image icons for SWT components.
 * 
 * @author majha
 * 
 */
public class ImageIconsFactory {

	private static final String ImagePath = "/icons/";

	public static Image createImageIcon(String file) {
		Image image = null;
		try {
			InputStream resource = ModulePlugin.class.getClassLoader().getResourceAsStream(ImagePath + file);
			if (resource == null) {
				System.err.println("Image file " + file + " is missing");
			}
			else {
				ImageData imageData = new ImageData(resource);
				image = new Image(Display.getDefault(), imageData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
}
