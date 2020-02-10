package com.tibco.cep.studio.ui.util;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Creating image icons for swing components
 * @author sasahoo
 *
 */
public class ImageIconsFactory {
   
	private static final String ImagePath = "/icons/";

     /**
      * 
      * @param file
      * @return
      */
     public static ImageIcon  createImageIcon(String file, ClassLoader classLoader)  {
		 ImageIcon imageIcon = null;
		 	try{
			 	InputStream resource = classLoader.getResourceAsStream(ImagePath + file);
			 	if (resource == null) {
			 		System.err.println("Image file " + file + " is missing");
			 	}
			 	return new ImageIcon(ImageIO.read(resource));
		 		} catch (IOException e) {
					e.printStackTrace();
				}
		  return imageIcon;
	 }
     
     /**
      * 
      * @param icon
      * @return
      */
     public static ImageIcon  createIcon(String icon, ClassLoader classLoader)  {
		 ImageIcon imageIcon = null;
		 	try{
			 	InputStream resource = classLoader.getResourceAsStream(icon);
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
