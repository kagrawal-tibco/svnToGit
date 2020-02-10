package com.tibco.be.parser.codegen;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 2, 2004
 * Time: 5:33:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitializerRec {
    private static final String BRK = CGConstants.BRK;
    private final boolean isStatic;
    String body = null;
    
    public InitializerRec(boolean isStatic, String body) {
        this.isStatic = isStatic;
        this.body = body;
    }
    
    public boolean isStatic() {
        return isStatic;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public StringBuffer toStringBuffer() {
        StringBuffer ret = new StringBuffer();
        
        if(isStatic) ret.append("static");
        ret.append("{");
        ret.append(BRK);
        ret.append(body);
        ret.append(BRK);
        ret.append("}");
        ret.append(BRK);
        return ret;
    }
    
    public String toString() {
        return toStringBuffer().toString();
    }
}