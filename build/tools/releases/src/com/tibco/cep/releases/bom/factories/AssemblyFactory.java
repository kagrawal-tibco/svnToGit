package com.tibco.cep.releases.bom.factories;

import com.tibco.cep.releases.bom.Assembly;
import com.tibco.cep.releases.bom.FileSet;
import com.tibco.cep.releases.bom.FileSets;
import com.tibco.cep.releases.bom.XNames;
import com.tibco.cep.releases.bom.impl.AssemblyImpl;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * User: nprade
 * Date: 6/27/11
 * Time: 6:46 PM
 */
public class AssemblyFactory
        implements XmlBasedFactory<Assembly> {


    private final FileSets fileSets;


    public AssemblyFactory(
            FileSets fileSets) {

        this.fileSets = fileSets;
    }


    @Override
    public Assembly make(
            XiNode node)
            throws Exception {

        if (null == node) {
            throw new IllegalArgumentException();
        }

        final String name = node.getAttributeStringValue(XNames.Attributes.NAME);

        final SortedMap<String, FileSet> nameToFileSet = new TreeMap<String, FileSet>();

        for (Iterator it = XiChild.getIterator(node, XNames.Nodes.FILE_SET_REF); it.hasNext(); ) {
            final XiNode fileSetRefNode = (XiNode) it.next();
            final String ref = fileSetRefNode.getAttributeStringValue(XNames.Attributes.REF);
            nameToFileSet.put(ref, this.fileSets.get(ref));
        }

        final boolean portSpecific = Boolean.valueOf(node.getAttributeStringValue(XNames.Attributes.PORT_SPECIFIC));

        return new AssemblyImpl(name, nameToFileSet, portSpecific);
    }


}
