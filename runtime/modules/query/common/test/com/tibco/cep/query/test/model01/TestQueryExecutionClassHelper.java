package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.exec.ExecutionClassInfo;
import com.tibco.cep.query.exec.ModifierMask;
import com.tibco.cep.query.exec.codegen.QueryExecutionClassHelper;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleSession;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * QueryByteCodeHelper Tester.
 *
 * @author pdhar
 * @version $LastChangedRevision$, $LastChangedDate$
 *          Date November 7, 2007
 * @since 1.0
 */

@Test(groups = {"Model01"}, enabled = true)
public class TestQueryExecutionClassHelper extends TestNGBase {
    private static TestQueryExecutionClassHelper tngbase;
    private QueryExecutionClassHelper bc;
    final static String queryText = "select  name  from  \"/Concepts/Customer\" order by age;";
    private static Query query;
    private ExecutionClassInfo classInfo;


    public TestQueryExecutionClassHelper() {
    }

    public TestQueryExecutionClassHelper(String name) {
        super(name);
    }

    @BeforeClass
    public static void startEngine() throws Exception {
        try {
            System.out.println("Testing ByteCode Suite ....");
            System.out.println("STARTING BE-ENGINE ....");
            tngbase = new TestQueryExecutionClassHelper();
            tngbase.setUpClass();
            query = tngbase.getQuerySession().createQuery("query", queryText);
        } catch (Exception e) {
            Assert.fail(e.getMessage(), e);
        }
    }


