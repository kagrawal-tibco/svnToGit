package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.model.FromClause;
import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.service.Query;
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
public class TestResolution extends TestNGBase{
    private static TestResolution tngbase;

    public TestResolution() {
        super();
    }


    public TestResolution(String name) {
        super(name);
    }

    @BeforeClass
    public static void startEngine() throws Exception {
        System.out.println("Testing Resolution Suite ....");
        System.out.println("STARTING BE-ENGINE ....");
        tngbase = new TestResolution();
        tngbase.setUpClass();
    }



    @AfterClass
    public static void stopEngine() throws Exception {
        System.out.println("STOPPING BE-ENGINE ....");
        tngbase.tearDownClass();
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
            final Query query = tngbase.getQuerySession().createQuery("query", queryText);
            Assert.assertNotNull(query);
            Assert.assertNotNull(query.getModel());
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

//            final Aliased  aliased  = fromClause.getAliasedByAlias("c");
//            Assert.assertTrue(aliased instanceof AliasedIdentifier);
//            final AliasedIdentifier ai = (AliasedIdentifier) aliased;
//            final com.tibco.cep.query.model.Entity e = fromClause.getEntityByAlias("c");
//            Assert.assertNotNull(e);
//            Assert.assertEquals(ai.getIdentifiedContext(),e);
            query.close();
        } catch (Exception e) {
            Assert.fail(e.getMessage(),e);
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
}
