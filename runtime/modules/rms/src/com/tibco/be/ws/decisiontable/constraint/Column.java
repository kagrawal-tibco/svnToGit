package com.tibco.be.ws.decisiontable.constraint;

import java.util.HashMap;

/**
 * Table Column class
 * @author vdhumal
 */
public class Column {
    //public String id;
    public String name;
    public String propertyPath;
    public ColumnType columnType;
    public int propertyType;
       
    public static enum ColumnType {
        CONDITION, CONDITION_CUSTOM, ACTION, ACTION_CUSTOM;

        private static HashMap<String, ColumnType> map = new HashMap<String, ColumnType>();
        static {
            map.put("CONDITION", CONDITION);
            //map.put("", CONDITION_CUSTOM);
            map.put("ACTION", ACTION);
            //map.put("", ACTION_CUSTOM);
        }
        
        public static ColumnType get(String name) {
            return map.get(name);
        }
    }
}
