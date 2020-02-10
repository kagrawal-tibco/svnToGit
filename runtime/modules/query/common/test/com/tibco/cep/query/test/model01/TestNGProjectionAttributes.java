package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProjectionAttributes;
import com.tibco.cep.query.service.Query;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 1, 2007
 * Time: 4:33:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Test(groups = { "Model01" }, enabled = true )
public class TestNGProjectionAttributes extends TestNGBase {

    private static TestNGProjectionAttributes tngbase;

    public TestNGProjectionAttributes() {
        super();
    }


    public TestNGProjectionAttributes(String name) {
        super(name);
    }


    @BeforeClass
    public static void startEngine() throws Exception {
        System.out.println("Testing Resolution Suite ....");
        System.out.println("STARTING BE-ENGINE ....");
        tngbase = new TestNGProjectionAttributes();
        tngbase.setUpClass();
    }



    @AfterClass
    public static void stopEngine() throws Exception {
        System.out.println("STOPPING BE-ENGINE ....");
        tngbase.tearDownClass();
    }



    @Test
    public void test01() {
        final String queryText = "select  name  from  \"/Concepts/Customer\" ;";
        try {
            final Query query = tngbase.getQuerySession().createQuery("query", queryText);
            final NamedSelectContext root = (NamedSelectContext) query.getModel();
            final ProjectionAttributes pa = root.getProjectionAttributes();
            Assert.assertNotNull(pa);
            Assert.assertEquals(pa.getProjectionElementCount(),1);
            Projection[] pas = pa.getAllProjections();
            Assert.assertEquals(pas.length,1);
            Assert.assertNotNull(pa.getProjection("$NSC$0002"));
            final Projection pr = (Projection) pa.getProjectionIterator().next();
            Assert.assertNotNull(pr);
            Assert.assertEquals(pr,pas[0]);
            Assert.assertEquals(pr,pa.getProjection("$NSC$0002"));
            Assert.assertNotNull(pr.getAlias());
            Assert.assertNotNull(pr.getAlias());
            query.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed:" + getClass().getSimpleName()+"->test01()");
        }
    }

    @Test
    public void test02() {
        final String queryText = "select  gender as g  from \"/Concepts/Customer\" group by g order by age;";

        try {
            final Query query = tngbase.getQuerySession().createQuery("query", queryText);

            final NamedSelectContext root = (NamedSelectContext) query.getModel();
            final ProjectionAttributes pa = root.getProjectionAttributes();
            Assert.assertNotNull(pa);
            Assert.assertEquals(pa.getProjectionElementCount(),1);
            Projection[] pas = pa.getAllProjections();
            Assert.assertEquals(pas.length,1);
            Assert.assertNotNull(pa.getProjection("g"));
            final Projection pr = (Projection) pa.getProjectionIterator().next();
            Assert.assertNotNull(pr);
            Assert.assertEquals(pr,pas[0]);
            Assert.assertEquals(pr,pa.getProjection("g"));
            Assert.assertNotNull(pr.getAlias());
            Assert.assertNotNull(pr.getAlias());

            query.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed:" + getClass().getSimpleName()+"->test02()");;
        }
    }
     @Test
     public void test03() {
        final String queryText = "select  1,2,3,4,5  from \"/Concepts/Customer\" C group by gender;";

        try {
            final Query query = tngbase.getQuerySession().createQuery("query", queryText);

            final NamedSelectContext root = (NamedSelectContext) query.getModel();
            final ProjectionAttributes pa = root.getProjectionAttributes();
            Assert.assertNotNull(pa);
            Assert.assertEquals(pa.getProjectionElementCount(),5);
            Projection[] pas = pa.getAllProjections();
            Assert.assertEquals(pas.length,5);


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
