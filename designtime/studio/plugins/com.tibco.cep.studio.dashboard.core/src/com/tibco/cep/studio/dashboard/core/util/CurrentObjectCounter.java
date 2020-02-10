package com.tibco.cep.studio.dashboard.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @
 *  
 */
public class CurrentObjectCounter {
	
	public static final Logger LOGGER = Logger.getLogger(CurrentObjectCounter.class.getName());
    
    public static final String OBJECT_TYPE_ELEMENT = "Element";

    public static final String OBJECT_TYPE_PROPERTY = "Property";

//    private static final String OBJECT_TYPE_UNDEFINED = "Undefined";
    
    public static final String OBJECT_TYPE_TREE_ELEMENT = "TreeElement";

    private static Map<String, int[]> objectCount = new HashMap<String, int[]>();

//    private static Map objectTypeCount = new HashMap();

    public static void increment(String objectType) {
        int[] counter = (int[]) objectCount.get(objectType);
        if (null != counter) {
            counter[0]++;
        }
        else {
            objectCount.put(objectType, new int[] { 1 });
        }

    }

    public static void resetCounter(String objectType) {

        objectCount.remove(objectType);

    }

    public static void decrement(String objectType) {
        int[] counter = (int[]) objectCount.get(objectType);
        if (null != counter) {

            counter[0]--;
        }
        else {
            LOGGER.info(
                    objectType + " has not been initialized; this request to decrement the count for this type is invalid and will be ignored.");
        }

    }

    public static int getCount(String objectType) {
        int[] counter = (int[]) objectCount.get(objectType);
        if (null != counter) {

            return counter[0];
        }
        return 0;
    }

    public static List<String> getObjectTypes() {
        return new ArrayList<String>(objectCount.keySet());
    }

    public static String[][] getCounterArray() {
        String[][] counts = new String[objectCount.size()][2];

        int keyCounter = 0;
        for (Iterator<String> iter = objectCount.keySet().iterator(); iter.hasNext();) {
            String objectType = iter.next();
            counts[keyCounter][0] = objectType;
            counts[keyCounter][1] = ((int[]) objectCount.get(objectType))[0] + "";
            keyCounter++;
        }
        return counts;

    }

    public static String dump() {
        String[][] counts = getCounterArray();
        StringBuffer buffer = new StringBuffer();
        buffer.append("Object counts:\n");
        for (int i = 0; i < counts.length; i++) {
            buffer.append(counts[i][0]);
            buffer.append(" , ");
            buffer.append(counts[i][1]);
            buffer.append("\n");
        }

        buffer.append(dumpTotal());
        return buffer.toString();
    }

    public static String dumpTotal() {
        int elementCount = 0;
        int PropertyCount = 0;

        for (Iterator<String> iter = objectCount.keySet().iterator(); iter.hasNext();) {
            String objectType = iter.next();
            if (objectType.startsWith("Element:")) {
                elementCount += ((int[]) objectCount.get(objectType))[0];
            }
            else if (objectType.startsWith("Property:")) {
                PropertyCount += ((int[]) objectCount.get(objectType))[0];
            }

        }

        String result = "Element Total = " + elementCount + "\n";
        result += "Property Total = " + PropertyCount;
        return result;
    }
}
