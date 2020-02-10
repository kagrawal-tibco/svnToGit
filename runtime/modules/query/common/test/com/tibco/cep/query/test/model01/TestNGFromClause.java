package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.model.Aliased;
import com.tibco.cep.query.model.AliasedIdentifier;
import com.tibco.cep.query.model.FromClause;
import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.service.Query;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 31, 2007
 * Time: 4:33:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Test(groups = { "Model01" }, enabled = true )
public class TestNGFromClause extends TestNGBase {
    private static TestNGFromClause tngbase;

    public TestNGFromClause() {
        super();
    }


    public TestNGFromClause(String name) {
        super(name);
    }

    @BeforeClass
    public static void startEngine() throws Exception {
        System.out.println("Testing Resolution Suite ....");
        System.out.println("STARTING BE-ENGINE ....");
        tngbase = new TestNGFromClause();
        tngbase.setUpClass();
    }



    @AfterClass
    public static void stopEngine() throws Exception {
        System.out.println("STOPPING BE-ENGINE ....");
        tngbase.tearDownClass();
    }

    @Test
    public void test01() {
        final String queryText = "select "
                + "  customer.ppattern.pattern, "
                + "  c.ppattern@id, "
                + "  customer.orders "
                //+" , count(shopCard.*), \"/Concepts/Customer\".age, \"/Standard/Number/valueOfString\"(\"123\", 10)"
                + " from "
                + "  \"/Concepts/Customer\" customer, "
                + "  \"/Concepts/Customer\" c, "
                + "  \"/Scorecards/ShopCard\" shopCard "
                + ", (select a.street, \"/Scorecards/ShopCard\".open from \"/Concepts/Address\" a) as sub"
                // + ", (select a.street from \"/Concepts/Address\" a) as sub"
                + " where "
                + "  customer.age > 18 "
                + "  and customer.netWorth > 150 "
                + "  and shopCard.open = true "
                + "  and customer.name in (select extId, street from \"/Concepts/Address\" b)";

        try {
            final Query query = tngbase.getQuerySession().createQuery("query", queryText);
            Assert.assertNotNull(query);
            Assert.assertNotNull(query.getModel());
            Assert.assertTrue(query.getModel() instanceof NamedSelectContext);
            final NamedSelectContext root = (NamedSelectContext) query.getModel();
            final FromClause fromClause = root.getFromClause();
            Assert.assertNotNull(fromClause);
            Assert.assertTrue(fromClause.containsAlias("customer"));
            Assert.assertTrue(fromClause.containsAlias("c"));
            Assert.assertTrue(fromClause.containsAlias("shopCard"));
            Assert.assertTrue(fromClause.containsEntity("/Concepts/Customer"));
            Assert.assertTrue(fromClause.containsEntity("/Scorecards/ShopCard"));
            final String[] epaths = fromClause.getAllEntityPaths();
            Assert.assertEquals(epaths.length,3);
            final String[] aliases = fromClause.getAllEntityAliases(false);
            Assert.assertEquals(aliases.length,3);

            final Aliased  aliased  = fromClause.getAliasedByAlias("c");
            Assert.assertTrue(aliased instanceof AliasedIdentifier);
            final AliasedIdentifier ai = (AliasedIdentifier) aliased;
            final com.tibco.cep.query.model.Entity e = fromClause.getEntityByAlias("c");
            Assert.assertNotNull(e);
            Assert.assertEquals(ai.getIdentifiedContext(),e);
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
