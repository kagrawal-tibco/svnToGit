package com.tibco.be.parser.codegen;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 2, 2004
 * Time: 5:33:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class MemberRec {
    private static final String BRK = CGConstants.BRK;
    String name;
    String access = null;
    String type = null;
    String initializer = null;
    
    public MemberRec(String name) {
        this(null, null, name, null);
    }
    
    public MemberRec(String access, String type, String name) {
        this(access, type, name, null);
    }

    public MemberRec(String access, String type, String name, String initializer) {
        this.access = access;
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    public void setAccess(String access) {
        this.access = access;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setInitializer(String initializer) {
        this.initializer = initializer;
    }
    
    public StringBuffer toStringBuffer() {
        StringBuffer ret = new StringBuffer();
        
        if(access != null && !access.equals("")) {
            ret.append(access);
            ret.append(" ");
        }
        ret.append(type);
        ret.append(" ");
        ret.append(name);

        if(initializer != null) {
            ret.append(" = ");
            ret.append(initializer);
        }
        ret.append(";");
        ret.append(BRK);
        return ret;
    }
    
    public String toString() {
        return toStringBuffer().toString();
    }
    
    public void addToCtClass(CtClass ctc) throws CannotCompileException {
        ctc.addField(CtField.make(toString(), ctc));
    }
}