package com.tibco.cep.studio.core.repo.emf.providers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;


public class EMFSharedArchiveResourceProvider
        implements ArchiveResourceProvider {


    protected static final XiParser PARSER = XiSupport.getParser();
    protected static final int BUFFER_SIZE = 2048;


    protected Map registry = new HashMap();
    protected Map xiNodeCache = new HashMap();
    String[] sharedExtensions = new String[]{"sharedjdbc", "sharedjmsapp", "sharedjmscon", "sharedjndiconfig",
            "rvtransport", "cert", "xml", "dtd", "xsd", "wsdl", "sharedhttp", "httpproxy", "id"};

    boolean readingSharedResource = false;


    public byte[] getResourceAsByteArray(String uri) {
        return (byte[]) this.registry.get(uri);
    }


    public XiNode getResourceAsXiNode(String uri) throws Exception {
        XiNode node = (XiNode) this.xiNodeCache.get(uri);

        if (node == null) {
            final byte[] bytes = this.getResourceAsByteArray(uri);
            if (bytes == null) {
                return null;

            } else {
                final InputStream is = new ByteArrayInputStream(bytes);
                try {
                    final InputSource source = new InputSource(is);
                    source.setSystemId(uri);
                    node = PARSER.parse(new InputSource(is));
                    this.xiNodeCache.put(uri, node);
                } finally {
                    is.close();
                }
            }//else
        }//if

        return node.getFirstChild(); // Change by Puneet to sync with how BW treats shared resources
    }


    public int deserializeResource(String uri, InputStream is, Project project, VFileStream stream) throws Exception {
        is.reset();
        uri = stream.getURI();
        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }
        final String lowerCaseUri = uri.toLowerCase();

        if (lowerCaseUri.endsWith(".sar")) {
            this.readingSharedResource = true;
            final ZipVFileFactory vFileFactory = new ZipVFileFactory(new ZipInputStream(is));
            final VFileDirectory dir = vFileFactory.getRootDirectory();
            ((BEProject) project).scanDirectory(dir, uri, null);
            this.readingSharedResource = false;
        } else {
            if (uri.startsWith("mem:///")) {
                uri = uri.substring("mem:///".length());
                final int idx = uri.indexOf('/');
                if (idx != -1) {
                    uri = uri.substring(idx);
                }
            }
            if (lowerCaseUri.endsWith(".xsd")
                    || lowerCaseUri.endsWith(".dtd")
                    || lowerCaseUri.endsWith(".wsdl")) {
                is.reset();
                project.getTnsCache().resourceChanged(uri, is);
            }
            this.register(uri, is);
        }
        return ResourceProvider.SUCCESS_CONTINUE;
    }


    protected void register(String uri, InputStream is) throws Exception {
        final ByteArrayOutputStream boas = new ByteArrayOutputStream();
        final byte[] buffer = new byte[BUFFER_SIZE];

        is.reset();
        for (int bytesRead = 0; bytesRead >= 0;) {
            bytesRead = is.read(buffer);
            if (bytesRead > 0) {
                boas.write(buffer, 0, bytesRead);
            }//if
        }//for
        final byte[] data = boas.toByteArray();

        this.registry.put(uri, data);
        this.xiNodeCache.remove(uri);
    }


    public void init() throws Exception {
    }


    public boolean supportsResource(String uri) {
        final String lowerCaseUri = uri.toLowerCase();
        final String ext = lowerCaseUri.substring(lowerCaseUri.lastIndexOf(".") + 1);
        if (ext.endsWith(".sar")) {
            return true;
        }

        final boolean isBEJarResource = uri.toLowerCase().endsWith("be.jar");
        final boolean isEMFResource = CommonIndexUtils.isEMFType(ext)
                || CommonIndexUtils.isRuleType(ext)
                || CommonIndexUtils.isTnsCacheResource(ext);
        final boolean isTestDataResource = ext.endsWith(".concepttestdata") || ext.endsWith(".eventtestdata");

        if (this.readingSharedResource
                && !(isEMFResource  || isTestDataResource  || isBEJarResource)) {
            return true; // Should handle everything that is a part of the shared resource
        }

        for (final String sharedExtension : this.sharedExtensions) {
            if (lowerCaseUri.endsWith(sharedExtension)) {
                return true;
            }
        }

        return false;
    }


    public Collection getAllResourceURI() {
        return this.registry.keySet();
    }


    public void removeResource(String uri) {
        this.registry.remove(uri);
        this.xiNodeCache.remove(uri);
    }

}
