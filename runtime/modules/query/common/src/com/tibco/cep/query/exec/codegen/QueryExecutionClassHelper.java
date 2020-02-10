package com.tibco.cep.query.exec.codegen;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javassist.ClassPath;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.tools.reflect.CannotCreateException;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.exec.ExecutionClassInfo;
import com.tibco.cep.query.exec.ModifierMask;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.runtime.service.loader.ByteCodeLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 6, 2007
 * Time: 4:47:49 PM
 */


public class QueryExecutionClassHelper implements ModifierMask {

    private QueryClassPool pool;
    private Query query;
    private QueryExecutionClassLoader loader;
    private CtClass ctClazz;
    private List<CtConstructor> constructors;
    private List<CtClass> nestedClasses;
    private Logger logger;
    private Map<CtClass, QueryExecutionClassHelper> nestedHelpers;
    boolean isInterface;
    boolean isNested;
    private QueryExecutionClassHelper parentHelper;


    private QueryExecutionClassHelper() {
        this.constructors = new ArrayList<CtConstructor>();
        this.nestedClasses = new ArrayList<CtClass>();
        this.nestedHelpers = new HashMap<CtClass, QueryExecutionClassHelper>();
    }

    /**
     * constructor
     *
     * @param query
     * @throws com.tibco.cep.query.api.QueryException
     */
    public QueryExecutionClassHelper(Query query) throws Exception {
        this();
        this.parentHelper = null;
        this.query = query;
        this.logger = this.query.getQuerySession().getRuleSession().getRuleServiceProvider().getLogger(this.getClass());
        this.loader = query.getExecutionClassLoader();
        final LoaderClassPath classpath = new LoaderClassPath(loader);
        QueryClassPool.doPruning = false;
        this.pool = QueryClassPool.getDefault();
        this.pool.appendClassPath(classpath);
//        this.pool.appendSystemPath();
        this.pool.appendClassPath(new QueryExecutionClassHelper.ExecutionClassPath(query));
    }


    /**
     * private constructor
     *
     * @param parenthelper
     * @param nestedClass
     * @throws com.tibco.cep.query.api.QueryException
     */
    private QueryExecutionClassHelper(QueryExecutionClassHelper parenthelper, CtClass nestedClass) throws Exception {
        this();
        this.parentHelper = parenthelper;
        this.query = this.parentHelper.getQuery();
        this.logger = this.parentHelper.getLogger();
        this.loader = this.query.getExecutionClassLoader();
        this.pool = this.parentHelper.getQueryClassPool();
        this.isInterface = false;
        this.ctClazz = nestedClass;
        //this.ctClazz.stopPruning(true);
    }

    /**
     * Creates a class
     *
     * @param clazzName
     * @param superClazzName
     * @throws Exception
     */
    public void createClass(String clazzName, String superClazzName) throws Exception {
        this.isInterface = false;
        this.isNested = false;
        if (null != superClazzName) {
            this.ctClazz = pool.makeClass(clazzName, pool.get(superClazzName));
        } else {
            this.ctClazz = pool.makeClass(clazzName);
        }
        //this.ctClazz.stopPruning(true);
    }

    /**
     * Creates an interface
     *
     * @param interfaceName
     * @param superInterfaceName
     * @throws Exception
     */
    public void createInterface(String interfaceName, String superInterfaceName) throws Exception {
        this.isInterface = false;
        this.isNested = false;
        this.isInterface = true;
        if (null != superInterfaceName) {
            this.ctClazz = pool.makeInterface(interfaceName, pool.get(superInterfaceName));
        } else {
            this.ctClazz = pool.makeInterface(interfaceName);
        }
        //this.ctClazz.stopPruning(true);
    }

