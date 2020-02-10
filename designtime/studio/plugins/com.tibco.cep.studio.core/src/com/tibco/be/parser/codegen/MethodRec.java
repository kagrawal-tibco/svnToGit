package com.tibco.be.parser.codegen;

import java.util.ArrayList;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 2, 2004
 * Time: 5:33:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodRec {
    private static final String BRK = CGConstants.BRK;
    String name;
    String access = null;
    String returnType = null;
    ArrayList args = new ArrayList();
    ArrayList exceptions = new ArrayList();
    CharSequence body = null;
    
    public MethodRec(String name) {
        this.name = name;
    }
    
    //public MethodRec(MethodRec copy) {
    //    name = copy.name;
    //    access = copy.access;
    //    returnType = copy.returnType;
    //    args = copy.args;
    //    exceptions = copy.exceptions;
    //    body = copy.body;
    //}
    
    public MethodRec(String access, String returnType, String name) {
        this.access = access;
        this.returnType = returnType;
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setAccess(String access) {
        this.access = access;
    }
    
    // the empty string is a valid return type and is
    // used for constructor methods
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
    
    public void addArg(String type, String name) {
        args.add(type + " " + name);
    }
    
    public void addThrows(String exception) {
        exceptions.add(exception);
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public void setBody(StringBuilder body) {
        this.body = body;
    }
    
    public CharSequence getBody() {
        return body;
    }
    
    public StringBuilder toStringBuilder() {
        StringBuilder ret = new StringBuilder();
        
        if(access != null && !access.equals("")) {
            ret.append(access);
            ret.append(" ");
        }
        if(returnType == null) {
            ret.append("void");
        } else {
            ret.append(returnType);
        }
        ret.append(" ");
        ret.append(name);
        ret.append("(");
        for(int ii = 0; ii < args.size(); ii++) {
            if(ii > 0) {
                ret.append(", ");
            }
            ret.append((String)args.get(ii));
        }
        ret.append(") ");
        
        for(int ii = 0; ii < exceptions.size(); ii++) {
            if(ii == 0) {
                ret.append("throws ");
            } else {
                ret.append(", ");
            }
            ret.append((String)exceptions.get(ii));
        }
        
        ret.append(" {" + BRK);
        if(body != null) {
            ret.append(body);
        }
        ret.append(BRK + "}" + BRK);
        
        return ret;
    }
    
    public String toString() {
        return toStringBuilder().toString();
    }
    
    public void addToCtClass(CtClass ctc) throws CannotCompileException {
        if("".equals(returnType)) {
            ctc.addConstructor(CtNewConstructor.make(toString(), ctc));
        } else {
            ctc.addMethod(CtNewMethod.make(toString(), ctc));
        }
    }
}
