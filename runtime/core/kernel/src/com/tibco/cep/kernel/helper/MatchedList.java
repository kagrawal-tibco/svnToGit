package com.tibco.cep.kernel.helper;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Sep 5, 2007
 * Time: 3:28:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class MatchedList {

    int length;
    int nonNull;
    Object[][] resultList;
    Map idToObjMap;

    public MatchedList(int initSize, Map objectMaps){
        nonNull = 0;
        length  = 0;
        resultList = new Object[initSize < 32? 32 : initSize][];
        idToObjMap = objectMaps;
    }

    public MatchedList(Object[] firstRow, Map objectMaps) {
        this(0, objectMaps);
        addRow(firstRow);
    }

    public int numRow() {
        return nonNull;
    }

    public int length() {
        return length;
    }

    public void nullRow(int i) {
        if(resultList[i] == null)
            return;
        resultList[i] = null;
        nonNull--;
    }

    public Object[] getRow(int i) {
        return resultList[i];
    }

    public void addRow(Object[] row) {
        if(row == null) throw new RuntimeException("PROGRAM ERROR: Can't add null row");
        nonNull++;
        if(length == resultList.length)
            resize();
        resultList[length] = row;
        length++;
    }

    public void chgRow(int i, Object[] row) {
        if(row == null) throw new RuntimeException("PROGRAM ERROR: Can't reset with null row");
        resultList[i] = row;
    }

    protected void resize() {
        Object[][] newList = new Object[resultList.length*2][];
        System.arraycopy(resultList, 0, newList, 0, resultList.length);
        resultList = newList;
    }

    public Map getObjectMaps() {
        return idToObjMap;
    }


}
