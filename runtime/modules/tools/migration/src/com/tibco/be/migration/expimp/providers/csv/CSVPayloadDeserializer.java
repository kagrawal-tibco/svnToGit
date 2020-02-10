package com.tibco.be.migration.expimp.providers.csv;

import com.tibco.be.migration.expimp.Base64;
import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.driver.tibrv.serializer.TibrvMsgPayload;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.service.loader.BEClassLoader;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 28, 2008
 * Time: 10:04:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVPayloadDeserializer {
    public final static int XINODE_PAYLOAD_TYPE = 1;
    public final static int OBJECT_PAYLOAD_TYPE = 2;
    public final static int TIBRVMSG_PAYLOAD_TYPE = 3;
    private EntityStore entityStore;
    private PayloadFactory payloadFactory;

    static {

        try {
            Class.forName("com.tibco.cep.runtime.model.event.impl.XiNodePayload");
            Class.forName("com.tibco.cep.runtime.model.event.impl.ObjectPayload");
            Class.forName("com.tibco.cep.driver.tibrv.serializer.TibrvMsgPayload");
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }


    public CSVPayloadDeserializer(EntityStore es) {
        this.entityStore = es;
        BEClassLoader bcl = (BEClassLoader) es.getDbStore().getRuleServiceProvider().getClassLoader();
        this.payloadFactory = bcl.getPayloadFactory();
    }

    public SimpleEventImpl deserialize(String[] columns, SimpleEventImpl event, String inputVersion, String outputVersion) throws Exception {
        long id = 0L;
        int payloadType = 0;
        byte [] payloadData = new byte[0];

        //#"eventId;payloadType;payloadData"
        if(columns.length < 3) {
            throw new Exception("Incorrect CSV data structure: not enough columns.");
        }
        if(null != columns[0]) {
            id = Long.valueOf(columns[0]).longValue();
        }
        if(null != columns[1]) {
            payloadType = Integer.valueOf(columns[1]).intValue();
        }
        if(null != columns[2]) {
            payloadData = Base64.decode(columns[2]);
        }

        EventPayload payload = null;
        if(inputVersion.startsWith("1.4")) {
            payloadType = -99;
            payload = loadPayload(entityStore.getTypeDescriptor(),payloadData);
            if(payload != null) {
                payloadType = PayloadFactoryImpl.getPayloadTypeId(payload.getClass());
            }
        }
        switch(payloadType) {
             case XINODE_PAYLOAD_TYPE:
             case OBJECT_PAYLOAD_TYPE:
             case TIBRVMSG_PAYLOAD_TYPE:
                 try {
                    payload = payloadFactory.createPayload(entityStore.getTypeDescriptor().getExpandedName(),payloadData);
                 }
                 catch (Throwable oe) {
                    this.entityStore.getLogger().log(Level.ERROR, oe, oe.getMessage());
                 }
                 event.setPayload(payload);
                 break;
            default:
                throw new IllegalArgumentException("Unknown payload type.");
        }
        return event;
    }

    private EventPayload loadPayload(TypeManager.TypeDescriptor td, byte[] payloadData) {
        try {
            XiNodePayload xpl = new XiNodePayload(td,payloadData);
            return xpl;
        } catch (Exception e) {
            try {
                ObjectPayload opl = new ObjectPayload(td,payloadData);
                return opl;
            } catch (Exception e1) {
                try {
                    TibrvMsgPayload tpl = new TibrvMsgPayload(td,payloadData);
                    return tpl;
                } catch (Exception e2) {
                    return null;
                }
            }
        }
    }
}
