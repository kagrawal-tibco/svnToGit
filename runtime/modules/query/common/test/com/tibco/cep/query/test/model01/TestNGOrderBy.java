package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.model.*;
import com.tibco.cep.query.service.Query;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 1, 2007
 * Time: 4:33:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Test(groups = { "Model01" }, enabled = true )
public class TestNGOrderBy extends TestNGBase {
    private static TestNGOrderBy tngbase;

    public TestNGOrderBy() {
        super();
    }


    public TestNGOrderBy(String name) {
        super(name);
    }

    @BeforeClass
    public static void startEngine() throws Exception {
        System.out.println("Testing Resolution Suite ....");
        System.out.println("STARTING BE-ENGINE ....");
        tngbase = new TestNGOrderBy();
        tngbase.setUpClass();
    }



    @AfterClass
    public static void stopEngine() throws Exception {
        System.out.println("STOPPING BE-ENGINE ....");
        tngbase.tearDownClass();
    }

    @Test
    public void test01() {
        final String queryText = "select  name  from  \"/Concepts/Customer\" order by age;";

        try {
            final Query query = tngbase.getQuerySession().createQuery("query", queryText);
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void test02() {
        final String queryText = "select  gender as g  from \"/Concepts/Customer\" group by g order by age;";

        try {
            final Query query = tngbase.getQuerySession().createQuery("query", queryText);

            final NamedSelectContext root = (NamedSelectContext) query.getModel();
            final OrderClause orderBy = root.getOrderClause();

            final Iterator it = orderBy.getChildrenIterator();
            assert(it.hasNext());

            final SortCriterion sortCriterion = (SortCriterion) it.next();
            assert(!it.hasNext());
            assert(sortCriterion.getDirection() == SortCriterion.Direction.ASC);
            assert(sortCriterion.getLimitFirst() == 1);
            assert(sortCriterion.getLimitOffset() == Integer.MAX_VALUE);

            final Expression ageExpr = sortCriterion.getExpression();
            assert(ageExpr != null);
            assert(ageExpr.getChildCount() == 0);
            assert(ageExpr.getTypeInfo().isInt());

            final EntityPropertyProxy ageProxy = (EntityPropertyProxy) ageExpr.getIdentifiedContext();
            assert(ageProxy != null);

            final EntityProperty ageProp = ageProxy.getProperty();
            assert(ageProp != null);
            Assert.assertEquals("age", ageProp.getName());

            final Entity owner = ageProp.getEntity();
            assert(owner != null);
            Assert.assertEquals("/Concepts/Customer", owner.getEntityPath());

            final AliasedIdentifier aliasedIdentifier = (AliasedIdentifier) ageProxy.getParentContext();
            assert(aliasedIdentifier != null);
            assert(aliasedIdentifier.getIdentifiedContext() == owner);

            // TODO: run the query?

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



//  Will be used when we run the query!
//
//    /**
//     * Overridden to populate the RuleSession.
//     * @throws Exception
//     */
//    protected void populateRuleSession() throws Exception {
//        final RuleSession rs = this.getRuleSession();
//        final TypeManager typeManager = rs.getRuleServiceProvider().getTypeManager();
//
//        final List<Concept> allCustomers = new LinkedList<Concept>();
//        final DataReader reader = new DataReader(this.getTestDirectory().getPath() + "/customer.dat");
//        for (Object guest : reader.read()) {
//            final DataReader.Customer cust = (DataReader.Customer) guest;
//            final Concept beCustomer = (Concept) typeManager.createEntity("/Concepts/Customer");
//            beCustomer.getPropertyAtom("name").setValue(cust.getName());
//            beCustomer.getPropertyAtom("gender").setValue(cust.getSex());
//            beCustomer.getPropertyAtom("age").setValue(cust.getAge());
//            beCustomer.getPropertyAtom("business").setValue(cust.getBusiness());
//            beCustomer.getPropertyAtom("netWorth").setValue(cust.getNetworth());
//
//            allCustomers.add(beCustomer);
//        }
//
//        for (Concept customer : allCustomers) {
//            rs.assertObject(customer, true);
//        }
//    }

}
