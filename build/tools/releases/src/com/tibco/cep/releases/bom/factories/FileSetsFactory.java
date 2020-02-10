package com.tibco.cep.releases.bom.factories;

import com.tibco.cep.releases.bom.FileSet;
import com.tibco.cep.releases.bom.FileSets;
import com.tibco.cep.releases.bom.XNames;
import com.tibco.cep.releases.bom.impl.FileSetsImpl;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/27/11
 * Time: 6:54 PM
 */
public class FileSetsFactory
        implements XmlBasedFactory<FileSets> {

    private final FileSetFactory fileSetFactory = new FileSetFactory();


    @Override
    public FileSets make(
            XiNode node)
            throws Exception {

        if (null == node) {
            throw new IllegalArgumentException();
        }

        final SortedMap<String, FileSet> nameToFileSet = new TreeMap<String, FileSet>();

        for (Iterator it = XiChild.getIterator(node, XNames.Nodes.FILE_SET); it.hasNext(); ) {
            final FileSet fileSet = this.fileSetFactory.make((XiNode) it.next());
            nameToFileSet.put(fileSet.getName(), fileSet);
        }

        return new FileSetsImpl(nameToFileSet);
    }


}
