package com.tibco.cep.studio.dashboard.ui.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * @author ssrinivasan
 *
 */
public class SynColorUtils {

    private static Map<String, Image> colorImages = new HashMap<String, Image>();
    /**
     * 
     */
    private SynColorUtils() {
    }

    public static Image makeColorImage(Display display, String colorName) {
        Image image;
        image = colorImages.get(colorName);
        if (image == null) {
            try {
		        image = new Image (display, 16, 16);
		        GC gc = new GC (image);
		        RGB rgb = StringToRGB(colorName);
		        gc.setForeground(ColorConstants.white);
		        gc.fillRectangle (image.getBounds ());
		        gc.setForeground(ColorConstants.black);
		        gc.drawRectangle(4, 1, 10, 10);
		        gc.setBackground (new Color(display, rgb.red, rgb.green, rgb.blue));
		        gc.fillRectangle (new Rectangle(5, 2, 9, 9));
		        gc.dispose ();
		        colorImages.put(colorName, image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return image;
    }
    
    public static RGB StringToRGB(String s) {
        s = pad(s, 6);

        int r = Integer.parseInt(s.substring(0, 2), 16);
        int g = Integer.parseInt(s.substring(2, 4), 16);
        int b = Integer.parseInt(s.substring(4, 6), 16);
        return new RGB(r, g, b);

    }
    public static String RGBToString(RGB rgb) {
        return
        	(pad(Integer.toHexString(rgb.red), 2) +
        	pad(Integer.toHexString(rgb.green), 2) +
        	pad(Integer.toHexString(rgb.blue), 2)).toUpperCase();
    }
   
    public static String pad (String s, int n) {
        while (s.length() < n) {
            s = "0" + s;
        }
        return s;
    }
    
    public static void dispose() {
        for (Iterator<Image> iter = colorImages.values().iterator(); iter.hasNext(); ) {
            iter.next().dispose();
        }
        colorImages.clear();
    }
}
