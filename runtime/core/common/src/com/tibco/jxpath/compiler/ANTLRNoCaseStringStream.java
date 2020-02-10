package com.tibco.jxpath.compiler;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;

/*
* Author: Suresh Subramani / Date: 10/30/11 / Time: 7:20 AM
*/
public class ANTLRNoCaseStringStream extends ANTLRStringStream {

    public ANTLRNoCaseStringStream(String p_input) {
        super(p_input);
    }

    public int LA(int p_i) {
        if (p_i == 0) {
            return 0; // undefined
        }
        if (p_i < 0) {
            p_i++; // e.g., translate LA(-1) to use offset 0
        }

        if ((p + p_i - 1) >= n) {

            return CharStream.EOF;
        }
        return Character.toLowerCase(data[p + p_i - 1]);
    }
}
