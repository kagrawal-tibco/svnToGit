package com.tibco.cep.loadbalancer.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: 4/26/11 / Time: 3:24 PM
*/
public class CustomObjectInputStream extends ObjectInputStream {
    protected
    @Optional
    ClassLoader customClassLoader;

    public CustomObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    protected CustomObjectInputStream() throws IOException, SecurityException {
        super();
    }

    public ClassLoader getCustomClassLoader() {
        return customClassLoader;
    }

    public void setCustomClassLoader(ClassLoader customClassLoader) {
        this.customClassLoader = customClassLoader;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws ClassNotFoundException, IOException {
        if (customClassLoader == null) {
            super.resolveClass(desc);
        }

        String className = desc.getName();
        try {
            return customClassLoader.loadClass(className);
        }
        catch (ClassNotFoundException exc) {
            return Class.forName(className);
        }
    }
}
