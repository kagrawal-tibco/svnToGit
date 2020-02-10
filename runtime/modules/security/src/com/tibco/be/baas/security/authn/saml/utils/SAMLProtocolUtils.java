/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.utils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 *
 * @author Aditya Athalye
 * Date : 21 Oct, 2011
 */
public class SAMLProtocolUtils {
    
    /**
     * 
     * @param bytes
     * @param encoding
     * @return 
     */
    public static String convertByteArrayToString(byte[] bytes, String encoding) {
        CharsetDecoder decoder = Charset.forName(encoding).newDecoder();
        ByteBuffer bbuf = ByteBuffer.wrap(bytes);
        CharBuffer cBuff = CharBuffer.allocate(bbuf.capacity());
        decoder.decode(bbuf, cBuff, false);
        //Flip the buffer, and take the position to 0.
        ((Buffer)cBuff).flip();
        return cBuff.toString();
    }
}
