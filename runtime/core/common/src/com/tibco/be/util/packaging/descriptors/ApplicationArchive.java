package com.tibco.be.util.packaging.descriptors;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:54:27 PM
 */

import java.io.IOException;
import java.util.List;

import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.vfile.VFileFactory;

public interface ApplicationArchive
        extends ServiceArchive {


//    Map diffApplicationArchive(ApplicationArchive applicationarchive)
//            throws IOException, ObjectRepoException;
//
//    Map diffApplicationArchive(ApplicationArchive applicationarchive, boolean flag)
//            throws IOException, ObjectRepoException;
//
//    Map diffApplicationArchiveContents(ApplicationArchive applicationarchive)
//            throws IOException, ObjectRepoException;
//
//    VFileFactory getExternalJarFile()
//            throws ObjectRepoException, IOException;
//
//    VFileFactory getExternalResourceFile()
//            throws ObjectRepoException, IOException;
//
    ServiceArchive getServiceArchiveByName(String s);

    List getServiceArchives();

    VFileFactory getSharedArchive()
            throws ObjectRepoException, IOException;
//
//    boolean isExternalFileChanged(ApplicationArchive applicationarchive)
//            throws IOException, ObjectRepoException;
//
//    boolean isExternalJarChanged(ApplicationArchive applicationarchive)
//            throws IOException, ObjectRepoException;
//

}