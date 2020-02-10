package com.tibco.cep.runtime;




import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.functions.VariableListImpl;

import java.util.Properties;


public class StatelessRuleSessionExample {

    /**
     * @param args
     */

    RuleServiceProvider provider;

    StatelessRuleSessionExample(String repoUrl, String traFile) throws Exception {

        Properties env = new Properties();
        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);
        provider = RuleServiceProviderManager.getInstance().newProvider("StatelessRuleSessionExample", env);

    }

    private void init() throws Exception {
        provider.configure(RuleServiceProvider.MODE_API);
    }

    /**
     * A Typical Rule invocation cycle will include
     * a.> Create an entity of the Concept type modeled in Designer
     * b.> set properties values (Primitive, Refereences, Contained Concept, and Array
     *     @see PropertyAtom
     *     @see PropertyAtomArray
     *  c.> Get the Rulesession by name from the Provider
     *      The Rulesession name is the BE archive module name within the Enterprise Archive
     *  d.> Now assert the object
     *  e.> Call reset to clear the Working Memory
     *  f.> Call ruleSession.getObjects()
     * @throws Exception
     */
    private void testSingleInvoke() throws Exception {

        Concept animal = (Concept)provider.getTypeManager().createEntity("/TestModel/Concepts/Animal");

//      Property props[] = animal.getProperties();
//        for (Property prop : props) {
//            System.out.println("PropertyName : " + prop.getName());
//        }
        animal.getPropertyAtom("family").setValue("dinosaur");

        //"Stateless" refers to the BE Archive name inside the deployed Project
        RuleSession ruleSession = provider.getRuleRuntime().getRuleSession("Stateless");

        //Assert the object to the working memory and tell it to execute the rules immediately
        ruleSession.assertObject(animal, true);

        //Reset the RuleSession, remove all objects from the working Memory.
        ruleSession.reset();

        //Now check the status of the animal upon execution
        System.out.println("The Status of animal is " + animal.getPropertyAtom("status").getValue());

        provider.getChannelManager().shutdown();


    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        try {
            String repourl = args[0];
            String traFile = args[1];

            StatelessRuleSessionExample example = new StatelessRuleSessionExample(repourl, traFile);
            example.init();
            example.testSingleInvoke();
            //example.testTransformInvoke();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void testTransformInvoke() throws Exception{

        String xsltString =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns=\"www.tibco.com/be/ontology/Concepts/Account\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsd\">" +
            "    <xsl:output method=\"xml\"/>" +
            "    <xsl:param name=\"SW_FIELDS\"/>" +
            "    <xsl:template match=\"/\">" +
            "        <ns:Account>" +
            "            <Id>" +
            "                <xsl:value-of select=\"$SW_FIELDS/user_fields/PARAM1\"/>" +
            "            </Id>" +
            "            <Debits>" +
            "                <xsl:value-of select=\"$SW_FIELDS/user_fields/PARAM2\"/>" +
            "            </Debits>" +
            "            <Status>" +
            "                <xsl:value-of select=\"$SW_FIELDS/user_fields/PARAM3\"/>" +
            "            </Status>" +
            "        </ns:Account>" +
            "    </xsl:template>" +
            "</xsl:stylesheet>";

        //Create a variableList - essentially a name-value pair -where name is variable name, and value is XiNode representation
        //of the Object
        //Create an XiNode of the Case data
        XiNode sw_field = makeXiNode();
        System.out.print(XiSerializer.serialize(sw_field)); //Prints it to the stdout

        //Create a variableList - essentially a name-value pair -where name is variable name, and value is XiNode representation
        //of the Object
        VariableList varlist = new VariableListImpl();
        varlist.addVariable("SW_FIELDS", sw_field);

        //Now to the runtime
        //"Stateless" refers to the BE Archive name inside the deployed Project
        RuleSession ruleSession = provider.getRuleRuntime().getRuleSession("FraudDetection Archive");

        //Call the standard Helper function, transform2Instance -
        // the firstone is the ruleSession,
        // second is the key. It could be iProcess ActivityName. In this example I used the xsltString as the key itself
        // xsltString - The actual template
        // The arguments to the template
        // returns an Instance of the Concept.
        Concept obj = com.tibco.be.functions.object.ObjectHelper.transform2Instance(ruleSession, xsltString, xsltString, varlist);

        //Now assert the Object.
        ruleSession.assertObject(obj,true);
        


    }

    private XiNode makeXiNode()
        {
            XiFactory factory = XiFactoryFactory.newInstance();

            XiNode doc = factory.createDocument();

            //Append a Element SW_FIELDS. The ExpandedName is FQName with Namespace, QName. It would
            //be good have a namespace. The same namespace should also be used in the designtime when
            //mapping
            //
            String iProcessNS = null; //specify the namespace that you used in the mapper
            String varName = "SW_FIELDS"; //specify the variable name that you see in the mapper. At the root of the maper on LHS,
            //you see variable starting with $ sign. That is the name without the $ sign.

            XiNode sw_field = doc.appendElement(ExpandedName.makeName(iProcessNS, varName));
            XiNode user_field = sw_field.appendElement(ExpandedName.makeName(iProcessNS, "user_fields"));

            user_field.appendElement(ExpandedName.makeName(null, "PARAM1"), "2"); //each field with in the variable
            user_field.appendElement(ExpandedName.makeName(null, "PARAM2"), "4");
            user_field.appendElement(ExpandedName.makeName(null, "PARAM3"), "Normal");

            //sw_field.appendElement(ExpandedName.makeName(null, "date_field"), XsDate.compile() ));
            //check with XMLSDK team or I have dig up our code base to check
            return user_field;
        }


}

