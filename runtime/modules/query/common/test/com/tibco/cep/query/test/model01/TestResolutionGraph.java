package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.model.FromClause;
import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.utils.BaseObjectTreeAdaptor;
import com.tibco.cep.query.utils.SourceTreeAdaptor;
import com.tibco.cep.query.utils.TSGraphWalker;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Dec 7, 2007
 * Time: 3:45:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestResolutionGraph extends TestNGBase{
    private static TestResolutionGraph tngbase;

    public TestResolutionGraph() {
        super();
    }


    public TestResolutionGraph(String name) {
        super(name);
    }

    @BeforeClass
    public static void startEngine() throws Exception {
        System.out.println("Testing Resolution Suite ....");
        System.out.println("STARTING BE-ENGINE ....");
        TestResolutionGraph.tngbase = new TestResolutionGraph();
        TestResolutionGraph.tngbase.setUpClass();
    }



    @AfterClass
    public static void stopEngine() throws Exception {
        System.out.println("STOPPING BE-ENGINE ....");
        TestResolutionGraph.tngbase.tearDownClass();
    }

    @Test
    public void testResolution01() {
        final String queryText = "select \n"
                + "  customer.ppattern@id, \n"
                + "  customer.addresses[0]@id, \n"
                + "  customer.addresses@length, \n"
                + "  customer.addresses[0].street, \n"
                + "  customer.addresses[0].city, \n"
                + "  customer.addresses[0].state, \n"
                + "  customer.addresses[0].country \n"
                + "  from \n"
                + "  \"/Concepts/Customer\" customer \n"
                + "  ,\"/Scorecards/ShopCard\" shopCard \n"
                + "  where \n"
                + "  customer.age > 18 \n"
                + "  and customer.netWorth > 150 \n"
//                + "  and customer.ppattern <> null \n"
                + "  and customer.addresses@length > 0 \n"
                + "  and customer.addresses[0].city = \"Palo Alto\" \n"
                + "  and shopCard.open = true; \n"
                ;

        try {
            final Query query = TestResolutionGraph.tngbase.getQuerySession().createQuery("query", queryText);
            Assert.assertNotNull(query);
            Assert.assertNotNull(query.getModel());

            TSGraphWalker gw = new TSGraphWalker();
            TSEGraphManager gm = gw.toGraph(query.getModel(),new BaseObjectTreeAdaptor());
            GraphViewer gv = new GraphViewer();
            gv.setGraphManager(gm);
            gv.init();
            Assert.assertTrue(query.getModel() instanceof NamedSelectContext);
            final NamedSelectContext root = (NamedSelectContext) query.getModel();
            final FromClause fromClause = root.getFromClause();
            Assert.assertNotNull(fromClause);
            Assert.assertTrue(fromClause.containsAlias("customer"));
            //Assert.assertTrue(fromClause.containsAlias("c"));
            Assert.assertTrue(fromClause.containsAlias("shopCard"));
            Assert.assertTrue(fromClause.containsEntity("/Concepts/Customer"));
            Assert.assertTrue(fromClause.containsEntity("/Scorecards/ShopCard"));
            final String[] epaths = fromClause.getAllEntityPaths();
            Assert.assertEquals(epaths.length,2);
            final String[] aliases = fromClause.getAllEntityAliases(false);
            Assert.assertEquals(aliases.length,2);
            ((QueryContext)root).getSourceString();

            query.close();
        } catch (Exception e) {
            Assert.fail(e.getMessage(),e);
        }
    }


    public void testGraph() {
        final String queryText = "select \n"
                + "  customer.ppattern@id, \n"
                + "  customer.addresses[0]@id, \n"
                + "  customer.addresses@length, \n"
                + "  customer.age, \n"
                + "  customer.name \n"
                + "  from \n"
                + "  \"/Concepts/Customer\" customer \n"
                + "  ,\"/Scorecards/ShopCard\" shopCard \n"
                + "  where \n"
                + "  (customer.age > 18 \n"
                + "  and customer.netWorth > 150 \n"
                + "  and customer.addresses <> null \n"
                + "  and shopCard.open = true); \n"
                ;

        try {
            setUpClass();
            final Query query = getQuerySession().createQuery("query", queryText);
            Assert.assertNotNull(query);
            Assert.assertNotNull(query.getModel());

            TSGraphWalker gw = new TSGraphWalker();
            TSEGraphManager gm = gw.toGraph(query.getModel(),new SourceTreeAdaptor());
            GraphViewer gv = new GraphViewer();
            gv.setGraphManager(gm);
            gv.init();
            Assert.assertTrue(query.getModel() instanceof NamedSelectContext);
            final NamedSelectContext root = (NamedSelectContext) query.getModel();
            final FromClause fromClause = root.getFromClause();
            Assert.assertNotNull(fromClause);
            Assert.assertTrue(fromClause.containsAlias("customer"));
            //Assert.assertTrue(fromClause.containsAlias("c"));
            Assert.assertTrue(fromClause.containsAlias("shopCard"));
            Assert.assertTrue(fromClause.containsEntity("/Concepts/Customer"));
            Assert.assertTrue(fromClause.containsEntity("/Scorecards/ShopCard"));
            final String[] epaths = fromClause.getAllEntityPaths();
            Assert.assertEquals(epaths.length,2);
            final String[] aliases = fromClause.getAllEntityAliases(false);
            Assert.assertEquals(aliases.length,2);
            ((QueryContext)root).getSourceString();
            WhereClause wc = root.getWhereClause();
            ((QueryContext)wc).getSourceString();
            ((QueryContext)wc.getChildren()[0]).getSourceString();
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getTestDirectoryName() {
        return "Model01";
    }


    protected String getRuleServiceProviderName() {
        return "TestQuery";
    }


    protected String getRuleSessionName() {
        return "BusinessEvents Archive";
    }

    public static void main(String[] argv) {
        TestResolutionGraph trg = new TestResolutionGraph();        
        trg.testGraph();
    }
}
