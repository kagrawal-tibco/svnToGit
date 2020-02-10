package com.tibco.cep.runtime.service.tester.core;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_RESULTS;

import java.io.StringWriter;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.runtime.service.tester.model.ResultObject;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 3, 2010
 * Time: 10:21:05 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ResultObjectSerializer {

    public String serialize(ResultObject resultObject) throws Exception {
        StringWriter stringWriter = new StringWriter();

        XiFactory factory = XiSupport.getXiFactory();
        XiNode root = factory.createDocument();

        XiNode rootResultsNode = factory.createElement(EX_RESULTS);
        root.appendChild(rootResultsNode);

        resultObject.serialize(factory, rootResultsNode);

        //Serialize the root
        XiSerializer.serialize(root, stringWriter);
        return stringWriter.toString();
    }
}
