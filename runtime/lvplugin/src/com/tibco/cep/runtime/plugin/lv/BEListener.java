package com.tibco.cep.runtime.plugin.lv;

import com.streambase.liveview.client.LiveViewClientCapability;
import com.streambase.liveview.server.event.EventListener;
import com.streambase.liveview.server.event.TupleAddedEvent;
import com.streambase.liveview.server.table.Table;
import com.streambase.sb.TupleException;
import com.tibco.cep.query.api.QueryListener;
import com.tibco.cep.query.api.QueryListenerHandle;
import com.tibco.cep.query.api.Row;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: Ameya
 * Date: 10/7/14
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEListener implements QueryListener{

    private final EventListener listener;
    private BETupleCopier beTupleCopier;
    private final EnumSet<LiveViewClientCapability> clientCapabilities;
    private Object eveConcept;
    public final int limit;

    private QueryListenerHandle handle;

    private final Table table;
    private BEQueryModel beQueryModel;

    public BEListener(Object eveConcept, EventListener listener, BEQueryModel beQueryModel, EnumSet<LiveViewClientCapability> clientCapabilities, Table table, int limit) {
        this.listener = listener;
        this.beQueryModel = beQueryModel;
        this.beTupleCopier = beQueryModel.getBETupleCopier();
        this.clientCapabilities = clientCapabilities;
        this.limit=limit;
        this.table=table;
        this.eveConcept=eveConcept;

    }

    @Override
    public void onBatchEnd() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNewRow(Row row) {
        com.streambase.sb.Tuple sbNewTuple = null;
        try {
            sbNewTuple = beTupleCopier.copy(row);
        } catch (TupleException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        TupleAddedEvent evt = new TupleAddedEvent(this, (long) 0, sbNewTuple, clientCapabilities, beQueryModel);
        listener.tupleAdded(evt);

    }

    @Override
    public void onQueryEnd() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onQueryStart(QueryListenerHandle handle) {
        this.handle = handle;

    }




}

