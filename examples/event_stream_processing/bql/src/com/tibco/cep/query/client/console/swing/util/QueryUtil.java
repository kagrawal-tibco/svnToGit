package com.tibco.cep.query.client.console.swing.util;

import com.tibco.cep.query.functions.QueryMetadataFunctions;

/**
 *
 * @author ksubrama
 */
public class QueryUtil {

    private QueryUtil() {
    }

    public static String[] getColumnNames(String queryName) {
        SessionUtil.initSession();
        int colCount = QueryMetadataFunctions.getColumnCount(queryName);
        String[] columns = new String[colCount + 1];
        columns[0] = "#";
        for (int i = 0; i < colCount; i++) {
            columns[i + 1] = QueryMetadataFunctions.getColumnName(queryName, i);
        }
        return columns;
    }

    public static String[] getTypes(String queryName) {
        SessionUtil.initSession();
        int colCount = QueryMetadataFunctions.getColumnCount(queryName);
        String[] types = new String[colCount + 1];
        types[0] = Integer.class.getName();
        for (int i = 0; i < colCount; i++) {
            types[i + 1] = QueryMetadataFunctions.getColumnType(queryName, i);
        }
        return types;
    }

}
