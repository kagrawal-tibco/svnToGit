package com.tibco.be.util.packaging.descriptors;
/*
 * User: nprade
 * Date: Feb 8, 2010
 * Time: 2:10:48 PM
 */


import java.io.IOException;

import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.vfile.VFileFactory;


public interface VFileFactoryProvider {


    VFileFactory getVFileFactory(String s)
            throws IOException, ObjectRepoException;


}
