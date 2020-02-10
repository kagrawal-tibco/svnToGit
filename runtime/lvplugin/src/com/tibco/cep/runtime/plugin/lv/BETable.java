package com.tibco.cep.runtime.plugin.lv;

import com.streambase.liveview.client.LiveViewClientCapability;
import com.streambase.liveview.client.LiveViewException;
import com.streambase.liveview.client.LiveViewExceptionType;
import com.streambase.liveview.client.LiveViewQueryType;
import com.streambase.liveview.server.core.LiveViewServer;
import com.streambase.liveview.server.event.BeginSnapshotEvent;
import com.streambase.liveview.server.event.EndSnapshotEvent;
import com.streambase.liveview.server.event.EventListener;
import com.streambase.liveview.server.event.TupleAddedEvent;
import com.streambase.liveview.server.query.QueryModel;
import com.streambase.liveview.server.table.CatalogedTable;
import com.streambase.sb.Schema;
import com.streambase.sb.Tuple;
import com.tibco.cep.query.api.QueryResultSet;
import com.tibco.cep.query.functions.QueryUtilFunctions;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Ameya
 * Date: 10/7/14
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class BETable implements com.streambase.liveview.server.table.Table

{
    private Map<EventListener, BEListener> listenerMap = new ConcurrentHashMap<EventListener, BEListener>(8, 0.8f, 1);
    private CatalogedTable cTable;
    private BETableProvider beTableProvider;
    private Object eveConcept;

    public BETable(BETableProvider beTableProvider, Object eveConcept, CatalogedTable ctable) {
        this.beTableProvider = beTableProvider;
        this.cTable = ctable;
        this.eveConcept = eveConcept;

    }

    @Override
    public void addListener(EventListener lvListener, LiveViewQueryType type,
                            QueryModel query) throws LiveViewException {
        EnumSet<LiveViewClientCapability> clientCapabilities = LiveViewServer.getAbstractSession().getClientCapabilities();
        BEListener belistener = null;
        String queryStatement = query.getPredicate();
        switch (type) {

            case CONTINUOUS:
//            timeScope = TimeScope.NEW;
                break;

            case SNAPSHOT:
            /*
             * BUGBUG I'm not sure running the whole snapshot in this thread is the right thing to do.
             * The thread is per client connection at this point, but the clients thread could be tied up for a long time with a big snapshot.
             */
                doSnapShot(lvListener, queryStatement, ((BEQueryModel)query), clientCapabilities, query.getLimit());
                return;

            case SNAPSHOT_AND_CONTINUOUS:

//            timeScope = TimeScope.ALL;
                break;

            case DELETE:
                // The same BUGBUG as snapshot
                //BE doesn't support delete
                return;
            default:
                throw LiveViewExceptionType.TABLE_DOES_NOT_SUPPORT_QUERY.error();
        }

        belistener = new BEListener(eveConcept, lvListener, (BEQueryModel)query, clientCapabilities, this, query.getLimit());
        if(type != LiveViewQueryType.CONTINUOUS){
            BeginSnapshotEvent beginEvent = new BeginSnapshotEvent(belistener, clientCapabilities);
            lvListener.snapshotBegin(beginEvent );

            // Since Listeners don't provide any indicate of snapshot end, just send it immediately.
            EndSnapshotEvent endEvent = new EndSnapshotEvent(belistener, clientCapabilities);
            lvListener.snapshotEnd(endEvent);
        }

        listenerMap.put(lvListener, belistener);
    }

    private void doSnapShot(EventListener lvListener, String predicate, BEQueryModel beQueryModel, EnumSet<LiveViewClientCapability> clientCapabilities, int limit) {
        QueryResultSet qResult = null;
        BeginSnapshotEvent beginEvent = new BeginSnapshotEvent(qResult, clientCapabilities);
        lvListener.snapshotBegin(beginEvent);

        String sessionName = beTableProvider.provider.getRuleRuntime().getRuleSessions()[0].getName();
        Object resultList =   QueryUtilFunctions.executeInQuerySession(sessionName, predicate,
                null,
                false);

        Object [] rows = QueryUtilFunctions.listToArray(resultList);
        for (Object row : rows) {
            com.streambase.sb.Tuple sbTuple=null;
            sbTuple = (Tuple) row;
            TupleAddedEvent evt = new TupleAddedEvent(this, (long) 0, sbTuple, clientCapabilities, beQueryModel);
            lvListener.tupleAdded(evt);
        }
    }

    @Override
    public com.streambase.liveview.server.table.TablePublisher createPublisher(
            String arg0) throws LiveViewException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void doDelete(String arg0, QueryModel arg1) throws LiveViewException {
        // TODO Auto-generated method stub

    }

    @Override
    public QueryModel parseQuery(CatalogedTable catalogedTable, String queryString,
                                 LiveViewQueryType type, boolean includeInternal) throws LiveViewException {
        BEQueryModel bqm = new BEQueryModel(catalogedTable, this, queryString, type);
        bqm.validate(catalogedTable, includeInternal);
        return bqm;
    }

    @Override
    public void removeListener(EventListener lvListener) throws LiveViewException {
        BEListener beListener = listenerMap.get(lvListener);
        if (beListener == null) {
            // The Browsers aren't in this list and can't currently be stopped, as least partly because they execute in the service thread
            return;
        }

        listenerMap.remove(lvListener);


    }

    public String getMappedName() {
        return cTable.getId();
    }

    public List<String> getKeyFields() {
        return cTable.getKeyFields();
    }

    public Object getSpaceDef() {
        return eveConcept;
    }

    public Schema getTableSchema() {
        return cTable.getSchema(true);
    }

}

