package com.tibco.cep.releases.bom.factories;

import com.tibco.cep.releases.bom.*;
import com.tibco.cep.releases.bom.impl.BomImpl;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/27/11
 * Time: 6:26 PM
 */
public class BomFactory
        implements XmlBasedFactory<Bom> {


    private final FileSetsFactory fileSetsFactory = new FileSetsFactory();


    public Bom make(
            String path)
            throws Exception {

        return this.make(new File(path));
    }

    public Bom make(
            File file)
            throws Exception {

        final InputStream is = new FileInputStream(file);
        try {
            return this.make(is);
        } finally {
            is.close();
        }
    }


    public Bom make(
            InputStream stream)
            throws Exception {

        final XiNode doc = XiParserFactory.newInstance().parse(new InputSource(stream));

        return this.make(XiChild.getChild(doc, XNames.Nodes.BOM));
    }


    public Bom make(
            XiNode node)
            throws Exception {

        if (null == node) {
            throw new IllegalArgumentException();
        }

        final String name = node.getAttributeStringValue(XNames.Attributes.NAME);

        final FileSets fileSets = this.fileSetsFactory
                .make(XiChild.getChild(node, XNames.Nodes.FILE_SETS));

        final Assemblies assemblies = new AssembliesFactory(fileSets)
                .make(XiChild.getChild(node, XNames.Nodes.ASSEMBLIES));

        final AddOns addOns = new AddOnsFactory(assemblies)
                .make(XiChild.getChild(node, XNames.Nodes.ADD_ONS));

        return new BomImpl(name, addOns, assemblies, fileSets);
    }

}
