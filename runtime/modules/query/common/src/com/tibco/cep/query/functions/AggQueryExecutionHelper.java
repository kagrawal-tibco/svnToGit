package com.tibco.cep.query.functions;

import com.tibco.cep.query.api.impl.local.AggregateResultSet;
import com.tibco.cep.query.api.QueryResultSet;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 1/30/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AggQueryExecutionHelper extends SsQueryExecutionHelper {
    public static List execute(Map<Object, Object> resultMap, String resultSetName, String valueFieldNames, String... groupByFieldNames) {
        Object resultList = QueryUtilFunctions.newList();
        QueryResultSet queryResultSet;

        if (groupByFieldNames.length > 0) {
            queryResultSet = new AggregateResultSet(resultMap, valueFieldNames, groupByFieldNames);
        } else {
            queryResultSet = new AggregateResultSet(resultMap, valueFieldNames);
        }

        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultSet);

        int columnCount = groupByFieldNames.length + 1;
        try {
            while (ResultSetFunctions.next(resultSetName)) {
                //Single column in the row.
                if (columnCount == 1) {
                    Object column = ResultSetFunctions.get(resultSetName, 0);

                    QueryUtilFunctions.addToList(resultList, column);
                }

                //----------------

                // Multiple columns in the row.
                // Delete query returns 2 columns - (id, ext_id) in each row.
                else {
                     Object[] columns = QueryUtilFunctions.newArray(columnCount);
                    //ArrayList<Object> columns = new ArrayList<Object>();//QueryUtilFunctions.newArray(columnCount);
                    for (int i = 0; i < columnCount; i = i + 1) {
                        columns[i] = ResultSetFunctions.get(resultSetName, i);
                      //  columns.add(ResultSetFunctions.get(resultSetName, i));
                    }

                    QueryUtilFunctions.addToList(resultList, columns);
                }
            }
        } finally {
            ResultSetFunctions.closeAggregate(resultSetName);
        }

        return (List) resultList;
    }

}
