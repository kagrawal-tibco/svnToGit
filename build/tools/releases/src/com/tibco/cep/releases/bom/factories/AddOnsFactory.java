package com.tibco.cep.releases.bom.factories;

import com.tibco.cep.releases.bom.AddOn;
import com.tibco.cep.releases.bom.AddOns;
import com.tibco.cep.releases.bom.Assemblies;
import com.tibco.cep.releases.bom.XNames;
import com.tibco.cep.releases.bom.impl.AddOnsImpl;
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
public class AddOnsFactory
        implements XmlBasedFactory<AddOns> {


    private Assemblies assemblies;
    private AddOnFactory addOnFactory;


    public AddOnsFactory(
            Assemblies assemblies) {

        this.setAssemblies(assemblies);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public Assemblies getAssemblies() {

        return this.assemblies;
    }


    @Override
    public AddOns make(
            XiNode node)
            throws Exception {

         if (null == node) {
            throw new IllegalArgumentException();
        }

        final SortedMap<String, AddOn> nameToAddOn = new TreeMap<String, AddOn>();

        for (Iterator it = XiChild.getIterator(node, XNames.Nodes.ADD_ON); it.hasNext(); ) {
            final AddOn addOn = this.addOnFactory.make((XiNode) it.next());
            nameToAddOn.put(addOn.getName(), addOn);
        }

        return new AddOnsImpl(nameToAddOn);

    }


    public void setAssemblies(
            Assemblies assemblies) {

        if (!assemblies.equals(this.assemblies)) {
            this.assemblies = assemblies;
            this.addOnFactory = new AddOnFactory(assemblies);
        }
    }


}
