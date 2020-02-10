package com.tibco.be.functions.file;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

//emits characters where each byte from the input becomes the low byte of a character, like how RandomAccessFile does
public class LowByteCharset extends Charset
{
    public static final String name = "com.tibco.be.functions.FileHelper.LowByteCharset";
    public static final Charset charset = new LowByteCharset();
    private static final CharsetDecoder decoder = new LowByteCharsetDecoder();
    
    protected LowByteCharset() {
        super(name, null);
    }

    @Override
    public boolean contains(Charset cs) {
        return cs instanceof LowByteCharset;
    }

    @Override
    public CharsetDecoder newDecoder() {
        return decoder;
    }

    @Override
    public CharsetEncoder newEncoder() {
        return null;
    }
}