package com.tibco.cep.query.api.impl.local;

import com.tibco.cep.query.api.QueryResultSet;
import com.tibco.cep.query.api.QueryStatement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 12/13/12
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class AggregateResultSet implements QueryResultSet {

    Map<Object, Object> resultMap;
    Iterator entries;
    Entry resultEntry;
    int keyFields;
    String[] fieldNames;
    String valueFieldName;
    boolean nextFlag;


    public AggregateResultSet(Map resultMap, String valueFieldName, String... groupByFieldNames) {
        this.resultMap = resultMap;
        this.entries = resultMap.entrySet().iterator();
        this.valueFieldName = valueFieldName;
        if (groupByFieldNames.length > 0) {
            fieldNames = groupByFieldNames;
            this.keyFields = groupByFieldNames.length;
        } else {
            fieldNames = null;
            this.keyFields = 0;
        }

    }


    @Override
    public void close() throws Exception {
    }

    @Override
    public int findColumn(String name) {
        if (fieldNames != null) {
            int index = 0;
            for (String s : fieldNames) {
                if (s.equals(name)) {
                    return index;
                }
                index++;
            }
        }

        return valueFieldName.equals(name) ? 0 : -1;
    }


    @Override
    public Object getObject(int columnIndex) throws IndexOutOfBoundsException {
        Object columnEntry;
        if (columnIndex <= keyFields) {
            if (nextFlag == true) {
                resultEntry = (Entry) entries.next();
            }

            if (keyFields >= 1 && fieldNames != null) {
                if (columnIndex <= keyFields - 1) {
                    Object key = resultEntry.getKey();
                    List<Object> keyList = (List<Object>) key;
                    columnEntry = keyList.get(columnIndex);
                } else {
                    columnEntry = resultEntry.getValue();
                }
            } else {
                columnEntry = resultEntry.getValue();
            }
            nextFlag = false;
            return columnEntry;
        }

        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getRowCountIfPossible() {
        return resultMap.size();
    }

    @Override
    public QueryStatement getStatement() {
        return null;
    }

    @Override
    public boolean isBatchEnd() {
        return false;
    }

    @Override
    public boolean next() {
        nextFlag = true;
        return entries.hasNext();
    }
}
