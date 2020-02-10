package com.tibco.cep.query.exec.codegen;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 8, 2007
 * Time: 7:58:22 PM
 * To change this template use File | Settings | File Templates.
 */
class QueryClassPool extends ClassPool {

    private Map classmap;
    private static QueryClassPool instance = null;

    /**
     * Returns the default class pool.
     * The returned object is always identical since this method is
     * a singleton factory.
     *
     * <p>The default class pool searches the system search path,
     * which usually includes the platform library, extension
     * libraries, and the search path specified by the
     * <code>-classpath</code> option or the <code>CLASSPATH</code>
     * environment variable.
     *
     * <p>When this method is called for the first time, the default
     * class pool is created with the following code snippet:
     *
     * <ul><code>ClassPool cp = new ClassPool();
     * cp.appendSystemPath();
     * </code></ul>
     *
     * <p>If the default class pool cannot find any class files,
     * try <code>ClassClassPath</code> and <code>LoaderClassPath</code>.
     *
     * @see javassist.ClassClassPath
     * @see javassist.LoaderClassPath
     */
    public static synchronized QueryClassPool getDefault() {
        if (instance == null) {
            instance = new QueryClassPool(null);
            instance.appendSystemPath();
        }

        return instance;
    }



    /**
     * Creates a class pool.
     *
     * @param parent the parent of this class pool.  If this is a root
     *               class pool, this parameter must be <code>null</code>.
     * @see javassist.ClassPool#getDefault()
     */
    public QueryClassPool(ClassPool parent) {
        super(parent);
        classmap = Collections.synchronizedMap(new Hashtable(16));
        //classmap = new ClassMap();
        if (parent == null) {
            for (Iterator it = classes.keySet().iterator(); it.hasNext();) {
                final String name = (String) it.next();
                final CtClass cl = (CtClass) classes.get(name);
                //classmap.getAdd(name,cl);
                classmap.put(name,cl);
            }

        }
    }



    /**
     * Provide a hook so that subclasses can do their own
     * caching of classes.
     *
     * @see #cacheCtClass(String,javassist.CtClass,boolean)
     * @see #removeCached(String)
     */
    protected CtClass getCached(String classname) {
        return (CtClass) classmap.get(classname);
    }

    /**
     * Provides a hook so that subclasses can do their own
     * caching of classes.
     *
     * @see #getCached(String)
     * @see #removeCached(String)
     */
    protected void cacheCtClass(String string, CtClass ctClass, boolean b) {
         classmap.put(string,ctClass);
    }

    /**
     * Provide a hook so that subclasses can do their own
     * caching of classes.
     *
     * @see #getCached(String)
     * @see #cacheCtClass(String,CtClass,boolean)
     */
    protected CtClass removeCached(String classname) {
        return (CtClass) classmap.remove(classname);
    }


    /**
     * close the context class pool and clean up the data
     */
    public void close() {
        classmap.clear();
    }

    /**
     * returns the size of the pool
     * @return int
     */
    public int size() {
        return classmap.size();
    }

    /**
     * Iterate class names in the pool
     * @return Iterator
     */
    Iterator getClassNameIterator() {
        return classmap.keySet().iterator();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for(Iterator it = classmap.keySet().iterator();it.hasNext();) {
            sb.append(it.next()).append(",");
        }
        sb.append("]");
        return sb.toString();
    }


}
