package com.tibco.be.functions.event;

import java.util.Iterator;

import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.navigation.NodeKindNodeTest;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 8, 2004
 * Time: 3:44:39 PM
 * To change this template use Options | File Templates.
 */
public class SAX3EventInstance  {


    protected static final XmlNodeTest NODE_TEST_ELEMENT  = NodeKindNodeTest.getInstance(XmlNodeKind.ELEMENT);
    static ExpandedName EXT_ID_NM = ExpandedName.makeName("extId");
    SimpleEvent evt;
    RuleSession session;

    SAX3EventInstance(RuleSession session, SimpleEvent evt) {
        this.evt = evt;
        this.session = session;
    }


    private void setPayload(XiNode n1) {
        RuleServiceProvider provider = session.getRuleServiceProvider();
        EventPayload payload = provider.getTypeManager().getPayloadFactory().createPayload(evt.getExpandedName(), n1.getFirstChild());
        evt.setPayload(payload);
    }



    public void deserialize(XiNode node) throws Exception {

        XiNode extNode = node.getAttribute(EXT_ID_NM);
        if (extNode != null) evt.setExtId(extNode.getStringValue());

        Iterator itr = node.getChildren(NODE_TEST_ELEMENT);
        for (int i=0; itr.hasNext(); i++)
        {
            XiNode n = (XiNode) itr.next();
            String name = n.getName().getLocalName();
            XmlTypedValue value = n.getTypedValue();
            if ("payload".equalsIgnoreCase(name)) {
                setPayload(n);
            }
            else {
                evt.setProperty(name, value);
            }


        }
    }
}
