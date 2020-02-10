package com.tibco.be.dbutils;

/**
 * Created by IntelliJ IDEA.
 * User: hokwok
 * Date: Nov 21, 2006
 * Time: 1:03:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyInfo {
    String tableName;
        int historySize;

        PropertyInfo(String _tableName, int _historySize) {
            tableName = _tableName;
            historySize = _historySize;
        }       
}
