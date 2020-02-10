package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.service.impl.QueryServiceProviderImpl;
import com.tibco.cep.query.utils.BaseObjectTreeAdaptor;
import com.tibco.cep.query.utils.DotTreeWalker;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import org.antlr.stringtemplate.StringTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jul 9, 2007
 * Time: 1:10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestModel {

    RuleServiceProvider ruleServiceProvider;
    RuleSession ruleSession;
    TypeManager typeManager;
    String testData;
    List allCustomers;

    public static StringTemplate _treeST =
        new StringTemplate(
            "digraph {\n" +
            "  ordering=out;\n" +
            "  ranksep=.4;\n" +
            "  node [shape=plaintext, fixedsize=false, fontsize=10, fontname=\"Helvetica\",\n" +
            "        width=.25, height=.25];\n" +
            "  edge [arrowsize=.5]\n" +
            "  $nodes$\n" +
            "  $edges$\n" +
            "}\n");

    TestModel(String mannersFile, String repoUrl, String traFile) throws Exception {
        Properties env = new Properties();
        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);

        ruleServiceProvider = RuleServiceProviderManager.getInstance().newProvider("TestQuery", env);
        ruleServiceProvider.configure(RuleServiceProvider.MODE_API);

        ruleSession = ruleServiceProvider.getRuleRuntime().getRuleSession("BusinessEvents Archive");
        typeManager = ruleServiceProvider.getTypeManager();

        testData = mannersFile;
        allCustomers = new LinkedList();
    }

    private void readData() throws Exception {
        //read data and put in the rule session
        DataReader reader = new DataReader(testData);
        Collection guests = reader.readAddressData();

        Iterator ite = guests.iterator();
        while (ite.hasNext()) {
            DataReader.Customer cust = (DataReader.Customer) ite.next();
            Concept BECustomer = (Concept) typeManager.createEntity("/Concepts/Customer");
            BECustomer.getPropertyAtom("name").setValue(cust.getName());
            BECustomer.getPropertyAtom("gender").setValue(cust.getSex());
            BECustomer.getPropertyAtom("age").setValue(cust.getAge());
            BECustomer.getPropertyAtom("business").setValue(cust.getBusiness());
            BECustomer.getPropertyAtom("netWorth").setValue(cust.getNetworth());

            allCustomers.add(BECustomer);
        }

        ite = allCustomers.iterator();
        while(ite.hasNext()) {
            Concept BECustomer = (Concept) ite.next();
            ruleSession.assertObject(BECustomer, true);
        }
    }

    private void runTest() throws Exception {
        QueryServiceProviderImpl qsp = new QueryServiceProviderImpl(ruleServiceProvider);

        this.createDotFile(qsp.getProjectContext().getFunctionRegistry(), "C:/tmp/functionregistry.dot");
        this.createDotFile(qsp.getProjectContext().getEntityRegistry(), "C:/tmp/entityregistry.dot");

        QuerySession qs = qsp.getQuerySession(ruleSession.getName());
        String selectStr = "select "
                //+" *"
                + " customer.ppattern.pattern"
                + " , c.ppattern@id"
                //+ " , customer.orders@length"
                //+"count(shopCard.*), \"/Concepts/Customer\".age, \"/Standard/Number/valueOfString\"(\"123\", 10)"
                + " from \"/Concepts/Customer\" customer, \"/Concepts/Customer\" c, \"/Scorecards/ShopCard\" shopCard "
                //   + ", (select a.street, \"/Scorecards/ShopCard\".open from \"/Concepts/Address\" a) as sub"
                //   + ", (select a.street from \"/Concepts/Address\" a) as sub"
                + " where customer.age > 18 and customer.netWorth > 150 and shopCard.open = true"
                + " and customer.name in (select extId,street from \"/Concepts/Address\" b)"
                + ";";
        Query query = qs.createQuery("query", selectStr);

        this.createDotFile(query.getModel(), "C:/tmp/beoql.dot");

        System.out.println("Query ID:"+query.getName() + " hashCode:"+query.hashCode());

        //QueryStatement qstmt = query.compile();
        //ResultSet set = qstmt.executeOnce();


//        System.out.println("set size " +  set.size());
//        for (Iterator itr = set.iterator(); itr.hasNext();) {
//            Tuple t = (Tuple) itr.next();
//            Concept customer = (Concept) t.get(1);
//            System.out.println(customer.getPropertyAtom("name").getValue());
//        }
    }

    private void createDotFile(ModelContext mc, String fileName) throws IOException {
        //DOTTreeGenerator gen = new DOTTreeGenerator();
        DotTreeWalker gen = new DotTreeWalker();
        File dotf = new File(fileName);
        FileOutputStream fos = new FileOutputStream(dotf);
        StringTemplate st = gen.toDOT(mc, new BaseObjectTreeAdaptor());
        fos.write(st.toString().getBytes());
        fos.flush();
        fos.close();
    }


    private void done() {
        ruleSession.getRuleServiceProvider().shutdown();
    }

    public static void main(String[] args) {
        try {
            String testFile;
            String traFile  = args[0];
            String repourl  = args[1];
            if(args.length > 2) {
                testFile = args[2];
            } else {
                testFile = null;
            }


            System.out.println(">>> Running test for " + testFile + " <<<");
            TestModel test = new TestModel(testFile, repourl, traFile);
            test.readData();

            //initialized and read the data
            System.out.println(">>> First run - ");
            test.runTest();
            //test.readData();
            //test.resetTest();
            System.out.println(">>> Done ");

            test.done();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
