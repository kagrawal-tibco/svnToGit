package com.tibco.cep.query.aggregate.as;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/28/12
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.query.aggregate.common.AbstractSumAggregator;
import com.tibco.cep.query.aggregate.common.TypeConstants;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/27/12
 * Time: 11:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class AsSumAggregator extends AbstractSumAggregator implements MemberInvocable {
    private static AsHelper helper;
    private static final String LONG = "long";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";

    public AsSumAggregator() {
    }

    @Override
    public Map<Object, Object> aggregate() throws ASException {
        HashMap<Object, Object> resultMap = new HashMap<Object, Object>();
        int type = 0;

        if (helper.getDataType().equalsIgnoreCase(LONG) && helper.getGroupByFieldNames() == null) {
            type = 1;
        } else if (helper.getDataType().equalsIgnoreCase(FLOAT) && helper.getGroupByFieldNames() == null) {
            type = 2;
        } else if (helper.getDataType().equalsIgnoreCase(INTEGER) && helper.getGroupByFieldNames() == null) {
            type = 3;
        } else if (helper.getDataType().equalsIgnoreCase(DOUBLE) && helper.getGroupByFieldNames() == null) {
            type = 4;
        } else if (helper.getDataType().equalsIgnoreCase(LONG) && helper.getGroupByFieldNames() != null) {
            type = 5;
        } else if (helper.getDataType().equalsIgnoreCase(FLOAT) && helper.getGroupByFieldNames() != null) {
            type = 6;
        } else if (helper.getDataType().equalsIgnoreCase(INTEGER) && helper.getGroupByFieldNames() != null) {
            type = 7;
        } else if (helper.getDataType().equalsIgnoreCase(DOUBLE) && helper.getGroupByFieldNames() != null) {
            type = 8;
        }

        Tuple tmpEntry;
        Browser lBrowser = helper.getBrowser();
        String[] groupByFieldNames = helper.getGroupByFieldNames();
        String fieldName = helper.getFieldName();
        ArrayList<Object> groupByList = null;

        while ((tmpEntry = lBrowser.next()) != null) {
            if (type >= 5) {
                groupByList = new ArrayList<Object>();
                for (String s : groupByFieldNames) {
                    groupByList.add(tmpEntry.get(s));
                }
            }
            switch (type) {
                case 1:
                    long lValue = tmpEntry.getLong(fieldName);
                    Long l = (Long) resultMap.get(AsConstants.SUM_RESULT);
                    if (l != null) {
                        long lSum = l + lValue;
                        resultMap.put(AsConstants.SUM_RESULT, lSum);
                    } else {
                        resultMap.put(AsConstants.SUM_RESULT, lValue);
                    }
                    break;

                case 2:
                    float fValue = tmpEntry.getFloat(fieldName);
                    Float f = (Float) resultMap.get(AsConstants.SUM_RESULT);
                    if (f != null) {
                        float fSum = f + fValue;
                        resultMap.put(AsConstants.SUM_RESULT, fSum);
                    } else {
                        resultMap.put(AsConstants.SUM_RESULT, fValue);
                    }
                    break;

                case 3:
                    int iValue = tmpEntry.getInt(fieldName);
                    Integer i = (Integer) resultMap.get(AsConstants.SUM_RESULT);
                    if (i != null) {
                        int iSum = i + iValue;
                        resultMap.put(AsConstants.SUM_RESULT, iSum);
                    } else {
                        resultMap.put(AsConstants.SUM_RESULT, iValue);
                    }
                    break;

                case 4:
                    double dValue = tmpEntry.getInt(fieldName);
                    Double d = (Double) resultMap.get(AsConstants.SUM_RESULT);
                    if (d != null) {
                        double dSum = d + dValue;
                        resultMap.put(AsConstants.SUM_RESULT, dSum);
                    } else {
                        resultMap.put(AsConstants.SUM_RESULT, dValue);
                    }
                    break;

                case 5:
                    lValue = tmpEntry.getLong(fieldName);
                    l = (Long) resultMap.get(groupByList);
                    if (l != null) {
                        long lSum = l + lValue;
                        resultMap.put(groupByList, lSum);
                    } else {
                        resultMap.put(groupByList, lValue);
                    }
                    groupByList = null;
                    break;

                case 6:
                    fValue = tmpEntry.getFloat(fieldName);
                    f = (Float) resultMap.get(groupByList);
                    if (f != null) {
                        float fSum = f + fValue;
                        resultMap.put(groupByList, fSum);
                    } else {
                        resultMap.put(groupByList, fValue);
                    }
                    break;

                case 7:
                    iValue = tmpEntry.getInt(fieldName);
                    i = (Integer) resultMap.get(groupByList);
                    if (i != null) {
                        int iSum = i + iValue;
                        resultMap.put(groupByList, iSum);
                    } else {
                        resultMap.put(groupByList, iValue);
                    }
                    break;

                case 8:
                    dValue = tmpEntry.getDouble(fieldName);
                    d = (Double) resultMap.get(groupByList);
                    if (d != null) {
                        double dSum = d + dValue;
                        resultMap.put(groupByList, dSum);
                    } else {
                        resultMap.put(groupByList, dValue);
                    }
                    break;
            }
        }
        lBrowser.stop();
        return resultMap;
    }

    @Override
    public Tuple invoke(Space space, Tuple tuple) {
        String filter = tuple.getString(AsConstants.FIELD_FILTER);
        String fieldName = tuple.getString(AsConstants.FIELD_NAME);
        String dataType = tuple.getString(AsConstants.DATA_TYPE);
        String[] groupByFieldNames;
        Serializer ser = new Serializer();

        //-------------------------------------

        if (tuple.containsKey(AsConstants.GROUP_BY)) {
            try {
                groupByFieldNames = (String[]) ser.deSerialize(tuple.getBlob(AsConstants.GROUP_BY));
                helper = new AsHelper(fieldName, dataType, filter, space, groupByFieldNames);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            helper = new AsHelper(fieldName, dataType, filter, space, null);
        }

        //----------------------------------------

        Tuple sumResult = Tuple.create();
        Map<Object, Object> resMap;

        try {
            resMap = aggregate();
            byte[] result = ser.serialize(resMap);
            sumResult.putBlob(TypeConstants.RESULT, result);
        } catch (ASException ase) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ase.printStackTrace(printWriter);
            sumResult = Tuple.create();
            sumResult.put("error", writer.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sumResult;
    }
}
