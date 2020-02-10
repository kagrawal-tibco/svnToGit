/**
 * 
 */
package com.tibco.cep.bemm.runtime.service.management.process;

import java.util.HashMap;

import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

/**
 * @author dijadhav
 *
 */
public class TabularDataDescriptor {

    //Column and item have the same meaning in this class and are used interchangeably. Each item is a column in the table

    private static TabularDataDescriptor instance = null;
    private static HashMap<String, ItemsDescriptor> methodToItemsDescriptor = null;

    //The items descriptor for every exposed method returning tabular data are created in this constructor.
    //The descriptors are put in a hashmap. The key is the invoked method name.
    public TabularDataDescriptor() {
        methodToItemsDescriptor = new HashMap<String, ItemsDescriptor>();
      
        methodToItemsDescriptor.put("getLoggerNamesWithLevels", new ItemsDescriptor(
        		new OpenType[]{SimpleType.STRING, SimpleType.STRING},
        		new String[]{"Logger Name", "Log Level"},
        		new String[]{"Logger Name", "Current Log Level"},
        		new String[]{"Logger Name"},
        		"LoggerInfoRowType",
        		"Type of each row of the table with logger information",
        		"LoggerInfoTabularType",
        		"Type to represent the Logger information in tabular form") );

    } //constructor

    public static TabularDataDescriptor getInstance(){
        if(instance == null)
            instance = new TabularDataDescriptor();
        return instance;
    }

    public ItemsDescriptor getItemsDescriptor(String invokedMethod) {
       return methodToItemsDescriptor.get(invokedMethod);
    }

    //sacrifice encapsulation for simplicity. Goal is to simulate properties equivalent to C# and other languages
    //Class fields were made final for safety
    public class ItemsDescriptor {
        final public OpenType[] itemTypes;
        final public String[] itemNames;
        final public String[] itemDescriptions;
        final public String[] indexNames;
        final public String rowTypeName;
        final public String rowTypeDescription;
        final public String tabularTypeName;
        final public String tabularTypeDescription;

        public ItemsDescriptor(OpenType[] itemTypes, String[] itemNames, String[] itemDescriptions, String[] indexNames,
                                 String rowTypeName, String rowTypeDescription, String tabularTypeName, String tabularTypeDescription) {
            this.itemTypes = itemTypes;
            this.itemNames = itemNames;
            this.itemDescriptions = itemDescriptions;
            this.indexNames = indexNames;
            this.rowTypeName = rowTypeName;
            this.rowTypeDescription = rowTypeDescription;
            this.tabularTypeName = tabularTypeName;
            this.tabularTypeDescription = tabularTypeDescription;
        }
    } 
}
