package com.tibco.cep.query.aggregate.as;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.query.aggregate.common.AbstractMaxAggregator;
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
 * Date: 11/26/12
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class AsMaxAggregator extends AbstractMaxAggregator implements MemberInvocable {
    private static AsHelper helper;
    private static final String STRING = "string";
    private static final String LONG = "long";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";

    public AsMaxAggregator() {
    }

    @Override
    public Map<Object, Object> aggregate() throws ASException {//String fieldName, String filter, String... groupByFieldNames) {
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
        } else if (helper.getDataType().equalsIgnoreCase(STRING) && helper.getGroupByFieldNames() == null) {
            type = 5;
        } else if (helper.getDataType().equalsIgnoreCase(LONG) && helper.getGroupByFieldNames() != null) {
            type = 6;
        } else if (helper.getDataType().equalsIgnoreCase(FLOAT) && helper.getGroupByFieldNames() != null) {
            type = 7;
        } else if (helper.getDataType().equalsIgnoreCase(INTEGER) && helper.getGroupByFieldNames() != null) {
            type = 8;
        } else if (helper.getDataType().equalsIgnoreCase(DOUBLE) && helper.getGroupByFieldNames() != null) {
            type = 9;
        } else if (helper.getDataType().equalsIgnoreCase(STRING) && helper.getGroupByFieldNames() != null) {
            type = 10;
        }

        Tuple tmpEntry;
        Browser lBrowser = helper.getBrowser();
        String[] groupByFieldNames = helper.getGroupByFieldNames();
        String fieldName = helper.getFieldName();
        ArrayList<Object> groupByList = null;

        while ((tmpEntry = lBrowser.next()) != null) {
            if (type >= 6) {
                groupByList = new ArrayList<Object>();
                for (String s : groupByFieldNames) {
                    groupByList.add(tmpEntry.get(s));
                }
            }
            switch (type) {
                case 1:
                    long lValue = tmpEntry.getLong(fieldName);
                    Long l = (Long) resultMap.get(AsConstants.MAX_RESULT);
                    if (l != null) {
                        if (l < lValue) {
                            resultMap.put(AsConstants.MAX_RESULT, lValue);
                        }
                    } else {
                        resultMap.put(AsConstants.MAX_RESULT, lValue);
                    }
                    break;

                case 2:
                    float fValue = tmpEntry.getFloat(fieldName);
                    Float f = (Float) resultMap.get(AsConstants.MAX_RESULT);
                    if (f != null) {
                        if (f < fValue) {
                            resultMap.put(AsConstants.MAX_RESULT, fValue);
                        }
                    } else {
                        resultMap.put(AsConstants.MAX_RESULT, fValue);
                    }
                    break;

                case 3:
                    int iValue = tmpEntry.getInt(fieldName);
                    Integer i = (Integer) resultMap.get(AsConstants.MAX_RESULT);
                    if (i != null) {
                        if (i < iValue) {
                            resultMap.put(AsConstants.MAX_RESULT, iValue);
                        }
                    } else {
                        resultMap.put(AsConstants.MAX_RESULT, iValue);
                    }
                    break;

                case 4:
                    double dValue = tmpEntry.getInt(fieldName);
                    Double d = (Double) resultMap.get(AsConstants.MAX_RESULT);
                    if (d != null) {
                        if (d < dValue) {
                            resultMap.put(AsConstants.MAX_RESULT, dValue);
                        }
                    } else {
                        resultMap.put(AsConstants.MAX_RESULT, dValue);
                    }
                    break;

                case 5:
                    String sValue = tmpEntry.getString(fieldName);
                    String s = (String) resultMap.get(AsConstants.MAX_RESULT);
                    if (s != null) {
                        if (sValue.compareTo(s) > 0) {
                            resultMap.put(AsConstants.MAX_RESULT, sValue);
                        }
                    } else {
                        resultMap.put(AsConstants.MAX_RESULT, sValue);
                    }
                    break;

                case 6:
                    lValue = tmpEntry.getLong(fieldName);
                    l = (Long) resultMap.get(groupByList);
                    if (l != null) {
                        if (l < lValue) {
                            resultMap.put(groupByList, lValue);
                        }
                    } else {
                        resultMap.put(groupByList, lValue);
                    }
                    break;

                case 7:
                    fValue = tmpEntry.getFloat(fieldName);
                    f = (Float) resultMap.get(groupByList);
                    if (f != null) {
                        if (f < fValue) {
                            resultMap.put(groupByList, fValue);
                        }
                    } else {
                        resultMap.put(groupByList, fValue);
                    }
                    break;

                case 8:
                    iValue = tmpEntry.getInt(fieldName);
                    i = (Integer) resultMap.get(groupByList);
                    if (i != null) {
                        if (i < iValue) {
                            resultMap.put(groupByList, iValue);
                        }
                    } else {
                        resultMap.put(groupByList, iValue);
                    }
                    groupByList = null;
                    break;

                case 9:
                    dValue = tmpEntry.getDouble(fieldName);
                    d = (Double) resultMap.get(groupByList);
                    if (d != null) {
                        if (d < dValue) {
                            resultMap.put(groupByList, dValue);
                        }
                    } else {
                        resultMap.put(groupByList, dValue);
                    }
                    break;

                case 10:
                    sValue = tmpEntry.getString(fieldName);
                    s = (String) resultMap.get(groupByList);
                    if (s != null) {
                        if (sValue.compareTo(s) > 0) {
                            resultMap.put(groupByList, sValue);
                        }
                    } else {
                        resultMap.put(groupByList, sValue);
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

        //------------------------------------

        if (tuple.containsKey(AsConstants.GROUP_BY)) {
            try {
                groupByFieldNames = (String[]) ser.deSerialize(tuple.getBlob(AsConstants.GROUP_BY));
                helper = new AsHelper(fieldName, dataType, filter, space, groupByFieldNames);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            helper = new AsHelper(fieldName, dataType, filter, space, null);
        }

        //---------------------------------------

        Tuple maxResult = Tuple.create();
        Map<Object, Object> resMap;
        try {
            resMap = aggregate();
            byte[] result = ser.serialize(resMap);
            maxResult.putBlob(TypeConstants.RESULT, result);
        } catch (ASException ase) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ase.printStackTrace(printWriter);
            maxResult = Tuple.create();
            maxResult.put("error", writer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maxResult;
    }
}
