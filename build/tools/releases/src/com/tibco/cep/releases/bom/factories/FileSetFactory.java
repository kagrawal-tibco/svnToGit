package com.tibco.cep.releases.bom.factories;

import com.tibco.cep.releases.bom.FileSet;
import com.tibco.cep.releases.bom.FileSetType;
import com.tibco.cep.releases.bom.Paths;
import com.tibco.cep.releases.bom.XNames;
import com.tibco.cep.releases.bom.impl.FileSetImpl;
import com.tibco.cep.releases.bom.impl.PathsImpl;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/27/11
 * Time: 6:54 PM
 */
public class FileSetFactory
        implements XmlBasedFactory<FileSet> {


    @Override
    public FileSet make(
            XiNode node)
            throws Exception {

        if (null == node) {
            throw new IllegalArgumentException();
        }

        final String name = node.getAttributeStringValue(XNames.Attributes.NAME);
        final String type = node.getAttributeStringValue(XNames.Attributes.TYPE);
        FileSetType fileSetType;
        try {
            fileSetType = FileSetType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            fileSetType = FileSetType.FILE;
        }

        final Paths buildPaths = this.parsePaths(node, XNames.Nodes.BUILD);
        final Paths installedGaPaths = this.parsePaths(node, XNames.Nodes.INSTALLED_GA);
        final Paths installedHfPaths = this.parsePaths(node, XNames.Nodes.INSTALLED_HF);
        final Paths sourcePaths = this.parsePaths(node, XNames.Nodes.SOURCE);

        return new FileSetImpl(name, fileSetType, buildPaths, installedGaPaths, installedHfPaths, sourcePaths);
    }


    private Paths parsePaths(
            XiNode node,
            ExpandedName xName) {

        final SortedSet<String> paths = new TreeSet<String>();

        for (Iterator it = XiChild.getIterator(node, xName); it.hasNext(); ) {
            paths.add(((XiNode) it.next()).getStringValue().trim());
        }

        return new PathsImpl(paths);
    }

}
