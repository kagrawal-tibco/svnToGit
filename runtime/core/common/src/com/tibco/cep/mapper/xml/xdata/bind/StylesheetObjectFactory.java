/*
 * User: jroberts
 * Date: Jun 19, 2002
 * Time: 11:00:06 AM
 * To change template for new class use
 */
package com.tibco.cep.mapper.xml.xdata.bind;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.tibco.io.xml.XMLStreamReader;
import com.tibco.objectrepo.ErrorCollectionException;
import com.tibco.objectrepo.NotAllowedException;
import com.tibco.objectrepo.NotSupportedException;
import com.tibco.objectrepo.object.ObjectFactory;
import com.tibco.objectrepo.object.ObjectProvider;
import com.tibco.objectrepo.object.ObjectReadException;
import com.tibco.objectrepo.object.ObjectWriteException;
import com.tibco.objectrepo.object.ObjectWriteState;
import com.tibco.objectrepo.object.ShallowObjectProvider;
import com.tibco.objectrepo.object.SubstitutionVariableAccessor;

/**
 * The factory for the StylesheetSharedResource.
 */
public class StylesheetObjectFactory extends ObjectFactory
{
    public StylesheetObjectFactory() {        
    }

    public Object newInstance(String s, InputStream inputStream, Iterator iterator, Class aClass, SubstitutionVariableAccessor substitutionVariableAccessor) throws ObjectReadException
    {
        if (inputStream==null) {
            return null;
        }
        XMLStreamReader sr = new XMLStreamReader(inputStream);
        StringBuffer sb = new StringBuffer();
        try {
            // Is there a utility method somewhere???
            char[] t = new char[4096];
            for (;;) {
                int l = sr.read(t);
                if (l<=0) {
                    break;
                }
                sb.append(t,0,l);
            }
        } catch (IOException ioe) {
            throw new ObjectReadException(ioe.getMessage(),ioe.getMessage());
        }
        String str = sb.toString();
        return ReadFromXSLT.read(str);
    }

    public Collection startLoading(Object o, InputStream inputStream, Iterator iterator, ShallowObjectProvider shallowObjectProvider) throws ErrorCollectionException
    {
        return new ArrayList();
    }

    public void finishLoading(Object o, InputStream inputStream, Iterator iterator, SubstitutionVariableAccessor substitutionVariableAccessor) throws ErrorCollectionException
    {
    }

    public Object startReload(Object o, InputStream inputStream, Iterator iterator, ShallowObjectProvider shallowObjectProvider) throws ObjectReadException
    {
        return o; //?
    }

    public void finishReload(Object o, InputStream inputStream, Iterator iterator, SubstitutionVariableAccessor substitutionVariableAccessor) throws ErrorCollectionException
    {
    }

    public ObjectWriteState write(Object o, ObjectProvider objectProvider) throws ObjectWriteException
    {
        throw new RuntimeException();
    }

    public Iterator getReferences(Object o, ObjectProvider objectProvider) throws NotAllowedException
    {
        return new ArrayList().iterator();
    }

    public String[] getReferences(InputStream inputStream) throws NotSupportedException
    {
        return new String[0];
    }

    public Object resolve(Object o, String s) throws NotSupportedException
    {
        return null;
    }

    public void delete(Object o, ObjectProvider objectProvider)
    {
    }

    public void validate(Object o) throws ErrorCollectionException
    {
    }
}