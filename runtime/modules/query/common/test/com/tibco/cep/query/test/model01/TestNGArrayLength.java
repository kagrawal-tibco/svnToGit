package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.service.Query;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleSession;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 31, 2007
 * Time: 6:48:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Test(groups = { "Model01" }, enabled = true )
public class TestNGArrayLength extends TestNGBase {
    private static TestNGArrayLength tngbase;

    public TestNGArrayLength() {
        super();
    }


    public TestNGArrayLength(String name) {
        super(name);
    }

    @BeforeClass
    public static void startEngine() throws Exception {
        System.out.println("Testing Resolution Suite ....");
        System.out.println("STARTING BE-ENGINE ....");
        try{
        tngbase = new TestNGArrayLength();
        tngbase.setUpClass();
        } catch(Throwable t) {
            t.printStackTrace();
            Assert.fail(t.getMessage(),t);
        }
    }



    @AfterClass
    public static void stopEngine() throws Exception {
        System.out.println("STOPPING BE-ENGINE ....");
        tngbase.tearDownClass();
    }


    public void testQuery() {
        final String queryText =  "select  customer.orders@length  from  \"/Concepts/Customer\" customer";

        try {
            final Query query = tngbase.getQuerySession().createQuery("query", queryText);
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

    protected void populateRuleSession() throws Exception {
        final RuleSession rs = tngbase.getRuleSession();
        final TypeManager typeManager = rs.getRuleServiceProvider().getTypeManager();

        final Map<String,Concept> allCustomers = new HashMap<String,Concept>();
        final DataReader custReader = new DataReader(this.getTestDirectory().getPath() + "/customer.dat");
        for (Object guest : custReader.readCustomerData()) {
            final DataReader.Customer cust = (DataReader.Customer) guest;
            final Concept beCustomer = (Concept) typeManager.createEntity("/Concepts/Customer");
            beCustomer.setExtId(String.valueOf(cust.getId()));
            beCustomer.getPropertyAtom("customerId").setValue(cust.getId());
            beCustomer.getPropertyAtom("name").setValue(cust.getName());
            beCustomer.getPropertyAtom("gender").setValue(cust.getSex());
            beCustomer.getPropertyAtom("age").setValue(cust.getAge());
            beCustomer.getPropertyAtom("business").setValue(cust.getBusiness());
            beCustomer.getPropertyAtom("netWorth").setValue(cust.getNetworth());
            allCustomers.put(String.valueOf(cust.getId()),beCustomer);
        }
        final List<Concept> allAddresses = new LinkedList<Concept>();
        final DataReader addrReader = new DataReader(this.getTestDirectory().getPath() + "/address.dat");
        for (Object obj : addrReader.readAddressData()) {
            DataReader.Address addr = (DataReader.Address) obj;
            final Concept beCustomer = (Concept) typeManager.createEntity("/Concepts/Address");
            beCustomer.setExtId(String.valueOf(addr.getAddressid()));
            beCustomer.getPropertyAtom("addressId").setValue(addr.getAddressid());
            beCustomer.getPropertyAtom("street").setValue(addr.getStreet());
            beCustomer.getPropertyAtom("city").setValue(addr.getCity());
            beCustomer.getPropertyAtom("state").setValue(addr.getState());
            beCustomer.getPropertyAtom("country").setValue(addr.getCountry());
            beCustomer.getPropertyAtom("customerId").setValue(addr.getCustomerid());
            allAddresses.add(beCustomer);
        }
        for (Concept address : allAddresses) {
            //rs.assertObject(address, true);
            long customerid = (Long) address.getPropertyAtom("customerId").getValue();
            //Concept customer = (Concept) rs.getObjectManager().getElement(String.valueOf(customerid));
            Concept customer = allCustomers.get(String.valueOf(customerid));
            customer.getPropertyArray("addresses").add(address);
        }

        final List<Concept> allOrders = new LinkedList<Concept>();
        final DataReader orderReader = new DataReader(this.getTestDirectory().getPath() + "/order.dat");
        for (Object obj : orderReader.readOrderData()) {
            DataReader.Order order = (DataReader.Order) obj;
            final Concept beCustomer = (Concept) typeManager.createEntity("/Concepts/Order");
            beCustomer.setExtId(String.valueOf(order.getOrderid()));
            beCustomer.getPropertyAtom("orderId").setValue(order.getOrderid());
            beCustomer.getPropertyAtom("customerId").setValue(order.getCustomerid());
            beCustomer.getPropertyAtom("items").setValue(order.getItems());
            beCustomer.getPropertyAtom("value").setValue(order.getValue());
            allOrders.add(beCustomer);
        }
        for (Concept order : allOrders) {
           long customerid = (Long) order.getPropertyAtom("customerId").getValue();
            long orderid = (Long) order.getPropertyAtom("orderId").getValue();
            //Concept customer = (Concept) rs.getObjectManager().getElement(String.valueOf(customerid));
            Concept customer = allCustomers.get(String.valueOf(customerid));
            customer.getPropertyArray("orders").add(order);
            customer.getPropertyArray("orderNumbers").add(orderid);
            rs.assertObject(order, true);
        }
        for (Concept customer : allCustomers.values()) {
            rs.assertObject(customer, true);
        }


    }

}
