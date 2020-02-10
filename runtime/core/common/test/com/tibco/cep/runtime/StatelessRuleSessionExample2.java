package com.tibco.cep.runtime;

import java.util.Properties;
import java.util.List;

import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.functions.VariableListImpl;
import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.TraxSupport;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.XiNodeUtilities;
import org.w3c.dom.Node;

public class StatelessRuleSessionExample2
{
    /**
     * @param args
     */

    private RuleServiceProvider provider;

    public StatelessRuleSessionExample2(String repoUrl, String traFile) throws Exception
    {
        Properties env = new Properties();
        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);
        //env.put("be.property.file.1", "C:\\Perforce\\DEV\\be\\2.0\\p.cfg");
        provider = RuleServiceProviderManager.getInstance().newProvider("FraudDetectionRepo", env);
    }

    private void init() throws Exception
    {
        provider.configure(RuleServiceProvider.MODE_API);
    }

    /**
     * A Typical Rule invocation cycle will include
     * a.> Create an entity of the Concept type modeled in Designer
     * b.> set properties values (Primitive, Refereences, Contained Concept, and Array
     *     @see PropertyAtom
     *     @see PropertyArrayAtom
     *  c.> Get the Rulesession by name from the Provider
     *      The Rulesession name is the BE archive module name within the Enterprise Archive
     *  d.> Now assert the object
     *  e.> Call reset to clear the Working Memory
     *  f.> Call ruleSession.getObjects()
     * @throws Exception
     */
    private void testSingleInvoke() throws Exception
    {
        Concept account = (Concept) provider.getTypeManager().createEntity("/Concepts/Account");

        Property props[] = account.getProperties();

        System.out.println("======================Property Listing=======================");
        for (int i=0; i < props.length; i++)
        {
            Property prop = props[i];
            System.out.println("PropertyName : " + prop.getName());
        }

        account.getPropertyAtom("Id").setValue("2");
        //		account.getPropertyAtom("Balance").setValue("2");
        //		account.getPropertyAtom("AccountType").setValue("2");
        account.getPropertyAtom("Debits").setValue("4");
        account.getPropertyAtom("Status").setValue("Normal");
        //		account.getPropertyAtom("AvgMonthlyBalance").setValue("2");

        System.out.println("======================Before Rule Execution=======================");
        for (int i=0; i < props.length; i++)
        {
            Property prop = props[i];
            System.out.println("PropertyName : " + prop.getName() + "; PropertyValue : "
                    + account.getPropertyAtom(prop.getName()).getValue());
        }

        //"Stateless" refers to the BE Archive name inside the deployed Project
        RuleSession ruleSession = provider.getRuleRuntime().getRuleSession("ServerMonitor Archive");

        //Assert the object to the working memory and tell it to execute the rules immediately
        ruleSession.assertObject(account, true);

        //Reset the RuleSession, remove all objects from the working Memory.
        ruleSession.reset();

        System.out.println("======================After Rule Execution=======================");
        for (int i=0; i < props.length; i++)
        {
            Property prop = props[i];
            System.out.println("PropertyName : " + prop.getName() + "; PropertyValue : "
                    + account.getPropertyAtom(prop.getName()).getValue());
        }
    }

    private List testTransformInvoke() throws Exception
    {
//        String xsltString =
//            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
//            "<xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns=\"www.tibco.com/be/ontology/Concepts/Account\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsd\">" +
////            "<xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns=\"www.tibco.com/be/ontology/Concepts/Account\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsd\">" +
//            "    <xsl:output method=\"xml\"/>" +
//            "    <xsl:param name=\"SW_FIELDS\"/>" +
//            "    <xsl:template match=\"/\">" +
//            "        <Concepts>" +
//            "        <ns:Account>" +
//            "            <Id>" +
//            "                <xsl:value-of select=\"$SW_FIELDS/user_fields/PARAM1\"/>" +
//            "            </Id>" +
//            "            <Debits>" +
//            "                <xsl:value-of select=\"$SW_FIELDS/user_fields/PARAM2\"/>" +
//            "            </Debits>" +
//            "            <Status>" +
//            "                <xsl:value-of select=\"$SW_FIELDS/user_fields/PARAM3\"/>" +
//            "            </Status>" +
//            "        </ns:Account>" +
//            "        </Concepts>" +
//            "    </xsl:template>" +
//            "</xsl:stylesheet>";

        String xsltString =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns1=\"www.tibco.com/be/ontology/Concepts/Account\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsd\">" +
                        "    <xsl:output method=\"xml\"/>" +
                        "    <xsl:template match=\"/\">" +
                        "        <Concepts>" +
                        "            <ns1:Account>" +
                        "                <CheckingAccount>" +
                        "                    <Status>" +
                        "                        <xsl:value-of select=\"&quot;Current&quot;\"/>" +
                        "                    </Status>" +
                        "                </CheckingAccount>" +
                        "                <CreditCardAccount>" +
                        "                    <Type>" +
                        "                        <xsl:value-of select=\"&quot;Gold&quot;\"/>" +
                        "                    </Type>" +
                        "                </CreditCardAccount>" +
                        "            </ns1:Account>" +
                        "        </Concepts>" +
                        "    </xsl:template>" +
                        "</xsl:stylesheet>";

        //Create an XiNode of the Case data
        XiNode sw_field = makeXiNode();

        System.out.print(XiSerializer.serialize(sw_field)); //Prints it to the stdout

        //Create a variableList - essentially a name-value pair -where name is variable name, and value is XiNode representation
        //of the Object
        VariableList varlist = new VariableListImpl();
        varlist.addVariable("SW_FIELDS", sw_field);

        //Now to the runtime
        //"Stateless" refers to the BE Archive name inside the deployed Project
        RuleSession ruleSession = provider.getRuleRuntime().getRuleSession("ServerMonitor Archive");


        //Call the standard Helper function, transform2Instance -
        // the firstone is the ruleSession,
        // second is the key. It could be iProcess ActivityName. In this example I used the xsltString as the key itself
        // xsltString - The actual template
        // The arguments to the template
        // returns an Instance of the Concept.
        Concept account = com.tibco.be.functions.object.ObjectHelper.transform2Instance(ruleSession, xsltString, xsltString, varlist);

        Property props[] = account.getProperties();

        System.out.println("======================Before Rule Execution=======================");
        for (int i=0; i < props.length; i++)
        {
            Property prop = props[i];
            System.out.println("PropertyName : " + prop.getName() + "; PropertyValue : "
                    + account.getPropertyAtom(prop.getName()).getValue());
        }

        //Now assert the Object.
        ruleSession.assertObject(account, true);

        return ruleSession.getObjects();
    }

    private XiNode makeXiNode()
    {
        XiFactory factory = XiFactoryFactory.newInstance();

        XiNode doc = factory.createDocument();

        //Append a Element SW_FIELDS. The ExpandedName is FQName with Namespace, QName. It would
        //be good have a namespace. The same namespace should also be used in the designtime when
        //mapping
        //
        String iProcessNS = ""; //specify the namespace that you used in the mapper
        String varName = "SW_FIELDS"; //specify the variable name that you see in the mapper. At the root of the maper on LHS,
        //you see variable starting with $ sign. That is the name without the $ sign.

        XiNode sw_field = doc.appendElement(ExpandedName.makeName(iProcessNS, varName));
        XiNode user_field = sw_field.appendElement(ExpandedName.makeName(iProcessNS, "user_fields"));

        user_field.appendElement(ExpandedName.makeName(iProcessNS, "PARAM1"), "2"); //each field with in the variable
        user_field.appendElement(ExpandedName.makeName(iProcessNS, "PARAM2"), "4.0");
        user_field.appendElement(ExpandedName.makeName(iProcessNS, "PARAM3"), "Normal");

        //sw_field.appendElement(ExpandedName.makeName(null, "date_field"), XsDate.compile() ));
        //check with XMLSDK team or I have dig up our code base to check
        return user_field;
    }

    private XiNode makeXiNode2()
       {
           XiFactory factory = XiFactoryFactory.newInstance();

           XiNode doc = factory.createDocument();

           //Append a Element SW_FIELDS. The ExpandedName is FQName with Namespace, QName. It would
           //be good have a namespace. The same namespace should also be used in the designtime when
           //mapping
           //
           String iProcessNS = ""; //specify the namespace that you used in the mapper
           String varName = "SW_FIELDS"; //specify the variable name that you see in the mapper. At the root of the maper on LHS,
           //you see variable starting with $ sign. That is the name without the $ sign.

           XiNode sw_field = doc.appendElement(ExpandedName.makeName(iProcessNS, varName));
           XiNode user_field = sw_field.appendElement(ExpandedName.makeName(iProcessNS, "user_fields"));

           user_field.appendElement(ExpandedName.makeName(iProcessNS, "PARAM1"), "2"); //each field with in the variable
           user_field.appendElement(ExpandedName.makeName(iProcessNS, "PARAM2"), "4.0");
           user_field.appendElement(ExpandedName.makeName(iProcessNS, "PARAM3"), "Normal");

           //sw_field.appendElement(ExpandedName.makeName(null, "date_field"), XsDate.compile() ));
           //check with XMLSDK team or I have dig up our code base to check
           return sw_field;
       }


    public void testReverseTransform(List l) throws Exception {

                String xsltString =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns=\"www.tibco.com/be/ontology/Concepts/Account\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsd\">" +
            //"<xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns=\"www.tibco.com/be/ontology/Concepts/Account\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsd\">" +
            "    <xsl:output method=\"xml\"/>" +
            "    <xsl:param name=\"SW_FIELDS\"/>" +
            "    <xsl:template match=\"/\">" +
            "        <Concepts>" +
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
            "        </Concepts>" +
            "    </xsl:template>" +
            "</xsl:stylesheet>";

        //Create an XiNode of the Case data

        XiNode nodes[];
        if (l == null) {
            //Sample test
            XiNode sw_field = makeXiNode2();
            System.out.print(XiSerializer.serialize(sw_field)); //Prints it to the stdout
        nodes = new XiNode[1];
        nodes[0] = sw_field;
        }
        else {
            nodes = XiNodeBuilder.buildXiNodes(l);
        }


        XiNode result =    TraxSupport.doTransform(xsltString, nodes);

        System.out.print(XiSerializer.serialize(result)); //Prints it to the stdout

        Node n = XiNodeUtilities.toDomNode(result); //Paul - you might want to talk to XMLSDK team to find out how to convert
        System.out.println("Dom Node" + n);


    }

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub

        try
        {
            String repourl = args[0];
            String traFile = args[1];

            StatelessRuleSessionExample2 example = new StatelessRuleSessionExample2(repourl, traFile);
            example.init();
            //example.testSingleInvoke();
            //example.testTransformInvoke();

            //Paul, I have passed null, since I did not the reverse mapping, but usually the way you would like to have is
            //example.testReverseTransform(example.testTransformInvoke());
            example.testReverseTransform(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
