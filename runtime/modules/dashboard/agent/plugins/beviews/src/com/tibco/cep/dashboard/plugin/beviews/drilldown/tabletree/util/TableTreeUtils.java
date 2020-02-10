package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * @author vchugh
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TableTreeUtils {

    public static void outAttributes(Map<Object, Object> attributes,  StringBuffer buffer) {
        if(attributes !=null){
            Iterator<Map.Entry<Object, Object>> iterator = attributes.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iterator.next();
                outAttribute(buffer, entry.getKey(), entry.getValue());
            }
        }
    }

    public static void outEvents(Map<Object, Object> events,StringBuffer buffer) {
        if(events !=null){
            Iterator<Map.Entry<Object, Object>> iterator = events.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iterator.next();
                outAttribute(buffer, entry.getKey(), entry.getValue());
            }
        }
    }
    
    public static void outAttribute(StringBuffer buffer, Object key, Object value){
        buffer.append(" " + key  +" =\"" + value +"\" ");
    }

    public static void outAttribute(StringBuffer buffer, Object key){
        buffer.append(" " + key  +" ");
    }

    public static void outTagStart(StringBuffer buffer, Object tag){
        buffer.append("<" + tag + " ");
    }
    
    public static void outTagStartEnd(StringBuffer buffer){
        buffer.append(">");
    }

    public static void outTagEnd(StringBuffer buffer, Object tag){
        buffer.append("</" + tag + ">");
    }

    /**
     * @param singleAttributes
     * @param buffer
     */
    public static void outAttributes(ArrayList<String> singleAttributes, StringBuffer buffer) {
        if (singleAttributes !=null){
            Iterator<String> iterator = singleAttributes.iterator();
            while(iterator.hasNext()){
                outAttribute(buffer, iterator.next());
            }
        }
    }

    /**
     * @param buffer
     * @param string
     */
    private static void outAttribute(StringBuffer buffer, String att) {
        buffer.append(" " + att + " ");
    }
}
