package com.tibco.cep.runtime.service.decision;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 18, 2009
 * Time: 2:45:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Column {
    //public String id;
    public String name;
    public String propertyPath;
    public ColumnType columnType;
    
//    public Column(String id, String name, String propertyPath, String columnType) {
//        this.id = id;
//        this.name = name;
//        this.propertyPath = propertyPath;
//        
//    }
    
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
    /*
    <columns>
      <column id="1" name="accountdecisionconcept.AccountClassification" propertyPath="/Concepts/AccountDecisionConcept/AccountClassification" columnType="CONDITION"/>
     */
}
