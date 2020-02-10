package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.service.*;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.framework.CustomMaster;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleSession;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Test(groups = {"QEPFactory"}, enabled = true)
public class TestQueryExecutionPlanFactory extends TestNGBase {


    final static String queryText = "select \n"
//        + " {limit: first 1 offset 1 } \n"
//        + " distinct \n"
            + " c1.age as Age \n"
//            + " , c1.age + $v1 \n"
//            + " , c1.age - $v2 \n"
//            + " , c1@id as id \n"
//        +" c1.name, sum(c1.age), count(*) \n"
            + " from "
            + " \"/Concepts/Customer\" "
//            + " { emit: dead; "
//            + " policy: maintain latest 5 seconds "
//            + " where age > 19 "
//            + " by age "
//            + "} "
            + " c1 \n"
//            + ", \"/Concepts/Address\" a \n"
//            + ", \"/Concepts/Customer\" "
//            + " {emit: dead; \n"
//            + " policy: maintain latest 5 seconds "
////        + " \"/Concepts/Customer\" c3 \n"
//        + " where age > 19 \n"
//            + " by age }"
//            + " c2"
            + " where c1.age > 20 \n"
//            + " and c1.name < c2.name \n"
//            + " group by \n"
//            + " {stateful_capture: new; emit: new} \n"
//            + " age, a.age, c1.name \n"
//        + " having Age in (10+9, 20, 21) and count(*) > 0 \n"
            + " order by "
//            + "id"
            + " Age asc \n";

    final static Map<String, Object> preparedStatementParams = new HashMap<String, Object>();

    static {
        preparedStatementParams.put("v1", new Double(10));
        preparedStatementParams.put("v2", new Double(20));
    }


    private static Query query;

    private static TestQueryExecutionClassHelper tngbase;


    public TestQueryExecutionPlanFactory() {
    }

    public TestQueryExecutionPlanFactory(String name) {
        super(name);
    }

    @BeforeClass
    public static void startEngine() throws Exception {
        try {
            System.out.println("Testing QueryExecutionPlan generation...");
            System.out.println("STARTING BE-ENGINE ....");
            tngbase = new TestQueryExecutionClassHelper();
            tngbase.setUpClass();
            query = tngbase.getQuerySession().createQuery("query", queryText);
        }
        catch (Exception e) {
            Assert.fail(e.getMessage(), e);
        }
    }


    @AfterClass
    public static void stopEngine() throws Exception {
        System.out.println("STOPPING BE-ENGINE ....");
        try {
            query.close();
            tngbase.tearDownClass();
        }
        catch (QueryException e) {
            Assert.fail(e.getMessage(), e);
        }
    }


    @BeforeTest
    public void setUp() {
    }


    @AfterTest
    public void tearDown() {
    }


    @Test
    public void testCreateAndCloseStatement() {
        QueryStatement statement = null;
        try {
            statement = query.createStatement();
        }
        catch (Exception e) {
            Assert.fail("Could not create the statement: " + e.getMessage(), e);
        }

        if (null != statement) {
            try {
                statement.close();
            }
            catch (Exception e) {
                Assert.fail("Could not close the statement: " + e.getMessage(), e);
            }
        }
    }


