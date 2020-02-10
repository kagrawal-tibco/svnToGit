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
 * Created by IntelliJ IDEA. User: nprade Date: Oct 31, 2007 Time: 4:33:00 PM To
 * change this template use File | Settings | File Templates.
 */
@Test(groups = { "Model01" }, enabled = true)
public class TestValidations extends TestNGBase {
    private static TestValidations tngbase;

    public TestValidations() {
        super();
    }

    public TestValidations(String name) {
        super(name);
    }

    @BeforeClass
    public static void startEngine() throws Exception {
        System.out.println("Testing Resolution Suite ....");
        System.out.println("STARTING BE-ENGINE ....");
        tngbase = new TestValidations();
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
                + " {limit: first 30 offset 5} "
                + " \"/Standard/Math/sqrt\" (4), "
                + " min(customer.ppattern.pattern), "
                + " c.ppattern@id, "
                + " c@id, "
                + " c.name, "
                + " c, "
                + " shopCard, "
                + " c.name, "
                + " avg(c.ppattern@id), "                
                + " max(100 * customer.age), "
                + " customer.age "
            
                + " from "
                + "  \"/Concepts/Customer\" "
                // todo Resolution errors.
                // + "{policy: maintain by customer.age, customer.name last 3 seconds purge first count(*) - 10 when pending_count(*) > 5} "
                + " customer, "
                + "  \"/Concepts/Customer\" c, "
                + "  \"/Scorecards/ShopCard\" shopCard "               
                //todo Sub-selects.
                // + ", (select a.street, \"/Scorecards/ShopCard\".open from \"/Concepts/Address\" a) as sub"                
                // + ", (select a.street from \"/Concepts/Address\" a) as sub"
                
                + " where "
                + " 2007-10-14T21:35:44.563-0800 >= 2007-10-14T21:35:44.563-0800"
                + " and 10/12.0 > 3 and (10 + 20 + 30) = (customer.age * 10)"
                + " and 10 + 20 > 10 = true"
                //todo Null pointer error.
                //+ " and customer.age between 20 and 76"
                + " and customer.name > \"TestName\" "
                + " and 30 in (10.0, 20, 30)"
                + " and customer.orders[customer.age].orderId > 3"
                // todo 30 gets registered as a Byte. Otherwise all ok.
                + " and -30 > 300.66" 
                + " and not(10 > 5)"
                + " and customer.age > 18 and customer.netWorth > 150"
                + " and shopCard.open = (true and false)"
                + " and customer.age <= customer.netWorth"
                //todo Sub-selects
                // + " and customer.name in (select extId, street from \"/Concepts/Address\" b) "
                
                + " group by {stateful_capture: all; emit: new} customer.age, shopCard, customer.age/30, c"
                
                + " having customer.age > 30 and c.name = \"abcd\""
                //todo Function's return type is not working.
                //+ " or max(c.age) = 10"
                //todo Return type of count() is not correct. 
                //+ " or count(customer.name) = \"Hello\" "
                
                + " order by customer.age desc {limit: first 1 offset 8}"
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
            Assert.assertTrue(fromClause.containsAlias("c"));
            Assert.assertTrue(fromClause.containsAlias("shopCard"));
            Assert.assertTrue(fromClause.containsEntity("/Concepts/Customer"));
            Assert.assertTrue(fromClause.containsEntity("/Scorecards/ShopCard"));
            final String[] epaths = fromClause.getAllEntityPaths();
            Assert.assertEquals(epaths.length, 3);
            final String[] aliases = fromClause.getAllEntityAliases(false);
            Assert.assertEquals(aliases.length, 3);

            final Aliased aliased = fromClause.getAliasedByAlias("c");
            Assert.assertTrue(aliased instanceof AliasedIdentifier);
            final AliasedIdentifier ai = (AliasedIdentifier) aliased;
            final com.tibco.cep.query.model.Entity e = fromClause.getEntityByAlias("c");
            Assert.assertNotNull(e);
            Assert.assertEquals(ai.getIdentifiedContext(), e);
            query.close();
        }
        catch (Exception e) {
            Assert.fail(e.getMessage(), e);
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
