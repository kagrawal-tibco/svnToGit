package com.tibco.cep.repo.provider.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.provider.SMapContentProvider;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;
import com.tibco.xml.datamodel.XiNode;


public class SMapContentProviderImpl
        implements SMapContentProvider {


    protected Map<String, byte[]> registry = new HashMap<String, byte[]>();
    protected String jarUri;


    public SMapContentProviderImpl() {
        // TODO Auto-generated constructor stub
    }


    @Override
    public void init() throws Exception {
        // TODO Auto-generated method stub

    }


    public boolean supportsResource(String uri) {
        final String lowerCaseUri = uri.toLowerCase();
        return lowerCaseUri.endsWith(".smap") || lowerCaseUri.endsWith(".jar");

    }


    public int deserializeResource(String uri, InputStream is, Project project,
                                   VFileStream stream) throws Exception {
        if (uri.toLowerCase().endsWith(".jar")) {
            final ZipVFileFactory vFileFactory = new ZipVFileFactory(new ZipInputStream(is));
            this.jarUri = vFileFactory.getURI();
            final VFileDirectory dir = vFileFactory.getRootDirectory();
            ((BEProject) project).scanDirectory(dir, uri, null);
        } else {
            final String smapUri = uri.substring(this.jarUri.length());
            this.registry.put(smapUri, getBytes(is));
        }
        return ResourceProvider.SUCCESS_CONTINUE;
    }


    private static byte[] getBytes(InputStream is) throws Exception {
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        for (int x = is.read(data); x != -1; x = is.read(data)) {
            buf.write(data, 0, x);
        }
        return buf.toByteArray();
    }


    public Collection getAllResourceURI() {
        return this.registry.keySet();
    }


//    public String[] getAllResourcesURIArray() {
//        return this.registry.keySet().toArray(new String[this.registry.size()]);
//    }


    public byte[] getResourceAsByteArray(String uri) {
        return this.registry.get(uri);
    }


    @Override
    public XiNode getResourceAsXiNode(String uri) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void removeResource(String uri) {
        // TODO Auto-generated method stub
    }

}
