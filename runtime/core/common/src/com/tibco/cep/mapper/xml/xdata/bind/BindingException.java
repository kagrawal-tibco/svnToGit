package com.tibco.cep.mapper.xml.xdata.bind;

import java.io.PrintStream;
import java.io.PrintWriter;

public class BindingException extends RuntimeException {
    private String mData;
    public Throwable mChained;

    public BindingException(String msg, String data, Throwable e) {
        super(msg);
        mChained = e;
        mData = data;
    }

    public BindingException(String msg, Throwable e) {
        super(msg);
        mChained = e;
    }

    public BindingException(Throwable e) {
        this("Binding exception",null,e);
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream ps) {
        //ps.println(getMessage());
        super.printStackTrace(ps);
        if (mData!=null) {
            ps.println("Data\n" + mData);
        }
        if (mChained!=null) {
            ps.print("caused by: ");
            mChained.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        //pw.println(getMessage());
        super.printStackTrace(pw);
        if (mData!=null) {
            pw.println("Data\n" + mData);
        }
        if (mChained!=null) {
            pw.print("caused by: ");
            mChained.printStackTrace(pw);
        }
    }
}
