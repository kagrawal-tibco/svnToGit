package com.tibco.cep.driver.tibrv.serializer;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticleTerm;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Nov 2, 2006
 * Time: 4:34:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TibrvMsgPayload implements EventPayload {

    TibrvMsg msg;
    ExpandedName name;
    SmParticleTerm payloadTerm;
    protected boolean isReallyPayload;
    protected static final ExpandedName XNAME_OF_PAYLOAD_CONTAINER = ExpandedName.makeName("payload");


    static {
        try {
            PayloadFactoryImpl.registerType(TibrvMsgPayload.class, 3);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public TibrvMsgPayload(TypeManager.TypeDescriptor eventDescriptor, byte[] buf) throws Exception {
        msg = new TibrvMsg(buf);
        name = eventDescriptor.getExpandedName();
        payloadTerm = PayloadFactoryImpl.getPayloadTerm(eventDescriptor.getSmElement());
    }


    public TibrvMsgPayload(ExpandedName name, TibrvMsg msg) {
        this(name, null, msg, false);
    }


    public TibrvMsgPayload(
            ExpandedName name,
            SmParticleTerm payloadTerm,
            TibrvMsg msg,
            boolean isReallyPayload) {
        this.name = name;
        this.payloadTerm = payloadTerm;
        this.msg = msg;  //Should we keep around the Msg - NICK what is the OM doing, or should I release it
        this.isReallyPayload = isReallyPayload;
    }


    public void toXiNode(XiNode root) {
        if (this.payloadTerm == null) return ;

        XiNode node = RVDeSerializer.deSerialize((SmElement)this.payloadTerm, msg, !this.isReallyPayload);
        XiNode payloadContainerNode = root.appendElement(XNAME_OF_PAYLOAD_CONTAINER);
        payloadContainerNode.appendChild(node);
    }


    public byte[] toBytes() throws Exception {
        return msg.getAsBytes();
    }


    public String toString() {
        return msg.toString();
    }


    public Object getObject() {
        return msg;
    }
}
