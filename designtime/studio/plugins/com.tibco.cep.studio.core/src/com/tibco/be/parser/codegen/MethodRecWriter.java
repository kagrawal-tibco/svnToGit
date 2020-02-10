package com.tibco.be.parser.codegen;

import com.tibco.be.parser.tree.Node;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 12, 2008
 * Time: 5:09:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodRecWriter extends MethodRec {
    private static final String BRK = CGConstants.BRK;
    private LineBuffer bodyBuffer;

    public MethodRecWriter(String shortName,String access,String returntype, Node node) {
        super(access,returntype,shortName);
        this.bodyBuffer = new LineBuffer(node);
    }

    public MethodRecWriter(String shortName, Node node) {
        super(null,null,shortName);        
        this.bodyBuffer = new LineBuffer(node);
    }

    public LineBuffer getLineBuffer() {
        return bodyBuffer;
    }

    public void setBody(LineBuffer bodyBuffer) {
       this.bodyBuffer = bodyBuffer;
    }

    @Override
    public CharSequence getBody() {
        return bodyBuffer;
    }

    @Override
    public void setBody(String body) {
        super.setBody(body);
        bodyBuffer = new LineBuffer();
        bodyBuffer.append(body);
    }

    @Override
    public void setBody(StringBuilder body) {
        super.setBody(body);
        bodyBuffer = new LineBuffer();
        bodyBuffer.append(body);
    }

    public void writeBuffer(LineBuffer lbuffer,boolean adjust) {
        if(adjust) {
            adjustJavaLines(lbuffer.getJavaLine());
        }
        if(access != null && !access.equals("")) {
            lbuffer.append(access);
            lbuffer.append(" ");
        }
        if(returnType == null) {
            lbuffer.append("void");
        } else {
            lbuffer.append(returnType);
        }
        lbuffer.append(" ");
        lbuffer.append(name);
        lbuffer.append("(");
        for(int ii = 0; ii < args.size(); ii++) {
            if(ii > 0) {
                lbuffer.append(", ");
            }
            lbuffer.append((String)args.get(ii));
        }
        lbuffer.append(") ");

        for(int ii = 0; ii < exceptions.size(); ii++) {
            if(ii == 0) {
                lbuffer.append("throws ");
            } else {
                lbuffer.append(", ");
            }
            lbuffer.append((String)exceptions.get(ii));
        }

        lbuffer.append(" {"+ BRK);
        if(bodyBuffer != null) {
//            if(adjust) {
//                bodyBuffer.adjustJavaLines(lbuffer.getJavaLine()-1);
//            }
            lbuffer.append(bodyBuffer.toString());
        }

        lbuffer.append(BRK + "}"+ BRK);
    }

    public String toString() {
        LineBuffer lbuffer = new LineBuffer();
        writeBuffer(lbuffer,false);
        return lbuffer.toString();
    }


    public void adjustJavaLines(int offset) {
        if(bodyBuffer != null) {
            bodyBuffer.adjustJavaLines(offset);
        }
    }
}
