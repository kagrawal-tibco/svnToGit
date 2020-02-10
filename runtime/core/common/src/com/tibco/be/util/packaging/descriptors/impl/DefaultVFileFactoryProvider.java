package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 8, 2010
 * Time: 1:33:54 PM
 */


import java.io.InputStream;
import java.util.zip.ZipInputStream;

import com.tibco.be.util.packaging.descriptors.VFileFactoryProvider;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;


public class DefaultVFileFactoryProvider
        implements VFileFactoryProvider {


    VFileFactory factory;


    public DefaultVFileFactoryProvider(InputStream inputstream)
            throws ObjectRepoException {
        if (inputstream == null) {
            this.factory = new ZipVFileFactory();
        } else {
            if (!(inputstream instanceof ZipInputStream)) {
                inputstream = new ZipInputStream(inputstream);
            }
            this.factory = new ZipVFileFactory((ZipInputStream) inputstream);
        }
    }


    public VFileFactory getVFileFactory(String s)
            throws ObjectRepoException {
        if (s == null) {
            return this.factory;
        }
        if (this.factory.exists(s)) {
            return new ZipVFileFactory(new ZipInputStream(((VFileStream) this.factory.get(s)).getInputStream()));
        } else {
            return new ZipVFileFactory();
        }
    }
}