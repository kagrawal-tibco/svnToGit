package com.tibco.cep.runtime.hbase;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.hbase.adaptor.HConceptTupleAdaptor;
import com.tibco.cep.runtime.hbase.types.HDataType;
import com.tibco.cep.runtime.hbase.util.HMapConstants;
import com.tibco.cep.runtime.service.om.api.DataCacheConfig;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.impl.AbstractEntityDao;
import com.tibco.cep.runtime.util.SystemProperty;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 10/9/13
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class HEntityDao<E extends Entity> extends AbstractEntityDao<E, HMap<Long,E>> {

    HConnection hConnection;


    @Override
    protected HMap<Long, E> startHook(boolean overwrite) {
        try {
            DataCacheConfig dataCacheConfig = daoConfig.getDataCacheConfig();

            hConnection = HConnectionManager.createConnection(HResourceManager.getInstance().getHbaseConfig());
            HBaseAdmin hAdmin = HResourceManager.getInstance().getHbaseAdmin();

            HMapCreator.Parameters parameters = new HMapCreator.Parameters<Long, Entity>()
                                                .setHConnection(hConnection)
                                                .setTableName(cacheName)
                                                .setHAdmin(hAdmin);

            handleValueType(parameters, dataCacheConfig);

            HMap<Long, E> hMap = HMapCreator.createHMap(parameters);

            return hMap;

        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    private void handleValueType(HMapCreator.Parameters parameters, DataCacheConfig dataCacheConfig) {
        String uri = daoConfig.getUri();

        com.tibco.cep.designtime.model.Entity entity =
                cluster.getRuleServiceProvider().getProject().getOntology().getEntity(uri);

        boolean custom = false;

        Properties properties = cluster.getRuleServiceProvider().getProperties();
        String codecExplicitStr = properties.getProperty(SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(), Boolean.FALSE.toString());
        boolean codecExplicit = Boolean.parseBoolean(codecExplicitStr);

        if (codecExplicit) {
            if (entity instanceof Concept /*&& !entity.toString().contains("Scorecard")*/) {
                custom = HConceptTupleAdaptor.canHandle(cluster, parameters, (Concept) entity);
            }
            else if (entity instanceof Event) {
               // custom = HEventTupleAdaptor.canHandle(cluster, parameters, (Event) entity);
            }
        }

        if(!custom)
        {
            Map<String, HDataType> fieldDefs = new HashMap<>();
            fieldDefs.put(HMapConstants.VALUECOLUMN,HDataType.Object);
        }
        else
        {

        }


    }

    @Override
    public Collection<E> getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<E> getAll(Collection<Long> keys) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean lock(Long key, long timeoutMillis) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean unlock(Long key) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set entrySet(Filter filter, int limit) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set keySet(Filter filter, int limit) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map invoke(Filter filter, Invocable invocable) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Invocable.Result invokeWithKey(Object key, Invocable invocable) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map invoke(Set keys, Invocable invocable) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
