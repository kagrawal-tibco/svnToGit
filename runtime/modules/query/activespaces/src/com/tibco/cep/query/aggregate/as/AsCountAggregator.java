package com.tibco.cep.query.aggregate.as;

/*
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/16/12
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.remote.MemberInvocable;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.aggregate.common.AbstractCountAggregator;
import com.tibco.cep.query.aggregate.common.TypeConstants;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AsCountAggregator extends AbstractCountAggregator implements MemberInvocable {
    private static AsHelper helper;

    static Logger logger;

    public AsCountAggregator() {
        logger = LogManagerFactory.getLogManager().getLogger(AsCountAggregator.class);
    }

    @Override
    public Map<Object, Object> aggregate() throws ASException {
        HashMap<Object, Object> resultMap = new HashMap<Object, Object>();
        int type;

        if (helper.getGroupByFieldNames() == null) {
            type = 1;
        } else {
            type = 2;
        }

        Tuple tmpEntry;
        Browser lBrowser = helper.getBrowser();
        String[] groupByFieldNames = helper.getGroupByFieldNames();
        ArrayList<Object> groupByList;

        while ((tmpEntry = lBrowser.next()) != null) {

            switch (type) {
                case 1:
                    Integer i = (Integer) resultMap.get(AsConstants.COUNT_RESULT);
                    if (i != null) {
                        resultMap.put(AsConstants.COUNT_RESULT, i + 1);
                    } else {
                        resultMap.put(AsConstants.COUNT_RESULT, 1);
                    }
                    break;

                case 2:
                    groupByList = new ArrayList<Object>();
                    for (String s : groupByFieldNames) {
                        groupByList.add(tmpEntry.get(s));
                    }
                    i = (Integer) resultMap.get(groupByList);
                    if (i != null) {
                        resultMap.put(groupByList, i + 1);
                    } else {
                        resultMap.put(groupByList, 1);
                    }
                    break;
            }
        }
        lBrowser.stop();
        //logger.log(Level.DEBUG, "RESULT MAP : "+resultMap);
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            helper = new AsHelper(fieldName, dataType, filter, space, null);
        }

        //-----------------------------------------

        Tuple countResult = Tuple.create();
        Map<Object, Object> resMap;

        try {
            resMap = aggregate();
            byte[] result = ser.serialize(resMap);
            countResult.putBlob(TypeConstants.RESULT, result);
        } catch (ASException ase) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ase.printStackTrace(printWriter);
            countResult = Tuple.create();
            countResult.put("error", writer.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return countResult;
    }
}
