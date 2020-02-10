package com.tibco.cep.query.aggregate.as;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.aggregate.common.TypeConstants;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/18/12
 * Time: 7:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsHelper {

    private String fieldName;
    private String dataType;
    private String filter;
    private Space space;
    private String[] groupByFieldNames;

    private Logger logger;

    /*AsHelper(String fieldName, String dataType, String filter, Space space) {
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.filter = filter;
        this.space = space;
    }*/

    AsHelper(String fieldName, String dataType, String filter, Space space, String[] groupByFieldNames) {
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.filter = filter;
        this.space = space;
        this.groupByFieldNames = groupByFieldNames;
        this.logger = LogManagerFactory.getLogManager().getLogger(AsHelper.class);
    }

    public static Map<Object, Object> getResultData(Tuple result) throws ClassNotFoundException, IOException {
        byte[] resArray;
        Serializer ser = new Serializer();
        resArray = (byte[]) result.get(TypeConstants.RESULT);
        return (HashMap<Object, Object>) ser.deSerialize(resArray);
    }

    public String getFilter() {
        return filter;
    }

    public Space getSpace() {
        return space;
    }

    String getFieldName() {
        return this.fieldName;
    }

    String getDataType() {
        return this.dataType;
    }

    String[] getGroupByFieldNames() {
        return groupByFieldNames;
    }

    Browser getBrowser() throws ASException {
        BrowserDef lBrowserDef = BrowserDef.create();
        lBrowserDef.setTimeout(BrowserDef.NO_WAIT);
        lBrowserDef.setDistributionScope(BrowserDef.DistributionScope.SEEDED);
        lBrowserDef.setTimeScope(BrowserDef.TimeScope.SNAPSHOT);

        Long prefetchSize = Long.getLong(ASConstants.PROP_AS_AGGREGATE_PREFETCH_SIZE);

        if (prefetchSize == null) {
            lBrowserDef.setPrefetch(BrowserDef.PREFETCH_ALL);
            logger.log(Level.INFO, "Browser.prefetch has been set to PREFETCH_ALL");
        } else {
            lBrowserDef.setPrefetch(prefetchSize);
            logger.log(Level.INFO, "Browser.prefetch has been set to %s", prefetchSize);
        }

        // Setting the Query Limit to -1. This feature was added in AS 2.1.2 V110, where the default is set to 10000.
        // To revert it back to previous behavior we had to set it to -1.
        lBrowserDef.setQueryLimit(-1);

        Browser lBrowser;

        if (filter == null || filter.length() <= 0) {
            lBrowser = space.getMetaspace().browse(space.getName(), BrowserDef.BrowserType.GET, lBrowserDef);
        } else {
            lBrowser = space.getMetaspace().browse(space.getName(), BrowserDef.BrowserType.GET, lBrowserDef, filter);
        }

        return lBrowser;
    }
}