    @Test
    public void testRunStatement() {
        QueryPreparedStatement statement = null;

        try {
            statement = query.prepareStatement();
        }
        catch (Exception e) {
            Assert.fail("Could not create the statement: " + e.getMessage(), e);
        }

        try {
            statement.bindParameters(preparedStatementParams);
        }
        catch (Exception e) {
            Assert.fail("Could not set the prepared statement params: " + e.getMessage(), e);
        }


        if (null != statement) {
            try {
                final List<Object[]> results = new ArrayList<Object[]>();

                try {
                    final CustomMaster master = new CustomMaster();
                    master.start(master.getProperties());

                    final QueryExecutionPlan executionPlan = statement.getExecutionPlan();
                    executionPlan.initializeAsContinuous();
                    final Collection<Source> sources = executionPlan.getSources().values();
                    final StreamedSink sink = (StreamedSink) executionPlan.getSink();

                    final PublicContinuousQuery continuousQuery = new PublicContinuousQuery(
                            master.getAgentService().getName(), "test query",
                            sources.toArray(new Source[sources.size()]), sink);

                    final Map m = new HashMap();
                    for (Map.Entry e : preparedStatementParams.entrySet()) {
                        m.put("BV$" + e.getKey(), e.getValue());
                    }
                    continuousQuery.init(m);

                    continuousQuery.start();

                    try {

                        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
                        try {
                            Thread.currentThread()
                                    .setContextClassLoader(query.getExecutionClassLoader());

                            for (Concept c : this.getConceptInstances()) {
                                for (Source s : sources) {
                                    final TupleInfo tupleInfo =
                                            s.getInternalStream().getOutputInfo();
                                    final Tuple t = tupleInfo.createTuple(SimpleIdGenerator.generateNewId());
                                    t.setColumns(new Object[]{c});

                                    System.out.println("_________________________________________");
                                    continuousQuery.enqueueInput(t);
                                    System.out.println("Sent: " + t);
                                }
                            }

                            long s = 7000;
                            System.out.println("_________________________________________");
                            System.out.println("Pausing for " + s + "ms.");

                            Thread.sleep(s);

                            System.out.println("_________________________________________");
                            for (Tuple t = sink.poll(1, TimeUnit.SECONDS); t != null;
                                 t = sink.poll(1, TimeUnit.SECONDS)) {
                                System.out.println("Received: " + t);
                                results.add(t.getRawColumns());
                            }


                        }
                        finally {
                            Thread.currentThread().setContextClassLoader(cl);
                        }

                    }
                    finally {
                        continuousQuery.stop();
                        master.stop();
                    }
                }
                catch (Exception e) {
                    Assert.fail("Failed while running the statement: " + e.getMessage(), e);
                }

//                Assert.assertEquals(results.size(), 2, "Bad number of results.");

            }
            finally {
                try {
                    statement.close();
                }
                catch (Exception e) {
                    Assert.fail("Could not close the statement: " + e.getMessage(), e);
                }
            }//finally
        }//if
    }


    @Test
    public void testCreateStatement() {
        try {
            query.createStatement();
        }
        catch (Exception e) {
            Assert.fail("Could not create the statement: " + e.getMessage(), e);
        }
    }


    protected static RuleSession getRuleSession() {
        return ruleSession;
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


    protected static QuerySession getQuerySession() {
        return querySession;
    }


    protected List<Concept> getConceptInstances() throws Exception {
        final RuleSession rs = this.getRuleSession();
        final TypeManager typeManager = rs.getRuleServiceProvider().getTypeManager();

        final List<Concept> allCustomers = new LinkedList<Concept>();
        final DataReader reader =
                new DataReader(this.getTestDirectory().getPath() + "/customer.dat");
        for (Object guest : reader.readCustomerData()) {
            final DataReader.Customer cust = (DataReader.Customer) guest;
            final Concept beCustomer = (Concept) typeManager.createEntity("/Concepts/Customer");
            beCustomer.getPropertyAtom("name").setValue(cust.getName());
            beCustomer.getPropertyAtom("gender").setValue(cust.getSex());
            beCustomer.getPropertyAtom("age").setValue(cust.getAge());
            beCustomer.getPropertyAtom("business").setValue(cust.getBusiness());
            beCustomer.getPropertyAtom("netWorth").setValue(cust.getNetworth());

            allCustomers.add(beCustomer);
        }

        return allCustomers;
    }


    public static class PublicContinuousQuery extends ContinuousQueryImpl {
        public PublicContinuousQuery(String regionName, String name, Source[] sources, Sink sink) {
            super(regionName, new ResourceId(name), sources, sink, new AsyncProcessListener() {
                public void beforeStart() {
                }

                public void afterEnd() {
                }
            });
        }

        @Override
        protected void handleQueuedInput(Object object) throws Exception {
            sources[0].sendNewTuple(context, (Tuple) object);
        }
    }


}

