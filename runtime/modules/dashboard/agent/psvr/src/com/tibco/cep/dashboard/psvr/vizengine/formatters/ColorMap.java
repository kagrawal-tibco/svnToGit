package com.tibco.cep.dashboard.psvr.vizengine.formatters;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

public class ColorMap {

    //
    // Also, if the front end accepted both #xxxxxx
    // format and English names (JavaScript does, so
    // I'm assuming ActionScript also will), there
    // should be no need for this map at all. Specifying
    // a color either in English or in #xxxxxx notation
    // should automatically work.
    //
    private static Properties colorMap;
    
    static {
    	colorMap = new Properties();
    	try {
			colorMap.load(ColorMap.class.getResourceAsStream("colorsmap.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

    /** 
     * Map an English color to its hex equivalent.
     * 
     * @param s 
     * @return 
     */
    public static final String toHex(String s) {
        if (s == null || s.length() == 0)
            return null;

        String v = colorMap.getProperty(s.toLowerCase());

        if (v != null)
            return v;

        return s;
    }

    /** 
     * Map a hex color value to its English equivalent.
     * 
     * @param h 
     * @return 
     */
    public static final String fromHex(String h) {
        if (h == null || h.length() == 0)
            return null;

        if (h.startsWith("#") && h.length() > 1)
            h = h.substring(1);
        h = h.toUpperCase();

        Set<Map.Entry<Object, Object>> entries = colorMap.entrySet();
        Iterator<Entry<Object, Object>> iter = entries.iterator();
        while (iter.hasNext()) {
            Map.Entry<Object,Object> e = iter.next();
            if (e.getValue().equals(h))
                return (String) e.getKey();
        }
        return null;
    }
}
