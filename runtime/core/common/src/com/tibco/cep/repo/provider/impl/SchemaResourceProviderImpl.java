package com.tibco.cep.repo.provider.impl;


import java.io.InputStream;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.provider.SchemaResourceProvider;
import com.tibco.objectrepo.vfile.VFileStream;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 22, 2006
 * Time: 12:17:02 PM
 */

public class SchemaResourceProviderImpl implements SchemaResourceProvider {


    public SchemaResourceProviderImpl() {
    }


    public void clearDirty() {
    }


    public int deserializeResource(String uri, InputStream is, Project project, VFileStream stream) throws Exception {
        is.reset();
        final InputSource source = new InputSource(is);
        source.setSystemId(uri);
        project.getTnsCache().documentAddedOrChanged(source);
        return ResourceProvider.SUCCESS_STOP;

    }


    public List getChangeList() {
        return null;
    }


    public void init() throws Exception {
    }


    public boolean isDirty() {
        return false;
    }


    public boolean supportsResource(String uri) {
        final String upperCaseUri = uri.toUpperCase();
        return upperCaseUri.endsWith("XSD")
                || upperCaseUri.endsWith("AESCHEMA");

    }
}
