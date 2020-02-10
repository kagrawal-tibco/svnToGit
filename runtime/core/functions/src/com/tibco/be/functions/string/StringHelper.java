package com.tibco.be.functions.string;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import static com.tibco.be.model.functions.FunctionDomain.*;

/**
 * User: ssubrama
 * Date: Jul 7, 2004
 * Time: 4:03:45 PM
 */
public class StringHelper {
    @com.tibco.be.model.functions.BEFunction(
        name = "concat",
        synopsis = "Concatenates two strings and return the result.",
        signature = "String concat(String o1, String o2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "o1", type = "String", desc = "The first String to be concatenated."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "o2", type = "String", desc = "The second String to be concatenated.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The String result of concatenating the first argument and the second argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "If the length of the second argument string is 0, then the first String object is returned.\nOtherwise, a new String object is created, representing a character sequence that is the\nconcatenation of the character sequence represented by the first String object and the character\nsequence represented by the second argument string.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String concat(String o1, String o2) {
        StringBuffer sb = new StringBuffer();
        sb.append(o1);
        sb.append(o2);
        return sb.toString();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "convertByteArrayToString",
        synopsis = "A faster way of converting a <code>byte[]</code> to <code>String</code> object.\n<p>\nThis is especially useful when the buffer sizes are very large, where\n<code>String.getBytes(String)</code> is not optimal.\n</p>",
        signature = "String convertByteArrayToString(Object bytesObject, String encoding)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bytesObject", type = "Object", desc = "The input byte[]"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encoding", type = "String", desc = "The character set to use")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Representation of this <tt>byte[]</tt>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A faster way of converting a <code>byte[]</code> to <code>String</code> object.\n<p>\nThis is especially useful when the buffer sizes are very large, where\n<code>String.getBytes(String)</code> is not optimal.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String convertByteArrayToString(Object bytesObject, String encoding) {
        if (!(bytesObject instanceof byte[])) {
            throw new IllegalArgumentException("Input should be a byte[]");
        }
        byte[] bytes = (byte[])bytesObject;
        CharsetDecoder decoder = Charset.forName(encoding).newDecoder();
        ByteBuffer bbuf = ByteBuffer.wrap(bytes);
        CharBuffer cBuff = CharBuffer.allocate(bbuf.capacity());
        decoder.decode(bbuf, cBuff, false);
        //Flip the buffer, and take the position to 0.
        ((Buffer)cBuff).flip();
        String s = cBuff.toString();
        return s;
    }
}
