package com.tibco.cep.query.aggregate.as;

import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.query.aggregate.common.AbstractAvgAggregator;
import com.tibco.cep.query.aggregate.common.RawAverage;
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
 * Date: 11/29/12
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsAvgAggregator extends AbstractAvgAggregator implements MemberInvocable {
    private static AsHelper helper;
    private static final String STRING = "string";
    private static final String LONG = "long";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";

    public AsAvgAggregator() {
    }

    @Override
    public Map<Object, Object> aggregate() throws Exception {
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

        RawAverage avgRaw;
        while ((tmpEntry = lBrowser.next()) != null) {
            if (type >= 1 && type <= 4) {
                avgRaw = (RawAverage) resultMap.get(AsConstants.AVG_RESULT);
            } else {
                groupByList = new ArrayList<Object>();
                for (String s : groupByFieldNames) {
                    groupByList.add(tmpEntry.get(s));
                }
                avgRaw = (RawAverage) resultMap.get(groupByList);
            }

            switch (type) {
                case 1:
                    long lValue = tmpEntry.getLong(fieldName);
                    if (avgRaw != null) {
                        avgRaw.setSum((Long) avgRaw.getSum() + lValue);
                        avgRaw.setCount(avgRaw.getCount() + 1);
                    } else {
                        resultMap.put(AsConstants.AVG_RESULT, new RawAverage(lValue, 1));
                    }
                    break;

                case 2:
                    float fValue = tmpEntry.getFloat(helper.getFieldName());
                    if (avgRaw != null) {
                        avgRaw.setSum((Double) avgRaw.getSum() + fValue);
                        avgRaw.setCount(avgRaw.getCount() + 1);
                    } else {
                        resultMap.put(AsConstants.AVG_RESULT, new RawAverage(fValue, 1));
                    }
                    break;

                case 3:
                    int iValue = tmpEntry.getInt(fieldName);
                    if (avgRaw != null) {
                        avgRaw.setSum((Integer) avgRaw.getSum() + iValue);
                        avgRaw.setCount(avgRaw.getCount() + 1);
                    } else {
                        resultMap.put(AsConstants.AVG_RESULT, new RawAverage(iValue, 1));
                    }
                    break;

                case 4:
                    double dValue = tmpEntry.getDouble(fieldName);
                    if (avgRaw != null) {
                        avgRaw.setSum((Double) avgRaw.getSum() + dValue);
                        avgRaw.setCount(avgRaw.getCount() + 1);
                    } else {
                        resultMap.put(AsConstants.AVG_RESULT, new RawAverage(dValue, 1));
                    }
                    break;

                case 5:
                    lValue = tmpEntry.getLong(fieldName);
                    if (avgRaw != null) {
                        avgRaw.setSum((Long) avgRaw.getSum() + lValue);
                        avgRaw.setCount(avgRaw.getCount() + 1);
                    } else {
                        resultMap.put(groupByList, new RawAverage(lValue, 1));
                    }
                    break;

                case 6:
                    fValue = tmpEntry.getFloat(fieldName);
                    if (avgRaw != null) {
                        avgRaw.setSum((Float) avgRaw.getSum() + fValue);
                        avgRaw.setCount(avgRaw.getCount() + 1);
                    } else {
                        resultMap.put(groupByList, new RawAverage(fValue, 1));
                    }
                    break;

                case 7:
                    iValue = tmpEntry.getInt(fieldName);
                    if (avgRaw != null) {
                        avgRaw.setSum((Integer) avgRaw.getSum() + iValue);
                        avgRaw.setCount(avgRaw.getCount() + 1);
                    } else {
                        resultMap.put(groupByList, new RawAverage(iValue, 1));
                    }
                    break;

                case 8:
                    dValue = tmpEntry.getDouble(fieldName);
                    if (avgRaw != null) {
                        avgRaw.setSum((Double) avgRaw.getSum() + dValue);
                        avgRaw.setCount(avgRaw.getCount() + 1);
                    } else {
                        resultMap.put(groupByList, new RawAverage(dValue, 1));
                    }
                    break;
            }
        }

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

        Tuple avgResult = Tuple.create();
        Map<Object, Object> resMap;

        try {
            resMap = aggregate();
            byte[] result = ser.serialize(resMap);
            avgResult.putBlob(TypeConstants.RESULT, result);
        } catch (Exception ase) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ase.printStackTrace(printWriter);
            avgResult = Tuple.create();
            avgResult.put("error", writer.toString());
        }

        return avgResult;
    }
}
