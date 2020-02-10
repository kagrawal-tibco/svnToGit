package com.tibco.be.functions.file;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class LowByteCharsetDecoder extends CharsetDecoder
{
    public LowByteCharsetDecoder() {
        super(LowByteCharset.charset, 1, 1);
    }

    @Override
    protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
        if(in.remaining() < out.remaining()) {
            while(in.hasRemaining()) {
                out.put((char)(0xFF & in.get()));
            }
            return CoderResult.UNDERFLOW;
        } else {
            while(out.hasRemaining()) {
                out.put((char)(0xFF & in.get()));
            }
            return CoderResult.OVERFLOW;
        }
    }
}