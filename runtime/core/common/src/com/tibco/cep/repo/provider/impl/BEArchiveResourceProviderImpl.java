package com.tibco.cep.repo.provider.impl;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.xml.sax.InputSource;

import com.tibco.be.util.BEJarVersionsInspector;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.impl.BEArchiveResourceImpl;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 10:25:06 AM
 * To change this template use File | Settings | File Templates.
 */


/**
 * Resource Provider for .bar file, and engine-config.xml
 */
public class BEArchiveResourceProviderImpl
        implements BEArchiveResourceProvider {


    protected static final String CONFIGXML = "engine-config.xml";
    protected static final XiParser PARSER = XiParserFactory.newInstance();

    protected List<BEArchiveResource> beArchives;
    protected Map<String, BEJarVersionsInspector.Version> designTimeVersions;


    public BEArchiveResourceProviderImpl() {
        this.beArchives = new ArrayList<BEArchiveResource>();
        this.designTimeVersions = new LinkedHashMap<String, BEJarVersionsInspector.Version>();

    }


    public byte[] getResourceAsByteArray(String uri) {
        return null;
    }


    public XiNode getResourceAsXiNode(String uri) {
        return null;
    }


    public Collection getAllResourceURI() {
        return null;
    }


    public void removeResource(String uri) {
    }


    public Collection<BEArchiveResource> getBEArchives() {
        return this.beArchives;
    }


    public int deserializeResource(String uri, InputStream is, Project project, VFileStream stream) throws Exception {
        final String lower = uri.toLowerCase();

        if (lower.endsWith(".bar")) {
            this.deserializeBARResource(uri, is, project);
            return SUCCESS_STOP;

        } else if (lower.endsWith(CONFIGXML)) {
            this.deserializeConfigXML(uri, is, project);
            return SUCCESS_STOP;

//      } else if (lower.endsWith(CACHECONFIGXML)) {
//            deserializeCacheXML(uri, is, project);
//            return SUCCESS_STOP;

        } else {
            return SUCCESS_CONTINUE;
        }
    }


    private void deserializeBARResource(String uri, InputStream is, Project project) throws Exception {
        final ZipVFileFactory vFileFactory = new ZipVFileFactory(new ZipInputStream(is));
        final VFileDirectory dir = vFileFactory.getRootDirectory();
        ((BEProject) project).scanDirectory(dir, uri, null);

    }


    private void deserializeConfigXML(String uri, InputStream is, Project project) throws Exception {
        is.reset();
        final InputSource source = new InputSource(is);
        final XiNode doc = PARSER.parse(source);
        final XiNode node = doc.getFirstChild();
        XiNodeUtilities.cleanTextNodes(node);

        this.deserializeDesignTimeVersions(node);
        this.deserializeArchives(node);
        this.deserializeOntologyResources(uri, node, project);
    }

//    /**
//     *
//     * @param uri
//     * @param is
//     * @param project
//     * @throws Exception
//     */
//    private void deserializeCacheXML(String uri, InputStream is, Project project) throws Exception {
//        ByteArrayOutputStream bo = new ByteArrayOutputStream();
//        is.reset();
//        for(int x = is.read(); x != -1; x = is.read()) {
//            bo.write(x);
//        }
//        resources.put(CACHECONFIGXML, bo.toByteArray());
//    }


    private void deserializeOntologyResources(String uri, XiNode node, Project project) throws Exception {
        final XiNode resources = XiChild.getChild(node, Constants.Config.XNames.RESOURCES);
        for (final Iterator i = XiChild.getIterator(resources); i.hasNext();) {
            final XiNode entityNode = (XiNode) i.next();
            ((BEProject) project).getOntologyProvider().deserializeResource(uri, entityNode, project);
        }
    }


    private void deserializeArchives(XiNode node) {
        final XiNode archives = XiChild.getChild(node, Constants.Config.XNames.ARCHIVES);
        for (final Iterator i = XiChild.getIterator(archives); i.hasNext();) {
            final XiNode archiveEle = (XiNode) i.next();
            final BEArchiveResource bear = new BEArchiveResourceImpl(archiveEle, this) {
            };
            this.beArchives.add(bear);
        }
    }


    private void deserializeDesignTimeVersions(XiNode node) {
        final XiNode versionsNode = XiChild.getChild(node, BEJarVersionsInspector.XNAME_VERSIONS);
        if (null != versionsNode) {
            this.designTimeVersions.putAll(new BEJarVersionsInspector().parseJarVersions(versionsNode));
            final String edition = versionsNode.getAttributeStringValue(Constants.Config.XNames.DESIGNTIME_EDITION);
            final String license = versionsNode.getAttributeStringValue(Constants.Config.XNames.DESIGNTIME_LICENSE);
            for (final Iterator i = XiChild.getIterator(versionsNode); i.hasNext();) {
                final XiNode childNode = (XiNode) i.next();
                final ExpandedName childName = childNode.getName();
                if ((childNode.getNodeKind() == XmlNodeKind.ELEMENT)
                        && !BEJarVersionsInspector.XNAME_VERSION.equals(childName)) {
                    this.designTimeVersions.put(childName.getLocalName(),
                            new BEJarVersionsInspector.Version(childName.getLocalName(),
                                    childNode.getStringValue(), edition, license));
                }
            }
        }
    }


    public Map<String, BEJarVersionsInspector.Version> getDesignTimeVersions() {
        return new LinkedHashMap<String, BEJarVersionsInspector.Version>(this.designTimeVersions);
    }


    public void init() throws Exception {
    }


    public boolean supportsResource(String uri) {
        final String lower = uri.toLowerCase();
        
        return lower.endsWith(".bar")
                || lower.endsWith(CONFIGXML);

//        if (lower.endsWith(CACHECONFIGXML)) return true;
        // todo comment out since this affects hot deploy
        // if (bReadingBarResource && lower.endsWith(CONFIGXML)) return true;
//        return false;
    }


}

