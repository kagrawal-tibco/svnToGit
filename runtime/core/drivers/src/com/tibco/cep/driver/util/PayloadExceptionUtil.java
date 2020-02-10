package com.tibco.cep.driver.util;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.session.RuleSession;

import java.io.StringWriter;

/**
 * Created by aamaya on 6/5/2014.
 */
public class PayloadExceptionUtil {
    static public void assertPayloadExceptionAdvisoryEvent(Exception e, Object inputMessage, SimpleEvent event, RuleSession session) {
        String error = "Exception while deserializing message payload for message.\n";
        String evIdPfx = "\nAssociated Event id=";
        String evId = String.valueOf(event.getId());
        String extIdPfx = " extId=";
        String extId = event.getExtId();
        if(extId == null) extId = "";
        String inputMessageStr = inputMessage.toString();

        int len = error.length() + inputMessageStr.length() + evIdPfx.length() + evId.length() + extIdPfx.length() + extId.length() + 1;
        StringWriter msg = new StringWriter(len).append(error).append(inputMessageStr).append(evIdPfx).append(evId);
        if(extId.length() > 0) msg.append(extIdPfx).append(extId);
        msg.append(".");
        AdvisoryEventImpl.assertExceptionAdvisory(session, e, msg);
    }
}
