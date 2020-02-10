package com.tibco.cep.studio.mapper.ui.data.utils;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;

/**
 * Utility functions for reading preference strings.
 */
public class PreferenceUtils {

    /**
     * Reads a dimension.
     * @param uiAgent The application where prefs are.
     * @param name The name of the preference.
     * @param minDim The minimum dimension allowed.
     * @param maxDim The maximum dimension allowed.
     * @param defDim The default dimension if one isn't found in the prefs file.
     * @return The dimension, never null.
     */
    public static Dimension readDimension(UIAgent uiAgent, String name, Dimension minDim, Dimension maxDim,  Dimension defDim) {
        Dimension d1 = readDimension(uiAgent,name,defDim);
        if (d1==null)
        {
            return null;
        }
        if (d1.width<minDim.width)
        {
            d1.width = minDim.width;
        }
        if (d1.width>maxDim.width)
        {
            d1.width = maxDim.width;
        }
        if (d1.height<minDim.height)
        {
            d1.height = minDim.height;
        }
        if (d1.height>maxDim.height)
        {
            d1.height = maxDim.height;
        }
        return d1;
    }

    /**
     * Adjusts the position to fit on the screen...
     */
    public static Point readLocation(UIAgent uiAgent, String name, Point def, Dimension size) {
        Point p = readPoint(uiAgent,name,def);
        if (p==null) {
            return null;
        }
        if (p.x+size.width > Toolkit.getDefaultToolkit().getScreenSize().width) {
            p.x = 0;
        }
        if (p.y+size.height > Toolkit.getDefaultToolkit().getScreenSize().height) {
            p.y = 0;
        }
        return p;
    }

    public static Point readPoint(UIAgent uiAgent, String name, Point def) {
        if (uiAgent==null) {
            return def;
        }
        String val = uiAgent.getUserPreference(name);
        if (val==null) {
            return def;
        }
        int ii = val.indexOf(',');
        if (ii<0) {
            return def;
        }
        String l = val.substring(0,ii);
        String r = val.substring(ii+1,val.length());
        try {
            int x = Integer.parseInt(l);
            int y = Integer.parseInt(r);
            return new Point(x,y);
        } catch (Exception e) {
        }
        return def;
    }

    public static Dimension readDimension(UIAgent uiAgent, String name, Dimension def) {
        if (uiAgent==null) {
            return def;
        }
        String val = uiAgent.getUserPreference(name);
        if (val==null) {
            return def;
        }
        int ii = val.indexOf(',');
        if (ii<0) {
            return def;
        }
        String l = val.substring(0,ii);
        String r = val.substring(ii+1,val.length());
        try {
            int w = Integer.parseInt(l);
            int h = Integer.parseInt(r);
            return new Dimension(w,h);
        } catch (Exception e) {
        }
        return def;
    }

    public static int readInt(UIAgent uiAgent, String name, int defVal) {
        if (uiAgent==null) {
            return defVal;
        }
        String tb = uiAgent.getUserPreference(name);
        if (tb!=null) {
            try {
                return Integer.parseInt(tb);
            } catch (Exception e) {
            }
        }
        return defVal;
    }

    public static boolean readBool(UIAgent uiAgent, String name, boolean defVal) {
        if (uiAgent==null) {
            return defVal;
        }
        String tb = uiAgent.getUserPreference(name);
        if (tb!=null) {
            try {
                if (tb.equalsIgnoreCase("true")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
            }
        }
        return defVal;
    }

    public static void writeDimension(UIAgent uiAgent, String name, Dimension d) {
        if (uiAgent==null) {
            return;
        }
        String val = "" + d.width + "," + d.height;
        uiAgent.setUserPreference(name,val);
    }

    public static void writePoint(UIAgent uiAgent, String name, Point d) {
        if (uiAgent==null) {
            return;
        }
        String val = "" + d.x + "," + d.y;
        uiAgent.setUserPreference(name,val);
    }

    public static void writeInt(UIAgent uiAgent, String name, int at) {
        if (uiAgent==null) {
            return;
        }
        String val = "" + at;
        uiAgent.setUserPreference(name,val);
    }

    public static void writeBool(UIAgent uiAgent, String name, boolean at) {
        if (uiAgent==null) {
            return;
        }
        String val = "" + at;
        uiAgent.setUserPreference(name,val);
    }

    public static void writeString(UIAgent uiAgent, String name, String val)
    {
        if (val==null)
        {
            val = "";
        }
        uiAgent.setUserPreference(name,val);
    }

    /**
     * @return The file, or null if not found.
     */
    public static File readFile(UIAgent uiAgent, String name)
    {
        String str = uiAgent.getUserPreference(name,"");
        if (str==null || str.length()==0)
        {
            return null;
        }
        try
        {
            File f = new File(str);
            if (f.exists())
            {
                return f;
            }
        }
        catch (Exception e)
        {
            // don't fail because of this.
        }
        return null;
    }
}

