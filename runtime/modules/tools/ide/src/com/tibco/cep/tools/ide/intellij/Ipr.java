package com.tibco.cep.tools.ide.intellij;


import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: nicolasprade
 * Date: 11/30/11
 * Time: 11:21 AM
 */
public class Ipr
        implements IntellijDoc {


    private static final String LIBRARIES_NAME = "LibraryTable";
    private static final String MODULES_NAME = "ProjectModuleManager";
    private static final ExpandedName XNAME_COMPONENT = ExpandedName.makeName("component");
    private static final ExpandedName XNAME_FILEPATH = ExpandedName.makeName("filepath");
    private static final ExpandedName XNAME_FILEURL = ExpandedName.makeName("fileurl");
    private static final ExpandedName XNAME_MODULE = ExpandedName.makeName("module");
    private static final ExpandedName XNAME_MODULES = ExpandedName.makeName("modules");
    private static final ExpandedName XNAME_NAME = ExpandedName.makeName("name");
    private static final ExpandedName XNAME_PROJECT = ExpandedName.makeName("project");


    private final File iprFile;
    private final XiNode librariesNode;
    private final XiNode modulesNode;
    private final XiNode projectNode;

    public Ipr(File iprFile) throws IOException, SAXException {
        this.iprFile = iprFile;

        this.projectNode = XiChild.getChild(
                XiParserFactory.newInstance().parse(new InputSource(new FileReader(this.iprFile))),
                XNAME_PROJECT);

        XiNode libraryParentNode = null, modulesParentNode = null, modulesNode;
        for (final Iterator i = XiChild.getIterator(this.projectNode, XNAME_COMPONENT); i.hasNext(); ) {
            final XiNode node = (XiNode) i.next();
            final String name = node.getAttribute(XNAME_NAME).getStringValue();
            if (LIBRARIES_NAME.equals(name)) {
                libraryParentNode = node;
            } else if (MODULES_NAME.equals(name)) {
                modulesParentNode = node;
            }
        }
        if (null == libraryParentNode) {
            libraryParentNode = this.projectNode.appendElement(XNAME_COMPONENT);
            libraryParentNode.setAttributeStringValue(XNAME_NAME, LIBRARIES_NAME);
        }
        if (null == modulesParentNode) {
            modulesParentNode = this.projectNode.appendElement(XNAME_COMPONENT);
            modulesParentNode.setAttributeStringValue(XNAME_NAME, MODULES_NAME);
            modulesNode = modulesParentNode.appendElement(XNAME_MODULES);
        } else {
            modulesNode = XiChild.getChild(modulesParentNode, XNAME_MODULES);
            if (null == modulesNode) {
                modulesNode = modulesParentNode.appendElement(XNAME_MODULES);
            }
        }
        this.librariesNode = libraryParentNode;
        this.modulesNode = modulesNode;
    }


    public void addModule(String path) {
        final String urlPrefix = this.iprFile.getParent();
        final String relPath = (path.startsWith(urlPrefix))
                ? ("$PROJECT_DIR$" + path.substring(urlPrefix.length()))
                : path;
        XiNode moduleNode = null;
        boolean missing = true;
        for (final Iterator i = XiChild.getIterator(this.modulesNode, XNAME_MODULE); missing && i.hasNext();) {
            moduleNode = (XiNode) i.next();
            final String filePath = moduleNode.getAttribute(XNAME_FILEPATH).getStringValue();
            missing = !(path.equals(filePath) || relPath.equals(filePath));
        }
        if (missing) {
            moduleNode = this.modulesNode.appendElement(XNAME_MODULE);
        }
        moduleNode.setAttributeStringValue(XNAME_FILEURL, "file://" + relPath);
        moduleNode.setAttributeStringValue(XNAME_FILEPATH, relPath);
    }


    public void update() throws IOException {
        final FileWriter fw = new FileWriter(this.iprFile);
        try {
            fw.append(XiSerializer.serialize(this.projectNode));
        } finally {
            fw.close();
        }
    }

}
