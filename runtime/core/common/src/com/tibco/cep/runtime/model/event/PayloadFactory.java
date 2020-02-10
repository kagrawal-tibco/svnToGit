package com.tibco.cep.runtime.model.event;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmParticleTerm;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 5, 2006
 * Time: 4:41:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PayloadFactory {

    EventPayload createPayload(ExpandedName entityName, String payload) throws Exception;

    EventPayload createPayload(ExpandedName entityName, byte[] buf) throws Exception;

    EventPayload createPayload(ExpandedName entityName, String payload, boolean isJSONPayload) throws Exception;

    EventPayload createPayload(ExpandedName entityName, byte[] buf, boolean isJSONPayload) throws Exception;

    EventPayload createPayload(ExpandedName entityName, Object payload);

    EventPayload createPayload(ExpandedName entityName, XiNode node);

    SmParticleTerm getPayloadElement(ExpandedName entityName);
}
