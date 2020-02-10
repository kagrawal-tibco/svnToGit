package com.tibco.cep.releases.bom.factories;

import com.tibco.cep.releases.bom.AddOn;
import com.tibco.cep.releases.bom.Assemblies;
import com.tibco.cep.releases.bom.Assembly;
import com.tibco.cep.releases.bom.XNames;
import com.tibco.cep.releases.bom.impl.AddOnImpl;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * User: nprade
 * Date: 6/27/11
 * Time: 6:54 PM
 */
public class AddOnFactory
        implements XmlBasedFactory<AddOn> {


    private final Assemblies assemblies;


    public AddOnFactory(
            Assemblies assemblies) {

        this.assemblies = assemblies;
    }


    @Override
    public AddOn make(
            XiNode node)
            throws Exception {

        if (null == node) {
            throw new IllegalArgumentException();
        }

        final String name = node.getAttributeStringValue(XNames.Attributes.NAME);

        final SortedMap<String, Assembly> nameToAssembly = new TreeMap<String, Assembly>();

        for (Iterator it = XiChild.getIterator(node, XNames.Nodes.ASSEMBLY_REF); it.hasNext(); ) {
            final XiNode fileSetRefNode = (XiNode) it.next();
            final String ref = fileSetRefNode.getAttributeStringValue(XNames.Attributes.REF);
            nameToAssembly.put(ref, this.assemblies.get(ref));
        }

        return new AddOnImpl(name, nameToAssembly);
    }


}