    /**
     * creates a nested class
     *
     * @param clazzName
     * @param superClazzName
     * @return
     * @throws Exception
     */
    public QueryExecutionClassHelper createdNestedClass(String clazzName, String superClazzName) throws Exception {
        CtClass nestedCtClass;
        if (null == this.ctClazz) {
            throw new Exception("Outer class has not been created before creating nested class");
        }
        nestedCtClass = this.ctClazz.makeNestedClass(clazzName, true);
        if (null != superClazzName) {
            nestedCtClass.setSuperclass(pool.get(superClazzName));
        }
        this.nestedClasses.add(nestedCtClass);
        QueryExecutionClassHelper helper = new QueryExecutionClassHelper(this, nestedCtClass);
        helper.isNested = true;
        this.nestedHelpers.put(nestedCtClass, helper);
        return helper;
    }

    /**
     * Adds an interface to the class
     *
     * @param interfaceName
     * @throws Exception
     */
    public void implementInterface(String interfaceName) throws Exception {
        CtClass interfc = pool.get(interfaceName);
        this.ctClazz.addInterface(interfc);
    }


    /**
     * Adds a method
     *
     * @param modifierMask
     * @param returnType
     * @param methodName
     * @param args
     * @param exceptions
     * @param body
     * @throws Exception
     */
    public void addMethod(int modifierMask,
                          Class returnType,
                          String methodName,
                          Class[] args,
                          Class[] exceptions,
                          String body) throws Exception {
        CtClass returnClass = pool.get(returnType.getName());
        CtClass[] params = new CtClass[args.length];
        for (int i = 0; i < args.length; i++) {
            params[i] = pool.get(args[i].getName());
        }
        CtClass[] excps = new CtClass[exceptions.length];
        for (int i = 0; i < exceptions.length; i++) {
            excps[i] = pool.get(exceptions[i].getName());
        }
        CtMethod mtd = null;
        if (this.isInterface) {
            mtd = CtNewMethod.abstractMethod(returnClass, methodName, params, excps, this.ctClazz);
            this.ctClazz.addMethod(mtd);
        } else {
            mtd = new CtMethod(returnClass, methodName, params, this.ctClazz);
            mtd.setExceptionTypes(excps);
            mtd.setModifiers(modifierMask);
            this.ctClazz.addMethod(mtd);
            mtd.setBody(body);
        }

        return;
    }

    /**
     * Adds a field
     *
     * @param clazz
     * @param name
     * @throws Exception
     */
    public void addField(int modifierMask, Class clazz, String name, String initializer) throws Exception {
        this.addField(modifierMask, clazz.getName(), name, initializer);
    }

    /**
     * Adds a field
     *
     * @param clazzName
     * @param name
     * @throws Exception
     */
    public void addField(int modifierMask, String clazzName, String name, String initializer) throws Exception {
        if (
                (
                        ((modifierMask & STATIC) == 0) ||
                                ((modifierMask & PUBLIC) == 0) ||
                                ((modifierMask & FINAL) == 0)
                ) && this.isInterface) {
            throw new CannotCreateException("Interface field must be public static final");
        }
        CtField field = new CtField(pool.get(clazzName), name, this.ctClazz);
        field.setModifiers(modifierMask);
        if (null != initializer) {
            CtField.Initializer init = CtField.Initializer.byExpr(initializer);
            this.ctClazz.addField(field, initializer);
        } else {
            this.ctClazz.addField(field);
        }
    }

    /**
     * Adds a default public constructor
     *
     * @throws Exception
     */
    public void addDefaultConstructor() throws Exception {
        CtConstructor dc = CtNewConstructor.defaultConstructor(this.ctClazz);
        this.constructors.add(dc);
    }

    /**
     * Adds a constructor
     *
     * @param args
     * @param body
     * @throws Exception
     */
    public void addConstructor(int modifierMask, Class[] args, String body) throws Exception {
        if (this.isInterface) {
            throw new CannotCreateException("Cannot create constructor for an interface");
        }
        CtClass[] params = new CtClass[args.length];
        for (int i = 0; i < args.length; i++) {
            params[i] = pool.get(args[i].getName());
        }
        CtConstructor cns = new CtConstructor(params, this.ctClazz);
        cns.setModifiers(modifierMask);
        cns.setBody(body);
        this.constructors.add(cns);
        this.ctClazz.addConstructor(cns);

        return;
    }


