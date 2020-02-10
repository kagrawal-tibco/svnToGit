package com.tibco.be.functions.file;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class LowByteCharsetProvider extends CharsetProvider
{
    private static ArrayList<Charset> charsets = new ArrayList(Arrays.asList(LowByteCharset.charset));

    public LowByteCharsetProvider() {
        super();
    }
    
    @Override
    public Iterator<Charset> charsets() {
        return charsets.iterator();
    }

    @Override
    public Charset charsetForName(String charsetName) {
        if(LowByteCharset.name.equals(charsetName)) {
            return LowByteCharset.charset;
        } else {
            return null;
        }
    }        
}