package com.tibco.be.migration.expimp.providers.csv;

import java.util.HashMap;

import com.tibco.be.migration.CSVReader;
import com.tibco.be.migration.CSVReaderImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 26, 2008
 * Time: 10:36:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class CSVPropertyIndexDeserializer {
    public static String PROPERTY_INDEX_FILE_NAME = "propertiesIndex";
    private String inputUrl;

    public CSVPropertyIndexDeserializer(String inputUrl) {
        this.inputUrl = inputUrl;
    }

    public HashMap deserialize() throws Exception {
        HashMap indexMap = new HashMap();
        CSVReader reader = new CSVReaderImpl(this.inputUrl, PROPERTY_INDEX_FILE_NAME);
        for( String[] o = reader.nextRow(); o != null ; o = reader.nextRow() ) {

            if(o.length == 0)
                continue;

            PropertiesIndex pi = new PropertiesIndex();
            if (null != o[0]) {
                pi.setConceptName((String) o[0]);
            }
            if (null != o[1]) {
                pi.setPropertyName((String) o[1]);
            }
            if (null != o[2]) {
                pi.setPropertyIndex(Integer.valueOf((String) o[2]).intValue());
            }
            indexMap.put(pi.getConceptName()+"."+pi.getPropertyName(),pi);
        }
        return indexMap;
    }
}