    /**
     * [Re]defines the static initializer
     *
     * @param body
     * @throws Exception
     */
    public void setStaticInitializer(String body) throws Exception {
        CtConstructor cns = this.ctClazz.makeClassInitializer();
        cns.setBody(body);
    }


    /**
     * Returns the compiled bytecode information of an execution class
     *
     * @return ExecutionByteCodeInfo
     * @throws Exception
     */
    public ExecutionClassInfo getExecutionClassInfo() throws Exception {
        ExecutionClassInfo clzInfo = null;
        if (!this.isInterface) {
            if (this.constructors.size() == 0) {
                this.ctClazz.addConstructor(CtNewConstructor.defaultConstructor(this.ctClazz));
            }
        }

        for (Iterator it = this.nestedClasses.iterator(); it.hasNext();) {
            CtClass nc = (CtClass) it.next();
            QueryExecutionClassHelper helper = this.nestedHelpers.get(nc);
            helper.getExecutionClassInfo();
        }
        //if (!this.isNested) {
        clzInfo = this.loader.registerClass(this.ctClazz);
        //}

        //this.ctClazz.stopPruning(false);
        //this.ctClazz.detach();
        return clzInfo;
    }

    /**
     * @return ClassPool
     */
    QueryClassPool getQueryClassPool() {
        return pool;
    }

    /**
     * Returns the number of classes in the contained classpool
     *
     * @return int
     */
    public int size() {
        return pool.size();
    }

    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Returns the query object associated with the helper
     *
     * @return Query
     */
    public Query getQuery() {
        return this.query;
    }

    /**
     * Returns the class name being constructed by this helper
     *
     * @return String
     */
    public String getName() {
        return this.ctClazz.getName();
    }

    /**
     * Returns the String representation of the classpool contained inside
     *
     * @return String
     */
    public String toString() {
        return pool.toString();
    }

    /**
     * Provides ClassPath extension to ClassPool for getting BE generated classes
     */
    class ExecutionClassPath implements ClassPath {
        private static final String QUERY_CLASSPATH_PROTOCOL = "file";
        private static final String QUERY_HOST = "localhost";
        RuleServiceProvider provider;
        Query query;
        List<ByteCodeLoader> loaders;

        /**
         * constructor
         *
         * @param query
         */
        public ExecutionClassPath(Query query) throws Exception {
            this.query = query;
            this.provider = query.getQuerySession().getRuleSession().getRuleServiceProvider();
            loaders = new ArrayList<ByteCodeLoader>();
            loaders.add((ByteCodeLoader) this.query.getExecutionClassLoader());
            loaders.add((ByteCodeLoader) provider.getClassLoader());
        }


        /**
         * This method is invoked when the ClassPath object is detached from the search path.
         */
        public void close() {
            this.provider = null;
//            this.classRegistry = null;
        }

        /**
         * Returns a dummy URL for the Java assist loader
         *
         * @param classname
         * @return URL
         */
        public URL find(String classname) {
            for (Iterator it = loaders.iterator(); it.hasNext();) {
                ByteCodeLoader loader = (ByteCodeLoader) it.next();
                if (loader.containsClass(classname)) {
                    try {
                        return new URL(QUERY_CLASSPATH_PROTOCOL, QUERY_HOST, 0, classname);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        logger.log(Level.ERROR, e, e.getMessage());
                    }
                }
            }
            return null;
        }

        /**
         * Called after find(String classname) failure to load the class's
         * bytecode inputstream
         *
         * @param classname
         * @return
         * @throws NotFoundException
         */
        public InputStream openClassfile(String classname) throws NotFoundException {
            byte[] byteCode = null;
            for (Iterator it = loaders.iterator(); it.hasNext();) {
                ByteCodeLoader loader = (ByteCodeLoader) it.next();
                byteCode = loader.getByteCode(classname);
                if (null != byteCode) {
                    break;
                }
            }
            if (null == byteCode) {
                throw new NotFoundException("Class: " + classname + " not found.");
            }
            return new ByteArrayInputStream(byteCode);
        }

    }


}