    @AfterClass
    public static void stopEngine() throws Exception {
        System.out.println("STOPPING BE-ENGINE ....");
        try {
            query.close();
            tngbase.tearDownClass();
        } catch (QueryException e) {
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
    public void testCreateClass() {
        QueryExecutionClassHelper eClazz = null;
        try {
            eClazz = new QueryExecutionClassHelper(query);
            eClazz.createClass("com.tibco.cep.query.test.testNG.TestHashMap1", "java.util.LinkedHashMap");
            eClazz.addField(ModifierMask.PRIVATE | ModifierMask.STATIC, int.class, "MAX_ENTRIES",
                    "2");
            eClazz.addMethod(ModifierMask.PROTECTED,
                    boolean.class,
                    "removeEldestEntry",
                    new Class[]{Map.Entry.class},
                    new Class[0],
                    "return size() > MAX_ENTRIES;"
            );
            this.classInfo = eClazz.getExecutionClassInfo();
            Class clazz = classInfo.getClazz();
            Constructor ctor = clazz.getConstructor(new Class[0]);
            Map m = (Map) ctor.newInstance(new Object[0]);
            m.put("1", "One");
            m.put("2", "Two");
            m.put("3", "Three");
            System.out.println("QueryClassLoader:"+ query.getExecutionClassLoader().toString());
            Assert.assertEquals(m.toString(), "{2=Two, 3=Three}");
        } catch (Exception e) {
            Assert.fail(e.getMessage(), e);
        } finally {
            eClazz = null;
        }
    }

    @Test
    public void testCreateInterface() {
        QueryExecutionClassHelper eClazz = null;
        QueryExecutionClassHelper iClazz = null;
        try {
            iClazz = new QueryExecutionClassHelper(query);
            iClazz.createInterface("TestInterface1", null);
            iClazz.addField(ModifierMask.PUBLIC |
                    ModifierMask.STATIC |
                    ModifierMask.FINAL, String.class, "NAME",
                    "\"TestString\"");
            iClazz.addMethod(ModifierMask.PUBLIC, String.class, "getName", new Class[0], new Class[0], "");
            ExecutionClassInfo intInfo = iClazz.getExecutionClassInfo();

            eClazz = new QueryExecutionClassHelper(query);
            eClazz.createClass("com.tibco.cep.query.test.testNG.TestHashMap2", "java.util.LinkedHashMap");
            eClazz.implementInterface(intInfo.getClazz().getName());
            eClazz.addField(ModifierMask.PRIVATE | ModifierMask.STATIC, int.class, "MAX_ENTRIES",
                    "2");
            eClazz.addMethod(ModifierMask.PUBLIC,
                    String.class,
                    "getName",
                    new Class[0],
                    new Class[0],
                    "return TestInterface1.NAME;");
            eClazz.addMethod(ModifierMask.PROTECTED,
                    boolean.class,
                    "removeEldestEntry",
                    new Class[]{Map.Entry.class},
                    new Class[0],
                    "return size() > MAX_ENTRIES;"
            );
            ExecutionClassInfo clazzInfo = eClazz.getExecutionClassInfo();
            Class clazz = clazzInfo.getClazz();
            Constructor ctor = clazz.getConstructor(new Class[0]);
            Map m = (Map) ctor.newInstance(new Object[0]);
            m.put("1", "One");
            m.put("2", "Two");
            m.put("3", "Three");
            System.out.println("QueryClassLoader:"+ query.getExecutionClassLoader().toString());
            Assert.assertEquals(m.toString(), "{2=Two, 3=Three}");
            Method mtd = clazz.getMethod("getName", new Class[0]);
            Assert.assertEquals(mtd.invoke(m, new Object[0]), "TestString");
        } catch (Exception e) {
            Assert.fail(e.getMessage(), e);
        } finally {
            eClazz = null;
        }
    }

    @Test
    public void testCreatedNestedClass() {
        QueryExecutionClassHelper eClazz = null;
        QueryExecutionClassHelper iClazz = null;
        try {
            eClazz = new QueryExecutionClassHelper(query);
            // Outer Class stuff
            eClazz.createClass("com.tibco.cep.query.test.testNG.TestHashMap4", "java.util.LinkedHashMap");
            eClazz.addField(ModifierMask.PRIVATE | ModifierMask.STATIC, int.class, "MAX_ENTRIES", "2");

            eClazz.addMethod(ModifierMask.PROTECTED,
                    boolean.class,
                    "removeEldestEntry",
                    new Class[]{Map.Entry.class},
                    new Class[0],
                    "return size() > MAX_ENTRIES;"
            );
            iClazz = eClazz.createdNestedClass("TestInner01", null);
            // Inner class stuff
            iClazz.addField(ModifierMask.PRIVATE, int.class, "iValue", "0");
            iClazz.addConstructor(ModifierMask.PUBLIC, new Class[]{int.class}, "iValue = $1;");  //$0=this,$1=first arg
            iClazz.addMethod(ModifierMask.PUBLIC, int.class, "getIntValue", new Class[0], new Class[0], "return $0.iValue;");
            eClazz.addField(ModifierMask.PRIVATE | ModifierMask.STATIC, Object.class, "TEST_INT", "new "+ iClazz.getName() +"(6)");
            eClazz.addMethod(ModifierMask.PUBLIC,
                    int.class,
                    "getTestInt",
                    new Class[0],
                    new Class[0],
                    "return ((" + iClazz.getName() + ") TEST_INT).getIntValue();"
            );


            ExecutionClassInfo bci = eClazz.getExecutionClassInfo();
            Class clazz = bci.getClazz();
            Constructor ctor = clazz.getConstructor(new Class[0]);
            Map m = (Map) ctor.newInstance(new Object[0]);
            m.put("1", "One");
            m.put("2", "Two");
            m.put("3", "Three");
            System.out.println("QueryClassLoader:"+ query.getExecutionClassLoader().toString());
            Assert.assertEquals(m.toString(), "{2=Two, 3=Three}");

            Method mtdId = clazz.getMethod("getTestInt", new Class[0]);
            int val = (Integer) mtdId.invoke(m, new Object[0]);
            Assert.assertEquals(val, 6, "Entity mismatched");

        } catch (Exception e) {
            Assert.fail(e.getMessage(), e);
        } finally {
            eClazz = null;
        }
    }

    @Test
    public void testGetByteCodeInfo() {
        QueryExecutionClassHelper eClazz = null;
        try {
            eClazz = new QueryExecutionClassHelper(query);
            eClazz.createClass("com.tibco.cep.query.test.testNG.TestHashMap3", "java.util.LinkedHashMap");
            eClazz.addField(ModifierMask.PRIVATE | ModifierMask.STATIC, int.class, "MAX_ENTRIES",
                    "2");
            eClazz.addMethod(ModifierMask.PROTECTED,
                    boolean.class,
                    "removeEldestEntry",
                    new Class[]{Map.Entry.class},
                    new Class[0],
                    "return size() > MAX_ENTRIES;"
            );
            ExecutionClassInfo bci = eClazz.getExecutionClassInfo();
            Class clazz = bci.getClazz();
            Constructor ctor = clazz.getConstructor(new Class[0]);
            Map m = (Map) ctor.newInstance(new Object[0]);
            m.put("1", "One");
            m.put("2", "Two");
            m.put("3", "Three");
            System.out.println("QueryClassLoader:"+query.getExecutionClassLoader().toString());
            Assert.assertEquals(m.toString(), "{2=Two, 3=Three}");
        } catch (Exception e) {
            Assert.fail(e.getMessage(), e);
        } finally {
            eClazz = null;
        }
    }

    public void TestBEClassCompile() {
        QueryExecutionClassHelper eClazz = null;
        try {
            eClazz = new QueryExecutionClassHelper(query);
            eClazz.createClass("com.tibco.cep.query.test.testNG.ConceptViewer", "java.util.LinkedHashMap");
            eClazz.addField(ModifierMask.PRIVATE | ModifierMask.STATIC, int.class, "MAX_ENTRIES",
                    "2");
            eClazz.addMethod(ModifierMask.PROTECTED,
                    boolean.class,
                    "removeEldestEntry",
                    new Class[]{Map.Entry.class},
                    new Class[0],
                    "return size() > MAX_ENTRIES;"
            );
            eClazz.addMethod(ModifierMask.PUBLIC,
                    long.class,
                    "getId",
                    new Class[]{Concept.class},
                    new Class[0],
                    "return $1.getId();"
            );
            eClazz.addMethod(ModifierMask.PUBLIC,
                    String.class,
                    "getName",
                    new Class[]{Concept.class},
                    new Class[0],
                    "return (((be.gen.Concepts.Customer)$1).get$2zname().getString());"
            );
            ExecutionClassInfo bci = eClazz.getExecutionClassInfo();
            Class clazz = bci.getClazz();
            Constructor ctor = clazz.getConstructor(new Class[0]);
            Map m = (Map) ctor.newInstance(new Object[0]);
            m.put("1", "One");
            m.put("2", "Two");
            m.put("3", "Three");
            System.out.println("QueryClassLoader:"+query.getExecutionClassLoader().toString());
            Assert.assertEquals(m.toString(), "{2=Two, 3=Three}");

            final RuleSession rs = tngbase.getRuleSession();
            final TypeManager typeManager = rs.getRuleServiceProvider().getTypeManager();

            final Concept beCustomer = (Concept) typeManager.createEntity("/Concepts/Customer");
            beCustomer.getPropertyAtom("name").setValue("TestCustomer");
            beCustomer.getPropertyAtom("gender").setValue("M");
            beCustomer.getPropertyAtom("age").setValue(21);
            beCustomer.getPropertyAtom("netWorth").setValue(123.45);
            long id = beCustomer.getId();
            rs.assertObject(beCustomer, true);

            Method mtdId = clazz.getMethod("getId", new Class[]{Concept.class});
            //System.out.println("******* Number of elements: " + rs.getObjectManager().numOfElement());
            //System.out.println("******* Element @ 7: " + rs.getObjectManager().getElement(7));
            long val = (Long) mtdId.invoke(m, new Object[]{beCustomer});
            Assert.assertEquals(val, id, "Entity mismatched");

            Method mtdName = clazz.getMethod("getName", new Class[]{Concept.class});
            String name = (String) mtdName.invoke(m, new Object[]{beCustomer});
            Assert.assertEquals(name, "TestCustomer", "Name mismatched");

        } catch (Exception e) {
            Assert.fail(e.getMessage(), e);
        } finally {
            eClazz = null;
        }

    }


    public void testClassPool() {
        Query oldQuery = null;
        oldQuery = query;
        try {
            query = null;
            for(int i = 0 ; i < 5; i++ ) {
                query = tngbase.getQuerySession().createQuery("query", queryText);
                QueryExecutionClassHelper eClazz = null;
                QueryExecutionClassHelper iClazz = null;

                eClazz = new QueryExecutionClassHelper(query);
                System.out.println("Query Execution Helper Classpool size:"+ eClazz.size());
                // Outer Class stuff
                eClazz.createClass("com.tibco.cep.query.test.testNG."+query.getIdGenerator().nextIdentifier(), "java.util.LinkedHashMap");
                eClazz.addField(ModifierMask.PRIVATE | ModifierMask.STATIC, int.class, "MAX_ENTRIES", "2");
                eClazz.addMethod(ModifierMask.PROTECTED,
                        boolean.class,
                        "removeEldestEntry",
                        new Class[]{Map.Entry.class},
                        new Class[0],
                        "return size() > MAX_ENTRIES;"
                );
                eClazz.addMethod(ModifierMask.PUBLIC,
                        long.class,
                        "getId",
                        new Class[]{Concept.class},
                        new Class[0],
                        "return $1.getId();"
                );
                eClazz.addMethod(ModifierMask.PUBLIC,
                        String.class,
                        "getName",
                        new Class[]{Concept.class},
                        new Class[0],
                        "return (((be.gen.Concepts.Customer)$1).get$2zname().getString());"
                );
                // Inner class stuff
                //String innerClassName = "com.tibco.cep.query.test.testNG."+ query.getIdGenerator().nextIdentifier();
                String innerClassName =  query.getIdGenerator().nextIdentifier().toString();
                iClazz = eClazz.createdNestedClass(innerClassName, null);
                iClazz.addField(ModifierMask.PRIVATE, int.class, "iValue", "0");
                iClazz.addConstructor(ModifierMask.PUBLIC, new Class[]{int.class}, "iValue = $1;");  //$0=this,$1=first arg
                iClazz.addMethod(ModifierMask.PUBLIC, int.class, "getIntValue", new Class[0], new Class[0], "return $0.iValue;");
                eClazz.addField(ModifierMask.PRIVATE | ModifierMask.STATIC, Object.class, "TEST_INT", "new "+ iClazz.getName() +"(6)");
                eClazz.addMethod(ModifierMask.PUBLIC,
                        int.class,
                        "getTestInt",
                        new Class[0],
                        new Class[0],
                        "return ((" + iClazz.getName() + ") TEST_INT).getIntValue();"
                );
                ExecutionClassInfo bci = eClazz.getExecutionClassInfo();
                Class clazz = bci.getClazz();
                // test outer class
                Constructor ctor = clazz.getConstructor(new Class[0]);
                Map m = (Map) ctor.newInstance(new Object[0]);
                m.put("1", "One");
                m.put("2", "Two");
                m.put("3", "Three");

                Assert.assertEquals(m.toString(), "{2=Two, 3=Three}");
                // test inner
                Method mtdId = clazz.getMethod("getTestInt", new Class[0]);
                int val = (Integer) mtdId.invoke(m, new Object[0]);
                Assert.assertEquals(val, 6, "Entity mismatched");

                final RuleSession rs = tngbase.getRuleSession();
                final TypeManager typeManager = rs.getRuleServiceProvider().getTypeManager();
                // test be class
                final Concept beCustomer = (Concept) typeManager.createEntity("/Concepts/Customer");
                beCustomer.getPropertyAtom("name").setValue("TestCustomer");
                beCustomer.getPropertyAtom("gender").setValue("M");
                beCustomer.getPropertyAtom("age").setValue(21);
                beCustomer.getPropertyAtom("netWorth").setValue(123.45);
                long id = beCustomer.getId();
                rs.assertObject(beCustomer, true);

                Method mtdgetId = clazz.getMethod("getId", new Class[]{Concept.class});
                long idval = (Long) mtdgetId.invoke(m, new Object[]{beCustomer});
                Assert.assertEquals(idval, id, "Entity mismatched");

                Method mtdName = clazz.getMethod("getName", new Class[]{Concept.class});
                String name = (String) mtdName.invoke(m, new Object[]{beCustomer});
                Assert.assertEquals(name, "TestCustomer", "Name mismatched");
                System.out.println("****************************************************************");
                System.out.println("Query ClassLoader size:"+ query.getExecutionClassLoader().size());
                System.out.println("Query ClassLoader classes:"+query.getExecutionClassLoader().toString());
                System.out.println("Query Execution Helper Classpool size:"+ eClazz.size());
                System.out.println("Query Execution Helper Classpool classes:"+ eClazz.toString());
                System.out.println("Query ClassLoader Parent size:"+ query.getExecutionClassLoader().getParentLoader().size());
                System.out.println("Query ClassLoader Parent classes:"+ query.getExecutionClassLoader().getParentLoader().toString());
                //System.out.println("Query Classpool size:"+((QueryImpl)query).getClassPool().size());
                //System.out.println("Query Classpool classes:"+((QueryImpl)query).getClassPool().toString());
                System.out.println("****************************************************************");
            }

        } catch (Exception e) {
            Assert.fail(e.getMessage(), e);
        } finally {
           query = oldQuery;
        }
    }


    /**
     * Overridden to populate the RuleSession.
     *
     * @throws Exception
     */
    protected void populateRuleSession() throws Exception {
//        final RuleSession rs = tngbase.getRuleSession();
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


    protected File getTestDirectory() throws IOException {
        final File dir = new File(ROOT_TEST_DIRECTORY_PATH, this.getTestDirectoryName());
        if (!(dir.exists() && dir.isDirectory())) {
            throw new IOException("Invalid test directory name");
        }
        return dir;
    }


    protected static QuerySession getQuerySession() {
        return querySession;
    }

}
