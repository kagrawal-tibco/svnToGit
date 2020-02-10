package com.tibco.cep.runtime.service.tester.model;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_RUN_NAME;

import java.util.ArrayList;
import java.util.Collection;

import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 3, 2010
 * Time: 9:38:10 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ResultObject {

    private String testerRunName;

    private Collection<ReteObject> queuedObjects;

    public ResultObject() {
        queuedObjects = new ArrayList<ReteObject>();
    }

    public ResultObject(Collection<ReteObject> queuedObjects) {
        this(queuedObjects, "WorkingMemoryContents");
    }

    public ResultObject(Collection<ReteObject> queuedObjects, String testerRunName) {
        this.queuedObjects = queuedObjects;
        this.testerRunName = testerRunName;
    }

    public Collection<ReteObject> getQueuedObjects() {
        return queuedObjects;
    }

    public String getTesterRunName() {
        return testerRunName;
    }

    public void serialize(XiFactory factory, XiNode rootNode) throws Exception {
        //Create a <RunName>
        XiNode runNameElement = factory.createElement(EX_RUN_NAME);
        rootNode.appendChild(runNameElement);
        runNameElement.setStringValue(testerRunName);

        //Get all elements
        for (ReteObject reteObject : queuedObjects) {
            reteObject.serialize(factory, rootNode);
        }
    }
}
