package com.tibco.cep.ws.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.tibco.cep.studio.ws.WsPlugin;


/**
 * Creating image icons for swing components
 * @author majha
 *
 */
public class ImageIconsFactory {
   
	private static final String ImagePath = "/icons/";

     /**
      * 
      * @param file
      * @return
      */
     public static ImageIcon  createImageIcon(String file)  {
		 ImageIcon imageIcon = null;
		 	try{
			 	InputStream resource = WsPlugin.class.getClassLoader().getResourceAsStream(ImagePath+file);
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
     public static ImageIcon  createIcon(String icon)  {
		 ImageIcon imageIcon = null;
		 	try{
			 	InputStream resource = WsPlugin.class.getClassLoader().getResourceAsStream(icon);
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
