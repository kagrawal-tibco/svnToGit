package com.tibco.cep.runtime.service.tester.core;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_NAME;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_NAMESPACE;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_RULE;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.TIBCO_BE_BASE_NS;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 4, 2010
 * Time: 6:48:42 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class CustomRuleSerializer {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(CustomRuleSerializer.class);

    private Rule rule;

    protected XiFactory factory = XiSupport.getXiFactory();

    public CustomRuleSerializer(Rule rule) {
        this.rule = rule;
    }

    protected void serializeNS(XiNode node, String name, String namespace) {
        node.setAttributeStringValue(EX_NAMESPACE, namespace);
        node.setAttributeStringValue(EX_NAME, name);
    }

    public void serialize(XiNode documentNode) throws Exception {
        //Create a Rule element
        XiNode ruleElement = factory.createElement(EX_RULE);
        //Serialize base attrs
        String s = "be.gen.";
        //Set name and namespace
        String s1 = rule.toString().replace(s, "");
        String s2 = s1.substring(0, s1.indexOf("@"));
        String name = s2.substring(s2.lastIndexOf(".") + 1);
        LOGGER.log(Level.DEBUG, "Rule %s", name);
        String namespace = TIBCO_BE_BASE_NS + "/" + s2.replace(".", "/"); 
        LOGGER.log(Level.DEBUG, "Rule Namespace %s", namespace);
        serializeNS(ruleElement, name, namespace);
        documentNode.appendChild(ruleElement);
    }
}
