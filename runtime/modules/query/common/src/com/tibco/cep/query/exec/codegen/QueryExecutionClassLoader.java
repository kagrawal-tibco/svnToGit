package com.tibco.cep.query.exec.codegen;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javassist.CtClass;

import com.tibco.cep.query.service.Query;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.loader.ByteCodeLoader;

/**
 * Holds the generated classes for a query until that query is closed.
 * The same bytecode can be shared between multiple executing query statements.
 */
public class QueryExecutionClassLoader extends ClassLoader implements ByteCodeLoader {

    private Query query;
    private int jdiPort;
    private Map<String, ExecutionClassInfoImpl> byteCodeMap;
    private Map<String,Class> classRegistry;
    private ClassLoader parentClassLoader;




    public QueryExecutionClassLoader(BEClassLoader parentClassLoader, Query query) {
        super(parentClassLoader);
        this.parentClassLoader = parentClassLoader;
        this.query = query;
        this.byteCodeMap = new HashMap<String,ExecutionClassInfoImpl>();
        this.classRegistry = new HashMap<String,Class>();
    }


    public Query getQuery() {
        return query;
    }

    /**
     * returns the debug port
     * @param qry
     * @return int
     */
    private static int getJdiPort(Query qry) {
        Properties props =qry.getQuerySession().getRuleSession().getRuleServiceProvider().getProperties();
        return Integer.parseInt(props.getProperty("tibco.env.JDI_PORT", "-1"));
    }

    /**
     * registers the javassist class
     * @param ctClazz
     * @return ExecutionByteCodeInfo
     * @throws Exception
     */
    public ExecutionClassInfoImpl registerClass(CtClass ctClazz) throws Exception {
        final String clazzName = ctClazz.getName();
        final byte[] bytecode = ctClazz.toBytecode();
        final ExecutionClassInfoImpl execClassInfo = this.byteCodeMap.get(clazzName);
        if (null != execClassInfo) {
            if (Arrays.equals(execClassInfo.getBytecode(), bytecode)) {
                return execClassInfo;
            }
            throw new Exception("Attempted class redefinition: " + clazzName);
        }

        final Class<?> clazz = defineClass(clazzName, bytecode, 0, bytecode.length);
        final ExecutionClassInfoImpl bci = new ExecutionClassInfoImpl(
                                                        clazz.getName(),
                                                        clazz,
                                                        ctClazz.toBytecode());
        this.byteCodeMap.put(clazzName,bci);
        this.classRegistry.put(clazzName,clazz);
        return bci;
    }

    /**
     * declares a package
     * @param classname
     */
     protected void declarePackage(String classname) {
        //todo: check for seal package??
        String packageName = null;
        int pos = classname.lastIndexOf('.');
        if (pos != -1)
            packageName = classname.substring(0, pos);

        if (packageName != null) {
            Package pkg = getPackage(packageName);
            if (pkg == null) {
                definePackage(packageName, null, null, null, null, null, null, null);
            }
        }
    }

    /**
     * finds a class
     * @param name
     * @return Class
     * @throws ClassNotFoundException
     */
    protected Class findClass(String name) throws ClassNotFoundException {
        declarePackage(name);
        ExecutionClassInfoImpl info = (ExecutionClassInfoImpl) byteCodeMap.get(name);
        if(info != null) {
            if(info.getClazz() == null) {
                info.setClazz(defineClass(info.getClazzName(), info.getBytecode(), 0, info.getBytecode().length));
            }
            return info.getClazz();
        } else {
            return super.findClass(name);
        }

    }

    /**
     * String showing the classes known to this classloader
     * @return String
     */
    public String toString() {
        return this.byteCodeMap.keySet().toString();
    }


    public byte[] getByteCode(String classname) {
        ExecutionClassInfoImpl clinfo =  this.byteCodeMap.get(classname);
        if(null != clinfo) {
            return clinfo.getBytecode();
        } else  {
            return null;
        }
    }

    /**
     * Returns a collectioo of classes loaded by the classloader
     *
     * @return Collection
     */
    public Collection<Class> getClasses() {
        return this.classRegistry.values();
    }

    /**
     * Returns true if the specified class is known to the loader else false
     *
     * @param classname
     * @return boolean
     */
    public boolean containsClass(String classname) {
        return this.classRegistry.containsKey(classname);
    }

    /**
     * returns the number of classes in the loader
     * @return int
     */
    public int size() {
        return this.classRegistry.size();
    }

    /**
     * Returns the parent ByteCodeLoader
     *
     * @return ByteCodeLoader
     */
    public ByteCodeLoader getParentLoader() {
        if(this.parentClassLoader instanceof ByteCodeLoader) {
            return (ByteCodeLoader) this.parentClassLoader;
        }
        return null;
    }
}
