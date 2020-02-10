package com.tibco.cep.studio.core.util;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.InputSource;

import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.data.primitive.XmlTreeNode;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.navigation.NodeKindNodeTest;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 16, 2007
 * Time: 1:49:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArchiveResourceProvider implements ResourceProvider {


    protected static final String ARCHIVE_EXTENSION = "archive";
    protected static final String NAMESPACE_REPO = "http://www.tibco.com/xmlns/repo/types/2002";

    protected static final String NAME_REPOSITORY = "repository";
    protected static final String NAME_EAR = "enterpriseArchive";
    protected static final String NAME_SAR = "sharedArchive";
    protected static final String NAME_DESIGNER = "designer";

    protected static final ExpandedName XNAME_REPOSITORY = ExpandedName.makeName(NAMESPACE_REPO, NAME_REPOSITORY);
    protected static final ExpandedName XNAME_EAR = ExpandedName.makeName(NAMESPACE_REPO, NAME_EAR);
    protected static final ExpandedName XNAME_SAR = ExpandedName.makeName(NAMESPACE_REPO, NAME_SAR);
    protected static final ExpandedName XNAME_RESOURCE_TYPE = ExpandedName.makeName(NAMESPACE_REPO, "resourceType");
    protected static final ExpandedName XNAME_VERSION = ExpandedName.makeName(NAMESPACE_REPO, "versionProperty");
    protected static final ExpandedName XNAME_AUTHOR = ExpandedName.makeName(NAMESPACE_REPO, "authorProperty");
    protected static final ExpandedName XNAME_ADD_SERVICE_GETTABLE_GVARS = ExpandedName.makeName(NAMESPACE_REPO, "addServiceSettableGvars");
    protected static final ExpandedName XNAME_NAME = ExpandedName.makeName(NAMESPACE_REPO, "name");
    protected static final ExpandedName XNAME_DESIGNER = ExpandedName.makeName(NAMESPACE_REPO, NAME_DESIGNER);
    protected static final ExpandedName XNAME_LOCKED_PROPS = ExpandedName.makeName(NAMESPACE_REPO, "lockedProperties");
    protected static final ExpandedName XNAME_FIXED_CHILDREN = ExpandedName.makeName(NAMESPACE_REPO, "fixedChildren");
    protected static final ExpandedName XNAME_RESOURCE_DESCRIPTIONS = ExpandedName.makeName(NAMESPACE_REPO, "resourceDescriptions");
    protected static final ExpandedName XNAME_FILE_LOCATION = ExpandedName.makeName(NAMESPACE_REPO, "fileLocationProperty");


    protected static final XmlNodeTest NODE_TEST_ELEMENT  = NodeKindNodeTest.getInstance(XmlNodeKind.ELEMENT);

    private XiParser parser;
    private Map earPathsToEars;


    public ArchiveResourceProvider() {
        this.parser = XiParserFactory.newInstance();
        this.earPathsToEars = new HashMap();
    }

    public void init() throws Exception {
    }


    public int deserializeResource(String uri, InputStream is, Project project, VFileStream stream) throws Exception {
        final XiNode node = this.parser.parse(new InputSource(is));

        Map model = this.parseNode(node);
        model = (Map) model.get(NAME_REPOSITORY);
        model = (Map) model.get(NAME_EAR);

        this.earPathsToEars.put("/" + stream.getURI(), model);

        return ResourceProvider.SUCCESS_STOP;
    }


    private Map parseNode(XiNode node) {
        final Map model = new HashMap();

        for (Iterator it = XiChild.getIterator(node); it.hasNext();) {
            final XiNode childNode = (XiNode) it.next();
            final String name = childNode.getName().getLocalName();
            if (Constants.PROPERTY_NAME_BUSINESSEVENTS_ARCHIVE.equals(name)) {
                Map barsMap = (Map) model.get(Constants.PROPERTY_NAME_BUSINESSEVENTS_ARCHIVES);
                if (null == barsMap) {
                    barsMap = new HashMap();
                    model.put(Constants.PROPERTY_NAME_BUSINESSEVENTS_ARCHIVES, barsMap);
                }
                final String barName = childNode.getAttributeStringValue(Constants.XNAME_NAME);
                final Map barMap = parseNode(childNode);
                barMap.put(Constants.PROPERTY_NAME_NAME, barName);
                barsMap.put(barName, barMap);
            } else if (Constants.PROPERTY_NAME_DESTINATIONS_INPUT_ENABLED.equals(name)
                    || Constants.PROPERTY_NAME_RULESETS.equals(name)
                    || Constants.PROPERTY_NAME_STARTUP.equals(name)
                    || Constants.PROPERTY_NAME_SHUTDOWN.equals(name)) {
                final String value = childNode.getStringValue();
                final String[] strings = Utils.getUrisAsStringArrayFromStringProperty(value);
                model.put(name, strings);
            } else if (Constants.PROPERTY_NAME_DESTINATIONS_RULEFUNCTIONS.equals(name)
                    || Constants.PROPERTY_NAME_DESTINATIONS_WORKERS.equals(name)
                    || Constants.PROPERTY_NAME_DESTINATIONS_QUEUE_SIZE.equals(name)
                    || Constants.PROPERTY_NAME_DESTINATIONS_QUEUE_WEIGHT.equals(name)) {
                final String value = childNode.getStringValue();
                final Map map = Utils.getUrisAsMapFromStringProperty(value);
                model.put(name, map);
            } else {
                boolean hasChildElements = false;
                for (Iterator itChild = XiChild.getIterator(childNode); (!hasChildElements) && itChild.hasNext();) {
                    hasChildElements = NODE_TEST_ELEMENT.matches((XmlTreeNode) itChild.next());
                }
                if (hasChildElements) {
                    model.put(name, this.parseNode(childNode));
                } else {
                    model.put(name, childNode.getStringValue());
                }
            }//else
        }//for

        return model;
    }


    public Map getEars() {
        return this.earPathsToEars;
    }


    public boolean supportsResource(String uri) {
        final int lastDotPosition = uri.lastIndexOf(".");
        if ((lastDotPosition >= 0) && (lastDotPosition < uri.length() - 1)) {
            final String extension = uri.substring(lastDotPosition + 1);
            return ARCHIVE_EXTENSION.equals(extension);
        }
        return false;
    }


}
