package com.tibco.be.parser.codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 2, 2004
 * Time: 12:46:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class JavaFile {
    private static final String BRK = CGConstants.BRK;
    protected String packageName = null;
    protected HashSet imports = new HashSet();
    protected String name;
    protected ArrayList<JavaClass> classes = new ArrayList<JavaClass>();
    
    public JavaFile(String shortName) {
        name = shortName;
    }
    
    public String getShortName() {
        return name;
    }
    
    public String getPackage() {
        return packageName == null ? "" : packageName;
    }
    
    public void setPackage(String packageName) {
        this.packageName = packageName;
    }
    
    public void addImport(String importPath) {
        imports.add(importPath);
    }
    
    public void addClass(JavaClass clazz) {
        classes.add(clazz);
    }
    
    public Iterator<JavaClass> getClasses() {
        return classes.iterator();
    }
    
    public StringBuilder toStringBuilder() {
        StringBuilder ret = new StringBuilder();
        
        if(packageName != null) {
            ret.append("package ");
            ret.append(packageName);
            ret.append(";" + BRK);
        }
        for(Iterator it = imports.iterator(); it.hasNext();) {
            ret.append("import ");
            ret.append((String)it.next());
            ret.append(";" + BRK);
        }
        for(Iterator it = classes.iterator();it.hasNext();) {
            ret.append("" + BRK + BRK);
            ret.append(((JavaClass)it.next()).toStringBuilder());
        }
        return ret;
    }
    
    public String toString() {
        return toStringBuilder().toString();
    }
}
